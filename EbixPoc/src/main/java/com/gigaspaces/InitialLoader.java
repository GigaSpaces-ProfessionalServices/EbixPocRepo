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
import java.math.RoundingMode;
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
    private final int objCount = 1001;

    @Autowired
    private GigaSpace gigaspace;

    public void init() {
        InsurancePolicyItem[] insurancePolicyItems = gigaspace.readMultiple(createInsurancePolicyItemSQLQuery());
        logger.info("GROUPING BY COUNTRY,STATE");

        Set<GroupingAsset> groupingAssets = Arrays.stream(insurancePolicyItems).map(insurancePolicyItem ->
                new GroupingAsset(insurancePolicyItem.getCountry(), insurancePolicyItem.getState())).collect(Collectors.toSet());

        int id = 0;
        for (GroupingAsset groupingAsset : groupingAssets) {
            groupingAsset.setId(++id);
        }

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
            combination.setNewExposure(isBigDecimalNull(coverage.getNewExposure()) ? BigDecimal.ZERO : coverage.getNewExposure());

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


        updateExposureAndLimit(calculationLoops.stream().filter(calculationLoop -> calculationLoop.getNewExposure() != null).collect(Collectors.toList()), false);

        calculationLoops.forEach(calculationLoop -> {
            if (calculationLoop.getMaxSeq()) calculationLoop.setFinalExposure(calculationLoop.getNewExposure());
        });


//        ------------------------------ -- RI


        Map<String, CalculationLoop> mappedCalculationLoopsRI = calculationLoopsRI.stream().collect(Collectors.toMap(calculationLoop ->
                "" + calculationLoop.getId() + calculationLoop.getPolicyaid() + calculationLoop.getGroupingId() + calculationLoop.getCoverageId(), calculationLoop -> calculationLoop));

        Map<String, BigDecimal> mappedMaxSeqCalculationLoops = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getFinalExposure() != null).filter(CalculationLoop::getMaxSeq).collect(Collectors.toMap(calculationLoop ->
                "" + calculationLoop.getId() + calculationLoop.getPolicyaid() + calculationLoop.getGroupingId() + calculationLoop.getCoverageId(), CalculationLoop::getFinalExposure));

        for (Map.Entry<String, CalculationLoop> entry : mappedCalculationLoopsRI.entrySet()) {

            if (mappedMaxSeqCalculationLoops.containsKey(entry.getKey())) {
                BigDecimal finalExposure = mappedMaxSeqCalculationLoops.get(entry.getKey());
                CalculationLoop update = entry.getValue();
                update.setTIV(finalExposure);
                update.setNewExposure(finalExposure);
                update.setSumTIVForExcess(finalExposure);
                update.setSumTIVForLimit(finalExposure);
                update.setSumTIVForExcessCSL(finalExposure);
                update.setSumTIVForLimitCSL(finalExposure);
            }
        }

        Set<BigDecimal> getNewExposureAfter = calculationLoopsRI.stream().map(CalculationLoop::getNewExposure).collect(Collectors.toSet());

