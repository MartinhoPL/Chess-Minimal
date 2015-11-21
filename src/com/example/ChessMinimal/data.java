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
    private int fifty; // liczba ruchow (polruchow?) od bicia lub ruchu pionem ==50->remis
    //ograniczenia w szukaniu ruchow;
    private int max_time;
    private int max_depth;
    private boolean isCheck;
    //czas rozpoczecia szukania i maksymalny czas zaakonczenia
    private int start_time;
    private int stop_time;
    private Move lastMove;private int capture;

    Data() {
        isCheck = false;
        side = 1;
        xside = 2;
        fifty = 0;
        //    piece = new int[25];
        piece = Fixed.INIT_PIECE.clone();
        //    color = new int[25];
        color = Fixed.INIT_COLOR.clone();
        lastMove = new Move(-1, -1, -1, -1, -1, -1);
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

    public boolean getIsCheck() {
        return this.isCheck;
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

    public void undoMove(byte[] move, Data data) {
        if (move[2] == 0) {
            if (move[0] != move[1]) {
                data.piece[move[0]] = data.piece[move[1]];//wykonanie ruchu
                data.color[move[0]] = data.color[move[1]];
                data.color[move[1]] = 0;
                data.piece[move[1]] = 0;
                data.changeSite();
            }
        } else if (move[2] == 4){
            if (move[0] != move[1]) {
                data.piece[move[0]] = 1;//wykonanie ruchu
                data.color[move[0]] = data.color[move[1]];
                data.color[move[1]] = 0;
                data.piece[move[1]] = 0;
                data.changeSite();
            }
        }else {
            if (move[0] != move[1]) {
                data.piece[move[0]] = data.piece[move[1]];
                data.color[move[0]] = data.color[move[1]];
                data.color[move[1]] = move[3];
                if(data.color[move[0]] == 1) {
                    data.piece[move[1]] = 2;
                }
                else{
                    data.piece[move[1]] = 1;
                }
                data.changeSite();
            }
        }
    }

    public void makeMove(int x1, int y1, int x2, int y2) {
        if (x1 != x2 || y1 != y2) {
            lastMove.setAll(x1, y1, x2, y2, this.piece[x2 + y2 * Fixed.XWIDTH], this.color[x2 + y2 * Fixed.XWIDTH]);
            this.piece[x2 + y2 * Fixed.XWIDTH] = this.piece[x1 + y1 * Fixed.XWIDTH];//wykonanie ruchu
            this.color[x2 + y2 * Fixed.XWIDTH] = this.color[x1 + y1 * Fixed.XWIDTH];
            this.color[x1 + y1 * Fixed.XWIDTH] = 0;
            this.piece[x1 + y1 * Fixed.XWIDTH] = 0;
            changeSite();
        }
    }

    public void undoMove() {
        if (lastMove.getX1() != -1) {
            this.color[lastMove.getX1() + lastMove.getY1() * Fixed.XWIDTH] = this.color[lastMove.getX2() + lastMove.getY2() * Fixed.XWIDTH];
            this.piece[lastMove.getX1() + lastMove.getY1() * Fixed.XWIDTH] = this.piece[lastMove.getX2() + lastMove.getY2() * Fixed.XWIDTH];
            this.piece[lastMove.getX2() + lastMove.getY2() * Fixed.XWIDTH] = lastMove.getBeatenPiece();
            this.color[lastMove.getX2() + lastMove.getY2() * Fixed.XWIDTH] = lastMove.getBeatenColor();
            changeSite();
            lastMove.setX1(-1);
        }
    }

    public void promotion(int x, int y, int piece) {
        this.piece[Fixed.XWIDTH * y + x] = piece;
    }

    //na podstawie planszy z data wypelnia tablice posiotionNumber (dwuelementowa) przyklad tworzenia liczby:
    //dla data.color=0 positionNumber[0] *= 16 i nastepny przebieg petli
    //dla pozostalych positionNumber[0] = 16 * positionNumber[0] + (data.color[i] - 1] * 6 + data.piece[i]
    public void convertPositionToNumber(long positionNumber[], Data data){
        positionNumber[0] = 0;
        positionNumber[1] = 0;
        long simplePosition = 0;
        for (int i = 0; i < 16; i++) {
            positionNumber[0] *= 16;
            if(data.color[i] != 0) {
                simplePosition = data.piece[i] + (data.color[i] - 1) * 6;
            }else {
                simplePosition = 0;
            }
            positionNumber[0] += simplePosition;
        }
        for (int i = 16; i < 25; i++) {
            positionNumber[1] *= 16;
            if(data.color[i] != 0) {
                simplePosition = data.piece[i] + (data.color[i] - 1) * 6;
            }else {
                simplePosition = 0;
            }
            positionNumber[1] += simplePosition;
        }
    }

    //odwraca dzialanie powyzszej funkcji za pomoca tablicy positionNumber wypelnia plansze w data
    public void convertNumberToPosition(long positionNumber[], Data data) {
        long simplePosition = 0;
        for (int i = 24; i > 15; i++) {
            simplePosition = positionNumber[1] % 16;
            if(simplePosition < 7){
                data.piece[i] = ((int) simplePosition);
                if(simplePosition == 0){
                    data.color[i] = 0;
                }else {
                    data.color[i] = 1;
                }
            }else {
                data.piece[i] = ((int) simplePosition - 6);
                data.color[i] = 2;
            }
            positionNumber[1] /= 16;
        }
        for (int i = 15; i > -1; i++) {
            simplePosition = positionNumber[0] % 16;
            if(simplePosition < 7){
                data.piece[i] = ((int) simplePosition);
                if(simplePosition == 0){
                    data.color[i] = 0;
                }else {
                    data.color[i] = 1;
                }
            }else {
                data.piece[i] = ((int) simplePosition - 6);
                data.color[i] = 2;
            }
            positionNumber[0] /= 16;
        }
    }
    public static int calculateArrayIndexForCoords(int x, int y){
        return (y * 5) + x;
    }

    public void setCapture(int x, int y){
        this.capture = this.piece[Fixed.XWIDTH * y + x];
    }

    public int getCapture(){
        return this.capture;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }

    public int getMax_depth() {
        return max_depth;
    }

    public void setMax_depth(int max_depth) {
        this.max_depth = max_depth;
    }

    public boolean isCheck() {
        return isCheck;
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

    public void setColor(int[] color) {
        this.color = color;
    }

    public int[] getPiece() {
        return piece;
    }

    public void setPiece(int[] piece) {
        this.piece = piece;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public void setXside(int xside) {
        this.xside = xside;
    }

    public int getFifty() {
        return fifty;
    }

    public void setFifty(int fifty) {
        this.fifty = fifty;
    }

    public int getMax_time() {
        return max_time;
    }

    public void setMax_time(int max_time) {
        this.max_time = max_time;
    }
}
