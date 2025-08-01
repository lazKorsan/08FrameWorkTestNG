package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;

public class Driver {
    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    private Driver() {}

    public static WebDriver getDriver(String browser) {
        if (driverPool.get() == null) {
            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addPreference("layout.css.devPixelsPerPx", "1.0");
                    driverPool.set(new FirefoxDriver(firefoxOptions));
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driverPool.set(new EdgeDriver());
                    break;
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments(
                            "--disable-search-engine-choice-screen",
                            "--force-device-scale-factor=1.0"
                    );
                    driverPool.set(new ChromeDriver(options));
                    break;
            }
            driverPool.get().manage().window().maximize();
            driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        }
        return driverPool.get();
    }

    public static WebDriver getDriver() {
        // HATA DÜZELTME: ConfigReader sınıfı ve metod ismi kontrolü
        String defaultBrowser = ConfigReader.getProperty("browser", "chrome");
        return getDriver(defaultBrowser);
    }

    public static void quitDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }

    public static void closeDriver() {
        // İsteğe bağlı: Sadece mevcut pencereyi kapatmak için
        if (driverPool.get() != null) {
            driverPool.get().close();
        }
    }
}
