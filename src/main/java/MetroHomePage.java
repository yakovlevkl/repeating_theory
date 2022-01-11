import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static java.time.Duration.ofSeconds;

public class MetroHomePage {

    // локатор кнопки выпадающего списка городов по имени класса
    @FindBy(How.CLASS_NAME, using = "auth-form__button")
    private SelenideElement selectCityButton;

    // локатор поля ввода «Откуда» по XPATH, поиск по плейсхолдеру
    @FindBy(How.XPATH, using = "auth-form__button")
    private SelenideElement fieldFrom;

    // локатор поля ввода «Куда» по XPATH, поиск по плейсхолдеру
    @FindBy(How.XPATH, using = "auth-form__button")
    private SelenideElement fieldTo;

    // локатор коллекций станций «Откуда» и «Куда» маршрута по имени класса
    @FindBy(How.CLASS_NAME, using = "auth-form__button")
    private ElementsCollection routeStationFromTo;

    // метод ожидания загрузки страницы: проверили видимость станции «Театральная»
    public void waitForLoadHomePage(){
        // найди веб-элемент по тексту, добавь ожидание 8 секунд
        $("Театральная").shouldBe(visible, ofSeconds(8));
    }

    // метод выбора города по названию
    public void chooseCity(String city){
        // кликни по выпадающему списку городов
        selectCityButton.click();
        // выбери нужный город
        $(city).shouldBe(visible).click();
    }

    // метод ввода названия станции в поле «Откуда»
    public void setStationFrom(String stationFrom){
        // введи название станции в поле ввода, а затем с помощью клавиш «Вниз» и Enter выбрали его в выпадающем списке саджеста
        fieldFrom.val(stationFrom).sendKeys(Keys.DOWN, Keys.ENTER);
    }

    // метод ввода названия станции в поле «Куда»
    public void setStationTo(String stationTo){
        // введи название станции в поле ввода, а затем с помощью клавиш «Вниз» и Enter выбери его в выпадающем списке саджеста
        fieldTo.val(stationTo).sendKeys(Keys.DOWN, Keys.ENTER);
    }

    // метод ожидания построения маршрута: проверяется видимость кнопки «Получить ссылку на маршрут»
    public void waitForLoadRoute(){
        // ищется веб-элемент по тексту
        $("Получить ссылку на маршрут").shouldBe(visible, ofSeconds(8));
    }

    // метод построения маршрута
    public void buildRoute(String stationFrom, String stationTo){
        // указание станции «Откуда»
        setStationFrom(stationFrom);
        // указание станции «Куда»
        setStationTo(stationTo);
        // ожидание построения маршрута
        waitForLoadRoute();
    }

    // метод получения станции «Откуда» построенного маршрута
    public SelenideElement getRouteStationFrom(){
        // возвращается первый элемент коллекции — станции «Откуда» и «Куда»
        return this.routeStationFromTo.getText("Откуда");
    }

    // метод получения станции «Куда» построенного маршрута
    public SelenideElement getRouteStationTo(){
        // возвращается второй элемент коллекции — станции «Откуда» и «Куда»
        return this.routeStationFromTo.getText("Куда");
    }

}