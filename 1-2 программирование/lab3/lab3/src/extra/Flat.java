package extra;

import java.util.ArrayList;
import characters.Character;

public class Flat extends FlatRoom {
    static private boolean doorToKitchenOpened;

    public Flat(boolean doorToKitchenOpened) {
        this.doorToKitchenOpened = doorToKitchenOpened;
    }

    public boolean isDoorToKitchenOpened() { return doorToKitchenOpened; }
    static public void openDoorToKitchen() { doorToKitchenOpened = true; }
    static public void closeDoorToKitchen() { doorToKitchenOpened = false; }

    public class Kitchen extends FlatRoom {
        public Kitchen (ArrayList<characters.Character> characters) {
            super(characters);
        }
    }
    public class Hallway extends FlatRoom {
        public Hallway(ArrayList<characters.Character> characters) {
            super(characters);
        }
    }
}