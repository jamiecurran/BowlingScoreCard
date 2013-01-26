package bowling.tenpin.scorecard.frames;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FramesScoreComparatorUTest {

    private static final int GREATER = 1;
    private static final int EQUAL = 0;
    private static final int LESSTHAN = -1;

    private Frames player1Frames;
    private Frames player2Frames;
    private FramesScoreComparator testObj;

    @Before
    public void setup(){
        testObj = new FramesScoreComparator();
        player1Frames = mock(Frames.class);
        player2Frames = mock(Frames.class);
    }

    @Test
    public void testFramesScore1GreaterThanFrameScore2(){
        when(player1Frames.getScore()).thenReturn(11);
        when(player2Frames.getScore()).thenReturn(10);
        assertEquals(GREATER, testObj.compare(player1Frames, player2Frames));
    }

    @Test
    public void testFramesScore1EqualToFrameScore2(){
        when(player1Frames.getScore()).thenReturn(10);
        when(player2Frames.getScore()).thenReturn(10);
        assertEquals(EQUAL, testObj.compare(player1Frames, player2Frames));
    }

    @Test
    public void testFramesScore1LessThanFrameScore2(){
        when(player1Frames.getScore()).thenReturn(9);
        when(player2Frames.getScore()).thenReturn(10);
        assertEquals(LESSTHAN, testObj.compare(player1Frames, player2Frames));
    }
}
