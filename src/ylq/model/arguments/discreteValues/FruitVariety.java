package ylq.model.arguments.discreteValues;

/**
 * 水果的种类
 * @author lichenyv
 * @date 2022/3/27
 */
public class FruitVariety extends DiscreteValue {
    public static final FruitVariety
            PITAYA = new FruitVariety("火龙果");
    public static final FruitVariety[] FRUIT_VARIETIES = {
            PITAYA,
    };

    private FruitVariety(String valueName) {
        super(valueName);
    }
}
