class Rectangle {
    int width;  // 幅
    int height; // 高さ

    Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    int area() {
        return width * height;
    }
}

public class Main {
    public static void main(String[] args) {
        Rectangle rect = new Rectangle(5, 3);
        System.out.println("Area of the rectangle: " + rect.area());
    }
}