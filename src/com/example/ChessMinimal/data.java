package com.example.ChessMinimal;


public class Data {
    //mozna polaczyc color i piece np. biale parzyste czarne nieparyste 0 puste
    public int[] color; //1-bialy; 2-czarny; 0-pusty
    public int[] piece; //0-pusty; 1-pion; 2-kon; 3-goniec; 4-wieza; 5-hetman/krolowa; 6-krol
    //mozliwe zamiana na bool true-bialy; false-czarny
    private int side;  // strona wykonujaca ruch
    private int xside;  //strona niewykonujaca ruchu
    //private int castle;    // jak wyglada roszada?
    //czy jest podwojny ruch piona na planszy 5x5?
    //private int fifty; // liczba ruchow (polruchow?) od bicia lub ruchu pionem ==50->remis
    //ograniczenia w szukaniu ruchow;
    private int max_time;
    private int max_depth;
    private boolean isCheck;
    //czas rozpoczecia szukania i maksymalny czas zaakonczenia
    private int start_time;
    private int stop_time;
    private Move lastMove;
    public int[] init_color = {
            2, 2, 2, 2, 2,
            2, 2, 2, 2, 2,
            0, 0, 0, 0, 0,
            1, 1, 1, 1, 1,
            1, 1, 1, 1, 1,
    };
    public int[] init_piece = {// wie�a ko� goniec hetman kr�l
            4, 2, 3, 5, 6,
            1, 1, 1, 1, 1,
            0, 0, 0, 0, 0,
            1, 1, 1, 1, 1,
            4, 2, 3, 5, 6,
    };

    Data() {
        isCheck = false;
        side = 1;
        xside = 2;
        fifty = 0;
        piece = new int[25];
        piece = init_piece.clone();
        color = new int[25];
        color = init_color.clone();
        lastMove = new Move(-1, -1, -1, -1, -1, -1);
    }

    public int getPiece(int x, int y) {
        if (x < 5 && x > -1)
            if (y < 5 && y > -1)
                return piece[x + y * 5];
        return -1;
    }

    public int getSide() {
        return this.side;
    }

    public int getXside() {
        return this.xside;
    }

    public int getColor(int x, int y) {
        if (x < 5 && x > -1)
            if (y < 5 && y > -1)
                return color[x + y * 5];
        return -1;
    }

    public boolean getIsCheck() {
        return this.isCheck;
    }

    public int setZero(int x, int y) {
        int piece = this.getPiece(x, y) + this.getColor(x, y) * 10;
        this.color[x + y * 5] = 0;
        this.piece[x + y * 5] = 0;
        return piece;
    }

    public void resetZero(int x, int y, int piece) {
        this.color[x + y * 5] = piece / 10;
        this.piece[x + y * 5] = piece % 10;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public void changeSite() {
        int a = this.xside;
        this.xside = this.side;
        this.side = a;
    }

    public void makeMove(byte[] move, Data data) {
        if (move[2] != 4) {
            if (move[0] != move[1]) {
                data.piece[move[1]] = data.piece[move[0]];//wykonanie ruchu
                data.color[move[1]] = data.color[move[0]];
                data.color[move[0]] = 0;
                data.piece[move[0]] = 0;
                data.changeSite();
            }
        } else {
            if (move[0] != move[1]) {
                data.piece[move[1]] = move[3];//wykonanie ruchu
                data.color[move[1]] = data.color[move[0]];
                data.color[move[0]] = 0;
                data.piece[move[0]] = 0;
                data.changeSite();
            }
        }
    }

    public void makeMove(int x1, int y1, int x2, int y2) {
        if (x1 != x2 || y1 != y2) {
            lastMove.setAll(x1, y1, x2, y2, this.piece[x2 + y2 * 5], this.color[x2 + y2 * 5]);
            this.piece[x2 + y2 * 5] = this.piece[x1 + y1 * 5];//wykonanie ruchu
            this.color[x2 + y2 * 5] = this.color[x1 + y1 * 5];
            this.color[x1 + y1 * 5] = 0;
            this.piece[x1 + y1 * 5] = 0;
            changeSite();
        }
    }

    public void undoMove() {
        if (lastMove.getX1() != -1) {
            this.color[lastMove.getX1() + lastMove.getY1() * 5] = this.color[lastMove.getX2() + lastMove.getY2() * 5];
            this.piece[lastMove.getX1() + lastMove.getY1() * 5] = this.piece[lastMove.getX2() + lastMove.getY2() * 5];
            this.piece[lastMove.getX2() + lastMove.getY2() * 5] = lastMove.getBeatenPiece();
            this.color[lastMove.getX2() + lastMove.getY2() * 5] = lastMove.getBeatenColor();
            changeSite();
            lastMove.setX1(-1);
        }
    }

    public void promotion(int x, int y, int piece) {
        this.piece[5 * y + x] = piece;
    }
}
