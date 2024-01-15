package Pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

/**
 * Страница "Настройки" сайта auto.ru
 */
public class SettingsPage {
    private final SelenideElement aboutMeTextAreaElement = $x("//textarea");
    private final SelenideElement birthdateInputElement = $x("//label[contains(@class, 'MyProfile__birthday')]//input");
    private final SelenideElement saveButtonElement = $x("//button[contains(@class, 'MyProfile__save')]");

    /**
     * Получение указанной даты рождения
     * @return Дата рождения из вкладки "Настройки"
     */
    public String getBirthdate() {
        return birthdateInputElement.getValue();
    }

    /**
     * Получение информации из поля "Обо мне"
     * @return "Обо мне" из вкладки "Настройки"
     */
    public String getAboutMe() {
        return aboutMeTextAreaElement.getValue();
    }

    public SettingsPage setBirthdate(String birthdate) {
        birthdateInputElement.clear();
        birthdateInputElement.setValue(birthdate);
        sleep(500);
        return this;
    }

    public SettingsPage setAboutMeText(String text) {
        aboutMeTextAreaElement.clear();
        aboutMeTextAreaElement.setValue(text);
        sleep(500);
        return this;
    }

    public SettingsPage clickSaveButton(){
        saveButtonElement.click();
        refresh();
        return this;
    }
}