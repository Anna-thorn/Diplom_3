package utils;

import io.qameta.allure.*;
import net.datafaker.Faker;

import model.User;

/**
 * Генератор тестовых данных пользователя + методы для работы с API.
 */
public class UserGenerator {
    private static final int MIN_PASSWORD_LENGTH = 6;

    private static final Faker faker = new Faker();

    //=== Генерация пользователей ===
    @Step("Создание пользователя с валидными данными")
    public static User createValidUser() {
        return buildUser("test-", faker.internet().password(MIN_PASSWORD_LENGTH, 10));
    }
    @Step("Создание пользователя с коротким паролем (<6 символов)")
    public static User createUserWithShortPassword() {
        return buildUser("test-short-", faker.internet().password(1, MIN_PASSWORD_LENGTH-1));
    }
    @Step("Создание пользователя с пустым паролем")
    public static User createUserWithEmptyPassword() {
        return buildUser("test-empty-", "");
    }

    @Step("Создание случайного пользователя")
    public static User createRandomUser() {
        return new User(
                faker.internet().emailAddress(),
                faker.internet().password(MIN_PASSWORD_LENGTH, 12),
                faker.name().firstName(),
                null
        );
    }

    private static User buildUser(String emailPrefix, String password) {
        return new User(
                emailPrefix + faker.regexify("[a-z0-9]{6}") + "@yandex.ru",
                password,
                "User-" + faker.name().firstName(),
                null
        );
    }
}