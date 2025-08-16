package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class VignetteDialog extends JDialog {
    private final JSlider radiusSlider;
    private final JSlider softnessSlider;
    private boolean confirmed = false;

    public VignetteDialog(JFrame parent) {
        super(parent, "Vignette Parameter", true);
        setLayout(new BorderLayout());

        radiusSlider = createSlider(0, 100, 75, "Radius");
        softnessSlider = createSlider(0, 100, 45, "Softness");

        JPanel sliderPanel = new JPanel(new GridLayout(2, 1));
        sliderPanel.add(wrapWithLabel("Radius", radiusSlider));
        sliderPanel.add(wrapWithLabel("Softness", softnessSlider));

        add(sliderPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");

        ok.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        cancel.addActionListener(e -> dispose());

        buttonPanel.add(ok);
        buttonPanel.add(cancel);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private JPanel wrapWithLabel(String label, JSlider slider) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.WEST);
        panel.add(slider, BorderLayout.CENTER);
        return panel;
    }

    private JSlider createSlider(int min, int max, int initial, String tooltip) {
        JSlider slider = new JSlider(min, max, initial);
        slider.setMajorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setToolTipText(tooltip);
        return slider;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public float getRadius() {
        return radiusSlider.getValue() / 100.0f;
    }

    public float getSoftness() {
        return softnessSlider.getValue() / 100.0f;
    }

}
