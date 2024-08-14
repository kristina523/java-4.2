package com.example.tic_tac_toe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int currentPlayer = 1; // 1 - X, 0 - O
    private int[][] board = new int[3][3]; // 3x3 board
    private int playerXWins = 0;
    private int playerOWins = 0;

    private TextView playerXWinsText;
    private TextView playerOWinsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerXWinsText = findViewById(R.id.player_x_wins);
        playerOWinsText = findViewById(R.id.player_o_wins);

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final int row = i / 3;
            final int col = i % 3;
            Button button = (Button) gridLayout.getChildAt(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (board[row][col] == -1) {
                        board[row][col] = currentPlayer;
                        ((Button) v).setText(currentPlayer == 1 ? "X" : "O");
                        if (checkWin()) {
                            if (currentPlayer == 1) {
                                playerXWins++;
                                playerXWinsText.setText("Игрок X выигрывает: " + playerXWins);
                            } else {
                                playerOWins++;
                                playerOWinsText.setText("Игрок o выигрывает: " + playerOWins);
                            }
                            showToast("Игрок " + (currentPlayer == 1 ? "X" : "O") + " Выиграл!");
                            resetBoard();
                        } else if (isBoardFull()) {
                            showToast("Выиграл!");
                            resetBoard();
                        } else {
                            currentPlayer = 1 - currentPlayer;
                        }
                    }
                }
            });
        }

        if (savedInstanceState != null) {
            currentPlayer = savedInstanceState.getInt("Текущий игрок");
            playerXWins = savedInstanceState.getInt("Игрок X выигрывает");
            playerOWins = savedInstanceState.getInt("Игрок O выигрывает");

            playerXWinsText.setText("Игрок X выигрывает: " + playerXWins);
            playerOWinsText.setText("Игрок O выигрывает: " + playerOWins);

            // Restore the board state
            int[][] savedBoard = (int[][]) savedInstanceState.getSerializable("доска");
            if (savedBoard != null) {
                board = savedBoard;
                GridLayout gridLayout1 = findViewById(R.id.gridLayout);
                for (int i = 0; i < gridLayout1.getChildCount(); i++) {
                    Button button = (Button) gridLayout1.getChildAt(i);
                    int row = i / 3;
                    int col = i % 3;
                    if (board[row][col] == 1) {
                        button.setText("X");
                    } else if (board[row][col] == 0) {
                        button.setText("O");
                    } else {
                        button.setText("");
                    }
                }
            }
        } else {
            resetBoard();
        }
    }

    private boolean checkWin() {
        // Check rows, columns and diagonals
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != -1) {
                return true;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != -1) {
                return true;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != -1) {
            return true;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != -1) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = -1;
            }
        }
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setText("");
        }
        currentPlayer = 1;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Текущий игрок", currentPlayer);
        outState.putInt("Игрок X выигрывает", playerXWins);
        outState.putInt("Игрок O выигрывает", playerOWins);

        // Save the board state
        outState.putSerializable("доска", board);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentPlayer = savedInstanceState.getInt("Текущий игрок");
        playerXWins = savedInstanceState.getInt("Игрок X выигрывает");
        playerOWins = savedInstanceState.getInt("Игрок O выигрывает");

        playerXWinsText.setText("Игрок X выигрывает: " + playerXWins);
        playerOWinsText.setText("Игрок O выигрывает: " + playerOWins);

        // Restore the board state
        int[][] savedBoard = (int[][]) savedInstanceState.getSerializable("доска");
        if (savedBoard != null) {
            board = savedBoard;
            GridLayout gridLayout = findViewById(R.id.gridLayout);
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                Button button = (Button) gridLayout.getChildAt(i);
                int row = i / 3;
                int col = i % 3;
                if (board[row][col] == 1) {
                    button.setText("X");
                } else if (board[row][col] == 0) {
                    button.setText("O");
                } else {
                    button.setText("");
                }
            }
        }
    }
}
