package com.example.ChessMinimal;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

public class ChessBoardView {

    private TableRow[]tableRows;
    private ImageButton[][]imageButtons;
    private TableLayout tableLayout;
    private Data data;
    private Context context;
    private int startX;
    private int startY;
    private int imageResourceDrag;
    private ChessMechanic chessMechanic;
    public ChessBoardView(TableLayout tableLayout, Data data, Context context){
        tableRows = new TableRow[5];
        imageButtons = new ImageButton[5][5];
        this.tableLayout = tableLayout;
        this.data = data;
        this.context = context;
        chessMechanic = new ChessMechanic(data);
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

        }
        imageButton.setTag(imageResource);
        imageButton.setBackgroundColor(color);
        imageButton.setMinimumHeight(100);
        imageButton.setMinimumWidth(100);
        imageButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int s = motionEvent.getAction();
                if (s == MotionEvent.ACTION_DOWN) {
                    startX = x;
                    startY = y;
                    try {
                        imageResourceDrag = (Integer) imageButtons[x][y].getTag();
                        if (imageResourceDrag != -0) {
                            ClipData clipData = ClipData.newPlainText("", "");
                            View.DragShadowBuilder dsb = new View.DragShadowBuilder(view);
                            view.startDrag(clipData, dsb, view, 0);
                           // imageButton.setImageResource(android.R.color.transparent);
                            //view.setVisibility(View.INVISIBLE);
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
                        for (int i = 0; i < 5 ; i++)
                        {
                            for (int j = 0 ; j < 5 ; j++)
                            {
                                imageButtons[i][j].setImageResource((Integer)imageButtons[i][j].getTag());
                            }
                        }
                    } else if (dragAction == DragEvent.ACTION_DROP && containsDragable) {
                        // TODO PATRYK, TU DAJ IF Z WALIDACJA
                        if (chessMechanic.isMoveCorrect(startX, startY, x, y)) {
                            movePiece(startX, startY, x, y);
                        } else {
                            movePiece(startX, startY, startX, startY);
                        }
//                    dragView.setVisibility(View.VISIBLE);
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
            data.makeMove(x1, y1, x2, y2);
            //imageResources = getimageResourceForCell(x1, y1);
            imageButtons[x1][y1].setImageResource(android.R.color.transparent);
            imageButtons[x1][y1].setTag(-0);
            imageButtons[x2][y2].setImageResource(imageResourceDrag);
            imageButtons[x2][y2].setTag(imageResourceDrag);
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
