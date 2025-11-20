package com.source.classes.requisitions.type;

import com.source.interfaces.login.ILogin;
import com.source.interfaces.requisitions.IPrCreate;
import com.source.interfaces.requisitions.IPrType;
import com.utils.LoggerUtil;
import com.utils.rpa.salesordersync.PR_List_Flow;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class PurchaseRequisitionTypeHandler implements IPrType {

    PR_List_Flow prListFlow;
    private IPrCreate iPrCreate;
    Logger logger;

    private PurchaseRequisitionTypeHandler() {
    }

    //TODO Constructor
    public PurchaseRequisitionTypeHandler(IPrCreate iPrCreate, PR_List_Flow prListFlow) {
        this.iPrCreate = iPrCreate;
        this.prListFlow = prListFlow;
        this.logger = LoggerUtil.getLogger(PurchaseRequisitionTypeHandler.class);
    }

    public int processRequisitionType(String type, String purchaseType) {
        int status = 0;
        int soNumber = 0;
        String salesReferenceId = "";
        try {
            if(purchaseType.equalsIgnoreCase("CATALOG BOP2/BOP5") && type.equalsIgnoreCase("SD")){
                iPrCreate.adminLogin();
                soNumber = prListFlow.syncSO();
                prListFlow.updateSalesreferenceId(soNumber);
                iPrCreate.adminLogout();
            }

            iPrCreate.requesterLoginPRCreate();
            iPrCreate.createButton();
            iPrCreate.purchaseType(type, purchaseType);

            switch (purchaseType.toUpperCase()) {
                case "CATALOG" -> {
                    iPrCreate.title(type, purchaseType);
                    iPrCreate.shipToYokogawa();
                    if (type.equalsIgnoreCase("Sales")) {
                        iPrCreate.company();
                        iPrCreate.departmentPic(type);
                        iPrCreate.salesReferenceId(purchaseType, type);
                    } else if (type.equalsIgnoreCase("PS")) {
                        List<String> getWbsJson = iPrCreate.project();
                        iPrCreate.wbs(getWbsJson);
                    } else if (type.equalsIgnoreCase("SD")) {
                        List<String> serviceOrders = iPrCreate.salesOrder(purchaseType, soNumber, type);
                        iPrCreate.departmentPic(type);
                        iPrCreate.serviceOrder(serviceOrders);
                        iPrCreate.svoItemNumber();
                        iPrCreate.salesReferenceId(purchaseType,type);
                    }
                    Map<String, String> rateContractArray = iPrCreate.vendor(type, purchaseType);
                    List<String> rateContractItems = iPrCreate.rateContract(rateContractArray);
                    iPrCreate.shippingAddress();
                    if(type.equalsIgnoreCase("SD")) {
                        iPrCreate.checker(type, purchaseType);
                    }
                    iPrCreate.expectedPOIssue(type, purchaseType);
                    iPrCreate.expectedDelivery(type, purchaseType);
                    if (type.equalsIgnoreCase("SD")) {
                        iPrCreate.billableToCustomer();
                        iPrCreate.caseMarking();
                        iPrCreate.messageToSourcing();
                    }
                    if (type.equalsIgnoreCase("PS")) {
                        iPrCreate.checker(type, purchaseType);
                    }
                    iPrCreate.inspectionRequired(type, purchaseType);
                    if (type.equalsIgnoreCase("PS")) {
                        iPrCreate.tcasCompliance();
                    }
                    iPrCreate.addLineRequisitionItemsCatalog(rateContractItems);
                }
                case "CATALOG BOP2/BOP5" -> {
                    iPrCreate.catalogTypeHandler(type, purchaseType);
                    iPrCreate.title(type, purchaseType);
                    if (type.equalsIgnoreCase("PS")) {
                        iPrCreate.shipToYokogawa();
                        List<String> getWbsJson = iPrCreate.project();
                        iPrCreate.wbs(getWbsJson);
                    }
                    if (type.equalsIgnoreCase("SD")) {
                        List<String> serviceOrders = iPrCreate.salesOrder(purchaseType, soNumber, type);
                        iPrCreate.salesReferenceId(purchaseType,type);
                        iPrCreate.departmentPic(type);
                        iPrCreate.purchaseGroup();
                        iPrCreate.poType();
                    }
                    if (type.equalsIgnoreCase("PS")) {
                        Map<String, String> rateContractArray = iPrCreate.vendor(type, purchaseType);
                        iPrCreate.shippingAddress();
                    }
                    iPrCreate.shippingMode(type, purchaseType);
                    if (type.equalsIgnoreCase("SD")) {
                        iPrCreate.caseMarking();
                        iPrCreate.messageToSourcing();
                        iPrCreate.billableToCustomer();
                    }
                    iPrCreate.expectedPOIssue(type, purchaseType);
                    if (type.equalsIgnoreCase("PS")) {
                        iPrCreate.expectedDelivery(type, purchaseType);
                        iPrCreate.oiAndTpCurrency();
                    }
                    iPrCreate.checker(type, purchaseType);
                    iPrCreate.inspectionRequired(type, purchaseType);
                    if (type.equalsIgnoreCase("PS")) {
                        iPrCreate.tcasCompliance();
                    }
                    iPrCreate.addItemsForBOP2BOP5(type);
                }
                case "NONCATALOG" -> {
                    iPrCreate.title(type, purchaseType);
                    iPrCreate.shipToYokogawa();
                    if (type.equalsIgnoreCase("Sales")) {
                        iPrCreate.company();
                        iPrCreate.departmentPic(type);
                        iPrCreate.salesReferenceId(purchaseType,type);
                    } else if (type.equalsIgnoreCase("PS")) {
                        List<String> getWbsJson = iPrCreate.project();
                        iPrCreate.incoterm();
                        iPrCreate.wbs(getWbsJson);
                    } else if (type.equalsIgnoreCase("SD")) {
                        List<String> serviceOrders = iPrCreate.salesOrder(purchaseType, soNumber, type);
                        iPrCreate.departmentPic(type);
                        iPrCreate.serviceOrder(serviceOrders);
                        iPrCreate.svoItemNumber();
                        iPrCreate.salesReferenceId(purchaseType,type);
                    }
                    if(type.equalsIgnoreCase("Sales")) {
                        iPrCreate.incoterm();
                        iPrCreate.liquidatedDamages();
                        iPrCreate.warrantyRequirements(type);
                        iPrCreate.priceValidity(type);
                    }
                    iPrCreate.shippingAddress();
                    if (type.equalsIgnoreCase("SD")) {
                        iPrCreate.incoterm();
                        iPrCreate.billableToCustomer();
                        iPrCreate.caseMarking();
                        iPrCreate.messageToSourcing();
                    }
                    iPrCreate.quotationRequiredBy();
                    iPrCreate.expectedPOIssue(type, purchaseType);
                    iPrCreate.expectedDelivery(type, purchaseType);
                    if (type.equalsIgnoreCase("PS") || type.equalsIgnoreCase("SD")) {
                        iPrCreate.checker(type, purchaseType);
                    }
                    iPrCreate.rohsCompliance();
                    iPrCreate.inspectionRequired(type, purchaseType);
                    iPrCreate.orderIntake(type);
                    iPrCreate.targetPrice(type, purchaseType);
                    if (type.equalsIgnoreCase("PS")) {
                        iPrCreate.tcasCompliance();
                    }
                    iPrCreate.addLineRequisitionItemsNonCatalog();
                }
                case "PUNCHOUT" -> {
                    iPrCreate.punchoutCreate();
                    iPrCreate.title(type, purchaseType);
                    iPrCreate.shipToYokogawa();

                    if (type.equalsIgnoreCase("Sales")) {
                        iPrCreate.company();
                        iPrCreate.departmentPic(type);
                        iPrCreate.salesReferenceId(purchaseType,type);
                    } else if (type.equalsIgnoreCase("PS")) {
                        List<String> getWbsJson = iPrCreate.project();
                        iPrCreate.wbs(getWbsJson);
                    } else {
                        List<String> serviceOrders = iPrCreate.salesOrder(purchaseType, soNumber, type);
                        iPrCreate.salesReferenceId(purchaseType,type);
                        iPrCreate.departmentPic(type);
                        iPrCreate.serviceOrder(serviceOrders);
                    }

                    iPrCreate.expectedPOIssue(type, purchaseType);
                    iPrCreate.expectedDelivery(type, purchaseType);
                    iPrCreate.inspectionRequired(type, purchaseType);
                    if (type.equalsIgnoreCase("SD")) {
                        iPrCreate.svoItemNumber();
                        iPrCreate.billableToCustomer();
                        iPrCreate.caseMarking();
                        iPrCreate.messageToSourcing();
                        iPrCreate.checker(type, purchaseType);
                    }
                    if (type.equalsIgnoreCase("PS")) {
                        iPrCreate.checker(type, purchaseType);
                        iPrCreate.tcasCompliance();
                    }
                    iPrCreate.editItemsForPunchout();
                }
            }

            iPrCreate.notes();
            iPrCreate.attachments();
            status = iPrCreate.prCreate(type, purchaseType);
        } catch (Exception exception) {
            logger.error("Error in Purchase Type Function: {}", exception.getMessage());
        }
        return status;
    }
}