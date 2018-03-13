public class Board {
    Piece[][] state;

    public Board(){
        state = new Piece[5][5];
        for(int i=0;i<5;i++) state[i][0] = new Wight(i,0,this);
        for(int i=1;i<4;i++) state[i][3] = new Dragon(i,3,this);
        state[2][4] = new Queen(2,4,this);
    }

    public Board(Board board){

    }

    public void move(Piece piece,int x, int y){
        state[x][y] = piece;
        state[piece.getX()][piece.getY()] = null;
    }

    public Boolean occupied(int x, int y){
        if(state[x][y]!=null)return true;
        return false;
    }

}
