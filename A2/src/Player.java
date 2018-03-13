import java.io.IOException;

/**
 * Created by daniel on 12/03/18.
 */
public abstract class Player {

    int side;
    Board board;


    public abstract void turn() throws IOException;
}
