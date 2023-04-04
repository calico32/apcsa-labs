package app;

import lab01_temp_converter.TempConverter;
import lab02_number_cubes.LibreYachts;
import lab03_substrings.SubstringGame;
import lab04_leap_years.LeapYears;
import lab05_strings.StringSorter;
import lab06_loops.HighLowGame;
import lab07_vowel_count.VowelCounter;
import lab08_random_walk.RandomWalk;
import lab09_integer_sequence.IntegerSequence;
import lab10_classes.DatingGame;
import lab11_arrays.IntegerCounter;
import lab12_arraylists.ListensExplorer;
import lab13_2darray.MatrixThing;
import lab14_inheritance.School;
import lab15_recursion.Exponentiator;
import shared.Input;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static shared.TextHelpers.*;

class Main {
    record Program(String name, Class<?> cls) {
    }

    static final ArrayList<Program> programs = new ArrayList<>();

    static void program(String name, Class<?> cls) {
        programs.add(new Program(name, cls));
    }

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
        program("Karuta date game (WIP)", DatingGame.class);
        program("Integer counter", IntegerCounter.class);
        program("Listens analyzer", ListensExplorer.class);
        program("Matrix thing", MatrixThing.class);
        program("School", School.class);
        program("Exponentiator", Exponentiator.class);
        program("Exit", Exit.class);
    }


    static class Exit {
        public static void main(String[] args) {
            System.exit(0);
        }
    }


    public static void main(String[] args) throws NoSuchMethodException {
        var scanner = new Scanner(System.in);

        int choice;

        if (args.length > 0) {
            var input = args[0];
            choice = Integer.parseInt(input);
            if (choice <= 0 || choice >= programs.size()) {
                println(text("Invalid program number: " + input).red());
                return;
            }
        } else {
            println(text("Select a program to run:").white().bold());

            for (var i = 0; i < programs.size(); i++) {
                var program = programs.get(i);
                println(text((i + 1) + ". " + program.name));
            }

            choice = Input.readInt(1, programs.size());

            System.out.println();
            print(text("\n---------------\n\n").dim());
        }

        try {
            var programClass = programs.get(choice - 1).cls;
            programClass.getMethod("main", String[].class).invoke(null, (Object) args);
        } catch (InputMismatchException e) {
            println(text("\nInvalid choice.").red());
        } catch (NoSuchElementException e) {
            // EOF, do nothing
        } catch (NoSuchMethodException e) {
            println(text("\nError: Program class does not have a main method.").red());
            scanner.close();
            throw e;
        } catch (InvocationTargetException e) {
            println(text("\nProgram threw an exception:").red());
            e.getCause().printStackTrace();
        } catch (Exception e) {
            println(text("\nError: " + e).red());
        }

        scanner.close();
    }
}
