package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class NoiseDialog extends JDialog {
    private JSlider noiseSlider;

    public NoiseDialog(JFrame parent) {
        super(parent, "Noise Parameter", true);
        setLayout(new BorderLayout());
        setSize(300, 150);
        setLocationRelativeTo(parent);

        // Slider: Noise-Stärke
        noiseSlider = new JSlider(0, 100, 75);
        noiseSlider.setMajorTickSpacing(10);
        noiseSlider.setMinorTickSpacing(5);
        noiseSlider.setPaintTicks(true);
        noiseSlider.setPaintLabels(true);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(new JLabel("Noise Strength:"), BorderLayout.NORTH);
        contentPanel.add(noiseSlider, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            int value = noiseSlider.getValue();
            System.out.println("Set Noise Level: " + value);
            dispose();
        });

        add(contentPanel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
    }

    public float getNoiseStrength() {
        return noiseSlider.getValue() / 100f;
    }

}
