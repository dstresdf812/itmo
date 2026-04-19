package pokemons;

import ru.ifmo.se.pokemon.*;
import moves.*;

public class Numel extends Pokemon {
    public Numel(String name, int level) {
        super(name, level);
        setStats(60,60,40,65,45,35);
        setType(Type.FIRE,Type.GROUND);
        setMove(new FlameCharge(),new Amnesia(), new Magnitude());
    }
}
