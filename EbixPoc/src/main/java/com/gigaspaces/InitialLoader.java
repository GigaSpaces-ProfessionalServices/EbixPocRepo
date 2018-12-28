package com.gigaspaces;

import com.gigaspaces.pojos.*;
import com.gigaspaces.pojos.tables.*;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Denys_Novikov
 * Date: 12/19/18
 */
public class InitialLoader {

    private static Logger logger = LoggerFactory.getLogger(InitialLoader.class);
    private static final LhSeqComparator LHSEQ_COMPARATOR = new LhSeqComparator();
    private static final LSeqComparator LSEQ_COMPARATOR = new LSeqComparator();

    @Autowired
    private GigaSpace gigaspace;

    public void init() {
        InsurancePolicyItem[] insurancePolicyItems = gigaspace.readMultiple(createInsurancePolicyItemSQLQuery());
        logger.info("GROUPING BY COUNTRY,STATE");

        Set<GroupingAsset> groupingAssets = Arrays.stream(insurancePolicyItems).map(insurancePolicyItem ->
                new GroupingAsset(insurancePolicyItem.getCountry(), insurancePolicyItem.getState())).collect(Collectors.toSet());

        logger.info("Step 1 result size -> " + groupingAssets.size());

        //---------------------------

        logger.info("Create GroupPolicyItems");
        InsurancePolicy[] insurancePolicies = gigaspace.readMultiple(createInsurancePolicySQLQuery());

        Map<Integer, List<InsurancePolicyItem>> policyaidToItem = Arrays.stream(insurancePolicyItems).collect(Collectors.groupingBy(InsurancePolicyItem::getPolicyaId));
        Map<Integer, InsurancePolicy> idToPolicy = Arrays.stream(insurancePolicies).collect(Collectors.toMap(InsurancePolicy::getId, item -> item));

        List<GroupPolicyItem> groupPolicyItems = new ArrayList<>();

        for (Map.Entry<Integer, List<InsurancePolicyItem>> entry : policyaidToItem.entrySet()) {
            if (idToPolicy.containsKey(entry.getKey())) {

                for (InsurancePolicyItem insurancePolicyItem : entry.getValue()) {
                    GroupPolicyItem groupPolicyItem = new GroupPolicyItem();

                    groupPolicyItem.setId(insurancePolicyItem.getId());
                    groupPolicyItem.setReference(insurancePolicyItem.getReference());
                    groupPolicyItem.setCountry(insurancePolicyItem.getCountry());
                    groupPolicyItem.setState(insurancePolicyItem.getState());
                    groupPolicyItem.setCity(insurancePolicyItem.getCity());
                    groupPolicyItem.setTiv(insurancePolicyItem.getTiv());
                    groupPolicyItem.setInception(insurancePolicyItem.getInception());
                    groupPolicyItem.setExpiry(insurancePolicyItem.getExpiry());
                    groupPolicyItem.setPolicyaid(insurancePolicyItem.getPolicyaId());
                    groupPolicyItem.setTempPolicyItemId(insurancePolicyItem.getTempPolicyItemId());
                    groupPolicyItem.setLine(idToPolicy.get(entry.getKey()).getLine());
                    groupPolicyItem.setPolicyReference(idToPolicy.get(entry.getKey()).getReference());

                    groupPolicyItems.add(groupPolicyItem);
                }
            }
        }

        Map<String, Integer> countryStateGroupingAssets = groupingAssets.stream().collect(Collectors.toMap(asset -> asset.getCountry() + asset.getState(), GroupingAsset::getId));
        Map<String, List<GroupPolicyItem>> countryStateGroupPolicyItems = groupPolicyItems.stream().collect(Collectors.groupingBy(item -> item.getCountry() + item.getState()));


        for (Map.Entry<String, List<GroupPolicyItem>> entry : countryStateGroupPolicyItems.entrySet()) {
            if (countryStateGroupingAssets.containsKey(entry.getKey())) {
                entry.getValue().forEach(item -> item.setGroupingId(countryStateGroupingAssets.get(entry.getKey())));
            }
        }


        // step2result contains all results
        logger.info("Step 2 result size -> " + groupPolicyItems.size());

        //---------------------------
        // copy data from groupPolicyItems to coverage (COLLECT ALL COVERAGES)

        logger.info("Collecting all coverages");
        InsurancePolicyItemCoverageValue[] insurancePolicyItemCoverageValues = gigaspace.readMultiple(createInsurancePolicyCoverageValueSQLQuery());

        Map<Integer, List<InsurancePolicyItemCoverageValue>> mappedByPolicyItemId = Arrays.stream(insurancePolicyItemCoverageValues).collect(Collectors.groupingBy(InsurancePolicyItemCoverageValue::getPolicyItemId));

        List<Coverage> coverages = new ArrayList<>();

        for (GroupPolicyItem groupPolicyItem : groupPolicyItems) {

            Coverage coverage = new Coverage();
            coverage.setId(groupPolicyItem.getId());
            coverage.setReference(groupPolicyItem.getReference());
            coverage.setCountry(groupPolicyItem.getCountry());
            coverage.setState(groupPolicyItem.getState());
            coverage.setCity(groupPolicyItem.getCity());
            coverage.setInception(groupPolicyItem.getInception());
            coverage.setExpiry(groupPolicyItem.getExpiry());
            coverage.setPolicyaid(groupPolicyItem.getPolicyaid());
            coverage.setGroupingId(groupPolicyItem.getGroupingId());

            coverage.setSumTIVForExcess(new BigDecimal(groupPolicyItem.getTiv(), new MathContext(20)).setScale(8));
            coverage.setApportionExcess(new BigDecimal(0, new MathContext(20)).setScale(8));
            coverage.setSumTIVForLimit(new BigDecimal(groupPolicyItem.getTiv(), new MathContext(20)).setScale(8));
            coverage.setApportionLimit(new BigDecimal(0, new MathContext(20)).setScale(8));
            coverage.setSumTIVForExcessCSL(new BigDecimal(groupPolicyItem.getTiv(), new MathContext(20)).setScale(8));
            coverage.setSumTIVForLimitCSL(new BigDecimal(groupPolicyItem.getTiv(), new MathContext(20)).setScale(8));

            if (mappedByPolicyItemId.containsKey(groupPolicyItem.getId())) {
                for (InsurancePolicyItemCoverageValue insurancePolicyItemCoverageValue : mappedByPolicyItemId.get(groupPolicyItem.getId())) {

                    Coverage newCoverage = new Coverage(coverage);

                    newCoverage.setCoverageId(insurancePolicyItemCoverageValue.getCoverageId());
                    newCoverage.setValue(insurancePolicyItemCoverageValue.getValue());
                    newCoverage.setCurrencyId(insurancePolicyItemCoverageValue.getCurrencyId());
                    newCoverage.setAi(insurancePolicyItemCoverageValue.getAi());
                    newCoverage.setCoverageDependencyId(insurancePolicyItemCoverageValue.getCoverageDependencyId());
                    newCoverage.setPercentage(insurancePolicyItemCoverageValue.getPercentage());
                    newCoverage.setPeriodOfIndemnity(insurancePolicyItemCoverageValue.getPeriodOfIndemnity());
                    newCoverage.setNewExposure(insurancePolicyItemCoverageValue.getValue().setScale(8));

                    coverages.add(newCoverage);
                }
            } else {
                coverages.add(coverage);
            }
        }


        logger.info("Step 3 result size -> " + coverages.size());



        Limit[] limits = gigaspace.readMultiple(createLimitSQLQuery());
        LimitLocation[] limitLocations = gigaspace.readMultiple(createLimitLocationSQLQuery());
        LimitCoverage[] limitCoverages = gigaspace.readMultiple(createLimitCoverageSQLQuery());

        Map<Integer, InsurancePolicy> mappedById = Arrays.stream(insurancePolicies).collect(Collectors.toMap(InsurancePolicy::getId, policy -> policy));
        Map<Integer, Limit> mappedLimitByLimitId = Arrays.stream(limits).collect(Collectors.toMap(Limit::getLimitId, limit -> limit));
        Map<Integer, List<LimitLocation>> mappedLimitLocationByLocationId = Arrays.stream(limitLocations).collect(Collectors.groupingBy(LimitLocation::getLocationId));
        Map<Integer, List<LimitCoverage>> mappedLimitCoverageByLimitId = Arrays.stream(limitCoverages).collect(Collectors.groupingBy(LimitCoverage::getLimitId));


        List<QueryLimit> resultLimits = new ArrayList<>();

        for (GroupPolicyItem groupPolicyItem : groupPolicyItems) {

            QueryLimit queryLimit = new QueryLimit();
            queryLimit.setId(groupPolicyItem.getId());
            queryLimit.setPolicyaid(groupPolicyItem.getPolicyaid());
            queryLimit.setGroupingId(groupPolicyItem.getGroupingId());
            queryLimit.setLine(new BigDecimal(groupPolicyItem.getLine(), new MathContext(20)).setScale(8).divide(new BigDecimal(100)));
            queryLimit.setReference(groupPolicyItem.getReference());

            queryLimit.setAfterExcess(new BigDecimal(0, new MathContext(20)).setScale(8));
            queryLimit.setMaxSeq(false);
            queryLimit.setFinalExposure(new BigDecimal(0, new MathContext(20)).setScale(8));

            if (mappedById.containsKey(groupPolicyItem.getPolicyaid())) {
                InsurancePolicy insurancePolicy = mappedById.get(groupPolicyItem.getPolicyaid());

                queryLimit.setPolicyReference(insurancePolicy.getReference());
                queryLimit.setStatus(insurancePolicy.getStatus());
            }

            if (mappedLimitLocationByLocationId.containsKey(groupPolicyItem.getId())) {
                for (LimitLocation limitLocation : mappedLimitLocationByLocationId.get(groupPolicyItem.getId())) {
                    
                    QueryLimit updatedWithLimitLocation = new QueryLimit(queryLimit);

                    if (mappedLimitByLimitId.containsKey(limitLocation.getLimitId())) {

                        Limit limit = mappedLimitByLimitId.get(limitLocation.getLimitId());
                        updatedWithLimitLocation.setLimit(BigDecimal.ZERO.add(limit.getLimit(), new MathContext(20)).setScale(8));
                        updatedWithLimitLocation.setExcess(BigDecimal.ZERO.add(limit.getExcess(), new MathContext(20)).setScale(8));
                        updatedWithLimitLocation.setDeductible(limit.getDeductible());
                        updatedWithLimitLocation.setNewLimit(BigDecimal.ZERO.add(limit.getLimit(), new MathContext(20)).setScale(8));
                        updatedWithLimitLocation.setNewExcess(BigDecimal.ZERO.add(limit.getExcess(), new MathContext(20)).setScale(8));
                        updatedWithLimitLocation.setNewDeductible(limit.getDeductible());
                        updatedWithLimitLocation.setCsl(limit.getCsl());
                        updatedWithLimitLocation.setLimitType(limit.getLimitType());
                        updatedWithLimitLocation.setLhSeq(limit.getLimitHeaderSequence());
                        updatedWithLimitLocation.setRiLimit(limit.getRiLimit());
                        updatedWithLimitLocation.setlSeq(limit.getLimitSequence());
                        updatedWithLimitLocation.setLimitHeaderName(limit.getLimitHeaderName());

                    }

                    if (mappedLimitCoverageByLimitId.containsKey(limitLocation.getLimitId())) {

                        for (LimitCoverage limitCoverage : mappedLimitCoverageByLimitId.get(limitLocation.getLimitId())) {
                            QueryLimit updatedWithLimitCoverage = new QueryLimit(updatedWithLimitLocation);
                            updatedWithLimitCoverage.setCslSeq(limitCoverage.getCslSeq());
                            updatedWithLimitCoverage.setCoverageId(limitCoverage.getCoverageId());

                            resultLimits.add(updatedWithLimitCoverage);
                        }

                    } else {
                        resultLimits.add(updatedWithLimitLocation);
                    }

                }
            } else {
                resultLimits.add(queryLimit);
            }

        }

        logger.info("Limits size -> " + resultLimits.size());


        logger.info("Creating combinations...");

        Map<String, Coverage> mappedCoverages = coverages.stream().collect(Collectors.toMap(coverage -> "" + coverage.getId() + coverage.getPolicyaid() + coverage.getGroupingId() + coverage.getCoverageId(), coverage -> coverage));
        Map<String, List<QueryLimit>> mappedLimits = resultLimits.stream().collect(Collectors.groupingBy(limit -> "" + limit.getId() + limit.getPolicyaid() + limit.getGroupingId() + limit.getCoverageId()));

        List<Combination> combinations = new ArrayList<>();

        for (Map.Entry<String, Coverage> entry : mappedCoverages.entrySet()) {

            Coverage coverage = entry.getValue();
            List<QueryLimit> matchedLimits = mappedLimits.remove(entry.getKey());
            boolean limitExists = matchedLimits != null;

            Combination combination = new Combination();
            combination.setId(coverage.getId());
            combination.setPolicyaid(coverage.getPolicyaid());
            combination.setGroupingId(coverage.getGroupingId());
            combination.setCoverageId(coverage.getCoverageId());


            combination.setTIV(coverage.getValue() != null ? coverage.getValue() : BigDecimal.ZERO);
            combination.setCovCurrency(coverage.getCurrencyId() != null ? coverage.getCurrencyId() : "USD");
            combination.setCovAi(coverage.getAi() != null ? coverage.getAi() : false);
            combination.setCoverageDependencyId(coverage.getCoverageDependencyId());
            combination.setPercentage(coverage.getPercentage());
            combination.setPeriodOfIndemnity(coverage.getPeriodOfIndemnity());
            combination.setSumTIVForExcess(coverage.getSumTIVForExcess() != null ? coverage.getSumTIVForExcess() : BigDecimal.ZERO);
            combination.setApportionExcess(coverage.getApportionExcess() != null ? coverage.getApportionExcess() : BigDecimal.ZERO);
            combination.setSumTIVForLimit(coverage.getSumTIVForLimit() != null ? coverage.getSumTIVForLimit() : BigDecimal.ZERO);
            combination.setApportionLimit(coverage.getApportionLimit() != null ? coverage.getApportionLimit() : BigDecimal.ZERO);
            combination.setSumTIVForExcessCSL(coverage.getSumTIVForExcessCSL() != null ? coverage.getSumTIVForExcessCSL() : BigDecimal.ZERO);
            combination.setSumTIVForLimitCSL(coverage.getSumTIVForLimitCSL() != null ? coverage.getSumTIVForLimitCSL() : BigDecimal.ZERO);
            combination.setNewExposure(coverage.getNewExposure() != null ? coverage.getNewExposure() : BigDecimal.ZERO);

            if (limitExists) {
                fillCombinationWithLimit(combinations, combination, matchedLimits);
            } else {
                combinations.add(combination);
            }

        }

        if (!mappedLimits.isEmpty()) {
            fillCombinationWithLimit(combinations, new Combination(), mappedLimits.values().stream().flatMap(List::stream).collect(Collectors.toList()));
        }


        logger.info("Combinations size -> " + combinations.size());
        logger.info("Calculating loops...");

        Map<String, List<Combination>> mappedCombinations = combinations.stream().collect(Collectors.groupingBy(combination -> "" + combination.getId() + combination.getPolicyaid() + combination.getGroupingId()));
        Map<String, GroupPolicyItem> mappedGroupPolicyItems = groupPolicyItems.stream().collect(Collectors.toMap(groupPolicyItem -> "" + groupPolicyItem.getId() + groupPolicyItem.getPolicyaid() + groupPolicyItem.getGroupingId(), groupPolicyItem -> groupPolicyItem));

        List<CalculationLoop> calculationLoops = new ArrayList<>();

        for (Map.Entry<String, List<Combination>> entry : mappedCombinations.entrySet()) {

            if (mappedGroupPolicyItems.containsKey(entry.getKey())) {

                for (Combination combination : entry.getValue()) {
                    GroupPolicyItem groupPolicyItem = mappedGroupPolicyItems.get(entry.getKey());

                    CalculationLoop calculationLoop = new CalculationLoop();

                    calculationLoop.copyMatchingFields(combination);
                    calculationLoop.setPolicyReference(groupPolicyItem.getPolicyReference());
                    calculationLoop.setLine(new BigDecimal(groupPolicyItem.getLine(), new MathContext(20)).setScale(8).divide(new BigDecimal(100)));
                    calculationLoop.setReference(groupPolicyItem.getReference());
                    calculationLoop.setCountry(groupPolicyItem.getCountry());
                    calculationLoop.setState(groupPolicyItem.getState());
                    calculationLoop.setCity(groupPolicyItem.getCity());
                    calculationLoop.setInception(groupPolicyItem.getInception());
                    calculationLoop.setExpiry(groupPolicyItem.getExpiry());

                    calculationLoops.add(calculationLoop);
                }
            }
        }

        logger.info("calculationLoops size -> " + calculationLoops.size());


        Set<CalculationLoop> calculationLoopsRI = calculationLoops.stream().filter(CalculationLoop::getRiLimit).collect(Collectors.toSet());
        calculationLoops = calculationLoops.stream().filter(calculationLoop -> !calculationLoop.getRiLimit()).collect(Collectors.toList());

        logger.info("Unique calculationLoops -> " + calculationLoopsRI.size());
        logger.info("After filter calculationLoops -> " + calculationLoops.size());
        updateMaxCalculationLoops(calculationLoops);
        updateMaxCalculationLoops(calculationLoopsRI);


    }

