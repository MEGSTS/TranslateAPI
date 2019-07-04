import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        API_Query Q = new API_Query();
        Scanner in = new Scanner(System.in);
        System.out.println("Введите текст для перевода : ");
        String textRU = in.nextLine();
        String textEN = Q.PostQuery(textRU);
        textEN = Q.ParsersXML(textEN);
        System.out.println("Перевод :\n" + textEN);
    }
}
