package main.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.sql.SQLException;

public class ApplicationController {
    @FXML
    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    // Import the application's controls
    @FXML
    private Label invalidLoginCredentials;
    @FXML
    private Label invalidSignupCredentials;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField loginUsernameTextField;
    @FXML
    private TextField loginPasswordPasswordField;
    @FXML
    private TextField signUpNameTextField;
    @FXML
    private TextField signUpLoginTextField;
    @FXML
    private TextField signUpPasswordPasswordField;
    @FXML
    private TextField signUpRepeatPasswordPasswordField;
    @FXML
    private TextField signUpBirthdayTextField;
    @FXML
    private TextField signUpEmailTextField;
    @FXML
    private Button LoginButton;

    @FXML
    private Label myLabel;

    @FXML
    private RadioButton citizenshipButton1, citizenshipButton2;

    static User user;

    private int ageUser;
    private int citizenship;

    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onLoginButtonClick() throws SQLException, IOException {
        if (loginUsernameTextField.getText().isBlank() || loginPasswordPasswordField.getText().isBlank()) {
            invalidLoginCredentials.setText("The Login fields are required!");
            invalidLoginCredentials.setStyle(errorMessage);
            invalidSignupCredentials.setText("");

            if (loginUsernameTextField.getText().isBlank()) {
                loginUsernameTextField.setStyle(errorStyle);
            } else if (loginPasswordPasswordField.getText().isBlank()) {
                loginPasswordPasswordField.setStyle(errorStyle);
            }
        } else {
            user = new User();
            String login = loginUsernameTextField.getText();
            String pswd = loginPasswordPasswordField.getText();
            if (user.enter(login, pswd)) {
                user.setLogin(login);
                user.setPassword(pswd);
                if (user.admin(login, pswd) == 1) {
                    FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("adminPanel.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
                    Stage stage = new Stage();
                    stage.setTitle("Админ");
                    stage.setScene(scene);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(LoginButton.getScene().getWindow());
                    stage.show();
                } else {
                    FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("userPanel.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1000, 1400);
                    Stage stage = new Stage();
                    stage.setTitle("Выборы");
                    stage.setScene(scene);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(LoginButton.getScene().getWindow());
                    stage.show();
                }

            } else {
                loginUsernameTextField.setStyle(errorStyle);
                loginPasswordPasswordField.setStyle(errorStyle);

            }


        }
    }


    @FXML
    protected void onSignUpButtonClick() throws SQLException, IOException {
        String birthdateStr = signUpBirthdayTextField.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthdate, currentDate);
        ageUser = age.getYears();
        if (citizenshipButton1.isSelected()) {
            citizenshipButton1.getText();
            citizenship = 1;
        } else if (citizenshipButton2.isSelected()) {
            citizenshipButton2.getText();
            citizenship = 0;
        }
        if (signUpNameTextField.getText().isBlank() || signUpLoginTextField.getText().isBlank() || signUpPasswordPasswordField.getText().isBlank() || signUpRepeatPasswordPasswordField.getText().isBlank() || signUpBirthdayTextField.getText().isBlank() || signUpEmailTextField.getText().isBlank()) {
            invalidSignupCredentials.setText("Please fill in all fields!");
            invalidSignupCredentials.setStyle(errorMessage);
            invalidLoginCredentials.setText("");

            if (signUpNameTextField.getText().isBlank()) {
                signUpNameTextField.setStyle(errorStyle);
            } else if (signUpLoginTextField.getText().isBlank()) {
                signUpLoginTextField.setStyle(errorStyle);
            } else if (signUpPasswordPasswordField.getText().isBlank()) {
                signUpPasswordPasswordField.setStyle(errorStyle);
            } else if (signUpRepeatPasswordPasswordField.getText().isBlank()) {
                signUpRepeatPasswordPasswordField.setStyle(errorStyle);
            } else if (signUpBirthdayTextField.getText().isBlank()) {
                signUpBirthdayTextField.setStyle(errorStyle);
            } else if (signUpEmailTextField.getText().isBlank()) {
                signUpEmailTextField.setStyle(errorStyle);
            }
        } else if (ageUser < 18 || citizenship == 0) {
            invalidSignupCredentials.setText("You can't vote");
            invalidSignupCredentials.setStyle(errorMessage);
        } else if (signUpRepeatPasswordPasswordField.getText().equals(signUpPasswordPasswordField.getText())) {

            user = new User();

            String login = signUpLoginTextField.getText();
            String pswd = signUpPasswordPasswordField.getText();
            String email = signUpEmailTextField.getText();
            String fullName = signUpNameTextField.getText();// Example full name
            String[] nameParts = fullName.split(" "); // Split the full name by spaces
            String second_name = nameParts[0]; // First part is first name
            String first_name = nameParts[1]; // Second part is last name
            String patronymic = nameParts[2];

            System.out.println(first_name);
            System.out.println(second_name);
            System.out.println(patronymic);
            System.out.println(email);
            System.out.println(login);
            System.out.println(pswd);
            System.out.println(citizenship);
            System.out.println(ageUser);
            System.out.println(0);

            try {
                user.addUser(first_name, second_name, patronymic, email, login, pswd, citizenship, ageUser, 0);
                user.setName(first_name);
                user.setSecond_name(second_name);
                user.setPatronymic(patronymic);
                user.setEmail(email);
                user.setLogin(login);
                user.setPassword(pswd);
                user.setCitizenship(citizenship);
                user.setAge(ageUser);
            } catch (SQLException se) {
                System.out.println(se);
            }


            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("userPanel.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 1440);
            Stage stage = new Stage();
            stage.setTitle("Выборы");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(LoginButton.getScene().getWindow());
            stage.show();
            System.out.println("your set");
        } else {
            invalidSignupCredentials.setText("The Passwords don't match!");
            invalidSignupCredentials.setStyle(errorMessage);
            signUpPasswordPasswordField.setStyle(errorStyle);
            signUpRepeatPasswordPasswordField.setStyle(errorStyle);
            invalidLoginCredentials.setText("");
        }
    }

}