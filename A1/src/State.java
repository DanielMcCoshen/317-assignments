import java.util.LinkedList;

/**
 * Created by daniel on 05/02/18.
 */
public class State {
    private LinkedList<Truck> trucks;
    private LinkedList<Package> awaitingPickup;
    private LinkedList<Package> enRoute;
    protected int totalDist;

    public State(int numTrucks, int numPackages, int truckCapacity, int dimensions){
        trucks = new LinkedList<>();
        for (int i = 0; i < numTrucks; i++){
            trucks.add(new Truck(truckCapacity, dimensions));
        }
        awaitingPickup = new LinkedList<>();
        for (int i = 0; i < numPackages; i++){
            awaitingPickup.add(new Package(dimensions));
        }
        enRoute = new LinkedList<>();
        totalDist = 0;
    }

    public State (State old){
        trucks = new LinkedList<>();
        enRoute = new LinkedList<>();
        awaitingPickup = new LinkedList<>();
        totalDist = old.totalDist;

        for (Truck t : old.getTrucks()){
            Truck tNew = new Truck(t);
            trucks.add(tNew);
            for (Package p : t.getPackages()){
                Package pNew = new Package(p);
                tNew.getPackages().add(pNew);
                enRoute.add(pNew);
            }
        }
        for (Package p : old.getAwaitingPickup()){
            awaitingPickup.add(new Package(p));
        }
    }

    public LinkedList<Truck> getTrucks() {
        return trucks;
    }

    public LinkedList<Package> getAwaitingPickup() {
        return awaitingPickup;
    }

    public LinkedList<Package> getEnRoute() {
        return enRoute;
    }


}
