import java.security.InvalidParameterException;
import java.util.LinkedList;

/**
 * Created by daniel on 05/02/18.
 */
public class Truck {
    private LinkedList<Package> packages;
    private float[] location;
    private int maxPackages;
    private int truckDist;

    public Truck(int maxPackages, int dimensions){
        packages = new LinkedList<>();
        location = new float[dimensions];
        truckDist = 0;
        this.maxPackages = maxPackages;

        for (int i =0; i < dimensions; i++){
            location[i] = 0;
        }
    }
    public Truck(Truck old){
        packages = new LinkedList<>();
        location = new float[old.getLocation().length];
        for (int i = 0; i < old.getLocation().length; i++){
            location[i] = old.getLocation()[i];
        }
        maxPackages = old.getMaxPackages();

    }

    public float[] getLocation(){
        return location;
    }

    public int getMaxPackages() {
        return maxPackages;
    }

    public LinkedList<Package> getPackages() {
        return packages;
    }

    public void moveLocation(float[] newLocate){
        truckDist += distanceTo(newLocate);
        for(int i = 0; i < location.length; i++)
        {
            location[i] = newLocate[i];
        }
    }

    public float distanceTo (float[] other){
        if (location.length != other.length){
            throw new InvalidParameterException();
        }
        float total = 0.0f;

        for (int i = 0; i < location.length; i++){
            total += Math.pow((location[i] - other[i]), 2);
        }
        return (float) Math.sqrt(total);
    }

    public int getTruckDist() {
        return truckDist;
    }
}
