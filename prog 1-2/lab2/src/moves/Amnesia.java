package moves;

import ru.ifmo.se.pokemon.*;

public class Amnesia extends StatusMove {
    public Amnesia() {
        super(Type.PSYCHIC,0,0);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.SPECIAL_DEFENSE, 2);
    }

    @Override
    protected String describe() {
        return "использует Amnesia";
    }
}
