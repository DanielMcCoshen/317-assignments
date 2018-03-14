import java.io.IOException;
import java.util.LinkedList;

public class MiniMaxPlayer extends Player {
    int turns = 0;

    public MiniMaxPlayer(int side, Board board){
        this.side = side;
        this.board = board;
        turns = 0;
    }
    @Override
    public Board turn() throws IOException {
        int maxLevel = 3;
        System.out.println(board);

        if (side == 0){
            System.out.println("Wights' turn");
        }
        else if (side == 1){
            System.out.println("Dragons' turn");
        }

        System.out.println("Thinking..."); //choose an action
        LinkedList<Board> moves = board.successors(side, turns);
        System.out.println("Found possible moves");
        Board current = moves.getFirst();
        System.out.print("Creating first value");
        int currentval = minimax(moves.getFirst(), (side +1 )%2, false, turns+ 1, maxLevel);
        int i = 1;
        for (Board b : moves){
            System.out.println("Evaluating move " + i + " of " + moves.size());
            int check = minimax(b, (side +1 )%2, false, turns+ 1, maxLevel);
            if (check > currentval){
                currentval = check;
                current = b;
            }
            i++;
        }
        turns ++;
        return current;

    }

    int minimax(Board b, int eval, boolean max, int level, int maxLevel){
        if (level == maxLevel){ //base case 1: at maximum search level
            return b.evaluate(side, level);
        }
        else if(b.evaluate(side, level) == 0 || b.evaluate(side, level) == Integer.MAX_VALUE){//base case: 2 win or loss
            return b.evaluate(side,level);
        }
        else if (max){ //recursive case 1: max of nodes below
            int toRet = 0;
            for (Board check: b.successors(eval, level)){
                int temp = minimax(check, (eval +1) % 2, !max, level + 1, maxLevel);
                if (temp > toRet){
                    toRet = temp;
                }
            }
            return  toRet;
        }
        else{ //recursive case 2: min of nodes below
            int toRet = 0;
            for (Board check: b.successors(eval, level)){
                int temp = minimax(check, (eval +1) % 2, !max, level + 1, maxLevel);
                if (temp < toRet){
                    toRet = temp;
                }
            }
            return  toRet;
        }
    }
}
