package miniQuestionnaires.persistence.layer.repository;

import miniQuestionnaires.persistence.layer.entity.Answer;
import miniQuestionnaires.persistence.layer.entity.AnswerForQuestion;
import miniQuestionnaires.persistence.layer.entity.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepo extends JpaRepository<Answer, Long> {
    List<Answer> findAnswersByQuestionIdAndUserId(Long questionId, Long userId);
}
