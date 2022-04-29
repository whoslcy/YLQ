package ylq.view.inputBlocks.inputBlockWithLabel;

import ylq.view.MyLayout;

import javax.swing.*;

/**
 * @author lichenyv
 * @date 2022/4/7
 */
public abstract class InputBlockWithLabel extends JPanel implements MyLayout<InputBlockWithLabel> {
    protected String inputBlockName;

    protected InputBlockWithLabel(String inputBlockName) {
        this.inputBlockName = inputBlockName;
    }

    public void setInputBlockName(String inputBlockName) {
        this.inputBlockName = inputBlockName;
    }

    public String getInputBlockName() {
        return inputBlockName;
    }

    @Override
    public InputBlockWithLabel myLayout(){
        return this;
    }
}
