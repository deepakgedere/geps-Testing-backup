package com.utils.rpa.purchaseorderrequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Page;
import com.utils.LoggerUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.Logger;
import java.io.File;

public class MSA_Flow {

    Logger logger;
    Page page;

//TODO Constructor
    private MSA_Flow() {
    }

    public MSA_Flow(Page page) {
        this.page = page;
        this.logger = LoggerUtil.getLogger(MSA_Flow.class);
    }

    public void msaFlow() {
        try {
            FTPClient ftpClient = new FTPClient();
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(new File("src\\test\\resources\\config\\msa-config.json"));
            JsonNode testDataJsonNode = objectMapper.readTree(new File("src\\test\\resources\\config\\test-data.json"));

            String ftpHost = jsonNode.get("msa").get("ftpHost").asText();
            int ftpPort = jsonNode.get("msa").get("ftpPort").asInt();
            String ftpUser = jsonNode.get("msa").get("ftpUser").asText();
            String ftpPassword = jsonNode.get("msa").get("ftpPassword").asText();

            String localPath = jsonNode.get("msa").get("localPath").asText();

            //TODO POC and Sales Folder Path
            String remoteGepsToHopesFilePath = jsonNode.get("msa").get("remoteGepsToHopesFilePath").asText();
            String remoteHopesToGepsPathXLS = jsonNode.get("msa").get("remoteHopesToGepsPath").asText();
            String remotePOFilePathPO = jsonNode.get("msa").get("remotePOFilePath").asText();

            //TODO NON-POC Folder Path
            String remoteGepsToHopesNonPocFilePath = jsonNode.get("msa").get("remoteGepsToHopesNonPocFilePath").asText();
            String remoteHopesToGepsPathXLSNonPoc = jsonNode.get("msa").get("remoteHopesToGepsNonPocPath").asText();
            String remotePOFilePathNonPoc = jsonNode.get("msa").get("remotePONonPocFilePath").asText();

            //TODO API URL for POC, NON-POC and Sales
            String readPORAPIUrl = jsonNode.get("msa").get("readPORAPIUrl").asText();
            String generatePOAPIUrl = jsonNode.get("msa").get("generatePOAPIUrl").asText();

            String type = testDataJsonNode.get("requisition").get("purchaseType").asText();
            int id = testDataJsonNode.get("purchaseOrderRequests").get("purchaseOrderRequestId").asInt();
            String porReferenceNumber = testDataJsonNode.get("purchaseOrderRequests").get("porReferenceNumber").asText();

            String excelDownloadFilePath;
            String uploadPoFilePath;
            String excelUploadFilePath;
            if(type.equalsIgnoreCase("PS") || type.equalsIgnoreCase("Sales")) {
                excelDownloadFilePath = remoteGepsToHopesFilePath;
                uploadPoFilePath = remotePOFilePathPO;
                excelUploadFilePath = remoteHopesToGepsPathXLS;
            } else {
                excelDownloadFilePath = remoteGepsToHopesNonPocFilePath;
                uploadPoFilePath = remotePOFilePathNonPoc;
                excelUploadFilePath = remoteHopesToGepsPathXLSNonPoc;
            }

//TODO Step 1: Connect to FTP and Download File
            MSA_FTPHelper msaFtpHelper = new MSA_FTPHelper(ftpClient);
            msaFtpHelper.connectionEstablish(ftpHost, ftpPort, ftpUser, ftpPassword);
            String localExcelFilePath = msaFtpHelper.downloadFile(excelDownloadFilePath, localPath, porReferenceNumber, testDataJsonNode, type);

//TODO Step 2: Update the Excel File
            MSA_ExcelHelper msaExcelHelper = new MSA_ExcelHelper();
            int poNumber = msaExcelHelper.updateExcel(localExcelFilePath);

//TODO Step 3: Update the PO PDF File
            MSA_PO_PDF_Helper msaPoPdfHelper = new MSA_PO_PDF_Helper();
            String localPoFilePath = msaPoPdfHelper.poPdfFileNameUpdate(poNumber, localPath, testDataJsonNode);

//TODO Step 4: Upload the Updated File
            msaFtpHelper.connectionEstablishAndUploadFiles(ftpHost, ftpPort, ftpUser, ftpPassword, localPoFilePath, localExcelFilePath, poNumber, uploadPoFilePath, excelUploadFilePath, testDataJsonNode);

//TODO Step 5: Call API to Update Status
            MSA_APIHelper msaApiHelper = new MSA_APIHelper(page);
            msaApiHelper.updateStatus(readPORAPIUrl, id);
            msaApiHelper.updateStatus(generatePOAPIUrl, id);
        } catch (Exception exception) {
            logger.error("Exception in MSA File Automation Flow Function: {}", exception.getMessage());
        }
    }
}