    private void updateMaxCalculationLoops(Collection<CalculationLoop> calculationLoops) {
        Map<String, List<CalculationLoop>> groupedCalculationLoops = calculationLoops.stream().collect(Collectors.groupingBy(calculationLoop -> "" + calculationLoop.getId() + calculationLoop.getPolicyaid() + calculationLoop.getGroupingId()));

        List<Max> maxList = new ArrayList<>();

        for (Map.Entry<String, List<CalculationLoop>> entry : groupedCalculationLoops.entrySet()) {

            Integer maxlh = Collections.max(entry.getValue(), LHSEQ_COMPARATOR).getLhSeq();
            String maxl = Collections.max(entry.getValue(), LSEQ_COMPARATOR).getlSeq();
            CalculationLoop first = entry.getValue().get(0);

            maxList.add(new Max(maxlh, maxl, first.getId(), first.getPolicyaid(), first.getGroupingId()));

        }

        logger.info("Max size -> " + maxList.size());

        Map<String, CalculationLoop> mappedCalculationLoops = calculationLoops.stream().collect(Collectors.toMap(calculationLoop ->
                "" + calculationLoop.getId() + calculationLoop.getPolicyaid() + calculationLoop.getGroupingId() + calculationLoop.getLhSeq() + calculationLoop. getlSeq(), calculationLoop -> calculationLoop));

        Map<String, Max> mappedMaxList = maxList.stream().collect(Collectors.toMap(max ->
                "" + max.getId() + max.getPolicyaid() + max.getGroupingId() + max.getMaxlh() + max. getMaxl(), max -> max));


        for (Map.Entry<String, CalculationLoop> entry : mappedCalculationLoops.entrySet()) {
            if (mappedMaxList.containsKey(entry.getKey())) {
                entry.getValue().setMaxSeq(true);
            }
        }
    }

