public abstract class Piece {
    int x;
    int y;
    Integer value;
    Character name;
    Board board;

    public Piece(int x, int y,Board board){
        this.x = x;
        this.y = y;
        this.board = board;
    }

    public Piece(Piece piece,Board board){
        this.x = piece.x;
        this.y = piece.y;
        this.value = piece.value;
        this.name = piece.name;
        this.board = board;
    }

    public String toString(){
        return name.toString();
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public abstract void move(int x, int y);

    public abstract boolean valid(int x,int y);

}
