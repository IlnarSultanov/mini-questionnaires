package miniQuestionnaires.api.converter;

import miniQuestionnaires.api.data.UserApiData;
import miniQuestionnaires.persistence.layer.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserApiConverter {

    public UserApiData convertToShowApi(User user) {
        UserApiData userApi = new UserApiData();

        userApi.setId(user.getId());
        userApi.setEmail(user.getEmail());
        userApi.setName(user.getName());
        userApi.setSurname(user.getSurname());
        userApi.setPatronymic(user.getPatronymic());

        return userApi;
    }
}
