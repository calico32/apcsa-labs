package shared;

import java.util.Arrays;

public class Util {
  public static int printWidth(Object obj) {
    // remove ANSI escape sequences and count the remaining characters
    String text = obj.toString().replaceAll("\u001B\\[[;\\d]*m", "");
    return text.length();
  }

  public static String indent(String text, int indent) {
    String[] lines   = text.split("\n");
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < lines.length; i++) {
      if (i > 0) {
        sb.append("\n");
      }
      sb.append(" ".repeat(indent));
      sb.append(lines[i]);
    }
    return sb.toString();
  }

  public static String bytesToHex(int[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (int b : bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }

  public static <T> T[] reverse(T[] arr) {
    T[] reversed = Arrays.copyOf(arr, arr.length);
    for (int i = 0; i < arr.length / 2; i++) {
      T temp                       = reversed[i];
      reversed[i]                  = reversed[arr.length - i - 1];
      reversed[arr.length - i - 1] = temp;
    }
    return reversed;
  }

  public static int[] reverse(int[] arr) {
    int[] reversed = Arrays.copyOf(arr, arr.length);
    for (int i = 0; i < arr.length / 2; i++) {
      int temp                     = reversed[i];
      reversed[i]                  = reversed[arr.length - i - 1];
      reversed[arr.length - i - 1] = temp;
    }
    return reversed;
  }
}
