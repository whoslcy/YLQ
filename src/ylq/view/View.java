package ylq.view;

import ylq.model.arguments.ContinuousArgument;
import ylq.model.arguments.DiscreteArgument;
import ylq.model.arguments.discreteValues.*;
import ylq.model.calculation.Calculation;
import ylq.view.inputBlocks.Headline;
import ylq.view.inputBlocks.InputBlocksWithHeadLine;
import ylq.view.inputBlocks.inputBlockWithLabel.ComboBoxWithLabel;
import ylq.view.inputBlocks.inputBlockWithLabel.InputBlockWithLabel;
import ylq.view.inputBlocks.inputBlockWithLabel.TextFieldWithLabel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lichenyv
 * @date 2022/3/24
 */
public class View extends JFrame implements MyLayout<View> {
    /**
     * 用户页面的几个区域
     */
    private JPanel
            title,
            fruitVarietyCombo,
            sortingChannelCombo,
            productionArgumentsCards,
            productionArgumentsCard1,
            productionArgumentsCard2,
            productionArgumentsCard4,
            gearArguments,
            motorArguments,
            shaftDiameterReference,
            submitButton;

    private final String
            GENERAL_TITLE = "数字化的传动设计与校核",
            SHENG_CHAN_CAN_SHU = "一、生产参数",
            SHU_SONG_DUAN = "I. 输送段",
            TI_CHU_DUAN = "II. 剔除段",
            CHI_LUN_CAN_SHU = "二、齿轮参数",
            DIAN_JI_CAN_SHU = "电机参考值",
            CAN_KAO_ZHOU_JING = "三、参考轴径",
            COMMIT_BUTTON_NAME = "计算";

    /**
     * Special strings
     */
    private final String
            primitiveNameOfConveyingLineLength = ContinuousArgument.CONVEYING_LINE_LENGTH.getArgumentName(),
            primitiveNameOfConveyingCarrierWeight = ContinuousArgument.CONVEYING_CARRIER_WEIGHT.getArgumentName(),
            newNameOfConveyingLineLengthWhenChannelOne = "生产线长度",
            newNameOfConveyingCarrierWeightWhenChannelOne = "承载装置重量";


    /**
     * 将 argumentName 作为索引，获取InputBlock
     * InputBlock 涉及到业务逻辑，不能和页面设计耦合在一起，需要集中单独存放，以便获取来自用户选择或输入的值
     */
    private Map<String, InputBlockWithLabel> nameBlockMap = new HashMap<>();

    public View() {
        for (ContinuousArgument continuousArgument : ContinuousArgument.CONTINUOUS_ARGUMENTS) {
            nameBlockMap.put(
                    continuousArgument.getArgumentName(),
                    TextFieldWithLabel.createWithLayout(continuousArgument.getArgumentName(), continuousArgument.getUnitName())
            );
        }
        for (DiscreteArgument discreteArgument : DiscreteArgument.discreteArguments) {
            ComboBoxWithLabel comboBoxWithLabel = ComboBoxWithLabel.createWithLayout(
                    discreteArgument.getArgumentName(),
                    discreteArgument.getValues());
            nameBlockMap.put(discreteArgument.getArgumentName(), comboBoxWithLabel);
            // 分选通道的ItemListener比较复杂，单独添加
            if (discreteArgument.equals(DiscreteArgument.SORTING_CHANNEL)) {
                continue;
            }
            comboBoxWithLabel.getComboBox().addItemListener(
                    // TODO: 2022/4/20 这里出错，应该放DiscreteValue，但却放了一个String，这种错误不容易发现，代码不该这样写
                    e -> discreteArgument.setThePickedDiscreteValue(
                            DiscreteArgument.getTheDiscreteValueOfName((String)e.getItem())));
        }

        title = createTitle();
        fruitVarietyCombo = createFruitVariety();
        productionArgumentsCard1 = new JPanel(new BorderLayout());
        productionArgumentsCard2 = new JPanel(new BorderLayout());
        productionArgumentsCard4 = new JPanel(new BorderLayout());
        // createProductionArguments 和 sortingChannelCombo 里有用到上面三个card, 必须先创建三个card，再创建cards
        sortingChannelCombo = createSortingChannel();
        productionArgumentsCards = createProductionArguments();
        gearArguments = createGearArguments();
        motorArguments = createMotorArguments();
        shaftDiameterReference = createShaftDiameterReference();
        submitButton = createSubmitButton();

    }

