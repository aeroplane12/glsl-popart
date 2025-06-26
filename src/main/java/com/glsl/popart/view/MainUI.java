package com.glsl.popart.view;

import com.glsl.popart.utils.Renderer;
import com.glsl.popart.utils.TextureUtils;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {
    private JList<String> popArtEffectsList;
    private JList<String> physicalEffectsList;
    private GLCanvas previewCanvas;
    private Renderer renderer;
    private Texture loadedTexture;
    private int textureId = -1;

    public MainUI() {
        setTitle("GLSL PopArt");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Effektlisten links
        JPanel effectPanel = new JPanel(new GridLayout(1, 2));

        popArtEffectsList = new JList<>(createEffectListModel(new String[]{
                "posterization", "halftone", "comiclook", "duotone", "selectivecolorboost",
                "bendaydots", "tritone", "OutOfRegisterPrintShader",
                "IntelligentBoldOutlines", "seriality"
        }));

        physicalEffectsList = new JList<>(createEffectListModel(new String[]{
                "distortion", "bloom", "chromaticaberration", "noise", "vignette",
                "chromaticwavedistortion", "scanline", "waterripple", "heatdistortion", "refraction"
        }));

        effectPanel.add(new JScrollPane(popArtEffectsList));
        effectPanel.add(new JScrollPane(physicalEffectsList));

        // OpenGL-Vorschau rechts
        renderer = new Renderer();

        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        previewCanvas = new GLCanvas(caps);

        previewCanvas.addGLEventListener(new GLEventListener() {
            @Override
            public void init(GLAutoDrawable drawable) {
                // Init nur einmal
                GL2 gl = drawable.getGL().getGL2();
                gl.glEnable(GL2.GL_TEXTURE_2D);
            }

            @Override
            public void display(GLAutoDrawable drawable) {
                GL2 gl = drawable.getGL().getGL2();

                gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

                if (loadedTexture != null) {
                    loadedTexture.enable(gl);
                    loadedTexture.bind(gl);

                    renderer.renderFullScreenQuad(gl);

                    loadedTexture.disable(gl);
                }
            }

            @Override
            public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {}
            @Override
            public void dispose(GLAutoDrawable drawable) {}
        });

        previewCanvas.setPreferredSize(new Dimension(400, 600));

        /* FPSAnimator animator = new FPSAnimator(previewCanvas, 60);
        animator.start(); */

        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                effectPanel, previewCanvas);
        mainSplit.setDividerLocation(300);
        add(mainSplit, BorderLayout.CENTER);

        // Button unten
        JButton loadTextureButton = new JButton("Textur hochladen");
        loadTextureButton.addActionListener(e -> onLoadTexture());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(loadTextureButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private DefaultListModel<String> createEffectListModel(String[] items) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String item : items) model.addElement(item);
        return model;
    }

    private void onLoadTexture() {
        String[] availableTextures = {"horse.jpg", "river.jpg", "forestinfire.jpg"};

        String selected = (String) JOptionPane.showInputDialog(
                this,
                "Wähle eine Textur aus:",
                "Textur auswählen",
                JOptionPane.PLAIN_MESSAGE,
                null,
                availableTextures,
                availableTextures[0]
        );

        if (selected != null) {
            String resourcePath = "/textures/" + selected;

            previewCanvas.invoke(true, drawable -> {
                try {
                    // Texture im OpenGL-Kontext laden
                    drawable.getGL().getGL2().glFlush();
                    loadedTexture = TextureUtils.loadTexture(resourcePath);
                    textureId = -1; // Reset

                    // Nach Laden die Anzeige aktualisieren (im EDT)
                    SwingUtilities.invokeLater(() -> {
                        previewCanvas.display();
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this,
                                "Fehler beim Laden der Textur:\n" + ex.getMessage());
                    });
                }
                return true;
            });
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainUI ui = new MainUI();
            ui.setVisible(true);
        });
    }
}

