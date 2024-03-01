package nz.ac.auckland.se281;

public class Greetings {

  public void sayHello() {
     MessageCli.HELLO.printMessage();
  }

  public void sayGoodbye() {
    MessageCli.GOODBYE.printMessage();
  }

  public void greetPerson(String name) {
    String message = MessageCli.HELLO_PERSON.getMessage(name);
    System.out.println(message);
  }
}
