package Pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;

public class LogbookPage {
    private final SelenideElement logAuthorNickname = $x("//div[@class='LogbookListingSnippetHeading__author-gldDX'][1]");

    /**
     * Открывает профиль первого автора записи на странице с Бортжурналами.
     * @return Экземпляр класса страницы профиля пользователя.
     */
    public UserPage openFirstProfile() {
        logAuthorNickname.click();
        sleep(2000);
        return new UserPage();
    }
}
