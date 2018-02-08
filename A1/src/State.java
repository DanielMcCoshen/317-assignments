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

    public float cost(){ //this is the cost function given in the assignment spec
        float max = 0;
        float total = 0;
        for (Truck t : trucks){
            total += t.getTruckDist();
            if (t.getTruckDist() > max){
                max = t.getTruckDist();
            }
        }
        return total + max;
    }

    public String toString()
    {
        String retVal = "";
        int i = 1;
        for (Truck t : trucks)
        {
            retVal += "Truck " + i + ";\nLocation: ";
            for(int j = 0; j < t.getLocation().length; j++)
            {
                retVal += t.getLocation()[j] + " ";
            }
            retVal += "\nPackage Destinations:";
            for(Package p : t.getPackages())
            {
                retVal += "\n";
                for(int j = 0; j < p.getDestination().length; j++)
                {
                    retVal += " " + p.getDestination()[j];
                }
            }
            retVal += "\nDistance travelled: " + t.getTruckDist() + "\n\n";
            i++;
        }

        i = 1;
        for (Package p : awaitingPickup)
        {
            retVal += "Package awaiting pickup at: ";
            for(int j = 0; j < p.getSource().length; j++)
            {
                retVal += " " + p.getSource()[j];
            }
            retVal += "\nPackage destination: ";
            for(int j = 0; j < p.getDestination().length; j++)
            {
                retVal += " " + p.getDestination()[j];
            }
            retVal += "\n\n";
            i++;
        }

        return retVal;
    }
}
