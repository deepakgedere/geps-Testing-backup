package com.utils.rpa.orderacknowledgement;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class OA_ExcelHelper {

    Logger logger;

//TODO Constructor
    public OA_ExcelHelper() {
        this.logger = LoggerUtil.getLogger(OA_ExcelHelper.class);
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

//TODO Update Sheet (OA)
            Sheet sheet = workbook.getSheet("OA");
            updateCell(sheet, 13, 3, "Updated"); //TODO Order Acknowledgement Status

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