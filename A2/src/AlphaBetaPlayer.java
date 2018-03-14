import java.io.IOException;
import java.util.LinkedList;

public class AlphaBetaPlayer extends Player {
    int turns = 0;

    public AlphaBetaPlayer(int side, Board board){
        this.side = side;
        this.board = board;
        turns = 0;
    }
    @Override
    public Board turn() throws IOException {
        int maxLevel = 6;
        System.out.println(board);

        if (side == 0){
            System.out.println("Wights' turn " + turns);
        }
        else if (side == 1){
            System.out.println("Dragons' turn " + turns);
        }

        System.out.println("Thinking..."); //choose an action
        LinkedList<Board> moves = board.successors(side, turns);
        System.out.println("Found possible moves");
        Board current = moves.getFirst();
        System.out.println("Creating first value");
        int currentval = minimax(moves.getFirst(), (side +1 )%2, false, turns+ 1, maxLevel + turns, Integer.MIN_VALUE, Integer.MAX_VALUE);
        int i = 1;
        System.out.println("Evaluating " + moves.size() + " moves.");
        for (Board b : moves){
            //System.out.println("Evaluating move " + i + " of " + moves.size());
            int check = minimax(b, (side +1 )%2, false, turns+ 1, maxLevel + turns, Integer.MIN_VALUE, Integer.MAX_VALUE);
           // System.out.println(check);
            if (check > currentval){
                currentval = check;
                current = b;
            }
            i++;
        }
        turns ++;
        return current;

    }

    int minimax(Board b, int eval, boolean max, int level, int maxLevel, int alpha, int beta){
        if (level >= maxLevel){ //base case 1: at maximum search level
            return b.evaluate(side, level);
        }
        if(b.evaluate(side, level) == 0 || b.evaluate(side, level) == Integer.MAX_VALUE){//base case: 2 win or loss
            return b.evaluate(side,level);
        }
        else if (max){ //recursive case 1: max of nodes below
            int toRet = Integer.MIN_VALUE;
            for (Board check: b.successors(eval, level)){
                int temp = minimax(check, (eval +1) % 2, !max, level + 1, maxLevel, alpha, beta);
                if (temp > toRet){
                    toRet = temp;
                }
                alpha = Integer.max(toRet, alpha);
                if(beta <= alpha)
                {
                    break;
                }
            }
            return  toRet;
        }
        else{ //recursive case 2: min of nodes below
            int toRet = Integer.MAX_VALUE;
            for (Board check: b.successors(eval, level)){
                int temp = minimax(check, (eval +1) % 2, !max, level + 1, maxLevel, alpha, beta);
                if (temp < toRet){
                    toRet = temp;
                }
                beta = Integer.min(beta, toRet);
                if(beta <= alpha)
                {
                    break;
                }
            }
            return  toRet;
        }
    }
}
