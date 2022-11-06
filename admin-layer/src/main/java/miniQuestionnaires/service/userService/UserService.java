package miniQuestionnaires.service.userService;

import miniQuestionnaires.api.data.UserApiData;
import miniQuestionnaires.dto.RegisterUserDto;
import miniQuestionnaires.persistence.layer.entity.Question;
import miniQuestionnaires.persistence.layer.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService extends UserDetailsService {
    /**
     * получение списка всех пользователей, заполнивших анкету
     */
    List<UserApiData> getUsersCompletedQuestionnaire(Long questionnaireId);


    /**
     * получение одного пользователя
     */
    UserApiData getUserById(Long userId);

    /**
     * сохранение в БД нового пользователя
     * @param registerUserDto
     */
    @Transactional
    void saveRegisterUser(RegisterUserDto registerUserDto);

    /**
     * валидация нового пользователя
     * @return String errorMessage
     */
    String validateNewUser(RegisterUserDto registerUserDto);

    /**
     * получение роли пользователя
     * @return String roleTitle
     */
    String getUserRole(User user);

    /**
     * получение одного пользователя
     * @param email
     */
    User getUserByEmail(String email);
}
