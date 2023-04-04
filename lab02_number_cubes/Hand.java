package lab02_number_cubes;

import shared.TextSegment;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.BiConsumer;

public class Hand implements Iterable<NumberCube> {
    public final int capacity;
    public NumberCube[] dice;
    public boolean[] kept;

    public Hand(int capacity) {
        this.capacity = capacity;
        this.dice = new NumberCube[capacity];
        this.kept = new boolean[capacity];
    }

    public void clear() {
        Arrays.fill(kept, false);
        Arrays.fill(dice, null);
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

    public boolean allKept() {
        for (int i = 0; i < capacity; i++) {
            if (!kept[i]) {
                return false;
            }
        }
        return true;
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

        callback.accept(null, -1);

        for (int i = 0; i < capacity; i++) {
            if (!kept[i]) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                }

                dice[i] = new NumberCube();
                dice[i].roll();
                callback.accept(dice[i], i);
            }
        }
    }

    public void keep(int index) {
        kept[index] = true;
    }

    public void unkeep(int index) {
        kept[index] = false;
    }

    public void toggle(int index) {
        kept[index] = !kept[index];
    }

    public void unkeepAll() {
        for (int i = 0; i < capacity; i++) {
            kept[i] = false;
        }
    }

    @Override
    public Iterator<NumberCube> iterator() {
        return Arrays.asList(dice).iterator();
    }

    public String draw() {
        return draw("", null);
    }

    public String draw(String defaultStyle, Integer selected) {
        String[] faces = new String[capacity];
        for (int i = 0; i < capacity; i++) {
            if (dice[i] == null) {
                faces[i] = (" ".repeat(9) + "\n").repeat(4) + " ".repeat(9);
            } else {
                faces[i] = dice[i].draw();
            }
        }

        int numLines = faces[0].split("\n").length;

        String[][] faceLines = new String[capacity][numLines];
        for (int i = 0; i < capacity; i++) {
            faceLines[i] = faces[i].split("\n");
        }

        String[] lines = new String[numLines + 1];
        for (int i = 0; i < numLines + 1; i++) {
            lines[i] = "";
            for (int j = 0; j < capacity; j++) {
                String line;
                if (kept[j]) {
                    if (i == numLines) {
                        line = " ".repeat(10);
                    } else {
                        line = faceLines[j][i] + " ";
                    }
                } else if (i == 0) {
                    line = " ".repeat(10);
                } else {
                    line = faceLines[j][i - 1] + " ";
                }

                TextSegment segment = new TextSegment(line);

                if (selected != null && j == selected) {
                    segment.blue();
                } else if (kept[j]) {
                    segment.whiteBright();
                } else {
                    segment.style(defaultStyle);
                }

                lines[i] += segment + " ";
            }
        }

        return String.join("\n", lines);
    }
}
