package bowling.tenpin.scorecard;

import bowling.tenpin.scorecard.exceptions.PlayerNameAlreadyUsedException;

import java.util.List;
import java.util.Set;

public interface ScoreCard {

    void addPlayer(Player player) throws PlayerNameAlreadyUsedException;
    Set<Player> getPlayers();
    int getNumberOfPlayers();
    int getPlayerScore(Player player);
    int getTeamScore();
    List<Player> getWinners();
    void endCurrentFrame();
    void addFrame(Player player, int firstRoll, int secondRoll);
    void addFinalFrame(Player player, int firstRoll, int secondRoll);
    void addFinalFrame(Player player, int firstRoll, int secondRoll, int bonusRoll);
    void addFinalFrame(Player player, int firstRoll, int secondRoll, int firstBonusRoll, int secondBonusRoll);
    int getNumberOfFrames();
    int getCurrentFrame();
    boolean hasMinimumNumberOfPlayers();

}
