package lab02_number_cubes;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.BiConsumer;

public class Hand implements Iterable<NumberCube> {
  public final int capacity;
  public NumberCube[] dice;
  public boolean[] kept;

  public Hand(int capacity) {
    this.capacity = capacity;
    this.dice     = new NumberCube[capacity];
  }

  public void roll() {

    for (int i = 0; i < capacity; i++) {
      dice[i] = new NumberCube();
      dice[i].roll();
    }
  }

  public void rollUnkept() {
    for (int i = 0; i < capacity; i++) {
      if (!kept[i]) {
        dice[i] = new NumberCube();
        dice[i].roll();
      }
    }
  }

  public void roll(BiConsumer<NumberCube, Integer> callback, int delay) {
    for (int i = 0; i < capacity; i++) {
      try {
        Thread.sleep(delay);
      } catch (InterruptedException e) {
      }

      dice[i] = new NumberCube();
      dice[i].roll();
      callback.accept(dice[i], i);
    }
  }

  public void rollUnkept(BiConsumer<NumberCube, Integer> callback, int delay) {
    for (int i = 0; i < capacity; i++) {
      if (!kept[i]) {
        dice[i] = null;
      }
    }

    for (int i = 0; i < capacity; i++) {
      if (!kept[i]) {
        try {
          Thread.sleep(i * delay);
        } catch (InterruptedException e) {
        }

        dice[i] = new NumberCube();
        dice[i].roll();
        callback.accept(dice[i], i);
      }
    }
  }

  public void keep(int index) { kept[index] = true; }
  public void unkeep(int index) { kept[index] = false; }
  public void toggle(int index) { kept[index] = !kept[index]; }

  @Override
  public Iterator<NumberCube> iterator() {
    return Arrays.asList(dice).iterator();
  }

  public String draw() {
    String[] faces = new String[capacity];
    for (int i = 0; i < capacity; i++) {
      if (dice[i] == null) {
        faces[i] = (" ".repeat(9) + "\n").repeat(5) + " ".repeat(9);
      } else {
        faces[i] = dice[i].draw();
      }
    }

    int numLines = faces[0].split("\n").length;

    String[][] faceLines = new String[capacity][numLines];
    for (int i = 0; i < capacity; i++) {
      faceLines[i] = faces[i].split("\n");
    }

    String[] lines = new String[numLines];
    for (int i = 0; i < numLines; i++) {
      lines[i] = "";
      for (int j = 0; j < capacity; j++) {
        lines[i] += faceLines[j][i] + " ";
      }
    }

    return String.join("\n", lines);
  }
}
