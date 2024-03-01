package nz.ac.auckland.se281;

import static nz.ac.auckland.se281.Main.Command.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  MainTest.Task1.class,
  // MainTest.YourTests.class, // Uncomment this line to run your own tests
})
public class MainTest {

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task1 extends CliTest {

    public Task1() {
      super(Main.class);
    }

    @Test
    public void T1_01_say_hello() throws Exception {
      runCommands(SAY_HELLO);

      assertContains("Hello! Hello!! Hello!!!");
    }

    @Test
    public void T1_02_say_goodbye() throws Exception {
      runCommands(SAY_GOODBYE);

      assertContains("Bye! Bye!! Bye!!!");
    }

    @Test
    public void T1_03_say_hello_only() throws Exception {
      runCommands(SAY_HELLO);

      assertContains("Hello! Hello!! Hello!!!");
      assertDoesNotContain("Bye! Bye!! Bye!!!", true);
    }

    @Test
    public void T1_04_say_goodbye_only() throws Exception {
      runCommands(SAY_GOODBYE);

      assertContains("Bye! Bye!! Bye!!!");
      assertDoesNotContain("Hello! Hello!! Hello!!!", true);
    }

    @Test
    public void T1_05_greet_person() throws Exception {
      runCommands(GREET_PERSON, "Alex");

      assertContains("Hello Alex, how are you?");
      assertDoesNotContain("Hello! Hello!! Hello!!!", true);
      assertDoesNotContain("Bye! Bye!! Bye!!!", true);
    }
  }

  public static class YourTests extends CliTest {
    public YourTests() {
      super(Main.class);
    }
  }
}
