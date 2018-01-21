package com.teamthayhung.superbrain;

/**
 * Created by Admin on 20/1/2018.
 */

public class Fight {
    private String id1, id2, score1, score2;

    public Fight() {
    }

    public Fight(String id1, String id2, String score1, String score2) {
        this.id1 = id1;
        this.id2 = id2;
        this.score1 = score1;
        this.score2 = score2;
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }
}
