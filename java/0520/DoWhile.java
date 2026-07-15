public class DoWhile {
    public static void main(String[] args) {
        int i = 0;
        do {
            System.out.println("こんにちは" + i + "回目");
            i++;
        } while(i < 5);

        int k = 10;
        while (k < 5) {
            System.out.println("こんにちは" + k + "回目");
            k++;
        }

        for(int j = 0; j < 5; j++) {
            System.out.println("こんにちは" + j + "回目");
        }
    }
}
