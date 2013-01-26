package bowling.tenpin.scorecard.bonus;

import bowling.tenpin.scorecard.frames.Frame;

public class Bonus {

    private BonusType type;
    private Frame frame;
    private boolean fulfilled;
    private int value;

    public Bonus(BonusType type, Frame frame){
        this.type = type;
        this.frame = frame;
        fulfilled = false;
        value = 0;
    }

    public BonusType getType() {
        return type;
    }

    public Frame getFrame() {
        return frame;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public int getValue(){
        return value;
    }

    public void fulfillBonus(int value){
        this.value = value;
        fulfilled = true;
    }
}
