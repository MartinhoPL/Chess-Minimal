package com.example.ChessMinimal;


public class Data implements Cloneable{
    //mozna polaczyc color i piece np. biale parzyste czarne nieparyste 0 puste
    public int[] color; //1-bialy; 2-czarny; 0-pusty
    public int[] piece; //0-pusty; 1-pion; 2-kon; 3-goniec; 4-wieza; 5-hetman/krolowa; 6-krol
    //mozliwe zamiana na bool true-bialy; false-czarny
    private int side;  // strona wykonujaca ruch
    private int xside;  //strona niewykonujaca ruchu
    //private int castle;    // jak wyglada roszada?
    //czy jest podwojny ruch piona na planszy 5x5?
    //ograniczenia w szukaniu ruchow;
    private int max_time;
    private int max_depth;
    //czas rozpoczecia szukania i maksymalny czas zaakonczenia
    private int start_time;
    private int stop_time;
    private byte[] lastMove = {-1,0,0,0};
    private int capture;

    Data() {
        side = 1;
        xside = 2;
        piece = Fixed.INIT_PIECE.clone();
        color = Fixed.INIT_COLOR.clone();
    }

    @Override
    public Data clone() {
        Data temp = null;
        try {
            temp = (Data) super.clone();
        }catch (Exception e){
        }
        if(temp != null) {
            temp.piece = this.piece.clone();
            temp.color = this.color.clone();
            temp.lastMove = this.lastMove.clone();
        }
        return temp;
    }

    public int getPiece(int x, int y) {
        if (x < Fixed.XWIDTH && x > -1)
            if (y < Fixed.YHEIGHT && y > -1)
                return piece[x + y * Fixed.XWIDTH];
        return -1;
    }

    public int getSide() {
        return this.side;
    }

    public int getXside() {
        return this.xside;
    }

    public int getColor(int x, int y) {
        if (x < Fixed.XWIDTH && x > -1)
            if (y < Fixed.YHEIGHT && y > -1)
                return color[x + y * Fixed.XWIDTH];
        return -1;
    }

    public int setZero(int x, int y) {
        int piece = this.getPiece(x, y) + this.getColor(x, y) * 10;
        this.color[x + y * Fixed.XWIDTH] = 0;
        this.piece[x + y * Fixed.XWIDTH] = 0;
        return piece;
    }

    public void resetZero(int x, int y, int piece) {
        this.color[x + y * Fixed.XWIDTH] = piece / 10;
        this.piece[x + y * Fixed.XWIDTH] = piece % 10;
    }

    public void changeSite() {
        int a = this.xside;
        this.xside = this.side;
        this.side = a;
    }

    public void makeMove(byte[] move) {
        if (move[2] != 4) {
            if (move[0] != move[1]) {
                this.piece[move[1]] = this.piece[move[0]];//wykonanie ruchu
                this.color[move[1]] = this.color[move[0]];
                this.color[move[0]] = 0;
                this.piece[move[0]] = 0;
                this.changeSite();
            }
        } else {
            if (move[0] != move[1]) {
                this.piece[move[1]] = 5;//wykonanie ruchu
                this.color[move[1]] = this.color[move[0]];
                this.color[move[0]] = 0;
                this.piece[move[0]] = 0;
                this.changeSite();
            }
        }
    }

    public void undoMove(byte[] move) {
        if(move[2] != 4){
            if (move[0] != move[1]) {
                this.piece[move[0]] = this.piece[move[1]];
                this.color[move[0]] = this.color[move[1]];
                this.piece[move[1]] = move[3];
                if(move[3] == 0){
                    this.color[move[1]] = 0;
                }else if(this.color[move[0]] == 1) {
                    this.color[move[1]] = 2;
                } else{
                    this.color[move[1]] = 1;
                }
                this.changeSite();
            }
        } else {
            if (move[0] != move[1]) {
                this.piece[move[0]] = 1;//wykonanie ruchu
                this.color[move[0]] = this.color[move[1]];
                this.piece[move[1]] = move[3];
                if (move[3] == 0) {
                    this.color[move[1]] = 0;
                } else {
                    if (this.color[move[0]] == 1) {
                        this.color[move[1]] = 2;
                    } else {
                        this.color[move[1]] = 1;
                    }
                }
                this.changeSite();
            }
        }
    }

