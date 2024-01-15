package Pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class UserPage {
    private final SelenideElement subscribeButtonElement = $x("//div[@class='PublicProfile__buttons']//button");
    private final SelenideElement loginButtonElement = $x("//span[@class='Button__content']");
    public UserPage() { }

    public UserPage(String profileUrl, String email, String password) {
        Selenide.open(profileUrl);
        sleep(15000);
        performAuth(email, password);
    }

    /**
     * Вход в аккаунт с помощью Яндекс ID
     */
    public void performAuth(String email, String password){
        clickOnLogInButton().clickOnYandexID().enterEmail(email).enterPassword(password);
    }

    public LogInPage clickOnLogInButton() {
        loginButtonElement.click();
        return new LogInPage();
    }

    /**
     * Подписывается (или отписывается от пользователя, если подписка уже оформлена), профиль которого открыт на экране
     * @return Экземпляр страницы профиля пользователя
     */
    public UserPage subscribeToUser() {
        sleep(1000);
        subscribeButtonElement.click();
        return this;
    }

    /**
     * Получение статуса подписки на пользователя
     * @return "Подписаться", если подписка не оформлена, "Вы подписаны" в противном случае
     */
    public String getSubscriptionStatus() {
        sleep(1000);
        return subscribeButtonElement.text();
    }
}
