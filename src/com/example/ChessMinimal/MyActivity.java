package com.example.ChessMinimal;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TableLayout;

public class MyActivity extends Activity {
    private GameStateEnum gameState;
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
        chessBoardView = new ChessBoardView(chessBoardLayout, data, this);
        chessBoardView.createBoard();
    }

    public void onStartButtonClick(View view) {
        gameState = GameStateEnum.STARTED;
    }

    public void onPauseButtonClick(View view) {
        if (gameState == GameStateEnum.STARTED) {
            gameState = GameStateEnum.PAUSED;
        } else {
            gameState = GameStateEnum.STARTED;
        }
    }
}

