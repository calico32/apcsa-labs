package lab09_integer_sequence;

import static shared.TextHelpers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;

import shared.Input;
import shared.TextSegment;

public class IntegerSequence {
  public static void main(String[] args) {
    println(text("Integer sequence analysis").bold().randomRainbow());

    println(
      text("Enter any sequence of integers, separated by spaces. Add "),
      text("info").bold(),
      text(" at any time to get intermediate results, or add "),
      text("end").bold(),
      text(" to end your sequence."),
      text("Add "),
      text("clear").bold(),
      text(" to clear the stored sequence.")
    );

    var numbers = new ArrayList<Integer>();

  lines:
    while (true) {
      var newNumbers = new ArrayList<Integer>();
      String input;
      try {
        input = Input.readString(text(numbers.size()).dim());
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
          case "status":
            var temp = new ArrayList<Integer>();
            temp.addAll(numbers);
            temp.addAll(newNumbers);
            printAnalysis(temp);
            continue;
          case "end": numbers.addAll(newNumbers); break lines;
          case "clear":
            numbers.clear();
            newNumbers.clear();
            continue;
        }

        try {
          var number = Integer.parseInt(part);
          newNumbers.add(number);
        } catch (NumberFormatException e) {
          println(text("Error parsing part #%d: Invalid integer", i + 1).red());
          println(text("No new numbers were added to the sequence.").dim());
          continue lines;
        }
      }

      numbers.addAll(newNumbers);
    }

    printAnalysis(numbers);
  }

  public static void printAnalysis(Collection<Integer> numbers) {
    var min  = Integer.MAX_VALUE;
    var max  = Integer.MIN_VALUE;
    var odd  = 0;
    var even = 0;
    var scan = new ArrayList<Integer>();

    var adjacent = new HashSet<Integer>();

    Integer lastNumber = null;
    for (var number : numbers) {
      min = Math.min(min, number);
      max = Math.max(max, number);
      if (number % 2 == 0) {
        even++;
      } else {
        odd++;
      }

      if (number == lastNumber) {
        adjacent.add(number);
      }

      if (scan.size() == 0) {
        scan.add(number);
      } else {
        scan.add(scan.get(scan.size() - 1) + number);
      }

      lastNumber = number;
    }

    if (scan.size() == 0) {
      println(text("Nothing to analyze.\n").red());
      return;
    }

    println(text("Sequence information").bold());
    println(text(scan.size()).dim(), text(" integers").dim());

    String duplicates = Arrays.toString(adjacent.toArray()).replaceAll("[\\[,\\]]", "");

    String[] labels = {
      "Minimum",
      "Maxmimum",
      "# of odd",
      "# of even",
      "Addition scan",
      "Adjacent duplicates",
    };
    TextSegment[] values = {
      text(min),
      text(max),
      text(odd),
      text(even),
      text(Arrays.toString(scan.toArray()).replaceAll("[\\[,\\]]", "")),
      text(duplicates.length() != 0 ? duplicates : "none"),
    };

    var labelWidth = Arrays.stream(labels).map(a -> a.length()).reduce(0, Math::max);

    for (var i = 0; i < labels.length; i++) {
      println(text(labels[i]).padRight(labelWidth).bold(), text("  "), text(values[i]));
    }

    println();
  }
}
