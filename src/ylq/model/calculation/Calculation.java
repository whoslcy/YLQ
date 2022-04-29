package ylq.model.calculation;

import ylq.model.arguments.DiscreteArgument;
import ylq.model.arguments.discreteValues.*;

import static ylq.model.arguments.ContinuousArgument.*;


/**
 * @author lichenyv
 * @date 2022/3/24
 */
public class Calculation {
    /**
     * 这四个是需要计算后输出至GUI的参数
     */
    private Double power,
            rotatingSpeed,
            /**
             * 无需加工键槽处轴径
             */
            shaftDiameterOfPositionWhereNoNeedToMachineKeyway,
            /**
             * 固定齿轮处轴径
             */
            shaftDiameterOfPositionWhereFixingGear;

    /**
     * 以下从用户处得到的参数
     */
    private Double
            /** 连续性变量 */
            numberOfTeeth = NUMBER_OF_TEETH.getValue(),
            powerTransferEfficiency = POWER_TRANSFER_EFFICIENCY.getValue(),
            modulus = MODULUS.getValue(),
            motorResponseTime = MOTOR_RESPONSE_TIME.getValue(),
            averageFruitWeight = AVERAGE_FRUIT_WEIGHT.getValue(),
            productivity = PRODUCTIVITY.getValue(),
            conveyingCarrierWeight = CONVEYING_CARRIER_WEIGHT.getValue(),
            cullingCarrierWeight = CULLING_CARRIER_WEIGHT.getValue(),
            carrierSpacing = CARRIER_SPACING.getValue(),
            chainWeightPerMeter = CHAIN_WEIGHT_PER_METER.getValue(),
            conveyingLineLength = CONVEYING_LINE_LENGTH.getValue(),
            cullingLineLength = CULLING_LINE_LENGTH.getValue();

    /**
     * 离散型变量
     */
    private DiscreteValue
            sortingChannel = DiscreteArgument.SORTING_CHANNEL.getThePickedDiscreteValue(),
            shaftMaterial = DiscreteArgument.SHAFT_MATERIAL.getThePickedDiscreteValue(),
            slidingFriction = DiscreteArgument.SLIDING_FRICTION.getThePickedDiscreteValue(),
            lubrication = DiscreteArgument.LUBRICATION.getThePickedDiscreteValue();

    /**
     * 离散型变量对应的具体值
     */
    private int channelNumber;
    private double τ;
    private double frictionFactor;

    /**
     * 以下为计算 需要输出的参数 需要用到的关键变量
     */
    private Double
            velocity,
            force;

    /**
     * 重力加速度常数
     */
    private final Double g = 9.8;

    public Calculation() {
        channelNumber = ((SortingChannel)sortingChannel).getChannelNumber();
        τ = ((ShaftMaterial) shaftMaterial).getτ();
        frictionFactor = Friction.getFriction(
                (Lubrication) lubrication,
                (SlidingFriction) slidingFriction
        );
    }

    public Calculation calculate() {
        // TODO: 2022/4/13 考虑用 “事务” 来规范顺序？
        // 这两个必须先算，且先 velocity 后 force
       velocity = calculateVelocity();
        force = calculateForce();
        // 这两个必须比下面两个先算，但这两个的顺序可以交换
        rotatingSpeed = calculateRotatingSpeed();
        power = calculatePower();
        // 下面这两个顺序不能乱
        shaftDiameterOfPositionWhereNoNeedToMachineKeyway = calculateShaftDiameterOfPositionWhereNoNeedToMachineKeyway();
        shaftDiameterOfPositionWhereFixingGear = calculateShaftDiameterOfPositionWhereFixingGear();

        return this;
    }

    public Double getPower() {
        return power;
    }

    public Double getRotatingSpeed() {
        return rotatingSpeed;
    }

    public Double getShaftDiameterOfPositionWhereNoNeedToMachineKeyway() {
        return shaftDiameterOfPositionWhereNoNeedToMachineKeyway;
    }

    public Double getShaftDiameterOfPositionWhereFixingGear() {
        return shaftDiameterOfPositionWhereFixingGear;
    }

    private Double calculateForce() {
        double conveyingFruitAndCarrierWeight = (averageFruitWeight + conveyingCarrierWeight) * (conveyingLineLength / carrierSpacing) * channelNumber;
        double cullingFruitAndCarrierWeight = (averageFruitWeight + cullingCarrierWeight) * (cullingLineLength / carrierSpacing) * channelNumber;
        double conveyingChainWeight = chainWeightPerMeter * conveyingLineLength * 2 * channelNumber;
        double cullingChainWeight = chainWeightPerMeter * cullingLineLength * 2 * (channelNumber / 2);
        double totalWeight = conveyingFruitAndCarrierWeight + conveyingChainWeight + cullingFruitAndCarrierWeight + cullingChainWeight;
        double acceleration = velocity / motorResponseTime;
        double force = totalWeight * (frictionFactor * g + acceleration);
        return force;
    }

    private Double calculateVelocity() {
        return (1000 * productivity / (3600 * averageFruitWeight)) * carrierSpacing / channelNumber;
    }


    private Double calculateRotatingSpeed() {
        return velocity * (numberOfTeeth * modulus) / 60;
    }

    private Double calculatePower() {
        return force * velocity / powerTransferEfficiency * 100 * 1000;
    }

    /**
     * @return 无需加工键槽处轴径
     */
    private Double calculateShaftDiameterOfPositionWhereNoNeedToMachineKeyway() {
        return Math.pow(power * 9550000 / (0.2 * τ * rotatingSpeed), 1.0 / 3) * 1.03;
    }

    /**
     * @return 固定齿轮处轴径
     */
    private Double calculateShaftDiameterOfPositionWhereFixingGear() {
        return shaftDiameterOfPositionWhereNoNeedToMachineKeyway * 1.03;
    }

    public static Calculation createAndCalculate() {
        return new Calculation().calculate();
    }
}
