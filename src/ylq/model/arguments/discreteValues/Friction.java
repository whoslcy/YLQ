package ylq.model.arguments.discreteValues;

/**
 * @author lichenyv
 * @date 2022/4/6
 */
public class Friction {
    public static double getFriction(Lubrication lubrication, SlidingFriction slidingFriction) {
        if (lubrication.isLubricated()) {
            return slidingFriction.get╬╝WithLubrication();
        } else {
            return slidingFriction.get╬╝WithoutLubrication();
        }
    }
}
