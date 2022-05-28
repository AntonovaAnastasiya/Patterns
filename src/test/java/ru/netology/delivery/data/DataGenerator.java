package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import lombok.Value;
import java.time.LocalDate;
import java.util.Locale;
import java.time.format.DateTimeFormatter;


@UtilityClass
public class DataGenerator {

    private final static Faker faker = new Faker(new Locale("ru"));

    public static String generateDate(int days) {
        String data = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return data;
    }

    public static String generateCity() {
        String city = faker.address().city();
        return city;
    }

    public static String generateName() {
        String name =faker.name().fullName();
        return name;
    }

    public static String generatePhone() {
        String phone = faker.phoneNumber().subscriberNumber(11);
        return phone;
    }


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




