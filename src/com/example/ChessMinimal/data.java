package com.example.ChessMinimal;


public class data {
    //mozna polaczyc color i piece np. biale parzyste czarne nieparyste 0 puste
    int[] color; //1-bialy; 2-czarny; 0-pusty
    int[] piece; //0-pusty; 1-pion; 2-kon; 3-goniec; 4-wieza; 5-hetman/krolowa; 6-krol
    //mozliwe zamiana na bool true-bialy; false-czarny
    int side;  // strona wykonujaca ruch
    int xside;  //strona niewykonujaca ruchu
    int castle;    // jak wyglada roszada?
    //czy jest podwojny ruch piona na planszy 5x5?
    int fifty; // liczba ruchow (polruchow?) od bicia lub ruchu pionem ==50->remis
    //ograniczenia w szukaniu ruchow;
    int max_time;
    int max_depth;
    //czas rozpoczecia szukania i maksymalny czas zaakonczenia
    int start_time;
    int stop_time;
    int[] init_color={
            2, 2, 2, 2, 2,
            2, 2, 2, 2, 2,
            0, 0, 0, 0, 0,
            1, 1, 1, 1, 1,
            1, 1, 1, 1, 1,
    };
    int[] init_piece;//TO DO startowe umieszczenie figur
}
