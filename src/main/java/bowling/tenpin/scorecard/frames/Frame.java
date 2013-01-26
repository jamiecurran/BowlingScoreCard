package bowling.tenpin.scorecard.frames;

import bowling.tenpin.scorecard.bonus.BonusType;

import static bowling.tenpin.scorecard.bonus.BonusType.SPARE;
import static bowling.tenpin.scorecard.bonus.BonusType.STRIKE;
import static bowling.tenpin.scorecard.bonus.BonusType.NONE;

public class Frame {

    private int round;

    private int firstRoll;
    private int secondRoll;
    private int firstBonusRoll;
    private int secondBonusRoll;
    private int score;

    private BonusType type;
    private boolean lastFrame = false;

    public Frame(int firstRoll, int secondRoll, int round) {
        validateInput(firstRoll, secondRoll);
        this.firstRoll = firstRoll;
        this.secondRoll = secondRoll;
        score = firstRoll + secondRoll;
        this.round = round;
        setFrameType();
    }

    public Frame(int firstRoll, int secondRoll, int round, boolean lastFrame){
        this(firstRoll, secondRoll, round);
        this.lastFrame = lastFrame;
    }

    private void setFrameType() {
        if(firstRoll == 10){
            type = STRIKE;
        } else if(score == 10){
            type = SPARE;
        } else {
            type = NONE;
        }
    }

    public int getScore(){
        return score;
    }

    private void validateInput(int firstRoll, int secondRoll) {
        if(firstRoll < 0 || secondRoll < 0){
            throw new IllegalArgumentException("A roll cannot be a negative value");
        }
        if((firstRoll + secondRoll) > 10){
            throw new IllegalStateException("The initial frame score cannot exceed ten");
        }
    }

    public int getFirstRoll() {
        return firstRoll;
    }

    public BonusType getType() {
        return type;
    }

    public void setFirstBonusRoll(int firstBonusRoll) {
        if(firstBonusRoll > 10){
            throw new IllegalArgumentException("Invalid roll. A single roll cannot exceed ten.");
        }
        if(score == 10 && lastFrame){
            this.firstBonusRoll = firstBonusRoll;
            score += firstBonusRoll;
        } else {
            throw new IllegalStateException("Cannot enter bonus rolls if a strike hasn't been rolled for the last frame.");
        }
    }

    public void setSecondBonusRoll(int secondBonusRoll) {
        if(secondBonusRoll > 10){
            throw new IllegalArgumentException("Invalid roll. A single roll cannot exceed ten.");
        }
        if(firstRoll == 10 && firstBonusRoll == 10 && lastFrame){
            this.secondBonusRoll = secondBonusRoll;
            score += secondBonusRoll;
        } else {
            throw new IllegalStateException("Cannot enter second bonus roll if a strike hasn't been rolled for the last frame and first bonus roll.");
        }
    }

    public int getRound() {
        return round;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Frame frame = (Frame) o;

        if (round != frame.round) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return round;
    }

    public boolean isLastFrame() {
        return lastFrame;
    }

    public int getFirstBonusRoll() {
        return firstBonusRoll;
    }

    public int getSecondRoll() {
        return secondRoll;
    }

}
