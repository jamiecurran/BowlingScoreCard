package bowling.tenpin.scorecard;

import bowling.tenpin.scorecard.exceptions.PlayerNameAlreadyUsedException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class ScoreCardUTest {

    private ScoreCard testObj;
    private Player player1 = new Player("player1");
    private Player player2 = new Player("player2");

    @Before
    public void setup(){
        testObj = new ScoreCardImpl();
    }

    @Test
    public void testAddPlayerName() throws PlayerNameAlreadyUsedException {
        testObj.addPlayer(player1);
        Set<Player> players = testObj.getPlayers();
        assertTrue(players.contains(player1));
        assertEquals(1, testObj.getNumberOfPlayers());
    }

    @Test
    public void testAddTwoPlayers() throws PlayerNameAlreadyUsedException {
        testObj.addPlayer(player1);
        testObj.addPlayer(player2);
        Set<Player> players = testObj.getPlayers();
        assertTrue(players.size() == 2);
        assertTrue(players.contains(player1));
        assertTrue(players.contains(player2));
    }

    @Test(expected = PlayerNameAlreadyUsedException.class)
    public void testAddTwoPlayersWithSameNameThrowsException() throws PlayerNameAlreadyUsedException {
        Player duplicatePlayer = new Player("player1");
        testObj.addPlayer(player1);
        testObj.addPlayer(duplicatePlayer);
    }


    @Test
    public void testHasMinimumNumberOfPlayers(){
        assertFalse(testObj.hasMinimumNumberOfPlayers());
    }

}