package characters;

import extra.Costume;
import extra.FlatRoom;

import java.util.Random;

public class Malish extends Character {
    private boolean isListeningIntently;
    private Random random;

    public Malish(String name, FlatRoom room, Costume costume, int attentionLevel) {
        super(name, room, costume, Math.max(80, attentionLevel)); // Малыш всегда внимательный
        this.isListeningIntently = true;
        this.random = new Random();
    }

    public void listen() {
        if (isListeningIntently) {
            System.out.println(name + " слушает затаив дыхание");
            this.attentionLevel = Math.min(100, this.attentionLevel + 30);
            System.out.println(name + ": Всё это очень интересно!");
                if (random.nextDouble() > 0.7) {
                    System.out.println(name + " на мгновение забыл про Карлсона, спрятанного в ящике");
                } else {
                    System.out.println(name + " едва не забыл про Карлсонп");
                }
        } else {
            System.out.println(name + " ничего необычного не слышит :(");
        }
    }

    public void seeKarlson(Karlson karlson) {
        if (karlson.isInCostume()) {
            System.out.println(name + " видит Карлсона в " + room.getName() + "!");
            System.out.println("Но в то же время это не Карлсон!");
            System.out.println("Боже праведный, на кого он похож!");

            System.out.println("На нём: " + karlson.getCostume().toString());

            System.out.println("Он кажется маленькой кругленькой бойкой девочкой!");
            becomeAttented();
        } else {
            System.out.println(name + " видит Карлсона в " + room.getName());
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                (isListeningIntently ? " [внимательно слушает]" : " [отвлекся]");
    }
}