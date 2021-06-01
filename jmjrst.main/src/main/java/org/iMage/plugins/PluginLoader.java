package org.iMage.plugins;

import org.jis.Main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * The plugin loader loads all available plugins using the {@link ServiceLoader}.
 *
 * @author Dominik Fuchss
 * @author Paul Hoger
 * @version 1.0
 */
public final class PluginLoader {

  private static Iterable<JmjrstPlugin> plugins = null;

  /**
   * No constructor for utility class.
   */
  private PluginLoader() {
    throw new IllegalAccessError();
  }

  /**
   * Return an {@link Iterable} object with all available {@link JmjrstPlugin JmjrstPlugins}.
   * sorted alphabetically according to their name.
   * In case of equally named plugins sort them by the number of authors they have (less first).
   * <br>
   * The plugins should only be loaded once.
   *
   * @return an {@link Iterable} object containing all available plug-ins
   */

  public static Iterable<JmjrstPlugin> getPlugins() {
    if (plugins == null) {
      Iterator<JmjrstPlugin> pluginIterator = ServiceLoader.load(JmjrstPlugin.class).iterator();
      List<JmjrstPlugin> pluginList = new ArrayList<>();
      while (pluginIterator.hasNext()) {
        pluginList.add(pluginIterator.next());
      }
      /*ServiceLoader<JmjrstPlugin> serviceLoader = ServiceLoader.load(JmjrstPlugin.class);
      List<JmjrstPlugin> pluginList = new ArrayList<>();
      serviceLoader.forEach(pluginList::add);*/
      for (int i = 0; i < 5; i++) {
        int j = i;
        pluginList.add(new JmjrstPlugin() {
          String config = super.getConfigurationDescription();
          @Override
          public String getName() {
            return "test-plugin-" + j;
          }
          @Override
          public List<String> getAuthors() {
            return null;
          }
          @Override
          public void init(Main main) {
          }
          @Override
          public void run() {
            System.out.println("running " + getName());
          }
          @Override
          public void configure(String input) {
            config = input;
          }
          @Override
          public boolean isConfigurable() {
            return j % 2 == 0;
          }
          @Override
          public String getConfigurationDescription() {
            return config;
          }
        });
      }
      pluginList.sort(Comparator.comparing((JmjrstPlugin p) -> p).thenComparingInt(p -> p.getAuthors().size()));
      plugins = pluginList;
    }
    return plugins;
  }
}