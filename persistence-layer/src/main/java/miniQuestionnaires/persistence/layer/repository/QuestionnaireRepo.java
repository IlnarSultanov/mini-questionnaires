package miniQuestionnaires.persistence.layer.repository;

import miniQuestionnaires.persistence.layer.entity.Questionnaire;
import miniQuestionnaires.persistence.layer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionnaireRepo extends JpaRepository<Questionnaire, Long> {
    List<Questionnaire> findAll();
    Optional<Questionnaire> findQuestionnaireByTitle(String title);

    @Query("SELECT DISTINCT questionnaire FROM Questionnaire questionnaire " +
            "JOIN Question question ON questionnaire.id = question.questionnaire.id " +
            "JOIN Answer answer ON question.id = answer.question.id " +
            "WHERE answer.user.id = :userId")
    List<Questionnaire> findCompletedQuestionnairesByUser(@Param("userId") Long userId);
}
