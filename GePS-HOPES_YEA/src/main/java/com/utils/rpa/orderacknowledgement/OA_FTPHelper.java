package com.utils.rpa.orderacknowledgement;
import com.utils.LoggerUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class OA_FTPHelper {

    FTPClient ftpClient;
    Logger logger;
    String excelFileName;
    String excelRemoteFilePath;
    File excelLocalFile;

//TODO Constructor
    private OA_FTPHelper() {
    }

    public OA_FTPHelper(FTPClient ftpClient){
        this.ftpClient = ftpClient;
        this.logger = LoggerUtil.getLogger(OA_FTPHelper.class);
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

    public String downloadOaFile(String remoteOaDownloadFilePath, String localPath, String poReferenceId, String poRevisionNumber){
        try {
            File localDir = new File(localPath);
            if (!localDir.exists() && !localDir.mkdirs());

            excelFileName = "OA_" + poReferenceId + "_" + poRevisionNumber + "-GePS-HOPES.xls";
            excelRemoteFilePath = remoteOaDownloadFilePath + excelFileName;
            excelLocalFile = new File(localDir, excelFileName);

            try (FileOutputStream fileOutputStream = new FileOutputStream(excelLocalFile)) {
                ftpClient.retrieveFile(excelRemoteFilePath, fileOutputStream);
            }
        } catch (Exception exception) {
            logger.error("Exception in downloading OA file function: {}", exception.getMessage());
        }
        closeConnection();
        return excelLocalFile.getAbsolutePath();
    }

    public void connectionEstablishAndUploadFiles(String server, int port, String user, String password, String localOaUploadFilePath, String remoteOaUploadFilePath) {
        try {
            connectionEstablish(server, port, user, password);

//TODO OA File Upload
            uploadIvFile(localOaUploadFilePath, remoteOaUploadFilePath + excelFileName);
        } catch (Exception exception) {
            logger.error("Exception in call upload file function: {}", exception.getMessage());
        }
        closeConnection();
    }

    public void uploadIvFile(String localOaUploadFilePath, String remoteOaUploadFilePath) {
        try {
            ftpClient.storeFile(remoteOaUploadFilePath, new FileInputStream(localOaUploadFilePath));
        } catch (Exception exception) {
            logger.error("Exception in upload OA File function: {}", exception.getMessage());
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