    public void makeMove(int x1, int y1, int x2, int y2) {
        if (x1 != x2 || y1 != y2) {
            lastMove[0] = (byte)(x1 + y1 * Fixed.XWIDTH);
            lastMove[1] = (byte)(x2 + y2 * Fixed.XWIDTH);
            lastMove[2] = 0;
            lastMove[3] = (byte)this.piece[lastMove[1]];
            this.piece[x2 + y2 * Fixed.XWIDTH] = this.piece[x1 + y1 * Fixed.XWIDTH];//wykonanie ruchu
            this.color[x2 + y2 * Fixed.XWIDTH] = this.color[x1 + y1 * Fixed.XWIDTH];
            this.color[x1 + y1 * Fixed.XWIDTH] = 0;
            this.piece[x1 + y1 * Fixed.XWIDTH] = 0;
            changeSite();
        }
    }

    public void undoMove() {
        if (lastMove[0] != -1) {
            this.color[lastMove[0]] = this.color[lastMove[1]];
            this.piece[lastMove[0]] = this.piece[lastMove[1]];
            this.piece[lastMove[1]] = lastMove[3];
            if (lastMove[3] == 0) {
                this.color[lastMove[1]] = 0;
            } else if (this.color[lastMove[0]] == 1) {
                this.color[lastMove[1]] = 2;
            } else {
                this.color[lastMove[1]] = 1;
            }
            changeSite();
            lastMove[0] = -1;
        }
    }

    public void promotion(int x, int y, int piece) {
        this.piece[Fixed.XWIDTH * y + x] = piece;
    }

    public int[] convertPositionToNumber(){
        int positionNumber[] = {0,0,0}; //stworzenie tablicy na wynik
        int simplePosition = 0; //nota pojedynczej pozycji
        for (int i = 0; i < 8; i++) { //stworzenie 1. czesci
            //mno�nik 16 bo 2 kolory * 6 figur + pole puste = 13 mozliwosci
            //a najblizsza (wieksza) potega 2 to 16
            positionNumber[0] *= 16;
            if(this.color[i] != 0) { //jezeli pole nie puste
                //numer figury +6 dla czarnych lub +0 dla bialych
                simplePosition = this.piece[i] + (this.color[i] - 1) * 6;
            }else {
                simplePosition = 0;
            }
            //zwieksz kontener o wyliczona wartosc
            positionNumber[0] += simplePosition;
        }
        for (int i = 8; i < 14; i++) { //2. czesc analogicznie jak wyzej
            positionNumber[1] *= 16;
            if(this.color[i] != 0) {
                simplePosition = this.piece[i] + (this.color[i] - 1) * 6;
            }else {
                simplePosition = 0;
            }
            positionNumber[1] += simplePosition;
        }
        for (int i = 14; i < 20; i++) { //3. czesc analogcznie jak wyzej
            positionNumber[2] *= 16;
            if(this.color[i] != 0) {
                simplePosition = this.piece[i] + (this.color[i] - 1) * 6;
            }else {
                simplePosition = 0;
            }
            positionNumber[2] += simplePosition;
        }
        return positionNumber.clone();
    }

    public static int calculateArrayIndexForCoords(int x, int y){
        return (y * Fixed.XWIDTH) + x;
    }

    public void setCapture(int x, int y){
        this.capture = this.piece[Fixed.XWIDTH * y + x];
    }

    public int getCapture(){
        return this.capture;
    }

    public int getMax_depth() {
        return max_depth;
    }

    public void setMax_depth(int max_depth) {
        this.max_depth = max_depth;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getStop_time() {
        return stop_time;
    }

    public void setStop_time(int stop_time) {
        this.stop_time = stop_time;
    }

    public int[] getColor() {
        return color;
    }

    public int[] getPiece() {
        return piece;
    }

    public int getMax_time() {
        return max_time;
    }

    public void setMax_time(int max_time) {
        this.max_time = max_time;
    }
}
