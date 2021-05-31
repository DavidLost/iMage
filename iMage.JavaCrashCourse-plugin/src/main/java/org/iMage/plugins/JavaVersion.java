package org.iMage.plugins;

/**
 * Enumeration of the LTS (long term support) Java versions including Java 16.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public enum JavaVersion {

  /**
   * Java 8.<br>
   * The main addition was the support of lambda expressions and functional programming styles.
   */
  JAVA_8(8),

  /**
   * Java 11.<br>
   * Small additions like the var keyword, a new http client {@link java.net.http.HttpClient} and
   * the JShell tool.
   */
  JAVA_11(11),

  /**
   * Java 16.<br>
   * New features like text blocks, record classes and pattern matching for {@code instanceof}
   * expressions.
   */
  JAVA_16(16);

  private final int version;

  /**
   * Create a new java version constant.
   *
   * @param version readable version number
   */
  JavaVersion(int version) {
    this.version = version;
  }

  /**
   * Get the version number.
   *
   * @return version number
   */
  public int getVersion() {
    return version;
  }

  /**
   * Parse the Java version from an input string.<br>
   * If the input is not a valid version number (e.g, {@code null}, not a number, not a version),
   * {@link JavaVersion#JAVA_16} will be returned.
   *
   * @param input input string, should represent the version number, can be {@code null}
   * @return parsed java version or {@code JAVA_16} if the input was not valid
   */
  public static JavaVersion parseFromInput(String input) {
    if (input == null) {
      return JavaVersion.JAVA_16;
    }

    int inputVersion;
    try {
      inputVersion = Integer.parseInt(input);
    } catch (NumberFormatException e) {
      return JavaVersion.JAVA_16;
    }

    for (JavaVersion version : JavaVersion.values()) {
      if (version.getVersion() == inputVersion) {
        return version;
      }
    }

    return JavaVersion.JAVA_16;
  }
}