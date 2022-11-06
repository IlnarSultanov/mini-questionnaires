package miniQuestionnaires.persistence.layer.repository;


import miniQuestionnaires.persistence.layer.entity.AnswerForQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerForQuestionRepo extends JpaRepository<AnswerForQuestion, Long> {
    List<AnswerForQuestion> findAllByQuestionId(Long questionId);
}
