package com.utils.rpa.invoiceverification;
import com.utils.LoggerUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class IV_FTPHelper {

    FTPClient ftpClient;
    Logger logger;
    String excelFileName;
    String excelRemoteFilePath;
    File excelLocalFile;

//TODO Constructor
    private IV_FTPHelper() {
    }

    public IV_FTPHelper(FTPClient ftpClient){
        this.ftpClient = ftpClient;
        this.logger = LoggerUtil.getLogger(IV_FTPHelper.class);
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

    public String downloadIvFile(String remotePath, String localPath, String vendorReferenceId, String poReferenceId, String invoiceReferenceId, String companyCode) {
        try {
            File localDir = new File(localPath);
            if (!localDir.exists() && !localDir.mkdirs());

            excelFileName = "IV_" + vendorReferenceId + "_" + poReferenceId + "_" + invoiceReferenceId + "_" + companyCode + "_GePS-HOPES.xls";
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

    public void connectionEstablishAndUploadFiles(String server, int port, String user, String password, String localIvFilePath, String remoteIvFolderPath) {
        try {
            connectionEstablish(server, port, user, password);

//TODO IV File Upload
            uploadIvFile(localIvFilePath, remoteIvFolderPath + excelFileName);
        } catch (Exception exception) {
            logger.error("Exception in call upload file function: {}", exception.getMessage());
        }
        closeConnection();
    }

    public void uploadIvFile(String localIvFilePath, String remoteIvFolderPath) {
        try {
            ftpClient.storeFile(remoteIvFolderPath, new FileInputStream(localIvFilePath));
        } catch (Exception exception) {
            logger.error("Exception in upload IV File function: {}", exception.getMessage());
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
