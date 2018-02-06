import java.util.LinkedList;

/**
 * Created by daniel on 05/02/18.
 */
public class Truck {
    private LinkedList<Package> packages;
    private float[] location;
    private int maxPackages;

    public Truck(int maxPackages, int dimentions){
        packages = new LinkedList<>();
        location = new float[dimentions];
        this.maxPackages = maxPackages;

        for (int i =0; i < dimentions; i++){
            location[i] = 0;
        }
    }
}
