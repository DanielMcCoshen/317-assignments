import java.io.IOException;

/**
 * Created by daniel on 12/03/18.
 */
public class Play {

    public static void main(String args[]) throws IOException{
        Board b = new Board();
        Player wight = new MiniMaxPlayer(0,b);
        Player dragon = new MiniMaxPlayer(1, b);

        while(true){
            int turns = 0;
            b = wight.turn();
            dragon.setBoard(b);

            if (wightWin(b)){
                System.out.println("WIGHTS WIN");
                break;
            }

            b = dragon.turn();
            wight.setBoard(b);
            if(dragonWin(b)){
                System.out.println("DRAGONS WIN");
                break;
            }
            if(checkTie(b, turns)){
                System.out.println("DRAW GAME");
                break;
            }
        }

    }

    public static boolean dragonWin(Board b){
        for (int x= 0; x < 5; x ++){
            if (b.occupied(x, 0) && (b.getPiece(x, 0) instanceof Queen)){
                return true;
            }
        }
        return  false;
    }

    public static boolean wightWin(Board b){
        for (int x = 0; x < 5; x++){
            for (int y = 0; y < 5; y++ ){
                if(b.occupied(x, y) && b.getPiece(x, y) instanceof Queen){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkTie(Board b, int turns){
        return b.successors(0, turns).size() == 0 || b.successors(1, turns).size() == 0;
    }
}
