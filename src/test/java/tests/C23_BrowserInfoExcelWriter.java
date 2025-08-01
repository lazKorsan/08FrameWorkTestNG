package tests;

import Pages.PracticeexpandtestingPage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import utilities.Driver;
import utilities.ConfigReader;
import java.io.FileOutputStream;
import java.io.IOException;

public class C23_BrowserInfoExcelWriter {

    public static void main(String[] args) {
        // Sayfaya git

        Driver.getDriver().get(ConfigReader.getProperty("dinamikXpathUrl"));

        // Sayfadaki butona tıkla
        PracticeexpandtestingPage practiceexpandtestingPage = new PracticeexpandtestingPage();

        practiceexpandtestingPage.showBrowserInformationButtons.click();

        // Elementlerden bilgileri al
        String userAgent = practiceexpandtestingPage.userAgent.getText();  // satır26
        String codeName = practiceexpandtestingPage.codeName.getText();    // satır27
        String name = practiceexpandtestingPage.name.getText();            // satır28
        String cookiesEnabled = practiceexpandtestingPage.cookiesEnabled.getText();  // satır29

        // Excel dosyasına yaz
        String excelPath = "C:\\Users\\Hp\\OneDrive\\Desktop\\PracticeExpandtesting\\webtablo.xlsx";

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Browser Info");

        // Başlık satırı
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Bilgi Türü");
        header.createCell(1).setCellValue("Değer");

        // Veri satırları
        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("User Agent");
        row1.createCell(1).setCellValue(userAgent);

        Row row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("Code Name");
        row2.createCell(1).setCellValue(codeName);

        Row row3 = sheet.createRow(3);
        row3.createCell(0).setCellValue("Name");
        row3.createCell(1).setCellValue(name);

        Row row4 = sheet.createRow(4);
        row4.createCell(0).setCellValue("Cookies Enabled");
        row4.createCell(1).setCellValue(cookiesEnabled);

        try (FileOutputStream fileOut = new FileOutputStream(excelPath)) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Bilgiler Excel dosyasına başarıyla yazıldı.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Driver kapatılabilir
        Driver.quitDriver();
    }
}
