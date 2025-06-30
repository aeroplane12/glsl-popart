package com.glsl.popart.view;


import javax.swing.*;
import java.awt.*;

public class TritoneDialog extends JDialog {
    private final JColorChooser shadowColorChooser;
    private final JColorChooser midtoneColorChooser;
    private final JColorChooser highlightColorChooser;

    private float[] shadowColor;
    private float[] midtoneColor;
    private float[] highlightColor;

    public TritoneDialog(Frame parent) {
        super(parent, "Tritone Farben", true);
        setLayout(new BorderLayout());
        setSize(700, 600);
        setLocationRelativeTo(parent);

        shadowColorChooser = new JColorChooser(Color.MAGENTA);
        midtoneColorChooser = new JColorChooser(Color.ORANGE);
        highlightColorChooser = new JColorChooser(Color.YELLOW);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Schatten", shadowColorChooser);
        tabs.addTab("Mitteltöne", midtoneColorChooser);
        tabs.addTab("Lichter", highlightColorChooser);

        add(tabs, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            shadowColor = toFloatArray(shadowColorChooser.getColor());
            midtoneColor = toFloatArray(midtoneColorChooser.getColor());
            highlightColor = toFloatArray(highlightColorChooser.getColor());
            setVisible(false);
        });

        JPanel bottom = new JPanel();
        bottom.add(okButton);
        add(bottom, BorderLayout.SOUTH);
    }

    private float[] toFloatArray(Color color) {
        return new float[]{
                color.getRed() / 255f,
                color.getGreen() / 255f,
                color.getBlue() / 255f
        };
    }

    public float[] getShadowColor() {
        return shadowColor;
    }

    public float[] getMidtoneColor() {
        return midtoneColor;
    }

    public float[] getHighlightColor() {
        return highlightColor;
    }

}
