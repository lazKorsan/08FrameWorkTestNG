package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.*;
import java.util.Set;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;

public class ReusableMethods {

    public static void bekle(int saniye){

        try {
            Thread.sleep(saniye*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getStringList(List<WebElement> kaynakList){

        List<String> stringList = new ArrayList<>();

        for ( WebElement eachElement : kaynakList
        ) {

            stringList.add(eachElement.getText());

        }
        return stringList;
    }

    public static void switchWindowByUrl (WebDriver driver, String hedefUrl){

        Set<String> acikWindowlarinWhdSeti = driver.getWindowHandles();
        for (String eachWhd : acikWindowlarinWhdSeti
        ) {
            // once bizim oglanin getirdigi whd ile bir window'a gecis yapalim
            driver.switchTo().window(eachWhd);
            String gecilenSayfaUrl = driver.getCurrentUrl();

            if (gecilenSayfaUrl.equals(hedefUrl)){
                break;
            }
        }
    }

    public static void switchWindowByTitle(WebDriver driver, String hedefTitle){
        Set<String> acikWindowlarinWhdSeti = driver.getWindowHandles();
        for (String eachWhd : acikWindowlarinWhdSeti
        ) {
            // once bizim oglanin getirdigi whd ile bir window'a gecis yapalim
            driver.switchTo().window(eachWhd);
            String gecilenSayfaTitle = driver.getTitle();

            if (gecilenSayfaTitle.equals(hedefTitle)){
                break;
            }
        }
    }

    public static void getFullScreenshot(WebDriver driver, String screenshotIsmi){
        // 1.adim screenshot objesi olusturmak ve deger olarak driver'imizi atamak
        TakesScreenshot tss = (TakesScreenshot) driver;

        // 2.adim screenshot'i kaydedecegimiz File'i olusturun
        File tumSayfaSS = new File("target/ekranGoruntuleri/"+screenshotIsmi+".png");

        // 3.adim screenshot'i alip gecici bir dosyaya kopyalayalim
        File geciciDosya = tss.getScreenshotAs(OutputType.FILE);

        // 4.adim gecici dosyayi, asil kaydetmek istedigimiz dosyaya kopyalayalim
        try {
            FileUtils.copyFile(geciciDosya,tumSayfaSS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void getFullScreenshot(WebDriver driver){
        // dosya isimlerine tarih etiketi ekleyelim
        // ... 240829114023 gibi bir etiket eklemek dosya ismini benzersiz yapar

        LocalDateTime zaman = LocalDateTime.now(); // 2024.08.29T11:42:23:123456
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        String tarihEtiketi = zaman.format(dateTimeFormatter); // 240829114023

        // 1.adim screenshot objesi olusturmak ve deger olarak driver'imizi atamak
        TakesScreenshot tss = (TakesScreenshot) driver;

        // 2.adim screenshot'i kaydedecegimiz File'i olusturun
        File tumSayfaSS = new File("target/ekranGoruntuleri/TumSayfaSS_"+tarihEtiketi+".png");

        // 3.adim screenshot'i alip gecici bir dosyaya kopyalayalim
        File geciciDosya = tss.getScreenshotAs(OutputType.FILE);

        // 4.adim gecici dosyayi, asil kaydetmek istedigimiz dosyaya kopyalayalim
        try {
            FileUtils.copyFile(geciciDosya,tumSayfaSS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void getWebelementScreenshot(WebElement istenenWebelement){

        // tarih etiketi olusturalim
        LocalDateTime zaman = LocalDateTime.now(); // 2024.08.29T11:42:23:123456
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        String tarihEtiketi = zaman.format(dateTimeFormatter); // 240829114023


        // 1.adim kullanacagimiz WebElementi locate edip kaydedin

        // 2.adim kaydedeceginiz dosyayi olusturun
        File webelementSS = new File("target/ekranGoruntuleri/webelementSS_"+tarihEtiketi+".png");

        // 3.adim webelementi kullanarak screenshot'i alip gecici dosyaya kaydedin
        File geciciDosya = istenenWebelement.getScreenshotAs(OutputType.FILE);

        // 4.adim gecici dosyayi asil dosyaya kopyalayalim
        try {
            FileUtils.copyFile(geciciDosya,webelementSS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void getWebelementScreenshot(WebElement istenenWebelement,String resimIsmi){

        // tarih etiketi olusturalim
        LocalDateTime zaman = LocalDateTime.now(); // 2024.08.29T11:42:23:123456
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        String tarihEtiketi = zaman.format(dateTimeFormatter); // 240829114023


        // 1.adim kullanacagimiz WebElementi locate edip kaydedin

        // 2.adim kaydedeceginiz dosyayi olusturun
        File webelementSS = new File("target/ekranGoruntuleri/"+resimIsmi+"_"+tarihEtiketi+".png");

        // 3.adim webelementi kullanarak screenshot'i alip gecici dosyaya kaydedin
        File geciciDosya = istenenWebelement.getScreenshotAs(OutputType.FILE);

        // 4.adim gecici dosyayi asil dosyaya kopyalayalim
        try {
            FileUtils.copyFile(geciciDosya,webelementSS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static List<String> urunAramaSonuclari(WebDriver driver, String... arananKelimeler) {
        List<String> bulunanUrunler = new ArrayList<>();
        for (String urun : arananKelimeler) {
            driver.get("https://www.testotomasyonu.com/");
            WebElement aramaKutusu = driver.findElement(By.id("global-search"));
            aramaKutusu.sendKeys(urun + Keys.ENTER);

            WebElement sonucYazisi = driver.findElement(By.className("product-count-text"));
            String actualSonucYazisi = sonucYazisi.getText();
            String urunSayisiStr = actualSonucYazisi.split(" ")[0].trim();
            try {
                int urunSayisi = Integer.parseInt(urunSayisiStr);
                if (urunSayisi > 0) {
                    bulunanUrunler.add(urun + ": " + urunSayisi + " ürün bulundu");
                } else {
                    bulunanUrunler.add(urun + ": ürün bulunamadı!");
                }
            } catch (NumberFormatException e) {
                bulunanUrunler.add(urun + ": ürün sayısı alınamadı! Gelen metin: " + actualSonucYazisi);
            }
        }
        return bulunanUrunler;
    }

    public static void writeToExcel(String dosyaYolu, int satirNo, int hucreNo, String yazilacakVeri) {
        try {
            // 1. Dosyayı aç
            FileInputStream fileInputStream = new FileInputStream(dosyaYolu);
            Workbook workbook = WorkbookFactory.create(fileInputStream);

            // 2. İstenen sayfayı (sheet), satırı (row) ve hücreyi (cell) al
            // Genellikle ilk sayfa ile çalışırız (index 0)
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(satirNo);
            if (row == null) { // Eğer satır daha önce oluşturulmamışsa, oluştur
                row = sheet.createRow(satirNo);
            }

            Cell cell = row.getCell(hucreNo);
            if (cell == null) { // Eğer hücre daha önce oluşturulmamışsa, oluştur
                cell = row.createCell(hucreNo);
            }

            // 3. Hücreye veriyi yaz
            cell.setCellValue(yazilacakVeri);

            // 4. Değişiklikleri kaydetmek için dosyayı tekrar yaz
            fileInputStream.close(); // Okuma modunu kapat
            FileOutputStream fileOutputStream = new FileOutputStream(dosyaYolu);
            workbook.write(fileOutputStream);

            // 5. Kaynakları serbest bırak
            fileOutputStream.close();
            workbook.close();

            System.out.println("Excel'e başarıyla yazıldı: " + yazilacakVeri);

        } catch (IOException e) {
            System.err.println("Excel dosyasına yazılırken bir hata oluştu.");
            e.printStackTrace();
        }
    }

    // Bu metodu utilities/ReusableMethods.java sınıfınıza ekleyin


    /**
     * Belirtilen Excel dosyasındaki, belirtilen sütunda bulunan tüm verileri
     * bir String listesi olarak döndürür.
     * @param dosyaYolu Excel dosyasının yolu.
     * @param sayfaIsmi Çalışılacak sayfanın adı.
     * @param sutunIndex Verilerin alınacağı sütunun indeksi (0'dan başlar).
     * @return Sütundaki verileri içeren bir List<String>.
     */
    public static List<String> getExcelColumnData(String dosyaYolu, String sayfaIsmi, int sutunIndex) {
        List<String> sutunVerileri = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(dosyaYolu);
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheet(sayfaIsmi);

            int sonSatir = sheet.getLastRowNum();

            for (int i = 0; i <= sonSatir; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Cell cell = row.getCell(sutunIndex);
                    if (cell != null) {
                        // Hücredeki veriyi String olarak alıp listeye ekliyoruz.
                        sutunVerileri.add(cell.toString());
                    } else {
                        sutunVerileri.add(""); // Boş hücreler için listeye boş string ekle
                    }
                }
            }
            workbook.close();
            fileInputStream.close();
        } catch (IOException e) {
            System.err.println("Excel dosyasından veri okunurken hata oluştu.");
            e.printStackTrace();
        }
        return sutunVerileri;
    }


    public static List<String[]> readExcelData(String filePath, String sheetName) {
        List<String[]> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in Excel file");
            }

            int rowCount = sheet.getPhysicalNumberOfRows();
            for (int i = 1; i < rowCount; i++) { // Başlık satırını atlıyoruz
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String mail = getCellValue(row.getCell(0));
                String password = getCellValue(row.getCell(1));

                if (mail != null && password != null) {
                    data.add(new String[]{mail, password});
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file: " + e.getMessage(), e);
        }

        return data;
    }

    public static void writeResultToExcel(String filePath, String sheetName, int rowNum, String result) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in Excel file");
            }

            Row row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }

            Cell cell = row.createCell(2); // 3. sütuna yazıyoruz
            cell.setCellValue(result);

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to Excel file: " + e.getMessage(), e);
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return null;

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            default:
                return null;
        }
    }
}



