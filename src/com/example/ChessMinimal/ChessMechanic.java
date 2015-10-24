package com.example.ChessMinimal;

public class ChessMechanic {
    Data data; //wskaznik na klase data by moc sprawdzac polozenie pionow

    ChessMechanic(){}

    ChessMechanic(Data data){
        this.data=data;
    }

    public int findKing(boolean isMyKing){ // jak w nazwie
        for(int i=0;i<5;i++){//y
            for(int j=0;j<5;j++) {//x
                if (data.getPiece(j, i) == 6){
                    if (isMyKing) {
                        if ((data.getSide() == data.getColor(j, i))){
                            return i * 5 + j;
                        }
                    } else {
                        if ((data.getSide() != data.getColor(j, i))){
                            return i * 5 + j;
                        }
                    }
                }
            }
        }
        return -1;
    }

    public boolean pawnMoveIsCorrect(int x1, int y1, int x2, int y2){
        if (data.getSide() == 1) {
            if (y1 - y2 != 1) {//czy ruch o jedno pole do przodu białe
                return false;
            }
        } else {
            if (y2 - y1 != 1) {//czy ruch o jedno pole do przodu czarne
                return false;
            }
        }
        if(data.getColor(x2,y2)==0 && x1-x2==0) {
            return true;
        }
        if ((x1 - x2 == 1 || x1 - x2 == -1) && (data.getColor(x2, y2) == data.getXside() )) {//czy poprawne bicie pionem
            return true;
        } else {
            return false;
        }
    }

