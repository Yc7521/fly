module com.fly {
    requires javafx.graphics;
    requires javafx.fxml;

    opens com.fly to javafx.fxml;

    exports com.fly;

    exports com.fly.game;
    exports com.fly.game.net;
    exports com.fly.game.UI;

    exports com.fly.entities;
    exports com.fly.entities.bullet;
    exports com.fly.entities.effect;
    exports com.fly.entities.env.wall;
    exports com.fly.entities.util;
    exports com.fly.entities.unit;
    exports com.fly.entities.unit.controller;

    opens com.fly.game to javafx.fxml;
    opens com.fly.game.net to javafx.fxml;
}