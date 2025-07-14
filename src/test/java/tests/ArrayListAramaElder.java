package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ReusableMethods; // utilities paketini import ediyoruz
import utilities.YenidenKullanilabilirMetotlar;

import java.time.Duration;
import java.util.List;

public class ArrayListAramaElder {
    WebDriver driver;
    // Yeniden kullanılabilir metotları içeren sınıfımızdan bir nesne oluşturuyoruz.
    YenidenKullanilabilirMetotlar reusableMetotlar;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Nesneyi setup metodu içinde initialize ediyoruz.
        reusableMetotlar = new YenidenKullanilabilirMetotlar();
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Bu DataProvider, testimize aranacak ürünlerin listesini sağlar.
     * Test metodu bu listedeki her bir ürün için bir kez çalışacaktır.
     */
    @DataProvider(name = "aranacakUrunListesi")
    public Object[][] urunListesi() {
        return new Object[][] {
                {"phone"},
                {"dress"},
                {"shoes"},
                {"laptop"}
                // Buraya test etmek istediğiniz diğer ürünleri ekleyebilirsiniz
        };
    }

    /**
     * Bu test metodu, DataProvider'dan aldığı her bir ürünü
     * reusable metodumuzu kullanarak arar ve doğrular.
     * @param aranacakUrun DataProvider'dan gelen ürün adı
     */
    @Test(dataProvider = "aranacakUrunListesi")
    public void urunAramaTesti(String aranacakUrun) {
        // Her testin temiz bir sayfada başlaması için anasayfaya gidiyoruz.
        driver.get("https://www.testotomasyonu.com/");

        // Reusable sınıfımızdaki metodu çağırarak arama işlemini gerçekleştiriyoruz.
        reusableMetotlar.urunAraVeDogrula(driver, aranacakUrun);
    }

    // YENİ EKLENEN TEST METODU
    /**
     * Bu test, birden fazla ürünü arar ve sonuçları bir liste olarak alıp doğrular.
     * utilities.ReusableMethods içerisindeki static metodu kullanır.
     */
    @Test(dependsOnMethods = "urunAramaTesti") // Bu testin, ilk testten sonra çalışmasını sağlar
    public void aramaSonuclariniListelemeTesti(){
        // 1. utilities.ReusableMethods'daki static metodu çağırarak arama yap ve sonuçları al
        // Metot zaten içinde döngü barındırdığı için ürünleri direkt parametre olarak veriyoruz.
        List<String> aramaSonuclari = ReusableMethods.urunAramaSonuclari(
                driver,
                "phone", "dress", "java", "selenium"
        );

        // 2. Sonuçları konsola yazdırarak görelim
        System.out.println("--- Arama Sonuç Raporu ---");
        for (String sonuc : aramaSonuclari) {
            System.out.println(sonuc);
        }
        System.out.println("--------------------------");


        // 3. Testin başarılı sayılması için bir doğrulama (assertion) ekleyelim.
        // Örneğin, arama sonucu listesinin boş olmadığını doğrulayabiliriz.
        Assert.assertFalse(aramaSonuclari.isEmpty(), "Arama sonuç listesi boş dönmemeliydi.");

        // Ayrıca listede belirli bir sonucun varlığını da kontrol edebiliriz.
        // Örneğin "phone" için ürün bulunduğunu doğrulayalım.
        boolean phoneSonucuVarMi = false;
        for (String sonuc : aramaSonuclari) {
            if (sonuc.startsWith("phone") && sonuc.contains("ürün bulundu")){
                phoneSonucuVarMi = true;
                break;
            }
        }
        Assert.assertTrue(phoneSonucuVarMi, "Phone için arama sonucu bulunamadı.");
    }
}