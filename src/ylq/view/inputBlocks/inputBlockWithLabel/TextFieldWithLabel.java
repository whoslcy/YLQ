package ylq.view.inputBlocks.inputBlockWithLabel;

import javax.swing.*;
import java.awt.*;

/**
 * @author lichenyv
 * @date 2022/3/25
 */
public class TextFieldWithLabel extends InputBlockWithLabel {
    private JLabel
            textFieldLabel,
            unitLabel;
    private JTextField textField;
    private String unitName;

    public TextFieldWithLabel(String inputBlockName, String unitName) {
        super(inputBlockName);
        this.unitName = unitName;
        textFieldLabel = new JLabel(inputBlockName);
        unitLabel = new JLabel(unitName);
        textField = new JTextField(10);
    }

    public String getUnitName() {
        return unitName;
    }

    @Override
    public TextFieldWithLabel myLayout() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(textFieldLabel);
        add(textField);
        add(unitLabel);
        return this;
    }

    @Override
    public void setInputBlockName(String inputBlockName) {
        textFieldLabel.setText(inputBlockName);
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setEditable(boolean whetherEditable) {
        textField.setEditable(whetherEditable);
    }

    public static TextFieldWithLabel createWithLayout(String inputBlockName, String unitName) {
        return new TextFieldWithLabel(inputBlockName, unitName).myLayout();
    }

    public static TextFieldWithLabel createUneditableWithLayout(String inputBlockName, String unitName) {
        TextFieldWithLabel textFieldWithLabel = createWithLayout(inputBlockName, unitName);
        textFieldWithLabel.getTextField().setEditable(false);
        return textFieldWithLabel;
    }

}
