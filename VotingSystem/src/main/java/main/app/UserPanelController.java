package main.app;

import db.DbConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserPanelController implements Initializable {
    @FXML
    private ListView<String> electionsListView;
    @FXML
    private ListView<String> candidatesListView;
    @FXML
    private ListView<String> questionsListView;

    @FXML
    private ListView<String> questionVoteListView;

    @FXML
    private Button cancelButton;

    @FXML
    private Button getCandidatesVotesButton;
    @FXML
    private Button getQuestionsVotesButton;

    @FXML
    private Label election;

    @FXML
    private Label vote;

    @FXML
    private Label addVoiceLabel;

    @FXML
    private RadioButton answerButton1, answerButton2;

    int answer;

    String[] elections;
    String currentElection;

    String[] candidates;
    String currentCandidate;

    String[] questionVotes;


    String[] questions;


    String currentQuestion;

    String currentQuestionVote;

    String candidateString = "";
    String votingString = "";

    String questionVotingString = "";
    String questionString = "";

    int votingId;
    int userId;
    int candidateId;

    int questionId;

    int is_voted = 0;
    String login;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        try {
//            DbConnection connection = new DbConnection();
//            connection.dbConnection();
//            String query = "SELECT title FROM votings";
//            PreparedStatement stmt = connection.conn.prepareStatement(query);
//            ResultSet resultSet = stmt.executeQuery();
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString("title") + "     1");
//                votingString = resultSet.getString("title") + ";" + votingString;
//
//
//            }
//            elections = votingString.split(";");
//            for (int i = 0; i < elections.length; i++) {
//                System.out.println(i + "  " + elections[i]);
//            }
//
//        } catch (SQLException ex) {
//            System.out.println(ex);
//
//        }

//        try{
//            DbConnection connection = new DbConnection();
//            connection.dbConnection();
//            String query = "SELECT name FROM candidates";
//            PreparedStatement stmt = connection.conn.prepareStatement(query);
//            ResultSet resultSet = stmt.executeQuery();
//            while ( resultSet.next() ) {
//                candidateString = candidateString +","+ resultSet.getString("name");
//            }
//            candidates = candidateString.split(",");
//
//        }
//        catch(SQLException ex){
//            System.out.println(ex);
//
//        }

//        electionsListView.getItems().addAll(elections);

//        electionsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                currentElection = electionsListView.getSelectionModel().getSelectedItem();
//                election.setText(currentElection);
//            }
//        });
    }
