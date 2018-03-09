/**
 * Created by Kole on 3/8/2018.
 */
public class A2Coord {
    int row;
    int col;

    public A2Coord(int r, int c)
    {
        row = r;
        col = c;
    }

    public void set(int r, int c)
    {
        row = r;
        col = c;
    }

    /**
     * Check to see if the given coordinate is one space away (excluding diagonally)
     * @param compare Coordinate we are comparing
     * @return true if it is adjacent, false otherwise
     */
    public boolean adjTo(A2Coord compare)
    {
        if(row == compare.row && (col == compare.col - 1 || col == compare.col + 1))
        {
            return true;
        }
        return col == compare.col && (row == compare.row - 1 || row == compare.row + 1);
    }

    /**
     * Check to see if the given coordinate is one space away diagonally
     * @param compare Coordinate we are comparing
     * @return true if it is diagonally adjacent, false otherwise
     */
    public boolean diagFrom(A2Coord compare)
    {
        return (row == compare.row - 1 || row == compare.row + 1) && (col == compare.col - 1 || col == compare.col + 1);
    }
}
