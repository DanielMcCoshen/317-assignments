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
        if (x<=4 && x>=0 && y<=4 && y>=0 && state[x][y]!=null) return true;
        return false;
    }

    public Piece getPiece(int x, int y){
        return state[x][y];
    }

    public String toString(){
        String out = " +01234+\n++-----+\n";
        for(int i = 4; i >= 0 ;i--){
            out += i;
            out += '|';
            for(int j = 0; j < 5;j++){
                if(this.occupied(j,i)){
                   out += state[j][i].name;
                }else{
                    out += ' ';
                }
            }
            out += "|\n";
        }
        out += "++-----+";
        return out;
    }
}
