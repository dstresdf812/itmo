package characters;

import extra.Costume;
import extra.FlatRoom;

public class Malish extends Character {
    public Malish(String name, FlatRoom room, Costume costume, int attentionLevel) {
        super(name, room, costume, attentionLevel);
    }

    @Override
    public void doSomething() {
        System.out.println(name + " затаил дыхание и внимательно слушает...");
    }
}