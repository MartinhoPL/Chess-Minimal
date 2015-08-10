package com.example.ChessMinimal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MyActivity extends Activity {
    private GameStateEnum gameState;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        gameState = GameStateEnum.BLOCKED;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onStartButtonClick(View view) {
        gameState = GameStateEnum.STARTED;
    }

    public void onPauseButtonClick(View view) {
        if (gameState == GameStateEnum.STARTED)
        {
            gameState = GameStateEnum.PAUSED;
        }
        else
        {
            gameState = GameStateEnum.STARTED;
        }
    }
}

