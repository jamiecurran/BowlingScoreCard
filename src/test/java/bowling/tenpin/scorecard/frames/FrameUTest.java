package bowling.tenpin.scorecard.frames;

import bowling.tenpin.scorecard.bonus.BonusType;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class FrameUTest {

    @Test(expected = IllegalArgumentException.class)
    public void testFrameCannotContainNegativePinCount(){
        Frame testObj = new Frame(-1,0, 0);
    }

    @Test(expected = IllegalStateException.class)
    public void testTotalFrameScoreDoesNotExceedTen(){
        Frame testObj = new Frame(12,0, 0);
    }

    @Test
    public void testIsStrike(){
        Frame testObj = new Frame(10, 0, 0);
        assertTrue(testObj.getType() == BonusType.STRIKE);
    }

    @Test
    public void testIsNotStrike(){
        Frame testObj = new Frame(3, 3, 0);
        assertTrue(testObj.getType() == BonusType.NONE);
    }

    @Test
    public void testIsSpare(){
        Frame testObj = new Frame(3, 7, 0);
        assertTrue(testObj.getType() == BonusType.SPARE);
    }

    @Test
    public void testLastFrameWithAStrikeAllowsAdditionalRoll(){
        boolean isLastFrame = true;
        Frame testObj = new Frame(10, 0, 0, isLastFrame);
        testObj.setFirstBonusRoll(10);
    }

    @Test
    public void testLastFrameWithSpareAllowedToSetBonusRole(){
        boolean isLastFrame = true;
        Frame testObj = new Frame(9, 1, 0, isLastFrame);
        testObj.setFirstBonusRoll(10);
    }

    @Test(expected = IllegalStateException.class)
    public void testNotLastFrameNotAllowedToSetBonusRole(){
        Frame testObj = new Frame(10, 0, 0);
        testObj.setFirstBonusRoll(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testASingleBonusRoleCannotExceedTen(){
        boolean isLastFrame = true;
        Frame testObj = new Frame(10, 0, 0, isLastFrame);
        testObj.setFirstBonusRoll(11);
    }

}
