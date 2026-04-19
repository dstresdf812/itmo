package moves;

import ru.ifmo.se.pokemon.*;

public class RazorLeaf extends PhysicalMove {
    public RazorLeaf() {
        super(Type.GRASS,55,95);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect.sleep(p);
    }

    @Override
    protected double calcCriticalHit(Pokemon var1, Pokemon var2) {
        if (var1.getStat(Stat.SPEED)*3 / (double)512.0F > Math.random()) {
            System.out.println("critical");
            return (double)2.0F;
        } else {
            return (double)1.0F;
        }
    }
    @Override
    protected String describe() {
        return "использует Razor Leaf";
    }
}
