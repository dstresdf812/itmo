import characters.*;
import characters.Character;
import extra.*;
import exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        runFullScenario();
        System.out.println(":(");
    }

    private static void runFullScenario() {
        Random random = new Random();

        ClothingItem longSkirt = new ClothingItem("Длинная бархатная юбка",true);
        ClothingItem cloakFront = new ClothingItem("Тюлевая накидка (передняя)", true);
        ClothingItem cloakBack = new ClothingItem("Тюлевая накидка (задняя)", true);
        ClothingItem masqueradeCostume = new ClothingItem("Маскарадный костюм Бетан",  true);

        ClothingItem fartuk = new ClothingItem("Фартук", false);
        ClothingItem regularSkirt = new ClothingItem("Обычная юбка", false);

        ClothingItem jeans = new ClothingItem("Джинсы", false);
        ClothingItem tshirt = new ClothingItem("Футболка",  false);
        ClothingItem sweater = new ClothingItem("Свитер",  false);


        ArrayList<ClothingItem> karlsonClothingItems = new ArrayList<>();
        karlsonClothingItems.add(longSkirt);
        karlsonClothingItems.add(cloakFront);
        karlsonClothingItems.add(cloakBack);
        karlsonClothingItems.add(masqueradeCostume);
        ClothingState karlsonClothingState = new ClothingState(true);
        Costume karlsonsCostume = new Costume(
                "Маскарадный костюм Бетан",
                karlsonClothingItems,
                karlsonClothingState
        );

        ArrayList<ClothingItem> frekenBokClothingItems = new ArrayList<>();
        frekenBokClothingItems.add(fartuk);
        frekenBokClothingItems.add(regularSkirt);
        frekenBokClothingItems.add(sweater);
        ClothingState frekenBokClothingState = new ClothingState(false);
        Costume frekenBoksCostume = new Costume(
                "Домашний костюм",
                frekenBokClothingItems,
                frekenBokClothingState
        );

        ArrayList<ClothingItem> malishClothingItems = new ArrayList<>();
        malishClothingItems.add(jeans);
        malishClothingItems.add(tshirt);
        malishClothingItems.add(sweater);
        ClothingState malishClothingState = new ClothingState(false);
        Costume malishsCostume = new Costume(
                "Повседневная одежда",
                malishClothingItems,
                malishClothingState
        );

        ArrayList<ClothingItem> peksClothingItems = new ArrayList<>();
        peksClothingItems.add(regularSkirt);
        peksClothingItems.add(sweater);
        ClothingState peksClothingState = new ClothingState(false);
        Costume peksCostume = new Costume(
                "Домашняя одежда",
                peksClothingItems,
                peksClothingState
        );

        Flat flat = new Flat(random.nextBoolean());

        Flat.Kitchen kitchen = flat.getKitchen();
        Flat.Hallway hallway = flat.getHallway();
        Flat.LivingRoom livingRoom = flat.getLivingRoom();

        Karlson karlson = new Karlson("Карлсон", livingRoom, karlsonsCostume, 45, true);
        karlson.setHidingPlace("ящик в гостиной");

        Malish malish = new Malish("Малыш", livingRoom, malishsCostume, 95);
        GospodinPek pek = new GospodinPek("Господин Пек", kitchen, peksCostume, 20);
        FrekenBok freken = new FrekenBok("Фрекен Бок", kitchen, frekenBoksCostume, 25);
        freken.startCooking("жареные пельмени");

        malish.doSomething();

        freken.doSomething();
        pek.doSomething();

        flat.openDoorToKitchen();
        System.out.println("\n" + karlson.getName() + " выбирается из укрытия...");
        karlson.reveal();

        karlson.putOnCostume();

        System.out.println("\n" + karlson.getName() + " пытается пройти в прихожую...");
        try {
            karlson.moveTo(hallway);
        } catch (MovementException e) {
            System.out.println(e.getMessage());
            System.out.println("Но Карлсон всё равно оказывается в прихожей!");
            livingRoom.removeCharacter(karlson);
            hallway.addCharacter(karlson);
            karlson.makeSound("громкий скрип в прихожей", 85, SoundType.CREAK);
        }

        System.out.println("\n" + malish.getName() + " смотрит в прихожую...");
        malish.seeKarlson(karlson);

        karlson.doSomething();
        karlson.walkAround();
        karlson.gotoKitchen();
        List<Character> kitchenCharacters = kitchen.getCharacters();
        boolean anyoneNoticed = false;

        for (Character c : kitchenCharacters) {
            System.out.print("- " + c.getName() + ": ");
            if (c.getAttentionLevel() > 40) {
                System.out.println("замечает что-то странное!");
                c.notice(karlson);
                anyoneNoticed = true;
            } else {
                System.out.println("слишком занят, чтобы заметить");
            }
        }

        if (!anyoneNoticed) {
            System.out.println("\nНи фрекен Бок, ни господин Пек еще ничего не заметили!");
        }

        try {
            if (karlson.getRoom() == hallway && flat.isDoorToKitchenOpened()) {
                System.out.println("\nКарлсон пытается войти на кухню...");
                karlson.moveTo(kitchen);
            }
        } catch (MovementException e) {
            System.out.println(e.getMessage());
            System.out.println("Костюм путается в ногах, мешая ходить!");
            System.out.println("Но эта маленькая бойкая девочка неумолимо приближается к кухне!");
        }

    }
}