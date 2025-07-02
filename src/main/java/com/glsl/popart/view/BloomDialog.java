package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class BloomDialog extends JDialog {
    private final JSlider thresholdSlider;
    private final JSlider intensitySlider;
    private boolean confirmed = false;

    public BloomDialog(JFrame parent) {
        super(parent, "Bloom-Einstellungen", true);
        setLayout(new GridLayout(3, 2));
        setSize(300, 150);
        setLocationRelativeTo(parent);

        thresholdSlider = new JSlider(0, 100, 80); // Entspricht 0.0–1.0
        intensitySlider = new JSlider(0, 200, 100); // Entspricht 0.0–2.0

        add(new JLabel("Threshold:"));
        add(thresholdSlider);
        add(new JLabel("Intensity:"));
        add(intensitySlider);

        JButton ok = new JButton("OK");
        ok.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });
        JButton cancel = new JButton("Abbrechen");
        cancel.addActionListener(e -> setVisible(false));

        add(ok);
        add(cancel);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public float getThreshold() {
        return thresholdSlider.getValue() / 100.0f;
    }

    public float getIntensity() {
        return intensitySlider.getValue() / 100.0f * 2.0f;
    }

}
