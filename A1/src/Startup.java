import java.util.Arrays;
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
    public static void main(String args[]) {
        int M = 4;
        int N = 8;
        int K = 2;
        int Y = 2;

        State current = new State(M, N, K, Y);

        int i = 0;
        while (!isGoal(current)) {
            System.out.println("STATE " + i + ":\n" + current);
            LinkedList<State> successors = successor(current);
            current = select(successors);
            i++;
        }
        System.out.println("STATE " + i + ":\n" + current);

        System.out.println("Cost for route with "+  M + " trucks, " + N + " packages, " + K + " truck capacity, and " + Y + " dimensions: " + current.cost());
    }

    /**
     * Find all possible states we can reach from the given state
     * @param prev The state which we are generating all potential future states from
     * @return a LinkedList containing all possible states we can reach from the given state
     */
    private static LinkedList<State> successor(State prev) {
        LinkedList<State> toRet = new LinkedList<>();
        for (Truck t : prev.getTrucks()) {
            float[] oldLoc = new float[t.getLocation().length]; // backup truck location
            System.arraycopy(t.getLocation(), 0, oldLoc, 0, t.getLocation().length);
            float oldDist = t.getTruckDist(); //backup truck distance

            if (!t.isFull()) { //pick up packages
                for (Package p : new LinkedList<Package>(prev.getAwaitingPickup())) {
                    //pick up package
                    t.moveLocation(p.getSource());
                    t.getPackages().add(p);
                    prev.getAwaitingPickup().remove(p);
                    prev.getEnRoute().add(p);
                    //clone state
                    toRet.add(new State(prev));
                    //restore previous values
                    prev.getEnRoute().remove(p);
                    prev.getAwaitingPickup().add(p);
                    t.getPackages().remove(p);
                    System.arraycopy(oldLoc, 0, t.getLocation(), 0, t.getLocation().length);
                    t.setTruckDist(oldDist);
                }
            }

            if (t.getPackages().size() > 0) { //drop off packages
                for (Package p : new LinkedList<Package>(t.getPackages())) {
                    //drop off packages
                    t.moveLocation(p.getDestination());
                    t.getPackages().remove(p);
                    prev.getEnRoute().remove(p);
                    //clone state
                    toRet.add(new State(prev));
                    //restore previous values
                    prev.getEnRoute().add(p);
                    t.getPackages().add(p);
                    System.arraycopy(oldLoc, 0, t.getLocation(), 0, t.getLocation().length);
                    t.setTruckDist(oldDist);
                }
            }
            if (!Arrays.equals(new float[t.getLocation().length], t.getLocation())) {
                //move to garage
                t.moveLocation(new float[oldLoc.length]); // move to the garage;
                toRet.add(new State(prev));
                //reset location
                System.arraycopy(oldLoc, 0, t.getLocation(), 0, t.getLocation().length);
                t.setTruckDist(oldDist);
            }
        }
        return toRet;
    }

    /**
     * Chooses one of several states which will have the lowest long-term cost according to the heuristics, as well as the
     * immediate cost of the given states
     * @param states a list of all possible states we can reach from the current state
     * @return the state which will result in the lowest possible cost, according to our heuristics
     */
    private static State select (LinkedList<State> states){ //determines the best successor state and returns it
        State current = states.getFirst() ;

        for (State eval : states){
            if (current.cost() + heuristic(current) > eval.cost() + heuristic(eval)){
                current = eval;
            }
        }
        return current;
    }

    /**
     * Compares state to our two heuristics, and gives a cost value
     * @param state The state being evaluated
     * @return The estimated long-term cost of the state
     */
    private static float heuristic(State state){
        float toRet = 0;
        float max = 0;
        float garageScore = 0;

        for (Truck t : state.getTrucks()){

            if ((state.getAwaitingPickup().isEmpty() && t.getPackages().isEmpty())) {
                float estimate = 0;
                for (Package p : t.getPackages()) {
                    estimate += t.distanceTo(p.getDestination());
                }
                for (Package p : state.getAwaitingPickup()) {
                    estimate += t.distanceTo(p.getSource());
                }
                toRet += estimate;
                if (estimate > max) {
                    max = estimate;
                }
            }
            else if (Arrays.equals(t.getLocation(), new float[t.getLocation().length])) {
                garageScore += Math.sqrt(t.getLocation().length);
            }
        }

        return toRet + garageScore + max;
    }

    private static boolean isGoal(State eval){ //TODO: determine if at goal
        boolean atGarage = true;
        for (Truck t : eval.getTrucks()){
            float[] garage = new float[t.getLocation().length];
            if (!Arrays.equals(garage, t.getLocation())){
                atGarage = false;
            }
        }

        return atGarage && eval.getAwaitingPickup().size() == 0 && eval.getEnRoute().size() == 0;
    }

}
