package org.iMage.iGen.gui;

import org.iMage.iGen.gui.components.JOptionsPanel;
import org.iMage.iGen.gui.components.JTitledPanel;
import org.iMage.iGen.listener.FrameActionListener;
import org.iMage.iGen.listener.FrameItemListener;
import org.iMage.iGen.listener.FrameKeyListener;
import org.iMage.iGen.utils.ImageUtils;
import org.iMage.screengen.DefaultScreenGenerator;
import org.iMage.screengen.base.Position;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Graphical implementation of the the chroma-and luma-keying process.
 * This GUI is also capable of enhancing the processed image with a given background-image or even a text.
 *
 * @author David Rösler (KIT)
 * @version 1.0
 */
public class IGenGUI extends JFrame {

    public static final int IMAGE_MAX_WIDTH = 540;
    public static final int IMAGE_MAX_HEIGHT = 320;
    public static final Color BACKGROUND_COLOR = Color.decode("#EEEEEE");
    public static final int HORIZONTAL_PADDING = 20;
    public static final double INVALID_KEYING_DISTANCE = -1;
    public final String KEYING_MODE_CHROMA = "Chroma";
    public final String KEYING_MODE_LUMA = "Luma";
    public final String ENHANCE_MODE_BACKGROUND = "Background";
    public final String ENHANCE_MODE_TEXT = "Text";

    public BufferedImage inputImage = null;
    public BufferedImage outputImage = null;
    public ArrayList<BufferedImage> outputImageHistory = new ArrayList<>();
    public BufferedImage backgroundImage = null;
    public Color keyingColor = Color.decode("#43E23D");
    public double keyingDistance = 100;

    public final JLabel inputImageLabel = new JLabel();
    public final JLabel outputImageLabel = new JLabel();
    public final JButton keyingLoadInputButton = new JButton("Load Input");
    public final JComboBox<String> keyingModeComboBox = new JComboBox<>(
            new String[] {KEYING_MODE_CHROMA, KEYING_MODE_LUMA});
    public final JButton keyingApplyButton = new JButton("Apply");
    public final JPanel keyingConfigPanel = new JPanel(new CardLayout());
    public final JButton keyingColorSelectButton = new JButton("Select color chooser");
    public final JTextField keyingDistanceField = new JTextField(Double.toString(keyingDistance));
    public final JSlider keyingBrightnessMinSlider = new JSlider(0, 100, 25);
    public final JSlider keyingBrightnessMaxSlider = new JSlider(0, 100, 75);
    public final JComboBox<String> enhanceModeComboBox = new JComboBox<>(
            new String[] {ENHANCE_MODE_BACKGROUND, ENHANCE_MODE_TEXT});
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

    /**
     * Constructor starts the initialization of the frame.
     */
    public IGenGUI() {

        EventQueue.invokeLater(this::init);
    }

