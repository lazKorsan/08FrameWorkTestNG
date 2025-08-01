package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class PracticeexpandtestingPage {

    /// ////////////////////////////////////////////
    //<===========olmazsaOlmaz====================>
    public PracticeexpandtestingPage(){
        PageFactory.initElements(Driver.getDriver(),this);
        //<============10 ve 11 satırlar constactırı görünür yapar=========>
    }

    @FindBy(xpath = "//*[@id=\"home-header\"]/div[1]/div/div/a[1]")
    public WebElement testCasesButton ;

    @FindBy(xpath = "//*[@id=\"home-header\"]/div[1]/div/div/a[2]")
    public WebElement ApiTestingButtons ;

    @FindBy(xpath = "//*[@id=\"home-header\"]/div[1]/div/div/a[3]")
    public WebElement XpathTestButtons ;

    @FindBy(xpath = "//input[@id=\"search-input\"]")
    public WebElement searchBox ;

    @FindBy(xpath = "//*[@id=\"examples\"]/div[2]/div[1]/div/div[1]/h3/a")
    public static WebElement oneTimePasswordButtons ;

    @FindBy(xpath = "//*[@id=\"examples\"]/div[3]/div[1]/div/div[1]/h3/a")
    public WebElement browserInformationButtons ;

    @FindBy(xpath = "//*[@id=\"browser-toggle\"]")
    public WebElement showBrowserInformationButtons ;

    @FindBy(xpath = "//*[@id=\"browser-info\"]/table/tbody/tr[1]")
    public WebElement userAgent ;

    @FindBy(xpath = "//*[@id=\"browser-info\"]/table/tbody/tr[2]")
    public WebElement codeName ;

    @FindBy(xpath = "//*[@id=\"browser-info\"]/table/tbody/tr[3]")
    public WebElement name ;

    @FindBy(xpath = "//*[@id=\"browser-info\"]/table/tbody/tr[4]")
    public WebElement cookiesEnabled ;

    @FindBy(xpath = "//*[@id=\"browser-info\"]/table/tbody/tr[1]")
    public WebElement platform ;



    @FindBy(xpath = "//*[@id=\"browser-info\"]/table/tbody/tr")
    public List<WebElement> browserInfoItems;












}
