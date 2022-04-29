package ylq.view;

import ylq.model.arguments.discreteValues.DiscreteValue;
import ylq.view.inputBlocks.inputBlockWithLabel.ComboBoxWithLabel;

import javax.swing.*;

/**
 * 一个多选框，占一行
 * @author lichenyv
 * @date 2022/4/7
 */
public abstract class ComboBoxLine extends JPanel implements MyLayout<ComboBoxLine> {
    protected ComboBoxWithLabel comboBoxWithLabel;

    public ComboBoxLine(String inputBlockName, DiscreteValue[] discreteValues) {
        comboBoxWithLabel = ComboBoxWithLabel.createWithLayout(inputBlockName, discreteValues);
    }

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public ComboBoxLine(ComboBoxWithLabel comboBoxWithLabel) {
        this.comboBoxWithLabel = comboBoxWithLabel;
    }

    public ComboBoxWithLabel getComboBoxWithLabel() {
        return comboBoxWithLabel;
    }

    public JComboBox getComboBox() {
        return comboBoxWithLabel.getComboBox();
    }
}
