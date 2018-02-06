import java.util.LinkedList;

/**
 * Created by daniel on 05/02/18.
 */
public class State {
    private LinkedList<Truck> trucks;
    private LinkedList<Package> awaitingPickup;
    private LinkedList<Package> enRoute;

    public State(int numTrucks, int numPackages, int maxPackages, int dimentions){
        trucks = new LinkedList<>();
        for (int i = 0; i < numTrucks; i++){
            trucks.add(new Truck(maxPackages, dimentions));
        }
        awaitingPickup = new LinkedList<>();
        for (int i = 0; i < numPackages; i++){
            awaitingPickup.add(new Package(dimentions));
        }
        enRoute = new LinkedList<>();
    }

    public State (State old){ //TODO: DEEP COPY
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
