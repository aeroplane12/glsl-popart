package com.glsl.popart.view;

import javax.swing.*;
import java.awt.*;

public class SerialityDialog extends JDialog {
    private final JSpinner repeatXSpinner;
    private final JSpinner repeatYSpinner;

    public SerialityDialog(Frame parent) {
        super(parent, "Seriality-Einstellungen", true);
        setLayout(new GridLayout(3, 2, 10, 10));

        repeatXSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 20, 1));
        repeatYSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 20, 1));

        add(new JLabel("Wiederholungen horizontal:"));
        add(repeatXSpinner);
        add(new JLabel("Wiederholungen vertikal:"));
        add(repeatYSpinner);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());
        add(okButton);

        pack();
        setLocationRelativeTo(parent);
    }

    public float getRepeatX() {
        return ((Integer) repeatXSpinner.getValue()).floatValue();
    }

    public float getRepeatY() {
        return ((Integer) repeatYSpinner.getValue()).floatValue();
    }

}
