package bowling.tenpin.scorecard;

import bowling.tenpin.scorecard.display.DisplayConsole;
import bowling.tenpin.scorecard.display.DisplayConsoleImpl;
import bowling.tenpin.scorecard.exceptions.PlayerNameAlreadyUsedException;

import java.util.List;
import java.util.Set;

public class TenPinBowlingScoreCardApp {

    private static final int STRIKE = 10;
    private static final int NO_ROLL = 0;

    private ScoreCard scoreCard;
    private Set<Player> players;
    private DisplayConsole displayConsole;

    public TenPinBowlingScoreCardApp(DisplayConsole displayConsole){
        this.displayConsole = displayConsole;
        this.scoreCard = new ScoreCardImpl();
    }

    public TenPinBowlingScoreCardApp(ScoreCard scoreCard, DisplayConsole displayConsole){
         this.scoreCard = scoreCard;
         this.displayConsole = displayConsole;
    }

    public static void main(String[] args){
        DisplayConsole displayConsole = new DisplayConsoleImpl();
        TenPinBowlingScoreCardApp app = new TenPinBowlingScoreCardApp(displayConsole);
        app.playGame();
    }

    public void playGame() {
        displayWelcomeMessage();
        addPlayers();
        captureScores();
        displayWinner();
    }

    private void displayWelcomeMessage() {
        displayConsole.displayWelcomeMessage();
    }

    private void addPlayers() {
        boolean continueInput = true;
        while(continueInput){
            String playerName = displayConsole.getPlayerName();
            continueInput = processAddPlayerInput(playerName);
        }
        players = scoreCard.getPlayers();
    }

    private boolean processAddPlayerInput(String playerName) {
        boolean continueInput = true;
        if(playerName.isEmpty()){
            if(!scoreCard.hasMinimumNumberOfPlayers()){
                displayConsole.displayErrorMessage("ERROR - You have not entered a player.\n");
                continueInput = true;
            } else {
                continueInput = false;
            }
        } else{
            try {
                scoreCard.addPlayer(new Player(playerName.toUpperCase()));
            } catch (PlayerNameAlreadyUsedException e) {
                displayConsole.displayErrorMessage("ERROR - Player name is already in use.\n");
            }
        }
        return continueInput;
    }

    private void captureScores() {
        int maxNumberOfFrames = scoreCard.getNumberOfFrames();
        for(int i = 0; i < maxNumberOfFrames - 1; i++){
            displayConsole.displayCurrentFrame(getCurrentFrame());
            playFrame();
            scoreCard.endCurrentFrame();
        }
        playFinalFrame();
    }

    private int getCurrentFrame() {
        return scoreCard.getCurrentFrame() + 1;
    }

    private void playFrame() {
        for(Player player: players){
            int firstRoll = 0;
            int secondRoll = 0;
            boolean validInput = false;
            while (!validInput){
                firstRoll = getRollEntry("Player " + player.getName() + ", enter first roll:\n");
                if(firstRoll < STRIKE){
                    secondRoll = getRollEntry("Enter second roll:\n");
                }
                try{
                    scoreCard.addFrame(player, firstRoll, secondRoll);
                    validInput = true;
                    displayConsole.displayFrameMessage(firstRoll, secondRoll);
                }catch (IllegalArgumentException ex){
                    displayConsole.displayErrorMessage("Error: " + ex.getMessage() + "\n");
                    validInput = false;
                }catch (IllegalStateException ex){
                    displayConsole.displayErrorMessage("Error: " + ex.getMessage() + "\n");
                    validInput = false;
                }
            }
        }
    }



    private void playFinalFrame() {
        int firstRoll = 0;
        int secondRoll = 0;
        boolean validInput;

        displayConsole.displayFinalFrame();

        for(Player player: players){
            validInput = false;
            while (!validInput){
                try{
                    firstRoll = getRollEntry("Player " + player.getName() + ", enter first roll:\n");
                    if(firstRoll == STRIKE){
                        validInput = processFrameWithStrikeBonusRolls(player);
                    }else {
                        secondRoll = getRollEntry("Enter second roll:\n");
                        if(firstRoll + secondRoll == 10){
                            validInput = processFrameWithSpareBonusRoll(firstRoll, secondRoll, player);
                        } else {
                            validInput = processWithNoBonusRolls(firstRoll, secondRoll, player);
                        }
                    }
                }catch (IllegalArgumentException ex){
                        displayConsole.displayErrorMessage("Error: " + ex.getMessage() + "\n");
                        validInput = false;
                }catch (IllegalStateException ex){
                        displayConsole.displayErrorMessage("Error: " + ex.getMessage() + "\n");
                        validInput = false;
                }
            }
        }

    }

    private boolean processWithNoBonusRolls(int firstRoll, int secondRoll, Player player) {
        boolean validInput;
        scoreCard.addFinalFrame(player, firstRoll, secondRoll);
        validInput = true;
        return validInput;
    }

    private boolean processFrameWithSpareBonusRoll(int firstRoll, int secondRoll, Player player) {
        int firstBonusRoll;
        boolean validInput;
        firstBonusRoll = getRollEntry("You got a spare on the final frame, please enter your bonus roll:\n");
        scoreCard.addFinalFrame(player, firstRoll, secondRoll, firstBonusRoll);
        validInput = true;
        return validInput;
    }

    private boolean processFrameWithStrikeBonusRolls(Player player) {
        int firstBonusRoll;
        int secondBonusRoll;
        boolean validInput;
        firstBonusRoll = getRollEntry("Strike! As this is the final frame please enter your bonus roll:\n");
        if(firstBonusRoll == STRIKE){
            secondBonusRoll = getRollEntry("Another Strike! As this is the final frame please enter your final bonus roll:\n");
            scoreCard.addFinalFrame(player, STRIKE, NO_ROLL, firstBonusRoll, secondBonusRoll);
            validInput = true;
        } else {
            scoreCard.addFinalFrame(player, STRIKE, NO_ROLL, firstBonusRoll);
            validInput = true;
        }
        return validInput;
    }

    private int getRollEntry(String message){
        boolean inputValid = false;
        int rollValue = 0;
        while(!inputValid){
            try{
                rollValue = displayConsole.getPlayerRoll(message);
                inputValid = true;
            } catch (NumberFormatException nfe){
                displayConsole.displayErrorMessage("ERROR - invalid roll entered, please re-enter roll.\n");
            }
        }
        return rollValue;
    }

    private void displayWinner() {
        List<Player> winners = scoreCard.getWinners();
        if(winners.size() > 1){
            displayMultipleWinners(winners);
        } else {
            displaySingleWinner(winners.get(0));
        }
        displayConsole.displayTeamScore(scoreCard.getTeamScore());
    }

    private void displaySingleWinner(Player winner) {
        displayConsole.displayWinner(winner);
    }

    private void displayMultipleWinners(List<Player> winners) {
        displayConsole.displayMultipleWinners(winners);
    }
}