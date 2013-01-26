package bowling.tenpin.scorecard;

import bowling.tenpin.scorecard.display.DisplayConsole;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

public class TenPinBowlingScoreCardAppUTest {

    private static final String NO_PLAYER = "";

    @Test
    public void testEmptyPlayerNameWithNoPreviousPlayersEnteredGeneratesErrorMessage(){
        ScoreCard mockScoreCard = mock(ScoreCard.class);
        when(mockScoreCard.hasMinimumNumberOfPlayers()).thenReturn(false, true);
        when(mockScoreCard.getWinners()).thenReturn(Arrays.asList(new Player("Test User")));

        DisplayConsole mockDisplayConsole = mock(DisplayConsole.class);
        when(mockDisplayConsole.getPlayerName()).thenReturn("","Test User","");

        TenPinBowlingScoreCardApp app = new TenPinBowlingScoreCardApp(mockScoreCard, mockDisplayConsole);
        app.playGame();
        verify(mockDisplayConsole, atLeastOnce()).displayErrorMessage("ERROR - You have not entered a player.\n");
    }

    @Test
    public void testInvalidFrameScoreDisplaysErrorMessageAndNewPrompt(){
        Player testPlayer = new Player("Test user");
        Set<Player> players = new LinkedHashSet<Player>();
        players.add(testPlayer);

        ScoreCard mockScoreCard = mock(ScoreCard.class);
        when(mockScoreCard.hasMinimumNumberOfPlayers()).thenReturn(true);
        when(mockScoreCard.getPlayers()).thenReturn(players);
        when(mockScoreCard.getWinners()).thenReturn(Arrays.asList(testPlayer));
        when(mockScoreCard.getNumberOfFrames()).thenReturn(10);
        doThrow(new IllegalStateException("The initial frame score cannot exceed ten")).when(mockScoreCard).addFrame(testPlayer, 11, 0);

        DisplayConsole mockDisplayConsole = mock(DisplayConsole.class);
        when(mockDisplayConsole.getPlayerName()).thenReturn("Test User", NO_PLAYER);
        when(mockDisplayConsole.getPlayerRoll("Player " + testPlayer.getName() + ", enter first roll:\n")).thenReturn(11, 10);

        TenPinBowlingScoreCardApp app = new TenPinBowlingScoreCardApp(mockScoreCard, mockDisplayConsole);
        app.playGame();
        verify(mockDisplayConsole,atLeastOnce()).displayErrorMessage("Error: The initial frame score cannot exceed ten\n");
    }

    @Test
    public void testGetTwoBonusRollPromptsForAStrikeThenAnotherStrikeInTheFinalFrame(){
        Player testPlayer = new Player("Test user");
        Set<Player> players = new LinkedHashSet<Player>();
        players.add(testPlayer);

        ScoreCard mockScoreCard = mock(ScoreCard.class);
        when(mockScoreCard.hasMinimumNumberOfPlayers()).thenReturn(true);
        when(mockScoreCard.getPlayers()).thenReturn(players);
        when(mockScoreCard.getWinners()).thenReturn(Arrays.asList(testPlayer));
        when(mockScoreCard.getNumberOfFrames()).thenReturn(1);

        DisplayConsole mockDisplayConsole = mock(DisplayConsole.class);
        when(mockDisplayConsole.getPlayerName()).thenReturn("Test User", NO_PLAYER);
        when(mockDisplayConsole.getPlayerRoll(anyString())).thenReturn(10,10,10);

        TenPinBowlingScoreCardApp app = new TenPinBowlingScoreCardApp(mockScoreCard, mockDisplayConsole);
        app.playGame();
        verify(mockDisplayConsole,times(1)).getPlayerRoll("Strike! As this is the final frame please enter your bonus roll:\n");
        verify(mockDisplayConsole,times(1)).getPlayerRoll("Another Strike! As this is the final frame please enter your final bonus roll:\n");
        verify(mockDisplayConsole,never()).getPlayerRoll("You got a spare on the final frame, please enter your bonus roll:\n");
    }

    @Test
    public void testGetOneBonusRollPromptForASpareInTheFinalFrame(){
        Player testPlayer = new Player("Test user");
        Set<Player> players = new LinkedHashSet<Player>();
        players.add(testPlayer);

        ScoreCard mockScoreCard = mock(ScoreCard.class);
        when(mockScoreCard.hasMinimumNumberOfPlayers()).thenReturn(true);
        when(mockScoreCard.getPlayers()).thenReturn(players);
        when(mockScoreCard.getWinners()).thenReturn(Arrays.asList(testPlayer));
        when(mockScoreCard.getNumberOfFrames()).thenReturn(1);

        DisplayConsole mockDisplayConsole = mock(DisplayConsole.class);
        when(mockDisplayConsole.getPlayerName()).thenReturn("Test User", NO_PLAYER);
        when(mockDisplayConsole.getPlayerRoll(anyString())).thenReturn(9,1,10);

        TenPinBowlingScoreCardApp app = new TenPinBowlingScoreCardApp(mockScoreCard, mockDisplayConsole);
        app.playGame();
        verify(mockDisplayConsole,times(1)).getPlayerRoll("You got a spare on the final frame, please enter your bonus roll:\n");
    }

}