//        -------------------------------------------------------


        updateExposureAndLimit(calculationLoopsRI.stream().filter(calculationLoop -> calculationLoop.getNewExposure() != null).collect(Collectors.toList()), true);

        // final select
                //   Gross
        Map<String, CalculationLoop> a = calculationLoops.stream().filter(CalculationLoop::getMaxSeq)
                .collect(Collectors.toMap(calculationLoop -> "" + calculationLoop.getId() + calculationLoop.getPolicyaid()
                        + calculationLoop.getGroupingId() + calculationLoop.getCoverageId(), calculationLoop -> calculationLoop));

                //   Net
        Map<String, BigDecimal> b = calculationLoopsRI.stream().filter(CalculationLoop::getMaxSeq)
                .collect(Collectors.toMap(calculationLoop -> "" + calculationLoop.getId() + calculationLoop.getPolicyaid()
                        + calculationLoop.getGroupingId()+ calculationLoop.getCoverageId(), CalculationLoop::getNewExposure));



        List<FinalResult> result = new ArrayList<>();
        for (Map.Entry<String, CalculationLoop> entry : a.entrySet()) {
            FinalResult obj = new FinalResult();
            CalculationLoop calculationLoop = entry.getValue();
            obj.setId(calculationLoop.getId());
            obj.setPolicyaid(calculationLoop.getPolicyaid());
            obj.setGroupingId(calculationLoop.getGroupingId());
            obj.setCountry(calculationLoop.getCountry());
            obj.setState(calculationLoop.getState());
            obj.setCoverageId(calculationLoop.getCoverageId());
            obj.setGross(calculationLoop.getFinalExposure());

            if (b.containsKey(entry.getKey())) {
                obj.setNet(b.get(entry.getKey()));
            }
            result.add(obj);
        }

        logger.info("result size -> " + result.size());
        gigaspace.writeMultiple(result.toArray());


    }

    private void updateExposureAndLimit(Collection<CalculationLoop> calculationLoops, boolean ri) {
        // cursor C1 simulation
        Set<Integer> c1cursor = calculationLoops.stream().map(CalculationLoop::getLhSeq).sorted().collect(Collectors.toSet());
        Iterator<Integer> c1cursorIterator = c1cursor.iterator();


        while (c1cursorIterator.hasNext()) {
            Integer limitHeader = c1cursorIterator.next();

            // cursor B1 simulation
            Set<String> b1cursor = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader)).map(CalculationLoop::getlSeq).sorted().collect(Collectors.toSet());

            Iterator<String> b1cursorIterator = b1cursor.iterator();

            while (b1cursorIterator.hasNext()) {
                Integer limit = getAnIntFromString(b1cursorIterator.next());

                Map<String, BigDecimal> newExposureSumsCSL0 = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(calculationLoop -> !calculationLoop.getCsl())
                        .collect(Collectors.toMap(calculationLoop -> "" + calculationLoop.getGroupingId() + calculationLoop.getPolicyaid() + calculationLoop.getCoverageId(),
                                CalculationLoop::getNewExposure, BigDecimal::add));

                Map<String, List<CalculationLoop>> mappedForSumTIVForCSL0 = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(calculationLoop -> !calculationLoop.getCsl())
                        .collect(Collectors.groupingBy(calculationLoop -> "" + calculationLoop.getGroupingId() + calculationLoop.getPolicyaid() + calculationLoop.getCoverageId()));

                for (Map.Entry<String, List<CalculationLoop>> entry : mappedForSumTIVForCSL0.entrySet()) {
                    if (newExposureSumsCSL0.containsKey(entry.getKey())) {
                        entry.getValue().forEach(calculationLoop -> calculationLoop.setSumTIVForExcess(newExposureSumsCSL0.get(entry.getKey())));
                    }
                }



                Map<String, BigDecimal> newExposureSumsCSL1 = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(CalculationLoop::getCsl)
                        .collect(Collectors.toMap(calculationLoop -> "" + calculationLoop.getGroupingId() + calculationLoop.getPolicyaid(),
                                CalculationLoop::getNewExposure, BigDecimal::add));

                Map<String, List<CalculationLoop>> mappedForSumTIVForCSL1 = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(CalculationLoop::getCsl)
                        .collect(Collectors.groupingBy(calculationLoop -> "" + calculationLoop.getGroupingId() + calculationLoop.getPolicyaid()));

                for (Map.Entry<String, List<CalculationLoop>> entry : mappedForSumTIVForCSL1.entrySet()) {
                    if (newExposureSumsCSL1.containsKey(entry.getKey())) {
                        entry.getValue().forEach(calculationLoop -> calculationLoop.setSumTIVForExcess(newExposureSumsCSL1.get(entry.getKey())));
                    }
                }


                Map<String, BigDecimal> newExposureSumsExcessCSL = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(CalculationLoop::getCsl)
                        .collect(Collectors.toMap(calculationLoop -> "" + calculationLoop.getGroupingId() + calculationLoop.getPolicyaid() + calculationLoop.getId(),
                                CalculationLoop::getNewExposure, BigDecimal::add));

                Map<String, List<CalculationLoop>> mappedForSumTIVForExcessCSL = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(CalculationLoop::getCsl)
                        .collect(Collectors.groupingBy(calculationLoop -> "" + calculationLoop.getGroupingId() + calculationLoop.getPolicyaid() + calculationLoop.getId()));

                for (Map.Entry<String, List<CalculationLoop>> entry : mappedForSumTIVForExcessCSL.entrySet()) {
                    if (newExposureSumsExcessCSL.containsKey(entry.getKey())) {
                        entry.getValue().forEach(calculationLoop -> calculationLoop.setSumTIVForExcessCSL(newExposureSumsExcessCSL.get(entry.getKey())));
                    }
                }

                // calculationLoops updated

                calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).collect(Collectors.toList())
                        .forEach(calculationLoop -> {
                    if (calculationLoop.getCsl()) {
                        if (isBigDecimalNull(calculationLoop.getSumTIVForExcessCSL()) || isBigDecimalNull(calculationLoop.getSumTIVForExcess())) {
                            calculationLoop.setApportionExcess(BigDecimal.ZERO);
                        } else {
                            calculationLoop.setApportionExcess(calculationLoop.getSumTIVForExcessCSL().divide(calculationLoop.getSumTIVForExcess(), 8, RoundingMode.HALF_UP));
                        }
                    } else {
                        if (isBigDecimalNull(calculationLoop.getNewExposure()) || isBigDecimalNull(calculationLoop.getSumTIVForExcess())) {
                            calculationLoop.setApportionExcess(BigDecimal.ZERO);
                        } else {
                            calculationLoop.setApportionExcess(calculationLoop.getNewExposure().divide(calculationLoop.getSumTIVForExcess(), 8, RoundingMode.HALF_UP));
                        }
                    }
                });
                // -- calculated Excess Apportionment

                calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).collect(Collectors.toList())
                        .forEach(calculationLoop -> calculationLoop.setNewExcess(calculationLoop.getExcess().multiply(calculationLoop.getApportionExcess())));

                // -- apply excess for non csl
                calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(calculationLoop -> !calculationLoop.getCsl())
                        .collect(Collectors.toList()).forEach(calculationLoop -> {
                            calculationLoop.setAfterExcess(calculationLoop.getNewExposure().subtract(calculationLoop.getNewExcess()).compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : calculationLoop.getNewExposure().subtract(calculationLoop.getNewExcess()));
                });


                if (!ri) {
                    updateCalcAuditForExcess(calculationLoops, limitHeader, limit);
                }


                // --SUM TIV FOR APPORTIONMENT OF LIMIT

                Map<String, BigDecimal> sumTIVForLimitMapCSL0 = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(calculationLoop -> !calculationLoop.getCsl())
                        .collect(Collectors.toMap(calculationLoop -> "" + calculationLoop.getGroupingId() + calculationLoop.getPolicyaid(),
                                CalculationLoop::getAfterExcess, BigDecimal::add));

                Map<String, List<CalculationLoop>> mappedForSumTIVForLimitCSL0Map = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(calculationLoop -> !calculationLoop.getCsl())
                        .collect(Collectors.groupingBy(calculationLoop -> "" + calculationLoop.getGroupingId() + calculationLoop.getPolicyaid()));

                for (Map.Entry<String, List<CalculationLoop>> entry : mappedForSumTIVForLimitCSL0Map.entrySet()) {
                    if (sumTIVForLimitMapCSL0.containsKey(entry.getKey())) {
                        entry.getValue().forEach(calculationLoop -> calculationLoop.setSumTIVForLimit(sumTIVForLimitMapCSL0.get(entry.getKey())));
                    }
                }




                Map<String, BigDecimal> sumTIVForLimitCSL1Map = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(CalculationLoop::getCsl)
                        .collect(Collectors.toMap(calculationLoop -> "" + calculationLoop.getGroupingId() + calculationLoop.getPolicyaid(),
                                CalculationLoop::getAfterExcess, BigDecimal::add));

                Map<String, List<CalculationLoop>> mappedForSumTIVForLimitCSL1Map = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(CalculationLoop::getCsl)
                        .collect(Collectors.groupingBy(calculationLoop -> "" + calculationLoop.getGroupingId() + calculationLoop.getPolicyaid()));

                for (Map.Entry<String, List<CalculationLoop>> entry : mappedForSumTIVForLimitCSL1Map.entrySet()) {
                    if (sumTIVForLimitCSL1Map.containsKey(entry.getKey())) {
                        entry.getValue().forEach(calculationLoop -> calculationLoop.setSumTIVForLimit(sumTIVForLimitCSL1Map.get(entry.getKey())));
                    }
                }



                Map<String, BigDecimal> sumTIVForLimitCSLMap = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(CalculationLoop::getCsl)
                        .collect(Collectors.toMap(calculationLoop -> "" + calculationLoop.getGroupingId() + calculationLoop.getPolicyaid() + calculationLoop.getId(),
                                CalculationLoop::getAfterExcess, BigDecimal::add));

                Map<String, List<CalculationLoop>> mappedForSumTIVForLimitCSLMap = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(CalculationLoop::getCsl)
                        .collect(Collectors.groupingBy(calculationLoop -> "" + calculationLoop.getGroupingId() + calculationLoop.getPolicyaid() + calculationLoop.getId()));

                for (Map.Entry<String, List<CalculationLoop>> entry : mappedForSumTIVForLimitCSLMap.entrySet()) {
                    if (sumTIVForLimitCSLMap.containsKey(entry.getKey())) {
                        entry.getValue().forEach(calculationLoop -> calculationLoop.setSumTIVForLimitCSL(sumTIVForLimitCSLMap.get(entry.getKey())));
                    }
                }


                // --Calculate Limit Apportionment
                calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).collect(Collectors.toList())
                        .forEach(calculationLoop -> {
                            if (calculationLoop.getCsl()) {
                                if (isBigDecimalNull(calculationLoop.getSumTIVForLimitCSL()) || isBigDecimalNull(calculationLoop.getSumTIVForLimit()) ) {
                                    calculationLoop.setApportionLimit(BigDecimal.ZERO);
                                } else {
                                    calculationLoop.setApportionLimit(calculationLoop.getSumTIVForLimitCSL().divide(calculationLoop.getSumTIVForLimit(), 8, RoundingMode.HALF_UP));
                                }
                            } else {
                                if (isBigDecimalNull(calculationLoop.getAfterExcess()) || isBigDecimalNull(calculationLoop.getSumTIVForLimit()) ) {
                                    calculationLoop.setApportionLimit(BigDecimal.ZERO);
                                } else {
                                    calculationLoop.setApportionLimit(calculationLoop.getAfterExcess().divide(calculationLoop.getSumTIVForLimit(), 8, RoundingMode.HALF_UP));
                                }
                            }
                        });



                // -- Apportion Limit

                calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).collect(Collectors.toList())
                        .forEach(calculationLoop -> calculationLoop.setNewLimit(calculationLoop.getLimit().multiply(calculationLoop.getApportionLimit())));



                // apply limit

                if (ri) {
                    calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                            .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(calculationLoop -> !calculationLoop.getCsl())
                            .collect(Collectors.toList()).forEach(calculationLoop ->
                        calculationLoop.setNewExposure(calculationLoop.getAfterExcess().subtract(calculationLoop.getNewLimit()).compareTo(BigDecimal.ZERO) >= 0 ? calculationLoop.getNewExposure().subtract(calculationLoop.getNewLimit()) : calculationLoop.getNewExposure().subtract(calculationLoop.getAfterExcess())));
                } else {
                    calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                            .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(calculationLoop -> !calculationLoop.getCsl())
                            .collect(Collectors.toList()).forEach(calculationLoop ->
                        calculationLoop.setNewExposure(calculationLoop.getAfterExcess().subtract(calculationLoop.getNewLimit()).compareTo(BigDecimal.ZERO) >= 0 ? calculationLoop.getNewLimit() : calculationLoop.getAfterExcess()));
                }


                if (!ri) {
                    updateCalcAuditForLimit(calculationLoops, limitHeader, limit);
                }

            //-- update new exposure for all future limit sequencing of item in aggregation grouping

                Map<String, BigDecimal> newExposures = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                        .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).collect(Collectors.toMap(calculationLoop -> "" + calculationLoop.getId() + calculationLoop.getPolicyaid() + calculationLoop.getGroupingId() + calculationLoop.getCoverageId(),
                                CalculationLoop::getNewExposure));

                Map<String, List<CalculationLoop>> mappedForNewExposures = calculationLoops.stream().filter(calculationLoop -> (calculationLoop.getLhSeq() > limitHeader)
                        ||  (calculationLoop.getLhSeq().equals(limitHeader) && getAnIntFromString(calculationLoop.getlSeq()) >= limit))
                        .collect(Collectors.groupingBy(calculationLoop -> "" + calculationLoop.getId() + calculationLoop.getPolicyaid() + calculationLoop.getGroupingId() + calculationLoop.getCoverageId()));


                for (Map.Entry<String, List<CalculationLoop>> entry : mappedForNewExposures.entrySet()) {
                    if (newExposures.containsKey(entry.getKey())) {
                        entry.getValue().forEach(calculationLoop -> calculationLoop.setNewExposure(newExposures.get(entry.getKey())));
                    }
                }



                Map<String, List<CalculationLoop>> mappedForTIV = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq() > limitHeader)
                        .collect(Collectors.groupingBy(calculationLoop -> "" + calculationLoop.getId() + calculationLoop.getPolicyaid() + calculationLoop.getGroupingId() + calculationLoop.getCoverageId()));

                for (Map.Entry<String, List<CalculationLoop>> entry : mappedForTIV.entrySet()) {
                    if (newExposures.containsKey(entry.getKey())) {
                        entry.getValue().forEach(calculationLoop -> calculationLoop.setTIV(newExposures.get(entry.getKey())));
                    }
                }


            }
        }
    }

    private int getAnIntFromString(String input) {
        return Integer.parseInt(input.trim());
    }

    private void updateCalcAuditForLimit(Collection<CalculationLoop> calculationLoops, Integer limitHeader, Integer limit) {
        calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(calculationLoop -> !calculationLoop.getCsl())
                .collect(Collectors.toList()).forEach(calculationLoop -> {
                    calculationLoop.setCalcAudit(!calculationLoop.getExcessBreach()
                            ? calculationLoop.getCalcAudit() + " EXCESS HAS NOT BEEN REACHED: (TIV) " + calculationLoop.getNewExposure().toString() + " (EXCESS) " + calculationLoop.getNewExcess().toString()
                            : calculationLoop.getAfterExcess().subtract(calculationLoop.getNewLimit()).compareTo(BigDecimal.ZERO) >= 0
                            ? calculationLoop.getCalcAudit() + "LIMIT REACHED: (TIV AFTER EXCESS) " + calculationLoop.getAfterExcess().toString() + " (LIMIT) " + calculationLoop.getNewLimit().toString()
                            : calculationLoop.getCalcAudit() + " LIMIT NOT REACHED: (TIV AFTER EXCESS) " + calculationLoop.getAfterExcess().toString() + " (EXCESS) " + calculationLoop.getNewLimit().toString());

                    calculationLoop.setLimitBreach(calculationLoop.getExcessBreach() && (calculationLoop.getAfterExcess().subtract(calculationLoop.getNewLimit()).compareTo(BigDecimal.ZERO) >= 0));
                }
        );


        // cursor AA1 simulation
        Set<Integer> aa1cursor = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(CalculationLoop::getCsl)
                .map(CalculationLoop::getCslSeq).sorted().collect(Collectors.toSet());

        Iterator<Integer> aa1cursorIterator = aa1cursor.iterator();

        while (aa1cursorIterator.hasNext()) {
            Integer csl = aa1cursorIterator.next();


            calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                    .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit)
                    .filter(calculationLoop -> calculationLoop.getCslSeq().equals(csl)).filter(CalculationLoop::getCsl)
                    .collect(Collectors.toList()).forEach(calculationLoop ->
                    calculationLoop.setNewExposure(calculationLoop.getAfterExcess().subtract(calculationLoop.getNewLimit()).compareTo(BigDecimal.ZERO) >= 0 ? calculationLoop.getNewLimit() : calculationLoop.getAfterExcess())
            );

            calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                    .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit)
                    .filter(calculationLoop -> calculationLoop.getCslSeq().equals(csl)).filter(CalculationLoop::getCsl)
                    .collect(Collectors.toList()).forEach(calculationLoop -> {
                        calculationLoop.setCalcAudit(!calculationLoop.getExcessBreach()
                                ? calculationLoop.getCalcAudit() + " EXCESS HAS NOT BEEN REACHED: (TIV)" + calculationLoop.getNewExposure().toString() + " (EXCESS) " + calculationLoop.getNewExcess().toString()
                                : calculationLoop.getAfterExcess().subtract(calculationLoop.getNewLimit()).compareTo(BigDecimal.ZERO) >= 0
                                ? calculationLoop.getCalcAudit() + "LIMIT REACHED: (TIV AFTER EXCESS)" + calculationLoop.getAfterExcess().toString() + " (LIMIT) " + calculationLoop.getNewLimit().toString()
                                : calculationLoop.getCalcAudit() + "LIMIT NOT REACHED: (TIV AFTER EXCESS)" + calculationLoop.getAfterExcess().toString() + " (LIMIT) " + calculationLoop.getNewLimit().toString());

                        calculationLoop.setLimitBreach(calculationLoop.getExcessBreach() && (calculationLoop.getAfterExcess().subtract(calculationLoop.getNewLimit()).compareTo(BigDecimal.ZERO) >= 0));
                    }
            );


            Map<String, BigDecimal> limitUpdates = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                    .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit)
                    .filter(calculationLoop -> calculationLoop.getCslSeq().equals(csl)).filter(CalculationLoop::getCsl)
                    .collect(Collectors.toMap(calculationLoop -> "" + calculationLoop.getId() + calculationLoop.getPolicyaid() + calculationLoop.getGroupingId() + calculationLoop.getLhSeq() + calculationLoop.getlSeq(),
                            calculationLoop -> calculationLoop.getNewLimit().subtract(calculationLoop.getAfterExcess()).compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : calculationLoop.getNewLimit().subtract(calculationLoop.getAfterExcess())));

            Map<String, List<CalculationLoop>> mappedForLimitUpdates = calculationLoops.stream().filter(CalculationLoop::getCsl)
                    .filter(calculationLoop -> calculationLoop.getCslSeq() > csl)
                    .collect(Collectors.groupingBy(calculationLoop -> "" + calculationLoop.getId() + calculationLoop.getPolicyaid() + calculationLoop.getGroupingId() + calculationLoop.getLhSeq() + calculationLoop.getlSeq()));

            for (Map.Entry<String, List<CalculationLoop>> entry : mappedForLimitUpdates.entrySet()) {
                if (limitUpdates.containsKey(entry.getKey())) {
                    entry.getValue().forEach(calculationLoop -> calculationLoop.setNewLimit(limitUpdates.get(entry.getKey())));
                }
            }

        }
    }

    private void updateCalcAuditForExcess(Collection<CalculationLoop> calculationLoops, Integer limitHeader, Integer limit) {
        calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(calculationLoop -> !calculationLoop.getCsl())
                .collect(Collectors.toList()).forEach(calculationLoop -> {
                    calculationLoop.setCalcAudit(calculationLoop.getAfterExcess().equals(BigDecimal.ZERO)
                            ? calculationLoop.getCalcAudit() + " EXCESS HAS NOT BEEN REACHED: (TIV)" + calculationLoop.getNewExposure().toString() + " (EXCESS) " + calculationLoop.getNewExcess().toString()
                            : calculationLoop.getCalcAudit() + " EXCESS BREACHED: (TIV)" + calculationLoop.getNewExposure().toString() + " (EXCESS) " + calculationLoop.getNewExcess().toString());

                    calculationLoop.setExcessBreach(!calculationLoop.getAfterExcess().equals(BigDecimal.ZERO));
                }
        );

        // ----SUM TIV FOR APPORTIONMENT OF LIMIT


        // cursor A1 simulation
        Set<Integer> a1cursor = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit).filter(CalculationLoop::getCsl)
                .map(CalculationLoop::getCslSeq).sorted().collect(Collectors.toSet());

        Iterator<Integer> a1cursorIterator = a1cursor.iterator();

        while (a1cursorIterator.hasNext()) {
            Integer csl = a1cursorIterator.next();


            calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                    .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit)
                    .filter(calculationLoop -> calculationLoop.getCslSeq().equals(csl)).filter(CalculationLoop::getCsl)
                    .collect(Collectors.toList()).forEach(calculationLoop ->
                calculationLoop.setAfterExcess(calculationLoop.getNewExposure().subtract(calculationLoop.getNewExcess()).compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : calculationLoop.getNewExposure().subtract(calculationLoop.getNewExcess()))
            );

            calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                    .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit)
                    .filter(calculationLoop -> calculationLoop.getCslSeq().equals(csl)).filter(CalculationLoop::getCsl)
                    .collect(Collectors.toList()).forEach(calculationLoop -> {
                        calculationLoop.setCalcAudit(calculationLoop.getAfterExcess().equals(BigDecimal.ZERO)
                                ? calculationLoop.getCalcAudit() + " EXCESS HAS NOT BEEN REACHED: (TIV)" + calculationLoop.getNewExposure().toString() + " (EXCESS) " + calculationLoop.getNewExcess().toString()
                                : calculationLoop.getCalcAudit() + " EXCESS BREACHED: (TIV)" + calculationLoop.getNewExposure().toString() + " (EXCESS) " + calculationLoop.getNewExcess().toString());

                        calculationLoop.setExcessBreach(!calculationLoop.getAfterExcess().equals(BigDecimal.ZERO));
                    }
            );

            // -- Update Excess for other CSL sequences


            Map<String, BigDecimal> excessUpdates = calculationLoops.stream().filter(calculationLoop -> calculationLoop.getLhSeq().equals(limitHeader))
                    .filter(calculationLoop -> getAnIntFromString(calculationLoop.getlSeq()) == limit)
                    .filter(calculationLoop -> calculationLoop.getCslSeq().equals(csl)).filter(CalculationLoop::getCsl)
                    .collect(Collectors.toMap(calculationLoop -> "" + calculationLoop.getId() + calculationLoop.getPolicyaid() + calculationLoop.getGroupingId() + calculationLoop.getLhSeq() + calculationLoop.getlSeq(),
                            calculationLoop -> calculationLoop.getNewExposure().subtract(calculationLoop.getNewExcess()).compareTo(BigDecimal.ZERO) <= 0 ? calculationLoop.getNewExposure().subtract(calculationLoop.getNewExcess()) : BigDecimal.ZERO));

            Map<String, List<CalculationLoop>> mappedForExcessUpdates = calculationLoops.stream().filter(CalculationLoop::getCsl)
                    .filter(calculationLoop -> calculationLoop.getCslSeq() > csl)
                    .collect(Collectors.groupingBy(calculationLoop -> "" + calculationLoop.getId() + calculationLoop.getPolicyaid() + calculationLoop.getGroupingId() + calculationLoop.getLhSeq() + calculationLoop.getlSeq()));

            for (Map.Entry<String, List<CalculationLoop>> entry : mappedForExcessUpdates.entrySet()) {
                if (excessUpdates.containsKey(entry.getKey())) {
                    entry.getValue().forEach(calculationLoop -> {
                        calculationLoop.setNewExcess(excessUpdates.get(entry.getKey()));
                        calculationLoop.setAfterExcess(calculationLoop.getNewExposure());
                    });
                }
            }
        }
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

        Map<String, List<CalculationLoop>> mappedCalculationLoops = calculationLoops.stream().collect(Collectors.groupingBy(calculationLoop ->
                "" + calculationLoop.getId() + calculationLoop.getPolicyaid() + calculationLoop.getGroupingId() + calculationLoop.getLhSeq() + calculationLoop. getlSeq()));

        Map<String, Max> mappedMaxList = maxList.stream().collect(Collectors.toMap(max ->
                "" + max.getId() + max.getPolicyaid() + max.getGroupingId() + max.getMaxlh() + max. getMaxl(), max -> max));


        for (Map.Entry<String, List<CalculationLoop>> entry : mappedCalculationLoops.entrySet()) {
            if (mappedMaxList.containsKey(entry.getKey())) {
                entry.getValue().forEach(calculationLoop -> calculationLoop.setMaxSeq(true));
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
        List<Integer> ids = new ArrayList<>();
        for (int i = 1; i < objCount; i++) {
            ids.add(i);
        }
        logger.info("ids -> " + ids.size());

        // add limiter here to reduce result set
        return new SQLQuery<>(InsurancePolicy.class, "id IN (?) ")
                .setParameter(1, ids);
    }

    private boolean isBigDecimalNull(BigDecimal bigDecimal) {
        return bigDecimal == null || (bigDecimal.compareTo(BigDecimal.ZERO) == 0);
    }
}
