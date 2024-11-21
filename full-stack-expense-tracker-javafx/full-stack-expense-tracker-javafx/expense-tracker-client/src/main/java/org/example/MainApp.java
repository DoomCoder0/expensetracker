package org.example;

import org.example.utils.ViewNavigator;
import org.example.views.LoginView;

public class MainApp extends Application implements MainlyApp {
    @Override
    public void start(Stage stage) throws Exception {
        ViewNavigator.setMainStage(stage);

        // the login view will be the first to be shown when the app is ran
        LoginView loginView = new LoginView();
        loginView.show();
    }
}
