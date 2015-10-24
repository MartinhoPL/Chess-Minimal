package com.example.ChessMinimal;

class Move {
    //wspó³rzêdne sk¹d
    private int x1;
    private int y1;
    // -||- dok¹d
    private int x2;
    private int y2;
    //zbita bierka
    private int beatenPiece;
    //i jej kolor
    private int beatenColor;

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getBeatenPiece() {
        return beatenPiece;
    }

    public void setBeatenPiece(int beatenPiece) {
        this.beatenPiece = beatenPiece;
    }

    public int getBeatenColor() {
        return beatenColor;
    }

    public void setBeatenColor(int beatenColor) {
        this.beatenColor = beatenColor;
    }

    public void setAll(int x1, int y1, int x2, int y2, int beatenPiece, int beatenColor) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.beatenPiece = beatenPiece;
        this.beatenColor = beatenColor;
    }

    public Move(){}

    public Move(int x1, int y1, int x2, int y2, int beatenPiece, int beatenColor) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.beatenPiece = beatenPiece;
        this.beatenColor = beatenColor;
    }
}
