import java.io.IOException;

public class MiniMaxPlayer extends Player {
    int turns = 0;
    @Override
    public void turn() throws IOException {
        System.out.println(board);

        Board b = minimax(board, side, side, true, turns);


        turns ++;
    }

    Board minimax(Board b, int player, int eval, boolean max, int level){
        if (b.evaluate(player, turns) == 0 ||b.evaluate(player, turns) == Integer.MAX_VALUE ){ //base case
            return b;
        }
        else if (max){
            Board toRet;
            for (Board toCheck : b.successors(eval, level)){

            }
        }
    }
}
