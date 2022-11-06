package miniQuestionnaires.persistence.layer.repository;

import miniQuestionnaires.persistence.layer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    @Query("SELECT DISTINCT answer.user FROM Questionnaire questionnaire " +
            "JOIN Question question ON questionnaire.id = question.questionnaire.id " +
            "JOIN Answer answer ON question.id = answer.question.id " +
            "WHERE questionnaire.id = :questionnaireId")
    List<User> findUsersByQuestionAndQuestionnaire(@Param("questionnaireId") Long questionnaireId);
}
