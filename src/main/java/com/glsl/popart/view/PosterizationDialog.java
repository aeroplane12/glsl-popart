package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class PosterizationDialog extends JDialog{
    private JSlider levelSlider;

    public PosterizationDialog(JFrame parent) {
        super(parent, "Posterization Parameter", true);
        setLayout(new BorderLayout());
        setSize(300, 150);
        setLocationRelativeTo(parent);

        // Slider: Anzahl Farben
        levelSlider = new JSlider(2, 16, 4);
        levelSlider.setMajorTickSpacing(2);
        levelSlider.setPaintTicks(true);
        levelSlider.setPaintLabels(true);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(new JLabel("Farbanzahl:"), BorderLayout.NORTH);
        contentPanel.add(levelSlider, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            int value = levelSlider.getValue();
            System.out.println("Posterization-Level gesetzt auf: " + value);
            dispose();
        });

        add(contentPanel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
    }

    public int getPosterizationLevel() {
        return levelSlider.getValue();
    }
}
