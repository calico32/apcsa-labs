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

    println(text("\nExamples:").bold());
    while (true) {
      println();

      for (int i = 0; i < Text.examples.length; i++) {
        println(text("%d. ", i + 1).bold().white(), text(Text.examples[i].name));
      }

      println(
        text(
          "\nSelect an example text by entering its number, or begin typing your own text. Type "
        ),
        text("[endtext]").bold(),
        text(" on a new line to finish.\n"),
        text("Type ").dim(),
        text("quit").dim().bold(),
        text(" to exit.").dim()
      );

      printHistograms(promptText());
    }
  }

  private static Text promptText() {
    Text text;
    var input = Input.readString();
    if (input.equals("quit")) {
      System.exit(0);
    }
    try {
      var index = Integer.parseInt(input) - 1;
      if (index < 0 || index >= Text.examples.length) {
        throw new NumberFormatException();
      }
      text = Text.examples[index];
    } catch (NumberFormatException e) {
      while (true) {
        var next = Input.readString();
        if (next.equals("[endtext]")) {
          break;
        }
        if (!next.isBlank()) {
          input += "\n" + next;
        }
      }

      text = new Text("Custom text", input.trim());
    }
    return text;
  }

  static HashMap<Character, Integer> countCharacters(Text text) {
    var counts = new HashMap<Character, Integer>();
    for (char c : text.content.toCharArray()) {
      if (!Character.isLetter(c) && !Character.isDigit(c)) {
        continue;
      }

      var key = Character.toLowerCase(c);

      if (counts.containsKey(key)) {
        counts.put(key, counts.get(key) + 1);
      } else {
        counts.put(key, 1);
      }
    }
    return counts;
  }

  private static void printHistograms(Text text) {
    var counts = countCharacters(text);

    println();
    println();

    var h = new Histogram("Character counts: " + text.name);
    h.setValuePlacement(ValuePlacement.INSIDE);
    h.setValueMode(ValueMode.BOTH);

    counts.entrySet()
      .stream()
      .sorted((a, b) -> a.getKey() - b.getKey())
      .forEach(e -> h.addCategory(String.valueOf(e.getKey()), e.getValue()));
    h.display();
    h.clear();

    println();
    println();

    h.setTitle("Sorted character counts: " + text.name);
    counts.entrySet()
      .stream()
      .sorted((a, b) -> b.getValue() - a.getValue())
      .forEach(e -> h.addCategory(String.valueOf(e.getKey()), e.getValue()));
    h.display();
    h.clear();

    println();
    println();

    h.setTitle("Vowel counts: " + text.name);
    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char c : vowels) {
      if (counts.containsKey(c)) {
        h.addCategory(String.valueOf(c), counts.get(c));
      }
    }
    h.display();
    h.clear();
  }
}
