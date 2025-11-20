package com.utils.rpa.purchaseorderrequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;
import java.nio.file.Paths;

public class MSA_PO_PDF_Helper {

    Logger logger;

//TODO Constructor
    public MSA_PO_PDF_Helper() {
        this.logger = LoggerUtil.getLogger(MSA_PO_PDF_Helper.class);
    }

    public String poPdfFileNameUpdate(int newPoNumber, String filePath, JsonNode jsonNode) {
        File newFilePath = null;
        try {
            String oldFileName = "PO_.PDF";
            File oldFilePath = Paths.get(filePath, oldFileName).toFile();

            String newFileName;
            boolean poProcessed = jsonNode.get("purchaseOrderRequests").get("poProcessed").asBoolean();
            if (!poProcessed) {
                newFileName = "PO_" + newPoNumber + ".PDF";
            } else {
                newFileName = "PO_" + newPoNumber + "_001.PDF";
            }

            newFilePath = Paths.get(filePath, newFileName).toFile();

            try (PDDocument document = PDDocument.load(oldFilePath)) {
                document.setAllSecurityToBeRemoved(true);
                document.save(newFilePath);
            }
        } catch (Exception exception) {
            System.err.println("Exception in PO PDF File Name Update Function: " + exception.getMessage());
        }
        return newFilePath.getAbsolutePath();
    }
}