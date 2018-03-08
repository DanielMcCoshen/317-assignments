/**
 * Created by Kole on 3/8/2018.
 */
public class Game {
    char Board[][] = new char[5][5];
    int Turn;
    int TurnsPassed;

    /**
     * Initialize the gameboard, set the Wights to go first,
     * 0 turns have passed
     */
    public Game()
    {
        Turn = 2;
        TurnsPassed = 0;

        Board[0][0] = '.';
        Board[0][1] = '.';
        Board[0][2] = 'Q';
        Board[0][3] = '.';
        Board[0][4] = '.';
        Board[1][0] = '.';
        Board[1][1] = 'D';
        Board[1][2] = 'D';
        Board[1][3] = 'D';
        Board[1][4] = '.';
        Board[2][0] = '.';
        Board[2][1] = '.';
        Board[2][2] = '.';
        Board[2][3] = '.';
        Board[2][4] = '.';
        Board[2][4] = '.';
        Board[3][0] = '.';
        Board[3][1] = '.';
        Board[3][2] = '.';
        Board[3][3] = '.';
        Board[3][4] = '.';
        Board[4][0] = 'W';
        Board[4][1] = 'W';
        Board[4][2] = 'W';
        Board[4][3] = 'W';
        Board[4][4] = 'W';
    }

    public String toString() {
        String retVal = "Turn " + TurnsPassed + ", ";
        retVal += "Player " + Turn + "'s Turn:\n\n";
        for (int i = 0; i < 5; i++)
        {
            retVal += "      ";
            for(int j = 0; j < 5; j++)
            {
                retVal += Board[i][j] + " ";
            }
            retVal += "\n";
        }
        return retVal;
    }

    static public void main(String args[])
    {
        Game g = new Game();
        System.out.println(g);
    }
}
