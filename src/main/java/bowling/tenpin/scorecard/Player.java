package bowling.tenpin.scorecard;

import bowling.tenpin.scorecard.frames.Frame;
import bowling.tenpin.scorecard.frames.Frames;
import bowling.tenpin.scorecard.frames.FramesImpl;

import java.util.ArrayList;
import java.util.List;

public final class Player{

    private final String name;
    private int score = 0;

    public Player(final String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addScore(final int frameScore){
        score += frameScore;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Player player = (Player) o;

        if (name != null ? !name.equals(player.name) : player.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
