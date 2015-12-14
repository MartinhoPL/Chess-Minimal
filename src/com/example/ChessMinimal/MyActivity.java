package com.example.ChessMinimal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

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
        setTextViews();
        if (Settings.Mode == 2)
        {
            chessBoardView.lock = true;
        }
    }

    private void setTextViews() {
        switch (Settings.Mode)
        {
            case 0:
                ((TextView) findViewById(R.id.textView2)).setText(getString(R.string.Human));
                ((TextView) findViewById(R.id.textView3)).setText(getString(R.string.Human));
                break;
            case 1:
                ((TextView) findViewById(R.id.textView2)).setText(getString(R.string.Human));
                ((TextView) findViewById(R.id.textView3)).setText("Komputer");
                break;
            case 2:
                ((TextView) findViewById(R.id.textView3)).setText(getString(R.string.Human));
                ((TextView) findViewById(R.id.textView2)).setText("Komputer");
                break;
        }
    }

    public void onStartButtonClick(View view) {
        gameState = GameStateEnum.STARTED;

        data=new Data();
        setContentView(R.layout.main);
        TableLayout chessBoardLayout = (TableLayout)findViewById(R.id.chessBoardLayout);
        TextView textView = (TextView) findViewById(R.id.textView);
        chessBoardView = new ChessBoardView(chessBoardLayout, data, this, textView);
        chessBoardView.createBoard();
        setTextViews();
        if (Settings.Mode == 2)
        {
            chessBoardView.lock = false;
            //chessBoardView.alfaBeta = new AlfaBeta(chessBoardView.data);
            byte[] bestMove = chessBoardView.alfaBeta.getBestMove(chessBoardView.data);
            chessBoardView.imageResourceDrag = (Integer) chessBoardView.imageButtons[bestMove[0]%Fixed.XWIDTH][bestMove[0]/Fixed.XWIDTH].getTag();
            chessBoardView.movePiece(bestMove[0] % Fixed.XWIDTH, bestMove[0] / Fixed.XWIDTH, bestMove[1] % Fixed.XWIDTH, bestMove[1]/Fixed.XWIDTH);
        }
    }

    public void onPauseButtonClick(View view) {
        if (gameState == GameStateEnum.STARTED) {
            gameState = GameStateEnum.PAUSED;
        } else {
            gameState = GameStateEnum.STARTED;
        }
    }

    public void onSettingsButtonClick(View view) {
        int s = Settings.Mode;
        setContentView(R.layout.settings);

        ((Spinner)findViewById(R.id.spinner2)).setSelection(Settings.Mode);
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
        if (Settings.Mode != 0) {
            return;
        }
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


    public void onAcceptClick(View view) {
        Settings.Mode = ((Spinner)findViewById(R.id.spinner2)).getSelectedItemPosition();
        setContentView(R.layout.main);
        RestoreBoard();
        chessBoardView.lock = false;
        setTextViews();
        chessBoardView.makeComputerMove();
    }

    private void applyChangesInGameMode() {
        if (Settings.Mode == 1 && !chessBoardView.whiteNext)
        {
            //chessBoardView.alfaBeta = new AlfaBeta(chessBoardView.data);
            byte[] bestMove = chessBoardView.alfaBeta.getBestMove(chessBoardView.data);
            chessBoardView.imageResourceDrag = (Integer) chessBoardView.imageButtons[bestMove[0]% Fixed.XWIDTH][bestMove[0]/Fixed.XWIDTH].getTag();
            chessBoardView.movePiece(bestMove[0] % Fixed.XWIDTH, bestMove[0] / Fixed.XWIDTH, bestMove[1] % Fixed.XWIDTH, bestMove[1]/Fixed.XWIDTH);
        }
        else if (Settings.Mode == 2 && chessBoardView.whiteNext)
        {
            //chessBoardView.alfaBeta = new AlfaBeta(chessBoardView.data);
            byte[] bestMove = chessBoardView.alfaBeta.getBestMove(chessBoardView.data);
            chessBoardView.imageResourceDrag = (Integer) chessBoardView.imageButtons[bestMove[0]%Fixed.XWIDTH][bestMove[0]/Fixed.XWIDTH].getTag();
            chessBoardView.movePiece(bestMove[0] % Fixed.XWIDTH, bestMove[0] / Fixed.XWIDTH, bestMove[1] % Fixed.XWIDTH, bestMove[1]/Fixed.XWIDTH);
        }
    }

    private void RestoreBoard() {
        TableLayout chessBoardLayout = (TableLayout)findViewById(R.id.chessBoardLayout);
        chessBoardView.tableLayout = chessBoardLayout;
        chessBoardView.textView = (TextView) findViewById(R.id.textView);
        chessBoardView.changeTextViewForNextTurn();
        chessBoardView.restoreBoard();
    }

    public void undoMove(View view) {
        chessBoardView.undoMove();
    }

//    public void superClick(View view) {
////        GameTree gameTree = new GameTree(data);
////        gameTree.generateGameTree(3);
//        AlfaBeta alfaBeta = new AlfaBeta(data);
//        alfaBeta.getBestMove();
//    }
}

