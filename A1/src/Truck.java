import java.security.InvalidParameterException;
import java.util.LinkedList;

/**
 * Created by daniel on 05/02/18.
 */
public class Truck {
    private LinkedList<Package> packages;
    private float[] location;
    private int maxPackages;
    private float truckDist;

    public Truck(int maxPackages, int dimensions) {
        packages = new LinkedList<>();
        location = new float[dimensions];
        truckDist = 0.0f;
        this.maxPackages = maxPackages;

        for (int i = 0; i < dimensions; i++) {
            location[i] = 0;
        }
    }

    public Truck(Truck old) {
        packages = new LinkedList<>();
        location = new float[old.getLocation().length];
        System.arraycopy(old.getLocation(), 0, location, 0, old.getLocation().length);
        maxPackages = old.getMaxPackages();
        truckDist = old.getTruckDist();
    }

    public float[] getLocation() {
        return location;
    }

    public int getMaxPackages() {
        return maxPackages;
    }

    public LinkedList<Package> getPackages() {
        return packages;
    }

    public void moveLocation(float[] newLocate) {
        truckDist = truckDist + Math.abs(distanceTo(newLocate));
        System.arraycopy(newLocate, 0, location, 0, location.length);
    }

    public float distanceTo(float[] other) {
        if (location.length != other.length) {
            throw new InvalidParameterException();
        }
        float total = 0.0f;

        for (int i = 0; i < location.length; i++) {
            total += Math.pow((location[i] - other[i]), 2);
        }
        return (float) Math.sqrt(total);
    }

    public float getTruckDist() {
        return truckDist;
    }

    public void setTruckDist(float truckDist) {
        this.truckDist = truckDist;
    }

    public boolean isFull(){
        return packages.size() >= maxPackages;
    }
}
