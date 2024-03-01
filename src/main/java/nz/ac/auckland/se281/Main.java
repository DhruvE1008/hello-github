package nz.ac.auckland.se281;

import java.util.Scanner;

/** You cannot modify this class! */
public class Main {

  public enum Command {
    SAY_HELLO(0, "Print a greeting"), //
    SAY_GOODBYE(0, "Print a farewell"), //
    GREET_PERSON(1, "Print a greeting to a person <NAME>"), //

    HELP(0, "Print usage"),
    EXIT(0, "Exit the application");

    private final int numArgs;
    private final String message;
    private final String[] optionPrompts;

    private Command(final int numArgs, final String message) {
      this(numArgs, message, new String[] {});
    }

    private Command(final int numArgs, final String message, final String... optionPrompts) {
      this.numArgs = numArgs;
      this.message = message;
      this.optionPrompts = optionPrompts;
    }

    public boolean hasArguments() {
      return numArgs > 0;
    }

    public int getNumArgs() {
      return numArgs;
    }

    public boolean hasOptions() {
      return optionPrompts.length > 0;
    }

    public int getNumOptions() {
      return optionPrompts.length;
    }

    public String getOptionPrompt(final int index) {
      return optionPrompts[index];
    }
  }

  private static final String COMMAND_PREFIX = "hello github> ";

  public static void main(final String[] args) {
    new Main(new Scanner(System.in), new Greetings()).start(false);
  }

  public static String help() {
    final StringBuilder sb = new StringBuilder();

    for (final Command command : Command.values()) {
      sb.append(command).append("\t");

      // Add extra padding to align the argument counts
      // if the command name is less than the tab width.
      if (command.toString().length() < 8) {
        sb.append("\t");
      }

      if (command.numArgs > 0) {
        sb.append("[").append(command.numArgs).append(" arguments]");
      } else {
        sb.append("[no args]");
      }

      sb.append("\t").append(command.message).append(System.lineSeparator());
    }

    return sb.toString();
  }

  public static void printBanner() {
    // https://patorjk.com/software/taag/
    // https://www.freeformatter.com/java-dotnet-escape.html#before-output

    StringBuilder buf = new StringBuilder();
    buf.append(
        "   _____  ____  ______ _______ ______ _   _  _____   ___   ___  __ "
            + "\r\n  / ____|/ __ \\|  ____|__   __|  ____| \\ | |/ ____| |__ \\ / _ \\/_ |"
            + "\r\n | (___ | |  | | |__     | |  | |__  |  \\| | |  __     ) | (_) || |"
            + "\r\n  \\___ \\| |  | |  __|    | |  |  __| | . ` | | |_ |   / / > _ < | |"
            + "\r\n  ____) | |__| | |       | |  | |____| |\\  | |__| |  / /_| (_) || |"
            + "\r\n |_____/ \\____/|_|       |_|  |______|_| \\_|\\_____| |____|\\___/ |_|"
            + "\r\n");
    System.out.println(buf.toString());
  }

  private final Scanner scanner;

  private final Greetings greetings;

  public Main(final Scanner scanner, final Greetings greetings) {
    this.scanner = scanner;
    this.greetings = greetings;
  }

  public void start() {
    start(false);
  }

  public void start(boolean debug) {
    printBanner();
    System.out.println(help());

    String command;

    // Prompt and process commands until the exit command.
    do {
      System.out.print(COMMAND_PREFIX);
      command = scanner.nextLine().trim();
      if (debug) {
        System.out.println(command);
      }
    } while (processCommand(command));
  }

  private boolean processCommand(String input) {
    // Remove whitespace at the beginning and end of the input.
    input = input.trim();

    final String[] args = input.split(" ");

    // Allow any case, and dashes to be used instead of underscores.
    final String commandStr = args[0].toUpperCase().replaceAll("-", "_");

    final Command command;

    try {
      // Command names correspond to the enum names.
      command = Command.valueOf(commandStr);
    } catch (final Exception e) {
      MessageCli.COMMAND_NOT_FOUND.printMessage(commandStr);
      return true;
    }

    if (!checkArgs(command, args)) {
      MessageCli.WRONG_ARGUMENT_COUNT.printMessage(
          String.valueOf(command.getNumArgs()), command.getNumArgs() > 1 ? "s" : "", commandStr);
      return true;
    }

    switch (command) {
      case SAY_HELLO:
        greetings.sayHello();
        break;
      case SAY_GOODBYE:
        greetings.sayGoodbye();
        break;
      case GREET_PERSON:
        greetings.greetPerson(args[1]);
        break;
      case EXIT:
        MessageCli.END.printMessage();
        // Signal that the program should exit.
        return false;
      case HELP:
        System.out.println(help());
        break;
    }

    // Signal that another command is expected.
    return true;
  }

  private boolean checkArgs(final Command command, final String[] args) {
    return command.getNumArgs() == args.length - 1;
  }
}
