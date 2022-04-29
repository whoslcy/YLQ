package ylq.model.arguments.discreteValues;

/**
 * 轴径材料
 * @author lichenyv
 * @date 2022/3/27
 */
public class ShaftMaterial extends DiscreteValue {

    public static final ShaftMaterial
            STEEL_45TH = new ShaftMaterial("45号钢", 48.0),
            BEARING_STEEL = new ShaftMaterial("轴承钢", 52.0);
    public static final ShaftMaterial[] SHAFT_MATERIALS = {
            STEEL_45TH,
            BEARING_STEEL,
    };

    private final Double τ;

    private ShaftMaterial(String valueName, Double τ) {
        super(valueName);
        this.τ = τ;
    }

    public Double getτ() {
        return τ;
    }
}
