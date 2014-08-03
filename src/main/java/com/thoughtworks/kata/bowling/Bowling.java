package com.thoughtworks.kata.bowling;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Bowling {
    private List<Integer> scores;
    private List<Integer> roundScores;

    public Bowling() {
        scores = newArrayList();
        roundScores = newArrayList();
    }

    public void hit(int score) {
        scores.add(score);
    }

    public int getScore() {
        calculateEachRound();
        return calculateAll();
    }

    private void calculateEachRound() {
        for (int i = 0; i < scores.size(); ) {
            if (isLastRound()) {
                roundScores.add(getHitScore(i) + getHitScore(i + 1) + getHitScore(i + 2));
                return;
            }
            roundScores.add(getRoundScore(i));
            if (isStrike(i)) {
                i++;
            } else {
                i += 2;
            }
        }
    }

    private int getRoundScore(int i) {
        if (isSpare(i)) {
            return 10 + getHitScore(i + 2);
        }
        if (isStrike(i)) {
            return 10 + getHitScore(i + 1) + getHitScore(i + 2);
        }
        return getHitScore(i) + getHitScore(i + 1);
    }

    private boolean isLastRound() {
        return roundScores.size() == 9;
    }

    private boolean isStrike(int index) {
        return getHitScore(index) == 10;
    }

    private boolean isSpare(int roundStartIndex) {
        return getHitScore(roundStartIndex) + getHitScore(roundStartIndex + 1) == 10;
    }

    private int getHitScore(int index) {
        return index < scores.size() ? scores.get(index) : 0;
    }

    private int calculateAll() {
        int sum = 0;
        for (Integer roundScore : roundScores) {
            sum += roundScore;
        }
        return sum;
    }
}
