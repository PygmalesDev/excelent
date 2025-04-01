package net.pygmales.excelent.service;

import net.pygmales.excelent.Main;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;

public class ExcelManagerService {
    private static final ExcelManagerService INSTANCE = new ExcelManagerService();
    public static final Logger log = Main.getLogger();

    private XSSFWorkbook workbook;


    public static ExcelManagerService getInstance() {
        return INSTANCE;
    }

    public void openExcelFile(File file) {
        try {
           this.workbook = new XSSFWorkbook(file);
           log.info("Successfully opened '{}' as XSSF Workbook", file.getName());
        } catch (IOException | InvalidFormatException e) {
            log.error(e.getMessage());
        }
    }



    public static void load() {}
}
