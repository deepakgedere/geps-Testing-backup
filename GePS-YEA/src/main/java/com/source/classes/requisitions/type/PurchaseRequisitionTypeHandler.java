package com.source.classes.requisitions.type;
import com.source.interfaces.requisitions.IPrCreate;
import com.source.interfaces.requisitions.IPrType;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Map;

public class PurchaseRequisitionTypeHandler implements IPrType {

    private IPrCreate iPrCreate;
    Logger logger;

    private PurchaseRequisitionTypeHandler(){
    }

//TODO Constructor
    public PurchaseRequisitionTypeHandler(IPrCreate iPrCreate){
        this.iPrCreate = iPrCreate;
        this.logger = LoggerUtil.getLogger(PurchaseRequisitionTypeHandler.class);
    }

    public int processRequisitionType(String type, String purchaseType) {
        int status = 0;
        try {
            iPrCreate.requesterLoginPRCreate();
            iPrCreate.createButton();
            iPrCreate.purchaseType(type, purchaseType);
            iPrCreate.title(type, purchaseType);
            iPrCreate.shipToYokogawa();

            if(type.equalsIgnoreCase("Sales")){
                iPrCreate.company();
                iPrCreate.departmentPic();
                iPrCreate.buyerManager();
                iPrCreate.salesReferenceId();
            } else if(type.equalsIgnoreCase("POC")) {
                List<String> getWbsJson = iPrCreate.project();
                iPrCreate.wbs(getWbsJson);
            } else {
                List<String> serviceOrders = iPrCreate.salesOrder();
                iPrCreate.departmentPic();
                iPrCreate.serviceOrder(serviceOrders);
            }

            switch (purchaseType.toLowerCase()) {
                case "catalog":
                    Map<String, String> rateContractArray = iPrCreate.vendor();
                    List<String> rateContractItems = iPrCreate.rateContract(rateContractArray);
                    iPrCreate.shippingAddress();
                    iPrCreate.shippingMode(purchaseType);
                    iPrCreate.expectedPOIssue(purchaseType);
                    iPrCreate.expectedDelivery(purchaseType);
                    if(type.equalsIgnoreCase("SD")){
                        iPrCreate.billableToCustomer();
                        iPrCreate.caseMarking();
                        iPrCreate.messageToSourcing();
                        iPrCreate.buyerManager();
                    }
                    if(type.equalsIgnoreCase("POC")) {
                        iPrCreate.buyerManager();
                        iPrCreate.projectManager();
                    }
                    iPrCreate.inspectionRequired(type, purchaseType);
                    if(type.equalsIgnoreCase("POC")) {
                        iPrCreate.tcasCompliance();
                    }
                    iPrCreate.addLineRequisitionItemsCatalog(rateContractItems);
                    break;
                case "noncatalog":
                    iPrCreate.incoterm();
                    iPrCreate.shippingAddress();
                    iPrCreate.shippingMode(purchaseType);
                    if(type.equalsIgnoreCase("SD")){
                        iPrCreate.billableToCustomer();
                        iPrCreate.caseMarking();
                        iPrCreate.messageToSourcing();
                        iPrCreate.buyerManager();
                    }
                    iPrCreate.quotationRequiredBy();
                    iPrCreate.expectedPOIssue(purchaseType);
                    iPrCreate.expectedDelivery(purchaseType);
                    if(type.equalsIgnoreCase("POC")) {
                        iPrCreate.buyerManager();
                        iPrCreate.projectManager();
                    }
                    iPrCreate.rohsCompliance();
                    iPrCreate.inspectionRequired(type, purchaseType);
                    iPrCreate.oiAndTpCurrency();
                    iPrCreate.orderIntake(type);
                    iPrCreate.targetPrice(type, purchaseType);
                    iPrCreate.warrantyRequirements(type);
                    iPrCreate.priceValidity(type);
                    if(type.equalsIgnoreCase("POC")) {
                        iPrCreate.liquidatedDamages();
                        iPrCreate.tcasCompliance();
                    }
                    iPrCreate.addLineRequisitionItemsNonCatalog();
                    break;
                default:
                    System.out.println("__ Enter Proper Purchase Type __");
                    break;
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