package ru.netology.data;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryTest {

    String name =faker.name().fullName();
    String phone = faker.phoneNumber().subscriberNumber(11);
    String city = faker.address().city();

    private static Faker faker;

    public static String generateDate(int days) {
        String data = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return data;
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @BeforeAll
    static void setUpAll(){
        faker = new Faker(new Locale("ru"));
    }


    @Test
    void shouldGenerateTestAndRescheduleTheMeeting(){
        String planningDate = generateDate(4);
        Configuration.holdBrowserOpen=true;
        open("http://localhost:9999");
        $("[placeholder=\"Город\"]").setValue(city);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("[name=\"name\"]").setValue(name);
        $("[name=\"phone\"]").setValue("+").setValue(phone);
        $("[data-test-id=\"agreement\"]").click();
        $x("//*[text()=\"Запланировать\"]").click();
        $(withText("Успешно!")).should(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] > .notification__content").shouldHave(exactText("Встреча успешно " +
                "запланирована на " + planningDate));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder=\"Дата встречи\"]").setValue(DataGenerator.forwardDate(8));
        $(withText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__title").shouldHave(exactText("Необходимо подтверждение"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='replan-notification'] .notification__content").shouldHave(text("У вас уже" +
                " запланирована встреча на другую дату. Перепланировать?"));
        $(withText("Перепланировать")).click();
        $("[data-test-id='success-notification'] .notification__title").shouldHave(exactText("Успешно!"))
                .shouldBe(Condition.visible);
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible)
                .shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.forwardDate(8)));
    }

}
