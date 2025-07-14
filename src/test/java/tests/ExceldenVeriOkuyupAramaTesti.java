package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ReusableMethods; // Merkezi utilities paketini kullanıyoruz

import java.time.Duration;
import java.util.List;

public class ExceldenVeriOkuyupAramaTesti {

    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void exceldenAramaTesti() {
        // 1. Gerekli dosya yolu ve sayfa bilgilerini hazırlayalım
        // DOSYA YOLU DÜZELTİLDİ: System.getProperty("user.home") ile ana dizin alınır.

        String dosyaYolu = System.getProperty("user.home") + "/OneDrive/Desktop/innerData/testgirdisi.xlsx";

        String sayfaAdi = "Sayfa1";
        int aranacakUrunSutunu = 0; // A Sütunu
        int sonucYazilacakSutun = 1; // B Sütunu

        // 2. Excel'den aranacak ürünlerin listesini alalım
        List<String> aranacakUrunler = ReusableMethods.getExcelColumnData(dosyaYolu, sayfaAdi, aranacakUrunSutunu);

        // İYİLEŞTİRME: Excel'den veri gelip gelmediğini kontrol edelim
        if (aranacakUrunler.size() <= 1) {
            Assert.fail("Excel dosyasında ('" + dosyaYolu + "') okunacak veri bulunamadı veya sadece başlık satırı var.");
        }

        // Başlık satırını (0. index) atlamak için `subList(1, ...)` kullanıyoruz.
        List<String> testEdilecekListe = aranacakUrunler.subList(1, aranacakUrunler.size());

        // 3. Ürün listesindeki her bir eleman için arama yapıp sonucu Excel'e yazalım
        for (int i = 0; i < testEdilecekListe.size(); i++) {
            String aranacakUrun = testEdilecekListe.get(i);

            // İYİLEŞTİRME: Excel'deki boş satırları atlamak için kontrol
            if (aranacakUrun == null || aranacakUrun.trim().isEmpty()) {
                System.out.println((i+1) + ". satır boş olduğu için atlanıyor.");
                continue; // Döngünün bu adımını atla ve sonrakine geç
            }

            // a. Ana sayfaya git
            driver.get("https://www.testotomasyonu.com/");

            // b. Arama kutusuna ürünü yaz ve ara
            WebElement aramaKutusu = driver.findElement(By.id("global-search"));
            aramaKutusu.clear();
            aramaKutusu.sendKeys(aranacakUrun + Keys.ENTER);

            // c. Arama sonucunu al
            WebElement sonucYazisiElementi = driver.findElement(By.className("product-count-text"));
            String sonucYazisi = sonucYazisiElementi.getText();

            // d. Aldığın sonucu Excel'e geri yaz
            // Başlık satırı olduğu için i+1 diyerek doğru satıra yazıyoruz.
            ReusableMethods.writeToExcel(dosyaYolu, i + 1, sonucYazilacakSutun, sonucYazisi);
            System.out.println("'" + aranacakUrun + "' için sonuç: '" + sonucYazisi + "' Excel'e yazıldı.");
        }
    }
}