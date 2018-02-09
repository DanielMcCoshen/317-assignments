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

        float danavg = 0, koleavg = 0, fuseavg = 0;
        for (int j = 0; j<1000; j++) {
            State current = new State(4, 3, 2, 2);
            State current2 = new State(current);
            State current3 = new State(current);

            int i = 0;
            while (!isGoal(current)) {
                //System.out.println("STATE " + i + ":\n" + current);
                LinkedList<State> successors = successor(current);
                current = select(successors);
                i++;
            }

            //System.out.println("STATE " + i + ":\n" + current);
            //System.out.println("FINAL COST OF SOLUTION: " + current.cost());
            koleavg += current.cost();


            i = 0;
            while (!isGoal(current2)) {
                //System.out.println("STATE " + i + ":\n" + current2);
                LinkedList<State> successors = successor(current2);
                current2 = select2(successors);
                i++;
            }

            //System.out.println("STATE " + i + ":\n" + current2);
            //System.out.println("FINAL COST OF SOLUTION: " + current2.cost());
            danavg += current2.cost();

            i = 0;
            while (!isGoal(current3)) {
                //System.out.println("STATE " + i + ":\n" + current3);
                LinkedList<State> successors = successor(current3);
                current3 = select3(successors);
                i++;
            }

            //System.out.println("STATE " + i + ":\n" + current3);
            //System.out.println("FINAL COST OF SOLUTION: " + current3.cost());
            fuseavg += current3.cost();
        }

        System.out.println("KOLE AVG: " + (koleavg/1000));
        System.out.println("DAN AVG:  " + (danavg/1000));
        System.out.println("FUSE AVG:  " + (fuseavg/1000));
    }
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

    private static State select (LinkedList<State> states){ //determines the best successor state and returns it
        State current = states.getFirst() ;

        for (State eval : states){
            if (current.cost() + koleHeuristic(current) > eval.cost() + koleHeuristic(eval)){
                current = eval;
            }
        }
        return current;
    }

    private static State select2 (LinkedList<State> states){ //determines the best successor state and returns it
        State current = states.getFirst() ;

        for (State eval : states){
            if (current.cost() + danheuristic(current) > eval.cost() + danheuristic(eval)){
                current = eval;
            }
        }
        return current;
    }

    private static State select3 (LinkedList<State> states){ //determines the best successor state and returns it
        State current = states.getFirst() ;

        for (State eval : states){
            if (current.cost() + fuseHeuristic(current) > eval.cost() + fuseHeuristic(eval)){
                current = eval;
            }
        }
        return current;
    }

    private static float koleHeuristic(State state){
        float retVal = 0.0f;
        for(Truck t : state.getTrucks()) {
            if (Arrays.equals(t.getLocation(), new float[t.getLocation().length])) {
                retVal += Math.sqrt(t.getLocation().length);
            }
        }
        return retVal;
    }

    private static float danheuristic(State state){
        float toRet = 0;
        float max = 0;

        for (Truck t : state.getTrucks()){
            float estimate = 0;
            for (Package p : t.getPackages()){
                estimate += t.distanceTo(p.getDestination());
            }
            for (Package p : state.getAwaitingPickup()){
                estimate += t.distanceTo(p.getSource());
            }
            toRet += estimate;
            if (estimate > max){
                max = estimate;
            }
        }

        return toRet + max;
    }

    private static float fuseHeuristic(State state){
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


