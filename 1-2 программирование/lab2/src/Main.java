import ru.ifmo.se.pokemon.*;
import pokemons.*;

public class Main {
public static void main(String[] args) {
    Battle b = new Battle();
    Xerneas p1 = new Xerneas("1", 1);
    Swadloon p2 = new Swadloon("2", 1);
    Camerupt p3 = new Camerupt("3", 1);
    Leavanny p4 = new Leavanny("4", 2);
    Numel p5 = new Numel("5", 1);
    Sewaddle p6 = new Sewaddle("6", 1);
    b.addAlly(p1);
    b.addAlly(p2);
    b.addAlly(p3);
    b.addFoe(p4);
    b.addFoe(p5);
    b.addFoe(p6);
    b.go();
}
}