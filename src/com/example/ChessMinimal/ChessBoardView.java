package com.example.ChessMinimal;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class ChessBoardView {

    private TableRow[]tableRows;
    private ImageButton[][]imageButtons;
    private TableLayout tableLayout;
    private Data data;
    private Context context;
    private int startX;
    private int startY;
    private int imageResourceDrag;

    public ChessBoardView(TableLayout tableLayout, Data data, Context context){
        tableRows = new TableRow[5];
        imageButtons = new ImageButton[5][5];
        this.tableLayout = tableLayout;
        this.data = data;
        this.context = context;
    }
    
    public void createBoard(){
        int color;
        int imageResource;
        for(int i = 0; i < 5; i++)
        {
            createRow(i);
            for(int j = 0; j < 5; j++)
            {
                if((j + i*5) % 2 == 0){
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
            imageButton.setTag(imageResource);
        }
        imageButton.setBackgroundColor(color);
        imageButton.setMinimumHeight(100);
        imageButton.setMinimumWidth(100);
        imageButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = x;
                    startY = y;
                    imageResourceDrag = (Integer)imageButtons[x][y].getTag();
                    ClipData clipData = ClipData.newPlainText("", "");
                    View.DragShadowBuilder dsb = new View.DragShadowBuilder(view);
                    view.startDrag(clipData, dsb, view, 0);
                    imageButton.setImageResource(android.R.color.transparent);
                    //view.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        imageButton.setOnDragListener(new View.OnDragListener() {
            boolean containsDragable;

            public boolean onDrag(View v, DragEvent event) {
                int dragAction = event.getAction();
                View dragView = (View) event.getLocalState();
                if (dragAction == DragEvent.ACTION_DRAG_EXITED) {
                    containsDragable = false;
                } else if (dragAction == DragEvent.ACTION_DRAG_ENTERED) {
                    containsDragable = true;
                } else if (dragAction == DragEvent.ACTION_DRAG_ENDED) {
                    if (dropEventNotHandled(event)) {
                        dragView.setVisibility(View.VISIBLE);
                    }
                } else if (dragAction == DragEvent.ACTION_DROP && containsDragable) {
                    // TODO PATRYK, TU DAJ IF Z WALIDACJA
                    movePiece(startX, startY, x, y);

                    //dragView.setVisibility(View.VISIBLE);
                }
                return true;
            }

            private boolean dropEventNotHandled(DragEvent dragEvent) {
                return !dragEvent.getResult();
            }
        });

        imageButtons[x][y] = imageButton;
        tableRows[y].addView(imageButtons[x][y]);

    }

    private int getimageResourceForCell(int x, int y)
    {
        int imageResource = 0;
        switch (data.init_piece[x + y*5]){
            case 0:
                break;
            case 1:
                if(data.init_color[x + y*5] == 2) {
                    imageResource = R.drawable.blackpawn;
                } else
                {
                    imageResource =  R.drawable.whitepawn;
                }
                break;
            case 2:
                if(data.init_color[x + y*5] == 2) {
                    imageResource = R.drawable.blackknight;
                } else
                {
                    imageResource =  R.drawable.whiteknight;
                }
                break;
            case 3:
                if(data.init_color[x + y*5] == 2) {
                    imageResource =  R.drawable.blackbishop;
                } else
                {
                    imageResource =  R.drawable.whitebishop;
                }
                break;
            case 4:
                if(data.init_color[x + y*5] == 2) {
                    imageResource = R.drawable.blackrock;
                } else
                {
                    imageResource =  R.drawable.whiterook;
                }
                break;
            case 5:
                if(data.init_color[x + y*5] == 2) {
                    imageResource =  R.drawable.blackqueen;
                } else
                {
                    imageResource = R.drawable.whitequeen;
                }
                break;
            case 6:
                if(data.init_color[x + y*5] == 2) {
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
        if(data.piece[(x1) + ((y1)*5)] != 0)
        {
            //imageResources = getimageResourceForCell(x1, y1);
            imageButtons[x1][y1].setImageResource(android.R.color.transparent);
            imageButtons[x2][y2].setImageResource(imageResourceDrag);
            imageButtons[x2][y2].setTag(imageResourceDrag);

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