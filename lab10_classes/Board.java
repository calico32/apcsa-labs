package lab10_classes;

import static shared.TextHelpers.*;

import java.util.HashMap;
import java.util.HashSet;

import shared.Console;
import shared.Direction;
import shared.Histogram;
import shared.Histogram.ValuePlacement;
import shared.Input;

public class Board {
  public static final int WIDTH  = 5;
  public static final int HEIGHT = 7;

  private Cell[][] cells = new Cell[HEIGHT][WIDTH];

  class CellGroup {
    public final Cell a;
    public final Cell b;
    public CellGroup(Cell a, Cell b) {
      this.a = a;
      this.b = b;
    }
  }

  private HashMap<Cell, Direction> joined = new HashMap<>();
  private HashSet<CellGroup> groups       = new HashSet<>();

  public Car car =
    new Car(2, 6, Direction.SOUTH, Math.random() > 0.5 ? Direction.EAST : Direction.WEST);

  public Board() {
    for (var y = 0; y < HEIGHT; y++) {
      for (var x = 0; x < WIDTH; x++) {
        cells[y][x] = new Cell(x, y, CellType.NONE);
      }
    }

    // randomly distribute 3 gas stations and one of every other type
    for (var type : CellType.values()) {
      if (type == CellType.NONE) {
        continue;
      }

      var count = type == CellType.GAS_STATION ? 3 : 1;
      for (var i = 0; i < count; i++) {
        randomEmptyCell().type = type;
      }
    }

    // randomly join cells
    int count = random(6, 8);
    for (var i = 0; i < count; i++) {
      var cell = randomCell();
      while (joined.containsKey(cell)) {
        cell = randomCell();
      }

      var directions = Direction.values();
      shuffle(directions);
      for (var direction : directions) {
        var neighbor = getNeighbor(cell, direction);
        if (neighbor != null && !joined.containsKey(neighbor)) {
          groups.add(new CellGroup(cell, neighbor));
          joined.put(cell, direction);
          joined.put(neighbor, direction.opposite());
          break;
        }
      }
    }
  }

