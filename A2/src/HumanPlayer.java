import java.io.IOException;
import java.util.Scanner;

/**
 * Created by daniel on 12/03/18.
 */
public class HumanPlayer extends Player {
    Scanner in;

    public HumanPlayer(int side, Board board){
        this.side = side;
        this.board = board;
        in = new Scanner(System.in);
    }
    @Override
    public void turn() throws IOException{
        Piece p = null;
        //Runtime.getRuntime().exec("clear");
        System.out.println(board);

        if (side == 0){
            System.out.println("Wights' turn");
        }
        else if (side == 1){
            System.out.println("Dragons' turn");
        }
        else{
            throw new IllegalStateException("WTF");
        }
        while(true) {
        while(p == null) {
            System.out.println("Select a piece(x y)");
            int x = in.nextInt();
            int y = in.nextInt();
            if (!board.occupied(x, y)){
                System.out.println("no piece at " + x + ", " + y);
                continue;
            }
            p = board.getPiece(x,y);
            if (side == 0){ //is on wights side
                if (!(p instanceof Wight)){ //can only control wights
                    p = null;
                    System.out.println("Not your piece!");
                }

            }
            else if (side == 1){ //is on dragons side
                if (p instanceof Wight) { //cant control wights
                    p = null;
                    System.out.println("Not your piece!");
                }

            }
        }


            System.out.println("Select a place to move(x y)");
            int x = in.nextInt();
            int y = in.nextInt();
            try{
                p.move(x,y);
               break;
            }
            catch (IllegalStateException e){
                System.out.println(e.getMessage());
            }
        }


    }
}
