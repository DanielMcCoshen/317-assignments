public class Board {
    Piece[][] state;


    public void move(Piece piece,int x, int y){
        state[x][y] = piece;
        state[piece.getX()][piece.getY()] = null;
    }

    public Boolean occupied(int x, int y){
        if(state[x][y]!=null)return true;
        return false;
    }
}
