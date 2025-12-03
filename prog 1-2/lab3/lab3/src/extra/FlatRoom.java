package extra;

import characters.Character;
import java.util.ArrayList;

public class FlatRoom {
    private ArrayList<Character> characters;

    public FlatRoom(ArrayList<Character> characters) {
        this.characters = new ArrayList<>();
    }
    public void addCharacter(Character c) { characters.add(c); }
    public void removeCharacter(Character c) { characters.remove(c); }
    public ArrayList<Character> getCharacters() { return new ArrayList<Character>(characters); }
    public boolean contains(Character c) { return characters.contains(c); }
}