package org.iMage.iGen.gui;

import org.iMage.iGen.gui.components.JOptionsPanel;
import org.iMage.iGen.gui.components.JTitledPanel;
import org.iMage.iGen.listener.FrameActionListener;
import org.iMage.iGen.listener.FrameItemListener;
import org.iMage.iGen.listener.FrameKeyListener;
import org.iMage.iGen.utils.ImageUtils;
import org.iMage.screengen.DefaultScreenGenerator;
import org.iMage.screengen.base.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class IGenGUI extends JFrame {

    public static final int IMAGE_MAX_WIDTH = 540;
    public static final int IMAGE_MAX_HEIGHT = 320;
    public static final Color BACKGROUND_COLOR = Color.decode("#EEEEEE");
    public static final int HORIZONTAL_PADDING = 20;
    public static final int OUTPUTIMAGE_STATE_DEFAULT = 0;
    public static final int OUTPUTIMAGE_STATE_KEYED = 1;
    public static final int OUTPUTIMAGE_STATE_ENHANCED = 2;
    public static final double INVALID_KEYING_DISTANCE = -1;
    public final String KEYING_MODE_CHROMA = "Chroma";
    public final String KEYING_MODE_LUMA = "Luma";
    public final String ENHANCE_MODE_BACKGROUND = "Background";
    public final String ENHANCE_MODE_TEXT = "Text";

    public BufferedImage inputImage = null;
    public BufferedImage outputImage = null;
    public int outputImageState = OUTPUTIMAGE_STATE_DEFAULT;
    public BufferedImage lastOutputImage = null;
    public BufferedImage backgroundImage = null;
    public Color keyingColor = Color.decode("#43E23D");
    public double keyingDistance = 100;

    private final JLabel inputImageLabel = new JLabel();
    private final JLabel outputImageLabel = new JLabel();
    public final JButton keyingLoadInputButton = new JButton("Load Input");
    public final JComboBox<String> keyingModeComboBox = new JComboBox<>(new String[]{KEYING_MODE_CHROMA, KEYING_MODE_LUMA});
    public final JButton keyingApplyButton = new JButton("Apply");
    public final JPanel keyingConfigPanel = new JPanel(new CardLayout());
    public final JButton keyingColorSelectButton = new JButton("Select color chooser");
    public final JTextField keyingDistanceField = new JTextField(Double.toString(keyingDistance), 14);
    public final JSlider keyingBrightnessMinSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 25);
    public final JSlider keyingBrightnessMaxSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 75);
    public final JComboBox<String> enhanceModeComboBox = new JComboBox<>(new String[]{ENHANCE_MODE_BACKGROUND, ENHANCE_MODE_TEXT});
    public final JButton enhanceRevertButton = new JButton("Revert");
    public final JButton enhanceApplyButton = new JButton("Apply");
    public final JButton enhanceSaveButton = new JButton("Save");
    public final JPanel enhanceConfigPanel = new JPanel(new CardLayout());
    public final JButton enhanceSelectImageButton = new JButton("Select Image...");
    public final JComboBox<Position> enhancePositionComboBox1 = new JComboBox<>(
            DefaultScreenGenerator.POSITIONS.toArray(new Position[0]));
    public final JTextField enhanceTextField = new JTextField("Hello World");
    public final JComboBox<String> enhanceFontComboBox = new JComboBox<>(
            GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
    public final JSpinner enhanceSizeSpinner = new JSpinner();
    public final JComboBox<Position> enhancePositionComboBox2 = new JComboBox<>(
            DefaultScreenGenerator.POSITIONS.toArray(new Position[0]));

    public IGenGUI() {

        EventQueue.invokeLater(this::init);
    }

    private void init() {

        setTitle("iGen");
        setSize(1240, 630);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inputImage = ImageUtils.getBlankImage(IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT, keyingColor);
        outputImage = ImageUtils.getBlankImage(IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT, Color.WHITE);
        updateInputImageLabel();
        updateOutputImageLabel();
        keyingColorSelectButton.setBackground(keyingColor);
        enhanceSizeSpinner.setValue(16);

        Insets defaultPadding = new Insets(10, HORIZONTAL_PADDING, 10, HORIZONTAL_PADDING);
        Insets sliderLabelPadding = new Insets(0, 6, 0, 6);
        Insets sliderPadding = new Insets(1, HORIZONTAL_PADDING, 1, HORIZONTAL_PADDING);

        JOptionsPanel keyingConfigColorPanel = new JOptionsPanel(defaultPadding);
        keyingConfigColorPanel.addAll(new JLabel("Color"), keyingColorSelectButton);

        JOptionsPanel keyingConfigDistancePanel = new JOptionsPanel(defaultPadding);
        keyingConfigDistancePanel.addAll(new JLabel("Distance" + " ".repeat(93)), keyingDistanceField);

        JOptionsPanel keyingChromaConfigPanel = new JOptionsPanel(BoxLayout.Y_AXIS);
        keyingChromaConfigPanel.addAll(keyingConfigColorPanel, keyingConfigDistancePanel);

        JOptionsPanel sliderValPanel1 = new JOptionsPanel(sliderLabelPadding);
        sliderValPanel1.addAll(new JLabel("0.0"), new JLabel("0.5"), new JLabel("1.0"));

        JOptionsPanel minSliderPanel = new JOptionsPanel(BoxLayout.Y_AXIS);
        minSliderPanel.addAll(keyingBrightnessMinSlider, sliderValPanel1);

        JOptionsPanel keyingConfigBrightnessMinPanel = new JOptionsPanel(sliderPadding);
        keyingConfigBrightnessMinPanel.addAll(new JLabel("Brightness(minimum) "), minSliderPanel);

        JOptionsPanel sliderValPanel2 = new JOptionsPanel(sliderLabelPadding);
        sliderValPanel2.addAll(new JLabel("0.0"), new JLabel("0.5"), new JLabel("1.0"));

        JOptionsPanel maxSliderPanel = new JOptionsPanel(BoxLayout.Y_AXIS);
        maxSliderPanel.addAll(keyingBrightnessMaxSlider, sliderValPanel2);

        JOptionsPanel keyingConfigBrightnessMaxPanel = new JOptionsPanel(sliderPadding);
        keyingConfigBrightnessMaxPanel.addAll(new JLabel("Brightness(maximum)"), maxSliderPanel);

        JOptionsPanel keyingLumaConfigPanel = new JOptionsPanel(BoxLayout.Y_AXIS);
        keyingLumaConfigPanel.addAll(keyingConfigBrightnessMinPanel, keyingConfigBrightnessMaxPanel);

        keyingConfigPanel.add(keyingChromaConfigPanel, KEYING_MODE_CHROMA);
        keyingConfigPanel.add(keyingLumaConfigPanel, KEYING_MODE_LUMA);

        JOptionsPanel keyingButtonsPanel = new JOptionsPanel(defaultPadding);
        keyingButtonsPanel.addAll(keyingLoadInputButton, keyingModeComboBox, keyingApplyButton);

        JTitledPanel keyingPanel = new JTitledPanel("Keying", BoxLayout.Y_AXIS,
                keyingButtonsPanel, new JTitledPanel("Configuration", keyingConfigPanel));
        keyingPanel.setPreferredSize(new Dimension(IMAGE_MAX_WIDTH, 190));

        enhanceSelectImageButton.setPreferredSize(new Dimension(146, 30));
        JOptionsPanel enhanceConfigImagePanel = new JOptionsPanel(defaultPadding);
        enhanceConfigImagePanel.addAll(new JLabel("Background Image"), enhanceSelectImageButton);

        JOptionsPanel enhanceConfigPosPanel1 = new JOptionsPanel(defaultPadding);
        enhanceConfigPosPanel1.addAll(new JLabel("Position" + " ".repeat(92)), enhancePositionComboBox1);

        JOptionsPanel enhanceBackgroundConfigPanel = new JOptionsPanel(BoxLayout.Y_AXIS);
        enhanceBackgroundConfigPanel.addAll(enhanceConfigImagePanel, enhanceConfigPosPanel1);

        JOptionsPanel enhanceConfigTextPanel = new JOptionsPanel(sliderPadding);
        enhanceConfigTextPanel.addAll(new JLabel("Text" + " ".repeat(90)), enhanceTextField);

        JOptionsPanel enhanceConfigFontPanel = new JOptionsPanel(sliderPadding);
        enhanceConfigFontPanel.addAll(new JLabel("Font" + " ".repeat(90)), enhanceFontComboBox);

        JOptionsPanel enhanceConfigSizePanel = new JOptionsPanel(sliderPadding);
        enhanceConfigSizePanel.addAll(new JLabel("Size" + " ".repeat(41)), enhanceSizeSpinner);

        JOptionsPanel enhanceConfigPosPanel2 = new JOptionsPanel(sliderPadding);
        enhanceConfigPosPanel2.addAll(new JLabel("Position" + " ".repeat(66)), enhancePositionComboBox2);

        JOptionsPanel enhanceTextConfigPanel = new JOptionsPanel(BoxLayout.Y_AXIS);
        enhanceTextConfigPanel.addAll(enhanceConfigTextPanel, enhanceConfigFontPanel, enhanceConfigSizePanel, enhanceConfigPosPanel2);

        enhanceConfigPanel.add(enhanceBackgroundConfigPanel, ENHANCE_MODE_BACKGROUND);
        enhanceConfigPanel.add(enhanceTextConfigPanel, ENHANCE_MODE_TEXT);

        JOptionsPanel enhanceButtonsPanel = new JOptionsPanel(defaultPadding);
        enhanceButtonsPanel.addAll(enhanceModeComboBox, enhanceRevertButton, enhanceApplyButton, enhanceSaveButton);

        JTitledPanel enhancePanel = new JTitledPanel("Enhancement", BoxLayout.Y_AXIS,
                enhanceButtonsPanel, new JTitledPanel("Configuration", enhanceConfigPanel));
        enhancePanel.setPreferredSize(new Dimension(IMAGE_MAX_WIDTH, 190));

        JOptionsPanel total = new JOptionsPanel(BoxLayout.Y_AXIS);
        JPanel l = new JPanel();
        l.add(new JLabel("Input" + " ".repeat(182) + "Output" + " ".repeat(164)));
        JPanel i = new JPanel();
        i.add(inputImageLabel);
        i.add(new JLabel(" ".repeat(8)));
        i.add(outputImageLabel);
        JPanel d = new JPanel();
        d.add(keyingPanel);
        d.add(new JLabel(" ".repeat(8)));
        d.add(enhancePanel);

        total.addAll(l, i, d);

        add(total);

        FrameActionListener al = new FrameActionListener(this);
        FrameKeyListener kl = new FrameKeyListener(this);
        FrameItemListener il = new FrameItemListener(this);
        keyingLoadInputButton.addActionListener(al);
        keyingModeComboBox.addItemListener(il);
        keyingApplyButton.addActionListener(al);
        keyingColorSelectButton.addActionListener(al);
        keyingDistanceField.addKeyListener(kl);
        enhanceModeComboBox.addItemListener(il);
        enhanceRevertButton.addActionListener(al);
        enhanceApplyButton.addActionListener(al);
        enhanceSaveButton.addActionListener(al);
        enhanceSelectImageButton.addActionListener(al);
        enhanceSizeSpinner.addChangeListener(e -> {
            if ((int) enhanceSizeSpinner.getValue() <= 0) {
                enhanceSizeSpinner.setValue(1);
            }
        });

        setVisible(true);
    }

    public void updateInputImageLabel() {
        new Thread(() -> inputImageLabel.setIcon(new ImageIcon(ImageUtils.resizeKeepRelationAndFill(
                inputImage, IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT, BACKGROUND_COLOR)))).start();
    }

    public void updateOutputImageLabel() {
        new Thread(() -> outputImageLabel.setIcon(new ImageIcon(ImageUtils.resizeKeepRelationAndFill(
                outputImage, IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT, BACKGROUND_COLOR)))).start();
    }

}