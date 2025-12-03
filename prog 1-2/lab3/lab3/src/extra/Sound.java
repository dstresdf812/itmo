package extra;
import java.util.ArrayList;
import characters.Character;
public class Sound {
    private SoundType type;
    private int volume;
    private Character source;

    public Sound(SoundType type, int volume, Character source, String description) {
        this.type = type;
        this.volume = volume;
        this.source = source;
        if(type == SoundType.FOOTSTEP) {
            description = "Слышны шаги" + this.source.toString() + "\n";
        } else if(type == SoundType.CREAK) {
            description = this.source.toString() + " вызвал скрип\n";
        } else if(type == SoundType.RUSTLE) {
            description = "Из-за " + this.source.toString() + " послышался шорох\n";
        } else if (type == SoundType.SHOUT) {
            description = this.source.toString() + " крикнул\n";
        } else if (type == SoundType.WHISPER) {
            description = this.source.toString() + " шепчет\n";
        }
        System.out.println(description);
    }

    public void play(FlatRoom room, Character source) {
        for (Character c : room.getCharacters()) {
            if (c != this.source) {
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
}
