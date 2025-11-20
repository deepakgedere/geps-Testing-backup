package com.utils.rpa.salesordersync;
import com.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PR_List_ExcelHelper {

    Logger logger;
    int randomNumber;

//TODO Constructor
    public PR_List_ExcelHelper() {
        this.logger = LoggerUtil.getLogger(PR_List_ExcelHelper.class);
    }

    public int createSOFile(int itemCount, String filePath){
        Workbook workbook = null;
        int soNumber = 0;
        try {
            try(FileInputStream fileInputStream = new FileInputStream(filePath)){
                if (filePath.endsWith(".xls")) {
                    workbook = new HSSFWorkbook(fileInputStream); //TODO For .xls files
                } else {
                    workbook = new XSSFWorkbook(fileInputStream); //TODO For .xlsx files
                }
            }

//TODO Update Sheet 1 (Sheet 1)
            Sheet sheet1 = workbook.getSheet("Sheet1");

            int lastRowNum = sheet1.getLastRowNum(); //TODO Get the last row number
            for (int g = 1; g <= lastRowNum; g++) { //TODO Start from the second row (index 1)
                Row row = sheet1.getRow(g);
                sheet1.removeRow(row); //TODO Remove the row
            }

            randomNumber = (int) Math.round(Math.random() * 10000);
            soNumber = Integer.parseInt("202500" + randomNumber);

        for (int i = 1; i <= itemCount; i++) {
            Row row = sheet1.getRow(i);
            if (row == null) {
                sheet1.createRow(i); //TODO Create a new row if it doesn't exist
            }
            //TODO All details are hardcoded!!
            //TODO <<Mandatory Field>>
            updateCell(sheet1, i, 0, String.valueOf(soNumber)); //TODO Sales Document Number A column (index 1)
            //TODO <<Mandatory Field>>
            updateCell(sheet1, i, 1, String.valueOf(10 + i)); //TODO Sales Order Item Number B column (index 2)
            updateCell(sheet1, i, 3, "0"); //TODO Quotation Item No D column (index 4)
            updateCell(sheet1, i, 6, "Y101"); //TODO Billing Document No HK column (index 7)
            updateCell(sheet1, i, 7, "Sales Order(Prd)"); //TODO Sales Document Type HK column (index 8)
            //TODO <<Mandatory Field>>
            updateCell(sheet1, i, 8, "2400"); //TODO Sales Organization HK column (index 9)
            //TODO <<Mandatory Field>>
            updateCell(sheet1, i, 9, "YEA"); //TODO Text: Sales Organization HK column (index 10)
            updateCell(sheet1, i, 10, "30"); //TODO Distribution Channel HK column (index 11)
            updateCell(sheet1, i, 11, "Inter-company"); //TODO Text: Distribution Channel HK column (index 12)
            //TODO <<Mandatory Field>>
            updateCell(sheet1, i, 12, "GN40"); //TODO Sales office HK column (index 13)
            updateCell(sheet1, i, 13, "Overseas Int.Company"); //TODO Text: Sales Office HK column (index 14)
            updateCell(sheet1, i, 18, "5800- TEST 16"); //TODO Sales Group HK column (index 19)
            updateCell(sheet1, i, 20, "12-09-2025"); //TODO PO date HK column (index 21.00)
            updateCell(sheet1, i, 21, "31-10-2024"); //TODO Requested deliv.date HK column (index 22.00)
            updateCell(sheet1, i, 23, "Y12"); //TODO Order reason HK column (index 24)
            updateCell(sheet1, i, 24, "Y12 Order Intake with PO"); //TODO Text: Order reason HK column (index 25)
            updateCell(sheet1, i, 25, "0.00"); //TODO Net value of the order HK column (index 26)
            updateCell(sheet1, i, 26, "EUR"); //TODO SD document currency HK column (index 27)
            updateCell(sheet1, i, 27, "31-10-2024"); //TODO Pricing date HK column (index 28.00)
            updateCell(sheet1, i, 36, "Y2U00027"); //TODO Sold-to party HK column (index 37)
            updateCell(sheet1, i, 38, "Y2U00027"); //TODO Name: Sold-to party HK column (index 39)
            //TODO <<Mandatory Field>>
            updateCell(sheet1, i, 40, "200132028"); //TODO Payer HK column (index 41)
            updateCell(sheet1, i, 41, "Yokogawa India (Pty) Ltd."); //TODO Name: Payer HK column (index 42)
            updateCell(sheet1, i, 42, "0"); //TODO Name: End User HK column (index 43)
            //TODO <<Mandatory Field>>
            updateCell(sheet1, i, 52, "End User"); //TODO SD User Status HK column (index 53)
            //TODO <<Mandatory Field>>
            updateCell(sheet1, i, 53, "Name: End User"); //TODO Text: SD User Status HK column (index 54)
            updateCell(sheet1, i, 58, "2"); //TODO Customer Group HK column (index 59)
            updateCell(sheet1, i, 60, "0"); //TODO Text: Customer Group HK column (index 61)
            updateCell(sheet1, i, 64, "0"); //TODO SAP User HK column (index 65)
            updateCell(sheet1, i, 66, "2"); //TODO Serial Sales document item No HK column (index 67)
            updateCell(sheet1, i, 74, "SAPP"); //TODO Confirm Quantity HK column (index 75)
            updateCell(sheet1, i, 75, "SO: Approved"); //TODO Net value of the order item HK column (index 76)
            updateCell(sheet1, i, 78, "0"); //TODO Net price per unit of measure HK column (index 79)
            //TODO <<Mandatory Field>>
            updateCell(sheet1, i, 82, "Customer Group"); //TODO Standard Price HK column (index 83)
            //TODO <<Mandatory Field>>
            updateCell(sheet1, i, 83, "Text: Customer Group"); //TODO Domestic Price (Japan) HK column (index 84)
            updateCell(sheet1, i, 91, "30059222"); //TODO Direct PO to MFG Flag HK column (index 92)
            updateCell(sheet1, i, 92, "Rohit Bharad"); //TODO Reason for rejection HK column (index 93)
            updateCell(sheet1, i, 93, "12-09-2024"); //TODO Text: Reason for rejection HK column (index 94.00)
            updateCell(sheet1, i, 97, "10"); //TODO Delivery Quantity in sales unit HK column (index 98)
            updateCell(sheet1, i, 100, "F3XD64_F000000001"); //TODO Delivery Basis Indicator HK column (index 101)
            updateCell(sheet1, i, 105, "10.000"); //TODO Value to be Billed in Billing Plan HK column (index 106.000)
            //TODO <<Mandatory Field>>
            updateCell(sheet1, i, 107, String.valueOf((10 + i) * 10)); //TODO Billing Currency HK column (index 108.000)
            updateCell(sheet1, i, 108, "661.00"); //TODO Terms of Payment HK column (index 109)
            updateCell(sheet1, i, 109, "100.00"); //TODO Text: Terms of Payment HK column (index 110)
            updateCell(sheet1, i, 110, "0"); //TODO Usage HK column (index 111)
            updateCell(sheet1, i, 112, "0.00"); //TODO Text: Usage HK column (index 113)
            updateCell(sheet1, i, 113, "0.00"); //TODO Msg for Own ShipSect(Header) HK column (index 114)
            updateCell(sheet1, i, 115, "0"); //TODO Msg for Packing (Header) HK column (index 116)
            updateCell(sheet1, i, 116, "%"); //TODO Msg for All ShipSect(Header) HK column (index 117)
            updateCell(sheet1, i, 119, "0.00"); //TODO Msg for Own ShipSect(Item) HK column (index 120)
            updateCell(sheet1, i, 122, "0.00"); //TODO Msg for Packing (Item) HK column (index 123)
            updateCell(sheet1, i, 125, "0.00"); //TODO Msg for All ShipSect(Item) HK column (index 126)
            updateCell(sheet1, i, 128, "0.00"); //TODO Msg. on Delivery Note (Item) HK column (index 129)
            updateCell(sheet1, i, 131, "0.00"); //TODO SO H:Msg. on Invoice HK column (index 132)
            updateCell(sheet1, i, 139, "0.00"); //TODO Billing Status HK column (index 140)
            updateCell(sheet1, i, 142, "0.00"); //TODO Billing date HK column (index 143)
            updateCell(sheet1, i, 145, "0.00"); //TODO Tax classific.1 HK column (index 146)
            updateCell(sheet1, i, 148, "0.00"); //TODO Text: Tax classific.1 HK column (index 149)
            updateCell(sheet1, i, 151, "0.00"); //TODO Combined MS-Code HK column (index 152)
            updateCell(sheet1, i, 152, "0"); //TODO Combined MS-Code Indicator HK column (index 153)
            updateCell(sheet1, i, 154, "0.00"); //TODO Inquiry ID HK column (index 155)
            updateCell(sheet1, i, 155, "0"); //TODO Service Order Number HK column (index 156)
            updateCell(sheet1, i, 157, "0.00"); //TODO Your Reference HK column (index 158)
            updateCell(sheet1, i, 158, "0"); //TODO Your Reference (Ship-to party) HK column (index 159)
            updateCell(sheet1, i, 160, "0.00"); //TODO Shipping Point HK column (index 161)
            updateCell(sheet1, i, 161, "0"); //TODO Text: Shipping Point HK column (index 162)
            updateCell(sheet1, i, 163, "0.00"); //TODO Incoterms1 HK column (index 164)
            updateCell(sheet1, i, 164, "0"); //TODO Incoterms2 HK column (index 165)
            updateCell(sheet1, i, 166, "0.00"); //TODO Billing Gr. HK column (index 167)
            updateCell(sheet1, i, 167, "0.00"); //TODO Sales Turnover Date (Plan) HK column (index 168)
            updateCell(sheet1, i, 168, "0.00"); //TODO Sales Turnover Date (Actual) HK column (index 169)
            updateCell(sheet1, i, 169, "0.00"); //TODO Project definition(level 0) HK column (index 170)
            updateCell(sheet1, i, 170, "0.00"); //TODO Project text (Level 0) HK column (index 171)
            updateCell(sheet1, i, 171, "0.00"); //TODO WBS Element (Level 2) HK column (index 172)
            updateCell(sheet1, i, 172, "0.00"); //TODO WBS text (Level 2) HK column (index 173)
            updateCell(sheet1, i, 173, "0.000"); //TODO WBS System status HK column (index 174)
            updateCell(sheet1, i, 174, "%"); //TODO Text: WBS System status HK column (index 175)
            updateCell(sheet1, i, 175, "0.00"); //TODO WBS User Status HK column (index 176)
            updateCell(sheet1, i, 183, "F3XD64-3F/K2/CT"); //TODO Overall Status HK column (index 184)
            updateCell(sheet1, i, 213, "987654321"); //TODO Billing Status HK column (index 214)
            //TODO <<Mandatory Field>>
            updateCellDate(sheet1, i, 217, workbook,"30-12-2025"); //TODO Actual Billing Date HK column (index 218)
            //TODO <<Mandatory Field>>
            updateCell(sheet1, i, 218, "2002"); //TODO Planned Billing Date HK column (index 219)
            updateCell(sheet1, i, 220, "FCA"); //TODO Automatic Billing Style HK column (index 221)
            updateCell(sheet1, i, 221, "TOKYO,JAPAN"); //TODO Text: Automatic Billing Style HK column (index 222)
            updateCell(sheet1, i, 221, "0"); //TODO Incoterms1 HK column (index 223)
            updateCell(sheet1, i, 240, "P"); //TODO Incoterms2 HK column (index 241)
            updateCell(sheet1, i, 241, "N"); //TODO Project Category HK column (index 242)
            updateCell(sheet1, i, 242, "P"); //TODO Text: Project Category HK column (index 243)
            updateCell(sheet1, i, 243, "N"); //TODO Project definition(level 0) HK column (index 244)
            updateCell(sheet1, i, 244, "N"); //TODO Project text (Level 0) HK column (index 245)
            updateCell(sheet1, i, 245, "1.000"); //TODO WBS Element (Level 2) HK column (index 246.000)
            updateCell(sheet1, i, 247, "N"); //TODO WBS text (Level 2) HK column (index 248)
            updateCell(sheet1, i, 248, "N"); //TODO WBS System status HK column (index 249)
            updateCell(sheet1, i, 249, "661.00"); //TODO Text: WBS System status HK column (index 250)
            updateCell(sheet1, i, 250, "USD"); //TODO WBS User Status HK column (index 251)
            updateCell(sheet1, i, 251, "Not Performed"); //TODO Text: WBS User Status HK column (index 252)
            updateCell(sheet1, i, 279, "0.00"); //TODO Value to be Billed in Billing Plan HK column (index 280)
        }

//TODO Save Changes
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                workbook.write(fileOutputStream);
            }
        } catch (IOException exception) {
            logger.error("Exception in creating SO file function: {}", exception.getMessage());
        }
        return soNumber;
    }

    public void updatePRListExcel(String filePath, int soNumber, int itemCount, String transactionNumber) {
        Workbook workbook;
        try {
            try(FileInputStream fileInputStream = new FileInputStream(filePath)){
                if (filePath.endsWith(".xls")) {
                    workbook = new HSSFWorkbook(fileInputStream); //TODO For .xls files
                } else {
                    workbook = new XSSFWorkbook(fileInputStream); //TODO For .xlsx files
                }
            }

//TODO Update Sheet (SAPUI5 Export)
            Sheet sheet = workbook.getSheet("SAPUI5 Export");
            int lastRowNum = sheet.getLastRowNum(); //TODO Get the last row number
            for (int i = 1; i <= lastRowNum; i++) { //TODO Start from the second row (index 1)
                Row row = sheet.getRow(i);
                sheet.removeRow(row); //TODO Remove the row
            }

            for (int i = 1; i <= itemCount; i++) {
                Row row1 = sheet.getRow(i);
                if (row1 == null) {
                    sheet.createRow(i); //TODO Create a new row if it doesn't exist
                }
                updateCell(sheet, i, 0, String.format("%08d", i)); //TODO Purchase Requisition Number A column (index 0)
                updateCell(sheet, i, 11, "EJA530E_F000000001"); //TODO Material Number L column (index 11)
                updateCell(sheet, i, 12, "2400"); //TODO Plant M column (index 12)
                updateCell(sheet, i, 13, "9001"); //TODO Storage Location N column (index 13)
                updateCellDate(sheet, i, 20, workbook,"30-12-2025"); //TODO Item Delivery Date U column (index 20)
                updateCell(sheet, i, 75, "BOP252400"); //TODO MS Code BX column (index 75)
                updateCell(sheet, i, 102, String.valueOf(soNumber)); //TODO Sales Order Number CY column (index 102)
                updateCell(sheet, i, 103, String.valueOf((10 + i))); //TODO Sales Order Item Number CZ column (index 103)
                String formattedNumber = String.format("%03d", i); //TODO Format the number to 3 digits (eg. 001, 002..)
                updateCell(sheet, i, 134, transactionNumber + "-" + formattedNumber); //TODO Original Id EE column (index 134)
            }

//TODO Save Changes
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                workbook.write(fileOutputStream);
            }
            workbook.close();
        } catch (IOException exception) {
            logger.error("Exception in update PR List Excel function: {}", exception.getMessage());
        }
    }

    public void updateCell(Sheet sheet, int rowNum, int colNum, String newValue) {
        try {
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(colNum);
            if (cell == null || cell.getCellType() == CellType.BLANK) {
                cell = row.createCell(colNum); //TODO Create the cell if it doesn't exist
            }
            cell.setCellValue(newValue); //TODO Set the value
        } catch (Exception exception) {
            logger.error("Exception in update cell function: {}", exception.getMessage());
        }
    }

    public void updateCellDate(Sheet sheet, int rowNum, int colNum, Workbook workbook, String dateValue) {
        try {
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(colNum);
            if (cell == null || cell.getCellType() == CellType.BLANK) {
                cell = row.createCell(colNum); //TODO Create the cell if it doesn't exist
            }

            // Parse the date string into a Date object
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = dateFormat.parse(dateValue);

            // Set the cell value as a date
            cell.setCellValue(date);

            CellStyle dateCellStyle = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));
            cell.setCellStyle(dateCellStyle);

        } catch (Exception exception) {
            logger.error("Exception in update cell data function: {}", exception.getMessage());
        }
    }
}