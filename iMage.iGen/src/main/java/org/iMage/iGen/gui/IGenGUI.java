package org.iMage.iGen.gui;

import org.iMage.iGen.listener.FrameActionListener;
import org.iMage.iGen.listener.FrameKeyListener;
import org.iMage.iGen.utils.ImageUtils;
import org.iMage.screengen.DefaultScreenGenerator;
import org.iMage.screengen.base.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IGenGUI extends JFrame {

    public static int IMAGE_MAX_WIDTH = 540;
    public static int IMAGE_MAX_HEIGHT = 360;
    public static double INVALID_KEYING_DISTANCE = -1;

    public BufferedImage inputImage = null;
    public BufferedImage outputImage = null;
    public BufferedImage backgroundImage = null;
    public Color keyingColor = Color.decode("#43E23D");
    public double keyingDistance = 100;

    private final JLabel inputImageLabel = new JLabel();
    private final JLabel outputImageLabel = new JLabel();
    public final JButton keyingLoadInputButton = new JButton("Load Input");
    public final JButton keyingApplyButton = new JButton("Apply");
    public final JButton keyingColorSelectButton = new JButton("Select color chooser");
    public final JTextField keyingDistanceField = new JTextField(Double.toString(keyingDistance), 14);
    public final JButton enhanceRevertButton = new JButton("Revert");
    public final JButton enhanceApplyButton = new JButton("Apply");
    public final JButton enhanceSaveButton = new JButton("Save");
    public final JButton enhanceSelectImageButton = new JButton("Select Image...");
    public final JComboBox<Position> enhancePositionComboBox = new JComboBox<>(DefaultScreenGenerator.POSITIONS.toArray(new Position[0]));

    public IGenGUI() {

        init();
    }

    private void init() {

        setTitle("iGen");
        setSize(1240, 620);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        //URL imageUrl = getClass().getResource("/thumb.png");
        try {
            inputImage = ImageIO.read(new File("C:\\Users\\David\\Desktop\\thumb.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputImage = ImageUtils.getBlankImage(IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT);
        updateInputImageLabel();
        updateOutputImageLabel();
        keyingColorSelectButton.setBackground(keyingColor);

        panel.add(new JLabel("Input" + " ".repeat(188) + "Output" + " ".repeat(164)));
        panel.add(inputImageLabel);
        panel.add(new JLabel(" ".repeat(8)));
        panel.add(outputImageLabel);

        JPanel keyingConfigColorPanel = new JPanel();
        keyingConfigColorPanel.add(new JLabel("Color" + " ".repeat(90)));
        keyingConfigColorPanel.add(keyingColorSelectButton);

        JPanel keyingConfigDistancePanel = new JPanel();
        keyingConfigDistancePanel.add(new JLabel("Distance" + " ".repeat(82)));
        keyingConfigDistancePanel.add(keyingDistanceField);

        JPanel keyingConfigPanel = new JPanel();
        //keyingConfigPanel.setPreferredSize(new Dimension(0, 100));
        BoxLayout bl1 = new BoxLayout(keyingConfigPanel, BoxLayout.Y_AXIS);
        keyingConfigPanel.setLayout(bl1);
        Border buttonsBorder = new EmptyBorder(12, 16, 12, 16);
        Dimension blSpacerDim = new Dimension(140, 0);
        keyingConfigPanel.add(keyingConfigColorPanel);
        keyingConfigPanel.add(keyingConfigDistancePanel);
        keyingConfigPanel.setBorder(new TitledBorder("Configuration"));

        JPanel keyingButtonsPanel = new JPanel();
        BoxLayout bl2 = new BoxLayout(keyingButtonsPanel, BoxLayout.X_AXIS);
        keyingButtonsPanel.setLayout(bl2);
        keyingButtonsPanel.setBorder(buttonsBorder);
        keyingButtonsPanel.add(keyingLoadInputButton);
        keyingButtonsPanel.add(Box.createRigidArea(blSpacerDim));
        keyingButtonsPanel.add(new JLabel("--Bonus--"));
        keyingButtonsPanel.add(Box.createRigidArea(blSpacerDim));
        keyingButtonsPanel.add(keyingApplyButton);

        JPanel keyingPanel = new JPanel(new BorderLayout());
        keyingPanel.add(keyingButtonsPanel, BorderLayout.NORTH);
        keyingPanel.add(keyingConfigPanel, BorderLayout.CENTER);
        keyingPanel.setBorder(new TitledBorder("Keying"));

        JPanel enhanceConfigImagePanel = new JPanel();
        enhanceConfigImagePanel.add(new JLabel("Background Image" + " ".repeat(72)));
        enhanceConfigImagePanel.add(enhanceSelectImageButton);

        JPanel enhanceConfigPosPanel = new JPanel();
        enhanceConfigPosPanel.add(new JLabel("Position" + " ".repeat(88)));
        enhanceConfigPosPanel.add(enhancePositionComboBox);

        JPanel enhanceConfigPanel = new JPanel();
        BoxLayout bl3 = new BoxLayout(enhanceConfigPanel, BoxLayout.Y_AXIS);
        enhanceConfigPanel.setLayout(bl3);
        enhanceConfigPanel.setBorder(new TitledBorder("Configuration"));
        enhanceConfigPanel.add(enhanceConfigImagePanel);
        enhanceConfigPanel.add(enhanceConfigPosPanel);

        JPanel enhanceButtonsPanel = new JPanel();
        BoxLayout bl4 = new BoxLayout(enhanceButtonsPanel, BoxLayout.LINE_AXIS);
        enhanceButtonsPanel.setBorder(buttonsBorder);
        enhanceButtonsPanel.setLayout(bl4);
        enhanceButtonsPanel.add(enhanceRevertButton);
        enhanceButtonsPanel.add(Box.createRigidArea(blSpacerDim));
        enhanceButtonsPanel.add(enhanceApplyButton);
        enhanceButtonsPanel.add(Box.createRigidArea(blSpacerDim));
        enhanceButtonsPanel.add(enhanceSaveButton);

        JPanel enhancePanel = new JPanel(new BorderLayout());
        enhancePanel.add(enhanceButtonsPanel, BorderLayout.NORTH);
        enhancePanel.add(enhanceConfigPanel, BorderLayout.CENTER);
        enhancePanel.setBorder(new TitledBorder("Enhancement"));

        panel.add(keyingPanel);
        panel.add(new JLabel(" ".repeat(8)));
        panel.add(enhancePanel);

        add(panel);

        FrameActionListener al = new FrameActionListener(this);
        FrameKeyListener kl = new FrameKeyListener(this);
        keyingLoadInputButton.addActionListener(al);
        keyingApplyButton.addActionListener(al);
        keyingColorSelectButton.addActionListener(al);
        keyingDistanceField.addKeyListener(kl);
        enhanceRevertButton.addActionListener(al);
        enhanceApplyButton.addActionListener(al);
        enhanceSaveButton.addActionListener(al);
        enhanceSelectImageButton.addActionListener(al);

        setVisible(true);
    }

    public void updateInputImageLabel() {
        new Thread(() -> inputImageLabel.setIcon(new ImageIcon(ImageUtils.resizeKeepRelationAndFill(
                inputImage, IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT, Color.WHITE)))).start();
    }

    public void updateOutputImageLabel() {
        new Thread(() -> outputImageLabel.setIcon(new ImageIcon(ImageUtils.resizeKeepRelationAndFill(
                outputImage, IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT, Color.WHITE)))).start();
    }

}