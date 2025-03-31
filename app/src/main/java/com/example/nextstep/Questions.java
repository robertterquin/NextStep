package com.example.nextstep;

public class Questions {  // Renamed class from Questions to Question
    private String questionText;
    private int questionNumber;
    private int progress;

    // Constructor
    public Questions(String questionText, int questionNumber, int progress) {
        this.questionText = questionText;
        this.questionNumber = questionNumber;
        this.progress = progress;
    }

    // Getter and Setter for Question Text
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    // Getter and Setter for Question Number
    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    // Getter and Setter for Progress
    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
