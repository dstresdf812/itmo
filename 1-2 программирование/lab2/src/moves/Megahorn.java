package moves;

import ru.ifmo.se.pokemon.*;

public class Megahorn extends PhysicalMove {
    public Megahorn() {
        super(Type.BUG,120,85);
    }

    @Override
    protected String describe() {
        return "использует Megahorn";
    }
}
