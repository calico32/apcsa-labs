package lab07_vowel_count;

import static shared.TextHelpers.*;

import java.util.HashMap;

import shared.Histogram;
import shared.Histogram.ValueMode;
import shared.Histogram.ValuePlacement;
import shared.Input;
import shared.Text;

public class VowelCounter {
  public static void main(String[] args) {
    println(text("Character counter").bold().randomRainbow());

    Text text;

    println(text("Examples:").bold());
    for (int i = 0; i < Text.examples.length; i++) {
      println(text("%d. ", i + 1).bold().white(), text(Text.examples[i].name));
    }

    println(
      text(
        "Select an example text by entering its number, or begin typing your own text. Type "
      ),
      text("[endtext]").bold(),
      text(" on a new line to finish.")

    );

    String input = Input.readString();
    try {
      int index = Integer.parseInt(input) - 1;
      if (index < 0 || index >= Text.examples.length) {
        throw new NumberFormatException();
      }
      text = Text.examples[index];
    } catch (NumberFormatException e) {
      while (true) {
        String next = Input.readString();
        if (next.equals("[endtext]")) {
          break;
        }
        if (!next.isBlank()) {
          input += "\n" + next;
        }
      }

      text = new Text("Custom text", input.trim());
    }

    println();

    HashMap<Character, Integer> counts = new HashMap<>();

    for (char c : text.content.toCharArray()) {
      if (Character.isLetter(c)) {
        c = Character.toLowerCase(c);
        if (counts.containsKey(c)) {
          counts.put(c, counts.get(c) + 1);
        } else {
          counts.put(c, 1);
        }
      }
    }

    println();
    println();

    Histogram h = new Histogram("Character counts: " + text.name);
    h.setValuePlacement(ValuePlacement.INSIDE);
    h.setValueMode(ValueMode.BOTH);

    for (char c : counts.keySet()) {
      h.addCategory(String.valueOf(c), counts.get(c));
    }
    h.print();
    h.clear();

    println();
    println();

    h.setTitle("Sorted character counts: " + text.name);
    counts.entrySet()
      .stream()
      .sorted((a, b) -> b.getValue() - a.getValue())
      .forEach(e -> h.addCategory(String.valueOf(e.getKey()), e.getValue()));
    h.print();
    h.clear();

    println();
    println();

    h.setTitle("Vowel counts: " + text.name);
    h.addCategory("a", counts.getOrDefault('a', 0));
    h.addCategory("e", counts.getOrDefault('e', 0));
    h.addCategory("i", counts.getOrDefault('i', 0));
    h.addCategory("o", counts.getOrDefault('o', 0));
    h.addCategory("u", counts.getOrDefault('u', 0));
    h.print();
  }
}
