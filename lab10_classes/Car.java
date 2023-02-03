package lab10_classes;

import shared.Direction;

public class Car {
  public int cellX;
  public int cellY;
  public Direction side;
  public Direction facing;

  public Car() {
    cellX  = 0;
    cellY  = 0;
    side   = Direction.EAST;
    facing = Direction.EAST;
  }

  public Car(int cellX, int cellY, Direction side, Direction facing) {
    this.cellX  = cellX;
    this.cellY  = cellY;
    this.side   = side;
    this.facing = facing;
  }

  public Car clone() { return new Car(cellX, cellY, side, facing); }

  public void turnLeft() {
    switch (side) {
      case NORTH:
        if (facing == Direction.EAST) {
          cellY--;
        }
        break;
      case SOUTH:
        if (facing == Direction.WEST) {
          cellY++;
        }
        break;
      case EAST:
        if (facing == Direction.SOUTH) {
          cellX++;
        }
        break;
      case WEST:
        if (facing == Direction.NORTH) {
          cellX--;
        }
    }

    side   = facing;
    facing = facing.left();
  }

  public void turnRight() {
    switch (side) {
      case NORTH:
        if (facing == Direction.WEST) {
          cellY--;
        }
        break;
      case SOUTH:
        if (facing == Direction.EAST) {
          cellY++;
        }
        break;
      case EAST:
        if (facing == Direction.NORTH) {
          cellX++;
        }
        break;
      case WEST:
        if (facing == Direction.SOUTH) {
          cellX--;
        }
    }

    side   = facing;
    facing = facing.right();
  }

  public void moveForward() {
    switch (facing) {
      case NORTH: cellY--; break;
      case SOUTH: cellY++; break;
      case EAST: cellX++; break;
      case WEST: cellX--; break;
    }
  }
}
