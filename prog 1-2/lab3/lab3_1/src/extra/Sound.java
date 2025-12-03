package extra;

import characters.Character;
import java.util.List;

public class Sound {
    private final SoundType type;
    private final int volume;
    private final Character source;
    private final String description;

    public Sound(SoundType type, int volume, Character source, String description) {
        this.type = type;
        this.volume = Math.max(0, Math.min(100, volume));
        this.source = source;
        this.description = description;
    }

    public void play(FlatRoom room) {
        System.out.println(description);
        spreadSound(room);
        Flat flat = room.getFlat();
        if (flat.isDoorToKitchenOpened()) {
            List<FlatRoom> adjacentRooms = flat.getNearbyRooms(room);
            for (FlatRoom adjRoom : adjacentRooms) {
                spreadSound(adjRoom);
            }
        }
    }

    private void spreadSound(FlatRoom room) {
        for (Character c : room.getCharacters()) {
            if (c != source) {
                c.hear(this);
            }
        }
    }

    public SoundType getType() {
        return this.type;
    }

    public Character getSource() {
        return this.source;
    }

    public int getVolume() {
        return this.volume;
    }

    public String getDescription() {
        return description;
    }


    public String getSourceName() {
        return source.getName();
    }

    @Override
    public String toString() {
        return "Звук{" + "тип=" + type + ", громкость=" + volume + ", источник=" + getSourceName() + ", описание='" + description + '\'' + '}';
    }
}