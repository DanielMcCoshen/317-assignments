public class Wight extends Piece{

    public Wight(int x,int y,Board board){
        super(x,y,board);
        this.name = 'W';
        this.value = 2;
    }

    public void move(int x,int y){
        if(valid(x,y)){
            board.move(this,x,y);
            this.x = x;
            this.y = y;
        }
    }

    public boolean valid(int x,int y){
        if(x<=4 && x>=0 && y<=4 && y>=0 && !(board.getPiece(x,y) instanceof Wight)){
            if(board.occupied(x,y) && (this.x==x-1 || this.x==x+1) && (this.y==y-1 || this.y==y+1)){
                return true;
            }else if(   !board.occupied(x,y) &&
                        ((this.x==x && (this.y==y-1 || this.y==y+1)) !=
                        (this.y==y && (this.x==x-1 || this.x==x+1))) ){
                return true;
            }
        }
        throw new IllegalStateException("Wight Cannot Move Here");
    }
}