    public boolean knigthMoveIsCorrect(int x1, int y1, int x2, int y2){//ruchy konia
        if(data.getColor(x2,y2)!=data.getSide()) {
            if (x1 > x2) {
                if (y1 > y2) {
                    if ((x1 - x2 == 1 && y1 - y2 == 2) || (x1 - x2 == 2 && y1 - y2 == 1)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if ((x1 - x2 == 1 && y1 - y2 == -2) || (x1 - x2 == 2 && y1 - y2 == -1)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                if (y1 > y2) {
                    if ((x1 - x2 == -1 && y1 - y2 == 2) || (x1 - x2 == -2 && y1 - y2 == 1)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if ((x1 - x2 == -1 && y1 - y2 == -2) || (x1 - x2 == -2 && y1 - y2 == -1)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }else {
            return false;
        }
    }

    public boolean bishopMoveIsCorrect(int x1, int y1, int x2, int y2) {
        if(data.getColor(x2,y2)!=data.getSide()) {
            if (x1 - x2 != y1 - y2 && x1 - x2 != y2 - y1) {
                return false;  //czy ruch po skosie
            }
            int xminus = 1, yminus = 1, i = x1, j = y1; //czy linia ruchu jest pusta
            if (x1 > x2) {
                xminus = -1; //czy do gory?
            }
            if (y1 > y2) {
                yminus = -1;  // czy w lewo?
            }
            while (true) {
                i += xminus;
                j += yminus;
                if (i == x2) break;
                if (j == y2) break;
                if (data.getPiece(i, j) != 0)
                    return false;
            }
            if (data.getPiece(x2, y2) == data.getSide()) {
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

    public boolean rookMoveIsCorrect(int x1, int y1, int x2, int y2){
        if(data.getColor(x2,y2)!=data.getSide()) {
            if (x1 != x2 && y1 != y2) { // ruch w pionie lub poziomie
                return false;
            }
            if (x1 != x2) { //czy ruch w pionie
                int minus = 1;
                if (x1 > x2) minus = -1;
                for (int i = x1+minus; i != x2; i += minus) {
                    if (data.getPiece(i, y1) != 0) {
                        return false;
                    }
                }
            } else { //ruch w poziomie
                int minus = 1;
                if (y1 > y2) {
                    minus = -1;
                }
                for (int i = y1+minus; i != y2; i += minus) {
                    if (data.getPiece(x1, i) != 0) {
                        return false;
                    }
                }
            }
            return true;
        }else {
            return false;
        }
    }

    public boolean queenMoveIsCorrect(int x1, int y1, int x2, int y2){
        if(data.getColor(x2,y2)!=data.getSide()) {
            if(x1 == x2 || y1 == y2){
                if(rookMoveIsCorrect(x1, y1, x2, y2)) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else if (x1 - x2 == y1 - y2 || x1 - x2 == y2 - y1){
                if(bishopMoveIsCorrect(x1, y1, x2, y2)) {
                    return true;
                }
                else {
                    return false;
                }
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public boolean kingMoveIsCorrect(int x1, int y1, int x2, int y2){
        if(data.getColor(x2,y2)!=data.getSide()) {
            if (x1 - x2 > 1 || x1 - x2 < -1) {
                return false;
            } else if (y1 - y2 > 1 || y1 - y2 < -1) {
                return false;// bo ruch tylko o jedno pole
            } else if (x1 - x2 == 0 && y1 - y2 == 0) {
                return false;
            }
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    int piece = data.getPiece(i, j);
                    if (piece != 0) {
                        if (data.getColor(i, j) != data.getSide()) {
                            switch (piece) { //sprawdzanie czy cokolwiek moze sie poruszuc na pole króla (nowe)
                                case 1: {//pion (tylko do przodu o jedno pole)
                                    if (pawnMoveIsCorrect(i, j, x2, y2)) {
                                        return false;
                                    }
                                    break;
                                }
                                case 2: {//kon  ruch typu 2 na 1 (dwa w pionie 1 w poziomie lub dwa w poziomie 1 w pionie)
                                    if (knigthMoveIsCorrect(i, j, x2, y2)) {
                                        return false;
                                    }
                                    break;
                                }
                                case 3: {//goniec
                                    if (bishopMoveIsCorrect(i, j, x2, y2)) {
                                        return false;
                                    }
                                    break;
                                }
                                case 4: {//wieza
                                    if (rookMoveIsCorrect(i, j, x2, y2)) {
                                        return false;
                                    }
                                    break;
                                }
                                case 5: {//hetman
                                    if (queenMoveIsCorrect(i, j, x2, y2)) {
                                        return false;
                                    }
                                    break;
                                }
                                case 6: {//krol sprawdzamy czy nie podjeżdżamy pod króla przeciwnika(ruch hipotetyczny)
                                    if (i - x2 > 1 || i - x2 < -1) {
                                        return false;
                                    }
                                    else if (j - y2 > 1 || j - y2 < -1) {
                                        return false;
                                    }
                                    else if (i - x2 == 0 && j - y2 == 0) {
                                        return false;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }else {
            return false;
        }
    }

    public boolean isCheckAfterMove(boolean myKing){//czy po ruchu doszlo do szacha
        int king=findKing(myKing);
        int kingX=king%5, kingY=king/5;
        int side;
        if(myKing){
            side = data.getSide();
        } else {
            side = data.getXside();
        }
        for(int i = 0 ; i < 5 ; i++){
            for (int j = 0; j < 5 ; j++) {
                int piece = data.getPiece(i,j);
                if(piece != 0){
                    if(data.getColor(i,j) != side){
                        switch (piece) {//sprawdzanie czy dana figura może wykonać ruch na pole króla
                            case 1: {//pion
                                if(pawnMoveIsCorrect(i,j,kingX,kingY)) {
                                    return true;
                                }
                                break;
                            }
                            case 2: {//kon
                                if(knigthMoveIsCorrect(i,j,kingX,kingY)) {
                                    return true;
                                }
                                break;
                            }
                            case 3:{//goniec
                                if(bishopMoveIsCorrect(i,j,kingX,kingY)) {
                                    return true;
                                }
                                break;
                            }
                            case 4:{//wieza
                                if(rookMoveIsCorrect(i,j,kingX,kingY)) {
                                    return true;
                                }
                                break;
                            }
                            case 5:{//hetman
                                if(queenMoveIsCorrect(i, j, kingX, kingY)) {
                                    return true;
                                }
                                break;
                            }//króla nie sprawdzamy bo nie można ruszyć się drugim królem pod bicie więc wsystkie takie ruchy nie będą poprawne
                        }
                    }
                }
            }
        }
        return false;
    }
    

    public boolean isMoveCorrect(int x1, int y1, int x2, int y2){ // kompletne (docelowo) sprawdzenie poprawności ruchu
        int piece1 = data.getPiece(x1,y1);
        if(data.getColor(x1,y1)!=data.getSide()) //czy wybrana bierka jest przeciwnika lub puste pole
            return false;
        switch (piece1) { //sprawdzanie poprawnosci ruchu dla kazdej bierki
            case 0: {//puste (brak bierki na polu)
                return false;
            }
            case 1: {//pion (tylko do przodu o jedno pole)
                if(!pawnMoveIsCorrect(x1,y1,x2,y2)) {
                    return false;
                }
                break;
            }
            case 2: {//kon ruch typu 2 na 1 (dwa w pionie 1 w poziomie lub dwa w poziomie 1 w pionie)
                if(!knigthMoveIsCorrect(x1,y1,x2,y2)) {
                    return false;
                }
                break;
            }
            case 3:{//goniec
                if(!bishopMoveIsCorrect(x1,y1,x2,y2)) {
                    return false;
                }
                break;
            }
            case 4:{//wieza
                if(!rookMoveIsCorrect(x1,y1,x2,y2)) {
                    return false;
                }
                break;
            }
            case 5:{//hetman
                if(!queenMoveIsCorrect(x1,y1,x2,y2)) {
                    return false;
                }
                break;
            }
            case 6:{//krol
                if(!kingMoveIsCorrect(x1,y1,x2,y2)) {
                    return false;
                }
                break;
            }
        }
        if(data.getIsCheck()){//w przypadku szacha
            data.makeMove(x1, y1, x2, y2);
            if(isCheckAfterMove(true)){//sprawdzenie czy nadal król szachowany (true - nasz król)
                data.undoMove();
                return false;// i zrócenie niepoprawności ruchu
            }
            else{
                data.undoMove();
                return true;
            }
        }
        //todo spytać o zachowywanie sekwencji ruchów
        data.setIsCheck(isCheckAfterMove(false));//sprawdzanie czy po ruchu jest szach i zmiana wartosci pola data.isCheck
        return true;//ruch poprawny
    }
}