package com.example.ChessMinimal;


public class Data {
    //mozna polaczyc color i piece np. biale parzyste czarne nieparyste 0 puste
    public int[] color; //1-bialy; 2-czarny; 0-pusty
    public int[] piece; //0-pusty; 1-pion; 2-kon; 3-goniec; 4-wieza; 5-hetman/krolowa; 6-krol
    //mozliwe zamiana na bool true-bialy; false-czarny
    private int side;  // strona wykonujaca ruch
    private int xside;  //strona niewykonujaca ruchu
    private int castle;    // jak wyglada roszada?
    //czy jest podwojny ruch piona na planszy 5x5?
    private int fifty; // liczba ruchow (polruchow?) od bicia lub ruchu pionem ==50->remis
    //ograniczenia w szukaniu ruchow;
    private int max_time;
    private int max_depth;
    private boolean isCheck;
    //czas rozpoczecia szukania i maksymalny czas zaakonczenia
    private int start_time;
    private int stop_time;
    public int[] init_color={
            2, 2, 2, 2, 2,
            2, 2, 2, 2, 2,
            0, 0, 0, 0, 0,
            1, 1, 1, 1, 1,
            1, 1, 1, 1, 1,
    };
    public int[] init_piece={// wie�a ko� goniec hetman kr�l
            4, 2, 3, 5, 6,
            1, 1, 1, 1, 1,
            0, 0, 0, 0, 0,
            1, 1, 1, 1, 1,
            4, 2, 3, 5, 6,
    };
    Data (){
        isCheck=false;
        side=1;
        xside=2;
        fifty=0;
        piece=new int[25];
        piece=init_piece.clone();
        color=new int[25];
        color=init_color.clone();
    }
    public int getPiece(int x, int y){
        if(x<5 && x>-1)
            if(y<5 && y>-1)
                return piece[x+y*5];
        return -1;
    }
    public int getSide(){
        return this.side;
    }
    public int getXside(){
        return this.xside;
    }
    public int getColor(int x, int y){
        if(x<5 && x>-1)
            if(y<5 && y>-1)
                return color[x+y*5];
        return -1;
    }
    public boolean getIsCheck(){
        return this.isCheck;
    }

    public void setIsCheck(boolean isCheck){
        this.isCheck=isCheck;
    }

    public void changeSite(){
        int a = this.xside;
        this.xside = this.side;
        this.side = a;
    }
}
