package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class DistortionDialog extends JDialog {
    private final JSlider amplitudeSlider;
    private final JSlider frequencySlider;

    public DistortionDialog(JFrame parent) {
        super(parent, "Distortion Parameter", true);
        setLayout(new GridLayout(3, 1));

        amplitudeSlider = new JSlider(0, 100, 5); // entspricht 0.0f – 1.0f (Skaliert in 0.01er-Schritten)
        frequencySlider = new JSlider(0, 100, 20);

        add(createLabeledSlider("Amplitude", amplitudeSlider));
        add(createLabeledSlider("Frequency", frequencySlider));

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> setVisible(false));
        add(okButton);

        pack();
        setLocationRelativeTo(parent);
    }

    private JPanel createLabeledSlider(String label, JSlider slider) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.WEST);
        panel.add(slider, BorderLayout.CENTER);
        return panel;
    }

    public float getAmplitude() {
        return amplitudeSlider.getValue() / 100.0f;
    }

    public float getFrequency() {
        return frequencySlider.getValue();
    }

}
