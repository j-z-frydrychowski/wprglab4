package com.test.model;

public record Evaluation(int year, int score) {
    public Evaluation {
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("Score must be between 1 and 5");
        }
    }
}
