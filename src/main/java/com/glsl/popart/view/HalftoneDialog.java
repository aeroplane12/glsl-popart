package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class HalftoneDialog extends JDialog {
    private JSlider dotSizeSlider;

    public HalftoneDialog(JFrame parent) {
        super(parent, "Halftone Parameter", true);
        setLayout(new BorderLayout());
        setSize(300, 150);
        setLocationRelativeTo(parent);

        dotSizeSlider = new JSlider(1, 20, 4);
        dotSizeSlider.setMajorTickSpacing(2);
        dotSizeSlider.setPaintTicks(true);
        dotSizeSlider.setPaintLabels(true);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(new JLabel("Punktgröße:"), BorderLayout.NORTH);
        contentPanel.add(dotSizeSlider, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            int value = dotSizeSlider.getValue();
            System.out.println("Halftone dotSize gesetzt auf: " + value);
            dispose();
        });

        add(contentPanel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
    }

    public int getDotSize() {
        return dotSizeSlider.getValue();
    }
}
