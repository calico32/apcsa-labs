package lab01_temp_converter;

import java.util.NoSuchElementException;
import java.util.Scanner;
import shared.TextBuilder;
import shared.TextHelpers;

public class TempConverter extends TextHelpers {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    TextBuilder.println(text("Temperature converter").randomRainbow().bold());

    TextBuilder.println(
      text("Type a conversion expression, such as "),
      text("30f to c").bold(),
      text(" or "),
      text("320 kelvin to fahrenheit").bold(),
      text(".")
    );

    TextBuilder.println(
      text("Type "),
      text("q").bold(),
      text(" or "),
      text("quit").bold(),
      text(" to exit.\n")
    );

    while (true) {
      TextBuilder.print(text("> ").blue().bold());

      String input;
      try {
        input = scanner.nextLine().trim();
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
          TextBuilder.println(text("Invalid input: expected \"to\".").red());
          continue;
        }

        from = parts[0] + parts[1];
        to   = parts[3];
      } else if (parts.length == 3) {
        if (!parts[1].equals("to")) {
          TextBuilder.println(text("Invalid input: expected \"to\".").red());
          continue;
        }
        from = parts[0];
        to   = parts[2];
      } else {
        new TextBuilder(
          text("Invalid input: expected "),
          text("<num> <unit> to <unit>").bold(),
          text(" or "),
          text("<num><unit> to <unit>").bold(),
          text(".")
        )
          .red()
          .println();

        continue;
      }

      try {
        String fromValueString = from.replaceAll("[^0-9.]", "");
        String fromUnitString  = from.replaceAll("[^a-zA-Z]", "");

        double fromValue  = Double.parseDouble(fromValueString);
        TempUnit fromUnit = TempUnit.fromString(fromUnitString);

        TempUnit toUnit = TempUnit.fromString(to);

        double toValue = fromUnit.convert(fromValue, toUnit);

        TextBuilder.println(
          text("%.2f", fromValue).blue().bold(),
          text(" "),
          text(fromUnit),
          text(" = ").cyan(),
          text("%.2f", toValue).green().bold(),
          text(" "),
          text(toUnit),
          text("\n")
        );

      } catch (NumberFormatException e) {
        TextBuilder.println(text("Invalid number.").red());
        continue;
      } catch (IllegalArgumentException e) {
        TextBuilder.println(text(e.getMessage()).red());
        continue;
      }
    }

    scanner.close();
  }
}
