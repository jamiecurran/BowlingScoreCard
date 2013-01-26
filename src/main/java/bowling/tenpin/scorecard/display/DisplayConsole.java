package bowling.tenpin.scorecard.display;

import bowling.tenpin.scorecard.Player;

import java.util.List;

public interface DisplayConsole {

    void displayWelcomeMessage();
    void displayErrorMessage(String message);
    String getPlayerName();
    void displayCurrentFrame(int currentFrame);
    int getPlayerRoll(String message) throws NumberFormatException;
    void displayWinner(Player winner);
    void displayTeamScore(int teamScore);
    void displayMultipleWinners(List<Player> winners);
    void displayFinalFrame();
    void displayFrameMessage(int firstRoll, int secondRoll);
}
