import org.junit.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class SelenideTest {

    @Test
    public void testGoogle(){
        open("https://www.google.ru/");
        sleep(1000);
        $x("//textarea[@name='q']").setValue("Жаков Матвей Дмитриевич").pressEnter();
        sleep(3000);
    }

}