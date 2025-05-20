package model;

import lombok.*;
import com.google.gson.Gson;

/**
 * Класс для хранения данных пользователя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String email;
    private String password;
    private String name;
    private String accessToken;

    private static final Gson GSON = new Gson();

    public String toJson() {
        return GSON.toJson(this);
    }

    public static User fromJson(String json) {
        return GSON.fromJson(json, User.class);
    }
}