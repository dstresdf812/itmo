package extra;

import characters.Character;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlatRoom {
    private final String name;
    private final Flat flat;
    private final ArrayList<Character> characters;

    public FlatRoom(String name, Flat flat) {
        this.name = name;
        this.flat = flat;
        this.characters = new ArrayList<>();
    }

    public Flat getFlat() {
        return flat;
    }

    public String getName() {
        return name;
    }

    public void addCharacter(Character c) {
        characters.add(c);
    }

    public void removeCharacter(Character c) {
        characters.remove(c);
    }

    public ArrayList<Character> getCharacters() {
        return new ArrayList<>(characters);
    }

    public boolean contains(Character c) {
        return characters.contains(c);
    }

    public boolean isEmpty() {
        return characters.isEmpty();
    }

    @Override
    public String toString() {
        return name + " [персонажей: " + characters.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        FlatRoom flatRoom = (FlatRoom) o;
        return name.equals(flatRoom.name) && flat.equals(flatRoom.flat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, flat);
    }
}