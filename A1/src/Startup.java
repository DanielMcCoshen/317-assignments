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
        State current = new State(1, 1, 1, 1);


        System.out.println("INITIAL STATE:\n" + current);

        int i = 0;
        while (!isGoal(current)) {
            System.out.println("STATE " + i + ":\n" + current);
            LinkedList<State> successors =  successor(current);
            current = select(successors);
            i++;
        }

        System.out.println("FINAL COST OF SOLUTION: " + current.cost());
    }
    private static LinkedList<State> successor(State prev) { //TODO: enumerates all possible successor states
        LinkedList<State> toRet = new LinkedList<>();
        for (Truck t : prev.getTrucks()){
            float[] oldLoc = new float[t.getLocation().length]; // backup truck location
            System.arraycopy(t.getLocation(), 0, oldLoc, 0, t.getLocation().length);
            float oldDist = t.getTruckDist(); //backup truck distance

            if (!t.isfull()){ //pick up packages
                for (Package p : new LinkedList<Package>(prev.getAwaitingPickup())){
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
                    System.arraycopy(oldLoc,  0, t.getLocation(), 0, t.getLocation().length);
                    t.setTruckDist(oldDist);
                }
            }

            if (t.getPackages().size() > 0){ //drop off packages
                for(Package p : t.getPackages()){
                    //drop off packages
                    t.moveLocation(p.getDestination());
                    t.getPackages().remove(p);
                    prev.getEnRoute().remove(p);
                    //clone state
                    toRet.add(new State(prev));
                    //restore previous values
                    prev.getEnRoute().add(p);
                    t.getPackages().add(p);
                    System.arraycopy(oldLoc,  0, t.getLocation(), 0, t.getLocation().length);
                    t.setTruckDist(oldDist);
                }
            }
            t.moveLocation(new float[oldLoc.length]); // move to the garage;
            toRet.add(new State(prev));
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


