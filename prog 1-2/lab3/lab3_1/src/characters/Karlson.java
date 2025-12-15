package characters;

import extra.Costume;
import extra.Flat;
import extra.FlatRoom;
import extra.SoundType;
import interfaces.Hideable;
import exceptions.MovementException;
import java.util.Random;

public class Karlson extends Character implements Hideable {
    private boolean hidden;
    private boolean inCostume;
    private String hidingPlace;

    public Karlson(String name, FlatRoom room, Costume costume, int attentionLevel, boolean hidden) {
        super(name, room, costume, Math.min(60, attentionLevel));
        this.hidden = hidden;
        this.inCostume = false;
        this.hidingPlace = "ящик";
        this.random = new Random();
    }

    @Override
    public void moveTo(FlatRoom newRoom) throws MovementException {
        if (newRoom instanceof Flat.Kitchen) {
            System.out.println(name + " приближается к кухне...");

            if (inCostume) {
                System.out.println("В маскарадном костюме он кажется маленькой бойкой девочкой!");
                System.out.println("Он неумолимо приближается к кухне!");
            }

            makeSound("громкий шорох", 50, SoundType.RUSTLE);
        }
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

    @Override
    public void hide() {
        if (!hidden) {
            hidden = true;
            System.out.println(name + " прячется в " + hidingPlace);
            makeSound("тихое хихиканье", 15, SoundType.WHISPER);
        }
    }

    @Override
    public void reveal() {
        if (hidden) {
            hidden = false;
            System.out.println(name + " появляется из " + hidingPlace);
            makeSound("смех", 40, SoundType.SHOUT);
        }
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    public void putOnCostume(Costume costume) {
        if (!inCostume) {
            this.costume = costume;
            this.inCostume = true;
            System.out.println(costume.getDescription());
        }
    }

    public void takeOffCostume() {
        if (inCostume) {
            this.inCostume = false;
            this.costume = null;
            System.out.println(name + " снимает маскарадный костюм");
            }
    }

    public boolean isInCostume() {
        return inCostume;
    }

    public void clown() {
        if (hidden) {
            System.out.println(name + " тихо сидит в укрытии (" + hidingPlace + ")...");
            if (random.nextDouble() > 0.7) {
                makeSound("тихое постукивание", 10, SoundType.CREAK);
            }
        } else if (inCostume) {
            System.out.println(name + " разгуливает в маскарадном костюме в " + room.getName() + "!");
            try {
                moveinRoom();
            } catch (MovementException e) {
                System.out.println("Костюм мешает двигаться: " + e.getMessage());
            }

        } else {
            System.out.println(name + " что-то замышляет с хитрой улыбкой...");
        }
    }

    public void walkAround() {
        if (!hidden && inCostume) {
            System.out.println(name + " разгуливает по " + room.getName() + " в костюме:");
            for (int i = 1; i < 4; i++) {
                    System.out.println("Шаг " + i);
                    if (i == 0) {
                        makeSound("скрип половиц", 40, SoundType.CREAK);
                    } else {
                        makeSound("шорох костюма", 30, SoundType.RUSTLE);
                    }
                }
            }
            System.out.println(name + " неумолимо приближается к кухне!");
        }

    public void setHidingPlace(String place) {
        this.hidingPlace = place;
    }

    public String getHidingPlace() {
        return hidingPlace;
    }

    @Override
    public String toString() {
        return super.toString() +
                (hidden ? " [спрятан в " + hidingPlace + "]" : " [на виду]") +
                (inCostume ? " [в маскарадном костюме]" : " [без костюма]");
    }
}