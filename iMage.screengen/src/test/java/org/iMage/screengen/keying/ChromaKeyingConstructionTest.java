package org.iMage.screengen.keying;

import org.iMage.screengen.ChromaKeying;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class tests the construction of a a new {@link ChromaKeying}, especially the
 * parsing of a hex number to a color.
 *
 * @author Paul Hoger
 * @version 1.0
 * @see ChromaKeying#ChromaKeying(String, double)
 */
public class ChromaKeyingConstructionTest {

  @Test
  void testEmptyInputs() {
    assertIllegalArgumentException("");
    assertIllegalArgumentException(" ");
  }

  @Test
  void testMissingNumberSigns() {
    assertIllegalArgumentException("123456");
    assertIllegalArgumentException("000000");
    assertIllegalArgumentException("ABCDEF");
  }

  @Test
  void testValidNumbers() {
    assertColor(new Color(64, 64, 64), "#404040");
    assertColor(new Color(255, 255, 255), "#FFFFFF");
    assertColor(new Color(0, 255, 0), "#00FF00");
    assertColor(new Color(64, 224, 208), "#40E0D0");
    assertColor(new Color(18, 52, 86), "#123456");
  }

  @Test
  void testInvalidNumbers() {
    assertIllegalArgumentException("#XXXXXX");
    assertIllegalArgumentException("#12 34 56");
    assertIllegalArgumentException("#-123456");
    assertIllegalArgumentException("#12AB34CD");
    assertIllegalArgumentException("#CDEFGH");
  }

  @Test
  void testNegativeDistances() {
    assertThrows(IllegalArgumentException.class, () -> {
      new ChromaKeying(new Color(0, 255, 0, 255), -1);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new ChromaKeying(new Color(0, 255, 0, 255), Double.NEGATIVE_INFINITY);
    });
  }

  @Test
  void testValidDistances() {
    assertDoesNotThrow(() -> {
      new ChromaKeying(new Color(0, 255, 0, 255), 0);
    });
    assertDoesNotThrow(() -> {
      new ChromaKeying(new Color(0, 255, 0, 255), 255);
    });
  }

  /**
   * Assert that the parsed key representation refers to the expected color.
   *
   * @param expected          expected color
   * @param keyRepresentation key representation that will be parsed
   */
  private void assertColor(Color expected, String keyRepresentation) {
    ChromaKeying keying = new ChromaKeying(keyRepresentation, 0.0D);
    assertEquals(expected, keying.getKey());
  }

  /**
   * Assert that the construction of a new chroma keying process with an invalid key causes
   * an {@link IllegalArgumentException}.
   *
   * @param keyRepresentation key representation that will be parsed
   */
  private void assertIllegalArgumentException(String keyRepresentation) {
    assertThrows(IllegalArgumentException.class, () -> {
      new ChromaKeying(keyRepresentation, 0.0D);
    }, "Expected an invalid input, but got %s as valid input.".formatted(keyRepresentation));
  }
}