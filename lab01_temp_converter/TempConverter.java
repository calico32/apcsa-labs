package lab01_temp_converter;

import shared.TextBuilder;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static shared.TextHelpers.*;

public class TempConverter {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        println(text("Temperature converter").randomRainbow().bold());

        println(
            text("Type a conversion expression, such as "),
            text("30f to c").bold(),
            text(" or "),
            text("320 kelvin to fahrenheit").bold(),
            text(".")
        );

        println(
            text("Type "),
            text("q").bold(),
            text(" or "),
            text("quit").bold(),
            text(" to exit.")
        );

        while (true) {
            println();
            print(text("> ").blue().bold());

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

            var parts = input.split("\\s+");

            String from, to;

            if (parts.length == 4) {
                if (!parts[2].equalsIgnoreCase("to")) {
                    println(text("Invalid input: expected \"to\".").red());
                    continue;
                }

                from = parts[0] + parts[1];
                to = parts[3];
            } else if (parts.length == 3) {
                if (!parts[1].equalsIgnoreCase("to")) {
                    println(text("Invalid input: expected \"to\".").red());
                    continue;
                }
                from = parts[0];
                to = parts[2];
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
                var fromValueString = from.replaceAll("[^0-9.-]", "");
                var fromUnitString = from.replaceAll("[^a-zA-Z]", "");

                var fromValue = Double.parseDouble(fromValueString);
                var fromUnit = TempUnit.fromString(fromUnitString);
                var toUnit = TempUnit.fromString(to);
                var toValue = fromUnit.convert(fromValue, toUnit);

                println(
                    text("%.2f", fromValue).blue().bold(),
                    text(" "),
                    text(fromUnit),
                    text(" = ").cyan(),
                    text("%.2f", toValue).green().bold(),
                    text(" "),
                    text(toUnit)
                );

            } catch (NumberFormatException e) {
                println(text("Invalid number.").red());
                continue;
            } catch (IllegalArgumentException e) {
                println(text(e.getMessage()).red());
                continue;
            }
        }

        scanner.close();
    }
}
