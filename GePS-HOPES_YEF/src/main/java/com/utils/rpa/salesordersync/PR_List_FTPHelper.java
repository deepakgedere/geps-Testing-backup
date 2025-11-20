package com.utils.rpa.salesordersync;
import com.utils.LoggerUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class PR_List_FTPHelper {

    FTPClient ftpClient;
    Logger logger;
    String excelFileName;
    String excelRemoteFilePath;
    File excelLocalFile;

//TODO Constructor
    private PR_List_FTPHelper() {
    }

    public PR_List_FTPHelper(FTPClient ftpClient){
        this.ftpClient = ftpClient;
        this.logger = LoggerUtil.getLogger(PR_List_FTPHelper.class);
    }

    public void connectionEstablish(String server, int port, String user, String password){
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalActiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (Exception exception) {
            logger.error("Exception in connection establish function: {}", exception.getMessage());
        }
    }

    public String downloadPrListFile(String remotePath, String localPath) {
        try {
            File localDir = new File(localPath);
            if (!localDir.exists() && !localDir.mkdirs());

            excelFileName = URLDecoder.decode("PR%20list%20for%20original%20ID.xls", StandardCharsets.UTF_8.name());
            excelRemoteFilePath = remotePath + excelFileName;
            excelLocalFile = new File(localDir, excelFileName);

            try (FileOutputStream fileOutputStream = new FileOutputStream(excelLocalFile)) {
                ftpClient.retrieveFile(excelRemoteFilePath, fileOutputStream);
            }
        } catch (Exception exception) {
            logger.error("Exception in downloading PR List file function: {}", exception.getMessage());
        }
        closeConnection();
        return excelLocalFile.getAbsolutePath();
    }

    public void connectionEstablishAndUploadFiles(String server, int port, String user, String password, String localSoFilePath, String remoteSoFolderPath, String localPrListFilePath, String remotePrListFilePath) {
        try {
            connectionEstablish(server, port, user, password);

//TODO SO File Upload
            uploadSoFile(localSoFilePath, remoteSoFolderPath);

//TODO PR List File Upload
            uploadPrListFile(localPrListFilePath, remotePrListFilePath + excelFileName);
        } catch (Exception exception) {
            logger.error("Exception in call upload file function: {}", exception.getMessage());
        }
        closeConnection();
    }

    public void uploadSoFile(String localSoFilePath, String remoteSoFolderPath) {
        try {
            ftpClient.storeFile(remoteSoFolderPath, new FileInputStream(localSoFilePath));
        } catch (Exception exception) {
            logger.error("Exception in upload SO File function: {}", exception.getMessage());
        }
    }

    public void uploadPrListFile(String localPrListFilePath, String remotePrListFilePath) {
        try {
            ftpClient.storeFile(remotePrListFilePath, new FileInputStream(localPrListFilePath));
        } catch (Exception exception) {
            logger.error("Exception in upload PR List File function: {}", exception.getMessage());
        }
    }

    public void uploadSoFileForBOP2BOP5(String server, int port, String user, String password, String localSoFilePath, String remoteSoFolderPath) {
        try {
            connectionEstablish(server, port, user, password);
            ftpClient.storeFile(remoteSoFolderPath, new FileInputStream(localSoFilePath));
        } catch (Exception exception) {
            logger.error("Exception in upload SO File function: {}", exception.getMessage());
        }
    }

    public void closeConnection() {
        try {
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (Exception exception) {
            logger.error("Exception in closing FTP connection function: {}", exception.getMessage());
        }
    }
}
