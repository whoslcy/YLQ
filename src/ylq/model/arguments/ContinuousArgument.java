package ylq.model.arguments;

/**
 * 需要用户输入的连续值变量
 * @author lichenyv
 * @date 2022/3/27
 */
public enum ContinuousArgument {
    AVERAGE_FRUIT_WEIGHT("平均果重", "kg"),
    CHAIN_WEIGHT_PER_METER("链条米重", "kg/m"),
    PRODUCTIVITY("生产力要求", "t/h"),
    // 也作为单通道的 “生产线长度”
    CONVEYING_LINE_LENGTH("输送线长度", "m"),
    CULLING_LINE_LENGTH("剔除线长度", "m"),
    // 也作为单通道的 “承载装置重量”
    CONVEYING_CARRIER_WEIGHT("输送段-承载装置重量", "kg"),
    CULLING_CARRIER_WEIGHT("剔除段-承载装置重量", "kg"),
    CARRIER_SPACING("承载装置间距", "m"),
    MOTOR_RESPONSE_TIME("电机响应时间", "s"),
    POWER_TRANSFER_EFFICIENCY("功率传递效率", "%"),
    POWER("功率", "W"),
    // TODO: 2022/4/29 能不能做到更新单位后自动更新算法?
    ROTATING_SPEED("转速", "r/min"),
    NUMBER_OF_TEETH("齿数", ""),
    MODULUS("模数", "mm"),
    SHAFT_DIAMETER_OF_POSITION_WHERE_NO_NEED_TO_MACHINE_KEYWAY("无需加工键槽处轴径", "mm"),
    SHAFT_DIAMETER_OF_POSITION_WHERE_FIXING_GEAR("固定齿轮处轴径", "mm"),
    ;

    private final String argumentName;
    private Double value;
    private String unitName;

    // TODO: 2022/4/18 这个数组需要手动维护，容易出错
    public static final ContinuousArgument[] CONTINUOUS_ARGUMENTS = {
            AVERAGE_FRUIT_WEIGHT,
            CHAIN_WEIGHT_PER_METER,
            PRODUCTIVITY,
            CONVEYING_LINE_LENGTH,
            CULLING_LINE_LENGTH,
            CONVEYING_CARRIER_WEIGHT,
            CULLING_CARRIER_WEIGHT,
            CARRIER_SPACING,
            MOTOR_RESPONSE_TIME,
            POWER_TRANSFER_EFFICIENCY,
            POWER,
            ROTATING_SPEED,
            NUMBER_OF_TEETH,
            MODULUS,
            SHAFT_DIAMETER_OF_POSITION_WHERE_NO_NEED_TO_MACHINE_KEYWAY,
            SHAFT_DIAMETER_OF_POSITION_WHERE_FIXING_GEAR,
    };

    private ContinuousArgument(String argumentName, String unitName) {
        this.argumentName = argumentName;
        this.unitName = unitName;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getArgumentName() {
        return argumentName;
    }

    public String getUnitName() {
        return unitName;
    }

}
