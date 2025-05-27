package com.example.app.util;

import com.example.app.model.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    private static final String FILE_PATH = "users.xlsx";

    public static List<User> readUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_PATH);
    
        if (!file.exists() || file.length() == 0) {
            return users;
        }
    
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
    
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                Long id = (long) row.getCell(0).getNumericCellValue();
                String email = row.getCell(1).getStringCellValue();
                String password = row.getCell(2).getStringCellValue();
                String role = row.getCell(3).getStringCellValue();
                users.add(new User(id, email, password, role));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void writeUser(User user) {
        File file = new File(FILE_PATH);
        Workbook workbook;
        Sheet sheet;

        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Users");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("ID");
                header.createCell(1).setCellValue("Email");
                header.createCell(2).setCellValue("Password");
                header.createCell(3).setCellValue("Role");
            }

            int lastRow = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(lastRow);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getEmail());
            row.createCell(2).setCellValue(user.getPassword());
            row.createCell(3).setCellValue(user.getRole());

            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}