    private static class LhSeqComparator implements Comparator<CalculationLoop> {

        @Override
        public int compare(CalculationLoop o1, CalculationLoop o2) {
            if (o1.getLhSeq() > o2.getLhSeq()) return 1;
            else if (o1.getLhSeq() < o2.getLhSeq()) return -1;
            else return 0;
        }
    }

    private static class LSeqComparator implements Comparator<CalculationLoop> {
        @Override
        public int compare(CalculationLoop o1, CalculationLoop o2) {
            return o1.getlSeq().compareTo(o2.getlSeq());
        }
    }

    private void fillCombinationWithLimit(List<Combination> combinations, Combination source, List<QueryLimit> matchedLimits) {

        for (QueryLimit limit : matchedLimits) {
            Combination combination = new Combination(source);

            if (combination.getId() == null) {
                combination.setId(limit.getId());
            }

            if (combination.getPolicyaid() == null) {
                combination.setPolicyaid(limit.getPolicyaid());
            }

            if (combination.getGroupingId() == null) {
                combination.setGroupingId(limit.getGroupingId());
            }

            if (combination.getCoverageId() == null) {
                combination.setCoverageId(limit.getCoverageId());
            }

            combination.setLimit(limit.getLimit());
            combination.setExcess(limit.getExcess());
            combination.setDeductible(limit.getDeductible());
            combination.setNewLimit(limit.getNewLimit());
            combination.setNewExcess(limit.getNewExcess());
            combination.setNewDeductible(limit.getNewDeductible());
            combination.setCsl(limit.getCsl());
            combination.setCslSeq(limit.getCslSeq());
            combination.setLimitType(limit.getLimitType());
            combination.setLhSeq(limit.getLhSeq());
            combination.setlSeq(limit.getlSeq());
            combination.setLimitHeaderName(limit.getLimitHeaderName());
            combination.setRiLimit(limit.getRiLimit());
            combination.setAfterExcess(limit.getAfterExcess());
            combination.setMaxSeq(limit.getMaxSeq());
            combination.setFinalExposure(limit.getFinalExposure());

            combinations.add(combination);
        }

    }

    private SQLQuery<Limit> createLimitSQLQuery() {
        return new SQLQuery<>(Limit.class, "");
    }

    private SQLQuery<LimitLocation> createLimitLocationSQLQuery() {
        return new SQLQuery<>(LimitLocation.class, "");
    }

    private SQLQuery<LimitCoverage> createLimitCoverageSQLQuery() {
        return new SQLQuery<>(LimitCoverage.class, "");
    }

    private SQLQuery<InsurancePolicyItemCoverageValue> createInsurancePolicyCoverageValueSQLQuery() {
        return new SQLQuery<>(InsurancePolicyItemCoverageValue.class, "");
    }

    private SQLQuery<InsurancePolicyItem> createInsurancePolicyItemSQLQuery() {
        return new SQLQuery<>(InsurancePolicyItem.class, "");
    }

    private SQLQuery<InsurancePolicy> createInsurancePolicySQLQuery() {
        return new SQLQuery<>(InsurancePolicy.class, "");
    }
}
