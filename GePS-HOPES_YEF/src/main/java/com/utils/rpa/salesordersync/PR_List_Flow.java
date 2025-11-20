package com.utils.rpa.salesordersync;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.utils.LoggerUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.Logger;
import java.io.File;

public class PR_List_Flow {

    Logger logger;
    Page page;

//TODO Constructor
    private PR_List_Flow() {
    }

    public PR_List_Flow(Page page) {
        this.page = page;
        this.logger = LoggerUtil.getLogger(PR_List_Flow.class);
    }

    public void prListFlow(String purchaseType) {
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
            String localSoFilePath = jsonNode.get("msa").get("localSoFilePath").asText();
            String remoteSoFolderPath = jsonNode.get("msa").get("remoteSoFilePath").asText();
            String remotePrListFilePath = jsonNode.get("msa").get("remotePrListFilePath").asText();
            String localPrListFilePath = jsonNode.get("msa").get("localPrListFilePath").asText();
            int itemCount = jsonNode2.get("requisition").get("requisitionItemCount").asInt();
            String transactionNumber = jsonNode2.get("requisition").get("salesTransactionNumber").asText();
            String syncSOUrl = jsonNode.get("msa").get("syncSOUrl").asText();
            String readPRListFileForCatalog = jsonNode.get("msa").get("readPRListFileForCatalog").asText();
            String readPRListFileForNonCatalog = jsonNode.get("msa").get("readPRListFileForNonCatalog").asText();
            int requisitionId = jsonNode2.get("requisition").get("requisitionId").asInt();
            int requestForQuotationId = jsonNode2.get("requestForQuotation").get("requestForQuotationId").asInt();

//TODO Step 1: Create SO File
            PR_List_ExcelHelper prListExcelHelper = new PR_List_ExcelHelper();
            int soNumber = prListExcelHelper.createSOFile(itemCount, localSoFilePath);

//TODO Step 2: Connect to FTP and Download File
            PR_List_FTPHelper prListFtpHelper = new PR_List_FTPHelper(ftpClient);
            prListFtpHelper.connectionEstablish(ftpHost, ftpPort, ftpUser, ftpPassword);
            String localExcelFilePath = prListFtpHelper.downloadPrListFile(remotePrListFilePath, localPath);

//TODO Step 3: Update the PR List Excel File
            prListExcelHelper.updatePRListExcel(localExcelFilePath, soNumber, itemCount, transactionNumber);

//TODO Step 4: Upload the SO and PR List Files
            prListFtpHelper.connectionEstablishAndUploadFiles(ftpHost, ftpPort, ftpUser, ftpPassword, localSoFilePath, remoteSoFolderPath, localPrListFilePath, remotePrListFilePath);

//TODO Step 5: Call API to Update Status
            PR_List_APIHelper prListApiHelper = new PR_List_APIHelper(page);
            prListApiHelper.syncSO(syncSOUrl);
            prListApiHelper.updateStatus(purchaseType, readPRListFileForCatalog, readPRListFileForNonCatalog, requisitionId, requestForQuotationId);
            Thread.sleep(3000);
        } catch (Exception exception) {
            logger.error("Exception in PR List and SO File Automation Flow Function: {}", exception.getMessage());
        }
    }

    public int syncSO() {
        int soNumber = 0;
        try {
            FTPClient ftpClient = new FTPClient();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File("src\\test\\resources\\config\\msa-config.json"));
            JsonNode jsonNode2 = objectMapper.readTree(new File("src\\test\\resources\\config\\test-data.json"));
            String ftpHost = jsonNode.get("msa").get("ftpHost").asText();
            int ftpPort = jsonNode.get("msa").get("ftpPort").asInt();
            String ftpUser = jsonNode.get("msa").get("ftpUser").asText();
            String ftpPassword = jsonNode.get("msa").get("ftpPassword").asText();
            String localSoFilePath = jsonNode.get("msa").get("localSoFilePath").asText();
            String remoteSoFolderPath = jsonNode.get("msa").get("remoteSoFilePath").asText();
            int sdBOP2BOP5ItemCount = jsonNode2.get("requisition").get("sdBOP2BOP5ItemCount").asInt();
            String syncSOUrl = jsonNode.get("msa").get("syncSOUrl").asText();

//TODO Step 1: Create SO File
            PR_List_ExcelHelper prListExcelHelper = new PR_List_ExcelHelper();
            soNumber = prListExcelHelper.createSOFile(sdBOP2BOP5ItemCount, localSoFilePath);

//TODO Step 2: Upload the SO and PR List Files
            PR_List_FTPHelper prListFtpHelper = new PR_List_FTPHelper(ftpClient);
            prListFtpHelper.uploadSoFileForBOP2BOP5(ftpHost, ftpPort, ftpUser, ftpPassword, localSoFilePath, remoteSoFolderPath);

//TODO Step 3: Call API to Update Status
            PR_List_APIHelper prListApiHelper = new PR_List_APIHelper(page);
            prListApiHelper.syncSO(syncSOUrl);
        } catch (Exception exception) {
            logger.error("Exception in Sync SO Function: {}", exception.getMessage());
        }
        return soNumber;
    }

    public String updateSalesreferenceId(int salesOrder){
        try{
            String salesreferenceId = "YHQ123456";
            page.locator("//span[text()='Settings']").click();
            page.locator("//li/a[contains(text(),'Sales Orders')]").click();
            page.locator("//input[@type='search']").fill(String.valueOf(salesOrder));
            page.locator("//a[contains(text(),'Edit')]").click();
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.locator("#crmreference").fill(salesreferenceId);
            page.locator("#btnUpdate").click();
            page.locator("//button[text()='Yes']").click();
            return salesreferenceId;
        }
        catch (Exception exception) {
            logger.error("Exception in Update Sales Reference ID Function: {}", exception.getMessage());
            return null;
        }
    }
}