package bowling.tenpin.scorecard.exceptions;

public class NoSuchPlayerException extends RuntimeException{

    public NoSuchPlayerException(String message){
        super(message);
    }

}
