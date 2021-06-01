package org.iMage.plugins;

import org.iMage.plugins.teaching.Animal;
import org.iMage.plugins.teaching.Cat;
import org.iMage.plugins.teaching.Dog;
import org.jis.Main;
import org.kohsuke.MetaInfServices;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
@MetaInfServices()
public class JavaCrashCourse extends JmjrstPlugin {

    private boolean initialized = false;
    private JavaVersion currentVersion = JavaVersion.JAVA_16;
    private static final int FEATURES_PER_VERSION = 3;
    private final List<Feature> features = new ArrayList<>();
    private final Random rnd = new Random(ThreadLocalRandom.current().nextInt());

    /**
     * Get the name of the plugin.
     *
     * @return plugin name
     */
    @Override
    public String getName() {
        return "JavaCrashCourse-plugin";
    }

    /**
     * Get a list of authors who developed the plugin.<br>
     * If the source is unknown, an empty list should be returned.
     *
     * @return (possible empty) list of authors
     */
    @Override
    public List<String> getAuthors() {
        List<String> authors = new ArrayList<>();
        authors.add("David Rösler");
        return authors;
    }

    /**
     * Initialize the plugin.<br>
     * JMJRST pushes the main application to every subclass - so plugins are allowed to look at Main
     * as well.
     *
     * @param main JMJRST main application
     */
    @Override
    public void init(Main main) {

        if (initialized) {
            return;
        }

        Animal animal = switch (rnd.nextInt(3)) {
            case 0 -> new Animal("Fish", rnd.nextInt(42));
            case 1 -> new Dog(rnd.nextInt(16));
            case 2 -> new Cat(rnd.nextInt(20));
            default -> throw new IllegalStateException("Unexpected value");
        };
        if (animal instanceof Dog dog) {
            dog.bark();
        } else if (animal instanceof Cat cat) {
            cat.meow();
        } else {
            animal.sleep();
        }

        final String JAVA_8_CODE_EXAMPLE_1 = """
Thread t = new Thread(() -> {
    this.calculateSomething();
    notifyAll();
});
        """;

        final String JAVA_8_CODE_EXAMPLE_2 = """
interface LambdaFunction {
    void call();
}
   
class FirstLambda {
   public static void main(String []args) {
       LambdaFunction lambda = () -> System.out.println("Hello KIT");
       lambda.call();
   }
}
        """;

        final String JAVA_8_CODE_EXAMPLE_3 = """
Random rnd = new Random(ThreadLocalRandom.current().nextInt());
Animal animal = switch (rnd.nextInt(3)) {
    case 0 -> new Animal("Fish", rnd.nextInt(42));
    case 1 -> new Dog(rnd.nextInt(16));
    case 2 -> new Cat(rnd.nextInt(16));
    default -> throw new IllegalStateException("Unexpected value");
};
        """;

        final String JAVA_11_CODE_EXAMPLE_1 = """
var list = List.of("SWT1", "Proggen", "Algo1", "LA2");
list.forEach(s -> System.out.println(s));
        """;

        final String JAVA_11_CODE_EXAMPLE_2 = """
var i = 1;
int j = 2
i++;
int x = i + j;
int y = 2 * x - i;
        """;

        final String JAVA_11_CODE_EXAMPLE_3 = """
HttpClient httpClient = HttpClient.newBuilder()
  .version(HttpClient.Version.HTTP_2)
  .followRedirects(HttpClient.Redirect.NORMAL)
  .connectTimeout(Duration.ofSeconds(10))
  .proxy(ProxySelector.of(new InetSocketAddress("code-support.de", 80)))
  .authenticator(Authenticator.getDefault())
  .build();
        """;

        final String JAVA_16_CODE_EXAMPLE_1 = """
public record Feature(String name, JavaVersion version, String example) {
                   
    public String getName() {
        return name;
    }

    public JavaVersion getVersion() {
        return version;
    }

    public String getExample() {
        return example;
    }
}
        """;

        final String JAVA_16_CODE_EXAMPLE_2 = """
String json = ""\"
        {
            "name": "David",
            "skills": ["Java", "Kotlin", "C++", "Python"]
        },
        {
            "name": "Rüdiger",
            "skills": ["fliegen", "schreien", "nerven", "Kapriolen"]
        }
""\";
        """;

        final String JAVA_16_CODE_EXAMPLE_3 = """
if (samurai instanceof Warrior warrior) {
    System.out.println(warrior.getAttackDamage());
    System.out.println(warrior.getAttackSpeed());
}
        """;

        features.add(new Feature("lambda_thread", JavaVersion.JAVA_8, JAVA_8_CODE_EXAMPLE_1));
        features.add(new Feature("lambda_function", JavaVersion.JAVA_8, JAVA_8_CODE_EXAMPLE_2));
        features.add(new Feature("lambda_switch", JavaVersion.JAVA_8, JAVA_8_CODE_EXAMPLE_3));
        features.add(new Feature("var_list", JavaVersion.JAVA_11, JAVA_11_CODE_EXAMPLE_1));
        features.add(new Feature("var_and_int", JavaVersion.JAVA_11, JAVA_11_CODE_EXAMPLE_2));
        features.add(new Feature("http_client", JavaVersion.JAVA_11, JAVA_11_CODE_EXAMPLE_3));
        features.add(new Feature("record_class", JavaVersion.JAVA_16, JAVA_16_CODE_EXAMPLE_1));
        features.add(new Feature("text_block", JavaVersion.JAVA_16, JAVA_16_CODE_EXAMPLE_2));
        features.add(new Feature("instanceof_pattern_matching", JavaVersion.JAVA_16, JAVA_16_CODE_EXAMPLE_3));
        initialized = true;
    }

    /**
     * Run the plugin.
     */
    @Override
    public void run() {
        rnd.ints(0, FEATURES_PER_VERSION).distinct().limit((FEATURES_PER_VERSION + 1) / 2)
                .mapToObj(i -> features.stream().filter(f -> f.version().equals(currentVersion)).toList()
                .get(i)).sorted(Comparator.comparing(Feature::name)).forEach(Feature::print);
    }

    @Override
    public String getConfigurationDescription() {
        return "JavaCrashCourse is a test-plugin, showing you code-examples for new features in various java-versions."
                + "Valid java-versions to configure are: 8, 11 and 16.";
    }

    @Override
    public void configure(String input) {
        currentVersion = JavaVersion.parseFromInput(input);
    }

    /**
     * Check whether the plugin can be configured by the user or not.<br>
     * If it is configurable, {@link #getConfigurationDescription()} and {@link #configure(String)}
     * have to be overwritten.
     *
     * @return true if the plugin is configurable, otherwise false
     */
    @Override
    public boolean isConfigurable() {
        return true;
    }
}