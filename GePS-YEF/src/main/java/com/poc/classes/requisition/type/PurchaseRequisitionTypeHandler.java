package com.poc.classes.requisition.type;
import com.poc.interfaces.requisitions.IPrCreate;
import com.poc.interfaces.requisitions.IPrType;
import java.util.Properties;

public class PurchaseRequisitionTypeHandler implements IPrType {

    private IPrCreate iPrCreate;
    private Properties properties;

    private PurchaseRequisitionTypeHandler(){
    }

//TODO Constructor
    public PurchaseRequisitionTypeHandler(IPrCreate iPrCreate, Properties properties){
        this.iPrCreate = iPrCreate;
        this.properties = properties;
    }

    public void processRequisitionType() {
        try {
            String prType = properties.getProperty("purchaseType").toLowerCase();
            switch (prType) {
                case "catalog":
                    iPrCreate.requesterLoginPRCreate();
                    iPrCreate.createButton();
                    iPrCreate.purchaseType();
                    iPrCreate.title();
                    iPrCreate.shipToYokogawa();
                    iPrCreate.project();
                    iPrCreate.wbs();
                    iPrCreate.vendor();
                    iPrCreate.rateContract();
                    iPrCreate.shippingAddress();
                    iPrCreate.shippingMode();
                    iPrCreate.expectedPOIssue();
                    iPrCreate.expectedDelivery();
                    iPrCreate.orderIntake();
                    iPrCreate.inspectionRequired();
                    iPrCreate.addLineRequisitionItems();
                    iPrCreate.notes();
                    iPrCreate.attachments();
                    iPrCreate.prCreate();
                    break;
                case "noncatalog":
                    iPrCreate.requesterLoginPRCreate();
                    iPrCreate.createButton();
                    iPrCreate.purchaseType();
                    iPrCreate.title();
                    iPrCreate.shipToYokogawa();
                    iPrCreate.project();
                    iPrCreate.wbs();
                    iPrCreate.incoterm();
                    iPrCreate.shippingAddress();
                    iPrCreate.shippingMode();
                    iPrCreate.quotationRequiredBy();
                    iPrCreate.expectedPOIssue();
                    iPrCreate.expectedDelivery();
                    iPrCreate.rohsCompliance();
                    iPrCreate.inspectionRequired();
                    iPrCreate.oiAndTpCurrency();
                    iPrCreate.orderIntake();
                    iPrCreate.targetPrice();
                    iPrCreate.warrantyRequirements();
                    iPrCreate.priceValidity();
                    iPrCreate.liquidatedDamages();
                    iPrCreate.addLineRequisitionItems();
                    iPrCreate.notes();
                    iPrCreate.attachments();
                    iPrCreate.prCreate();
                    break;
                case "mh":
                    iPrCreate.requesterLoginPRCreate();
                    iPrCreate.createButton();
                    iPrCreate.purchaseType();
                    iPrCreate.title();
                    iPrCreate.shipToYokogawa();
                    iPrCreate.project();
                    iPrCreate.wbs();
                    iPrCreate.incoterm();
                    iPrCreate.shippingAddress();
                    iPrCreate.billingType();
                    iPrCreate.vendor();
                    iPrCreate.quotationRequiredBy();
                    iPrCreate.expectedPOIssue();
                    iPrCreate.expectedDelivery();
                    iPrCreate.addLineRequisitionItems();
                    iPrCreate.notes();
                    iPrCreate.attachments();
                    iPrCreate.prCreate();
                    break;
                default:
                    System.out.println("Enter Proper Purchase Type");
                    break;
            }
        } catch (Exception error) {
            System.out.println("Error encountered: " + error.getMessage());
        }
    }
}