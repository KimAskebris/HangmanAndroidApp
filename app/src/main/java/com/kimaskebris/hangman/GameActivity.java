package com.kimaskebris.hangman;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;
    private EditText input;
    private Button userButton;
    private TextView wordBox;
    private TextView msgBox;
    private TextView guessesBox;
    private TextView scoreBox;
    private TextView error;
    private String message;
    private boolean playAgain = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        input = findViewById(R.id.guess_input);
        userButton = findViewById(R.id.user_button);
        wordBox = findViewById(R.id.word_box);
        msgBox = findViewById(R.id.msg_box);
        scoreBox = findViewById(R.id.score_box);
        guessesBox = findViewById(R.id.guesses_box);
        toServer = ServerHandler.getToServerStream();
        fromServer = ServerHandler.getFromServerStream();
        initGame();
    }

    private void initGame() {
        Task mt = new Task();
        mt.execute();
    }

    public void buttonHandler(View view) {
        if (!playAgain) {
            guess(view);
        } else {
            newGame(view);
        }
    }

    private void guess(View view) {
        message = input.getText().toString();
        input.getText().clear();
        Task g = new Task();
        g.execute();
    }

    private void newGame(View view) {
        playAgain = false;
        userButton.setText("GUESS");
        input.setVisibility(View.VISIBLE);
        msgBox.setText(null);
        message = "SFKSMTRELR2423METERT242";
        Task play = new Task();
        play.execute();
    }

    private class Task extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> response = null;
            System.out.println(response);
            if(message != null) {
                try {
                    toServer.writeObject(message);
                    toServer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                response = (ArrayList<String>) fromServer.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(ArrayList<String> response) {
            if (response != null && response.size() > 0) {
                for (String msg : response) {
                    int i = msg.indexOf(" ");
                    String type = msg.substring(0, i);
                    String msgToUser = msg.substring(i + 1);
                    switch (type) {
                        case "msg":
                            msgBox.setText(msgToUser);
                            break;
                        case "word":
                            String toUser = msgToUser.replace("", " ").trim();
                            wordBox.setText(toUser);
                            break;
                        case "guesses":
                            guessesBox.setText(msgToUser);
                            break;
                        case "score":
                            scoreBox.setText(msgToUser);
                            break;
                        case "won":
                            userButton.setText("Play again");
                            input.setVisibility(View.GONE);
                            playAgain = true;
                        default:
                            break;
                    }
                }
            }
        }



    }


}
