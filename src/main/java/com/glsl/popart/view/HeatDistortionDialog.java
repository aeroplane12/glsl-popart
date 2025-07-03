package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class HeatDistortionDialog extends JDialog {
    private JSlider strengthSlider;
    private JSlider frequencySlider;

    public HeatDistortionDialog(JFrame parent) {
        super(parent, "Heat Distortion Parameter", true);
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(parent);

        strengthSlider = new JSlider(0, 20, 2);
        strengthSlider.setMajorTickSpacing(2);
        strengthSlider.setPaintTicks(true);
        strengthSlider.setPaintLabels(true);

        frequencySlider = new JSlider(0, 50, 10);
        frequencySlider.setMajorTickSpacing(10);
        frequencySlider.setPaintTicks(true);
        frequencySlider.setPaintLabels(true);

        JPanel contentPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(new JLabel("Strength:"));
        contentPanel.add(strengthSlider);
        contentPanel.add(new JLabel("Frequency:"));
        contentPanel.add(frequencySlider);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            System.out.println("Strength: " + getStrength());
            System.out.println("Frequency: " + getFrequency());
            dispose();
        });

        add(contentPanel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
    }

    public float getStrength() {
        return strengthSlider.getValue() / 100f;
    }

    public float getFrequency() {
        return frequencySlider.getValue();
    }

}
