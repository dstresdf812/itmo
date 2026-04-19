package characters;

import extra.Costume;
import extra.FlatRoom;

public class FrekenBok extends Character{
    private boolean isCooking;
    public FrekenBok(String name, FlatRoom room, Costume costume, int attentionLevel, boolean isCooking) {
        super(name,room,costume,attentionLevel);
        this.isCooking = isCooking;
    }
    public void cook() {
        this.isCooking = true;
    }
    @Override public void doSomething() {
    }
}
