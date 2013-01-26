package bowling.tenpin.scorecard;

import bowling.tenpin.scorecard.ScoreCard;
import bowling.tenpin.scorecard.exceptions.NoSuchPlayerException;
import bowling.tenpin.scorecard.exceptions.PlayerNameAlreadyUsedException;
import bowling.tenpin.scorecard.frames.Frame;
import bowling.tenpin.scorecard.frames.Frames;
import bowling.tenpin.scorecard.frames.FramesImpl;
import bowling.tenpin.scorecard.frames.FramesScoreComparator;

import java.util.*;

public class ScoreCardImpl implements ScoreCard {

    private Map<Player, Frames> players = new LinkedHashMap<Player, Frames>();
    private int maxNumberOfFrames;
    private int currentFrame;
    private int teamScore;

    public ScoreCardImpl(){
        this.maxNumberOfFrames = 10;
        this.teamScore = 0;
        this.currentFrame = 0;
    }

    public ScoreCardImpl(int numberOfFrames){
        this.maxNumberOfFrames = numberOfFrames;
        this.teamScore = 0;
        this.currentFrame = 0;
    }

    public void addPlayer(Player player) throws PlayerNameAlreadyUsedException {
        if(!players.containsKey(player)){
            players.put(player, new FramesImpl(player, maxNumberOfFrames));
        } else {
            throw new PlayerNameAlreadyUsedException("The player name " + player + " is already used by another player");
        }
    }

    public Set<Player> getPlayers() {
        return players.keySet();
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    private void addFrame(Player player, Frame frame){
        if(players.containsKey(player)){
            Frames frames = players.get(player);
            frames.addFrame(frame);
        } else {
            throw new NoSuchPlayerException("A player could not be found for this frame.");
        }
    }

    private void calculateTeamScore() {
        teamScore = 0;
        for(Player player: getPlayers()){
            teamScore += player.getScore();
        }
    }

    public int getPlayerScore(Player player){
        Frames frames = players.get(player);
        return frames.getScore();
    }

    public int getTeamScore() {
        return teamScore;
    }

    public List<Player> getWinners() {
        List<Frames> leaderBoard = createLeaderBoard();
        List<Player> winners = new ArrayList<Player>();
        if(!leaderBoard.isEmpty()){
            int winningScore = leaderBoard.get(0).getScore();
            Iterator<Frames> iterator = leaderBoard.iterator();
            boolean moreWinners = true;
            while (iterator.hasNext() && moreWinners){
                Frames frames = iterator.next();
                if(isWinner(frames, winningScore)){
                    winners.add(frames.getPlayer());
                } else {
                    moreWinners = false;
                }
            }
            calculateTeamScore();
        }
        return winners;
    }

    private boolean isWinner(Frames frames, int winningScore) {
        if(frames.getScore() == winningScore){
            return true;
        }
        return false;
    }

    private List<Frames> createLeaderBoard() {
        Collection<Frames> playerFrames = players.values();
        List<Frames> framesList = new ArrayList<Frames>(playerFrames);
        Collections.sort(framesList, new FramesScoreComparator());
        Collections.reverse(framesList);
        return framesList;
    }

    public void endCurrentFrame(){
        currentFrame++;
    }

    public void addFrame(Player player, int firstRoll, int secondRoll){
        Frame frame = new Frame(firstRoll, secondRoll, currentFrame);
        addFrame(player, frame);
    }

    public void addFinalFrame(Player player, int firstRoll, int secondRoll) {
        checkFinalFrameConditions();
        Frame frame = new Frame(firstRoll, secondRoll, currentFrame, true);
        addFrame(player, frame);
    }

    public void addFinalFrame(Player player, int firstRoll, int secondRoll, int firstBonusRoll) {
        checkFinalFrameConditions();
        Frame frame = new Frame(firstRoll, secondRoll, currentFrame, true);
        frame.setFirstBonusRoll(firstBonusRoll);
        addFrame(player, frame);

    }

    public void addFinalFrame(Player player, int firstRoll, int secondRoll, int firstBonusRoll, int secondBonusRoll) {
        checkFinalFrameConditions();
        Frame frame = new Frame(firstRoll, secondRoll, currentFrame, true);
        frame.setFirstBonusRoll(firstBonusRoll);
        frame.setSecondBonusRoll(secondBonusRoll);
        addFrame(player, frame);
    }

    public int getNumberOfFrames() {
        return maxNumberOfFrames;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public boolean hasMinimumNumberOfPlayers() {
        return !players.isEmpty();
    }

    private void checkFinalFrameConditions() {
        if(currentFrame + 1 != maxNumberOfFrames){
            throw new IllegalStateException("Cannot enter final frame when more frames are to be played");
        }
    }
}