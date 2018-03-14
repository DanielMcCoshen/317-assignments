import java.io.IOException;

/**
 * Created by daniel on 12/03/18.
 */
public abstract class Player {

    int side;

    public void setBoard(Board board) {
        this.board = board;
    }

    Board board;


    public abstract Board turn() throws IOException;


}
