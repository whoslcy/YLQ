package ylq.model.arguments.discreteValues;

/**
 * 分选通道的数量
 *
 * @author lichenyv
 * @date 2022/3/27
 */
public class SortingChannel extends DiscreteValue {
    public static final SortingChannel
            ONE = new SortingChannel("1", 1),
            TWO = new SortingChannel("2", 2),
            FOUR = new SortingChannel("4", 4);
    public static final SortingChannel[] SORTING_CHANNELS = {
            ONE,
            TWO,
            FOUR,
    };
    private final Integer channelNumber;

    private SortingChannel(String valueName, Integer channelNumber) {
        super(valueName);
        this.channelNumber = channelNumber;
    }

    public Integer getChannelNumber() {
        return channelNumber;
    }

}
