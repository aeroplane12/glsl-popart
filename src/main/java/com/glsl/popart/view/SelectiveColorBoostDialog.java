package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class SelectiveColorBoostDialog extends JDialog {
    private JSlider thresholdSlider;
    private JSlider boostSlider;
    private JButton colorButton;
    private Color selectedColor = Color.GREEN;

    public SelectiveColorBoostDialog(JFrame parent) {
        super(parent, "Selective Color Boost Parameter", true);
        setLayout(new BorderLayout());
        setSize(500, 350);
        setLocationRelativeTo(parent);

        // Threshold Slider
        thresholdSlider = new JSlider(0, 100, 80); // 0.0 - 1.0 mapped to 0–100
        thresholdSlider.setMajorTickSpacing(10);
        thresholdSlider.setPaintTicks(true);
        thresholdSlider.setPaintLabels(true);

        // Boost Slider
        boostSlider = new JSlider(100, 300, 180); // 1.0 – 3.0 mapped to 100–300
        boostSlider.setMajorTickSpacing(10);
        boostSlider.setPaintTicks(true);
        boostSlider.setPaintLabels(true);

        // Color Chooser Button
        colorButton = new JButton("Ziel-Farbe wählen");
        colorButton.setBackground(selectedColor);
        colorButton.setForeground(Color.WHITE);
        colorButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Ziel-Farbe auswählen", selectedColor);
            if (color != null) {
                selectedColor = color;
                colorButton.setBackground(selectedColor);
            }
        });

        // UI Layout
        JPanel contentPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(new JLabel("Farb-Schwelle (Threshold):"));
        contentPanel.add(thresholdSlider);
        contentPanel.add(new JLabel("Verstärkung (Boost Amount):"));
        contentPanel.add(boostSlider);
        contentPanel.add(colorButton);

        // OK Button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());

        add(contentPanel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
    }

    public float getThreshold() {
        return thresholdSlider.getValue() / 100.0f;
    }

    public float getBoostAmount() {
        return boostSlider.getValue() / 100.0f;
    }

    public float[] getTargetColor() {
        return new float[] {
                selectedColor.getRed() / 255.0f,
                selectedColor.getGreen() / 255.0f,
                selectedColor.getBlue() / 255.0f
        };
    }

}
