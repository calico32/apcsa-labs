package lab03_substrings;

import java.io.IOException;
import java.io.InputStream;

public class Text {
  public final String name;
  public final String content;
  public final int length;

  public Text(String name, String filename) {
    this.name = name;

    String content = "";
    InputStream input =
      getClass().getClassLoader().getResourceAsStream(filename + ".txt");
    if (input == null) {
      throw new IllegalArgumentException("No such file: " + filename);
    }
    try {
      content = new String(input.readAllBytes());
    } catch (IOException e) {
      e.printStackTrace();
      this.content = e.toString();
      this.length  = this.content.length();
      return;
    }
    this.content = content;
    this.length  = content.length();
  }
}
