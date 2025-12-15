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
        this.name = Objects.requireNonNull(name, "Название комнаты не может быть null");
        this.flat = Objects.requireNonNull(flat, "Квартира не может быть null");
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

    public int getCharacterCount() {
        return characters.size();
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
        if (o == null || getClass() != o.getClass()) return false;
        FlatRoom flatRoom = (FlatRoom) o;
        return name.equals(flatRoom.name) && flat.equals(flatRoom.flat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, flat);
    }
}