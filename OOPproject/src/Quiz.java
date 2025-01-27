import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private List<Question> questions;
    private int currentQuestionIndex;

    public Quiz() {
        questions = new ArrayList<>();
        currentQuestionIndex = 0;

        questions.add(new Question("Which keyword is used to create a subclass in Java?",
                List.of("extends", "implements", "inherits", "super"), 0));
        questions.add(new Question("What is the size of an int in Java?",
                List.of("8 bytes", "4 bytes", "2 bytes", "1 byte"), 1));
        questions.add(new Question("Which of the following is not a Java primitive type?",
                List.of("int", "String", "boolean", "char"), 1));
        questions.add(new Question("What is the default value of a boolean in Java?",
                List.of("true", "false", "0", "null"), 1));
        questions.add(new Question("Which method is called to start a thread in Java?",
                List.of("init()", "start()", "run()", "execute()"), 1));
    }

    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size();
    }

    public Question getNextQuestion() {
        if (hasNextQuestion()) {
            return questions.get(currentQuestionIndex++);
        }
        return null;
    }

    public int getTotalQuestions() {
        return questions.size();
    }
}
