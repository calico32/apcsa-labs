package shared;

import lab08_random_walk.Point;

public enum Direction {
  NORTH(0, -1, '↑', '^', '⬆'),
  SOUTH(0, 1, '↓', 'v', '⬇'),
  EAST(1, 0, '→', '>', '➡'),
  WEST(-1, 0, '←', '<', '⬅');

  public final int x;
  public final int y;
  public final char unicode;
  public final char ascii;
  public final char emoji;

  Direction(int x, int y, char unicode, char ascii, char emoji) {
    this.x       = x;
    this.y       = y;
    this.unicode = unicode;
    this.ascii   = ascii;
    this.emoji   = emoji;
  }

  public Direction opposite() {
    switch (this) {
      case NORTH: return SOUTH;
      case SOUTH: return NORTH;
      case EAST: return WEST;
      case WEST: return EAST;
      default: return null;
    }
  }

  public Direction[] choices() {
    switch (this) {
      case NORTH: return new Direction[] {EAST, WEST, NORTH};
      case SOUTH: return new Direction[] {EAST, WEST, SOUTH};
      case EAST: return new Direction[] {NORTH, SOUTH, EAST};
      case WEST: return new Direction[] {NORTH, SOUTH, WEST};
      default: return null;
    }
  }

  public static Direction from(Point p) { return from(p.x, p.y); }
  public static Direction from(int x, int y) {
    if (x == 0) {
      switch (y) {
        case -1: return NORTH;
        case 1: return SOUTH;
        default: return null;
      }
    }

    if (y == 0) {
      switch (x) {
        case -1: return WEST;
        case 1: return EAST;
        default: return null;
      }
    }

    return null;
  }

  public Direction left() {
    switch (this) {
      case NORTH: return WEST;
      case SOUTH: return EAST;
      case EAST: return NORTH;
      case WEST: return SOUTH;
      default: return null;
    }
  }

  public Direction right() {
    switch (this) {
      case NORTH: return EAST;
      case SOUTH: return WEST;
      case EAST: return SOUTH;
      case WEST: return NORTH;
      default: return null;
    }
  }
}
