package lab01_temp_converter;

import java.util.NoSuchElementException;
import java.util.Scanner;
import shared.TextBuilder;

public class TempConverter {
  static TextBuilder text(String text) { return new TextBuilder(text); }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    text("Temperature converter").whiteBold().print();

    System.out.println(
        "Type a conversion expression, such as \"30f to c\" or \"320 kelvin to fahrenheit\".");
    System.out.println("Type \"q\" or \"quit\" to exit.\n");
    while (true) {
      System.out.print(text("> ").blueBold());

      String input;
      try {
        input = scanner.nextLine();
      } catch (NoSuchElementException e) {
        // EOF
        break;
      }

      if (input.equals("q") || input.equals("quit")) {
        break;
      }

      if (input.isEmpty()) {
        continue;
      }

      String[] parts = input.split("\\s+");

      String from;
      String to;

      if (parts.length == 4) {
        if (!parts[2].equals("to")) {
          text("Invalid input: expected \"to\".").red().print();
          continue;
        }

        from = parts[0] + parts[1];
        to = parts[3];
      } else if (parts.length == 3) {
        if (!parts[1].equals("to")) {
          text("Invalid input: expected \"to\".").red().print();
          continue;
        }
        from = parts[0];
        to = parts[2];
      } else {
        text(
            "Invalid input: expected \"<num> <unit> to <unit>\" or \"<num><unit> to <unit>\".")
            .red()
            .print();
        continue;
      }

      try {
        double fromValue =
            Double.parseDouble(from.substring(0, from.length() - 1));

        TempUnit fromUnit =
            TempUnit.fromString(from.substring(from.length() - 1));
        TempUnit toUnit = TempUnit.fromString(to);

        double toValue = fromUnit.convert(fromValue, toUnit);

        System.out.printf("%.2f%s = %.2f%s\n", fromValue, fromUnit, toValue,
                          toUnit);
      } catch (NumberFormatException e) {
        text("Invalid number.").red().print();
        continue;
      } catch (IllegalArgumentException e) {
        text(e.getMessage()).red().print();
        continue;
      }
    }

    scanner.close();
  }
}
