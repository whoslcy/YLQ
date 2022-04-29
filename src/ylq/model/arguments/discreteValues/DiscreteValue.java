package ylq.model.arguments.discreteValues;

/**
 * 离散型变量的具体值
 * @author lichenyv
 * @date 2022/4/6
 */
public abstract class DiscreteValue {
    private final String valueName;


    protected DiscreteValue(String valueName) {
        this.valueName = valueName;
    }

    public String getValueName() {
        return valueName;
    }
}
