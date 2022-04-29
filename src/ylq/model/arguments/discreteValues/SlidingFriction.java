package ylq.model.arguments.discreteValues;

/**
 * 传送带材料的选择
 * @author lichenyv
 * @date 2022/3/27
 */
public class SlidingFriction extends DiscreteValue {
    public static final SlidingFriction
            STEEL__NYLON = new SlidingFriction("钢-尼龙", 0.4, 0.075),
            STEEL__STEEL = new SlidingFriction("钢-钢", 0.15, 0.11),
            STEEL__CAST_IRON = new SlidingFriction("钢-铸铁", 0.18, 0.1),
            STEEL__PLASTIC = new SlidingFriction("钢-塑料", 0.04, 0.01);
    public static final SlidingFriction[] SLIDING_FRICTIONS = {
            STEEL__NYLON,
            STEEL__STEEL,
            STEEL__CAST_IRON,
            STEEL__PLASTIC,
    };

    private final Double μWithoutLubrication;
    private final Double μWithLubrication;

    private SlidingFriction(String valueName, Double μWithoutLubrication, Double μWithLubrication) {
        super(valueName);
        this.μWithoutLubrication = μWithoutLubrication;
        this.μWithLubrication = μWithLubrication;
    }

    public Double getμWithoutLubrication() {
        return μWithoutLubrication;
    }

    public Double getμWithLubrication() {
        return μWithLubrication;
    }
}
