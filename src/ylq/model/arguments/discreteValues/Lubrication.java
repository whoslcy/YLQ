package ylq.model.arguments.discreteValues;

/**
 * 传送带的润滑情况
 * @author lichenyv
 * @date 2022/3/27
 */
public class Lubrication extends DiscreteValue {
    public static final Lubrication
            LUBRICATED = new Lubrication("有润滑", true),
            NOT_LUBRICATED = new Lubrication("无润滑", false);
    public static final Lubrication[] LUBRICATIONS = {
            LUBRICATED,
            NOT_LUBRICATED,
    };

    private final Boolean isLubricated;

    private Lubrication(String valueName, boolean isLubricated) {
        super(valueName);
        this.isLubricated = isLubricated;
    }

    public Boolean isLubricated() {
        return isLubricated;
    }
}
