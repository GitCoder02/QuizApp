# JavaFX Quiz Application

This JavaFX Quiz Application is a simple interactive quiz program where users can answer Java-related questions. The app captures user details, presents multiple-choice questions, and tracks scores based on correct answers. The application also features a countdown timer and a results screen summarizing the user’s performance.

## Features

1. **Player Information** - Captures the player's name and registration number.
2. **Multiple-Choice Questions** - Presents a series of Java-related questions with four options each.
3. **Score Tracking** - Increases the score if the answer is correct.
4. **Timer for Each Question** - Counts down from 20 seconds for each question.
5. **Result Display** - Shows the final score and player information at the end of the quiz.
6. **Multithreading** - Manages a separate thread for the timer functionality.
7. **Exception Handling** - Validates input fields to prevent empty player details.

## Technology Used

- **JavaFX** - For creating the GUI.
- **Java** - Core language for business logic and UI handling.
- **Multithreading** - For managing the timer independently of the UI.
- **Exception Handling** - To ensure that invalid inputs are handled gracefully.

## Key Components

### 1. Multithreading

- The app uses a separate thread to handle the countdown timer in `startTimer()` method of the `App` class. The timer runs in a loop, reducing the time every second, which allows the user interface to remain responsive.
- The `Platform.runLater()` method is used to update the timer label on the main JavaFX Application Thread, ensuring the UI updates occur without blocking.

### 2. Exception Handling

- **Input Validation**: When starting the quiz, the program checks that both the player's name and registration number fields are not empty. If they are, an `IllegalArgumentException` is thrown, and an error dialog is displayed to the user.
- **Timer Interruptions**: When the quiz completes or moves to the next question, the timer thread is stopped using the `stopTimer()` method, which interrupts the thread to prevent unnecessary CPU usage.

### 3. Classes

- **App**: The main application class that initializes and manages the quiz workflow, user input, questions, and results display.
- **Player**: Stores the player's details such as name, registration number, and score.
- **Quiz**: Manages the collection of questions and keeps track of the current question index.
- **Question**: Holds the question text, options, and the index of the correct answer.

## Screens and Workflow

1. **Player Information Screen**: Users enter their name and registration number. Validation ensures these fields are not empty.
2. **Quiz Screen**: Each question is displayed along with four answer options. A timer counts down from 20 seconds. If the timer reaches zero, the next question is automatically loaded.
3. **Results Screen**: Displays the user's name, registration number, score, and the total number of questions.

