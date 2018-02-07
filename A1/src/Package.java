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
        Random r = new Random(); // TODO: Bound between 0 and 1 (is already bound. nextFloat gives bbtween 0 and 1)

        for (int i = 0; i < dimensions; i++){
            source[i] = r.nextFloat();
            destination[i] = r.nextFloat();
        }
    }
    public Package(Package old){
        source = new float[old.getSource().length];
        destination = new float[old.getDestination().length];
        for (int i = 0; i < old.getSource().length; i++){
            source[i] = old.getSource()[i];
            destination[i] = old.getDestination()[i];
        }
    }

    public float[] getSource() {
        return source;
    }

    public float[] getDestination() {
        return destination;
    }

}
