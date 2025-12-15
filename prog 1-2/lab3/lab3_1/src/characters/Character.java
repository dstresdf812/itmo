package characters;

import extra.*;
import interfaces.Moveable;
import exceptions.MovementException;
import java.util.Objects;
import java.util.Random;

public abstract class Character implements Moveable {
    protected final String name;
    protected FlatRoom room;
    protected Costume costume;
    protected int attentionLevel;
    protected boolean isAttented;
    protected Random random;

    public Character(String name, FlatRoom room, Costume costume, int attentionLevel) {
        this.name = name;
        this.room = room;
        this.costume = costume;
        this.attentionLevel = Math.max(0, Math.min(100, attentionLevel));
        this.isAttented = false;
        room.addCharacter(this);
    }

    @Override
    public void moveTo(FlatRoom newRoom) throws MovementException {
        if (costume.restrictsMovement()) {
            System.out.println(name + " пытается двигаться, но костюм мешает!");
            makeSound("шорохи и скрипы", 40, SoundType.RUSTLE);
            if (Math.random() > 0.5) {
                System.out.println("Но " + name + " двигается дальше!");
            } else {
                throw new MovementException(name + " не может нормально двигаться из-за костюма!");
            }
        }
        System.out.println(name + " идет из " + room.getName() + " в " + newRoom.getName());
        room.removeCharacter(this);
        room = newRoom;
        room.addCharacter(this);
        makeSound("шаги", 60, SoundType.FOOTSTEP);
        }

    public void moveinRoom() throws MovementException {
        if (this.costume.restrictsMovement()) {
            System.out.println(this.name + " пытается идти, но костюм мешает.");
            makeSound("шуршание и скрипы", 50, SoundType.RUSTLE);
        } else {
            makeSound("шаги", 40, SoundType.FOOTSTEP);
        }
    }

    public void hear(Sound s) {
        int newVolume = s.getVolume();
        if (isAttented) {
            newVolume += 20;
        }
        if (attentionLevel >= newVolume) {
            System.out.println(name + " услышал: " + s.getDescription());
            notice(s.getSource());
            attentionLevel = Math.min(100, attentionLevel + 10);
        } else {
            System.out.println(name + " слишком занят и не слышит звук");
            attentionLevel = Math.max(0, attentionLevel - 10);
        }
    }

    public void notice(Character c) {
        if (c instanceof Karlson && ((Karlson) c).isHidden()) {
            System.out.println(name + "замечает что-то подозрительное в " + room.getName() + "...");
        } else if (c instanceof Karlson && ((Karlson) c).isInCostume()) {
            System.out.println(name + "видит странную фигуру! Это " + c.getName() + "?");
            becomeAttented();
        } else {
            System.out.println(name + "замечает " + c.getName() + " в " + room.getName());
        }
    }

    public Sound makeSound(String desc, int volume, SoundType type) {
        Sound sound = new Sound(type, volume, this, name + ": " + desc + " в " + room.getName());
        sound.play(this.room);
        return sound;
    }

    public void becomeAttented() {
        if (!this.isAttented) {
            this.isAttented = true;
            this.attentionLevel = 100;
            System.out.println(name + " испугался");
            makeSound("визг ", 70, SoundType.SHOUT);
        }
    }

    public void calmDown() {
        if (this.isAttented) {
            this.isAttented = false;
            this.attentionLevel = Math.max(50, this.attentionLevel - 30);
            System.out.println(name + " успокаивается");
        }
    }

    public String getName() {
        return name;
    }

    public FlatRoom getRoom() {
        return room;
    }

    public boolean isAttented() {
        return isAttented;
    }

    public int getAttentionLevel() {
        return attentionLevel;
    }

    public Costume getCostume() {
        return costume;
    }

    @Override
    public String toString() {
        return name + " в " + room.getName() + (costume.restrictsMovement() ? " (в неудобном костюме)" : "") + (isAttented ? " (удивлён)" : "") + " [внимание: " + attentionLevel + "%]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Character that = (Character) o;
        return name.equals(that.name) && room.equals(that.room) && attentionLevel == that.attentionLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, room, attentionLevel);
    }
}