public class Dragon extends Piece{

    public Dragon(int x,int y,Board board){
        super(x,y,board);
        this.name = 'D';
        this.value = 25;
    }

    public void move(int x,int y){
        if(valid(x,y)){
            board.move(this,x,y);
            this.x = x;
            this.y = y;
        }
    }

    public boolean valid(int x,int y){
        if(     !(this.x==x && this.y==y) &&
                x<=4 && x>=0 &&
                y<=4 && y>=0 &&
                (this.x==x-1 || this.x==x || this.x==x+1) &&
                (this.y==y-1 || this.y==y || this.y==y+1)
                ) return true;
        else{
            throw new IllegalStateException("Dragon Cannot Move Here");
        }

    }
}