//candidate vote controller

    @FXML
    protected void getCandidatesVotesButtonClick() {
        try {
            votingString = "";
            electionsListView.getItems().clear();
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query = "SELECT title FROM Votings Where is_questionable = 0";
            PreparedStatement stmt = connection.conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                votingString = resultSet.getString("title") + ";" + votingString;


            }
            elections = votingString.split(";");
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
    protected void addVoiceButtonClick() {
        try {
            is_voted = 0;
            login = ApplicationController.user.getLogin();
            System.out.println(login);
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query1 = "SELECT is_voted FROM UsersVotes INNER JOIN Users ON UsersVotes.user_id = Users.UserId WHERE (UsersVotes.user_id =( SELECT UserId FROM Users WHERE login = '" + login + "'))AND(UsersVotes.voting_id = (SELECT VotingId FROM Votings WHERE title = '" + currentElection + "'))";

            PreparedStatement stmt = connection.conn.prepareStatement(query1);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet);
                is_voted = resultSet.getInt("is_voted");
                System.out.println(is_voted);
            }
            if (is_voted == 1) {
                addVoiceLabel.setText("You voice already");
                String errorMessage = String.format("-fx-text-fill: RED;");
                addVoiceLabel.setStyle(errorMessage);
                System.out.println("You voice already");

            } else {

                String userIdQuery = "Select UserId from Users where login = '" + login + "'";
                String votingIdQuery = "Select VotingId from Votings where title = '" + currentElection + "'";
                String fullName = currentCandidate;// Example full name
                String[] nameParts = fullName.split(" "); // Split the full name by spaces
                String second_name = nameParts[0]; // First part is first name
                String first_name = nameParts[1]; // Second part is last name
                String patronymic = nameParts[2];


                String candidateIdQuery = "Select CandidateId from Candidates where name = '" + first_name + "' and second_name = '" + second_name + "' and patronymic = '" + patronymic + "'";
                PreparedStatement stmtUserId = connection.conn.prepareStatement(userIdQuery);
                ResultSet resultSetUserId = stmtUserId.executeQuery();
                while (resultSetUserId.next()) {
                    userId = resultSetUserId.getInt("UserId");
                    System.out.println(userId);
                }
                PreparedStatement stmtVotingId = connection.conn.prepareStatement(votingIdQuery);
                ResultSet resultSetVotingId = stmtVotingId.executeQuery();
                while (resultSetVotingId.next()) {
                    votingId = resultSetVotingId.getInt("VotingId");
                    System.out.println(votingId);
                }

                PreparedStatement stmtCandidateId = connection.conn.prepareStatement(candidateIdQuery);
                ResultSet resultSetCandidateId = stmtCandidateId.executeQuery();
                while (resultSetCandidateId.next()) {
                    candidateId = resultSetCandidateId.getInt("CandidateId");
                    System.out.println(candidateId);
                }

                String query2 = "Insert into UsersVotes (user_id,voting_id,candidate_id, is_voted) Values (" + userId + ", " + votingId + "," + candidateId + "," + 1 + ")";
                PreparedStatement preparedStmt = connection.conn.prepareStatement(query2);
                preparedStmt.executeUpdate();
                String query3 = "UPDATE ElectionSystem set voices = voices+1 WHERE candidate_id = " + candidateId + " and voting_id = " + votingId + "";
                PreparedStatement preparedStmt2 = connection.conn.prepareStatement(query3);
                preparedStmt2.executeUpdate();
                addVoiceLabel.setText("You voice has been add");
                String successMessage = String.format("-fx-text-fill: GREEN;");
                addVoiceLabel.setStyle(successMessage);
            }
        } catch (SQLException ex) {
            System.out.println(ex);


        }
    }

    @FXML
    protected void getCandidatesButtonClick() {
        try {
            candidatesListView.getItems().clear();
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query = "SELECT name, second_name, patronymic, job FROM Candidates LEFT JOIN ElectionSystem ON Candidates.CandidateId = ElectionSystem.candidate_id Where voting_id = (Select VotingId From Votings Where title = '" + currentElection + "');";
            PreparedStatement stmt = connection.conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            candidateString = "";

            while (resultSet.next()) {

                candidateString = resultSet.getString("second_name") + " " + resultSet.getString("name") + " " + resultSet.getString("patronymic") + " " + resultSet.getString("job") + ";" + candidateString;
            }
            candidates = candidateString.split(";");
            candidatesListView.getItems().addAll(candidates);

            candidatesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    currentCandidate = candidatesListView.getSelectionModel().getSelectedItem();
                }
            });

        } catch (SQLException ex) {
            System.out.println(ex);

        }
    }

    //    question vote controller


    @FXML
    protected void getQuestionsVotesButtonClick() {
        try {
            questionVotingString = "";
            questionVoteListView.getItems().clear();
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query = "SELECT title FROM Votings Where is_questionable = 1";
            PreparedStatement stmt = connection.conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                questionVotingString = resultSet.getString("title") + ";" + questionVotingString;


            }
            questionVotes = questionVotingString.split(";");
            for (int i = 0; i < questionVotes.length; i++) {
                System.out.println(i + "  " + questionVotes[i]);
            }

        } catch (SQLException ex) {
            System.out.println(ex);

        }

        questionVoteListView.getItems().addAll(questionVotes);
        questionVoteListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                currentQuestionVote = questionVoteListView.getSelectionModel().getSelectedItem();
                vote.setText(currentQuestionVote);
            }
        });
    }

    @FXML
    protected void addYNButtonClick() {
        try {
            is_voted = 0;
            login = ApplicationController.user.getLogin();
            System.out.println(login);
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query1 = "SELECT is_voted FROM UsersVotes INNER JOIN Users ON UsersVotes.user_id = Users.UserId WHERE (UsersVotes.user_id =( SELECT UserId FROM Users WHERE login = '" + login + "'))AND(UsersVotes.voting_id = (SELECT VotingId FROM Votings WHERE title = '" + currentQuestionVote + "'))";

            PreparedStatement stmt = connection.conn.prepareStatement(query1);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet);
                is_voted = resultSet.getInt("is_voted");
                System.out.println(is_voted);
            }
            if (is_voted == 1) {
                addVoiceLabel.setText("You voice already");
                String errorMessage = String.format("-fx-text-fill: RED;");
                addVoiceLabel.setStyle(errorMessage);
                System.out.println("You voice already");

            } else {


                String userIdQuery = "Select UserId from Users where login = '" + login + "'";
                String votingIdQuery = "Select VotingId from Votings where title = '" + currentQuestionVote + "'";
                String questionIdQuery = "Select QuestionId from Questions where question = '" + currentQuestion + "'";


                PreparedStatement stmtUserId = connection.conn.prepareStatement(userIdQuery);
                ResultSet resultSetUserId = stmtUserId.executeQuery();
                while (resultSetUserId.next()) {
                    userId = resultSetUserId.getInt("UserId");
                    System.out.println(userId);
                }
                PreparedStatement stmtVotingId = connection.conn.prepareStatement(votingIdQuery);
                ResultSet resultSetVotingId = stmtVotingId.executeQuery();
                while (resultSetVotingId.next()) {
                    votingId = resultSetVotingId.getInt("VotingId");
                    System.out.println(votingId);
                }

                PreparedStatement stmtQuestionId = connection.conn.prepareStatement(questionIdQuery);
                ResultSet resultSetQuestionId = stmtQuestionId.executeQuery();
                while (resultSetQuestionId.next()) {
                    questionId = resultSetQuestionId.getInt("QuestionId");
                    System.out.println(questionId);
                }

                if (answerButton1.isSelected()) {
                    answerButton1.getText();
                    answer = 1;
                } else if (answerButton2.isSelected()) {
                    answerButton2.getText();
                    answer = 0;
                }

                String query2 = "Insert into UsersVotes (user_id,voting_id,question_id,answer, is_voted) Values (" + userId + ", " + votingId + "," + questionId + "," + answer + "," + 1 + ")";
                PreparedStatement preparedStmt = connection.conn.prepareStatement(query2);
                preparedStmt.executeUpdate();
                String query3;
                if (answer == 1) {
                    query3 = "UPDATE QuestionElectionSystem set voices_yes = voices_yes+1 WHERE questionId = "+questionId+" and votingId = "+votingId+"";
                } else {
                    query3 = "UPDATE QuestionElectionSystem set voices_no = voices_no+1 WHERE questionId = "+questionId+" and votingId = "+votingId+"";
                }
                PreparedStatement preparedStmt2 = connection.conn.prepareStatement(query3);
                preparedStmt2.executeUpdate();
                addVoiceLabel.setText("You voice has been add");
                String successMessage = String.format("-fx-text-fill: GREEN;");
                addVoiceLabel.setStyle(successMessage);
            }
        } catch (SQLException ex) {
            System.out.println(ex);


        }
    }

    @FXML
    protected void getQuestionButtonClick() {
        try {
            questionsListView.getItems().clear();
            DbConnection connection = new DbConnection();
            connection.dbConnection();
            String query = "SELECT question FROM Questions LEFT JOIN QuestionElectionSystem ON Questions.QuestionId = QuestionElectionSystem.QuestionId Where votingId = (Select VotingId From Votings Where title = '" + currentQuestionVote + "');";
            PreparedStatement stmt = connection.conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            questionString = ";";

            while (resultSet.next()) {

                questionString = resultSet.getString("question") + ";" + questionString;
            }
            questions = questionString.split(";");
            questionsListView.getItems().addAll(questions);

            questionsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    currentQuestion = questionsListView.getSelectionModel().getSelectedItem();
                }
            });

        } catch (SQLException ex) {
            System.out.println(ex);

        }
    }

    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
