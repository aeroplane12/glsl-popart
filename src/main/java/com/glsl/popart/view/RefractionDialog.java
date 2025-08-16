package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class RefractionDialog extends JDialog {
    private JSlider strengthSlider;

    public RefractionDialog(JFrame parent) {
        super(parent, "Refraction Parameter", true);
        setLayout(new BorderLayout());
        setSize(300, 150);
        setLocationRelativeTo(parent);

        strengthSlider = new JSlider(0, 200, 100); // 0.0 – 2.0
        strengthSlider.setMajorTickSpacing(50);
        strengthSlider.setPaintTicks(true);
        strengthSlider.setPaintLabels(true);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(new JLabel("Refraction Strength:"), BorderLayout.NORTH);
        contentPanel.add(strengthSlider, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            System.out.println("Set Refraction Strength: " + getRefractionStrength());
            dispose();
        });

        add(contentPanel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
    }

    public float getRefractionStrength() {
        return strengthSlider.getValue() / 100f;
    }

}
