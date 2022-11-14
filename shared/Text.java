package shared;

import java.io.IOException;
import java.io.InputStream;

public class Text {
  public final String name;
  public final String content;
  public final int length;

  public Text(String name, String content) {
    this.name    = name;
    this.content = content;
    this.length  = content.length();
  }

  public static Text fromFile(String name, String filename) {
    String content = "";
    InputStream input =
      Text.class.getClassLoader().getResourceAsStream(filename + ".txt");
    if (input == null) {
      throw new IllegalArgumentException("No such file: " + filename);
    }
    try {
      content = new String(input.readAllBytes());
    } catch (IOException e) {
      e.printStackTrace();
      return new Text(e.toString(), e.toString());
    }

    return new Text(name, content);
  }

  public static final Text BEE_MOVIE_SCRIPT =
    Text.fromFile("Bee Movie script", "bee_movie");
  public static final Text DIGITS_OF_PI = Text.fromFile("Digits of Pi", "digits_of_pi");
  public static final Text FITNESSGRAM_PACER_TEST =
    Text.fromFile("FitnessGram Pacer Test", "fitnessgram_pacer_test");
  public static final Text HISTORY_OF_THE_ENTIRE_WORLD =
    Text.fromFile("history of the entire world, i guess", "history_of_the_entire_world");
  public static final Text LOREM_IPSUM   = Text.fromFile("Lorem Ipsum", "lorem_ipsum");
  public static final Text STEADMED_HAMS = Text.fromFile("Steamed Hams", "steamed_hams");
  public static final Text LEGO_MOVIE_SCRIPT =
    Text.fromFile("The Lego Movie script", "the_lego_movie");

  public static final Text[] examples = new Text[] {
    BEE_MOVIE_SCRIPT,
    DIGITS_OF_PI,
    FITNESSGRAM_PACER_TEST,
    HISTORY_OF_THE_ENTIRE_WORLD,
    LOREM_IPSUM,
    STEADMED_HAMS,
    LEGO_MOVIE_SCRIPT,
  };
}
