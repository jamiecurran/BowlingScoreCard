package bowling.tenpin.scorecard;

import bowling.tenpin.scorecard.exceptions.NoSuchPlayerException;
import bowling.tenpin.scorecard.exceptions.PlayerNameAlreadyUsedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ScoreCardITest {

    private static int STRIKE = 10;
    private static int PERFECT_GAME = 300;

    private ScoreCard scoreCard;
    private Player player1, player2, player3;

    @Before
    public void setup(){
        scoreCard = new ScoreCardImpl();
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        player3 = new Player("Player3");
    }

    @Test
    public void testRollThePerfectGame() throws PlayerNameAlreadyUsedException {

        Player player = new Player("Test Player");
        ScoreCardImpl scoreCard = new ScoreCardImpl();
        scoreCard.addPlayer(player);
        for(int i = 0; i < 9; i++){
            scoreCard.addFrame(player, STRIKE,0);
            scoreCard.endCurrentFrame();
        }
        scoreCard.addFinalFrame(player, STRIKE, 0, STRIKE, STRIKE);

        List<Player> winners = scoreCard.getWinners();
        assertNotNull(winners);

        Player winner = winners.get(0);
        assertEquals(PERFECT_GAME, winner.getScore());

    }

    @Test
       public void testAddFramesForTwoPlayersAndGetScores() throws PlayerNameAlreadyUsedException {
           scoreCard.addPlayer(player1);
           scoreCard.addPlayer(player2);
           scoreCard.addFrame(player1, 1,3);
           scoreCard.addFrame(player2, 2,3);
           scoreCard.endCurrentFrame();

           scoreCard.addFrame(player1, 4,2);
           scoreCard.addFrame(player2, 3,3);
           scoreCard.endCurrentFrame();

           Assert.assertEquals(10, scoreCard.getPlayerScore(player1));
           Assert.assertEquals(11, scoreCard.getPlayerScore(player2));
       }

       @Test
       public void testGetTeamScore() throws PlayerNameAlreadyUsedException {
           scoreCard.addPlayer(player1);
           scoreCard.addPlayer(player2);
           scoreCard.addPlayer(player3);

           scoreCard.addFrame(player1, 1, 3);
           scoreCard.addFrame(player2, 2, 3);
           scoreCard.endCurrentFrame();

           scoreCard.addFrame(player1, 4, 2);
           scoreCard.addFrame(player2, 3, 2);
           scoreCard.endCurrentFrame();

           List<Player> winners = scoreCard.getWinners();
           Assert.assertEquals(20, scoreCard.getTeamScore());
       }

       @Test
       public void testGetWinningPlayersName() throws PlayerNameAlreadyUsedException {
           int maxNumberOfFrames = 2;
           scoreCard = new ScoreCardImpl(maxNumberOfFrames);
           scoreCard.addPlayer(player1);
           scoreCard.addPlayer(player2);
           scoreCard.addPlayer(player3);

           scoreCard.addFrame(player1, 1, 3);
           scoreCard.addFrame(player2, 2, 3);
           scoreCard.addFrame(player3, 5, 2);
           scoreCard.endCurrentFrame();

           scoreCard.addFrame(player1, 4, 2);
           scoreCard.addFrame(player2, 3, 2);
           scoreCard.addFrame(player3, 9, 0);
           scoreCard.endCurrentFrame();

           List<Player> winners = scoreCard.getWinners();
           Assert.assertEquals(1, winners.size());
           assertTrue(winners.contains(player3));
       }

       @Test
       public void testTwoEqualScores() throws PlayerNameAlreadyUsedException {
           int maxNumberOfFrames = 2;
           scoreCard = new ScoreCardImpl(maxNumberOfFrames);
           scoreCard.addPlayer(player1);
           scoreCard.addPlayer(player2);
           scoreCard.addPlayer(player3);

           scoreCard.addFrame(player1, 0, 3);
           scoreCard.addFrame(player2, 4, 2);
           scoreCard.addFrame(player3, 2, 4);
           scoreCard.endCurrentFrame();

           scoreCard.addFrame(player1, 0, 3);
           scoreCard.addFrame(player2, 4, 2);
           scoreCard.addFrame(player3, 2, 4);
           scoreCard.endCurrentFrame();

           List<Player> winners = scoreCard.getWinners();
           Assert.assertEquals(2, winners.size());
           assertTrue(winners.contains(player2));
           assertTrue(winners.contains(player3));
       }

       @Test(expected = NoSuchPlayerException.class)
       public void testAddFrameForUnknownPlayer(){
           scoreCard.addFrame(new Player("Unknown player"), 10, 0);
       }


}
