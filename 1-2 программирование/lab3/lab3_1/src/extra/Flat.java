package extra;

import java.util.ArrayList;

public class Flat {
    private boolean doorToKitchenOpened;
    private final Kitchen kitchen;
    private final Hallway hallway;
    private final LivingRoom livingRoom;

    public Flat(boolean doorToKitchenOpened) {
        this.doorToKitchenOpened = doorToKitchenOpened;
        this.kitchen = new Kitchen(this);
        this.hallway = new Hallway(this);
        this.livingRoom = new LivingRoom(this);
    }

    public class Kitchen extends FlatRoom {
        public Kitchen(Flat flat) {
            super("Кухня", flat);
        }
    }

    public class Hallway extends FlatRoom {
        public Hallway(Flat flat) {
            super("Прихожая", flat);
        }
    }

    public class LivingRoom extends FlatRoom {
        public LivingRoom(Flat flat) {
            super("Гостиная", flat);
        }
    }

    public boolean isDoorToKitchenOpened() {
        return doorToKitchenOpened;
    }

    public void openDoorToKitchen() {
        if (!doorToKitchenOpened) {
            doorToKitchenOpened = true;
            System.out.println("Дверь в кухню приоткрылась");
        }
    }

    public void closeDoorToKitchen() {
        if (doorToKitchenOpened) {
            doorToKitchenOpened = false;
            System.out.println("Дверь в кухню закрылась");
        }
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public Hallway getHallway() {
        return hallway;
    }

    public LivingRoom getLivingRoom() {
        return livingRoom;
    }

    public ArrayList<FlatRoom> getNearbyRooms(FlatRoom room) {
        ArrayList<FlatRoom> bordering = new ArrayList<>();
        if (room == kitchen && doorToKitchenOpened) {
            bordering.add(hallway);
        } else if (room == hallway) {
            if (doorToKitchenOpened) {
                bordering.add(kitchen);
            }
            bordering.add(livingRoom);
        } else if (room == livingRoom) {
            bordering.add(hallway);
        }

        return bordering;
    }

    public ArrayList<FlatRoom> getAllRooms() {
        ArrayList<FlatRoom> rooms = new ArrayList<>();
        rooms.add(kitchen);
        rooms.add(hallway);
        rooms.add(livingRoom);
        return rooms;
    }


    @Override
    public String toString() {
        return "Квартира{" + ", дверь в кухню=" + (doorToKitchenOpened ? "открыта" : "закрыта") + '}';
    }
}