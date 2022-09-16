import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import lab01_temp_converter.TempConverter;
import shared.TextBuilder;

class Main {
  static TextBuilder text(String text) { return new TextBuilder(text); }

  static final HashMap<String, Class<?>> classes = new HashMap<>();

  // menu
  static {
    classes.put("Temperature converter", TempConverter.class);
    classes.put("Exit", Exit.class);
  }

  public static void main(String[] args) throws NoSuchMethodException {
    Scanner scanner = new Scanner(System.in);

    text("Select a program to run:").whiteBold().print();

    int choiceNumber = 0;
    List<String> choices = new ArrayList<>();
    for (String name : classes.keySet()) {
      choices.add(name);
      text(String.valueOf(choiceNumber++ + 1))
          .white()
          .text(". " + name)
          .print();
    }

    System.out.println();
    System.out.print("> ");

    try {
      int choice = scanner.nextInt();
      Class<?> programClass = classes.get(choices.get(choice - 1));
      System.out.print("\n\n");
      programClass.getMethod("main", String[].class).invoke(null, (Object)args);
    } catch (IndexOutOfBoundsException e) {
      text("\nInvalid choice.").red().print();
    } catch (NoSuchElementException e) {
      // EOF
    } catch (NoSuchMethodException e) {
      text("\nError: Program class does not have a main method.").red().print();
      scanner.close();
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
    }

    scanner.close();
  }
}

class Exit {
  public static void main(String[] args) { System.exit(0); }
}
