package pokemons;

import ru.ifmo.se.pokemon.*;
import moves.*;

public class Xerneas extends Pokemon {
    public Xerneas(String name, int level) {
        super(name, level);
        setStats(126,131,95,131,98,99);
        setType(Type.FAIRY);
        setMove(new FocusBlast(),new AuroraBeam(), new DoubleTeam(), new Megahorn());
    }
}
