package bowling.tenpin.scorecard.frames;

import bowling.tenpin.scorecard.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FramesITest {

    private FramesImpl testObj;
    private Player player1 = new Player("player1");

    @Before
    public void setup(){
        testObj = new FramesImpl(player1);
    }

    @Test
    public void testGetScore(){
        Frame frame1 = new Frame(5,4,0);
        Frame frame2 = new Frame(4,3,1);
        testObj.addFrame(frame1);
        testObj.addFrame(frame2);
        assertEquals(16, testObj.getScore());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxNumberOfFrames(){
        Frame frame1 = new Frame(5,4,0);
        Frame frame2 = new Frame(5,4,1);
        Frame frame3 = new Frame(5,4,2);
        int maxNumberOfFrames = 2;
        testObj = new FramesImpl(player1, maxNumberOfFrames);
        testObj.addFrame(frame1);
        testObj.addFrame(frame2);
        testObj.addFrame(frame3);
    }

    @Test
    public void testScoreWithOneStrikeInFirstFrameOfTwo(){
        int maxNumberOfFrames = 2;
        Frame frame1 = new Frame(10,0,0);
        Frame frame2 = new Frame(8,1,1);
        testObj = new FramesImpl(player1, maxNumberOfFrames);
        testObj.addFrame(frame1);
        testObj.addFrame(frame2);
        assertEquals(28, testObj.getScore());
    }

    @Test
    public void testScoreWithTwoSuccessiveStrikes(){
        int maxNumberOfFrames = 3;
        Frame frame1 = new Frame(10,0,0);
        Frame frame2 = new Frame(10,0,1);
        Frame frame3 = new Frame(9,0,2);
        testObj = new FramesImpl(player1, maxNumberOfFrames);
        testObj.addFrame(frame1);
        testObj.addFrame(frame2);
        testObj.addFrame(frame3);
        assertEquals(57,testObj.getScore());

    }

    @Test
    public void testScoreWithThreeSuccessiveStrikes(){
        int maxNumberOfFrames = 4;
        Frame frame1 = new Frame(10,0,0);
        Frame frame2 = new Frame(10,0,1);
        Frame frame3 = new Frame(10,0,2);
        Frame frame4 = new Frame(0,9,3);
        testObj = new FramesImpl(player1, maxNumberOfFrames);
        testObj.addFrame(frame1);
        testObj.addFrame(frame2);
        testObj.addFrame(frame3);
        testObj.addFrame(frame4);
        assertEquals(78,testObj.getScore());
    }

    @Test
    public void testScoreWithFourSuccessiveStrikes(){
        int maxNumberOfFrames = 5;
        Frame frame1 = new Frame(10,0,0);
        Frame frame2 = new Frame(10,0,1);
        Frame frame3 = new Frame(10,0,2);
        Frame frame4 = new Frame(10,0,3);
        Frame frame5 = new Frame(0,9,4);
        testObj = new FramesImpl(player1, maxNumberOfFrames);
        testObj.addFrame(frame1);
        testObj.addFrame(frame2);
        testObj.addFrame(frame3);
        testObj.addFrame(frame4);
        testObj.addFrame(frame5);
        assertEquals(108,testObj.getScore());
    }

    @Test
    public void testScoreWithSpareFinalFrameAndStrikeForBonusBall(){
        int maxNumberOfFrames = 2;
        Frame frame1 = new Frame(10,0,0);
        Frame finalFrame = new Frame(9,1,1,true);
        finalFrame.setFirstBonusRoll(10);
        testObj = new FramesImpl(player1, maxNumberOfFrames);
        testObj.addFrame(frame1);
        testObj.addFrame(finalFrame);
        assertEquals(40, testObj.getScore());
    }
}
