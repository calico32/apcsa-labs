package lab11_arrays;

import shared.Histogram;
import shared.Input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static shared.TextHelpers.println;
import static shared.TextHelpers.text;

public class IntegerCounter {
    public static void main(String[] args) {
        println(text("Number counter").bold().randomRainbow());

        println(
            text("Enter a some integers from 0 to 25, separated by spaces. Add "),
            text("info").bold(),
            text(" at any time to get intermediate results, or add "),
            text("end").bold(),
            text(" to end input."),
            text("Add "),
            text("clear").bold(),
            text(" to clear the stored values.")
        );

        var numbers = new int[0];

        lines:
        while (true) {
            var newNumbers = new ArrayList<Integer>();
            String input;
            try {
                input = Input.readString(text(numbers.length).dim());
            } catch (NoSuchElementException e) {
                // EOF
                println();
                break;
            }

            var parts = input.split("\\s+");
            for (var i = 0; i < parts.length; i++) {
                String part = parts[i];
                if (part.isBlank()) {
                    continue;
                }

                switch (part.trim()) {
                    case "info":
                    case "status": {
                        var temp = join(numbers, newNumbers);
                        printAnalysis(temp);
                        continue;
                    }
                    case "end": {
                        numbers = join(numbers, newNumbers);
                        break lines;
                    }
                    case "clear": {
                        numbers = new int[0];
                        newNumbers.clear();
                        continue;
                    }
                }

                try {
                    var number = Integer.parseInt(part);
                    if (number < 0 || number > 25) {
                        println(
                            text("Error parsing part #%d: Number must be between 0 and 25", i + 1).red()
                        );
                        println(text("No new numbers were added to the sequence.").dim());
                        continue lines;
                    }
                    newNumbers.add(number);
                } catch (NumberFormatException e) {
                    println(text("Error parsing part #%d: Invalid integer", i + 1).red());
                    println(text("No new numbers were added to the sequence.").dim());
                    continue lines;
                }
            }

            numbers = join(numbers, newNumbers);
        }

        printAnalysis(numbers);
    }

    public static void printAnalysis(int[] numbers) {
        HashMap<Integer, Integer> counts = new HashMap<>();

        for (var number : numbers) {
            counts.put(number, counts.getOrDefault(number, 0) + 1);
        }

        println(text("Number of values: %d", numbers.length).dim());
        println(text("Number of unique values: %d", counts.size()).dim());
        var histogram = new Histogram("Number of occurrences");

        for (var entry : counts.entrySet()) {
            histogram.addCategory(entry.getKey().toString(), entry.getValue());
        }

        println(histogram.draw());
    }

    public static int[] join(int[] arr, ArrayList<Integer> list) {
        var result = new int[arr.length + list.size()];
        System.arraycopy(arr, 0, result, 0, arr.length);
        for (var i = 0; i < list.size(); i++) {
            result[arr.length + i] = list.get(i);
        }
        return result;
    }
}
