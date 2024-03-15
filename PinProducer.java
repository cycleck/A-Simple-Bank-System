import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PinProducer {
    private static Set<String> producedPins = new HashSet<>();
    public static String producePin() {
        Random r = new Random();
        String pin;
        do {
            int num = r.nextInt(90000000) + 10000000; 
            pin = String.format("%08d", num); 
        } while (producedPins.contains(pin));
        producedPins.add(pin);
        return pin;
    }

}