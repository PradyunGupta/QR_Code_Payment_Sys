module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires com.google.zxing;
    requires com.google.zxing.javase;


    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
}