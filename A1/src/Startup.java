/**
 * Created by daniel on 05/02/18.
 */
public class Startup {
    /*
    * M - number of vehicles
    * N - number of packaged
    * K - max packages/vehicle
    * Y - dimensions in the universe
    */
    public static void main(char args[]) {
        State current = new State(1, 1, 1, 1);

        while (current != null) {
            current = successor(current);
        }
    }
    private static State successor(State prev) {
        return null;
    }
}


