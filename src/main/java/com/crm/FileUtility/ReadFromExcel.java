package com.crm.FileUtility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadFromExcel {

    public static String single(String sheet, int row, int colom)
            throws EncryptedDocumentException, IOException {

        FileInputStream fis = new FileInputStream(
                ".\\src\\test\\resources\\createAdmin.xlsx");

        Workbook wb = WorkbookFactory.create(fis);

        Sheet sh = wb.getSheet(sheet);
        
        return sh.getRow(row).getCell(colom).toString();
    }


    public static Object[][] readLoginData() throws IOException {

        FileInputStream fis = new FileInputStream(
                ".\\src\\test\\resources\\loginData.xlsx");

        Workbook wb = WorkbookFactory.create(fis);

        Sheet sh = wb.getSheet("LoginData");

        int rows = sh.getLastRowNum();

        // Create array for actual test cases, excluding header
        Object[][] data = new Object[rows - 1][4];

        // Start from row 2 because row 1 contains headings
        for (int i = 2; i <= rows; i++) {

            data[i - 2][0] = sh.getRow(i).getCell(0).getStringCellValue();

            data[i - 2][1] = sh.getRow(i).getCell(1) == null ? "" :
                    sh.getRow(i).getCell(1).getStringCellValue();

            data[i - 2][2] = sh.getRow(i).getCell(2) == null ? "" :
                    sh.getRow(i).getCell(2).getStringCellValue();

            data[i - 2][3] = sh.getRow(i).getCell(3).getStringCellValue();
        }

        wb.close();
        fis.close();

        return data;
    }
}