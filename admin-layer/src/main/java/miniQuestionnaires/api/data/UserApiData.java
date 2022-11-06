package miniQuestionnaires.api.data;

import lombok.Data;

@Data
public class UserApiData {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String patronymic;
}
