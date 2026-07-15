import java.util.Scanner;

public class InputExample {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("おなたのお名前は？");
        String name = in.nextLine();
        System.out.println(name + "さん、こんにちは。");
        in.close();
    }
}
