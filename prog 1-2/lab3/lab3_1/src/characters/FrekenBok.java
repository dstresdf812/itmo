package characters;

import extra.Costume;
import extra.FlatRoom;
import extra.SoundType;
import java.util.Random;

public class FrekenBok extends Character {
    private boolean isCooking;
    private String currentDish;


    public FrekenBok(String name, FlatRoom room, Costume costume, int attentionLevel) {
        super(name, room, costume, attentionLevel);
        this.isCooking = true;
        this.currentDish = "пельмени";
        this.random = new Random();
    }

    public void cook() {
        if (isCooking) {
            this.isCooking = true;
            this.attentionLevel = Math.max(10, attentionLevel-20);

            String[] cookingActions = {
                    "мешает пельмени",
                    "пробует на соль",
                    "добавляет специи",
                    "проверяет духовку"
            };

            String action = cookingActions[random.nextInt(cookingActions.length)];
            System.out.println(name + " занята готовкой: " + action);

            if (random.nextDouble() > 0.5) {
                makeSound("звон посуды", 40, SoundType.CREAK);
            }
        }
    }

    public void clean() {
        System.out.println(name + " прибирается в" + this.getRoom().getName() + "...");
    }

    public void startCooking(String dish) {
        if (!isCooking) {
            isCooking = true;
            currentDish = dish;
            System.out.println(name + " начинает готовить " + dish);
            attentionLevel = 20;
        }
    }

    public void stopCooking() {
        if (isCooking) {
            isCooking = false;
            System.out.println(name + " прекращает готовить");
            attentionLevel = Math.min(80, attentionLevel + 40);
        }
    }

    public boolean isCooking() {
        return isCooking;
    }


    @Override
    public String toString() {
        return super.toString() +
                (isCooking ? " [готовит " + currentDish + "]" : " [не готовит]");
    }
}