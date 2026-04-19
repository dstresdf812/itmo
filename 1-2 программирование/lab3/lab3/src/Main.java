import characters.*;
import characters.Character;
import extra.*;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ClothingItem ShortSkirt = new ClothingItem("Короткая юбка");
        ClothingItem LongSkirt = new ClothingItem("Длинная юбка");
        ClothingItem TShirt = new ClothingItem("Футболка");
        ClothingItem Fartuk = new ClothingItem("Фартук");
        ClothingItem Jeans = new ClothingItem("Джинсы");

        ArrayList<ClothingItem> KarlsonClothingItems = new ArrayList<ClothingItem>();
        KarlsonClothingItems.add(LongSkirt);
        KarlsonClothingItems.add(TShirt);
        ClothingState KarlsonClothingState = new ClothingState(true);
        Costume KarlsonsCostume = new Costume(KarlsonClothingItems, KarlsonClothingState);

        ArrayList<ClothingItem> FrekenBokClothingItems = new ArrayList<ClothingItem>();
        FrekenBokClothingItems.add(Fartuk);
        FrekenBokClothingItems.add(LongSkirt);
        ClothingState FrekenBokClothingState = new ClothingState(true);
        Costume FrekenBoksCostume = new Costume(FrekenBokClothingItems, FrekenBokClothingState);

        ArrayList<ClothingItem> MalishClothingItems = new ArrayList<ClothingItem>();
        MalishClothingItems.add(Jeans);
        MalishClothingItems.add(TShirt);
        ClothingState MalishClothingState = new ClothingState(false);
        Costume MalishsCostume = new Costume(MalishClothingItems, MalishClothingState);

        ArrayList<ClothingItem> PeksClothingItems = new ArrayList<ClothingItem>();
        PeksClothingItems.add(LongSkirt);
        PeksClothingItems.add(TShirt);
        ClothingState PeksClothingState = new ClothingState(true);
        Costume PeksCostume = new Costume(PeksClothingItems, PeksClothingState);


        ArrayList<Character> charactersHW = new ArrayList<>();
        ArrayList<Character> charactersKC = new ArrayList<>();

        Flat flat = new Flat(new Random().nextBoolean());
        Flat.Hallway HW = flat.new Hallway(charactersHW);
        Flat.Kitchen KC = flat.new Kitchen(charactersKC);

        Character karlson = new Karlson("Карлсон", HW, KarlsonsCostume,60,false);
        characters.Character malish = new characters.Malish("Малыш", HW, MalishsCostume,90);
        characters.Character pek = new characters.GospodinPek("Господин Пек", KC, PeksCostume,90);
        characters.Character freken = new characters.FrekenBok("Фрекен Бок", KC, FrekenBoksCostume,90, true);

        malish.doSomething();
        Random random = new Random();

    }
}