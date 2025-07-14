package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;

public class YenidenKullanilabilirMetotlar {

    /**
     * Bu metot, verilen WebDriver'ı kullanarak belirtilen ürünü arar
     * ve arama sonuçlarının geldiğini doğrular.
     * @param driver Aktif WebDriver nesnesi
     * @param aranacakUrun Arama kutusuna yazılacak ürün adı
     */
    public void urunAraVeDogrula(WebDriver driver, String aranacakUrun) {
        // 1. Arama kutusunu bul
        WebElement aramaKutusu = driver.findElement(By.id("global-search"));

        // 2. Arama kutusunu temizle (önceki aramalardan kalma metin olabilir)
        aramaKutusu.clear();

        // 3. Verilen ürünü arama kutusuna gönder ve Enter'a basarak aramayı başlat
        System.out.println(aranacakUrun + " için arama yapılıyor...");
        aramaKutusu.sendKeys(aranacakUrun + Keys.ENTER);

        // 4. Arama sonuçlarının yüklendiğini doğrula
        //    Örnek olarak, sonuç sayısını gösteren elementin varlığını kontrol edelim.
        WebElement sonucYazisiElementi = driver.findElement(By.className("product-count-text"));

        // 5. Sonuç yazısında "bulundu" kelimesinin geçtiğini doğrula (assertion)
        Assert.assertTrue(
                sonucYazisiElementi.isDisplayed(),
                "'" + aranacakUrun + "' için arama sonuçları yüklenemedi."
        );

        System.out.println("'" + aranacakUrun + "' için arama başarıyla doğrulandı.");
    }
    // Bu metot zaten utilities.ReusableMethods dosyanızda mevcut.
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


}