package miniQuestionnaires.persistence.layer.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "answers")
public class Answer implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="answer_for_question_id")
    private AnswerForQuestion answerForQuestion;
}
