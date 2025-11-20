package com.utils.rpa.purchaseorderrequest;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;

public class MSA_ExcelHelper {

    Logger logger;
    int randomNumber;

//TODO Constructor
    public MSA_ExcelHelper() {
        this.logger = LoggerUtil.getLogger(MSA_ExcelHelper.class);
    }

    public int updateExcel(String filePath) {
        Workbook workbook = null;
        try {
            try(FileInputStream fileInputStream = new FileInputStream(filePath)){
                if (filePath.endsWith(".xls")) {
                    workbook = new HSSFWorkbook(fileInputStream); //TODO For .xls files
                } else {
                    workbook = new XSSFWorkbook(fileInputStream); //TODO For .xlsx files
                }
            }

//TODO Update Sheet 1 (RequesterInput)
            Sheet sheet1 = workbook.getSheet("RequesterInput");
            randomNumber = (int) Math.round(Math.random() * 10000);
            updateCell(sheet1, 3, 1, String.valueOf(randomNumber));
            updateCell(sheet1, 3, 2, "POReleasePending");

//TODO Update Sheet 2 (MSA)
            Sheet sheet2 = workbook.getSheet("MSA");
            int rowCount = sheet2.getLastRowNum(); //TODO Get Total row count
            for (int i = 1; i <= rowCount; i++) {
                if(sheet2.getRow(i).getCell(0) != null && sheet2.getRow(i).getCell(0).getCellType() != CellType.BLANK) {
                    updateCell(sheet2, i, 11, String.valueOf(randomNumber)); //TODO Tokuchu Number L column (index 11)
                    updateCell(sheet2, i, 12, String.valueOf(randomNumber)); //TODO Tokuchu Number for Price M column (index 12)
                    updateCell(sheet2, i, 94, String.valueOf(randomNumber)); //TODO PR Number CQ column (index 94)
                    updateCell(sheet2, i, 95, String.valueOf(randomNumber)); //TODO PR Item Number CR column (index 95)
                    updateCell(sheet2, i, 96, String.valueOf(randomNumber)); //TODO PO Number CS column (index 96)
                }
            }

//TODO Save Changes
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                workbook.write(fileOutputStream);
            }
            workbook.close();
        } catch (IOException exception) {
            logger.error("Exception in update excel function: {}", exception.getMessage());
        }
        return randomNumber;
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