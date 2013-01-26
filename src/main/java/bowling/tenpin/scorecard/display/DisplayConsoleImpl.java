package bowling.tenpin.scorecard.display;

import bowling.tenpin.scorecard.Player;
import bowling.tenpin.scorecard.display.DisplayConsole;

import java.io.Console;
import java.util.List;

public class DisplayConsoleImpl implements DisplayConsole {

    private Console console = System.console();

    @Override
    public void displayWelcomeMessage() {
        console.printf("Welcome to the Mindy Candy Bowling Score Card application.\n");
        console.printf("Number of playable frames is 10.\n");
    }

    @Override
    public void displayErrorMessage(String message) {
        console.printf(message);
    }

    @Override
    public String getPlayerName() {
        return console.readLine("Please enter a player name (Leave blank to start game): ").trim();
    }

    @Override
    public void displayCurrentFrame(int currentFrame) {
        console.printf("\nFRAME: " + currentFrame + "\n");
    }

    @Override
    public int getPlayerRoll(String message) throws NumberFormatException {
        String input = console.readLine(message);
        return Integer.parseInt(input);
    }

    @Override
    public void displayWinner(Player winner) {
        console.printf("We have a single winner.\n");
        console.printf("Winner: " + winner.getName() + ": " + winner.getScore() + "\n");
    }

    @Override
    public void displayTeamScore(int teamScore) {
         console.printf("Team score: " + teamScore + "\n");
    }

    @Override
    public void displayMultipleWinners(List<Player> winners) {
        console.printf("We have " + winners.size() + "winners.\n");
        for(Player winner: winners){
            console.printf("Winner: " + winner.getName() + ": " + winner.getScore() + "\n");
        }
    }

    @Override
    public void displayFinalFrame() {
        console.printf("\nFINAL FRAME:\n");
    }

    @Override
    public void displayFrameMessage(int firstRoll, int secondRoll) {
        if(firstRoll == 10){
            console.printf("STRIKE!!!\n");
        } else if(firstRoll + secondRoll == 10){
            console.printf("SPARE!!!\n");
        }

    }


}
