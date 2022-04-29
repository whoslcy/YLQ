package ylq.view.inputBlocks;

import ylq.view.MyLayout;
import ylq.view.inputBlocks.inputBlockWithLabel.InputBlockWithLabel;

import javax.swing.*;
import java.awt.*;

/**
 * 一个输入板块
 * 左上角有标题
 * 标题下方是输入区块的网格
 * @author lichenyv
 * @date 2022/4/7
 */
public class InputBlocksWithHeadLine extends JPanel implements MyLayout<InputBlocksWithHeadLine> {
    private Headline headline;
    private InputBlocksGrid inputBlocksGrid;

    public InputBlocksWithHeadLine(String headlineName, int rows, int columns, InputBlockWithLabel... inputBlocksWithLabel) {
        headline = Headline.createWithLayout(headlineName);
        inputBlocksGrid = InputBlocksGrid.createWithLayout(rows, columns, inputBlocksWithLabel);
    }

    @Override
    public InputBlocksWithHeadLine myLayout() {
        setLayout(new BorderLayout());
        add(headline, BorderLayout.NORTH);
        add(inputBlocksGrid, BorderLayout.CENTER);

        return this;
    }

    public InputBlocksGrid getInputBlocksGrid() {
        return inputBlocksGrid;
    }

    public static InputBlocksWithHeadLine createWithLayout(String headlineName, int rows, int columns, InputBlockWithLabel... inputBlocksWithLabel) {
        return new InputBlocksWithHeadLine(headlineName, rows, columns, inputBlocksWithLabel).myLayout();
    }
}
