public class practice3_3for_while {
    public static void main(String[] args) {
        int sumFor = 0;
        for (int i = 10; i <= 20; i++) {
            sumFor += i;
        }
        System.out.println("for文を使用した10から20までの和: " + sumFor);
        
        int sumWhile = 0;
        int j = 10;
        while (j <= 20) {
            sumWhile += j;
            j++;
        }
        System.out.println("while文を使用した10から20までの和: " + sumWhile);
    }
}
