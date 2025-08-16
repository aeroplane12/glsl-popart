package com.glsl.popart.view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChromaticAberrationDialog extends JDialog {
    private boolean confirmed = false;
    private JSlider offsetSlider;

    public ChromaticAberrationDialog(JFrame parent) {
        super(parent, "Chromatic Aberration Parameter", true);
        setLayout(new BorderLayout());

        offsetSlider = new JSlider(0, 100, 10);
        offsetSlider.setMajorTickSpacing(20);
        offsetSlider.setPaintTicks(true);
        offsetSlider.setPaintLabels(true);

        JPanel sliderPanel = new JPanel();
        sliderPanel.setBorder(BorderFactory.createTitledBorder("Offset:"));
        sliderPanel.add(offsetSlider);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });

        cancelButton.addActionListener(e -> {
            confirmed = false;
            setVisible(false);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(sliderPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    public float getOffset() {
        return offsetSlider.getValue() / 1000.0f; // z.B. 0.01f für Wert 10
    }

    public boolean isConfirmed() {
        return confirmed;
    }

}