  public Cell getNeighbor(Cell cell, Direction direction) {
    var x = cell.x;
    var y = cell.y;
    switch (direction) {
      case NORTH: y--; break;
      case SOUTH: y++; break;
      case WEST: x--; break;
      case EAST: x++; break;
    }
    if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
      return null;
    }
    return cells[y][x];
  }

  private Cell randomCell() { return cells[random(0, HEIGHT - 1)][random(0, WIDTH - 1)]; }

  private Cell randomEmptyCell() {
    var cell = randomCell();
    while (cell.type != CellType.NONE) {
      cell = randomCell();
    }
    return cell;
  }

  private int random(int min, int max) {
    return (int)(Math.random() * (max - min + 1)) + min;
  }

  public Cell getCell(int x, int y) {
    if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
      return null;
    }
    return cells[y][x];
  }

  public String draw() {
    var output = new StringBuilder();
    output.append("╔");
    for (var x = 0; x < WIDTH; x++) {
      output.append("═══");
      if (car.cellX == x && car.cellY == 0 && car.side == Direction.NORTH) {
        output.append(car.facing.emoji + " ");
      } else {
        output.append("══");
      }
      output.append("═══");
      if (x != WIDTH - 1) {
        output.append("╦");
      } else {
        output.append("╗\n");
      }
    }

    for (var y = 0; y < HEIGHT; y++) {
      output.append("║");
      for (var x = 0; x < WIDTH; x++) {
        output.append("        ");
        if (joined.get(cells[y][x]) == Direction.EAST) {
          output.append(" ");
        } else {
          output.append("║");
        }
      }
      output.append("\n");
      if (car.cellX == 0 && car.cellY == y && car.side == Direction.WEST) {
        output.append(car.facing.emoji);
      } else {
        output.append("║");
      }

      for (var x = 0; x < WIDTH; x++) {
        output.append("   ");
        output.append(cells[y][x].type.symbol);
        output.append("   ");
        if (joined.get(cells[y][x]) == Direction.EAST) {
          output.append(" ");
        } else if (car.cellX == x && car.cellY == y && car.side == Direction.EAST || car.cellX == x + 1 && car.cellY == y && car.side == Direction.WEST) {
          output.append(car.facing.emoji);
        } else {
          output.append("║");
        }
      }
      output.append("\n");
      output.append("║");
      for (var x = 0; x < WIDTH; x++) {
        output.append("        ");
        if (joined.get(cells[y][x]) == Direction.EAST) {
          output.append(" ");
        } else {
          output.append("║");
        }
      }
      output.append("\n");
      if (y != HEIGHT - 1) {
        output.append("╠");
        for (var x = 0; x < WIDTH; x++) {
          if (joined.get(cells[y][x]) == Direction.SOUTH) {
            output.append("        ");
          } else {
            output.append("═══");
            if (car.cellX == x && car.cellY == y && car.side == Direction.SOUTH || car.cellX == x && car.cellY == y + 1 && car.side == Direction.NORTH) {
              output.append(car.facing.emoji + " ");
            } else {
              output.append("══");
            }
            output.append("═══");
          }
          if (x != WIDTH - 1) {
            output.append("╬");
          } else {
            output.append("╣\n");
          }
        }
      } else {
        output.append("╚");
        for (var x = 0; x < WIDTH; x++) {
          if (joined.get(cells[y][x]) == Direction.SOUTH) {
            output.append("        ");
          } else {
            output.append("═══");
            if (car.cellX == x && car.cellY == y && car.side == Direction.SOUTH) {
              output.append(car.facing.emoji + " ");
            } else {
              output.append("══");
            }
            output.append("═══");
          }
          if (x != WIDTH - 1) {
            output.append("╩");
          } else {
            output.append("╝\n");
          }
        }
      }
    }

    return output.toString();
  }

  private void shuffle(Object[] array) {
    for (var i = 0; i < array.length; i++) {
      var j    = random(0, array.length - 1);
      var temp = array[i];
      array[i] = array[j];
      array[j] = temp;
    }
  }

  public void loop() {
    Console.clear();
    System.out.println();
    var partner = new Partner();

    while (true) {
      System.out.println(draw());

      var bars = new Histogram("");
      bars.setSize(new Console.Size(40, 30))
        .setValuePlacement(ValuePlacement.INSIDE)
        .addCategory("Gas", partner.getGas())
        .addCategory("Food", partner.getFood())
        .addCategory("Drink", partner.getDrink())
        .addCategory("Enjoyment", partner.getEnjoyment())
        .addCategory("Time", partner.getTime());

      var chart = bars.draw(100).split("\n");
      for (var i = 0; i < chart.length; i++) {
        println(text(chart[i]));
      }

      println(text(
        "Car is at " + car.cellX + ", " + car.cellY + " on side " + car.side +
        " facing " + car.facing + "."
      ));
      var line = Input.readString(text("[uldr/q]"));

      if (line.equals("q")) {
        break;
      }

      var copy = car.clone();

      Console.clear();

      Direction direction = null;
      switch (line) {
        case "u": direction = Direction.NORTH; break;
        case "d": direction = Direction.SOUTH; break;
        case "l": direction = Direction.WEST; break;
        case "r": direction = Direction.EAST; break;
        case "R": copy.turnRight(); break;
        case "L": copy.turnLeft(); break;
        case "F": copy.moveForward(); break;
        default: continue;
      }

      if (direction == copy.facing.left()) {
        partner.step();
        partner.move();
        copy.turnLeft();
        println("left");
      } else if (direction == copy.facing.right()) {
        partner.step();
        partner.move();
        copy.turnRight();
        println("right");
      } else if (direction == copy.facing) {
        partner.step();
        partner.move();
        copy.moveForward();
        println("forward");
      }

      var cell = getCell(copy.cellX, copy.cellY);
      if (cell == null || joined.get(cell) == copy.side) {
        continue;
      } else {
        car = copy;
      }
    }
  }
}
