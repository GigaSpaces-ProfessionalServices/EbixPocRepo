package com.gigaspaces;

import com.gigaspaces.pojos.*;
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
