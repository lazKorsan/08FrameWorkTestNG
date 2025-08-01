package tests;

import Pages.PracticeexpandtestingPage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;
import utilities.Driver;
import utilities.ConfigReader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class C24_BrowserInfoExcelWriter {

    public static void main(String[] args) {
        // Sayfaya git
        Driver.getDriver().get(ConfigReader.getProperty("dinamikXpathUrl"));

        // Sayfadaki butona tıkla
        PracticeexpandtestingPage practiceexpandtestingPage = new PracticeexpandtestingPage();
        practiceexpandtestingPage.showBrowserInformationButtons.click();

        // Excel dosyası oluştur
        String excelPath = "C:\\Users\\Hp\\OneDrive\\Desktop\\PracticeExpandtesting\\webtablo.xlsx";
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Browser Info");

        // Başlık satırı
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Bilgi Türü");
        header.createCell(1).setCellValue("Değer");

        // Bilgileri DOM'dan dinamik olarak al ve yaz
        List<WebElement> infoItems = practiceexpandtestingPage.browserInfoItems;

        for (int i = 0; i < infoItems.size(); i++) {
            WebElement item = infoItems.get(i);
            String fullText = item.getText();

            // Çoğunlukla "Key: Value" şeklinde olduğu varsayımıyla ayrıştırıyoruz:
            String[] parts = fullText.split(":", 2);
            String label = parts[0].trim();
            String value = parts.length > 1 ? parts[1].trim() : "";

            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(label);
            row.createCell(1).setCellValue(value);
        }

        // Dosyayı kaydet
        try (FileOutputStream fileOut = new FileOutputStream(excelPath)) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Bilgiler Excel dosyasına başarıyla yazıldı.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Driver kapat
        Driver.quitDriver();
    }
}
