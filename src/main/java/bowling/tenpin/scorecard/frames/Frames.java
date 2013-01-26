package bowling.tenpin.scorecard.frames;

import bowling.tenpin.scorecard.Player;

public interface Frames{

    void addFrame(Frame frame);
    int size();
    int getScore();
    Player getPlayer();
}
