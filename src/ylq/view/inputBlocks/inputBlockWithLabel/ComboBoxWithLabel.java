package ylq.view.inputBlocks.inputBlockWithLabel;

import ylq.model.arguments.discreteValues.DiscreteValue;

import javax.swing.*;
import java.awt.*;

/**
 * 多选框供用户选择离散型变量的值
 * @author lichenyv
 * @date 2022/3/25
 */
public class ComboBoxWithLabel extends InputBlockWithLabel {
    private JLabel comboBoxLabel;
    private JComboBox comboBox;

    public ComboBoxWithLabel(String inputBlockName, DiscreteValue[] discreteValues) {
        super(inputBlockName);
        comboBox = new JComboBox<>();
        comboBoxLabel = new JLabel(inputBlockName);
        // 多选框内不应直接存放 DiscreteValue 的对象
        // 而应该存放 String
        for (DiscreteValue discreteValue : discreteValues) {
            comboBox.addItem(discreteValue.getValueName());
        }
    }

    @Override
    public void setInputBlockName(String inputBlockName) {
        comboBoxLabel.setText(inputBlockName);
    }

    @Override
    public ComboBoxWithLabel myLayout() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(comboBoxLabel);
        add(comboBox);
        return this;
    }

    public JComboBox getComboBox() {
        return comboBox;
    }

    public static ComboBoxWithLabel createWithLayout(String inputBlockName, DiscreteValue[] discreteValues) {
        return new ComboBoxWithLabel(inputBlockName, discreteValues).myLayout();
    }
}
