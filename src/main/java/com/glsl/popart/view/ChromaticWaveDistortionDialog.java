package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class ChromaticWaveDistortionDialog extends JDialog {
    private final JSpinner amplitudeSpinner;
    private final JSpinner frequencySpinner;

    private boolean confirmed = false;

    public ChromaticWaveDistortionDialog(JFrame parent) {
        super(parent, "Chromatic Wave Distortion", true);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Amplitude:"));
        amplitudeSpinner = new JSpinner(new SpinnerNumberModel(0.05, 0.0, 1.0, 0.01));
        add(amplitudeSpinner);

        add(new JLabel("Frequency:"));
        frequencySpinner = new JSpinner(new SpinnerNumberModel(12.0, 1.0, 50.0, 1.0));
        add(frequencySpinner);

        JButton ok = new JButton("OK");
        ok.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });
        add(ok);

        JButton cancel = new JButton("Abbrechen");
        cancel.addActionListener(e -> setVisible(false));
        add(cancel);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public float getAmplitude() {
        return ((Double) amplitudeSpinner.getValue()).floatValue();
    }

    public float getFrequency() {
        return ((Double) frequencySpinner.getValue()).floatValue();
    }

}
