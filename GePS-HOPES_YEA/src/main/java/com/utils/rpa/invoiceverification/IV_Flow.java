package com.utils.rpa.invoiceverification;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Page;
import com.utils.LoggerUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.Logger;
import java.io.File;

public class IV_Flow {

    Logger logger;
    Page page;

//TODO Constructor
    private IV_Flow(){
    }

    public IV_Flow(Page page) {
        this.page = page;
        this.logger = LoggerUtil.getLogger(IV_Flow.class);
    }

    public void ivFlow() {
        try {
            FTPClient ftpClient = new FTPClient();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File("src\\test\\resources\\config\\msa-config.json"));
            JsonNode jsonNode2 = objectMapper.readTree(new File("src\\test\\resources\\config\\test-data.json"));
            String ftpHost = jsonNode.get("msa").get("ftpHost").asText();
            int ftpPort = jsonNode.get("msa").get("ftpPort").asInt();
            String ftpUser = jsonNode.get("msa").get("ftpUser").asText();
            String ftpPassword = jsonNode.get("msa").get("ftpPassword").asText();
            String localPath = jsonNode.get("msa").get("localPath").asText();
            String vendorReferenceId = jsonNode2.get("invoices").get("vendorReferenceId").asText();
            String poReferenceId = jsonNode2.get("purchaseOrders").get("poReferenceId").asText();
            String invoiceReferenceId = jsonNode2.get("invoices").get("invoiceReferenceId").asText();
            String remoteIvDownloadFilePath = jsonNode.get("msa").get("remoteIvDownloadFilePath").asText();
            String remoteIvUploadFilePath = jsonNode.get("msa").get("remoteIvUploadFilePath").asText();
            String readIvNumberUrl = jsonNode.get("msa").get("readIvNumberUrl").asText();
            String invoiceId = jsonNode2.get("invoices").get("invoiceId").asText();
            String companyCode = jsonNode2.get("invoices").get("companyCode").asText();

//TODO Step 1: Connect to FTP and Download File
            IV_FTPHelper ivFtpHelper = new IV_FTPHelper(ftpClient);
            ivFtpHelper.connectionEstablish(ftpHost, ftpPort, ftpUser, ftpPassword);
            String localExcelFilePath = ivFtpHelper.downloadIvFile(remoteIvDownloadFilePath, localPath, vendorReferenceId, poReferenceId, invoiceReferenceId, companyCode);

//TODO Step 2: Update the IV File
            IV_ExcelHelper ivExcelHelper = new IV_ExcelHelper();
            ivExcelHelper.updateExcel(localExcelFilePath);

//TODO Step 3: Upload the IV File
            ivFtpHelper.connectionEstablishAndUploadFiles(ftpHost, ftpPort, ftpUser, ftpPassword, localExcelFilePath, remoteIvUploadFilePath);

//TODO Step 4: Call API to Update Status
            IV_APIHelper ivApiHelper = new IV_APIHelper(page);
            ivApiHelper.updateStatus(readIvNumberUrl, invoiceId);
        } catch (Exception exception) {
            logger.error("Exception in PR List and SO File Automation Flow Function: {}", exception.getMessage());
        }
    }
}