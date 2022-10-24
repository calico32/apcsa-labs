package scripts;

import java.io.IOException;

import shared.Console;
import shared.Util;

public interface LogInput {
  public static void main(String[] args) throws IOException, InterruptedException {
    Console.init();

    Console.clear();

    try {
      while (true) {
        int[] input = Console.next();

        System.out.println("input: " + Util.bytesToHex(input));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    Console.reset();
  }
}
