package Pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

/**
 * Главная страница сайта auto.ru
 */
public class MainPage {
    private final SelenideElement searchInputElement = $x("//div[contains(@class, 'SearchLineSuggest')]//input");
    private final SelenideElement profilePicElement = $x("//img[@class='HeaderUserMenu__userPic']");
    private final SelenideElement loginButtonElement = $x("//span[@class='Button__content']");
    private final SelenideElement settingsButtonElement = $x("//li[@data-id='settings']");
    private final SelenideElement regionSelectElement = $x("//span[@class='GeoSelect__titleShrinker-wjCdV']");
    private final SelenideElement regionInputElement = $x("//div[@class='RichInput GeoSelectPopup']//input[@class = 'TextInput__control']");
    private final SelenideElement suggestedRegionElement = $x("//div[@class='GeoSelectPopup__suggest-item-region']");
    private final ElementsCollection geoSelectPopupButtons = $$x("//div[@class='RichInput GeoSelectPopup']//button"); // в этой коллекции будет 2 кнопки, нам нужна вторая
    private final ElementsCollection addToFavouriteIcons = $$x("//div[contains(@class, 'ButtonFavorite ButtonFavorite_size_s')]"); // иконки сердечек у объявлений
    private final ElementsCollection removeFromFavouriteIcons = $$x("//div[@class='HeaderFavoritesPopupItem']//div[@class='HeaderFavoritesPopupItem__cellActions']//div[contains(@class, 'ButtonFavorite_active')]"); // иконки сердечек у объявлений в избранном
    private final SelenideElement ladaLogoImageElement = $x("//img[@class='IndexSuperMark__image'][1]");
    private final SelenideElement favouritesCountElement = $x("//div[contains(@class, 'HeaderMyLink_type_favorites')]//div");
    private final SelenideElement savedSearchesButtonElement = $x("//div[@class='HeaderMyLink HeaderMyLink_type_searches']");
    private final ElementsCollection savedSearches = $$x("//a[@class='Link SubscriptionItemDesktop__title']");
    private final SelenideElement saveSearchButton = $x("//div[@class='SubscriptionSaveButton__button SubscriptionSaveButton__button_color_blue']");
    private final SelenideElement deleteSearchButton = $x("//span[@class='Link Link_color_red SubscriptionItemDesktop__remove-link']");
    private final SelenideElement logbookButtonElement = $x("//div[@class='ServiceNavigation']//a[6]");
    private final SelenideElement headerButtonElement = $x("//a[@class='Header__logoLink']");

    public MainPage(String url) {
        Selenide.open(url);
        sleep(15000);
    }

    /**
     * Создает экземпляр страницы, и если не выполнена авторизация выполняет ее
     * @param url ссылка на страницу
     * @param email email для входа
     * @param password пароль для входа
     * @return Экземпляр главной страницы с выполненным входом
     */
    public static MainPage OpenWithAuth(String url, String email, String password) {
        MainPage mainPage = new MainPage(url);
        if(mainPage.userNotAuthorized())
            mainPage.performAuth(email, password);
        return mainPage;
    }

    /**
     * Получение указанного региона поиска
     * @return Указанный пользователем регион поиска
     */
    public String getSelectedRegion() { return regionSelectElement.text(); }

    /**
     * Получение количества объявлений во вкладке "Избранное"
     * @return Количество объявлений в избранном, если их нет - 0
     */
    public Integer getFavouritesCount() {
        if(!favouritesCountElement.text().matches(".*\\d"))
            return 0;

        return Integer.parseInt(favouritesCountElement.text().split(" ")[2]);
    }

    public Integer getSavedSearchesCount() {
        headerButtonElement.click();
        savedSearchesButtonElement.click();
        return savedSearches.size();
    }


    /**
     * Проверка авторизации пользователя
     * @return true, если вход не выполнен, false в противном случае
     */
    public boolean userNotAuthorized() {
        return loginButtonElement.isDisplayed();
    }

    /**
     * Вход в аккаунт с помощью Яндекс ID
     */
    public void performAuth(String email, String password){
        clickOnLogInButton().clickOnYandexID().enterEmail(email).enterPassword(password);
    }

    /**
     * Нажатие на кнопку "Войти"
     */
    public LogInPage clickOnLogInButton() {
        loginButtonElement.click();
        return new LogInPage();
    }

    /**
     * Открытие выпадающего списка по наведению на аватарку пользователя и выбор пункта "Настройки"
     * @return Экемпляр класса Pages.SettingsPage с логикой пункта "Настройки"
     */
    public SettingsPage clickOnSettingsButton() {
        profilePicElement.hover();
        sleep(1000);
        settingsButtonElement.click();
        return new SettingsPage();
    }

    /**
     * Выбор региона с именем regName
     */
    public MainPage selectRegion(String regName) {
        regionSelectElement.click();
        regionInputElement.setValue(regName);
        sleep(1000);
        suggestedRegionElement.click();
        sleep(1000);
        geoSelectPopupButtons.last().click();
        return this;
    }

    /**
     * Выполнение поискового запроса query
     * @param query поисковый запрос
     * @return Страница с результатами поиска
     */
    public SearchPage execQuery(String query) {
        searchInputElement.setValue(query);
        sleep(1000);
        searchInputElement.sendKeys(Keys.ENTER);
        return new SearchPage();
    }

    /**
     * Добавляет случайное объявление в избранное
     * @return Экземпляр главной страницы
     */
    public MainPage addRandomAdToFavourites() {
        sleep(2000);
        int randomIndex = (int)(Math.random() * (addToFavouriteIcons.size() - 1));
        addToFavouriteIcons.get(randomIndex).hover();
        addToFavouriteIcons.get(randomIndex).click();
        return this;
    }

    /**
     * Удаляет случайное объявление из избранного, если их нет, то ничего не происходит
     * @return Экземпляр главной страницы
     */
    public MainPage removeRandomAdFromFavourites() {
        int randomIndex = (int)(Math.random() * (removeFromFavouriteIcons.size() - 1));
        favouritesCountElement.click();
        removeFromFavouriteIcons.get(randomIndex).hover();
        removeFromFavouriteIcons.get(randomIndex).click();
        sleep(2000);
        return this;
    }

    /**
     * Открытие вкладики 'Бортжурнал'.
     * @return Экземпляр класса страницы 'Бортжурнал'
     */
    public LogbookPage openLogbook() {
        logbookButtonElement.click();
        return new LogbookPage();
    }

    public MainPage saveSearch() {
        ladaLogoImageElement.click();
        sleep(1000);
        saveSearchButton.click();
        return this;
    }

    public MainPage deleteSearch() {
        savedSearchesButtonElement.click();
        sleep(2000);
        deleteSearchButton.click();
        return this;
    }
}
