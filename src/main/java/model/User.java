package model;

import lombok.*;

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
}