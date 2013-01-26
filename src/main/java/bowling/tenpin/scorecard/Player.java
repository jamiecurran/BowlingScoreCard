package bowling.tenpin.scorecard;

import bowling.tenpin.scorecard.frames.Frame;
import bowling.tenpin.scorecard.frames.Frames;
import bowling.tenpin.scorecard.frames.FramesImpl;

import java.util.ArrayList;
import java.util.List;

public class Player{

    private String name;
    private int score = 0;

    public Player(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addScore(int frameScore){
        score += frameScore;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (name != null ? !name.equals(player.name) : player.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
