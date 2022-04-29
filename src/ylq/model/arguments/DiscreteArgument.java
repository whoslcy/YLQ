package ylq.model.arguments;

import ylq.model.arguments.discreteValues.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 以多选框的形式供用户选择的离散型变量
 * @author lichenyv
 * @date 2022/4/5
 */
public class DiscreteArgument {
    public static final DiscreteArgument
            FRUIT_VARIETY = new DiscreteArgument("水果种类", FruitVariety.FRUIT_VARIETIES),
            CONVEYING_BEARING_METHOD = new DiscreteArgument("输送段承载方式", ConveyingBearingMethod.BEARING_METHODS),
            CULLING_BEARING_METHOD = new DiscreteArgument("剔除段承载方式", CullingBearingMethod.BEARING_METHODS),
            SORTING_CHANNEL = new DiscreteArgument("分选通道", SortingChannel.SORTING_CHANNELS),
            SLIDING_FRICTION = new DiscreteArgument("滑动摩擦", SlidingFriction.SLIDING_FRICTIONS),
            LUBRICATION = new DiscreteArgument("润滑情况", Lubrication.LUBRICATIONS),
            SHAFT_MATERIAL = new DiscreteArgument("轴径材料", ShaftMaterial.SHAFT_MATERIALS);

    private final String argumentName;
    private final DiscreteValue[] values;
    private DiscreteValue thePickedDiscreteValue;

    public static final DiscreteArgument[] discreteArguments = {
            FRUIT_VARIETY,
            CONVEYING_BEARING_METHOD,
            CULLING_BEARING_METHOD,
            SORTING_CHANNEL,
            SLIDING_FRICTION,
            LUBRICATION,
            SHAFT_MATERIAL,
    };

    private static final Map<String, DiscreteValue> nameValueMap = new HashMap<>();

    protected DiscreteArgument(String argumentName, DiscreteValue[] values) {
        this.argumentName = argumentName;
        this.values = values;
    }

    public String getArgumentName() {
        return argumentName;
    }

    public DiscreteValue[] getValues() {
        return values;
    }

    public String getValueNameOf(int index) {
        return getValues()[index].getValueName();
    }

    public DiscreteValue getThePickedDiscreteValue() {
        return thePickedDiscreteValue;
    }

    public void setThePickedDiscreteValue(DiscreteValue thePickedDiscreteValue) {
        this.thePickedDiscreteValue = thePickedDiscreteValue;
    }

    public static DiscreteValue getTheDiscreteValueOfName(String valueName) {
        if (nameValueMap.isEmpty()) {
            initializeNameValueMap();
        }

        return nameValueMap.get(valueName);
    }

    private static void initializeNameValueMap() {
        for (DiscreteArgument discreteArgument : discreteArguments) {
            for (DiscreteValue value : discreteArgument.getValues()) {
                nameValueMap.put(value.getValueName(), value);
            }
        }
    }
}
