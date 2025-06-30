package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class BenDayDotsDialog extends JDialog {
    private JSlider dotSizeSlider;

    public BenDayDotsDialog(JFrame parent) {
        super(parent, "BenDay Dots Parameter", true);
        setLayout(new BorderLayout());
        setSize(300, 150);
        setLocationRelativeTo(parent);

        dotSizeSlider = new JSlider(1, 20, 4);
        dotSizeSlider.setMajorTickSpacing(2);
        dotSizeSlider.setPaintTicks(true);
        dotSizeSlider.setPaintLabels(true);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(new JLabel("Dot Size:"), BorderLayout.NORTH);
        contentPanel.add(dotSizeSlider, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());

        add(contentPanel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
    }

    public int getDotSize() {
        return dotSizeSlider.getValue();
    }

}
