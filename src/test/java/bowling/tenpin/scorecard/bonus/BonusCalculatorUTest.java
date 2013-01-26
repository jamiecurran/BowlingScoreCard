package bowling.tenpin.scorecard.bonus;

import bowling.tenpin.scorecard.frames.Frame;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static bowling.tenpin.scorecard.bonus.BonusType.SPARE;
import static bowling.tenpin.scorecard.bonus.BonusType.STRIKE;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class BonusCalculatorUTest {

    @Test
    public void testCalculateBonusForOneStrikeAndANormalThrow(){
        Frame firstFrame = new Frame(10,0,0);
        Frame secondFrame = new Frame(0,9,1);
        List<Frame> frames = Arrays.asList(firstFrame, secondFrame);

        Bonus strikeBonus = new Bonus(STRIKE, firstFrame);
        BonusCalculator.calculateBonus(frames, strikeBonus);
        assertTrue(strikeBonus.isFulfilled());
        assertEquals(9, strikeBonus.getValue());
    }

    @Test
    public void testCalculateStrikeWhereFirstBallIsAStrikeAndNextBallIsAGutterBall(){
        Frame firstFrame = new Frame(10,0,0);
        Frame secondFrame = new Frame(10,0,1);
        Frame thirdFrame = new Frame(0,9,2);
        List<Frame> frames = Arrays.asList(firstFrame, secondFrame, thirdFrame);

        Bonus strikeBonus = new Bonus(STRIKE, firstFrame);
        BonusCalculator.calculateBonus(frames, strikeBonus);
        assertTrue(strikeBonus.isFulfilled());
        assertEquals(10, strikeBonus.getValue());

    }

    @Test
    public void testCalculateStrikeWhereFirstBallIsAStrikeAndNextBallIsAScoringBall(){
        Frame firstFrame = new Frame(10,0,0);
        Frame secondFrame = new Frame(10,0,1);
        Frame thirdFrame = new Frame(7,2,2);
        List<Frame> frames = Arrays.asList(firstFrame, secondFrame, thirdFrame);

        Bonus strikeBonus = new Bonus(STRIKE, firstFrame);
        BonusCalculator.calculateBonus(frames, strikeBonus);
        assertTrue(strikeBonus.isFulfilled());
        assertEquals(17, strikeBonus.getValue());
    }

    @Test
    public void testCalculateFirstFrameIsNormalSecondIsStrikeThirdIsSpare(){
        Frame firstFrame = new Frame(5,4,0);
        Frame secondFrame = new Frame(10,0,1);
        Frame thirdFrame = new Frame(7,3,2);
        List<Frame> frames = Arrays.asList(firstFrame, secondFrame, thirdFrame);

        Bonus strikeBonus = new Bonus(STRIKE, secondFrame);
        BonusCalculator.calculateBonus(frames, strikeBonus);
        assertTrue(strikeBonus.isFulfilled());
        assertEquals(10, strikeBonus.getValue());
    }

    @Test
    public void testCalculateSpareBonus(){
        Frame firstFrame = new Frame(5,5,0);
        Frame secondFrame = new Frame(4,3,1);
        Frame thirdFrame = new Frame(7,3,2);
        List<Frame> frames = Arrays.asList(firstFrame, secondFrame, thirdFrame);

        Bonus strikeBonus = new Bonus(SPARE, firstFrame);
        BonusCalculator.calculateBonus(frames, strikeBonus);
        assertTrue(strikeBonus.isFulfilled());
        assertEquals(4, strikeBonus.getValue());
    }

    @Test
    public void testStrikeBonusUnfulfilledIfFirstBallIsStrikeButSecondBallHasNotBeenRolled(){
        Frame firstFrame = new Frame(10,0,0);
        Frame secondFrame = new Frame(10,0,1);
        List<Frame> frames = Arrays.asList(firstFrame, secondFrame);

        Bonus strikeBonus = new Bonus(STRIKE, firstFrame);
        BonusCalculator.calculateBonus(frames, strikeBonus);
        assertFalse(strikeBonus.isFulfilled());
    }

    @Test
    public void testSpareBonusIsNotFulfilledIfFirstBallHasNotBeenRolled(){
        Frame firstFrame = new Frame(10,0,0);
        Frame secondFrame = new Frame(9,1,1);
        List<Frame> frames = Arrays.asList(firstFrame, secondFrame);

        Bonus spareBonus = new Bonus(SPARE, secondFrame);
        BonusCalculator.calculateBonus(frames, spareBonus);
        assertFalse(spareBonus.isFulfilled());
    }

    @Test
    public void testStrikeBonusWhereNextFrameIsLastFrameWithTwoBonusRolls(){
        Frame firstFrame = new Frame(10,0,0);
        Frame finalFrame = new Frame(10,0,1,true);
        finalFrame.setFirstBonusRoll(10);
        finalFrame.setSecondBonusRoll(10);

        List<Frame> frames = Arrays.asList(firstFrame, finalFrame);

        Bonus strikeBonus = new Bonus(STRIKE, firstFrame);
        BonusCalculator.calculateBonus(frames, strikeBonus);
        assertTrue(strikeBonus.isFulfilled());
        assertEquals(20, strikeBonus.getValue());
    }

    @Test
    public void testStrikeBonusWhereNextFrameIsLastFrameWithOneBonusRoll(){
        Frame firstFrame = new Frame(10,0,0);
        Frame finalFrame = new Frame(9,1,1,true);
        finalFrame.setFirstBonusRoll(10);

        List<Frame> frames = Arrays.asList(firstFrame, finalFrame);

        Bonus strikeBonus = new Bonus(STRIKE, firstFrame);
        BonusCalculator.calculateBonus(frames, strikeBonus);
        assertTrue(strikeBonus.isFulfilled());
        assertEquals(10, strikeBonus.getValue());
    }

}
