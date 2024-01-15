import Pages.MainPage;
import Pages.SettingsPage;
import Pages.UserPage;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static com.codeborne.selenide.Selenide.*;

public class AutoruTests {
    private final static String BASE_URL = "https://auto.ru/";
    private final static String EMAIL = "zhakovmd";
    //region <PASSWORD>
    private final static String PASSWORD = "digitalrazor12";
    //endregion
    private final static String SEARCH_REGION = "Тюмень";
    private final static String EXPECTED_SEARCH_REGION = "Тюмень + 300 км";
    private final static String BIRTH_DATE = "02.12.2003";
    private final static String BIO = "Всем привет! Это описание создано с помощью Selenide.";
    private final static String SEARCH_QUERY = "Лада Гранта от 2018 года автомат";
    private final static String PROFILE_URL = "https://auto.ru/profile/67139692/";

    /**
     * 1. Выбор региона "Тюмень" для поиска объявлений
     */
    @Test
    public void setRegion() {
        Assert.assertEquals(EXPECTED_SEARCH_REGION, new MainPage(BASE_URL).selectRegion(SEARCH_REGION).getSelectedRegion());
    }

    /**
     * 2. Заполнение полей "Дата рождения" и "Обо мне" в настройках профиля.
     */
    @Test
        public void setBio() {
            SettingsPage settingsPage = MainPage.OpenWithAuth(BASE_URL, EMAIL, PASSWORD).clickOnSettingsButton().setBirthdate(BIRTH_DATE).setAboutMeText(BIO).clickSaveButton();
            Assert.assertArrayEquals(new String[] {BIRTH_DATE, BIO},
                    new String[] { settingsPage.getBirthdate(), settingsPage.getAboutMe()} );
    }

    /**
     * 3. Поиск объявлений по пользовательскому запросу
     */
    @Test
    public void searchForAds() {
        HashMap<String, String> expectedResults = new HashMap<String, String>() {{
            put("mark", "VAZ");
            put("model", "GRANTA");
            put("transmission", "AUTOMATIC");
            put("yearFrom", "2018");
        }};
        Assert.assertEquals(expectedResults, new MainPage(BASE_URL).execQuery(SEARCH_QUERY).getAttributes());
    }

    /**
     * 4. Добавление объявления в избранное
     */
    @Test
    public void addToFavourites() {
        MainPage mainPage = MainPage.OpenWithAuth(BASE_URL, EMAIL, PASSWORD);
        Integer beforeFavouritesCount = mainPage.getFavouritesCount();
        Assert.assertEquals(beforeFavouritesCount + 1, (int)mainPage.addRandomAdToFavourites().getFavouritesCount());
    }

    /**
     * 5. Удаление автомобиля из избранного
     */
    @Test
    public void removeFromFavourites() {
        MainPage mainPage = MainPage.OpenWithAuth(BASE_URL, EMAIL, PASSWORD);
        Integer beforeFavouritesCount = mainPage.getFavouritesCount();

        if(beforeFavouritesCount == 0)
            return;

        Assert.assertEquals(beforeFavouritesCount - 1, (int)mainPage.removeRandomAdFromFavourites().getFavouritesCount());
    }

    /**
     * 6. Подписка на пользователя
     */
    @Test
    public void subscribeToUser() {
        Assert.assertEquals("Вы подписаны", MainPage.OpenWithAuth(BASE_URL, EMAIL, PASSWORD).openLogbook().openFirstProfile().subscribeToUser().getSubscriptionStatus());
    }

    /**
     * 7. Отписка от пользователя
     */
    @Test
    public void unsubscribeFromUser() {
        Assert.assertEquals("Подписаться", new UserPage(PROFILE_URL, EMAIL, PASSWORD).subscribeToUser().getSubscriptionStatus());
    }

    /**
     * 8. Сохранение поиска
     */
    @Test
    public void saveSearch() {
        MainPage mainPage = MainPage.OpenWithAuth(BASE_URL, EMAIL, PASSWORD);
        Integer beforeSavedSearchesCount = mainPage.getSavedSearchesCount();
        mainPage.saveSearch();
        sleep(5000);
        Assert.assertEquals(beforeSavedSearchesCount + 1, (int)mainPage.getSavedSearchesCount());
        sleep(5000);
    }

    /**
     * 9. Удаление поиска
     */
    @Test
    public void deleteSearch() {
        MainPage mainPage = MainPage.OpenWithAuth(BASE_URL, EMAIL, PASSWORD);
        Integer beforeSavedSearchesCount = mainPage.getSavedSearchesCount();
        sleep(5000);
        Assert.assertEquals(beforeSavedSearchesCount - 1, (int)mainPage.deleteSearch().getSavedSearchesCount());
        sleep(5000);
    }
}
