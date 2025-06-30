package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class DuotoneDialog extends JDialog {
    private Color darkColor = Color.CYAN;
    private Color lightColor = Color.MAGENTA;

    private final JButton darkColorButton;
    private final JButton lightColorButton;

    public DuotoneDialog(JFrame parent) {
        super(parent, "Duotone Farben", true);
        setLayout(new BorderLayout());
        setSize(300, 200);
        setLocationRelativeTo(parent);

        JPanel colorPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        colorPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        darkColorButton = new JButton();
        darkColorButton.setBackground(darkColor);
        darkColorButton.addActionListener(e -> {
            Color selected = JColorChooser.showDialog(this, "Dunkle Farbe wählen", darkColor);
            if (selected != null) {
                darkColor = selected;
                darkColorButton.setBackground(darkColor);
            }
        });

        lightColorButton = new JButton();
        lightColorButton.setBackground(lightColor);
        lightColorButton.addActionListener(e -> {
            Color selected = JColorChooser.showDialog(this, "Helle Farbe wählen", lightColor);
            if (selected != null) {
                lightColor = selected;
                lightColorButton.setBackground(lightColor);
            }
        });

        colorPanel.add(new JLabel("Dunkle Farbe:"));
        colorPanel.add(darkColorButton);
        colorPanel.add(new JLabel("Helle Farbe:"));
        colorPanel.add(lightColorButton);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());

        add(colorPanel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
    }

    public float[] getDarkColor() {
        return new float[]{
                darkColor.getRed() / 255f,
                darkColor.getGreen() / 255f,
                darkColor.getBlue() / 255f
        };
    }

    public float[] getLightColor() {
        return new float[]{
                lightColor.getRed() / 255f,
                lightColor.getGreen() / 255f,
                lightColor.getBlue() / 255f
        };
    }

}
