package main.app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import db.DbConnection;
import model.User;

public class AdminPanelController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private ListView<String> electionsListView;

    @FXML

    private  ListView<String> questionableElectionsListView;
    @FXML
    private ListView<String> resultsListView;
    @FXML
    private Label election;

    @FXML
    private Label questionableElection;

    @FXML
    private TextField voteTitleTextField;
    @FXML
    private TextField voteDateTextField;
    @FXML
    private TextField candidateTextField;

    @FXML
    private TextField questionTextField;

    @FXML
    private TextField candidateJobTextField;

    @FXML
    private RadioButton questionableButton1, questionableButton2;

    private int questionable;

    String currentElection;
    String[] elections;

    String[] questionableElections;

    String[] results;
    String[] results2;
    String votingString = ":";

    String questionableElectionsString = ":";

    String currentQuestionableElection;

    String questionableVoting;
    String resultString = ":";
    String resultString2 = ":";

    int candidateId;
    int votingId;

    int questionableVotingId;

    int questionId;

    String candidate;

    int question;
    int votingID;
    String voting;
    int voices;

    int voices_yes;
    int voices_no;

    String candidateName;
    String questionName;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query = "SELECT title FROM Votings";
            PreparedStatement stmt = connection.conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                votingString = resultSet.getString("title") + ":" + votingString;


            }
            elections = votingString.split(":");
            for (int i = 0; i < elections.length; i++) {
                System.out.println(i + "  " + elections[i]);
            }

        } catch (SQLException ex) {
            System.out.println(ex);

        }

        electionsListView.getItems().addAll(elections);
        electionsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                currentElection = electionsListView.getSelectionModel().getSelectedItem();
                election.setText(currentElection);
            }
        });
    }


    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void AddVoteButtonClick() {
        try {
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String title = voteTitleTextField.getText();
            String date = voteDateTextField.getText();
            if (questionableButton1.isSelected()) {
                questionableButton1.getText();
                questionable = 1;
            } else if (questionableButton2.isSelected()) {
                questionableButton2.getText();
                questionable = 0;
            }
            String query = "INSERT INTO Votings (title, end_date, is_questionable) VALUES ('" + title + "','" + date + "'," + questionable + ")";
            PreparedStatement preparedStmt = connection.conn.prepareStatement(query);
            preparedStmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex);

        }
    }

    @FXML
    protected void addCandidatesButtonClick() {
        try {
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String job = candidateJobTextField.getText();
            String fullName = candidateTextField.getText();// Example full name
            String[] nameParts = fullName.split(" "); // Split the full name by spaces
            String second_name = nameParts[0]; // First part is first name
            String first_name = nameParts[1]; // Second part is last name
            String patronymic = nameParts[2];
            String query = "INSERT INTO Candidates (name,second_name, patronymic, job) VALUES ('" + first_name + "','" + second_name + "','" + patronymic + "','" + job + "')";
            PreparedStatement preparedStmt = connection.conn.prepareStatement(query);


//            preparedStmt.setString(1, candidateName);
            preparedStmt.executeUpdate();
            System.out.println("Done 1");
            connection.conn.close();

        } catch (SQLException ex) {
            System.out.println(ex);

        }

        try {
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String fullName = candidateTextField.getText();// Example full name
            String[] nameParts = fullName.split(" "); // Split the full name by spaces
            String second_name = nameParts[0]; // First part is first name
            String first_name = nameParts[1]; // Second part is last name
            String patronymic = nameParts[2];
            String query1 = "SELECT CandidateId FROM Candidates WHERE  name = '" + first_name + "' and second_name = '"+second_name+"' and patronymic = '"+patronymic+"' ";
            PreparedStatement stmt = connection.conn.prepareStatement(query1);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                candidateId = resultSet.getInt("CandidateId");
                System.out.println(candidateId);
            }
            System.out.println("Done 2");
        } catch (SQLException ex) {
            System.out.println(ex);

        }

        try {
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String votingIdQuery = "Select VotingId from Votings where title = '" + currentElection + "'";
            PreparedStatement stmtVotingId = connection.conn.prepareStatement(votingIdQuery);
            ResultSet resultSetVotingId = stmtVotingId.executeQuery();
            while (resultSetVotingId.next()) {
                votingId = resultSetVotingId.getInt("VotingId");
                System.out.println(votingId);
            }
            System.out.println("Done 4");
        } catch (SQLException ex) {
            System.out.println(ex);

        }

        try {
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query = "INSERT INTO ElectionSystem (candidate_id, voting_id) VALUES ('" + candidateId + "','" + votingId + "')";
            PreparedStatement preparedStmt = connection.conn.prepareStatement(query);
            preparedStmt.executeUpdate();
            System.out.println("Done 5");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }


    @FXML
    protected void getVotingButtonClick() {
        try {
            votingString = " ";
            electionsListView.getItems().clear();
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query = "SELECT title FROM Votings where is_questionable = 0";
            PreparedStatement stmt = connection.conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                votingString = resultSet.getString("title") + ":" + votingString;


            }
            elections = votingString.split(":");
            for (int i = 0; i < elections.length; i++) {
                System.out.println(i + "  " + elections[i]);
            }

        } catch (SQLException ex) {
            System.out.println(ex);

        }

        electionsListView.getItems().addAll(elections);
        electionsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                currentElection = electionsListView.getSelectionModel().getSelectedItem();
                election.setText(currentElection);
            }
        });
    }



    @FXML
    protected void addQuestionsButtonClick() {
        try {
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            questionName = questionTextField.getText();
            String query = "INSERT INTO Questions (question) VALUES ('" + questionName + "')";
            PreparedStatement preparedStmt = connection.conn.prepareStatement(query);


//            preparedStmt.setString(1, candidateName);
            preparedStmt.executeUpdate();
            System.out.println("Done 1");
            connection.conn.close();

        } catch (SQLException ex) {
            System.out.println(ex);

        }

        try {
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query1 = "SELECT QuestionId FROM Questions WHERE  question = '" + questionName + "'";

            PreparedStatement stmt = connection.conn.prepareStatement(query1);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet);
                questionId = resultSet.getInt("QuestionId");
                System.out.println(candidateId);
            }
            System.out.println("Done 2");
        } catch (SQLException ex) {
            System.out.println(ex);

        }

        try {
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            System.out.println(currentQuestionableElection);
            String votingIdQuery = "Select VotingId from Votings where title = '" + currentQuestionableElection + "'";

            PreparedStatement stmtVotingId = connection.conn.prepareStatement(votingIdQuery);
            ResultSet resultSetVotingId = stmtVotingId.executeQuery();
            while (resultSetVotingId.next()) {
                questionableVotingId = resultSetVotingId.getInt("VotingId");
                System.out.println(questionableVotingId);
            }
            System.out.println("Done 3");
        } catch (SQLException ex) {
            System.out.println(ex);

        }

        try {
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            System.out.println(questionId);
            String query = "INSERT INTO QuestionElectionSystem (questionId, votingId) VALUES (" + questionId + "," + questionableVotingId + ")";
            PreparedStatement preparedStmt = connection.conn.prepareStatement(query);
            preparedStmt.executeUpdate();
            System.out.println("Done 4");
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        try {
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query = "INSERT INTO QuestionUses (question_id, voting_id) VALUES (" + questionId + "," + questionableVotingId + ")";
            PreparedStatement preparedStmt = connection.conn.prepareStatement(query);
            preparedStmt.executeUpdate();
            System.out.println("Done 5");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }


    @FXML
    protected void getQuestionableVotingButtonClick() {
        try {
            questionableElectionsString = " ";
            questionableElectionsListView.getItems().clear();
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query = "SELECT title FROM Votings where is_questionable = 1";
            PreparedStatement stmt = connection.conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("title") + "     1");
                questionableElectionsString = resultSet.getString("title") + " " + questionableElectionsString;


            }
            questionableElections = questionableElectionsString.split(" ");
            for (int i = 0; i < questionableElections.length; i++) {
                System.out.println(i + "  " + questionableElections[i]);
            }

        } catch (SQLException ex) {
            System.out.println(ex);

        }

        questionableElectionsListView.getItems().addAll(questionableElections);
        questionableElectionsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                currentQuestionableElection = questionableElectionsListView.getSelectionModel().getSelectedItem();
                questionableElection.setText(currentQuestionableElection);
            }
        });
    }

    @FXML
    protected void getResultButtonClick() {
        try {
            resultString = ":";
            resultString2 = ":";
            resultsListView.getItems().clear();
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query = "\n" +
                    "SELECT name, title, voices FROM ElectionSystem LEFT Join Candidates on candidate_id = Candidates.CandidateId LEFT Join Votings on voting_id = Votings.VotingId;";
            PreparedStatement stmt = connection.conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                candidate = resultSet.getString("name");
                voting = resultSet.getString("title");
                voices = resultSet.getInt("voices");
                resultString = "" + candidate + "," + voting + "," + voices + ":" + resultString;
            }
            results = resultString.split(":");
            for (int i = 0; i < results.length; i++) {
                System.out.println(i + "  " + results[i]);
            }

            String query2 = "\n" +
                    "SELECT questionId, votingId, voices_yes, voices_no FROM QuestionElectionSystem;";
            PreparedStatement stmt2 = connection.conn.prepareStatement(query2);
            ResultSet resultSet2 = stmt2.executeQuery();
            while (resultSet2.next()) {
//                question = resultSet2.getString("question");
//                questionableVoting = resultSet2.getString("title");
                int i = 1;
                System.out.println(i);
                i = i+1;
                question = resultSet2.getInt("questionId");
                votingID = resultSet2.getInt("votingId");
                voices_yes = resultSet2.getInt("voices_yes");
                voices_no = resultSet2.getInt("voices_no");
                resultString2 = "" + question + "," + votingID + "," + voices_yes + "," +voices_no+":" + resultString2;
            }
            results2 = resultString2.split(":");
            for (int i = 0; i < results2.length; i++) {
                System.out.println(i + "  " + results2[i]);
            }

        } catch (SQLException ex) {
            System.out.println(ex);

        }

        resultsListView.getItems().addAll(results);
        resultsListView.getItems().addAll(results2);
    }


}
