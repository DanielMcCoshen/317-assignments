/**
 * Created by Kole on 3/8/2018.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    public Game clone()
    {
        Game clone = new Game();
        clone.Board = Board;
        clone.Turn = Turn;
        clone.TurnsPassed = TurnsPassed;
        return clone;
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

    /**
     * Declare the winner of the match
     * returns 1 if player 1 wins
     * returns 2 if player 2 wins
     * returns 3 if match is a draw
     * returns 0 if match is not complete
     */
    public int victor()
    {
        if(TurnsPassed > 49)
        {
            // Time's up, draw
            return 3;
        }
        for(int i = 0; i < 5; i++)
        {
            // If Queen is in bottom row, P1 wins
            if(Board[4][i] == 'Q')
            {
                return 1;
            }
        }
        // Check if the queen is in one of the remaining rows
        for(int i = 0; i< 4; i++)
        {
            for(int j = 0; j < 5; j++)
            {
                if(Board[i][j] == 'Q')
                {
                    // Queen is still on the board and has not reached the bottom row, keep playing
                    return 0;
                }
            }
        }
        // Queen is not on the board, P2 wins
        return 2;
    }

    public boolean validMove(A2Coord before, A2Coord after)
    {
        if(before.row > 4 || before.row < 0 || before.col > 4 || before.col < 0 ||
                after.row > 4 || after.row < 0 || after.col > 4 || after.col < 0)
        {
            return false;
        }
        if(Turn == 1)
        {
            if(Board[before.row][before.col] != 'Q' && Board[before.row][before.col] != 'D')
            {
                return false;
            }
            if(Board[after.row][after.col] != 'W' && Board[after.row][after.col] != '.')
            {
                return false;
            }
            return before.adjTo(after) || before.diagFrom(after);
        }
        else
        {
            if(Board[before.row][before.col] != 'W')
            {
                return false;
            }
            if(Board[after.row][after.col] == '.')
            {
                return before.adjTo(after);
            }
            if(Board[after.row][after.col] == 'W')
            {
                return false;
            }
            return before.diagFrom(after);
        }
    }

    public void move(A2Coord before, A2Coord after)
    {
        Board[after.row][after.col] = Board[before.row][before.col];
        Board[before.row][before.col] = '.';
        TurnsPassed++;
        Turn = (Turn%2) + 1;
    }

    static public void main(String args[])
    {
        Game g = new Game();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter String");

        while(g.victor() == 0)
        {
            System.out.println(g);
            A2Coord piece = new A2Coord(-1, -1);
            A2Coord destination = new A2Coord(-1, -1);

            while(!g.validMove(piece, destination))
            {
                System.out.println("Coordinates of piece you wish to move (separate by space): ");
                String userIn;
                try
                {
                    userIn = br.readLine();
                }
                catch(IOException e)
                {
                    System.out.println("Error in input reading.");
                    return;
                }
                String[] rawCoord = userIn.split(" ");
                try
                {
                    int row = Integer.parseInt(rawCoord[0]);
                    int col = Integer.parseInt(rawCoord[1]);
                    piece.set(row, col);
                }
                catch (Exception e)
                {
                    break;
                }


                System.out.println("Coordinates of piece destination: ");
                try
                {
                    userIn = br.readLine();
                }
                catch(IOException e)
                {
                    System.out.println("Error in input reading.");
                    return;
                }
                rawCoord = userIn.split(" ");
                try
                {
                    int row = Integer.parseInt(rawCoord[0]);
                    int col = Integer.parseInt(rawCoord[1]);
                    destination.set(row, col);
                }
                catch (Exception e)
                {
                    break;
                }
            }
            g.move(piece, destination);
            System.out.println("\n");
        }

        if(g.victor() == 3)
        {
            System.out.println("Game was a draw.");
        }
        else
        {
            System.out.println("Player " + g.victor() + " has won.");
        }
        return;
    }
}
