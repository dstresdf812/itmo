package moves;

import ru.ifmo.se.pokemon.*;

public class Magnitude extends PhysicalMove {
    public Magnitude() {
        super(Type.GROUND,0,100);
    }

    private double Power;
    @Override
    protected double calcBaseDamage(Pokemon var1, Pokemon var2) {
        double rand = Math.random();
        if (rand <= 0.05) { Power = 10; }
        else if (rand <= 0.15) { Power = 30; }
        else if (rand <= 0.35) { Power = 50; }
        else if (rand <= 0.65) { Power = 70; }
        else if (rand <= 0.85) { Power = 90; }
        else if (rand <= 0.95) { Power = 110; }
        else { Power = 150; }

        return (0.4 * (double) var1.getLevel() + (double)2.0F) * Power / (double)150.0F;
    }

    @Override
    protected String describe() {
        return "использует Magnitude";
    }
}
