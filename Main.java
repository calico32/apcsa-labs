import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;

import shared.TextBuilder;
import shared.TextHelpers;

import lab01_temp_converter.TempConverter;
import lab02_number_cubes.LibreYachts;
import lab03_substrings.SubstringGame;
import lab04_leap_years.LeapYears;
import lab05_strings.StringSorter;
import lab06_loops.HighLowGame;
import lab07_vowel_count.VowelCounter;

class Main extends TextHelpers {
  static final TreeMap<String, Class<?>> programs = new TreeMap<>();

  // menu
  static {
    programs.put("1. Temperature converter", TempConverter.class);
    programs.put("2. LibreYachts (open Yahtzee™ inspired game)", LibreYachts.class);
    programs.put("3. Substring guessing game", SubstringGame.class);
    programs.put("4. Leap year game", LeapYears.class);
    programs.put("5. String sorter", StringSorter.class);
    programs.put("6. High low game", HighLowGame.class);
    programs.put("7. Character/vowel counter", VowelCounter.class);
    programs.put("8. Exit", Exit.class);
  }

  public static void main(String[] args) throws NoSuchMethodException {
    Scanner scanner = new Scanner(System.in);

    TextBuilder.println(text("Select a program to run:").white().bold());

    List<String> choices = new ArrayList<>();
    for (String name : programs.keySet()) {
      choices.add(name);
      System.out.println(name);
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
