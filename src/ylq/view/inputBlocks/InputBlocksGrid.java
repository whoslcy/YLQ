package ylq.view.inputBlocks;

import ylq.view.MyLayout;
import ylq.view.inputBlocks.inputBlockWithLabel.InputBlockWithLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 按网格的格式将输入区域进行排版
 *
 * @author lichenyv
 * @date 2022/4/7
 */
public class InputBlocksGrid extends JPanel implements MyLayout<InputBlocksGrid> {
    private GridLayout inputBlocksLayoutManager;
    private List<InputBlockWithLabel> inputBlocksWithLabel = new ArrayList<>();

    public InputBlocksGrid(int rows, int columns, InputBlockWithLabel... inputBlocksWithLabel) {
        super();
        //默认从左到右，从上到下
        inputBlocksLayoutManager = new GridLayout(rows, columns);
        for (InputBlockWithLabel inputBlockWithLabel : inputBlocksWithLabel) {
            this.inputBlocksWithLabel.add(inputBlockWithLabel);
        }
    }

    @Override
    public InputBlocksGrid myLayout() {
        setLayout(inputBlocksLayoutManager);
        for (InputBlockWithLabel inputBlockWithLabel : inputBlocksWithLabel) {
            this.add(inputBlockWithLabel);
        }

        return this;
    }

    public void setRows(int rows) {
        inputBlocksLayoutManager.setRows(rows);
    }

    public void setColumns(int columns) {
        inputBlocksLayoutManager.setColumns(columns);
    }

    public static InputBlocksGrid createWithLayout(int rows, int columns, InputBlockWithLabel... inputBlocksWithLabel) {
        return new InputBlocksGrid(rows, columns, inputBlocksWithLabel).myLayout();
    }
}
