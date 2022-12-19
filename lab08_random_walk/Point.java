package lab08_random_walk;

import java.util.HashMap;

public class Point {
  private static HashMap<Integer, HashMap<Integer, Point>> cache = new HashMap<>();

  public final int x;
  public final int y;

  private Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static Point xy(int x, int y) {
    if (!cache.containsKey(x)) {
      cache.put(x, new HashMap<>());
    }

    if (!cache.get(x).containsKey(y)) {
      cache.get(x).put(y, new Point(x, y));
    }

    return cache.get(x).get(y);
  }

  public static final Point ZERO = xy(0, 0);

  public Point add(Point other) { return xy(x + other.x, y + other.y); }
  public Point add(int x, int y) { return xy(this.x + x, this.y + y); }
  public Point subtract(Point other) { return xy(x - other.x, y - other.y); }
  public Point subtract(int x, int y) { return xy(this.x - x, this.y - y); }

  @Override
  public boolean equals(Object other) {
    if (other == null)
      return false;
    if (other == this)
      return true;
    if (!(other instanceof Point))
      return false;

    var otherPoint = (Point)other;
    return x == otherPoint.x && y == otherPoint.y;
  }

  @Override
  public int hashCode() {
    return (x << 16) ^ y;
  }

  @Override
  public String toString() {
    return String.format("(%d, %d)", x, y);
  }
}
