package characters;

import extra.Costume;
import extra.FlatRoom;
import extra.SoundType;
import java.util.Random;

public class GospodinPek extends Character {
    private boolean isBusy;
    private String currentActivity;
    private Random random;

    public GospodinPek(String name, FlatRoom room, Costume costume, int attentionLevel) {
        super(name, room, costume, attentionLevel);
        this.isBusy = true;
        this.currentActivity = "читает газету";
        this.random = new Random();
    }

    public void comment() {
        if (isBusy) {
            String[] activities = {
                    "читает газету",
                    "пьёт кофе",
                    "размышляет о жизни",
                    "смотрит в окно"
            };

            if (random.nextDouble() > 0.9) {
                currentActivity = activities[random.nextInt(activities.length)];
            }

            System.out.println(name + " занят своими делами: " + currentActivity);

            if (random.nextDouble() > 0.65) {
                String[] comments = {
                        "\'Интересная статья...\'",
                        "\'Кофе сегодня особенно хорош\'",
                        "\'Погода что-то меняется\'"
                };
                String comment = comments[random.nextInt(comments.length)];
                makeSound(comment, 30, SoundType.WHISPER);
            }
        } else {
            System.out.println(name + " бездельничает");
            attentionLevel = Math.min(60, attentionLevel + 10);
        }
    }

    public String getCurrentActivity() {
        return currentActivity;
    }


    @Override
    public String toString() {
        return super.toString() +
                (isBusy ? " [занят: " + currentActivity + "]" : " [свободен]");
    }
}