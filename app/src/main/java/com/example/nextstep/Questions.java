package com.example.nextstep;

public class Questions {
    private String questionText;
    private int questionNumber;
    private int progress;


    public Questions(String questionText, int questionNumber, int progress) {
        this.questionText = questionText;
        this.questionNumber = questionNumber;
        this.progress = progress;
    }


    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }


    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }


    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
