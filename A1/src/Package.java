import java.util.Random;

/**
 * Created by daniel on 05/02/18.
 */
public class Package {
    private float[] source;
    private float[] destination;

    public Package(int dimensions){
        source = new float[dimensions];
        destination = new float[dimensions];
        Random r = new Random();

        for (int i = 0; i < dimensions; i++){
            source[i] = r.nextFloat();
            destination[i] = r.nextFloat();
        }
    }
}
