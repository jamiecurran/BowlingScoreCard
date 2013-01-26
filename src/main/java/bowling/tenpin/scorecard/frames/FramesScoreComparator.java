package bowling.tenpin.scorecard.frames;

import java.util.Comparator;

public class FramesScoreComparator implements Comparator<Frames>{

    @Override
    public int compare(Frames frames1, Frames frames2) {
        int frames1TotalScore = frames1.getScore();
        int frames2TotalScore = frames2.getScore();
        if(frames1TotalScore > frames2TotalScore){
            return 1;
        } else if(frames1TotalScore == frames2TotalScore){
            return 0;
        } else {
            return -1;
        }
    }
}
