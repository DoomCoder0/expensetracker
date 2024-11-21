package org.example.controllers;

import com.google.gson.JsonObject;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.example.utils.ApiHandler;
import org.example.views.LoginView;
import org.example.views.SignUpView;

import java.io.IOException;
import java.net.HttpURLConnection;

public class SignUpController {
    private SignUpView signUpView;

    public SignUpController(SignUpView signUpView){
        this.signUpView = signUpView;
        initialize();
    }

    private void initialize(){
        signUpView.getRegisterButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!validateInput()) return;

                // retrieve user info to store into database
                String name = signUpView.getNameField().getText();
                String email = signUpView.getUsernameField().getText();
                String password = signUpView.getPasswordField().getText();

                JsonObject jsonData = new JsonObject();
                jsonData.addProperty("name", name);
                jsonData.addProperty("email", email);
                jsonData.addProperty("password", password);

                try {
                    // call on the spring user api to create the user
                    HttpURLConnection httpURLConnection =
                            ApiHandler.fetchApiResponse("/api/users",
                                    ApiHandler.RequestMethod.POST, jsonData);

                    // failed to create user
                    if(httpURLConnection.getResponseCode() != 200){
                        System.out.println("Error: " + httpURLConnection.getResponseCode());
                        return;
                    }

                    // successfully created user
                    // todo replace with dialog message and then take user back to login view
                    System.out.println("Successfully Created User");
                    new LoginView().show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        // change to login view
        signUpView.getLoginLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // switch view to login view
                new LoginView().show();
            }
        });
    }

    // validates the user input
    private boolean validateInput(){
        // missing email
        if(signUpView.getUsernameField().getText().isEmpty()){
            return false;
        }

        // email already exists

        // missing name
        if(signUpView.getNameField().getText().isEmpty()){
            return false;
        }

        // missing password
        if(signUpView.getPasswordField().getText().isEmpty()){
            return false;
        }

        // missing re password
        if(signUpView.getRePasswordField().getText().isEmpty()){
            return false;
        }

        // retyped password doesn't match
        if(!signUpView.getRePasswordField().getText().equals(signUpView.getPasswordField().getText())){
            return false;
        }

        return true;
    }
}
