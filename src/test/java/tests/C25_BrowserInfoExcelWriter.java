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

public class C25_BrowserInfoExcelWriter {

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

        // Bilgileri DOM'dan al
        List<WebElement> infoItems = practiceexpandtestingPage.browserInfoItems;

        int rowIndex = 1; // Excel için satır indexi (0 başlık satırı)
        for (WebElement item : infoItems) {
            String fullText = item.getText();  // tüm bilgiler tek parça olabilir
            String[] lines = fullText.split("\\r?\\n"); // Satır satır böl

            for (String line : lines) {
                String[] parts = line.split(":", 2);
                String label = parts[0].trim();
                String value = parts.length > 1 ? parts[1].trim() : "";

                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(label);
                row.createCell(1).setCellValue(value);
            }
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
