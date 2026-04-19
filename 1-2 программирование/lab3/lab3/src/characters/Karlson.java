package characters;

import extra.Costume;
import extra.FlatRoom;
import interfaces.Hideable;

public class Karlson extends Character implements Hideable {
    private boolean hidden = false;

    public Karlson(String name, FlatRoom room, Costume costume, int attentionLevel, boolean hidden) {
        super(name, room, costume, attentionLevel);
        this.hidden = hidden;
    }

    @Override public void hide() { hidden = true; }
    @Override public void reveal() { hidden = false; }
    @Override public boolean isHidden() { return hidden; }

    @Override
    public void doSomething() {
        if (hidden) {
            System.out.println("Карлсон тихо сидит в укрытии...");
        } else {
            System.out.println("Карлсон важно разгуливает в маскарадном платье!");
            makeSound("шорох " + this.costume.toString(), 40, extra.SoundType.RUSTLE);
        }
    }
}