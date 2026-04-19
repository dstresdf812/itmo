package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;


public class Sewaddle extends Pokemon {
    public Sewaddle(String name, int level) {
        super(name, level);
        setStats(45,53,70,40,60,42);
        setType(Type.BUG,Type.GRASS);
        setMove(new DoubleTeam(),new EnergyBall());
    }
}
