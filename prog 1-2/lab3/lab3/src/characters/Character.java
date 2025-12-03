package characters;

import extra.*;
import interfaces.Moveable;
import exceptions.MovementException;
import java.util.Objects;

public abstract class Character implements Moveable {
    protected final String name;
    protected FlatRoom room;
    protected Costume costume;
    protected int attentionLevel;

    public Character(String name, FlatRoom room, Costume costume, int attentionLevel) {
        this.name = name;
        this.room = room;
        this.costume = costume;
        this.attentionLevel = attentionLevel;
        room.addCharacter(this);
    }

    @Override
    public void moveTo(FlatRoom newRoom) throws MovementException {
        if (costume.restrictsMovement()) {
            Sound s1 = makeSound("шорох", 80, SoundType.RUSTLE);
            throw new MovementException(name + " не может двигаться из-за костюма");
        }
        room.removeCharacter(this);
        room = newRoom;
        room.addCharacter(this);
        makeSound("шаги", 50, SoundType.FOOTSTEP);
        makeSound("шорох", 30, SoundType.RUSTLE);
    }

    public void hear(Sound s) {
        if (attentionLevel >= s.getVolume()) {
            System.out.println(name + " услышал звук и заметил " + s.getSource().getName());
            notice(s.getSource());
        } else {
            System.out.println(name + " слишком увлечён и не слышит ничего");
        }
    }

    public void notice(Character c) {
        System.out.println(name + " Это " + c.getName() + "!");
    }

    public Sound makeSound(String desc, int volume, SoundType type) {
        Sound sound = new Sound(type, volume, this, desc);
        sound.play(this.room,this);
        return sound;
    }

    public abstract void doSomething();

    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Character that = (Character) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}