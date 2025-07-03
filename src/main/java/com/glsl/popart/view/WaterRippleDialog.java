package com.glsl.popart.view;


import javax.swing.*;
import java.awt.*;

public class WaterRippleDialog extends JDialog {
    private JSlider amplitudeSlider;
    private JSlider frequencySlider;
    private JSlider speedSlider;

    public WaterRippleDialog(JFrame parent) {
        super(parent, "Water Ripple Parameter", true);
        setLayout(new BorderLayout());
        setSize(400, 350);
        setLocationRelativeTo(parent);

        amplitudeSlider = new JSlider(0, 100, 5);
        amplitudeSlider.setMajorTickSpacing(20);
        amplitudeSlider.setPaintTicks(true);
        amplitudeSlider.setPaintLabels(true);

        frequencySlider = new JSlider(1, 100, 30);
        frequencySlider.setMajorTickSpacing(20);
        frequencySlider.setPaintTicks(true);
        frequencySlider.setPaintLabels(true);

        speedSlider = new JSlider(0, 500, 200); // 0.0 – 5.0
        speedSlider.setMajorTickSpacing(100);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        JPanel contentPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(new JLabel("Amplitude:"));
        contentPanel.add(amplitudeSlider);
        contentPanel.add(new JLabel("Frequency:"));
        contentPanel.add(frequencySlider);
        contentPanel.add(new JLabel("Speed:"));
        contentPanel.add(speedSlider);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            System.out.println("Amplitude: " + getAmplitude());
            System.out.println("Frequency: " + getFrequency());
            System.out.println("Speed: " + getSpeed());
            dispose();
        });

        add(contentPanel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
    }

    public float getAmplitude() {
        return amplitudeSlider.getValue() / 100f;
    }

    public float getFrequency() {
        return frequencySlider.getValue();
    }

    public float getSpeed() {
        return speedSlider.getValue() / 100f;
    }

}
