import java.util.LinkedList;

public class Board {
    Piece[][] state;

    public Board(){
        state = new Piece[5][5];
        for(int i=0;i<5;i++) state[i][0] = new Wight(i,0,this);
        for(int i=1;i<4;i++) state[i][3] = new Dragon(i,3,this);
        state[2][4] = new Queen(2,4,this);
    }

    public Board(Board board){
        state = new Piece[5][5];
        for(int x = 0; x < 5; x++){
            for (int y = 0; y < 5; y++){
                Piece p = board.getPiece(x,y);
                if(p !=null){
                    if (p instanceof Queen){
                        state[x][y] = new Queen(x, y, this);
                    }
                    if (p instanceof Dragon){
                        state[x][y] = new Dragon(x,y,this);
                    }
                    if (p instanceof Wight){
                        state[x][y] = new Wight(x,y,this);
                    }
                }
            }
        }
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
                   out += state[j][i].getName();
                }else{
                    out += ' ';
                }
            }
            out += "|\n";
        }
        out += "++-----+";
        return out;
    }

    public LinkedList<Board> successors(int side, int turns){
        LinkedList<Board> toRet = new LinkedList<>();
        if (turns == 50){
            return toRet;
        }
        if (side == 0){
            //System.out.println("successor for wights");
            for(int x = 0; x < 5; x++){
                for (int y = 0; y < 5; y++){
                    Piece p = getPiece(x,y);
                    if(p !=null){
                        if (p instanceof Wight){
                            //System.out.println("Found piece" + x + " " + y);
                            for(int x2 = 0; x2 < 5; x2++){
                                for (int y2 = 0; y2 < 5; y2++){
                                    //System.out.println("attempting to move");
                                    Board b = new Board(this);
                                    try{
                                        b.getPiece(x,y).move(x2,y2);
                                        toRet.add(b);
                                    }
                                    catch (IllegalStateException e){}
                                }
                            }
                        }
                    }
                }
            }
        }
        else if (side == 1){
           // System.out.println("successor for dragons");
            for(int x = 0; x < 5; x++){
                for (int y = 0; y < 5; y++){
                    Piece p = getPiece(x,y);
                    if(p !=null){
                        if (!(p instanceof Wight)){
                            for(int x2 = 0; x2 < 5; x2++){
                                for (int y2 = 0; y2 < 5; y2++){
                                    Board b = new Board(this);
                                    try{
                                        b.getPiece(x,y).move(x2,y2);
                                        toRet.add(b);
                                    }
                                    catch (IllegalStateException e){}
                                }
                            }
                        }
                    }
                }
            }
        }
        else{
            throw new IllegalStateException("You are on a illegal players turn");
        }
        return toRet;
    }

    public int evaluate(int side, int turn){
        int sum = 0;
        int ourWeight = 2;
        int oppWeight = 1;
        int distWeight = 2;
        int timeWeight = 2;
        if (Play.checkTie(this,turn)){
            return Integer.MIN_VALUE;
        }
        sum += timeWeight*turn/10;
        if (side == 0){
            if (Play.wightWin(this)){
                return Integer.MAX_VALUE;
            }
            else if(Play.dragonWin(this)){
                return Integer.MIN_VALUE;
            }
            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 5 ; j++){
                    if (getPiece(i,j) != null && (getPiece(i,j) instanceof Wight)){
                       sum += ourWeight*getPiece(i,j).getValue();
                    }else if(getPiece(i,j) != null && !(getPiece(i,j) instanceof Wight)){
                        if(getPiece(i,j) instanceof Queen) sum -= distWeight*(5-getPiece(i,j).getY());
                        sum -= oppWeight*getPiece(i,j).getValue();
                    }
                }
            }
        }else if(side == 1){
            if (Play.dragonWin(this)){
                return Integer.MAX_VALUE;
            }
            else if(Play.wightWin(this)){
                return Integer.MIN_VALUE;
            }
            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 5 ; j++){
                    if (getPiece(i,j) != null && !(getPiece(i,j) instanceof Wight)){
                        if(getPiece(i,j) instanceof Queen) sum += distWeight*(5-getPiece(i,j).getY());
                        sum += ourWeight*getPiece(i,j).getValue();
                    }else if(getPiece(i,j) != null && (getPiece(i,j) instanceof Wight)){
                        sum -= oppWeight*getPiece(i,j).getValue();
                    }
                }
            }
        }else{
            throw new IllegalStateException("You are on a illegal players turn");
        }
        return sum;
    }
}