    private JPanel createSubmitButton() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton(COMMIT_BUTTON_NAME);

        buttonPanel.add(submitButton);
        submitButton.addActionListener(e -> {
            if (DiscreteArgument.SORTING_CHANNEL.getThePickedDiscreteValue().equals(SortingChannel.ONE)) {
                ((TextFieldWithLabel) nameBlockMap.get(ContinuousArgument.CULLING_CARRIER_WEIGHT.getArgumentName())).getTextField().setText("0");
                ((TextFieldWithLabel) nameBlockMap.get(ContinuousArgument.CULLING_LINE_LENGTH.getArgumentName())).getTextField().setText("0");
            }

            passArgumentValueToCalculation();
            Calculation calculation = Calculation.createAndCalculate();
            showResult(calculation);
        });

        return buttonPanel;
    }

    private void passArgumentValueToCalculation() {
        for (ContinuousArgument continuousArgument : ContinuousArgument.CONTINUOUS_ARGUMENTS) {
            // TODO: 2022/4/20 重复代码
            if (continuousArgument.equals(ContinuousArgument.POWER)) {
                continue;
            }
            if (continuousArgument.equals(ContinuousArgument.ROTATING_SPEED)) {
                continue;
            }
            if (continuousArgument.equals(ContinuousArgument.SHAFT_DIAMETER_OF_POSITION_WHERE_NO_NEED_TO_MACHINE_KEYWAY)) {
                continue;
            }
            if (continuousArgument.equals(ContinuousArgument.SHAFT_DIAMETER_OF_POSITION_WHERE_FIXING_GEAR)) {
                continue;
            }
            continuousArgument.setValue(Double.valueOf(((TextFieldWithLabel)nameBlockMap.get(continuousArgument.getArgumentName())).getTextField().getText()));
        }
        // DiscreteArgument 值的设置由 ItemListener 来完成
    }

    private void showResult(Calculation calculation) {
        // TODO: 2022/4/20 重复代码还是太多了 
        // TODO: 2022/4/20 输出需要限制位数
        ((TextFieldWithLabel) nameBlockMap.get(ContinuousArgument.POWER.getArgumentName())).getTextField().setText(String.format("%.2f", calculation.getPower()));
        ((TextFieldWithLabel) nameBlockMap.get(ContinuousArgument.ROTATING_SPEED.getArgumentName())).getTextField().setText(String.format("%.2f", calculation.getRotatingSpeed()));
        ((TextFieldWithLabel) nameBlockMap.get(ContinuousArgument.SHAFT_DIAMETER_OF_POSITION_WHERE_NO_NEED_TO_MACHINE_KEYWAY.getArgumentName())).getTextField().setText(String.format("%.2f", calculation.getShaftDiameterOfPositionWhereNoNeedToMachineKeyway()));
        ((TextFieldWithLabel) nameBlockMap.get(ContinuousArgument.SHAFT_DIAMETER_OF_POSITION_WHERE_FIXING_GEAR.getArgumentName())).getTextField().setText(String.format("%.2f", calculation.getShaftDiameterOfPositionWhereFixingGear()));
    }

    private JPanel createShaftDiameterReference() {
        ((ComboBoxWithLabel)nameBlockMap.get(DiscreteArgument.SHAFT_MATERIAL.getArgumentName())).getComboBox().setSelectedItem(DiscreteArgument.SHAFT_MATERIAL.getValueNameOf(0));
        DiscreteArgument.SHAFT_MATERIAL.setThePickedDiscreteValue(ShaftMaterial.STEEL_45TH);

        return InputBlocksWithHeadLine.createWithLayout(CAN_KAO_ZHOU_JING, 1, 3,
                nameBlockMap.get(DiscreteArgument.SHAFT_MATERIAL.getArgumentName()),
                nameBlockMap.get(ContinuousArgument.SHAFT_DIAMETER_OF_POSITION_WHERE_NO_NEED_TO_MACHINE_KEYWAY.getArgumentName()),
                nameBlockMap.get(ContinuousArgument.SHAFT_DIAMETER_OF_POSITION_WHERE_FIXING_GEAR.getArgumentName())
        );
    }

    private JPanel createMotorArguments() {
        return InputBlocksWithHeadLine.createWithLayout(DIAN_JI_CAN_SHU, 1, 2,
                nameBlockMap.get(ContinuousArgument.ROTATING_SPEED.getArgumentName()),
                nameBlockMap.get(ContinuousArgument.POWER.getArgumentName())
        );
    }

    private JPanel createGearArguments() {
        return InputBlocksWithHeadLine.createWithLayout(CHI_LUN_CAN_SHU, 1, 2,
                nameBlockMap.get(ContinuousArgument.NUMBER_OF_TEETH.getArgumentName()),
                nameBlockMap.get(ContinuousArgument.MODULUS.getArgumentName())
        );
    }

    private void setupCard4(JPanel card4) {
        setupCard2(card4);
    }

    private void setupCard2(JPanel card2) {
        card2.add(
                InputBlocksWithHeadLine.createWithLayout(SHENG_CHAN_CAN_SHU, 2, 5,
                        // The first row
                        nameBlockMap.get(ContinuousArgument.AVERAGE_FRUIT_WEIGHT.getArgumentName()),
                        nameBlockMap.get(ContinuousArgument.CHAIN_WEIGHT_PER_METER.getArgumentName()),
                        nameBlockMap.get(DiscreteArgument.SLIDING_FRICTION.getArgumentName()),
                        nameBlockMap.get(ContinuousArgument.MOTOR_RESPONSE_TIME.getArgumentName()),
                        // The second row
                        nameBlockMap.get(ContinuousArgument.PRODUCTIVITY.getArgumentName()),
                        nameBlockMap.get(ContinuousArgument.CARRIER_SPACING.getArgumentName()),
                        nameBlockMap.get(DiscreteArgument.LUBRICATION.getArgumentName()),
                        nameBlockMap.get(ContinuousArgument.POWER_TRANSFER_EFFICIENCY.getArgumentName())
                ),
                BorderLayout.NORTH);
        card2.add(
                InputBlocksWithHeadLine.createWithLayout(SHU_SONG_DUAN, 1, 3,
                        nameBlockMap.get(DiscreteArgument.CONVEYING_BEARING_METHOD.getArgumentName()),
                        nameBlockMap.get(ContinuousArgument.CONVEYING_CARRIER_WEIGHT.getArgumentName()),
                        nameBlockMap.get(ContinuousArgument.CONVEYING_LINE_LENGTH.getArgumentName())
                ),
                BorderLayout.CENTER);
        card2.add(
                InputBlocksWithHeadLine.createWithLayout(TI_CHU_DUAN, 1, 3,
                        nameBlockMap.get(DiscreteArgument.CULLING_BEARING_METHOD.getArgumentName()),
                        nameBlockMap.get(ContinuousArgument.CULLING_CARRIER_WEIGHT.getArgumentName()),
                        nameBlockMap.get(ContinuousArgument.CULLING_LINE_LENGTH.getArgumentName())
                ),
                BorderLayout.SOUTH);

        ((TextFieldWithLabel)nameBlockMap.get(ContinuousArgument.CONVEYING_LINE_LENGTH.getArgumentName())).setInputBlockName(primitiveNameOfConveyingLineLength);
        ((TextFieldWithLabel)nameBlockMap.get(ContinuousArgument.CONVEYING_CARRIER_WEIGHT.getArgumentName())).setInputBlockName(primitiveNameOfConveyingCarrierWeight);

        ((ComboBoxWithLabel)nameBlockMap.get(DiscreteArgument.CONVEYING_BEARING_METHOD.getArgumentName())).getComboBox().setSelectedItem(DiscreteArgument.CONVEYING_BEARING_METHOD.getValueNameOf(0));
        DiscreteArgument.CONVEYING_BEARING_METHOD.setThePickedDiscreteValue(ConveyingBearingMethod.ROLLER);
        ((ComboBoxWithLabel)nameBlockMap.get(DiscreteArgument.CULLING_BEARING_METHOD.getArgumentName())).getComboBox().setSelectedItem(DiscreteArgument.CONVEYING_BEARING_METHOD.getValueNameOf(0));
        DiscreteArgument.CULLING_BEARING_METHOD.setThePickedDiscreteValue(CullingBearingMethod.FISH_BONE);
    }

    private void setupCard1(JPanel card1) {
        card1.add(
                InputBlocksWithHeadLine.createWithLayout(SHENG_CHAN_CAN_SHU, 2, 5,
                        // The first row
                        nameBlockMap.get(ContinuousArgument.AVERAGE_FRUIT_WEIGHT.getArgumentName()),
                        nameBlockMap.get(ContinuousArgument.CHAIN_WEIGHT_PER_METER.getArgumentName()),
                        nameBlockMap.get(ContinuousArgument.CONVEYING_LINE_LENGTH.getArgumentName()),
                        nameBlockMap.get(DiscreteArgument.SLIDING_FRICTION.getArgumentName()),
                        nameBlockMap.get(ContinuousArgument.MOTOR_RESPONSE_TIME.getArgumentName()),
                        // The second row
                        nameBlockMap.get(ContinuousArgument.PRODUCTIVITY.getArgumentName()),
                        nameBlockMap.get(ContinuousArgument.CARRIER_SPACING.getArgumentName()),
                        nameBlockMap.get(ContinuousArgument.CONVEYING_CARRIER_WEIGHT.getArgumentName()),
                        nameBlockMap.get(DiscreteArgument.LUBRICATION.getArgumentName()),
                        nameBlockMap.get(ContinuousArgument.POWER_TRANSFER_EFFICIENCY.getArgumentName())
                ),
                BorderLayout.CENTER);

        ((ComboBoxWithLabel)nameBlockMap.get(DiscreteArgument.SLIDING_FRICTION.getArgumentName())).getComboBox().setSelectedItem(DiscreteArgument.SLIDING_FRICTION.getValueNameOf(0));
        // TODO: 2022/4/20 上下两句，逻辑脱节，如果 index == 0 的不是 STEEL__NYLON 就做了
        DiscreteArgument.SLIDING_FRICTION.setThePickedDiscreteValue(SlidingFriction.STEEL__NYLON);
        ((ComboBoxWithLabel)nameBlockMap.get(DiscreteArgument.LUBRICATION.getArgumentName())).getComboBox().setSelectedItem(DiscreteArgument.LUBRICATION.getValueNameOf(0));
        // TODO: 2022/4/20 同样逻辑脱节
        DiscreteArgument.LUBRICATION.setThePickedDiscreteValue(Lubrication.LUBRICATED);

        // TODO: 2022/4/20 如何做到动态更改？我只需要管String的值，JLabel的值会自己变
        // TODO: 2022/4/20 Card2 Card4 也存在这样的代码，如何消除代码重复，否则我很可能会只记得改这里，而不记得改 Card2 和 Card4
        ((TextFieldWithLabel)nameBlockMap.get(ContinuousArgument.CONVEYING_LINE_LENGTH.getArgumentName())).setInputBlockName(newNameOfConveyingLineLengthWhenChannelOne);
        ((TextFieldWithLabel)nameBlockMap.get(ContinuousArgument.CONVEYING_CARRIER_WEIGHT.getArgumentName())).setInputBlockName(newNameOfConveyingCarrierWeightWhenChannelOne);
    }

    private JPanel createProductionArguments() {
        JPanel productionArgumentsCards = new JPanel(new CardLayout());

        productionArgumentsCards.add(productionArgumentsCard1, SortingChannel.ONE.getValueName());
        productionArgumentsCards.add(productionArgumentsCard2, SortingChannel.TWO.getValueName());
        productionArgumentsCards.add(productionArgumentsCard4, SortingChannel.FOUR.getValueName());

        return productionArgumentsCards;
    }

    private JPanel createSortingChannel() {
        ComboBoxLine sortingChannelComboBoxLine = new ComboBoxLine((ComboBoxWithLabel) nameBlockMap.get(DiscreteArgument.SORTING_CHANNEL.getArgumentName())) {
            @Override
            public ComboBoxLine myLayout() {
                setLayout(new FlowLayout(FlowLayout.LEFT));
                //左边距
                int width = 200;
                add(Box.createRigidArea(new Dimension(width, 0)));
                add(comboBoxWithLabel);

                return this;
            }
        }.myLayout();

        sortingChannelComboBoxLine.getComboBox().addItemListener(e -> {
            DiscreteArgument.SORTING_CHANNEL.setThePickedDiscreteValue(DiscreteArgument.getTheDiscreteValueOfName((String) e.getItem()));
            // TODO : 把多个 if else 消除掉
            if (SortingChannel.ONE.equals(DiscreteArgument.SORTING_CHANNEL.getThePickedDiscreteValue())) {
                productionArgumentsCard2.removeAll();
                productionArgumentsCard4.removeAll();
                setupCard1(productionArgumentsCard1);
            } else if (SortingChannel.TWO.equals(DiscreteArgument.SORTING_CHANNEL.getThePickedDiscreteValue())) {
                productionArgumentsCard1.removeAll();
                productionArgumentsCard4.removeAll();
                setupCard2(productionArgumentsCard2);
            } else if (SortingChannel.FOUR.equals(DiscreteArgument.SORTING_CHANNEL.getThePickedDiscreteValue())) {
                productionArgumentsCard1.removeAll();
                productionArgumentsCard2.removeAll();
                setupCard4(productionArgumentsCard4);
            }
            // 把用户选择的 分选通道 对应的输入界面展示出来
            ((CardLayout) productionArgumentsCards.getLayout()).show(productionArgumentsCards, DiscreteArgument.SORTING_CHANNEL.getThePickedDiscreteValue().getValueName());
            validate();
            pack();
            repaint();
        });

        // TODO: 2022/4/20 改进，代码重复
        // initialize
        sortingChannelComboBoxLine.getComboBox().setSelectedItem(DiscreteArgument.SORTING_CHANNEL.getValueNameOf(0));
        DiscreteArgument.SORTING_CHANNEL.setThePickedDiscreteValue(SortingChannel.ONE);
        productionArgumentsCard2.removeAll();
        productionArgumentsCard4.removeAll();
        setupCard1(productionArgumentsCard1);
        validate();
        pack();
        repaint();

        return sortingChannelComboBoxLine;
    }

    private JPanel createFruitVariety() {
        ComboBoxLine fruitVarietyComboBoxLine = new ComboBoxLine((ComboBoxWithLabel) nameBlockMap.get(DiscreteArgument.FRUIT_VARIETY.getArgumentName())) {
            @Override
            public ComboBoxLine myLayout() {
                setLayout(new FlowLayout(FlowLayout.RIGHT));
                //右边距
                int width = 200;
                add(comboBoxWithLabel);
                add(Box.createRigidArea(new Dimension(width, 0)));

                return this;
            }
        }.myLayout();

        // TODO: 2022/4/20 ItemListener 只能接受用户的选择，我这里直接 setSelectedItem无法触发ItemListener，使得我需要手动 setThePickedDiscreteValue 多处出现这种问题
        fruitVarietyComboBoxLine.getComboBox().setSelectedItem(FruitVariety.PITAYA);
        DiscreteArgument.FRUIT_VARIETY.setThePickedDiscreteValue(FruitVariety.PITAYA);
        return fruitVarietyComboBoxLine;
    }

    private JPanel createTitle() {
        JPanel title = Headline.createWithLayout(GENERAL_TITLE);
        title.setFont(new Font("黑体", Font.BOLD, 32));
        return title;
    }

    public void myShow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public View myLayout() {
        Box northPanel = Box.createVerticalBox();
        JPanel southPanel = new JPanel(new BorderLayout());
        Box boxInSouthPanel = Box.createVerticalBox();

        this.add(northPanel, BorderLayout.NORTH);
        this.add(productionArgumentsCards, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);

        northPanel.add(title);
        northPanel.add(fruitVarietyCombo);
        northPanel.add(sortingChannelCombo);

        southPanel.add(gearArguments, BorderLayout.WEST);
        southPanel.add(motorArguments, BorderLayout.EAST);
        southPanel.add(boxInSouthPanel, BorderLayout.SOUTH);

        boxInSouthPanel.add(shaftDiameterReference);
        boxInSouthPanel.add(submitButton);

        return this;
    }

    public static View createWithLayout() {
        return new View().myLayout();
    }
}
