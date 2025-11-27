//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        String s = "()";
        char cur_symbol;
        for(int i = 0; i < s.length(); i++) {
            cur_symbol = s.charAt(i);
            System.out.println(cur_symbol);
        }
    }
}