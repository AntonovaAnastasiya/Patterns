package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import java.time.LocalDate;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import ru.netology.delivery.data.RegistrationInfo;

@UtilityClass
public class DataGenerator {


    @UtilityClass
    public static class Registration {

        public static RegistrationInfo generateInfo(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new RegistrationInfo(faker.name().fullName(), faker.phoneNumber().phoneNumber(), faker.address().city());
        }

    }

    public static String forwardDate(int plusDays) {
        LocalDate today = LocalDate.now();
        LocalDate newDate = today.plusDays(plusDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return formatter.format(newDate);

    }

}





