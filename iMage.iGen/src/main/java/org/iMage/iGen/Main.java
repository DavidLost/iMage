package org.iMage.iGen;

import org.iMage.screengen.DefaultScreenGenerator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame {

    public static int IMAGE_MAX_WIDTH = 560;
    public static int IMAGE_MAX_HEIGHT = 380;

    BufferedImage inputImage = null;
    BufferedImage outputImage = null;
    Color keyingColor = Color.decode("#43E23D");

    JLabel inputImageLabel = new JLabel();
    JLabel outputImageLabel = new JLabel();
    JButton keyingLoadInputButton = new JButton("Load Input");
    JButton keyingApplyButton = new JButton("Apply");
    JButton keyingColorSelectButton = new JButton("Select color chooser");
    JTextField keyingDistanceField = new JTextField("100.0");
    JButton enhanceRevertButton = new JButton("Revert");
    JButton enhanceApplyButton = new JButton("Apply");
    JButton enhanceSaveButton = new JButton("Save");
    JButton enhanceSelectImageButton = new JButton("Select Image");

    public static void main(String[] args) {

        new Main();
    }

    public Main() {

        init();
    }

    private void init() {

        setTitle("iGen");
        setSize(1240, 620);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        BufferedImage inputImage = null;
        try {
            inputImage = ImageIO.read(new File("C:\\Users\\David\\Desktop\\karlsruhe.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputImageLabel.setIcon(new ImageIcon(GraphicUtils.resizeKeepRelation(inputImage, IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT)));
        outputImageLabel.setIcon(new ImageIcon(GraphicUtils.resizeKeepRelation(inputImage, IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT)));
        keyingColorSelectButton.setBackground(keyingColor);

        panel.add(new JLabel("Input" + " ".repeat(188) + "Output" + " ".repeat(164)));
        panel.add(inputImageLabel);
        panel.add(new JLabel(" ".repeat(8)));
        panel.add(outputImageLabel);

        JPanel keyingConfigPanel = new JPanel(new GridLayout(2, 2, 130, 32));
        keyingConfigPanel.add(new JLabel("Color"));
        keyingConfigPanel.add(keyingColorSelectButton);
        keyingConfigPanel.add(new JLabel("Distance"));
        keyingConfigPanel.add(keyingDistanceField);
        keyingConfigPanel.setBorder(new TitledBorder("Configuration"));

        JPanel keyingButtonsPanel = new JPanel(new GridLayout());
        keyingButtonsPanel.add(keyingLoadInputButton);
        keyingButtonsPanel.add(new JLabel("--Bonus--"));
        keyingButtonsPanel.add(keyingApplyButton);

        JPanel keyingPanel = new JPanel(new BorderLayout());
        keyingPanel.add(keyingButtonsPanel, BorderLayout.NORTH);
        keyingPanel.add(keyingConfigPanel, BorderLayout.CENTER);
        keyingPanel.setBorder(new TitledBorder("Keying"));

        JPanel enhanceConfigPanel = new JPanel(new GridLayout(2, 2, 220, 32));
        enhanceConfigPanel.add(new JLabel("Background Image"));
        enhanceConfigPanel.add(enhanceSelectImageButton);
        enhanceConfigPanel.add(new JLabel("Position"));
        enhanceConfigPanel.add(new JComboBox<>());
        enhanceConfigPanel.setBorder(new TitledBorder("Configuration"));

        JPanel enhanceButtonsPanel = new JPanel(new GridLayout());
        enhanceButtonsPanel.add(enhanceRevertButton);
        enhanceButtonsPanel.add(enhanceApplyButton);
        enhanceButtonsPanel.add(enhanceSaveButton);

        JPanel enhancePanel = new JPanel(new BorderLayout());
        enhancePanel.add(enhanceButtonsPanel, BorderLayout.NORTH);
        enhancePanel.add(enhanceConfigPanel, BorderLayout.CENTER);
        enhancePanel.setBorder(new TitledBorder("Enhancement"));

        panel.add(keyingPanel);
        panel.add(new JLabel(" ".repeat(30)));
        panel.add(enhancePanel);

        add(panel);

        FrameActionListener listener = new FrameActionListener(this);
        keyingLoadInputButton.addActionListener(listener);
        keyingApplyButton.addActionListener(listener);
        keyingColorSelectButton.addActionListener(listener);
        keyingDistanceField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) {
                float f;
                try {
                    f = Float.parseFloat(keyingDistanceField.getText());
                    if (f < 0) keyingDistanceField.setBackground(Color.RED);
                    else keyingDistanceField.setBackground(Color.WHITE);
                } catch (NumberFormatException nfe) {
                    keyingDistanceField.setBackground(Color.RED);
                }
            }
        });
        enhanceRevertButton.addActionListener(listener);
        enhanceApplyButton.addActionListener(listener);
        enhanceSaveButton.addActionListener(listener);
        enhanceSelectImageButton.addActionListener(listener);

        setVisible(true);
    }

}