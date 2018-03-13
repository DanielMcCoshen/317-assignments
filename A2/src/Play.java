import java.io.IOException;

/**
 * Created by daniel on 12/03/18.
 */
public class Play {

    public static void main(String args) throws IOException{
        Board b = new Board();
        Player wight = new HumanPlayer(0,b);
        Player dragon = new HumanPlayer(1, b);

        while (true){
            wight.turn();
            if (wightWin(b)){
                System.out.println("WIGHTS WIN");
                break;
            }
            dragon.turn();
            if(dragonWin(b)){
                System.out.println("DRAGONS WIN");
            }
        }

    }

    private static boolean dragonWin(Board b){
        for (int x = 0; x < 5; x ++){
            if (b.occupied(x, 0) && b.getPiece(x, 0) instanceof Queen){
                return true;
            }
        }
        return  false;
    }

    private static boolean wightWin(Board b){
        for (int x = 0; x < 5; x++){
            for (int y = 0; y < 5; y++ ){
                if(b.occupied(x, y) && b.getPiece(x, y) instanceof Queen){
                    return false;
                }
            }
        }
        return true;
    }
}
