module com.xyc.fly {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.fly to javafx.fxml;
    exports com.fly;
    exports com.fly.game;
    opens com.fly.game to javafx.fxml;
    exports com.fly.game.net;
    opens com.fly.game.net to javafx.fxml;
}