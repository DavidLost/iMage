package org.iMage.plugins;

import java.util.List;
import java.util.Objects;

/**
 * Abstract base class for every JMJRST plugin.
 *
 * @author Dominik Fuchss
 * @author Paul Hoger
 * @version 1.0
 */
public abstract class JmjrstPlugin implements Comparable<JmjrstPlugin> {

  /**
   * Get the name of the plugin.
   *
   * @return plugin name
   */
  public abstract String getName();

  /**
   * Get a list of authors who developed the plugin.<br>
   * If the source is unknown, an empty list should be returned.
   *
   * @return (possible empty) list of authors
   */
  public abstract List<String> getAuthors();

  /**
   * Initialize the plugin.<br>
   * JMJRST pushes the main application to every subclass - so plugins are allowed to look at Main
   * as well.
   *
   * @param main JMJRST main application
   */
  public abstract void init(org.jis.Main main);

  /**
   * Run the plugin.
   */
  public abstract void run();

  /**
   * Check whether the plugin can be configured by the user or not.<br>
   * If it is configurable, {@link #getConfigurationDescription()} and {@link #configure(String)}
   * have to be overwritten.
   *
   * @return true if the plugin is configurable, otherwise false
   */
  public abstract boolean isConfigurable();

  /**
   * Get the configuration description that will be shown to the user.<br>
   * It has to inform the user about the config value they submit.
   *
   * @return informative configuration description
   */
  public String getConfigurationDescription() {
    return "Default config description";
  }

  /**
   * Configure the plugin with a given user input.
   *
   * @param input user input, may be empty
   */
  public void configure(String input) {
    // Configurable plugins have to overwrite this method.
  }

  /**
   * Compare two plugins by their names lexicographically.
   *
   * @param otherPlugin plugin to compare with
   * @return a suitable number according to {@link Comparable}
   * @see Comparable#compareTo(Object)
   */
  @Override
  public int compareTo(JmjrstPlugin otherPlugin) {
    return this.getName().compareTo(otherPlugin.getName());
  }

  /**
   * Hash the plugin based on the name and the authors.
   *
   * @return hash code
   * @see #getName()
   * @see #getAuthors()
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.getName(), this.getAuthors());
  }

  /**
   * Check if two plugins are equal.<br>
   * They are equal if the name and the list of authors are equal.
   *
   * @param object object to check
   * @return true if the plugins are equal, otherwise false
   */
  @Override
  public boolean equals(Object object) {
    if (!(object instanceof JmjrstPlugin plugin)) {
      return false;
    }
    return this.getName().equals(plugin.getName()) && this.getAuthors().equals(plugin.getAuthors());
  }
}