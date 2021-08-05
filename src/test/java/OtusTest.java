import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.ByteArrayInputStream;
import java.util.regex.Pattern;


public class OtusTest {

    private Logger logger = LogManager.getLogger(OtusTest.class);
    protected static WebDriver driver;

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Драйвер поднят");
    }

    @After
    public void end() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер погашен");
        }
    }


    /*Откройте сайт https://otus.ru :
    перейти во вкладку "Контакты" и проверить адрес: 125167, г. Москва, Нарышкинская аллея., д. 5, стр. 2, тел. +7 499 938-92-02;
    разверните окно браузера на полный экран(не киоск);
    проверьте title страницы*/
    @Test
    @Step("Check Address")
    @Epic("Otus")
    @Feature("Check Address")
    @Story("Checking adress on contacts page")
    @Description("Some description")
    public void otusContactsTest() throws Exception{
        //читаем конфиг, открываем сайт
        ServerConfig cfg = ConfigFactory.create(ServerConfig.class);
        driver.get(cfg.hostnameOtus());
        logger.info("Сайт открыт");

        //Открываем страницу Контакты
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.ByXPath.xpath("//a[@title='Контакты'][@class='header2_subheader-link']")));
        element.click();
        logger.info("Открыта страница "+driver.getTitle());

        //Считываем и сверяем адрес
        WebElement address = driver.findElement(By.ByXPath.xpath(".//div[text() = 'Адрес']/following-sibling::*"));

        logger.info("Адрес на странице = "+address.getText());
        String addressCheck = "125167, г. Москва, Нарышкинская аллея., д. 5, стр. 2, тел. +7 499 938-92-02";
        Assert.assertEquals(addressCheck,address.getText());

        //Разворачиваем окно браузера и сверяем заголовок
        driver.manage().window().maximize();
        String titleCheck = "Контакты | OTUS";
        Assert.assertEquals(titleCheck,driver.getTitle());
    }


    /*Перейти на сайт теле2 страница https://msk.tele2.ru/shop/number :
    ввести в поле "поиск номера" 97 и начать поиск;
    дождаться появления номеров.*/
  /*  @Test
    @Step("Find phone number")
    @Epic("Tele2")
    @Feature("Find phone number")
    @Story("Finding phone number starting with 97")
    @Description("Some description")
    public void tele2Test() throws Exception{
        //читаем конфиг, открываем страницу
        ServerConfig cfg = ConfigFactory.create(ServerConfig.class);
        driver.get(cfg.hostnameTele2());
        logger.info("Сайт открыт");

        //Ожидаем подгрузки умолчательного пула номеров
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement areaCode = wait.until(ExpectedConditions.visibilityOfElementLocated(By.ByXPath.xpath("//span[@class='area-code']")));

        //сбрасываем страничку если первый из номеров начинается на 97
        while
        (driver.findElement(By.ByXPath.xpath("(//span[@class='area-code'])[1]")).getText().startsWith("97"))
        {driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.ByXPath.xpath("//input[@id='searchNumber']")));}

        //ищем номера, начинающиеся с 97 и ожидаем их вывода на странице
        WebElement element = driver.findElement(By.ByXPath.xpath("//input[@id='searchNumber']"));
        element.sendKeys("97");
        wait.until(ExpectedConditions.textMatches(By.ByXPath.xpath("(//span[@class='area-code'])[1]"), Pattern.compile("97.")));

        Allure.addAttachment("Отображены номера", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        //на всякий случай считываем и логируем код первого номера (действительно ли начинается с 97)
        logger.info(driver.findElement(By.ByXPath.xpath("(//span[@class='area-code'])[1]")).getText());
        }*/


    /*Перейдите на сайт https://otus.ru :
    перейдите на F.A.Q, нажмите на вопрос: "Где посмотреть программу интересующего курса?";
    проверьте, что текст соответствует следующему:
    "Программу курса в сжатом виде можно увидеть на странице курса после блока с преподавателями.
    Подробную программу курса можно скачать кликнув на “Скачать подробную программу курса”.*/
   @Test
   @Step("Check course program112233")
   @Epic("Otus")
   @Feature("Check course program")
   @Story("Checking course program description on FAQ page")
   @Description("Some description")
    public void otusFAQTest() throws Exception{
        //читаем конфиг, открываем сайт
        ServerConfig cfg = ConfigFactory.create(ServerConfig.class);
        driver.get(cfg.hostnameOtus());
        logger.info("Сайт открыт");

        //Открываем страницу FAQ
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.ByXPath.xpath("//a[@title='FAQ'][@class='header2_subheader-link']")));
        element.click();
        logger.info("Открыта страница "+driver.getTitle());

        //Считываем и сверяем текст
        WebElement question = driver.findElement(By.ByXPath.xpath(".//div[text() = 'Где посмотреть программу интересующего курса?']"));
        question.click();
        String textCheck = "Программу курса в сжатом виде можно увидеть на странице курса после блока с преподавателями. Подробную программу курса можно скачать кликнув на “Скачать подробную программу курса”";
        String text = driver.findElement(By.ByXPath.xpath(".//div[text() = 'Где посмотреть программу интересующего курса?']/following-sibling::*")).getText();
        logger.info("Считан текст: "+text);
        logger.info("Ожидался текст "+textCheck);
        Assert.assertEquals(textCheck,text);
    }


       /* Зайдите на сайт https://otus.ru :
        заполните тестовый почтовый ящик в поле "Подпишитесь на наши новости";
        нажмите кнопку "Подписаться";
        проверьте, что появилось сообщение: "Вы успешно подписались".*/
   @Test
   @Step("Test Subcription")
   @Epic("Otus")
   @Feature("Test Subcription")
   @Story("Trying to subcribe a test user")
   @Description("Some description")
    public void otusSubscribeTest() throws Exception{
          //читаем конфиг, открываем сайт
        ServerConfig cfg = ConfigFactory.create(ServerConfig.class);
        driver.get(cfg.hostnameOtus());
        logger.info("Сайт открыт");

        //Ищем элемент ввода email и вводим email
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.ByXPath.xpath("//input[@type='email'][@class='input footer2__subscribe-input']")));
        element.clear();
        element.sendKeys("a@a.a");
        element.sendKeys("TAB");

        //жмём кнопку Подписаться
        driver.findElement(By.ByXPath.xpath("//button[contains(text(),'Подписаться')]")).click();

        //Ожидаем успешного оповещения
        WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.ByXPath.xpath(".//p[@class='subscribe-modal__success']")));

        //Сравниваем с ожидаемым
        String actualText = text.getText();
        String textCheck = "Вы успешно подписались";
        logger.info("Считан текст: "+actualText);
        logger.info("Ожидался текст "+textCheck);
        Assert.assertEquals(textCheck,actualText);
    }

}
