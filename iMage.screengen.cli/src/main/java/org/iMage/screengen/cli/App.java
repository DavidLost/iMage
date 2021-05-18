package org.iMage.screengen.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.iMage.screengen.BackgroundEnhancement;
import org.iMage.screengen.ChromaKeying;
import org.iMage.screengen.DefaultScreenGenerator;
import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.positions.CenterPosition;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The command line interface implementation to
 * interact with the {@link org.iMage.screengen.base.ScreenGenerator}.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public final class App {

  /**
   * Prevent initialization.
   */
  private App() {
    throw new IllegalAccessError();
  }

  private static final String CMD_OPTION_INPUT_IMAGE = "i";
  private static final String CMD_OPTION_OUTPUT_IMAGE = "o";
  private static final String CMD_OPTION_COLOR = "c";

  private static final String CMD_OPTION_BACKGROUND = "b";
  private static final String CMD_OPTION_POSITION = "p";
  private static final String CMD_OPTION_DISTANCE = "d";

  /**
   * Parse the command line and run the cli.
   *
   * @param args the args are specified in {@code App#doCommandLineParsing(String[])}
   * @see App#doCommandLineParsing(String[])
   */
  public static void main(String[] args) {
    // Parse arguments
    CommandLine cmd = null;
    try {
      cmd = App.doCommandLineParsing(args);
    } catch (ParseException e) {
      exit("Wrong command line arguments given", e);
    }

    assert cmd != null;
    //input argument
    String inputPath = cmd.getOptionValue("i");
    //color argument
    String colorString = cmd.getOptionValue("c");
    //output argument
    String outputPath = cmd.getOptionValue("o");
    //distance argument
    double distance = 0;
    if (cmd.getOptionValue("d") != null) {
      try {
        distance = Double.parseDouble(cmd.getOptionValue("d"));
      } catch (NumberFormatException nfe) {
        exit("invalid format for distance, type is double!", nfe);
      }
    }
    //background argument
    String backgroundPath = cmd.getOptionValue("b");
    //position argument
    String positionString = cmd.getOptionValue("p");

    ScreenImage input = null;
    try {
      input = new BufferedScreenImage(ImageIO.read(new File(inputPath)));
    } catch (IOException ioe) {
      exit("input file couldn't be read!", ioe);
    } catch (NullPointerException npe) {
      exit("the input file doesn't exist!", npe);
    }
    ChromaKeying chromaKeying = new ChromaKeying(colorString, distance);

    ScreenImage background = null;
    try {
      background = new BufferedScreenImage(ImageIO.read(new File(backgroundPath)));
    } catch (IOException ioe) {
      exit("background file couldn't be read!", ioe);
    } catch (NullPointerException ignored) { }
    Position position = new CenterPosition();
    for (Position pos : DefaultScreenGenerator.POSITIONS) {
      if (pos.getDescription().equals(positionString)) {
        position = pos;
      }
    }

    BackgroundEnhancement backgroundEnhancement = new BackgroundEnhancement(background, position);
    DefaultScreenGenerator defaultScreenGenerator = new DefaultScreenGenerator();

    ScreenImage output = background == null ? chromaKeying.process(input)
            : defaultScreenGenerator.generate(input, chromaKeying, backgroundEnhancement);

    try {
      output.save(outputPath);
    } catch (IOException ioe) {
      exit("generated image couldn't be saved at specified location!", ioe);
    }
  }

  /**
   * Exit the programm and print the reason to the user.
   *
   * @param description error description
   * @param exception   threw exception
   */
  private static void exit(String description, Throwable exception) {
    System.err.println(description + ": " + exception.getMessage());
    System.exit(1);
  }

  /**
   * Parse and check command line arguments.
   *
   * @param args command line arguments given by the user
   * @return CommandLine object encapsulating all options
   * @throws ParseException if wrong command line parameters or arguments are given
   */
  private static CommandLine doCommandLineParsing(String[] args) throws ParseException {
    Options options = new Options();
    Option opt;

    /*
     * Define command line options and arguments
     */
    opt = new Option(App.CMD_OPTION_INPUT_IMAGE, "input", true, "path to input image");
    opt.setRequired(true);
    opt.setType(String.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_OUTPUT_IMAGE, "output", true, "path to output image");
    opt.setRequired(true);
    opt.setType(String.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_BACKGROUND, "background", true, "path to background image");
    opt.setRequired(false);
    opt.setType(String.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_POSITION, "position", true, "the position of the input image");
    opt.setRequired(false);
    opt.setType(Integer.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_COLOR, "color", true, "the rgb color key in hex format");
    opt.setRequired(true);
    opt.setType(String.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_DISTANCE, "distance", true,
        "the maximal distance to the key color");
    opt.setRequired(false);
    opt.setType(Double.class);
    options.addOption(opt);

    CommandLineParser parser = new DefaultParser();
    return parser.parse(options, args);
  }
}