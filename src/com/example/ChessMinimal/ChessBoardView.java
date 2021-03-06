package com.example.ChessMinimal;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ChessBoardView {

    private final CharSequence whitesMoveString;
    private TableRow[]tableRows;
    public ImageButton[][]imageButtons;
    public AlfaBeta alfaBeta;
    public TableLayout tableLayout;
    public Data data;
    private Context context;
    private int startX;
    private int startY;
    public int imageResourceDrag;
    public TextView textView;
    public boolean whiteNext = true;
    public boolean lock = false;
    public byte[] move = {-1,-1,-1,-1};
    boolean firstMove = true;

    public ChessBoardView(TableLayout tableLayout, Data data, Context context, TextView textView){
        alfaBeta = new AlfaBeta(data);
        this.textView = textView;
        this.whitesMoveString = textView.getText();
        tableRows = new TableRow[Fixed.YHEIGHT];
        imageButtons = new ImageButton[Fixed.XWIDTH][Fixed.YHEIGHT];
        this.tableLayout = tableLayout;
        this.data = data;
        this.context = context;
    }
    
    public void createBoard(){
        int color;
        int imageResource;
        for(int i = 0; i < Fixed.YHEIGHT; i++)
        {
            createRow(i);
            for(int j = 0; j < Fixed.XWIDTH; j++)
            {
                if((j + i*Fixed.YHEIGHT) % 2 == 0){
                    color = Color.WHITE;
                }
                else {
                    color = Color.GRAY;
                }
                imageResource = getimageResourceForCell(j, i);
                createColumn(color, imageResource, j, i);
            }
            tableLayout.addView(tableRows[i]);
        }

    }

    public void restoreBoard(){
        int color;
        int imageResource;
        for(int i = 0; i < Fixed.YHEIGHT; i++)
        {
            createRow(i);
            for(int j = 0; j < Fixed.XWIDTH; j++)
            {
                if((j + i*Fixed.YHEIGHT) % 2 == 0){
                    color = Color.WHITE;
                }
                else {
                    color = Color.GRAY;
                }
                imageResource = (Integer) imageButtons[j][i].getTag();
                createColumn(color, imageResource, j, i);
            }
            tableLayout.addView(tableRows[i]);
        }

    }

    private void createRow(int x) {
        TableRow tableRow = new TableRow(context);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tableRows[x] = tableRow;
    }

    private void createColumn(int color, final int imageResource, final int x, final int y) {
        final ImageButton imageButton = new ImageButton(context);
        imageButton.setLayoutParams(new TableRow.LayoutParams(x));
        if (imageResource != -0) {
            imageButton.setImageResource(imageResource);

        }
        imageButton.setTag(imageResource);
        imageButton.setBackgroundColor(color);
        imageButton.setMinimumHeight(100);
        imageButton.setMinimumWidth(100);
        imageButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int s = motionEvent.getAction();
                if (s == MotionEvent.ACTION_DOWN) {
                    if (lock)
                        return true;
                    startX = x;
                    startY = y;
                    try {
                        imageResourceDrag = (Integer) imageButtons[x][y].getTag();
                        if (imageResourceDrag != -0) {
                            ClipData clipData = ClipData.newPlainText("", "");
                            View.DragShadowBuilder dsb = new View.DragShadowBuilder(view);
                            view.startDrag(clipData, dsb, view, 0);
                        }
                    } catch (Exception exception) {

                    } finally {
                        return true;
                    }
                } else {
                        return false;
                }
            }
        });

        imageButton.setOnDragListener(new View.OnDragListener() {
            boolean containsDragable;

            public boolean onDrag(View v, DragEvent event) {
                try {
                    if (lock)
                        return true;
                    int dragAction = event.getAction();
                    View dragView = (View) event.getLocalState();
                    if (dragAction == DragEvent.ACTION_DRAG_EXITED) {
                        containsDragable = false;
                    } else    if (dragAction == DragEvent.ACTION_DRAG_STARTED) {
                         imageButtons[startX][startY].setImageResource(android.R.color.transparent);
                    } else if (dragAction == DragEvent.ACTION_DRAG_ENTERED) {
                        containsDragable = true;
                    } else if (dragAction == DragEvent.ACTION_DRAG_ENDED) {
                        if (dropEventNotHandled(event)) {
                            movePiece(startX, startY, startX, startY);
                            dragView.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < Fixed.XWIDTH ; i++)
                        {
                            for (int j = 0 ; j < Fixed.YHEIGHT ; j++)
                            {
                                imageButtons[i][j].setImageResource((Integer)imageButtons[i][j].getTag());
                            }
                        }
                    } else if (dragAction == DragEvent.ACTION_DROP && containsDragable) {
                        validateMove(x, y);
                    }
                } catch (Exception ex) {
                } finally {
                    return true;
                }
            }

            private boolean dropEventNotHandled(DragEvent dragEvent) {
                return !dragEvent.getResult();
            }
        });

        imageButtons[x][y] = imageButton;
        tableRows[y].addView(imageButtons[x][y]);

    }

    private void validateMove(int x, int y) {
        switch (ChessMechanic.isMoveCorrect(startX, startY, x, y, data)){
            case FAIL:
                movePiece(startX, startY, startX, startY);
                break;
            case GOOD:
                movePiece(startX, startY, x, y);
                makeComputerMove();
                break;
            case PROMOTION:
                movePiece(startX, startY, x, y);
                makePromotion(x, y);
                break;
            case CHECKMATE:
                movePiece(startX, startY, x, y);
                if (whiteNext) {
                    endGame(1);
                }
                else
                {
                    endGame(-1);
                }
                break;
            case STALEMATE:
                movePiece(startX, startY, x, y);
                endGame(0);
                break;
            case CHECK:
                movePiece(startX, startY, x, y);
                makeComputerMove();
                break;
        }
    }

    public void endGame(final int result) { //-1 - wygrały białe, 0 - remis, 1 - wygrały czarne
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        switch(result)
        {
            case -1:
                builder.setMessage(context.getString(R.string.WhiteWon));

                break;
            case 0:
                builder.setMessage(context.getString(R.string.Draw));
                break;
            case 1:
                builder.setMessage(context.getString(R.string.BlackWon));

                break;
        }
        builder.setTitle(context.getString(R.string.GameEnds));

        AlertDialog alert = builder.create();
        alert.show();
        lock = true;
        ((MyActivity)context).gameState = GameStateEnum.BLOCKED;
    }

    private void makePromotion(final int x, final int y) {
        if (!whiteNext && Settings.Mode == 2)
        {
            imageButtons[x][y].setImageResource(R.drawable.whitequeen);
            imageButtons[x][y].setTag(R.drawable.whitequeen);
            CheckSituationAfterPromotion(ChessMechanic.promotion(x, y, 5, data));
            return;
        }
        if (whiteNext && Settings.Mode == 1)
        {
            imageButtons[x][y].setImageResource(R.drawable.blackqueen);
            imageButtons[x][y].setTag(R.drawable.blackqueen);
            CheckSituationAfterPromotion(ChessMechanic.promotion(x, y, 5, data));
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        final String[] items = {context.getString(R.string.Queen), context.getString(R.string.Bishop), context.getString(R.string.Pawn), context.getString(R.string.Rook)};
        final int[] imageResource = new int[1];
        builder.setTitle(context.getString(R.string.ChoosePiece))
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                if (y == 0) {
                                    imageResource[0] = R.drawable.whitequeen;
                                } else {
                                    imageResource[0] = R.drawable.blackqueen;
                                }
                                imageButtons[x][y].setImageResource(imageResource[0]);
                                imageButtons[x][y].setTag(imageResource[0]);
                                CheckSituationAfterPromotion(ChessMechanic.promotion(x, y, 5, data));
                                break;
                            case 1:
                                if (y == 0) {
                                    imageResource[0] = R.drawable.whitebishop;
                                } else {
                                    imageResource[0] = R.drawable.blackbishop;
                                }
                                imageButtons[x][y].setImageResource(imageResource[0]);
                                imageButtons[x][y].setTag(imageResource[0]);
                                CheckSituationAfterPromotion(ChessMechanic.promotion(x, y, 3, data));
                                break;
                            case 2:
                                if (y == 0) {
                                    imageResource[0] = R.drawable.whiteknight;
                                } else {
                                    imageResource[0] = R.drawable.blackknight;
                                }
                                imageButtons[x][y].setImageResource(imageResource[0]);
                                imageButtons[x][y].setTag(imageResource[0]);
                                CheckSituationAfterPromotion(ChessMechanic.promotion(x, y, 2, data));
                                break;
                            case 3:
                                if (y == 0) {
                                    imageResource[0] = R.drawable.whiterook;
                                } else {
                                    imageResource[0] = R.drawable.blackrock;
                                }
                                imageButtons[x][y].setImageResource(imageResource[0]);
                                imageButtons[x][y].setTag(imageResource[0]);
                                CheckSituationAfterPromotion(ChessMechanic.promotion(x, y, 4, data));
                                break;
                        }
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void CheckSituationAfterPromotion(MoveCorrectEnum promotion) {
        switch (promotion){
            case GOOD:
                makeComputerMove();
                break;
            case CHECKMATE:
                if (whiteNext) {
                    endGame(1);
                }
                else
                {
                    endGame(-1);
                }
                break;
            case STALEMATE:
                endGame(0);
                break;
            case CHECK:
                makeComputerMove();
                break;
        }
    }

    public void changeTextViewForNextTurn() {
        if (!whiteNext) {
            textView.setText(context.getString(R.string.BlacksMove));
        }
        else
        {
            textView.setText(whitesMoveString);
        }
    }

    private int getimageResourceForCell(int x, int y) {
        int imageResource = 0;
        switch (Fixed.INIT_PIECE[x + y*Fixed.XWIDTH]){
            case 0:
                break;
            case 1:
                if(Fixed.INIT_COLOR[x + y*Fixed.XWIDTH] == 2) {
                    imageResource = R.drawable.blackpawn;
                } else
                {
                    imageResource =  R.drawable.whitepawn;
                }
                break;
            case 2:
                if(Fixed.INIT_COLOR[x + y*Fixed.XWIDTH] == 2) {
                    imageResource = R.drawable.blackknight;
                } else
                {
                    imageResource =  R.drawable.whiteknight;
                }
                break;
            case 3:
                if(Fixed.INIT_COLOR[x + y*Fixed.XWIDTH] == 2) {
                    imageResource =  R.drawable.blackbishop;
                } else
                {
                    imageResource =  R.drawable.whitebishop;
                }
                break;
            case 4:
                if(Fixed.INIT_COLOR[x + y*Fixed.XWIDTH] == 2) {
                    imageResource = R.drawable.blackrock;
                } else
                {
                    imageResource =  R.drawable.whiterook;
                }
                break;
            case 5:
                if(Fixed.INIT_COLOR[x + y*Fixed.XWIDTH] == 2) {
                    imageResource =  R.drawable.blackqueen;
                } else
                {
                    imageResource = R.drawable.whitequeen;
                }
                break;
            case 6:
                if(Fixed.INIT_COLOR[x + y*Fixed.XWIDTH] == 2) {
                    imageResource =  R.drawable.blackking;
                } else
                {
                    imageResource =  R.drawable.whiteking;
                }
                break;
        }
        return imageResource;
    }

    public void setCellColor(int color, int x ,int y)
    {
        imageButtons[x][y].setBackgroundColor(color);
    }

    public void movePiece(int x1, int y1, int x2, int y2) {
            data.makeMove(x1, y1, x2, y2);
            imageButtons[x1][y1].setImageResource(android.R.color.transparent);
            imageButtons[x1][y1].setTag(-0);
            imageButtons[x2][y2].setImageResource(imageResourceDrag);
            imageButtons[x2][y2].setTag(imageResourceDrag);
            if (x1 != x2 || y1 != y2)
            {
                whiteNext = !whiteNext;
                changeTextViewForNextTurn();
                ((MyActivity)context).gameState = GameStateEnum.STARTED;
                if(firstMove) {
                    firstMove = !firstMove;
                    makeComputerMove();
                }
            }
    }

    public void makeComputerMove() {
        if (Settings.Mode == 1)
        {
            if (!whiteNext)
            {
                byte[] bestMove = alfaBeta.getBestMove(this.data);
                imageResourceDrag = (Integer) imageButtons[bestMove[0]% Fixed.XWIDTH][bestMove[0]/Fixed.XWIDTH].getTag();
                startX = bestMove[0]%Fixed.XWIDTH;
                startY = bestMove[0]/Fixed.XWIDTH;
                validateMove(bestMove[1]%Fixed.XWIDTH, bestMove[1]/Fixed.XWIDTH);
            }
        }
        else if (Settings.Mode == 2)
        {
            if (whiteNext)
            {
                alfaBeta = new AlfaBeta(data);
                byte[] bestMove = alfaBeta.getBestMove(this.data);
                imageResourceDrag = (Integer) imageButtons[bestMove[0]%Fixed.XWIDTH][bestMove[0]/Fixed.XWIDTH].getTag();
                startX = bestMove[0]%Fixed.XWIDTH;
                startY = bestMove[0]/Fixed.XWIDTH;
                validateMove(bestMove[1] % Fixed.XWIDTH, bestMove[1] / Fixed.XWIDTH);
            }
        }
    }

    public TableRow[] getTableRows() {
        return tableRows;
    }

    public void setTableRows(TableRow[] tableRows) {
        this.tableRows = tableRows;
    }

    public ImageButton[][] getImageButtons() {

        return imageButtons;
    }

    public void setImageButtons(ImageButton[][] imageButtons) {
        this.imageButtons = imageButtons;
    }

}
