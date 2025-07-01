package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class IntelligentBoldOutlinesDialog extends JDialog {
    private final JSlider thicknessSlider;
    private final JSlider edgeThresholdSlider;

    public IntelligentBoldOutlinesDialog(JFrame parent) {
        super(parent, "Intelligent Bold Outlines", true);
        setLayout(new BorderLayout());

        thicknessSlider = new JSlider(1, 20, 4);
        thicknessSlider.setMajorTickSpacing(5);
        thicknessSlider.setMinorTickSpacing(1);
        thicknessSlider.setPaintTicks(true);
        thicknessSlider.setPaintLabels(true);

        edgeThresholdSlider = new JSlider(0, 100, 70); // entspricht 0.0 bis 1.0
        edgeThresholdSlider.setMajorTickSpacing(25);
        edgeThresholdSlider.setMinorTickSpacing(5);
        edgeThresholdSlider.setPaintTicks(true);
        edgeThresholdSlider.setPaintLabels(true);

        JPanel sliders = new JPanel(new GridLayout(2, 1));
        sliders.add(createLabeledPanel("Thickness", thicknessSlider));
        sliders.add(createLabeledPanel("Edge Threshold", edgeThresholdSlider));

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());

        add(sliders, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private JPanel createLabeledPanel(String label, JSlider slider) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.NORTH);
        panel.add(slider, BorderLayout.CENTER);
        return panel;
    }

    public float getThickness() {
        return thicknessSlider.getValue();
    }

    public float getEdgeThreshold() {
        return edgeThresholdSlider.getValue() / 100.0f;
    }

}
