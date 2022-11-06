package miniQuestionnaires.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.sql.Date;

@Data
public class RegisterUserDto {
    @Email
    private String email;

    @NotEmpty(message = "Поле пароль не может быть пустым")
    @Size(min = 6, max = 30, message = "Длина пароля должна быть от 6 до 30 символов")
    private String password;

    @NotEmpty(message = "Поле фамилия не может быть пустым")
    @Size(min = 5, max = 25, message = "Фамилия должна быть от 5 до 25 символов")
    private String surname;

    @NotEmpty(message = "Поле имя не может быть пустым")
    @Size(min = 3, max = 15, message = "Имя должно быть от 3 до 15 символов")
    private String name;

    @NotEmpty(message = "Поле отчество не может быть пустым")
    @Size(min = 5, max = 25, message = "Отчество должно быть от 5 до 25 символов")
    private String patronymic;
}
