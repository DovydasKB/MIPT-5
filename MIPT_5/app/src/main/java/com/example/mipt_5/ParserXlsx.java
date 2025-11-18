package com.example.mipt_5;

import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.util.ArrayList;

public class ParserXlsx {
    private static double getNumericSafe(Cell cell) {
        if (cell == null) return 0;

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return cell.getNumericCellValue();
            }
            if (cell.getCellType() == CellType.STRING) {
                String s = cell.getStringCellValue().replace(",", ".").trim();
                if (s.isEmpty() || s.equals("-")) return 0;
                return Double.parseDouble(s);
            }
        } catch (Exception e) {
            System.out.println("getNumericSafe error: " + e.getMessage());
        }

        return 0;
    }

    private static String getStringSafe(Cell cell) {
        if (cell == null) return "";
        try {
            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            }
            if (cell.getCellType() == CellType.NUMERIC) {
                return String.valueOf(cell.getNumericCellValue());
            }
        } catch (Exception e) {
            System.out.println("getStringSafe error: " + e.getMessage());
        }
        return "";
    }

    public static ArrayList<Stock> parseExcel(InputStream inputStream) {
        System.out.println("ParserXLSX.parseExcel called");

        ArrayList<Stock> list = new ArrayList<>();

        try {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            boolean firstRow = true;

            for (Row row : sheet) {


                if (firstRow) {
                    firstRow = false;
                    continue;
                }

                try {
                    String ticker = getStringSafe(row.getCell(0));
                    if (ticker.isEmpty()) continue;

                    String name = getStringSafe(row.getCell(1));

                    double lastPrice = getNumericSafe(row.getCell(11));
                    double changePercent = getNumericSafe(row.getCell(12));

                    list.add(new Stock(ticker, name, lastPrice, changePercent));

                } catch (Exception e) {
                    System.out.println("ParserXlsx row parse FAILED: " + e.getMessage());
                }
            }

            workbook.close();
            System.out.println("ParserXlsx completed, total rows: " + list.size());

        } catch (Exception e) {
            System.out.println("ParserXlsx.parseExcel error: " + e.getMessage());
        }

        return list;
    }
}