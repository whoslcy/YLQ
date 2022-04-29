package ylq.view.inputBlocks;

import ylq.view.MyLayout;

import javax.swing.*;
import java.awt.*;

/**
 * @author lichenyv
 * @date 2022/4/7
 */
public class Headline extends JPanel implements MyLayout<Headline> {
    private String headlineName;
    private JLabel headlineLabel;

    public Headline(String headlineName) {
        this.headlineName = headlineName;
        headlineLabel = new JLabel(headlineName);
    }

    public String getHeadlineName() {
        return headlineName;
    }

    public JLabel getHeadlineLabel() {
        return headlineLabel;
    }

    @Override
    public Headline myLayout() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(headlineLabel);
        return this;
    }

    public static Headline createWithLayout(String headlineName) {
        return new Headline(headlineName).myLayout();
    }
}
