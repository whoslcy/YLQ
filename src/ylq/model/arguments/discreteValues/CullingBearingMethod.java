package ylq.model.arguments.discreteValues;

/**
 * @author lichenyv
 * @date 2022/4/14
 */
public class CullingBearingMethod extends BearingMethod{
    public static final BearingMethod
            FISH_BONE = new CullingBearingMethod("鱼骨式");

    public static final BearingMethod[] BEARING_METHODS = {
            FISH_BONE,
    };

    protected CullingBearingMethod(String valueName) {
        super(valueName);
    }
}
