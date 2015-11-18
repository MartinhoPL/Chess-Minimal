package com.example.ChessMinimal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

public class MyActivity extends Activity {
    public GameStateEnum gameState;
    private Data data;
    private ChessBoardView chessBoardView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        gameState = GameStateEnum.BLOCKED;
        super.onCreate(savedInstanceState);
        data=new Data();
        setContentView(R.layout.main);
        TableLayout chessBoardLayout = (TableLayout)findViewById(R.id.chessBoardLayout);
        TextView textView = (TextView) findViewById(R.id.textView);
        chessBoardView = new ChessBoardView(chessBoardLayout, data, this, textView);
        chessBoardView.createBoard();
    }

    public void onStartButtonClick(View view) {
       // gameState = GameStateEnum.STARTED;
        gameState = GameStateEnum.BLOCKED;
        data=new Data();
        setContentView(R.layout.main);
        TableLayout chessBoardLayout = (TableLayout)findViewById(R.id.chessBoardLayout);
        TextView textView = (TextView) findViewById(R.id.textView);
        chessBoardView = new ChessBoardView(chessBoardLayout, data, this, textView);
        chessBoardView.createBoard();
    }

    public void onPauseButtonClick(View view) {
        if (gameState == GameStateEnum.STARTED) {
            gameState = GameStateEnum.PAUSED;
        } else {
            gameState = GameStateEnum.STARTED;
        }
    }

    public void onSettingsButtonClick(View view) {
        setContentView(R.layout.settings);
    }

    public void onGiveUpButtonClick(View view) {
        if (gameState == GameStateEnum.BLOCKED)
            return;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.AreYouSure))
                    .setTitle(getString(R.string.GiveUp))
                    .setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (chessBoardView.whiteNext) {
                                        chessBoardView.endGame(1);
                                    } else {
                                        chessBoardView.endGame(-1);
                                    }
                                }
                            }
                    )
                    .setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            }
                    );


            AlertDialog alert = builder.create();
            alert.show();
        }

    public void onDrawButtonClick(View view) {
        if (gameState == GameStateEnum.BLOCKED)
            return;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.AreYouSure))
                .setTitle(getString(R.string.SuggestDraw)).setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        askForDraw();
                    }
                }
        )
                .setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }
                );


        AlertDialog alert = builder.create();
        alert.show();
    }

    private void askForDraw() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String message;
        if (chessBoardView.whiteNext)
        {
            message = getString(R.string.WhiteSuggestDraw);
        }
        else
        {
            message = getString(R.string.BlackSuggestDraw);
        }
        builder.setMessage("")
                .setMessage(message)
                .setTitle(getString(R.string.SuggestDraw))
                .setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                    chessBoardView.endGame(0);
                            }
                        }
                )
                .setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }
                );


        AlertDialog alert = builder.create();
        alert.show();
    }
    public void onButtonClick(View view) throws Exception{
//        Data data = new Data();
//        data.setPiece(TestUtils.loadPositionFromFile("pozycja.txt"));
//        data.setColor(TestUtils.loadPositionFromFile("kolor.txt"));
        ChessMechanic chessMechanic = new ChessMechanic(data);
        GameTree gameTree = new GameTree(data, chessMechanic);
//        gameTree.generateGameTree(1);
//        TestUtils.saveMovesToFile(gameTree.getMoves(), gameTree.getNodeChildrenNumber(), "testowanko.txt");
    }

}

