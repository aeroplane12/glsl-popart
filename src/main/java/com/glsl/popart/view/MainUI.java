package com.glsl.popart.view;

import com.glsl.popart.controller.PipelineManager;
import com.glsl.popart.model.*;
import com.glsl.popart.utils.Renderer;
import com.glsl.popart.utils.TextureUtils;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.texture.Texture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class MainUI extends JFrame {
    private JList<String> popArtEffectsList;
    private JList<String> physicalEffectsList;
    private GLCanvas previewCanvas;
    private Renderer renderer;
    private Texture loadedTexture;
    private int textureId = -1;
    private ShaderManager shaderManager;
    private ShaderPipeline shaderPipeline;
    private PipelineManager pipelineManager;
    private int canvasWidth = 400;
    private int canvasHeight = 600;
    private final Set<String> activeShaders = new HashSet<>();

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

        setupEffectList(popArtEffectsList);
        setupEffectList(physicalEffectsList);

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

                pipelineManager = new PipelineManager(canvasWidth, canvasHeight);
                pipelineManager.initShaders(gl);
                pipelineManager.initFBOs(gl);
                pipelineManager.setupPipeline();

                shaderManager = pipelineManager.getShaderManager();
                shaderPipeline = pipelineManager.getShaderPipeline();
            }

            @Override
            public void display(GLAutoDrawable drawable) {
                GL2 gl = drawable.getGL().getGL2();

                gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

                if (loadedTexture != null) {
                    loadedTexture.enable(gl);
                    loadedTexture.bind(gl);

                    int inputTextureId = loadedTexture.getTextureObject(gl);
                    int finalTextureId = shaderPipeline.renderPipeline(gl, inputTextureId);

                    renderer.renderTextureToScreen(gl, finalTextureId, canvasWidth, canvasHeight);

                    loadedTexture.disable(gl);
                }
            }

            @Override
            public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
                canvasWidth = w;
                canvasHeight = h;
            }
            @Override
            public void dispose(GLAutoDrawable drawable) {}
        });

        previewCanvas.setPreferredSize(new Dimension(canvasWidth, canvasHeight));

        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                effectPanel, previewCanvas);
        mainSplit.setDividerLocation(300);
        add(mainSplit, BorderLayout.CENTER);

        // Button unten
        JButton loadTextureButton = new JButton("Textur hochladen");
        loadTextureButton.addActionListener(e -> onLoadTexture());

        JButton editParamsButton = new JButton("Parameter");
        editParamsButton.addActionListener(e -> onEditParameters());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(loadTextureButton);
        bottomPanel.add(editParamsButton);
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

    private void setupEffectList(JList<String> list) {
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = list.locationToIndex(e.getPoint());
                if (index != -1) {
                    String selected = list.getModel().getElementAt(index);
                    if (activeShaders.contains(selected)) {
                        activeShaders.remove(selected);
                        list.removeSelectionInterval(index, index);
                    } else {
                        activeShaders.add(selected);
                        list.addSelectionInterval(index, index);
                    }
                    updateShaderPipeline();
                }
            }
        });
    }

    private void updateShaderPipeline() {
        if (shaderPipeline == null) return;

        previewCanvas.invoke(true, drawable -> {
            shaderPipeline.clearShaders();
            for (String shader : activeShaders) {
                shaderPipeline.addShader(shader);
            }
            SwingUtilities.invokeLater(() -> previewCanvas.display());
            return true;
        });
    }

    private void onEditParameters() {
        if (activeShaders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kein Effekt aktiv.");
            return;
        }

        for (String effectName : activeShaders) {
            showParameterDialog(effectName);
        }
    }

    private void showParameterDialog(String effectName) {
        switch (effectName) {
            case "posterization":
                showPosterizationDialog();
                break;
            case "halftone":
                showHalftoneDialog();
                break;
            case "comiclook":
                showComicLookDialog();
                break;
            case "duotone":
                showDuotoneDialog();
                break;
            case "selectivecolorboost":
                showSelectiveColorBoostDialog();
                break;
            case "bendaydots":
                showBendayDotsDialog();
                break;
            case "tritone":
                showTritoneDialog();
                break;
            case "OutOfRegisterPrintShader":
                showOutOfRegisterPrintDialog();
                break;
            case "IntelligentBoldOutlines":
                showIntelligentBoldOutlinesDialog();
                break;
            case "seriality":
                showSerialityDialog();
                break;
            case "distortion":
                showDistortionDialog();
                break;
            case "bloom":
                showBloomDialog();
                break;
            case "chromaticaberration":
                showChromaticAberrationDialog();
                break;
            case "noise":
                showNoiseDialog();
                break;
            case "vignette":
                showVignetteDialog();
                break;
            case "chromaticwavedistortion":
                showChromaticWaveDistortionDialog();
                break;
            case "scanline":
                showScanlineDialog();
                break;
            case "waterripple":
                showWaterRippleDialog();
                break;
            case "heatdistortion":
                showHeatDistortionDialog();
                break;
            case "refraction":
                showRefractionDialog();
                break;
            default:
                JOptionPane.showMessageDialog(this,
                        "Kein Parameterdialog für: " + effectName);
                break;
        }
    }

    private void showPosterizationDialog() {
        PosterizationDialog dialog = new PosterizationDialog(this);
        dialog.setVisible(true);
        int level = dialog.getPosterizationLevel();
        System.out.println("Setze Posterization-Uniform-Level auf: " + level);

        // UniformSetter abrufen und neuen Level setzen
        ShaderUniformSetter setter = shaderPipeline.getUniformSetter("posterization");
        if (setter instanceof PosterizationUniformSetter) {
            ((PosterizationUniformSetter) setter).setLevels(level);
        }

        // Vorschau neu zeichnen
        previewCanvas.display();
    }

    private void showHalftoneDialog() {
        HalftoneDialog dialog = new HalftoneDialog(this);
        dialog.setVisible(true);
        int dotSize = dialog.getDotSize();
        System.out.println("Setze Halftone dotSize Uniform auf: " + dotSize);

        ShaderUniformSetter setter = shaderPipeline.getUniformSetter("halftone");
        if (setter instanceof HalftoneUniformSetter) {
            ((HalftoneUniformSetter) setter).setDotSize(dotSize);
        }

        previewCanvas.display();
    }

    private void showComicLookDialog() {
        ComicLookDialog dialog = new ComicLookDialog(this);
        dialog.setVisible(true);
        int levels = dialog.getLevels();
        System.out.println("Setze ComicLook-Uniform-Level auf: " + levels);

        ShaderUniformSetter setter = shaderPipeline.getUniformSetter("comiclook");
        if (setter instanceof ComicLookUniformSetter) {
            ((ComicLookUniformSetter) setter).setLevels(levels);
        }

        previewCanvas.display();
    }

    private void showDuotoneDialog() {
        DuotoneDialog dialog = new DuotoneDialog(this);
        dialog.setVisible(true);

        float[] dark = dialog.getDarkColor();
        float[] light = dialog.getLightColor();

        System.out.println("Setze Duotone-Farben:");
        System.out.printf("Dark:  [%.2f, %.2f, %.2f]%n", dark[0], dark[1], dark[2]);
        System.out.printf("Light: [%.2f, %.2f, %.2f]%n", light[0], light[1], light[2]);

        ShaderUniformSetter setter = shaderPipeline.getUniformSetter("duotone");
        if (setter instanceof DuotoneUniformSetter) {
            ((DuotoneUniformSetter) setter).setDarkColor(dark);
            ((DuotoneUniformSetter) setter).setLightColor(light);
        }

        previewCanvas.display();
    }

    private void showSelectiveColorBoostDialog() {
        SelectiveColorBoostDialog dialog = new SelectiveColorBoostDialog(this);
        dialog.setVisible(true);

        float threshold = dialog.getThreshold();
        float boost = dialog.getBoostAmount();
        float[] color = dialog.getTargetColor();

        System.out.printf("SelectiveColorBoost → Threshold: %.2f, Boost: %.2f, Color: [%.2f, %.2f, %.2f]%n",
                threshold, boost, color[0], color[1], color[2]);

        ShaderUniformSetter setter = shaderPipeline.getUniformSetter("selectivecolorboost");
        if (setter instanceof SelectiveColorBoostUniformSetter) {
            ((SelectiveColorBoostUniformSetter) setter).setThreshold(threshold);
            ((SelectiveColorBoostUniformSetter) setter).setBoostAmount(boost);
            ((SelectiveColorBoostUniformSetter) setter).setTargetColor(color);
        }

        previewCanvas.display();
    }

    private void showBendayDotsDialog() {
        BenDayDotsDialog dialog = new BenDayDotsDialog(this);
        dialog.setVisible(true);
        int dotSize = dialog.getDotSize();
        System.out.println("Setze BenDayDots dotSize auf: " + dotSize);

        ShaderUniformSetter setter = shaderPipeline.getUniformSetter("bendaydots");
        if (setter instanceof BenDayDotsUniformSetter) {
            ((BenDayDotsUniformSetter) setter).setDotSize(dotSize);
        }

        previewCanvas.display();
    }

    private void showTritoneDialog() {
        TritoneDialog dialog = new TritoneDialog(this);
        dialog.setVisible(true);

        float[] shadow = dialog.getShadowColor();
        float[] midtone = dialog.getMidtoneColor();
        float[] highlight = dialog.getHighlightColor();

        System.out.println("Tritone-Farben:");
        System.out.printf("Dark: [%.2f, %.2f, %.2f]%n", shadow[0], shadow[1], shadow[2]);
        System.out.printf("Middle: [%.2f, %.2f, %.2f]%n", midtone[0], midtone[1], midtone[2]);
        System.out.printf("Light: [%.2f, %.2f, %.2f]%n", highlight[0], highlight[1], highlight[2]);

        ShaderUniformSetter setter = shaderPipeline.getUniformSetter("tritone");
        if (setter instanceof TritoneUniformSetter) {
            TritoneUniformSetter tritoneSetter = (TritoneUniformSetter) setter;
            tritoneSetter.setShadowColor(shadow);
            tritoneSetter.setMidtoneColor(midtone);
            tritoneSetter.setHighlightColor(highlight);
        }

        previewCanvas.display();
    }

    private void showOutOfRegisterPrintDialog() {
        OutOfRegisterPrintShaderDialog dialog = new OutOfRegisterPrintShaderDialog(this);
        dialog.setVisible(true);
        float offset = dialog.getSelectedOffset();
        System.out.println("Setze OutOfRegisterPrint-Offset auf: " + offset);

        ShaderUniformSetter setter = shaderPipeline.getUniformSetter("OutOfRegisterPrintShader");
        if (setter instanceof OutOfRegisterPrintShaderUniformSetter) {
            ((OutOfRegisterPrintShaderUniformSetter) setter).setOffset(offset);
        }

        previewCanvas.display();
    }

    private void showIntelligentBoldOutlinesDialog() {
        IntelligentBoldOutlinesDialog dialog = new IntelligentBoldOutlinesDialog(this);
        dialog.setVisible(true);
        float thickness = dialog.getThickness();
        float edgeThreshold = dialog.getEdgeThreshold();
        System.out.printf("IntelligentBoldOutlines → Thickness: %.2f, EdgeThreshold: %.2f%n",
                thickness, edgeThreshold);

        ShaderUniformSetter setter = shaderPipeline.getUniformSetter("IntelligentBoldOutlines");
        if (setter instanceof IntelligentBoldOutlinesUniformSetter) {
            ((IntelligentBoldOutlinesUniformSetter) setter).setThickness(thickness);
            ((IntelligentBoldOutlinesUniformSetter) setter).setEdgeThreshold(edgeThreshold);
        }

        previewCanvas.display();
    }

    private void showSerialityDialog() {
        JOptionPane.showMessageDialog(this, "Hier kommt dein SerialityDialog.");
    }

    private void showDistortionDialog() {
        JOptionPane.showMessageDialog(this, "Hier kommt dein DistortionDialog.");
    }

    private void showBloomDialog() {
        JOptionPane.showMessageDialog(this, "Hier kommt dein BloomDialog.");
    }

    private void showChromaticAberrationDialog() {
        JOptionPane.showMessageDialog(this, "Hier kommt dein ChromaticAberrationDialog.");
    }

    private void showNoiseDialog() {
        JOptionPane.showMessageDialog(this, "Hier kommt dein NoiseDialog.");
    }

    private void showVignetteDialog() {
        JOptionPane.showMessageDialog(this, "Hier kommt dein VignetteDialog.");
    }

    private void showChromaticWaveDistortionDialog() {
        JOptionPane.showMessageDialog(this, "Hier kommt dein ChromaticWaveDistortionDialog.");
    }

    private void showScanlineDialog() {
        JOptionPane.showMessageDialog(this, "Hier kommt dein ScanlineDialog.");
    }

    private void showWaterRippleDialog() {
        JOptionPane.showMessageDialog(this, "Hier kommt dein WaterRippleDialog.");
    }

    private void showHeatDistortionDialog() {
        JOptionPane.showMessageDialog(this, "Hier kommt dein HeatDistortionDialog.");
    }

    private void showRefractionDialog() {
        JOptionPane.showMessageDialog(this, "Hier kommt dein RefractionDialog.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainUI ui = new MainUI();
            ui.setVisible(true);
        });
    }
}

