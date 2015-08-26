package com.example.ChessMinimal;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

public class ChessBoardView {

    private TableRow[]tableRows;
    private ImageButton[][]imageButtons;
    private TableLayout tableLayout;
    private Data data;
    private Context context;

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

    private void createColumn(int color, int imageResource, int x, int y) {

        ImageButton imageButton = new ImageButton(context);
        imageButton.setLayoutParams(new TableRow.LayoutParams(x));
        if (imageResource != -0) {
            imageButton.setImageResource(imageResource);
        }
        imageButton.setBackgroundColor(color);
        imageButton.setMinimumHeight(100);
        imageButton.setMinimumWidth(100);

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
        int imageResources;
        if(data.piece[x1 + y1*5] != 0)
        {
            imageResources = getimageResourceForCell(x1, y1);
            imageButtons[x1][y1].setImageResource(android.R.color.transparent);
            imageButtons[x2][y2].setImageResource(imageResources);
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
