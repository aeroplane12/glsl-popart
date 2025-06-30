package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class OutOfRegisterPrintShaderDialog extends JDialog {
    private JSlider offsetSlider;
    private float selectedOffset = 0.05f;

    public OutOfRegisterPrintShaderDialog(Frame owner) {
        super(owner, "Out Of Register Offset", true);
        setLayout(new BorderLayout());

        offsetSlider = new JSlider(0, 100, (int) (selectedOffset * 100));
        offsetSlider.setMajorTickSpacing(20);
        offsetSlider.setMinorTickSpacing(5);
        offsetSlider.setPaintTicks(true);
        offsetSlider.setPaintLabels(true);
        offsetSlider.addChangeListener(e -> selectedOffset = offsetSlider.getValue() / 100.0f);

        add(new JLabel("Offset:"), BorderLayout.NORTH);
        add(offsetSlider, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> setVisible(false));
        add(okButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
    }

    public float getSelectedOffset() {
        return selectedOffset;
    }

}
