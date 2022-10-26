package lab03_substrings;

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
}
