import java.util.LinkedList;

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
            LinkedList<State> successors =  successor(current);

            current = select(successors);

        }
    }
    private static LinkedList<State> successor(State prev) { //TODO: enumerates all possible successor states
        LinkedList<State> toRet = new LinkedList<>();
        for (Truck t : prev.getTrucks()){
            float[] oldLoc = new float[t.getLocation().length]; // backup truck location
            System.arraycopy(t.getLocation(), 0, oldLoc, 0, t.getLocation().length);
            float oldDist = t.getTruckDist(); //backup truck distance

            if (!t.isfull()){ //pick up packages

            }

            if (t.getPackages().size() > 0){ //drop off packages

            }

        }
        return toRet;
    }

    private static State select (LinkedList<State> states){ //determines the best successor state and returns it
        State current = states.getFirst() ;

        for (State eval : states){
            if (current.cost() + heuristic(current) > eval.cost() + heuristic(eval)){
                current = eval;
            }
        }
        return current;
    }

    private static float heuristic(State state){ //TODO: estimates cost to goal
        return 0;
    }

}


