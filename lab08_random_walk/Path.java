package lab08_random_walk;

import java.util.ArrayList;
import java.util.HashMap;

import shared.Direction;
import shared.Point;

public class Path {

  public static enum NodeType {
    NS("│"),
    EW("─"),
    NE("└"),
    NW("┘"),
    SE("┌"),
    SW("┐"),
    N("╵"),
    S("╷"),
    E("╶"),
    W("╴"),
    NONE("·");

    public final String symbol;
    NodeType(String symbol) { this.symbol = symbol; }

    public static NodeType from(Direction direction) {
      switch (direction) {
        case NORTH: return N;
        case SOUTH: return S;
        case EAST: return E;
        case WEST: return W;
        default: return null;
      }
    }

    public static NodeType from(Direction a, Direction b) {
      if (a == b) {
        return from(a);
      }

      if (a.compareTo(b) > 0) {
        var temp = a;

        a = b;
        b = temp;
      }

      switch (a) {
        case NORTH:
          switch (b) {
            case SOUTH: return NS;
            case EAST: return NE;
            case WEST: return NW;
            default: return null;
          }
        case SOUTH:
          switch (b) {
            case EAST: return SE;
            case WEST: return SW;
            default: return null;
          }
        case EAST:
          switch (b) {
            case WEST: return EW;
            case SOUTH: return SE;
            default: return null;
          }
        case WEST:
          switch (b) {
            case SOUTH: return SW;
            default: return null;
          }
        default: return null;
      }
    }
  }

  public static void main(String[] args) {
    Direction a, b;
    Direction[] values = Direction.values();
    for (int i = 0; i < values.length; i++) {
      a = values[i];
      for (int j = i; j < values.length; j++) {
        b = values[j];
        System.out.printf("%s + %s = %s%n", a, b, NodeType.from(a, b));
      }
    }
  }

  public final HashMap<Point, NodeType> nodes = new HashMap<>();
  public final ArrayList<Point> path          = new ArrayList<>();

  public Path() {
    nodes.put(Point.ZERO, NodeType.NONE);
    path.add(Point.ZERO);
  }

  public void move(Direction direction) {
    var lastPoint = path.get(path.size() - 1);
    var newPoint  = lastPoint.add(direction.x, direction.y);

    if (path.size() == 1) {
      // last point must be the origin
      nodes.put(lastPoint, NodeType.from(direction));
    } else {
      var previous = Direction.from(lastPoint.subtract(path.get(path.size() - 2)));
      nodes.put(lastPoint, NodeType.from(previous.opposite(), direction));
    }

    path.add(newPoint);
    nodes.put(newPoint, NodeType.from(direction.opposite()));
  }

  public void reset() {
    nodes.clear();
    path.clear();
    nodes.put(Point.ZERO, NodeType.NONE);
    path.add(Point.ZERO);
  }
}
