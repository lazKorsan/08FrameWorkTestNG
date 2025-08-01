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

public class C26_BrowserInfoExcelWriter {

    public static void main(String[] args) throws IOException {
        // Sayfaya git
        Driver.getDriver().get(ConfigReader.getProperty("dinamikXpathUrl"));

        // Sayfadaki butona tıkla
        PracticeexpandtestingPage practiceexpandtestingPage = new PracticeexpandtestingPage();
        practiceexpandtestingPage.showBrowserInformationButtons.click();

        // Excel dosyası oluştur
        String excelPath = System.getProperty("user.home") + "\\OneDrive\\Desktop\\PracticeExpandtesting\\webtablo.xlsx";
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
            String fullText = item.getText();
            System.out.println("Item text: " + fullText); // Debugging için
            if (fullText == null || fullText.trim().isEmpty()) {
                continue; // Boş veya null metinleri atla
            }
            String[] parts = fullText.split(":", 2);
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(parts[0].trim());
            row.createCell(1).setCellValue(parts.length > 1 ? parts[1].trim() : "");
        }

        // Dosyayı kaydet
        try (FileOutputStream fileOut = new FileOutputStream(excelPath)) {
            workbook.write(fileOut);
            System.out.println("Bilgiler Excel dosyasına başarıyla yazıldı: " + excelPath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }

        // Driver kapat
        Driver.quitDriver();
    }
}