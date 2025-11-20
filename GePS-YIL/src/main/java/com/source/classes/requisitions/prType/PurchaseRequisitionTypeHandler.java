package com.source.classes.requisitions.prType;

import com.source.interfaces.requisitions.prCreate.IPrCreate;
import com.source.interfaces.requisitions.prType.IPrType;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class PurchaseRequisitionTypeHandler implements IPrType {
    private IPrCreate iPrCreate;
    Logger logger = LoggerUtil.getLogger(PurchaseRequisitionTypeHandler.class);
    ;

    private PurchaseRequisitionTypeHandler() {
    }

    //TODO Constructor
    public PurchaseRequisitionTypeHandler(IPrCreate iPrCreate) {
        this.iPrCreate = iPrCreate;
    }

    public int processRequisitionType(String type, String purchaseType) {
        int status = 0;
        try {
            iPrCreate.requesterLoginPRCreate(type, purchaseType);
            iPrCreate.createButton(type, purchaseType);
            iPrCreate.purchaseType(type, purchaseType);
            iPrCreate.title(type, purchaseType);
            iPrCreate.shipToYokogawa(type, purchaseType);
            if (type.equalsIgnoreCase("POC")) {
                List<String> wbsValues = iPrCreate.project(type, purchaseType);
                iPrCreate.wbs(wbsValues,type, purchaseType);
                if (purchaseType.equalsIgnoreCase("Catalog")) {
                    Map<String, String> rateContract = iPrCreate.vendor(type, purchaseType);
                    List<String> items = iPrCreate.rateContract(rateContract, type, purchaseType);
                    iPrCreate.shippingAddress(type, purchaseType);
                    iPrCreate.storageLocation(type, purchaseType);
                    iPrCreate.shippingMode(type, purchaseType);
                    iPrCreate.warrantyRequirements(type, purchaseType);
                    iPrCreate.checker(type, purchaseType);
                    iPrCreate.expectedPOIssue(type, purchaseType);
                    iPrCreate.expectedDelivery(type, purchaseType);
                    iPrCreate.buyerGroup(type, purchaseType);
                    iPrCreate.orderIntake(type, purchaseType);
                    iPrCreate.targetPrice(type, purchaseType);
                    iPrCreate.inspectionRequired(type, purchaseType);
                    iPrCreate.addLineRequisitionItemsCatalog(items,type, purchaseType);
                }
                else if (purchaseType.equalsIgnoreCase("NonCatalog")){
                    iPrCreate.incoterm(type, purchaseType);
                    iPrCreate.liquidatedDamages(type, purchaseType);
                    iPrCreate.warrantyRequirements(type,purchaseType);
                    iPrCreate.priceValidity(type, purchaseType);
                    iPrCreate.shippingAddress(type, purchaseType);
                    iPrCreate.storageLocation(type, purchaseType);
                    iPrCreate.shippingMode(type, purchaseType);
                    iPrCreate.quotationRequiredBy(type, purchaseType);
                    iPrCreate.expectedPOIssue(type, purchaseType);
                    iPrCreate.expectedDelivery(type, purchaseType);
                    iPrCreate.buyerGroup(type, purchaseType);
                    iPrCreate.inspectionRequired(type, purchaseType);
                    iPrCreate.checker(type, purchaseType);
                    iPrCreate.orderIntake(type, purchaseType);
                    iPrCreate.targetPrice(type, purchaseType);
                    iPrCreate.typeOfPurchase(type, purchaseType);
                    iPrCreate.addLineRequisitionItemsNonCatalog(type, purchaseType);
                }
                else if (purchaseType.equalsIgnoreCase("BOP2_5")) {
                    Map<String, String> rateContract = iPrCreate.vendor(type, purchaseType);
                    List<String> items = iPrCreate.rateContract(rateContract, type, purchaseType);
                    iPrCreate.shippingAddress(type, purchaseType);
                    iPrCreate.storageLocation(type, purchaseType);
                    iPrCreate.shippingMode(type, purchaseType);
                    iPrCreate.warrantyRequirements(type, purchaseType);
                    iPrCreate.checker(type, purchaseType);
                    iPrCreate.expectedPOIssue(type, purchaseType);
                    iPrCreate.expectedDelivery(type, purchaseType);
                    iPrCreate.buyerGroup(type, purchaseType);
                    iPrCreate.orderIntake(type, purchaseType);
                    iPrCreate.targetPrice(type, purchaseType);
                    iPrCreate.inspectionRequired(type, purchaseType);
                    //TODO : Modify rateContract api to BOP2_% api to get items
                    iPrCreate.addLineRequisitionItemsCatalog(items,type, purchaseType);
                }
                else if (purchaseType.equalsIgnoreCase("MH")) {
                    iPrCreate.incoterm(type, purchaseType);
                    iPrCreate.incotermLocation(type, purchaseType);
                    iPrCreate.shippingAddress(type, purchaseType);
                    iPrCreate.storageLocation(type, purchaseType);
                    //billingtype
                    //vendor
                    //paymentTerm
                    //currency
                    iPrCreate.buyerGroup(type, purchaseType);
                    iPrCreate.checker(type, purchaseType);
                    //fromDate
                    //toDate
                    //expectedMobilization
                    iPrCreate.typeOfPurchase(type, purchaseType);
                    iPrCreate.orderIntake(type, purchaseType);
                    iPrCreate.targetPrice(type, purchaseType);
                }
                iPrCreate.attachments(type, purchaseType);
                status = iPrCreate.prCreate(type, purchaseType);
            }
        } catch (Exception exception) {
            logger.error("Error in Purchase Type Function: {}", exception.getMessage());
        }
        return status;
    }
}