package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean playerOneActive;
    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[9];
    private Button reset, playAgain;
    private int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    private int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private int rounds;
    private int playerOneScoreCount, playerTwoScoreCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerOneScore = findViewById(R.id.score_Player1);
        playerTwoScore = findViewById(R.id.score_Player2);
        playerStatus = findViewById(R.id.textStatus);
        reset = findViewById(R.id.btn_reset);
        playAgain = findViewById(R.id.btn_play_again);
        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "btn" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(this);
        }
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        playerOneActive = true;
        rounds = 0;
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                updatePlayerScore();
            }
        });
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        } else if (checkWinner()) {
            return;
        }
        String buttonID = getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1));

        if (playerOneActive) {
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#ffc34a"));
            gameState[gameStatePointer] = 0;
        } else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#70fc3a"));
            gameState[gameStatePointer] = 1;
        }
        rounds++;

        if (checkWinner()) {
            if (playerOneActive) {
                playerOneScoreCount++;
                updatePlayerScore();
                playerStatus.setText("Player-1 has won");
            } else {
                playerTwoScoreCount++;
                updatePlayerScore();
                playerStatus.setText("Player-2 has won");
            }
        } else if (rounds == 9) {
            playerStatus.setText("No Winner");
        } else {
            playerOneActive = !playerOneActive;
        }
    }
    private boolean checkWinner() {
        boolean winnerResults = false;
        for (int[] winningPositions : winningPositions) {
            if (gameState[winningPositions[0]] == gameState[winningPositions[1]] &&
                    gameState[winningPositions[1]] == gameState[winningPositions[2]] &&
                    gameState[winningPositions[0]] != 2) {
                winnerResults = true;
            }
        }
        return winnerResults;
    }
    private void playAgain() {
        rounds = 0;
        playerOneActive = true;
        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
        playerStatus.setText("Status");
    }
    private void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }
}
