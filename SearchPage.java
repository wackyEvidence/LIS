package Pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$x;

public class SearchPage {
    private final SelenideElement carMarkInputElement = $x("//input[@name='mark']");
    private final SelenideElement carModelInputElement = $x("//input[@name='model']");
    private final SelenideElement carTransmissionInputElement = $x("//input[@name='transmission']");
    private final SelenideElement yearFromInputElement = $x("//input[@name='year_from']");

    public String getMark() {
        return carMarkInputElement.getValue();
    }

    public String getModel() {
        return carModelInputElement.getValue();
    }

    public String getTrasmissionType() {
        return carTransmissionInputElement.getValue();
    }

    public String getYearFrom() {
        return yearFromInputElement.getValue();
    }

    public Map<String, String> getAttributes() {
        return new HashMap<String, String>() {{
            put("model", getModel());
            put("mark", getMark());
            put("transmission", getTrasmissionType());
            put("yearFrom", getYearFrom());
        }};
    }
}
