package Pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * Страница входа сайта auto.ru
 */
public class LogInPage {
    private final SelenideElement yandexIdButtonElement = $x("//a[@id='yandex']");
    private final SelenideElement emailInputElement =  $x("//input[@id='passp-field-login']");
    private final SelenideElement passwordInputElement =  $x("//input[@id='passp-field-passwd']");
    private final SelenideElement submitButtonElement = $x("//button[@id='passp:sign-in']");

    /**
     * Нажатие на кнопку входа с помощью Яндекс ID
     */
    public LogInPage clickOnYandexID() {
        sleep(1000);
        yandexIdButtonElement.click();
        sleep(1000);
        return this;
    }

    /**
     * Ввод почты в форме входа и нажатие на кнопку "Продолжить"
     */
    public LogInPage enterEmail(String email) {
        sleep(1000);
        emailInputElement.setValue(email);
        sleep(1000);
        submitButtonElement.click();
        return this;
    }

    /**
     * Ввод пароля в форме входа и нажатие на кнопку "Войти"
     */
    public void enterPassword(String password) {
        sleep(1000);
        passwordInputElement.setValue(password);
        sleep(1000);
        submitButtonElement.click();
    }
}
