package pokemons;

import moves.GrassWhistle;

public class Swadloon extends Sewaddle {
    public Swadloon(String name, int level) {
        super(name, level);
        setStats(55,63,90,50,80,42);
        addMove(new GrassWhistle());
    }
}
