package com.utils.rpa.invoiceverification;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IV_ExcelHelper {

    Logger logger;
    int randomNumber;

//TODO Constructor
    public IV_ExcelHelper() {
        this.logger = LoggerUtil.getLogger(IV_ExcelHelper.class);
    }

    public void updateExcel(String filePath) {
        Workbook workbook = null;
        try {
            try(FileInputStream fileInputStream = new FileInputStream(filePath)){
                if (filePath.endsWith(".xls")) {
                    workbook = new HSSFWorkbook(fileInputStream); //TODO For .xls files
                } else {
                    workbook = new XSSFWorkbook(fileInputStream); //TODO For .xlsx files
                }
            }

//TODO Update Sheet (IV)
            Sheet sheet = workbook.getSheet("IV");
            randomNumber = (int) Math.round(Math.random() * 10000);
            updateCell(sheet, 13, 1, String.valueOf(randomNumber)); //TODO IV Number
            updateCell(sheet, 11, 3, "IVCompleted"); //TODO Invoice Status
            updateCell(sheet, 12, 3, String.valueOf(randomNumber * 2)); //TODO Payment Clearing Document Number
            updateCell(sheet, 13, 3, "12/12/2025"); //TODO Payment Clearing Document Date

            String getAdvancePaymentFlag = sheet.getRow(1).getCell(5).getStringCellValue();
            String getMilestonePaymentFlag = sheet.getRow(10).getCell(5).getStringCellValue();
            
            if(getAdvancePaymentFlag.equalsIgnoreCase("Yes")) {
                updateCell(sheet, 7, 5, String.valueOf(randomNumber)); //TODO Advance Payment Number
            } else if (getMilestonePaymentFlag.equalsIgnoreCase("Yes")) {
                updateCell(sheet, 13, 5, String.valueOf(randomNumber)); //TODO Milestone Payment Number
            }
//TODO Save Changes
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                workbook.write(fileOutputStream);
            }
            workbook.close();
        } catch (IOException exception) {
            logger.error("Exception in update excel function: {}", exception.getMessage());
        }
    }

    public void updateCell(Sheet sheet, int rowNum, int colNum, String newValue) {
        try {
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum); //TODO Create the cell if it doesn't exist
            }
            cell.setCellValue(newValue); //TODO Set the value
        } catch (Exception exception) {
            logger.error("Exception in update cell function: {}", exception.getMessage());
        }
    }
}