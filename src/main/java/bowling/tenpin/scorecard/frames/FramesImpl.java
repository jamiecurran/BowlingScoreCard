package bowling.tenpin.scorecard.frames;

import bowling.tenpin.scorecard.Player;
import bowling.tenpin.scorecard.bonus.Bonus;
import bowling.tenpin.scorecard.bonus.BonusCalculator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static bowling.tenpin.scorecard.bonus.BonusType.*;

public class FramesImpl implements Frames{

    private List<Frame> frames = new ArrayList<Frame>();
    private List<Bonus> unfulfilledBonus = new LinkedList<Bonus>();
    private int maxNumberOfFrames;
    private Player player;

    public FramesImpl(Player player){
        this(player, 10);
    }

    public FramesImpl(Player player, int maxNumberOfFrames) {
        this.player = player;
        this.maxNumberOfFrames = maxNumberOfFrames;
    }

    @Override
    public void addFrame(Frame frame){
        if(frames.size() < maxNumberOfFrames){
            frames.add(frame);
            player.addScore(frame.getScore());
            if(frame.getRound() > 0){
                evaluateUnfulfilledBonuses();
            }
            evaluateFrameBonus(frame);
        } else {
            throw new IllegalArgumentException("Max number of frames exceeded");
        }
    }

    private void evaluateFrameBonus(Frame frame) {
        if(frame.getType() == STRIKE){
            Bonus strikeBonus = new Bonus(STRIKE, frame);
            unfulfilledBonus.add(strikeBonus);
        } else if(frame.getType() == SPARE){
            Bonus spareBonus = new Bonus(SPARE, frame);
            unfulfilledBonus.add(spareBonus);
        }
    }

    private void evaluateUnfulfilledBonuses() {
       Iterator<Bonus> iterator = unfulfilledBonus.iterator();
       while(iterator.hasNext()){
            Bonus bonus = iterator.next();
            BonusCalculator.calculateBonus(frames, bonus);
            if(bonus.isFulfilled()){
                player.addScore(bonus.getValue());
                iterator.remove();
            }
        }
    }

    @Override
    public int size(){
        return frames.size();
    }

    @Override
    public int getScore(){
        return player.getScore();
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
