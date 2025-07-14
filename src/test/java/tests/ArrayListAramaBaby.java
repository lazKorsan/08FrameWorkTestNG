package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.YenidenKullanilabilirMetotlar;

import java.time.Duration;

public class ArrayListArama {
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
}