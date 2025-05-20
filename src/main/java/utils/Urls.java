package utils;

/** Базовый URL приложения */
public final class Urls {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public static final String REGISTER_PAGE_URL = BASE_URL + "/register";
    public static final String LOGIN_PAGE_URL = BASE_URL + "/login";
    public static final String PASSWORD_RECOVERY_URL = BASE_URL + "/forgot-password";
    public static final String PERSONAL_ACCOUNT_URL = BASE_URL + "/account/profile";

    public static final String API_BASE_URL = BASE_URL + "/api";

    private Urls() {}
}