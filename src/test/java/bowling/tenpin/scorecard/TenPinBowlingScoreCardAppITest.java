package bowling.tenpin.scorecard;

import bowling.tenpin.scorecard.display.DisplayConsole;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class TenPinBowlingScoreCardAppITest {

    private static int PERFECT_GAME = 300;
    private static int STRIKE = 10;
    private static String NO_PLAYER = "";

    @Test
    public void testPerfectGameForSinglePlayer(){
        DisplayConsole mockConsole = mock(DisplayConsole.class);
        when(mockConsole.getPlayerName()).thenReturn("User 1", NO_PLAYER);
        when(mockConsole.getPlayerRoll(anyString())).thenReturn(STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE);

        ScoreCard scoreCard = new ScoreCardImpl();

        TenPinBowlingScoreCardApp testObj = new TenPinBowlingScoreCardApp(scoreCard, mockConsole);
        testObj.playGame();

        Player winner = scoreCard.getWinners().get(0);
        verify(mockConsole,times(1)).displayWinner(winner);
        assertEquals(PERFECT_GAME, scoreCard.getTeamScore());
        assertEquals(PERFECT_GAME, winner.getScore());
    }

    @Test
    public void testPerfectGameForTwoPlayers(){
        DisplayConsole mockConsole = mock(DisplayConsole.class);
        when(mockConsole.getPlayerName()).thenReturn("User 1", "User 2", NO_PLAYER);
        when(mockConsole.getPlayerRoll(anyString())).thenReturn(STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE, STRIKE);

        ScoreCard scoreCard = new ScoreCardImpl();

        TenPinBowlingScoreCardApp testObj = new TenPinBowlingScoreCardApp(scoreCard, mockConsole);
        testObj.playGame();

        List<Player> winners = scoreCard.getWinners();
        verify(mockConsole,times(1)).displayMultipleWinners(winners);
        assertEquals(2, winners.size());
        assertEquals(PERFECT_GAME * 2, scoreCard.getTeamScore());
        for(Player winner: winners){
            assertEquals(PERFECT_GAME, winner.getScore());
        }
    }

    @Test
    public void testNormalGameForOnePlayers(){
        DisplayConsole mockConsole = mock(DisplayConsole.class);
        when(mockConsole.getPlayerName()).thenReturn("User 1", NO_PLAYER);
        when(mockConsole.getPlayerRoll(anyString())).thenReturn(STRIKE, 3, 6, 8, 2, 1, 4, 8, 2, 2, 4, 1, 6, 6, 0, 0, 5, 4, 6, 5);

        ScoreCard scoreCard = new ScoreCardImpl();

        TenPinBowlingScoreCardApp testObj = new TenPinBowlingScoreCardApp(scoreCard, mockConsole);
        testObj.playGame();

        Player winner = scoreCard.getWinners().get(0);
        verify(mockConsole,times(1)).displayWinner(winner);
        assertEquals(95, scoreCard.getTeamScore());
        assertEquals(95, winner.getScore());
    }


}
