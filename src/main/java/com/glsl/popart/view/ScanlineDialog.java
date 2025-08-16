package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class ScanlineDialog extends JDialog {
    private JSlider widthSlider;

    public ScanlineDialog(JFrame parent) {
        super(parent, "Scanline Parameter", true);
        setLayout(new BorderLayout());
        setSize(300, 150);
        setLocationRelativeTo(parent);

        widthSlider = new JSlider(1, 20, 4);
        widthSlider.setMajorTickSpacing(4);
        widthSlider.setPaintTicks(true);
        widthSlider.setPaintLabels(true);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(new JLabel("Number of Scanline:"), BorderLayout.NORTH);
        contentPanel.add(widthSlider, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            System.out.println("Set number of Scanline: " + getScanlineWidth());
            dispose();
        });

        add(contentPanel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
    }

    public float getScanlineWidth() {
        return widthSlider.getValue();
    }

}
