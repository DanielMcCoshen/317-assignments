public class Queen extends Piece{

    public Queen(int x,int y,Board board){
        super(x,y,board);
        this.name = 'Q';
        this.value = 100;
    }

    public void move(int x,int y){
        if(valid(x,y)){
            board.move(this,x,y);
            this.x = x;
            this.y = y;
        }
    }

    public boolean valid(int x,int y){
        if(     x<=4 && x>=0 &&
                y<=4 && y>=0 &&
                (this.x==x-1 || this.x==x || this.x==x+1) &&
                (this.y==y-1 || this.y==y || this.y==y+1)
                ) return true;
        return false;
    }
}
