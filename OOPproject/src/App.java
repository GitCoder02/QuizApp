import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.text.Text;

public class App extends Application {

    private Player player;
    private Quiz quiz;
    private Question currentQuestion;
    private int currentAnswerIndex;
    private int timeRemaining;
    private Thread timerThread;
    private boolean timerRunning = false;

    private Label timerLabel;
    private Label questionLabel;
    private RadioButton[] optionButtons;
    private Button nextButton;
    private Button submitButton;

    @Override
    public void start(Stage primaryStage) {
        quiz = new Quiz();

        TextField nameField = new TextField();
        TextField regNoField = new TextField();
        Button startQuizButton = new Button("Start Quiz");

        VBox playerInfoLayout = new VBox(10, new Label("Enter Name:"), nameField,
                new Label("Enter Registration Number:"), regNoField, startQuizButton);
        playerInfoLayout.setAlignment(Pos.CENTER);
        Scene playerInfoScene = new Scene(playerInfoLayout, 400, 300);

        startQuizButton.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                String regNo = regNoField.getText().trim();
                
                if (name.isEmpty() || regNo.isEmpty()) {
                    throw new IllegalArgumentException("Name and Registration Number cannot be empty.");
                }
        
                player = new Player(name, regNo);
                showQuizScreen(primaryStage);
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });
        
        primaryStage.setScene(playerInfoScene);
        primaryStage.setTitle("Quiz Application");
        primaryStage.show();
    }

    private void showQuizScreen(Stage stage) {
        questionLabel = new Label();
        timerLabel = new Label("Time left: 20");

        optionButtons = new RadioButton[4];
        ToggleGroup optionsGroup = new ToggleGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new RadioButton();
            optionButtons[i].setToggleGroup(optionsGroup);
        }

        nextButton = new Button("Next");
        submitButton = new Button("Submit Quiz");

        nextButton.setOnAction(e -> {
            if (checkAnswer()) {
                player.increaseScore();  // Increase score immediately when the answer is correct
            }
            loadNextQuestion(); // Load the next question right after checking the answer
        });
        
        submitButton.setOnAction(e -> showResults(stage));

        VBox questionLayout = new VBox(10, questionLabel, optionButtons[0], optionButtons[1],
                optionButtons[2], optionButtons[3], timerLabel, nextButton, submitButton);
        questionLayout.setAlignment(Pos.CENTER);
        Scene quizScene = new Scene(questionLayout, 500, 400);

        stage.setScene(quizScene);
        loadNextQuestion();
    }

    private void loadNextQuestion() {
        if (quiz.hasNextQuestion()) {
            currentQuestion = quiz.getNextQuestion();
            questionLabel.setText(currentQuestion.getQuestionText());
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(currentQuestion.getOptions().get(i));
                optionButtons[i].setSelected(false);
            }
            startTimer();
        } else {
            nextButton.setDisable(true);
        }
    }

    private void startTimer() {
        // Stop the current timer if running
        stopTimer();
        timeRemaining = 20;
        timerLabel.setText("Time left: 20");
        timerRunning = true;

        // Start a new timer thread
        timerThread = new Thread(() -> {
            while (timeRemaining > 0 && timerRunning) {
                try {
                    Thread.sleep(1000); // Sleep for 1 second
                    timeRemaining--;

                    // Update the timer label on the JavaFX Application Thread
                    Platform.runLater(() -> timerLabel.setText("Time left: " + timeRemaining));
                } catch (InterruptedException ex) {
                    System.err.println("Timer thread interrupted: " + ex.getMessage());
                    Thread.currentThread().interrupt(); // Restore interrupt status
                }
            }

            // If the timer reaches 0, check the answer and load the next question automatically
            if (timeRemaining == 0 && timerRunning) {
                Platform.runLater(() -> {
                    if (checkAnswer()) {
                        player.increaseScore();
                    }
                    loadNextQuestion();
                });
            }
        });

        timerThread.setDaemon(true); // Set as daemon to terminate when the application exits
        timerThread.start();
    }

    private void stopTimer() {
        timerRunning = false;
        if (timerThread != null && timerThread.isAlive()) {
            timerThread.interrupt(); 
        }
    }

    private boolean checkAnswer() {
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected() && currentQuestion.isCorrectAnswer(i)) {
                return true;  // Return true if the selected answer is correct
            }
        }
        return false;
    }
    

    private void showResults(Stage stage) {
        stopTimer(); // Stop the timer when the quiz is complete

        VBox resultsLayout = new VBox(10, new Text("Quiz Complete"),
                new Text("Name: " + player.getName()),
                new Text("Registration No: " + player.getRegistrationNumber()),
                new Text("Score: " + player.getScore() + " out of " + quiz.getTotalQuestions()));
        resultsLayout.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(resultsLayout, 400, 300));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
