package ylq.model.arguments.discreteValues;

/**
 * @author lichenyv
 * @date 2022/4/14
 */
public class ConveyingBearingMethod extends BearingMethod{
    public static final BearingMethod
            ROLLER = new ConveyingBearingMethod("滚轮式");

    public static final BearingMethod[] BEARING_METHODS = {
            ROLLER,
    };

    protected ConveyingBearingMethod(String valueName) {
        super(valueName);
    }
}
