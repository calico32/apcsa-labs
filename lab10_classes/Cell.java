package lab10_classes;

public class Cell {
    public static final int MAX_COOLDOWN = 4;

    public CellType type;
    public int cooldown = 0;
    public final int x;
    public final int y;

    public Cell(int x, int y, CellType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public String toString() {
        return type.symbol;
    }

    public void step() {
        if (cooldown > 0) {
            cooldown--;
        }
    }

    public boolean canUse() {
        return cooldown == 0;
    }

    public void use(Partner partner) {
        if (!canUse()) {
            return;
        }

        cooldown = MAX_COOLDOWN;

        type.effect.accept(partner);
    }
}
