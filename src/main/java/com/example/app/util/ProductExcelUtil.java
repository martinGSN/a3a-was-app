package com.example.app.util;

import com.example.app.model.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductExcelUtil {

    private static final String FILE_PATH = "products.xlsx";
    private static final String SHEET_NAME = "Products";

    // 전체 리스트 덮어쓰기 (권장 방식)
    public static void writeAll(List<Product> products) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            // 헤더 작성
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Name");
            header.createCell(2).setCellValue("Description");
            header.createCell(3).setCellValue("Price");
            header.createCell(4).setCellValue("OwnerId");

            int rowNum = 1;
            for (Product p : products) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(p.getId());
                row.createCell(1).setCellValue(p.getName());
                row.createCell(2).setCellValue(p.getDescription());
                row.createCell(3).setCellValue(p.getPrice());
                row.createCell(4).setCellValue(p.getOwnerId());
            }

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Product> readProducts() {
        List<Product> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return list;

        try (Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) return list;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Product p = new Product();
                p.setId((long) row.getCell(0).getNumericCellValue());
                p.setName(row.getCell(1).getStringCellValue());
                p.setDescription(row.getCell(2).getStringCellValue());
                p.setPrice(row.getCell(3).getNumericCellValue());
                p.setOwnerId((long) row.getCell(4).getNumericCellValue());
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
