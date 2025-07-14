package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.ReusableMethods;

import java.time.Duration;
import java.util.List;

public class MailKontrolTest {

    WebDriver driver;
    String dosyaYolu = "C:\\Users\\Hp\\OneDrive\\Desktop\\innerData\\mailKontrolu.xlsx"; // Doğru yolu belirtin

    @FindBy(xpath = "(//*[text()='Account'])[1]")
    WebElement accountButton;

    @FindBy(id = "email")
    WebElement emailInput;

    @FindBy(id = "password")
    WebElement passwordInput;

    @FindBy(id = "submitlogin")
    WebElement signInButton;

    @FindBy(xpath = "//div[@class='text-center bg-danger p-2 my-2 round']")
    WebElement errorMessage;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup(); // ChromeDriver'ı otomatik yapılandır
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.testotomasyonu.com");
        PageFactory.initElements(driver, this);
    }

    @Test
    public void testMailKontrol() {
        // Excel'den verileri oku
        List<String[]> mailList = ReusableMethods.readExcelData(dosyaYolu, "Sayfa1");

        for (String[] credentials : mailList) {
            String mail = credentials[0];
            String password = credentials[1];

            try {
                // Giriş işlemleri
                accountButton.click();
                emailInput.sendKeys(mail);
                passwordInput.sendKeys(password);
                signInButton.click();

                // Hata mesajını kontrol et
                if (errorMessage.isDisplayed() && errorMessage.getText().contains("Customer not found!")) {
                    ReusableMethods.writeResultToExcel(dosyaYolu, "Sayfa1", mailList.indexOf(credentials) + 1, "Customer not found!");
                }

                // Dinamik bekleme (opsiyonel)
                new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(errorMessage));
            } catch (Exception e) {
                System.out.println("Hata oluştu: " + e.getMessage());
                ReusableMethods.writeResultToExcel(dosyaYolu, "Sayfa1", mailList.indexOf(credentials) + 1, "Error: " + e.getMessage());
            }

            // Tarayıcıyı kapatıp yeniden açmak yerine sayfayı yenilemeyi düşünün
            driver.navigate().refresh();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}