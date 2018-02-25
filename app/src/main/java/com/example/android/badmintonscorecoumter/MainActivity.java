package com.example.android.badmintonscorecoumter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final String STATE_PLAYER_ONE_POINT = "playerOnePoint";
    static final String STATE_PLAYER_TWO_POINT = "playerTwoPoint";
    static final String STATE_CURRENT_GAME = "currentGame";
    static final String STATE_PLAYER_ONE_GAME = "playerOneGame";
    static final String STATE_PLAYER_TWO_GAME ="playerTwoGame";
    static final String STATE_GAMEOVER = "gameOver";

    private int pointPlayerOne = 0;
    private int pointPlayerTwo = 0;
    private int currentGame = 1;
    private int [] textViewPosition = new int[] {1,3,5}; // to store position of child text views in parent linear layout
    private int playerOneGame = 0;
    private int playerTwoGame = 0;
    private boolean gameover = false;

    @Override
    protected void onSaveInstanceState(Bundle savedInstantState) {
        savedInstantState.putInt(STATE_PLAYER_ONE_POINT,pointPlayerOne);
        savedInstantState.putInt(STATE_PLAYER_TWO_POINT,pointPlayerTwo);
        savedInstantState.putInt(STATE_CURRENT_GAME,currentGame);
        savedInstantState.putInt(STATE_PLAYER_ONE_GAME,playerOneGame);
        savedInstantState.putInt(STATE_PLAYER_TWO_GAME,playerTwoGame);
        savedInstantState.putBoolean(STATE_GAMEOVER,gameover);

        super.onSaveInstanceState(savedInstantState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            pointPlayerOne = savedInstanceState.getInt(STATE_PLAYER_ONE_POINT);
            pointPlayerTwo = savedInstanceState.getInt(STATE_PLAYER_TWO_POINT);
            currentGame = savedInstanceState.getInt(STATE_CURRENT_GAME);
            playerOneGame = savedInstanceState.getInt(STATE_PLAYER_ONE_GAME);
            playerTwoGame = savedInstanceState.getInt(STATE_PLAYER_TWO_GAME);
            gameover = savedInstanceState.getBoolean(STATE_GAMEOVER);
            showPlayerOneScore(pointPlayerOne);
            showPlayerTwoScore(pointPlayerTwo);

        }
        showMatchSummery("Game " + currentGame +" is playing");
    }

    //If player one won rally
    public void wonPlayerOne (View view) {

         if(currentGame<=3 && gameover==false) {
            pointPlayerOne += 1;
            showPlayerOneScore(pointPlayerOne);
            if (checkGameResult()) {
                playerOneGame += 1;
                nextGame("Player One Won");
                checkMatchResult();
            }
        }
    }
    //if player two won rally
    public void wonPlayerTwo (View view) {

        if (currentGame<=3 && gameover==false) {
            pointPlayerTwo += 1;
            showPlayerTwoScore(pointPlayerTwo);
            if (checkGameResult()) {
                playerTwoGame += 1;
                nextGame("Player Two Won");
                checkMatchResult();
            }
        }
    }

    // check result of current game & if it is over return true
    // after points are 20-20 winning margin is 2
    // after points are 29-29 winning margin is 1
    public boolean checkGameResult () {
        int scoreDifference = pointPlayerOne - pointPlayerTwo;

        if ((pointPlayerOne>29 || pointPlayerTwo>29) && (Math.abs(scoreDifference)>=1)) {
            return true;
        } else if ((pointPlayerOne>20 || pointPlayerTwo>20) && (Math.abs(scoreDifference)>=2)) {
            return true;
        } else {
            return false;
        }
    }

    // update player one points in score & table text views
    public void showPlayerOneScore(int score) {
        TextView playerOneScore = (TextView) findViewById(R.id.player_one_score);
        playerOneScore.setText("" + score);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.player_one_layout);
        TextView tableTextView = (TextView) linearLayout.getChildAt(textViewPosition[currentGame-1]);
        tableTextView.setText("" + score);
    }

    // update player two points in score & table text views
    public void showPlayerTwoScore(int score) {
        TextView playerTwoScore = (TextView) findViewById(R.id.player_two_score);
        playerTwoScore.setText("" + score);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.player_two_layout);
        TextView tableTextView = (TextView) linearLayout.getChildAt(textViewPosition[currentGame-1]);
        tableTextView.setText("" + score);
    }

    // update match summery text view
    public void showMatchSummery (String matchSummery) {
        TextView matchSummeryText = (TextView) findViewById(R.id.text_match_summery);
        matchSummeryText.setText(matchSummery);
    }

    // after one game finished update variables
    public void nextGame (String result) {

        Toast.makeText(getBaseContext(), result + " game " + currentGame, Toast.LENGTH_LONG).show();

        if (currentGame<3) {
            pointPlayerOne = 0;
            pointPlayerTwo = 0;
            currentGame +=1;

            showPlayerOneScore(pointPlayerOne);
            showPlayerTwoScore(pointPlayerTwo);
            showMatchSummery("Game " + currentGame + " is playing");
        }
    }

    //check match result, i.e. number of game won by player
    public void checkMatchResult () {
        if (playerOneGame==2) {
            showMatchSummery("Player One won : " + playerOneGame + " game by " + playerTwoGame);
            gameover=true;
        }

        if (playerTwoGame==2) {
            showMatchSummery("Player Two won : " + playerTwoGame + " game by " + playerOneGame);
            gameover=true;
        }
    }

    // if reset button clicked reset complete game
    public void resetMatch(View view) {
        pointPlayerOne = 0;
        pointPlayerTwo = 0;
        currentGame = 1;
        gameover=false;

        showPlayerOneScore(pointPlayerOne);
        showPlayerTwoScore(pointPlayerTwo);
        showMatchSummery("Game " + currentGame + " is playing");

        for (int i=0;i<3;i++) {
            LinearLayout layoutPlayerOne = (LinearLayout) findViewById(R.id.player_one_layout);
            TextView playerOneTable = (TextView) layoutPlayerOne.getChildAt(textViewPosition[i]);
            playerOneTable.setText("" + 0);

            LinearLayout layoutPlayerTwo = (LinearLayout) findViewById(R.id.player_two_layout);
            TextView playerTwoTable = (TextView) layoutPlayerTwo.getChildAt(textViewPosition[i]);
            playerTwoTable.setText("" + 0);
        }
    }
}
