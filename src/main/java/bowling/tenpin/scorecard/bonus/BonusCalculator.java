package bowling.tenpin.scorecard.bonus;

import bowling.tenpin.scorecard.bonus.Bonus;
import bowling.tenpin.scorecard.frames.Frame;

import java.util.List;

public class BonusCalculator {

    public static void calculateBonus(List<Frame> frames, Bonus bonus) {

        int nextFrameIndex = frames.lastIndexOf(bonus.getFrame()) + 1;
        if(nextFrameIndex < frames.size()){
            Frame nextFrame = frames.get(nextFrameIndex);
            if(bonus.getType() == BonusType.STRIKE){
                getBonusFromNextTwoBalls(bonus, frames, nextFrameIndex, nextFrame);
            } else if(bonus.getType() == BonusType.SPARE){
                getBonusFromNextBall(bonus, nextFrame);
            }
        }
    }

    private static void getBonusFromNextTwoBalls(Bonus bonus, List<Frame> frames, int nextFrameIndex, Frame nextFrame) {
        if(nextFrame.getType() == BonusType.STRIKE){
            if(nextFrame.isLastFrame()){
                getLastFrameBonus(bonus, nextFrame);
            }else{
                getDoubleStrikeBonus(bonus, frames, nextFrameIndex, nextFrame);
            }
        } else {
            int ballOne = nextFrame.getFirstRoll();
            int ballTwo = nextFrame.getSecondRoll();
            bonus.fulfillBonus(ballOne + ballTwo);
        }
    }

    private static void getDoubleStrikeBonus(Bonus bonus, List<Frame> frames, int nextFrameIndex, Frame nextFrame) {
        int secondBallIndex = nextFrameIndex + 1;
        if(anotherFrameAvailable(secondBallIndex, frames)){
            Frame ballTwoFrame = frames.get(nextFrameIndex + 1);
            int ballOne = nextFrame.getFirstRoll();
            int ballTwo = ballTwoFrame.getFirstRoll();
            bonus.fulfillBonus(ballOne + ballTwo);
        }
    }

    private static void getLastFrameBonus(Bonus bonus, Frame nextFrame) {
        int ballOne = nextFrame.getFirstRoll();
        int ballTwo = nextFrame.getFirstBonusRoll();
        bonus.fulfillBonus(ballOne + ballTwo);
    }

    private static boolean anotherFrameAvailable(int secondBallIndex, List<Frame> frames) {
        return secondBallIndex < frames.size();
    }

    private static void getBonusFromNextBall(Bonus bonus, Frame nextFrame) {
        bonus.fulfillBonus(nextFrame.getFirstRoll());
    }
}
