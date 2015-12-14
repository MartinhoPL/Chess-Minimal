package com.example.ChessMinimal;

public final class ChessMechanic {

    private ChessMechanic() {
    }

    public static boolean isInsideBoard(int x, int y, Data data){
        if(x < 0 || y < 0 || x >= Fixed.XWIDTH || y >= Fixed.YHEIGHT) {
            return false;
        }
        return true;
    }

    public static int findKing(boolean isMyKing, Data data) { // jak w nazwie
        for (int i = 0; i < Fixed.YHEIGHT; i++) {//y
            for (int j = 0; j < Fixed.XWIDTH; j++) {//x
                if (data.getPiece(j, i) == 6) {
                    if (isMyKing) {
                        if ((data.getSide() == data.getColor(j, i))) {
                            return i * Fixed.XWIDTH + j;
                        }
                    } else {
                        if ((data.getSide() != data.getColor(j, i))) {
                            return i * Fixed.XWIDTH + j;
                        }
                    }
                }
            }
        }
        return -1;
    }

    public static boolean pawnCanAttack(int x1, int y1, int x2, int y2, Data data) {
        if (y1 - y2 == 1) {
            if (data.getColor(x1, y1) == 1) {
                if (x1 - x2 == 1 || x1 - x2 == -1) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (y1 - y2 == -1) {
            if (data.getColor(x1, y1) == 2) {
                if (x1 - x2 == 1 || x1 - x2 == -1) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean pawnMoveIsCorrect(int x1, int y1, int x2, int y2, Data data) {
        int myColor = data.getColor(x1, y1), moveColor = data.getColor(x2, y2);
        if (myColor == moveColor) {
            return false;
        }
        if (myColor == 1) {
            if (y1 - y2 != 1) {//czy ruch o jedno pole do przodu białe
                return false;
            }
        } else {
            if (y2 - y1 != 1) {//czy ruch o jedno pole do przodu czarne
                return false;
            }
        }
        if (moveColor == 0 && x1 - x2 == 0) {
            return true;
        }
        if ((x1 - x2 == 1 || x1 - x2 == -1) && (moveColor != 0)) {//czy poprawne bicie pionem
            return true;
        } else {
            return false;
        }
    }

    public static boolean knigthMoveIsCorrect(int x1, int y1, int x2, int y2, Data data) {//ruchy konia
        if (data.getColor(x2, y2) == data.getColor(x1, y1)) {
            return false;
        }
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
    }

    public static boolean bishopMoveIsCorrect(int x1, int y1, int x2, int y2, Data data) {
        if (x1 - y1 != x2 - y2 && x1 + y1 != x2 + y2) {
            return false;  //czy ruch po skosie
        }
        int xminus = 1, yminus = 1, i = x1, j = y1; //czy linia ruchu jest pusta
        if (x1 > x2) {
            xminus = -1; //czy w lewo?
        }
        if (y1 > y2) {
            yminus = -1;  // czy do gory?
        }
        while (true) {
            i += xminus;
            j += yminus;
            if (i == x2) break;
            if (j == y2) break;
            if (data.getPiece(i, j) != 0)
                return false;
        }
        if (data.getColor(x2, y2) == data.getColor(x1, y1)) {
            return false;
        }
        return true;
    }

    public static boolean rookMoveIsCorrect(int x1, int y1, int x2, int y2, Data data) {
        if (data.getColor(x2, y2) == data.getColor(x1, y1)) {
            return false;
        }
        if (x1 != x2 && y1 != y2) { // ruch w pionie lub poziomie
            return false;
        }
        if (x1 != x2) { //czy ruch w pionie
            int minus = 1;
            if (x1 > x2) minus = -1;
            for (int i = x1 + minus; i != x2; i += minus) {
                if (data.getPiece(i, y1) != 0) {
                    return false;
                }
            }
        } else { //ruch w poziomie
            int minus = 1;
            if (y1 > y2) {
                minus = -1;
            }
            for (int i = y1 + minus; i != y2; i += minus) {
                if (data.getPiece(x1, i) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean queenMoveIsCorrect(int x1, int y1, int x2, int y2, Data data) {
        if (x1 == x2 || y1 == y2) {
            if (rookMoveIsCorrect(x1, y1, x2, y2, data)) {
                return true;
            } else {
                return false;
            }
        } else if (x1 - x2 == y1 - y2 || x1 - x2 == y2 - y1) {
            if (bishopMoveIsCorrect(x1, y1, x2, y2, data)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean kingAttackPiece(int x1, int y1, int x2, int y2, Data data) {
        int piece = data.setZero(x2, y2);
        boolean result = kingMoveIsCorrect(x1, y1, x2, y2, data);
        data.resetZero(x2, y2, piece);
        return result;
    }

    public static boolean kingMoveIsCorrect(int x1, int y1, int x2, int y2, Data data) {
        if (x1 - x2 > 1 || x1 - x2 < -1) {
            return false;//ruch tylko o jedno pole
        } else if (y1 - y2 > 1 || y1 - y2 < -1) {
            return false;//ruch o jedno pole
        } else if (x1 - x2 == 0 && y1 - y2 == 0) {
            return false;
        }
        int myColor = data.getColor(x1, y1), moveColor = data.getColor(x2, y2);
        if (moveColor == myColor) {
            return false;
        } else if (moveColor != 0) {
            return kingAttackPiece(x1, y1, x2, y2, data);
        }
        int king = data.setZero(x1,y1);
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 5; i++) {
                if (data.getColor(i, j) != myColor) {
                    int piece = data.getPiece(i, j);
                    if (piece != 0) {
                        switch (piece) { //sprawdzanie czy cokolwiek moze sie poruszuc na pole króla (nowe)
                            case 1: {//pion (tylko do przodu o jedno pole)
                                if (pawnCanAttack(i, j, x2, y2, data)) {
                                    data.resetZero(x1,y1,king);
                                    return false;
                                }
                                break;
                            }
                            case 2: {//kon  ruch typu 2 na 1 (dwa w pionie 1 w poziomie lub dwa w poziomie 1 w pionie)
                                if (knigthMoveIsCorrect(i, j, x2, y2, data)) {
                                    data.resetZero(x1,y1,king);
                                    return false;
                                }
                                break;
                            }
                            case 3: {//goniec
                                if (bishopMoveIsCorrect(i, j, x2, y2, data)) {
                                    data.resetZero(x1,y1,king);
                                    return false;
                                }
                                break;
                            }
                            case 4: {//wieza
                                if (rookMoveIsCorrect(i, j, x2, y2, data)) {
                                    data.resetZero(x1,y1,king);
                                    return false;
                                }
                                break;
                            }
                            case 5: {//hetman
                                if (queenMoveIsCorrect(i, j, x2, y2, data)) {
                                    data.resetZero(x1,y1,king);
                                    return false;
                                }
                                break;
                            }
                            case 6: {//krol sprawdzamy czy nie podjeżdżamy pod króla przeciwnika(ruch hipotetyczny)
                                if (i - x2 < 2 && i - x2 > -2) {
                                    if (j - y2 < 2 && j - y2 > -2) {
                                        data.resetZero(x1,y1,king);
                                        return false;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        data.resetZero(x1,y1,king);
        return true;
    }

    public static boolean isCheckAfterMove(boolean myKing, Data data) {//czy po ruchu doszlo do szacha
        int king = findKing(myKing, data);
        int kingX = king % Fixed.XWIDTH, kingY = king / Fixed.XWIDTH;
        int side;
        if (myKing) {
            side = data.getSide();
        } else {
            side = data.getXside();
        }
        for (int j = 0; j < Fixed.YHEIGHT; j++) {
            for (int i = 0; i < Fixed.XWIDTH; i++) {
                int piece = data.getPiece(i, j);
                if (piece != 0) {
                    if (data.getColor(i, j) != side) {
                        switch (piece) {//sprawdzanie czy dana figura może wykonać ruch na pole króla
                            case 1: {//pion
                                if (pawnMoveIsCorrect(i, j, kingX, kingY, data)) {
                                    return true;
                                }
                                break;
                            }
                            case 2: {//kon
                                if (knigthMoveIsCorrect(i, j, kingX, kingY, data)) {
                                    return true;
                                }
                                break;
                            }
                            case 3: {//goniec
                                if (bishopMoveIsCorrect(i, j, kingX, kingY, data)) {
                                    return true;
                                }
                                break;
                            }
                            case 4: {//wieza
                                if (rookMoveIsCorrect(i, j, kingX, kingY, data)) {
                                    return true;
                                }
                                break;
                            }
                            case 5: {//hetman
                                if (queenMoveIsCorrect(i, j, kingX, kingY, data)) {
                                    return true;
                                }
                                break;
                            }
                            //króla nie sprawdzamy bo nie można ruszyć się drugim królem pod bicie
                            //więc wszystkie takie ruchy nie będą poprawne(beda odrzucane wczesniej)
                        }
                    }
                }
            }
        }
        return false;
    }

    public static MoveCorrectEnum promotion(int x, int y, int piece, Data data) {
        data.promotion(x, y, piece);
        boolean isCheck = isCheckAfterMove(true, data);
        if (isCheck) {
            if (isCheckmate(data)) {
                return MoveCorrectEnum.CHECKMATE;
            } else {
                return MoveCorrectEnum.CHECK;
            }
        } else {
            if (isStalemate(data)) {
                return MoveCorrectEnum.STALEMATE;
            } else {
                return MoveCorrectEnum.GOOD;
            }
        }
    }

    public static boolean isPromotionAfterMove(Data data) {
        for (int i = 0; i < Fixed.XWIDTH; i++) {
            if (data.getPiece(i, 0) == 1 || data.getPiece(i, Fixed.YHEIGHT - 1) == 1)
                return true;
        }
        return false;
    }

    public static boolean pieceAttackKing(int x, int y, int king, Data data) {
        int kingX = king % Fixed.XWIDTH, kingY = king / Fixed.XWIDTH;
        int piece = data.getPiece(x, y);
        switch (piece) {
            case 1: {
                if (pawnCanAttack(x, y, kingX, kingY, data))
                    return true;
                break;
            }
            case 2: {
                if (knigthMoveIsCorrect(x, y, kingX, kingY, data))
                    return true;
                break;
            }
            case 3: {
                if (bishopMoveIsCorrect(x, y, kingX, kingY, data))
                    return true;
                break;
            }
            case 4: {
                if (rookMoveIsCorrect(x, y, kingX, kingY, data))
                    return true;
                break;
            }
            case 5: {
                if (queenMoveIsCorrect(x, y, kingX, kingY, data))
                    return true;
                break;
            }
        }
        return false;
    }

    public static boolean pieceBlocked(int x1, int y1, int x2, int y2 ,Data data) {
        int king = findKing(true, data), piece = 0, color = 0, iterator = 1, iterator2 = 1;
        int kingX = king % Fixed.XWIDTH, kingY = king / Fixed.XWIDTH;
        if (kingX == x1) { // w tej samej kolumnie co krol
            if (x1 == x2) {//ruch w obrebie kolumny
                return false;
            } else {
                if (kingY > y1) { // krol nizej na planszy
                    iterator = -1;
                }
                for (int i = y1 + iterator; i > -1 && i < Fixed.YHEIGHT; i += iterator) {
                    color = data.getColor(x1, i);
                    if (color == 0) {
                        continue;
                    } else if (color == data.getSide()) {
                        return false;
                    } else {
                        piece = data.getPiece(x1, i);
                        if (piece == 5 || piece == 4) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }
        } else if (kingY == y1) { // w tym samym wierszu co krol
            if (y1 == y2) {//ruch w obrebie wiersza
                return false;
            } else {
                if (kingX > x1) { // krol nizej na planszy
                    iterator = -1;
                }
                for (int i = x1 + iterator; i > -1 && i < Fixed.XWIDTH; i += iterator) {
                    color = data.getColor(i, y1);
                    if (color == 0) {
                        continue;
                    } else if (color == data.getSide()) {
                        return false;
                    } else {
                        piece = data.getPiece(i, y1);
                        if (piece == 5 || piece == 4) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }
        } else if (kingX + kingY == x1 + y1 || kingX - kingY == x1 - y1) { //ktorys ze skosow
            if (kingX > x1) {
                iterator = -1;
            }
            if (kingY > y1) {
                iterator2 = -1;
            }
            int i = x1, j = y1;
            while (true) {
                i += iterator;
                j += iterator2;
                if (i < Fixed.XWIDTH && i > -1 && j < Fixed.YHEIGHT && j > -1) {
                    color = data.getColor(i, j);
                    if (color == 0) {
                        continue;
                    } else if (color == data.getSide()) {
                        return false;
                    } else {
                        piece = data.getPiece(i, j);
                        if (piece == 5 || piece == 3) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static boolean attackerCanKilled(int x, int y, Data data) {
        for (int i = 0; i < Fixed.YHEIGHT; i++) {//y
            for (int j = 0; j < Fixed.XWIDTH; j++) { //x
                if (data.getColor(j, i) == data.getSide()) {
                    int piece = data.getPiece(j, i);
                    switch (piece) {
                        case 1: {
                            if (pawnCanAttack(j, i, x, y, data) && !pieceBlocked(j, i, x, y, data))
                                return true;
                            break;
                        }
                        case 2: {
                            if (knigthMoveIsCorrect(j, i, x, y, data) && !pieceBlocked(j, i, x, y, data))
                                return true;
                            break;
                        }
                        case 3: {
                            if (bishopMoveIsCorrect(j, i, x, y, data) && !pieceBlocked(j, i, x, y, data))
                                return true;
                            break;
                        }
                        case 4: {
                            if (rookMoveIsCorrect(j, i, x, y, data) && !pieceBlocked(j, i, x, y, data))
                                return true;
                            break;
                        }
                        case 5: {
                            if (queenMoveIsCorrect(j, i, x, y, data) && !pieceBlocked(j, i, x, y, data))
                                return true;
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean pieceCanMove(int x1, int y1, int x2, int y2, Data data) {
        int piece = data.getPiece(x1, y1);
        switch (piece) {
            case 1: {//pion
                if (pawnMoveIsCorrect(x1, y1, x2, y2, data)) {
                    return true;
                }
                break;
            }
            case 2: {//kon
                if (knigthMoveIsCorrect(x1, y1, x2, y2, data)) {
                    return true;
                }
                break;
            }
            case 3: {//goniec
                if (bishopMoveIsCorrect(x1, y1, x2, y2, data)) {
                    return true;
                }
                break;
            }
            case 4: {//wieza
                if (rookMoveIsCorrect(x1, y1, x2, y2, data)) {
                    return true;
                }
                break;
            }
            case 5: {//hetman
                if (queenMoveIsCorrect(x1, y1, x2, y2, data)) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    public static boolean diagonalAttackCanDefend(int x, int y, int king, Data data) {
        int kingX = king % Fixed.XWIDTH, kingY = king / Fixed.XWIDTH, myColor = data.getColor(kingX, kingY);
        int size = Fixed.XWIDTH;
        if (size < Fixed.YHEIGHT) {
            size = Fixed.YHEIGHT;
        }
        int xminus = 1, yminus = 1, i = y, j = x; //czy linia ruchu jest pusta
        if (x > kingX) {
            xminus = -1; //czy w lewo do krola
        }
        if (y > kingY) {
            yminus = -1;  // czy do gory do krola
        }
        //sprawdzamy czy można zasłonić linię ataku
        while (true) {
            i += yminus;
            j += xminus;
            if (j == kingX || i == kingY) break;
            for (int k = 0; k < Fixed.YHEIGHT; k++) { //pion
                if (k == i)
                    continue;
                if (data.getColor(j, k) == myColor) {
                    if (pieceCanMove(j, k, j, i, data))
                        return true;
                }
            }

            for (int k = 0; k < Fixed.XWIDTH; k++) { //poziom
                if (k == j)
                    continue;
                if (data.getColor(k, i) == myColor) {
                    if (pieceCanMove(k, i, j, i, data))
                        return true;
                }
            }
            //skosy
            for (int k = 1; k < size; k++) {//1
                if (i + k > Fixed.YHEIGHT - 1 || j + k > Fixed.XWIDTH - 1)
                    break;
                if (data.getColor(j + k, i + k) == myColor) {
                    if (pieceCanMove(j + k, i + k, j, i, data))
                        return true;
                }
            }
            for (int k = 1; k < size; k++) {//2
                if (i - k < 0 || j + k > Fixed.XWIDTH - 1)
                    break;
                if (data.getColor(j + k, i - k) == myColor) {
                    if (pieceCanMove(j + k, i - k, j, i, data))
                        return true;
                }
            }
            for (int k = 1; k < size; k++) {//3
                if (i + k > Fixed.YHEIGHT - 1 || j - k < 0)
                    break;
                if (data.getColor(j - k, i + k) == myColor) {
                    if (pieceCanMove(j - k, i + k, j, i, data))
                        return true;
                }
            }
            for (int k = 1; k < size; k++) {//4
                if (i - k < 0 || j - k < 0)
                    break;
                if (data.getColor(j - k, i - k) == myColor) {
                    if (pieceCanMove(j - k, i - k, j, i, data))
                        return true;
                }
            }
        }
        return false;
    }

    public static boolean fileOrRankAttackCanDefend(int x, int y, int king, Data data) {
        int kingX = king % Fixed.XWIDTH, kingY = king / Fixed.XWIDTH, myColor = data.getColor(kingX, kingY);
        int xminus = 1, yminus = 1;
        int size = Fixed.XWIDTH;
        if (size < Fixed.YHEIGHT) {
            size = Fixed.YHEIGHT;
        }
        if (x == kingX) { // file - pion
            xminus = 0;
            if (y > kingY) {//czy w dol do krola?
                yminus = -1;
            }
        } else if (y == kingY) { // rank - poziom
            yminus = 0;
            if (x > kingX) { // czy w prawo do krola?
                xminus = -1;
            }
        }
        while (true) {
            x += xminus;
            y += yminus;
            if (x == kingX && y == kingY)
                break;
            if (y != kingY) { // !czy atak w pionie?
                for (int k = 0; k < Fixed.YHEIGHT; k++) { //pion
                    if (k == x)
                        continue;
                    if (data.getColor(k, x) == myColor) {
                        if (pieceCanMove(k, y, x, y, data))
                            return true;
                    }
                }
            }
            if (x != kingX) {// !czy atak w poziomie?
                for (int k = 0; k < Fixed.XWIDTH; k++) { //poziom
                    if (k == y)
                        continue;
                    if (data.getColor(x, k) == myColor) {
                        if (pieceCanMove(x, k, x, y, data))
                            return true;
                    }
                }
            }
            //skosy
            for (int k = 1; k < size; k++) {//1
                if (y + k > Fixed.YHEIGHT - 1 || x + k > Fixed.XWIDTH - 1)
                    break;
                if (data.getColor(x + k, y + k) == myColor) {
                    if (pieceCanMove(x + k, y + k, x, y, data))
                        return true;
                }
            }
            for (int k = 1; k < size; k++) {//2
                if (y - k < 0 || x + k > Fixed.XWIDTH - 1)
                    break;
                if (data.getColor(x + k, y - k) == myColor) {
                    if (pieceCanMove(x + k, y - k, x, y, data))
                        return true;
                }
            }
            for (int k = 1; k < size; k++) {//3
                if (y + k > Fixed.YHEIGHT - 1 || x - k < 0)
                    break;
                if (data.getColor(x - k, y + k) == myColor) {
                    if (pieceCanMove(x - k, y + k, x, y, data))
                        return true;
                }
            }
            for (int k = 1; k < size; k++) {//4
                if (y - k < 0 || x - k < 0)
                    break;
                if (data.getColor(x - k, y - k) == myColor) {
                    if (pieceCanMove(x - k, y - k, x, y, data))
                        return true;
                }
            }
        }
        return false;
    }

    public static void pawnStalemate(int x, int y, int tab[][], Data data) {
        if (tab[x][y] != -1) {
            tab[x][y] = 0;
        }
        int minus = 1; //czarne przeciwnika
        if (data.getXside() == 1) {
            minus = -1;//biale
        }
        if (x - 1 > -1) {
            if (data.getColor(x - 1, y) == 0) { //pole wolne
                tab[x - 1][y + minus] = -1;
            } else if (data.getColor(x - 1, y) == data.getXside()) {
                tab[x - 1][y + minus] = -1;
            }
        }
        if (x + 1 < Fixed.XWIDTH) {
            if (data.getColor(x + 1, y) == 0) { //pole wolne
                tab[x + 1][y + minus] = -1;
            } else if (data.getColor(x + 1, y) == data.getXside()) {
                tab[x + 1][y + minus] = -1;
            }
        }
    }

    public static void knightStalemate(int x, int y, int tab[][], Data data) {
        if (tab[x][y] != -1) {
            tab[x][y] = 0;
        }
        if (x + 1 < Fixed.XWIDTH) {
            if (y + 2 < Fixed.YHEIGHT) {
                if (data.getColor(x + 1, y + 2) != data.getSide()) {
                    tab[x + 1][y + 2] = -1;
                }
            }
            if (y - 2 > -1) {
                if (data.getColor(x + 1, y - 2) != data.getSide()) {
                    tab[x + 1][y - 2] = -1;
                }
            }
            if (x + 2 < Fixed.XWIDTH) {
                if (y + 1 < Fixed.YHEIGHT) {
                    if (data.getColor(x + 2, y + 1) != data.getSide()) {
                        tab[x + 2][y + 1] = -1;
                    }
                }
                if (y - 1 > -1) {
                    if (data.getColor(x + 2, y + 1) != data.getSide()) {
                        tab[x + 2][y - 1] = -1;
                    }
                }
            }
        }
        if (x - 1 > -1) {
            if (y + 2 < Fixed.YHEIGHT) {
                if (data.getColor(x - 1, y + 2) != data.getSide()) {
                    tab[x - 1][y + 2] = -1;
                }
            }
            if (y - 2 > -1) {
                if (data.getColor(x - 1, y - 2) != data.getSide()) {
                    tab[x - 1][y - 2] = -1;
                }
            }
            if (x - 2 > -1) {
                if (y + 1 < Fixed.YHEIGHT) {
                    if (data.getColor(x - 2, y + 1) != data.getSide()) {
                        tab[x - 2][y + 1] = -1;
                    }
                }
                if (y - 1 > -1) {
                    if (data.getColor(x - 2, y - 1) != data.getSide()) {
                        tab[x - 2][y - 1] = -1;
                    }
                }
            }
        }
    }

    public static void bishopStalemate(int x, int y, int tab[][], int kingPos, Data data) {
        if (tab[x][y] != -1) {
            tab[x][y] = 0;
        }
        int piece, color, side = data.getSide(), xside = data.getXside();
        int kingX = kingPos % 5, kingY = kingPos / 5;
        boolean leftUp = false, rightUp = false, rightDown = false, leftDown = false;
        if (x - y == kingX - kingY) {
            if (x - kingX < 0) {
                rightDown = true;
            } else {
                leftUp = true;
            }
        } else if (x + y == kingX + kingY) {
            if (x - kingX > 0) {
                rightUp = true;
            } else {
                leftDown = true;
            }
        }
        //1 rightDown
        for (int i = 1; x + i < Fixed.XWIDTH && y + i < Fixed.YHEIGHT; i++) {
            color = data.getColor(x + i, y + i);
            if (color == 0) {
                tab[x + i][y + i] = -1;
            } else if (color == xside) {
                tab[x + i][y + i] = -1;
                break;
            } else {//color == side
                if (rightDown) {
                    piece = data.getPiece(x + i, y + i);
                    if (piece == 1) {
                        if (side == 1) {
                            if (i == 1) {
                                tab[x + i][y + i] = 2;
                            } else {
                                tab[x + i][y + i] = 3;
                            }
                        } else {
                            tab[x + i][y + i] = 3;
                        }
                    }
                    if (piece == 3 || piece == 5) {
                        tab[x + i][y + i] = 2;
                    } else {
                        tab[x + i][y + i] = 3;
                    }
                    for (int j = i + 1; x + j < Fixed.XWIDTH && y + j < Fixed.YHEIGHT; j++) {
                        if (data.getColor(x + j, y + j) != 0) {
                            if (data.getPiece(x + j, y + j) == 6 && data.getColor(x + j, y + j) == side) {
                                break;
                            } else {
                                tab[x + i][y + i] = 1;
                                break;
                            }
                        }
                    }
                }
                break;
            }
        }//leftDown
        for (int i = 1; x - i > -1 && y + i < Fixed.YHEIGHT; i++) {
            color = data.getColor(x - i, y + i);
            if (color == 0) {
                tab[x - i][y + i] = -1;
            } else if (color == xside) {
                tab[x - i][y + i] = -1;
                break;
            } else {//color == side
                if (leftDown) {
                    piece = data.getPiece(x - i, y + i);
                    if (piece == 1) {
                        if (side == 1) {
                            if (i == 1) {
                                tab[x - i][y + i] = 2;
                            } else {
                                tab[x - i][y + i] = 3;
                            }
                        } else {
                            tab[x - i][y + i] = 3;
                        }
                    }
                    if (piece == 3 || piece == 5) {
                        tab[x - i][y + i] = 2;
                    } else {
                        tab[x - i][y + i] = 3;
                    }
                    for (int j = i + 1; x - j > -1 && y + j < Fixed.YHEIGHT; j++) {
                        if (data.getColor(x - j, y + j) != 0) {
                            if (data.getPiece(x - j, y + j) == 6 && data.getColor(x - j, y + j) == side) {
                                break;
                            } else {
                                tab[x - i][y + i] = 1;
                                break;
                            }
                        }
                    }
                }
                break;
            }
        }//leftUp
        for (int i = 1; x - i > -1 && y - i > -1; i++) {
            color = data.getColor(x - i, y - i);
            if (color == 0) {
                tab[x - i][y - i] = -1;
            } else if (color == xside) {
                tab[x - i][y - i] = -1;
                break;
            } else {//color == side
                if (leftUp) {
                    piece = data.getPiece(x - i, y - i);
                    if (piece == 1) {
                        if (side == 2) {
                            if (i == 1) {
                                tab[x - i][y - i] = 2;
                            } else {
                                tab[x - i][y - i] = 3;
                            }
                        } else {
                            tab[x - i][y - i] = 3;
                        }
                    }
                    if (piece == 3 || piece == 5) {
                        tab[x - i][y - i] = 2;
                    } else {
                        tab[x - i][y - i] = 3;
                    }
                    for (int j = i + 1; x - j > -1 && y - j > -1; j++) {
                        if (data.getColor(x - j, y - j) != 0) {
                            if (data.getPiece(x - j, y - j) == 6 && data.getColor(x - j, y - j) == side) {
                                break;
                            } else {
                                tab[x - i][y - i] = 1;
                                break;
                            }
                        }
                    }
                }
                break;
            }
        }//rightUp
        for (int i = 1; x + i < Fixed.XWIDTH && y - i > -1; i++) {
            color = data.getColor(x + i, y - i);
            if (color == 0) {
                tab[x + i][y - i] = -1;
            } else if (color == xside) {
                tab[x + i][y - i] = -1;
                break;
            } else {//color == side
                if (rightUp) {
                    piece = data.getPiece(x + i, y - i);
                    if (piece == 1) {
                        if (side == 2) {
                            if (i == 1) {
                                tab[x + i][y - i] = 2;
                            } else {
                                tab[x + i][y - i] = 3;
                            }
                        } else {
                            tab[x + i][y - i] = 3;
                        }
                    }
                    if (piece == 3 || piece == 5) {
                        tab[x + i][y - i] = 2;
                    } else {
                        tab[x + i][y - i] = 3;
                    }
                    for (int j = i + 1; x + j < Fixed.XWIDTH && y - j > -1; j++) {
                        if (data.getColor(x + j, y - j) != 0) {
                            if (data.getPiece(x + j, y - j) == 6 && data.getColor(x + j, y - j) == side) {
                                break;
                            } else {
                                tab[x + i][y - i] = 1;
                                break;
                            }
                        }
                    }
                }
                break;
            }
        }
    }

    public static void rookStalemate(int x, int y, int tab[][], int kingPos, Data data) {
        if (tab[x][y] != -1) {
            tab[x][y] = 0;
        }
        int piece, color, side = data.getSide(), xside = data.getXside();
        int kingX = kingPos % Fixed.XWIDTH, kingY = kingPos / Fixed.XWIDTH;
        boolean up, right, down, left;
        up = right = down = left = false;
        if (x == kingX) {
            if (y > kingY) {
                up = true;
            } else {
                down = true;
            }
        }
        if (y == kingY) {
            if (x > kingX) {
                left = true;
            } else {
                right = true;
            }
        }//right
        for (int i = 1; x + i < Fixed.XWIDTH; i++) {
            color = data.getColor(x + i, y);
            if (color == 0) {
                tab[x + i][y] = -1;
            } else if (color == xside) {
                tab[x + i][y] = -1;
                break;
            } else { //color==site
                if (right) {
                    piece = data.getPiece(x + i, y);
                    if (piece == 4 || piece == 5) {
                        tab[x + i][y] = 2;
                    } else {
                        tab[x + i][y] = 3;
                    }
                    for (int j = i + 1; x + j < Fixed.XWIDTH; j++) {
                        if (data.getColor(x + j, y) != 0) {
                            if (data.getPiece(x + j, y) == 6 && data.getColor(x + j, y) == side) {
                                break;
                            } else {
                                tab[x + i][y] = 1;
                                break;
                            }
                        }
                    }
                }
                break;
            }
        }//left
        for (int i = 1; x - i > -1; i++) {
            color = data.getColor(x - i, y);
            if (color == 0) {
                tab[x - i][y] = -1;
            } else if (color == xside) {
                tab[x - i][y] = -1;
                break;
            } else { //color==site
                if (right) {
                    piece = data.getPiece(x - i, y);
                    if (piece == 4 || piece == 5) {
                        tab[x - i][y] = 2;
                    } else {
                        tab[x - i][y] = 3;
                    }
                    for (int j = i + 1; x - j > -1; j++) {
                        if (data.getColor(x - j, y) != 0) {
                            if (data.getPiece(x - j, y) == 6 && data.getColor(x - j, y) == side) {
                                break;
                            } else {
                                tab[x - i][y] = 1;
                                break;
                            }
                        }
                    }
                }
                break;
            }
        }//up
        for (int i = 1; y - i > -1; i++) {
            color = data.getColor(x, y - i);
            if (color == 0) {
                tab[x][y - i] = -1;
            } else if (color == xside) {
                tab[x][y - i] = -1;
                break;
            } else { //color==site
                if (right) {
                    piece = data.getPiece(x, y - i);
                    if (piece == 4 || piece == 5) {
                        tab[x][y - i] = 2;
                    } else {
                        tab[x][y - i] = 3;
                    }
                    for (int j = i + 1; y - j > -1; j++) {
                        if (data.getColor(x, y - j) != 0) {
                            if (data.getPiece(x, y - j) == 6 && data.getColor(x, y - j) == side) {
                                break;
                            } else {
                                tab[x][y - i] = 1;
                                break;
                            }
                        }
                    }
                }
                break;
            }
        }//down
        for (int i = 1; y + i < Fixed.YHEIGHT; i++) {
            color = data.getColor(x, y + i);
            if (color == 0) {
                tab[x][y + i] = -1;
            } else if (color == xside) {
                tab[x][y + i] = -1;
                break;
            } else { //color==site
                if (right) {
                    piece = data.getPiece(x, y + i);
                    if (piece == 4 || piece == 5) {
                        tab[x][y + i] = 2;
                    } else {
                        tab[x][y + i] = 3;
                    }
                    for (int j = i + 1; y + j < Fixed.YHEIGHT; j++) {
                        if (data.getColor(x, y + j) != 0) {
                            if (data.getPiece(x, y + j) == 6 && data.getColor(x, y + j) == side) {
                                break;
                            } else {
                                tab[x][y + i] = 1;
                                break;
                            }
                        }
                    }
                }
                break;
            }
        }
    }

    public static void kingStalemate(int x, int y, int tab[][], Data data) {
        tab[x][y] = 0;
        int piece, color, side = data.getSide(), xside = data.getXside();
        for (int i = -1; i < 2; i++) {//y
            if (y + i < 0 || y + i > Fixed.YHEIGHT - 1)
                continue;
            for (int j = -1; j < 2; j++) {//x
                if ((i == 0 && j == 0) || x + j < 0 || x + j > Fixed.XWIDTH - 1)
                    continue;
                color = data.getColor(x + j, y + i);
                if (color == 0 || color == xside) {
                    tab[x + j][y + i] = -1;
                }
            }
        }
    }

    public static boolean pawnCanMove(int x, int y, Data data) {
        int yminus = 1;
        if (data.getSide() == 1) { //czy biale?
            yminus = -1;
        }
        if (data.getColor(x, y + yminus) == 0)  //pole przed pionem wolne?
            return true;
        if ((x + 1 < Fixed.XWIDTH && data.getColor(x + 1, y + yminus) == data.getXside())
                || (x - 1 > -1 && data.getColor(x - 1, y + yminus) == data.getXside())) {
            return true;//czy mozna bic?
        }
        return false;
    }

    public static boolean knightCanMove(int x, int y, Data data) {
        int side = data.getSide();
        if (x + 1 < Fixed.XWIDTH) {
            if (y + 2 < Fixed.YHEIGHT) {
                if (data.getColor(x + 1, y + 2) != side)
                    return true;
            }
            if (y - 2 > -1) {
                if (data.getColor(x + 1, y - 2) != side)
                    return true;
            }
            if (x + 2 < Fixed.XWIDTH) {
                if (y + 1 < Fixed.YHEIGHT) {
                    if (data.getColor(x + 2, y + 1) != side)
                        return true;
                }
                if (y - 1 > -1) {
                    if (data.getColor(x + 2, y - 1) != side)
                        return true;
                }
            }
        }
        if (x - 1 > -1) {
            if (y + 2 < Fixed.YHEIGHT) {
                if (data.getColor(x - 1, y + 2) != side)
                    return true;
            }
            if (y - 2 > -1) {
                if (data.getColor(x - 1, y - 2) != side)
                    return true;
            }
            if (x - 2 > -1) {
                if (y + 1 < Fixed.YHEIGHT) {
                    if (data.getColor(x - 2, y + 1) != side)
                        return true;
                }
                if (y - 1 > -1) {
                    if (data.getColor(x - 2, y - 1) != side)
                        return true;
                }
            }
        }
        return false;
    }

    public static boolean bishopCanMove(int x, int y, Data data) {
        boolean leftUp, rightUp, rightDown, leftDown;
        leftUp = leftDown = rightDown = rightUp = true;
        int site = data.getSide();
        for (int i = 1; i < Fixed.XWIDTH; i++) {
            if (x + i < Fixed.XWIDTH) {
                if (rightDown && y + i < Fixed.YHEIGHT) {
                    if (data.getColor(x + i, y + i) != site) {
                        return true;
                    } else {
                        rightDown = false;
                    }
                }
                if (rightUp && y - i > -1) {
                    if (data.getColor(x + i, y - i) != site) {
                        return true;
                    } else {
                        rightUp = false;
                    }
                }
            }
            if (x - i > -1) {
                if (leftDown && y + i < Fixed.YHEIGHT) {
                    if (data.getColor(x - i, y + i) != site) {
                        return true;
                    } else {
                        leftDown = false;
                    }

                }
                if (leftUp && y - i > -1) {
                    if (data.getColor(x - i, y - i) != site) {
                        return true;
                    } else {
                        leftUp = false;
                    }

                }
            }
        }
        return false;
    }

    public static boolean rookCanMove(int x, int y, Data data) {
        boolean up, right, down, left;
        up = right = down = left = true;
        int site = data.getSide();
        for (int i = 1; i < Fixed.XWIDTH; i++) {
            if (right && x + i < Fixed.XWIDTH) {
                if (data.getColor(x + i, y) != site) {
                    return true;
                } else {
                    right = false;
                }
            }
            if (left && x - i > -1) {
                if (data.getColor(x - i, y) != site) {
                    return true;
                } else {
                    left = false;
                }
            }
            if (down && y + i < Fixed.YHEIGHT) {
                if (data.getColor(x, y + i) != site) {
                    return true;
                } else {
                    down = false;
                }
            }
            if (up && y - i > -1) {
                if (data.getColor(x, y - i) != site) {
                    return true;
                } else {
                    up = false;
                }
            }
        }
        return false;
    }

    public static boolean kingCanMove(int x, int y, int tab[][], Data data) {
        for (int i = -1; i < 2; i++) {//y
            if (y + i < 0 || y + i > Fixed.YHEIGHT - 1)
                continue;
            for (int j = -1; j < 2; j++) {//x
                if ((i == 0 && j == 0) || x + j < 0 || x + j > Fixed.XWIDTH - 1)
                    continue;
                if (tab[x + j][y + i] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isStalemate(Data data) {
        boolean isCheckmatePossible = false;
        int knigths[] = new int[2], bishops[] = new int[2], bishopsColor[] = new int[2];
        knigths[0] = knigths [1] = bishops [0] = bishops [1] = 0;
        bishopsColor[0] = bishopsColor[1] = -1;
        int king = findKing(true, data);
        int tab[][] = new int[Fixed.XWIDTH][Fixed.YHEIGHT]; // -1 atakowane(pole wolne i przeciwnika); 0 jak -1 tylko nieatakowane
        for (int i = 0; i < Fixed.XWIDTH; i++) {// 1 nasze niezwiazane; 2 nasze zwiazane ale moze zabic wiazacego
            for (int j = 0; j < Fixed.YHEIGHT; j++) {// 3 jak 2 tylko bez mozliwosci zabicia
                tab[i][j] = 1;
            }
        }
        int xside = data.getXside(), side = data.getSide(), color, piece;
        for (int i = 0; i < Fixed.YHEIGHT; i++) {//y
            for (int j = 0; j < Fixed.XWIDTH; j++) {//x
                color = data.getColor(j, i);
                if (color == side) {
                    piece = data.getPiece(j,i);
                    if(!isCheckmatePossible){
                        switch (piece) {
                            case 1:
                            case 4:
                            case 5: {
                                isCheckmatePossible = true;
                                break;
                            }
                            case 2: {
                                knigths[color - 1]++;
                                if (knigths[color - 1] > 1) {
                                    isCheckmatePossible = true;
                                }
                                break;
                            }
                            case 3: {
                                bishops[color - 1]++;
                                if (bishops[color - 1] > 1) {
                                    if ((bishopsColor[color - 1] != -1) && (bishopsColor[color - 1] != (j + i) % 2)) {
                                        isCheckmatePossible = true;
                                    }
                                } else {
                                    bishopsColor[color - 1] = (i + j) % 2;
                                }
                                break;
                            }
                        }
                    }
                    continue;
                } else if (color == xside) {
                    if (tab[j][i] == 1)
                        tab[j][i] = 0;
                    piece = data.getPiece(j, i);
                    switch (piece) {
                        case 1: {//pion
                            if(!isCheckmatePossible){
                                isCheckmatePossible = true;
                            }
                            pawnStalemate(j, i, tab, data);
                            break;
                        }
                        case 2: {//kuc
                            if (!isCheckmatePossible) {
                                knigths[color - 1]++;
                                if (knigths[color - 1] > 1) {
                                    isCheckmatePossible = true;
                                }
                            }
                            knightStalemate(j, i, tab, data);
                            break;
                        }
                        case 3: {//goniec
                            if (!isCheckmatePossible) {
                                bishops[color - 1]++;
                                if (bishops[color - 1] > 1) {
                                    if ((bishopsColor[color - 1]!= -1) &&(bishopsColor[color - 1] != (j + i) % 2)) {
                                        isCheckmatePossible = true;
                                    }
                                } else {
                                    bishopsColor[color - 1] = (i + j) % 2;
                                }
                            }
                            bishopStalemate(j, i, tab, king, data);
                            break;
                        }
                        case 4: {
                            if(!isCheckmatePossible){
                                isCheckmatePossible = true;
                            }
                            rookStalemate(j, i, tab, king, data);
                            break;
                        }
                        case 5: {
                            if(!isCheckmatePossible){
                                isCheckmatePossible = true;
                            }
                            rookStalemate(j, i, tab, king, data);
                            bishopStalemate(j, i, tab, king, data);
                            break;
                        }
                        case 6: {
                            kingStalemate(j, i, tab, data);
                            break;
                        }
                    }
                } else {
                    if (tab[j][i] != -1)
                        tab[j][i] = 0;
                }
            }

        }
        //koniec etapu 1 - ustalenia pomocniczej TABlicy
        //etap 2 interesuja nas tylko elementy tab o wartosci 1 lub 2
        //czyli niezwiązane lub związane z mozliwoscia bicia wiazacej figury
        if(!isCheckmatePossible) {
            if ((knigths[0] == 0 || bishops[0] == 0) && (knigths[1] == 0 || bishops[1] == 0))
                return true;
        }
        for (int i = 0; i < Fixed.YHEIGHT; i++) {//y
            for (int j = 0; j < Fixed.XWIDTH; j++) {//x
                if (tab[j][i] < 1 || tab[j][i] == 3) //-1,0,3
                    continue;
                if (tab[j][i] == 2)//2
                    return false;
                piece = data.getPiece(j, i);//1
                switch (piece) {
                    case 1: {//pion
                        if (pawnCanMove(j, i, data)) {
                            return false;
                        }
                        break;
                    }
                    case 2: {//kuc
                        if (knightCanMove(j, i, data)) {
                            return false;
                        }
                        break;
                    }
                    case 3: {//goniec
                        if (bishopCanMove(j, i, data)) {
                            return false;
                        }
                        break;
                    }
                    case 4: {//wieza
                        if (rookCanMove(j, i, data)) {
                            return false;
                        }
                        break;
                    }
                    case 5: {//hetman
                        if (rookCanMove(j, i, data) || bishopCanMove(j, i, data)) {
                            return false;
                        }
                        break;
                    }
                    case 6: {//krol
                        if (kingCanMove(j, i, tab, data)) {
                            return false;
                        }
                        break;
                    }
                }
            }
        }
        return true;
    }

    public static boolean isCheckmate(Data data) {
        //sprawdzenie czy król może się poruszyć
        int king = findKing(true, data);
        int kingX = king % Fixed.XWIDTH, kingY = king / Fixed.XWIDTH;
        int attackerCount = 0, attackerX = 0, attackerY = 0, attacker = 0;
        for (int i = -1; i < 2; i++) {
            if (kingX + i < Fixed.XWIDTH && kingX + i > -1) { //czy sprawdzany potencjalny ruch nie jest za szachownice
                for (int j = -1; j < 2; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    } else {
                        if (kingY + j < Fixed.YHEIGHT && kingY + j > -1) { // tak jak dla x
                            if (kingMoveIsCorrect(kingX, kingY, kingX + i, kingY + j, data)) {
                                return false; //bo krol ma mozliwosc ruchu na nieatakowane pole
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < Fixed.YHEIGHT; i++) {//y
            for (int j = 0; j < Fixed.XWIDTH; j++) {//x
                if (data.getColor(j, i) == data.getXside()) {
                    if (pieceAttackKing(j, i, king, data)) {
                        attacker = data.getPiece(j, i);
                        attackerX = j;
                        attackerY = i;
                        attackerCount++;
                        if (attackerCount > 1) {
                            return true; //bo krol nie moze się ruszyc a atakowany jest przez 2 figury
                        }
                    }
                }
            }
        }
        if (attackerCanKilled(attackerX, attackerY, data)) {
            return false; //mozna ubic atakujacego
        }
        //czy mozna zablokowac linie ataku
        switch (attacker) {
            case 1:
            case 2:
                return true;
            case 3: {
                if (diagonalAttackCanDefend(attackerX, attackerY, king, data))
                    return false;
                break;
            }
            case 4: {
                if (fileOrRankAttackCanDefend(attackerX, attackerY, king, data))
                    return false;
                break;
            }
            case 5: {
                if (attackerX == kingX || attackerY == kingY) {
                    if (fileOrRankAttackCanDefend(attackerX, attackerY, king, data))
                        return false;
                } else if (attackerX - attackerY == kingX - kingY || attackerX + attackerY == kingX + kingY) {
                    if (diagonalAttackCanDefend(attackerX, attackerY, king, data))
                        return false;
                }
            }
        }
        return true;
    }

    // kompletne (docelowo) sprawdzenie poprawności ruchu
    public static MoveCorrectEnum isMoveCorrect(int x1, int y1, int x2, int y2, Data data) {
        //poczatek == koniec
        if (x1 == x2 && y1 == y2) {
            return MoveCorrectEnum.FAIL;
        }
        //proba ruchu na pole zajete przez nasza bierke
        if (data.getColor(x2, y2) == data.getSide()) {
            return MoveCorrectEnum.FAIL;
        }
        //ruch poza plansze
        if(!isInsideBoard(x2, y2, data)) {
            return  MoveCorrectEnum.FAIL;
        }
        //wybrana bierka jest przeciwnika lub puste pole
        if (data.getColor(x1, y1) != data.getSide()) {
            return MoveCorrectEnum.FAIL;
        }
        int piece1 = data.getPiece(x1, y1);
        switch (piece1) { //sprawdzanie poprawnosci ruchu dla kazdego typu bierki
            case 0: {//puste (brak bierki na polu)
                return MoveCorrectEnum.FAIL;
            }
            case 1: {//pion
                if (!pawnMoveIsCorrect(x1, y1, x2, y2, data)) {
                    return MoveCorrectEnum.FAIL;
                }
                break;
            }
            case 2: {//kon
                if (!knigthMoveIsCorrect(x1, y1, x2, y2, data)) {
                    return MoveCorrectEnum.FAIL;
                }
                break;
            }
            case 3: {//goniec
                if (!bishopMoveIsCorrect(x1, y1, x2, y2, data)) {
                    return MoveCorrectEnum.FAIL;
                }
                break;
            }
            case 4: {//wieza
                if (!rookMoveIsCorrect(x1, y1, x2, y2, data)) {
                    return MoveCorrectEnum.FAIL;
                }
                break;
            }
            case 5: {//hetman
                if (!queenMoveIsCorrect(x1, y1, x2, y2, data)) {
                    return MoveCorrectEnum.FAIL;
                }
                break;
            }
            case 6: {//krol
                if (!kingMoveIsCorrect(x1, y1, x2, y2, data)) {
                    return MoveCorrectEnum.FAIL;
                }
                break;
            }
        }
        data.makeMove(x1, y1, x2, y2);//proba wykonania ruchu
        //czy po ruchu jest szachowany nasz krol
        if (isCheckAfterMove(false, data)) {
            data.undoMove(); //cofnij probe
            return MoveCorrectEnum.FAIL;
        }
        //ruch poprawny sprawdzenie sytuacji na planszy

        boolean isCheck = isCheckAfterMove(true, data);//sprawdzanie czy po ruchu jest szach
        boolean mate = false;
        boolean promotion = isPromotionAfterMove(data); //sprawdzenie czy możliwosc promocji
        if (!promotion) { //brak promocji
            if (isCheck) { //sprawdz czy szach
                mate = isCheckmate(data); //czy szach-mat
            } else {
                mate = isStalemate(data); //czy pat
            }
        }
        data.undoMove();// cofnięcie ruchu
        data.setCapture(x2, y2);
        if (promotion) { //jesli promocja
            return MoveCorrectEnum.PROMOTION;
        } else {
            if (isCheck) {//czy szach
                if (mate) {//czy mat (checkMATE lub staleMATE)
                    return MoveCorrectEnum.CHECKMATE;
                } else {
                    return MoveCorrectEnum.CHECK;
                }
            } else {
                if (mate) {//czy mat (checkMATE lub staleMATE)
                    return MoveCorrectEnum.STALEMATE;
                } else {
                    return MoveCorrectEnum.GOOD;
                }
            }
        }
    }
}