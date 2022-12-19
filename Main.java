import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import shared.TextBuilder;
import shared.TextHelpers;

import lab01_temp_converter.TempConverter;
import lab02_number_cubes.LibreYachts;
import lab03_substrings.SubstringGame;
import lab04_leap_years.LeapYears;
import lab05_strings.StringSorter;
import lab06_loops.HighLowGame;
import lab07_vowel_count.VowelCounter;
import lab08_random_walk.RandomWalk;
import lab09_integer_sequence.IntegerSequence;

class Main extends TextHelpers {
  static class Program {
    public final String name;
    public final Class<?> cls;

    public Program(String name, Class<?> cls) {
      this.name = name;
      this.cls  = cls;
    }
  }

  static final ArrayList<Program> programs = new ArrayList<>();

  static void program(String name, Class<?> cls) { programs.add(new Program(name, cls)); }

  // menu
  static {
    program("Temperature converter", TempConverter.class);
    program("LibreYachts (open Yahtzee™ inspired game)", LibreYachts.class);
    program("Substring guessing game", SubstringGame.class);
    program("Leap year game", LeapYears.class);
    program("String sorter", StringSorter.class);
    program("High low game", HighLowGame.class);
    program("Character/vowel counter", VowelCounter.class);
    program("Random walk simulator", RandomWalk.class);
    program("Integer sequence", IntegerSequence.class);
    program("Exit", Exit.class);
  }

  public static void main(String[] args) throws NoSuchMethodException {
    var scanner = new Scanner(System.in);

    TextBuilder.println(text("Select a program to run:").white().bold());

    var choices = new ArrayList<String>();
    for (var i = 0; i < programs.size(); i++) {
      var program = programs.get(i);
      choices.add(program.name);
      TextBuilder.println(text((i + 1) + ". " + program.name));
    }

    System.out.println();
    System.out.print("> ");

    try {
      var choice       = scanner.nextInt();
      var programClass = programs.get(choice - 1).cls;
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