    /**
     * Initialization-method, which builds all the GUI-compontens and adds them to the frame.
     * Also creating and applying listeners to make the frame interactive.
     */
    private void init() {
        //set window properties
        setTitle("iGen");
        setSize(1240, 630);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //set default images
        inputImage = ImageUtils.getBlankImage(IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT, keyingColor);
        outputImage = ImageUtils.getBlankImage(IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT, Color.WHITE);
        updateInputImageLabel();
        updateOutputImageLabel();
        //set specific properties of some components
        keyingColorSelectButton.setBackground(keyingColor);
        enhanceSelectImageButton.setPreferredSize(new Dimension(146, 30));
        enhanceSizeSpinner.setValue(16);
        //set padding-insets
        Insets bigPadding = new Insets(10, HORIZONTAL_PADDING, 10, HORIZONTAL_PADDING);
        Insets smallPadding = new Insets(1, HORIZONTAL_PADDING, 1, HORIZONTAL_PADDING);
        Insets sliderLabelPadding = new Insets(0, 6, 0, 6);
        //create keying-configuration-panel for chroma mode
        JOptionsPanel keyingConfigColorPanel = new JOptionsPanel(bigPadding);
        keyingConfigColorPanel.addAll(new JLabel("Color"), keyingColorSelectButton);
        JOptionsPanel keyingConfigDistancePanel = new JOptionsPanel(bigPadding);
        keyingConfigDistancePanel.addAll(new JLabel("Distance" + " ".repeat(93)), keyingDistanceField);
        JOptionsPanel keyingChromaConfigPanel = new JOptionsPanel(BoxLayout.Y_AXIS);
        keyingChromaConfigPanel.addAll(keyingConfigColorPanel, keyingConfigDistancePanel);
        //create keying-configuration-panel for luma mode
        JOptionsPanel sliderValPanel1 = new JOptionsPanel(sliderLabelPadding);
        sliderValPanel1.addAll(new JLabel("0.0"), new JLabel("0.5"), new JLabel("1.0"));
        JOptionsPanel minSliderPanel = new JOptionsPanel(BoxLayout.Y_AXIS);
        minSliderPanel.addAll(keyingBrightnessMinSlider, sliderValPanel1);
        JOptionsPanel keyingConfigBrightnessMinPanel = new JOptionsPanel(smallPadding);
        keyingConfigBrightnessMinPanel.addAll(new JLabel("Brightness(minimum) "), minSliderPanel);
        JOptionsPanel sliderValPanel2 = new JOptionsPanel(sliderLabelPadding);
        sliderValPanel2.addAll(new JLabel("0.0"), new JLabel("0.5"), new JLabel("1.0"));
        JOptionsPanel maxSliderPanel = new JOptionsPanel(BoxLayout.Y_AXIS);
        maxSliderPanel.addAll(keyingBrightnessMaxSlider, sliderValPanel2);
        JOptionsPanel keyingConfigBrightnessMaxPanel = new JOptionsPanel(smallPadding);
        keyingConfigBrightnessMaxPanel.addAll(new JLabel("Brightness(maximum)"), maxSliderPanel);
        JOptionsPanel keyingLumaConfigPanel = new JOptionsPanel(BoxLayout.Y_AXIS);
        keyingLumaConfigPanel.addAll(keyingConfigBrightnessMinPanel, keyingConfigBrightnessMaxPanel);
        //adding configuration-panels to cardlayout
        keyingConfigPanel.add(keyingChromaConfigPanel, KEYING_MODE_CHROMA);
        keyingConfigPanel.add(keyingLumaConfigPanel, KEYING_MODE_LUMA);
        //adding all components together to the keying-panel
        JOptionsPanel keyingButtonsPanel = new JOptionsPanel(bigPadding);
        keyingButtonsPanel.addAll(keyingLoadInputButton, keyingModeComboBox, keyingApplyButton);
        JTitledPanel keyingPanel = new JTitledPanel("Keying", BoxLayout.Y_AXIS,
                keyingButtonsPanel, new JTitledPanel("Configuration", keyingConfigPanel));
        keyingPanel.setPreferredSize(new Dimension(IMAGE_MAX_WIDTH, 191));
        //create enhancement-configuration-panel for background mode
        JOptionsPanel enhanceConfigImagePanel = new JOptionsPanel(bigPadding);
        enhanceConfigImagePanel.addAll(new JLabel("Background Image"), enhanceSelectImageButton);
        JOptionsPanel enhanceConfigPosPanel1 = new JOptionsPanel(bigPadding);
        enhanceConfigPosPanel1.addAll(new JLabel("Position" + " ".repeat(92)), enhancePositionComboBox1);
        JOptionsPanel enhanceBackgroundConfigPanel = new JOptionsPanel(BoxLayout.Y_AXIS);
        enhanceBackgroundConfigPanel.addAll(enhanceConfigImagePanel, enhanceConfigPosPanel1);
        //create enhancement-configuration-panel for text mode
        JOptionsPanel enhanceConfigTextPanel = new JOptionsPanel(smallPadding);
        enhanceConfigTextPanel.addAll(new JLabel("Text" + " ".repeat(90)), enhanceTextField);
        JOptionsPanel enhanceConfigFontPanel = new JOptionsPanel(smallPadding);
        enhanceConfigFontPanel.addAll(new JLabel("Font" + " ".repeat(90)), enhanceFontComboBox);
        JOptionsPanel enhanceConfigSizePanel = new JOptionsPanel(smallPadding);
        enhanceConfigSizePanel.addAll(new JLabel("Size" + " ".repeat(41)), enhanceSizeSpinner);
        JOptionsPanel enhanceConfigPosPanel2 = new JOptionsPanel(smallPadding);
        enhanceConfigPosPanel2.addAll(new JLabel("Position" + " ".repeat(66)), enhancePositionComboBox2);
        JOptionsPanel enhanceTextConfigPanel = new JOptionsPanel(BoxLayout.Y_AXIS);
        enhanceTextConfigPanel.addAll(
                enhanceConfigTextPanel, enhanceConfigFontPanel, enhanceConfigSizePanel, enhanceConfigPosPanel2);
        //adding configuration-panels to cardlayout
        enhanceConfigPanel.add(enhanceBackgroundConfigPanel, ENHANCE_MODE_BACKGROUND);
        enhanceConfigPanel.add(enhanceTextConfigPanel, ENHANCE_MODE_TEXT);
        //adding all components together to the enhancement-panel
        JOptionsPanel enhanceButtonsPanel = new JOptionsPanel(bigPadding);
        enhanceButtonsPanel.addAll(enhanceModeComboBox, enhanceRevertButton, enhanceApplyButton, enhanceSaveButton);
        JTitledPanel enhancePanel = new JTitledPanel("Enhancement", BoxLayout.Y_AXIS,
                enhanceButtonsPanel, new JTitledPanel("Configuration", enhanceConfigPanel));
        enhancePanel.setPreferredSize(new Dimension(IMAGE_MAX_WIDTH, 191));
        //create "Input" and "Output" Label
        JPanel inputOutputLabelPanel = new JPanel();
        inputOutputLabelPanel.add(new JLabel("Input" + " ".repeat(182) + "Output" + " ".repeat(164)));
        //create panel with input-and output-image
        JPanel inputOutputImageLabelPanel = new JPanel();
        inputOutputImageLabelPanel.add(inputImageLabel);
        inputOutputImageLabelPanel.add(new JLabel(" ".repeat(8)));
        inputOutputImageLabelPanel.add(outputImageLabel);
        //create panel with keying and enhancement options
        JPanel keyingEnhancePanel = new JPanel();
        keyingEnhancePanel.add(keyingPanel);
        keyingEnhancePanel.add(new JLabel(" ".repeat(8)));
        keyingEnhancePanel.add(enhancePanel);
        //create final main-panel, adding all components and writing it on the frame
        JOptionsPanel panel = new JOptionsPanel(BoxLayout.Y_AXIS);
        panel.addAll(inputOutputLabelPanel, inputOutputImageLabelPanel, keyingEnhancePanel);
        add(panel);
        //creating listeners and applying them to all needed components
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
        //finally setting the frame visible
        setVisible(true);
    }

    /**
     * Create new thread, which updates the icon of the inputImageLabel to the new input-image.
     */
    public void updateInputImageLabel() {
        new Thread(() -> inputImageLabel.setIcon(new ImageIcon(ImageUtils.resizeKeepRelationAndFill(
                inputImage, IGenGUI.IMAGE_MAX_WIDTH, IGenGUI.IMAGE_MAX_HEIGHT, IGenGUI.BACKGROUND_COLOR)))).start();
    }

    /**
     * Same algorithm as above just for the output-image.
     */
    public void updateOutputImageLabel() {
        new Thread(() -> outputImageLabel.setIcon(new ImageIcon(ImageUtils.resizeKeepRelationAndFill(
                outputImage, IGenGUI.IMAGE_MAX_WIDTH, IGenGUI.IMAGE_MAX_HEIGHT, IGenGUI.BACKGROUND_COLOR)))).start();
    }

}