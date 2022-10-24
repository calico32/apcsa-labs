import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import shared.TextBuilder;
import shared.TextHelpers;

import lab01_temp_converter.TempConverter;
import lab02_number_cubes.LibreYachts;

class Main extends TextHelpers {
  static final HashMap<String, Class<?>> programs = new HashMap<>();

  // menu
  static {
    programs.put("Temperature converter", TempConverter.class);
    programs.put("LibreYachts (open Yahtzee™ inspired game)", LibreYachts.class);
    programs.put("Exit", Exit.class);
  }

  public static void main(String[] args) throws NoSuchMethodException {
    Scanner scanner = new Scanner(System.in);

    TextBuilder.println(text("Select a program to run:").white().bold());

    int choiceNumber     = 0;
    List<String> choices = new ArrayList<>();
    for (String name : programs.keySet()) {
      choices.add(name);

      TextBuilder.println(
        text(String.valueOf(choiceNumber++ + 1)).white(), text(". " + name)
      );
    }

    System.out.println();
    System.out.print("> ");

    try {
      int choice            = scanner.nextInt();
      Class<?> programClass = programs.get(choices.get(choice - 1));
      TextBuilder.print(text("\n---------------\n\n").dim());
      programClass.getMethod("main", String[].class).invoke(null, (Object)args);
    } catch (IndexOutOfBoundsException e) {
      TextBuilder.println(text("\nInvalid choice.").red());
    } catch (InputMismatchException e) {
      TextBuilder.println(text("\nInvalid choice.").red());
    } catch (NoSuchElementException e) {
      // EOF, do nothing
    } catch (NoSuchMethodException e) {
      TextBuilder.println(
        text("\nError: Program class does not have a main method.").red()
      );
      scanner.close();
      throw e;
    } catch (Exception e) {
      TextBuilder.println(text("\nError: " + e).red());
    }

    scanner.close();
  }
}

class Exit {
  public static void main(String[] args) { System.exit(0); }
}
