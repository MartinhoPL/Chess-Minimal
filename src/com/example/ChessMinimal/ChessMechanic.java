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

    public boolean pawnCanAttack(int x1, int y1, int x2, int y2){
        if (y1 - y2 == 1 ){
            if(data.getColor(x1,y1)==1){
                if(x1 - x2 == 1 || x1 - x2 == -1){
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }else if ( y1 - y2 == -1 ){
            if(data.getColor(x1,y1)==2){
                if(x1 - x2 == 1 || x1 - x2 == -1){
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public boolean pawnMoveIsCorrect(int x1, int y1, int x2, int y2){
        if (data.getColor(x2, y2) == data.getColor(x1, y1)) {
            return false;
        }
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

    public boolean bishopMoveIsCorrect(int x1, int y1, int x2, int y2) {
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

    public boolean rookMoveIsCorrect(int x1, int y1, int x2, int y2) {
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

    public boolean queenMoveIsCorrect(int x1, int y1, int x2, int y2) {
        if (x1 == x2 || y1 == y2) {
            if (rookMoveIsCorrect(x1, y1, x2, y2)) {
                return true;
            } else {
                return false;
            }
        } else if (x1 - x2 == y1 - y2 || x1 - x2 == y2 - y1) {
            if (bishopMoveIsCorrect(x1, y1, x2, y2)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean kingMoveIsCorrect(int x1, int y1, int x2, int y2) {
        if (data.getColor(x2, y2) == data.getColor(x1, y1)) {
            return false;
        }
        if (x1 - x2 > 1 || x1 - x2 < -1) {
            return false;//ruch tylko o jedno pole
        } else if (y1 - y2 > 1 || y1 - y2 < -1) {
            return false;//ruch o jedno pole
        } else if (x1 - x2 == 0 && y1 - y2 == 0) {
            return false;
        }
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 5; i++) {
                int piece = data.getPiece(i, j);
                if (piece != 0) {
                    if (data.getColor(i, j) != data.getSide()) {
                        switch (piece) { //sprawdzanie czy cokolwiek moze sie poruszuc na pole króla (nowe)
                            case 1: {//pion (tylko do przodu o jedno pole)
                                if (pawnCanAttack(i, j, x2, y2)) {
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
                                if (i - x2 < 2 && i - x2 > -2) {
                                    if (j - y2 < 2 && j - y2 > -2) {
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
        return true;
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
        for(int j = 0 ; j < 5 ; j++){
            for (int i = 0; i < 5 ; i++) {
                int piece = data.getPiece(i,j);
                if(piece != 0){
                    if(data.getColor(i,j) != side){
                        switch (piece) {//sprawdzanie czy dana figura może wykonać ruch na pole króla
                            case 1: {//pion
                                if(pawnMoveIsCorrect(i, j, kingX, kingY)) {
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

    public MoveCorrectEnum promotion(int x,int y,int piece) {
        data.promotion(x, y, piece);
        data.setIsCheck(isCheckAfterMove(true));
        if (data.getIsCheck()) {
            if (isCheckmate()) {
                return MoveCorrectEnum.CHECKMATE;
            } else {
                return MoveCorrectEnum.CHECK;
            }
        } else {
            if (isStalemate()) {
                return MoveCorrectEnum.STALEMATE;
            } else {
                return MoveCorrectEnum.GOOD;
            }
        }
    }

    public boolean isPromotionAfterMove(){
        for (int i = 0; i < 5; i++) {
            if(data.getPiece(i,0)==1 || data.getPiece(i,4)==1)
                return true;
        }
        return false;
    }

    public boolean pieceAttackKing(int x, int y, int king){
        int kingX = king % 5, kingY = king/5;
        int piece = data.getPiece(x, y);
        switch (piece){
            case 1: {
                if (pawnCanAttack(x, y, kingX, kingY))
                    return true;
                break;
            }
            case 2: {
                if (knigthMoveIsCorrect(x, y, kingX, kingY))
                    return true;
                break;
            }
            case 3: {
                if (bishopMoveIsCorrect(x, y, kingX, kingY))
                    return true;
                break;
            }
            case 4: {
                if (rookMoveIsCorrect(x, y, kingX, kingY))
                    return true;
                break;
            }
            case 5: {
                if (queenMoveIsCorrect(x, y, kingX, kingY))
                    return true;
                break;
            }
        }
        return false;
    }

    public boolean attackerCanKilled(int x, int y){
        for (int i = 0; i < 5; i++) {//y
            for (int j = 0; j < 5; j++) { //x
                if(data.getColor(j,i)==data.getSide()){
                    int piece = data.getPiece(j,i);
                    switch (piece){
                        case 1: {
                            if (pawnCanAttack(j, i, x, y))
                                return true;
                            break;
                        }
                        case 2: {
                            if (knigthMoveIsCorrect(j, i, x, y))
                                return false;
                        }
                        case 3: {
                            if (bishopMoveIsCorrect(j, i, x, y))
                                return true;
                            break;
                        }
                        case 4: {
                            if (rookMoveIsCorrect(j, i, x, y))
                                return true;
                            break;
                        }
                        case 5: {
                            if (queenMoveIsCorrect(j, i, x, y))
                                return true;
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean pieceCanMove(int x1, int y1, int x2, int y2) {
        int piece = data.getPiece(x1, y1);
        switch (piece) {
            case 1: {//pion
                if (pawnMoveIsCorrect(x1, y1, x2, y2)) {
                    return true;
                }
                break;
            }
            case 2: {//kon
                if (knigthMoveIsCorrect(x1, y1, x2, y2)) {
                    return true;
                }
                break;
            }
            case 3: {//goniec
                if (bishopMoveIsCorrect(x1, y1, x2, y2)) {
                    return true;
                }
                break;
            }
            case 4: {//wieza
                if (rookMoveIsCorrect(x1, y1, x2, y2)) {
                    return true;
                }
                break;
            }
            case 5: {//hetman
                if (queenMoveIsCorrect(x1, y1, x2, y2)) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    public boolean diagonalAttackCanDefend(int x, int y,int king) {
        int kingX = king % 5, kingY = king / 5;
        int xminus = 1, yminus = 1, i = y, j = x; //czy linia ruchu jest pusta
        if (x > kingX) {
            xminus = -1; //czy w lewo do krola
        }
        if (y > kingY) {
            yminus = -1;  // czy do gory do krola
        }
        //sprawdzamy czy można zasłonić linię ataku
        while (true) {
            i += xminus;
            j += yminus;
            if (j == kingX || i == kingY) break;
            for (int k = 0; k < 5; k++) { //pion
                if (k == i)
                    continue;
                if (data.getColor(j, k) == data.getSide()) {
                    if (pieceCanMove(j, k, j, i))
                        return true;
                }
            }

            for (int k = 0; k < 5; k++) { //poziom
                if (k == j)
                    continue;
                if (data.getColor(k, i) == data.getSide()) {
                    if (pieceCanMove(k, i, j, i))
                        return true;
                }
            }
            //skosy
            for (int k = 1; k < 5; k++) {//1
                if (i + k > 4 || j + k > 4)
                    break;
                if (data.getColor(j + k, i + k) == data.getSide()) {
                    if (pieceCanMove(j + k, i + k, j, i))
                        return true;
                }
            }
            for (int k = 1; k < 5; k++) {//2
                if (i - k < 0 || j + k > 4)
                    break;
                if (data.getColor(j + k, i - k) == data.getSide()) {
                    if (pieceCanMove(j + k, i - k, j, i))
                        return true;
                }
            }
            for (int k = 1; k < 5; k++) {//3
                if (i + k > 4 || j - k < 0)
                    break;
                if (data.getColor(j - k, i + k) == data.getSide()) {
                    if (pieceCanMove(j - k, i + k, j, i))
                        return true;
                }
            }
            for (int k = 1; k < 5; k++) {//4
                if (i - k < 0 || j - k < 0)
                    break;
                if (data.getColor(j - k, i - k) == data.getSide()) {
                    if (pieceCanMove(j - k, i - k, j, i))
                        return true;
                }
            }
        }
        return false;
    }

    public boolean fileOrRankAttackCanDefend(int x, int y,int king) {
        int kingX = king % 5, kingY = king / 5;
        int xminus = 1, yminus = 1;
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
        while (true){
            x+=xminus;
            y+=yminus;
            if(x==kingX && y==kingY)
                break;
            if(y!=kingY) { // !czy atak w pionie?
                for (int k = 0; k < 5; k++) { //pion
                    if (k == y)
                        continue;
                    if (data.getColor(x, k) == data.getSide()) {
                        if (pieceCanMove(x, k, x, y))
                            return true;
                    }
                }
            }
            if(x!=kingX) {// !czy atak w poziomie?
                for (int k = 0; k < 5; k++) { //poziom
                    if (k == x)
                        continue;
                    if (data.getColor(k, y) == data.getSide()) {
                        if (pieceCanMove(k, y, x, y))
                            return true;
                    }
                }
            }
            //skosy
            for (int k = 1; k < 5; k++) {//1
                if (y + k > 4 || x + k > 4)
                    break;
                if (data.getColor(x + k, y + k) == data.getSide()) {
                    if (pieceCanMove(x + k, y + k, x, y))
                        return true;
                }
            }
            for (int k = 1; k < 5; k++) {//2
                if (y - k < 0 || x + k > 4)
                    break;
                if (data.getColor(x + k, y - k) == data.getSide()) {
                    if (pieceCanMove(x + k, y - k, x, y))
                        return true;
                }
            }
            for (int k = 1; k < 5; k++) {//3
                if (y + k > 4 || x - k < 0)
                    break;
                if (data.getColor(x - k, y + k) == data.getSide()) {
                    if (pieceCanMove(x - k, y + k, x, y))
                        return true;
                }
            }
            for (int k = 1; k < 5; k++) {//4
                if (y - k < 0 || x - k < 0)
                    break;
                if (data.getColor(x - k, y - k) == data.getSide()) {
                    if (pieceCanMove(x - k, y - k, x, y))
                        return true;
                }
            }
        }
        return false;
    }

    public void pawnStalemate(int x, int y, int tab[][]) {
        if(tab[x][y]!=-1){
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
        if (x + 1 < 5) {
            if (data.getColor(x + 1, y) == 0) { //pole wolne
                tab[x + 1][y + minus] = -1;
            } else if (data.getColor(x + 1, y) == data.getXside()) {
                tab[x + 1][y + minus] = -1;
            }
        }
    }

    public void knightStalemate(int x, int y, int tab[][]) {
        if(tab[x][y]!=-1){
            tab[x][y] = 0;
        }
        if (x + 1 < 5) {
            if (y + 2 < 5) {
                if (data.getColor(x + 1, y + 2) != data.getSide()) {
                    tab[x + 1][y + 2] = -1;
                }
            }
            if (y - 2 > -1) {
                if (data.getColor(x + 1, y - 2) != data.getSide()) {
                    tab[x + 1][y - 2] = -1;
                }
            }
            if (x + 2 < 5) {
                if (y + 1 < 5) {
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
            if (y + 2 < 5) {
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
                if (y + 1 < 5) {
                    if (data.getColor(x - 2, y + 1) != data.getSide()) {
                        tab[x - 2][y + 1] = -1;
                    }
                }
                if (y - 1 > -1) {
                    if (data.getColor(x - 2, y + 1) != data.getSide()) {
                        tab[x - 2][y - 1] = -1;
                    }
                }
            }
        }
    }

    public void bishopStalemate(int x, int y, int tab[][],int kingPos) {
        if(tab[x][y]!=-1){
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
        }
        if (x + y == kingX + kingY) {
            if (x - kingX > 0) {
                rightUp = true;
            } else {
                leftDown = true;
            }
        }
        //1 rightDown
        for (int i = 1; x + i < 5 && y + i < 5; i++) {
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
                    for (int j = i + 1; x + j < 5 && y + j < 5; j++) {
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
        for (int i = 1; x - i > -1 && y + i < 5; i++) {
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
                    for (int j = i + 1; x - j > -1 && y + j < 5; j++) {
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
        for (int i = 1; x + i < 5 && y - i > -1; i++) {
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
                    for (int j = i + 1; x + j < 5 && y - j > -1; j++) {
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

    public void rookStalemate(int x, int y, int tab[][],int kingPos) {
        if(tab[x][y]!=-1){
            tab[x][y] = 0;
        }
        int piece, color, side = data.getSide(), xside = data.getXside();
        int kingX = kingPos % 5, kingY = kingPos / 5;
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
        for (int i = 1; x + i < 5; i++) {
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
                    for (int j = i + 1; x + j < 5; j++) {
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
        for (int i = 1; y + i < 5; i++) {
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
                    for (int j = i + 1; y + j < 5; j++) {
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

    public void kingStalemate(int x, int y, int tab[][]){
        tab[x][y] = 0;
        int piece, color, side = data.getSide(), xside = data.getXside();
        for (int i = -1; i < 2; i++) {//y
            if(y+i<0 || y+i>4)
                continue;
            for (int j = -1; j < 2; j++) {//x
                if((i==0 && j==0) || x+j<0 || x+j>4)
                    continue;
                color = data.getColor(x+j,y+i);
                if(color==0 || color == xside){
                    tab[x+j][y+i] = -1;
                }
            }
        }
    }

    public boolean pawnCanMove(int x, int y) {
        int yminus = 1;
        if (data.getSide() == 1) { //czy biale?
            yminus = -1;
        }
        if (data.getColor(x, y + yminus) == 0)  //pole przed pionem wolne?
            return true;
        if ((x + 1 < 5 && data.getColor(x + 1, y + yminus) == data.getXside())
                || (x - 1 > -1 && data.getColor(x - 1, y + yminus) == data.getXside())) {
            return true;//czy mozna bic?
        }
        return false;
    }

    public boolean knightCanMove(int x, int y) {
        int side = data.getSide();
        if (x + 1 < 5) {
            if (y + 2 < 5) {
                if (data.getColor(x + 1, y + 2) != side)
                    return true;
            }
            if (y - 2 > -1) {
                if (data.getColor(x + 1, y - 2) != side)
                    return true;
            }
            if (x + 2 < 5) {
                if (y + 1 < 5) {
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
            if (y + 2 < 5) {
                if (data.getColor(x - 1, y + 2) != side)
                    return true;
            }
            if (y - 2 > -1) {
                if (data.getColor(x - 1, y - 2) != side)
                    return true;
            }
            if (x - 2 > -1) {
                if (y + 1 < 5) {
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

    public boolean bishopCanMove(int x, int y) {
        boolean leftUp, rightUp, rightDown, leftDown;
        leftUp = leftDown = rightDown = rightUp = true;
        int site = data.getSide();
        for (int i = 0; i < 5; i++) {
            if (x + i < 5) {
                if (rightDown && y + i < 5) {
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
                if (leftDown && y + i < 5) {
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

    public boolean rookCanMove(int x, int y) {
        boolean up, right, down, left;
        up = right = down = left = true;
        int site = data.getSide();
        for (int i = 0; i < 5; i++) {
            if (right && x + i < 5) {
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
            if (down && y + i < 5) {
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

    public boolean kingCanMove(int x, int y,int tab[][]) {
        for (int i = -1; i < 2; i++) {//y
            if (y + i < 0 || y + i > 4)
                continue;
            for (int j = -1; j < 2; j++) {//x
                if ((i == 0 && j == 0) || x + j < 0 || x + j > 4)
                    continue;
                if (tab[x + j][y + i] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isStalemate() {
        int king = findKing(true);
        int tab[][] = new int[5][5]; // -1 atakowane(pole wolne i przeciwnika); 0 jak -1 tylko nieatakowane
        for (int i = 0; i < 5; i++) {// 1 nasze niezwiazane; 2 nasze zwiazane ale moze zabic wiazacego
            for (int j = 0; j < 5; j++) {// 3 jak 2 tylko bez mozliwosci zabicia
                tab[i][j] = 1;
            }
        }
        int xside = data.getXside(), side = data.getSide(), color, piece;
        for (int i = 0; i < 5; i++) {//y
            for (int j = 0; j < 5; j++) {//x
                color = data.getColor(j, i);
                if (color == side) {
                    continue;
                } else if (color == xside) {
                    if (tab[j][i] == 1)
                        tab[j][i] = 0;
                    piece = data.getPiece(j, i);
                    switch (piece) {
                        case 1: {//pion
                            pawnStalemate(j, i, tab);
                            break;
                        }
                        case 2: {//kuc
                            knightStalemate(j, i, tab);
                            break;
                        }
                        case 3: {
                            bishopStalemate(j, i, tab, king);
                            break;
                        }
                        case 4: {
                            rookStalemate(j, i, tab, king);
                            break;
                        }
                        case 5: {
                            rookStalemate(j, i, tab, king);
                            bishopStalemate(j, i, tab, king);
                            break;
                        }
                        case 6: {
                            kingStalemate(j, i, tab);
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
        for (int i = 0; i < 5; i++) {//y
            for (int j = 0; j < 5; j++) {//x
                if (tab[j][i] < 1 || tab[j][i] == 3) //-1,0,3
                    continue;
                if (tab[j][i] == 2)//2
                    return false;
                piece = data.getPiece(j, i);//1
                switch (piece) {
                    case 1: {//pion
                        if (pawnCanMove(j, i)) {
                            return false;
                        }
                        break;
                    }
                    case 2: {//kuc
                        if (knightCanMove(j, i)) {
                            return false;
                        }
                        break;
                    }
                    case 3: {//goniec
                        if (bishopCanMove(j, i)) {
                            return false;
                        }
                        break;
                    }
                    case 4: {//wieza
                        if (rookCanMove(j, i)) {
                            return false;
                        }
                        break;
                    }
                    case 5: {//hetman
                        if (rookCanMove(j, i) || bishopCanMove(j, i)) {
                            return false;
                        }
                        break;
                    }
                    case 6: {//krol
                        if (kingCanMove(j, i, tab)) {
                            return false;
                        }
                        break;
                    }
                }
            }
        }
        return true;
    }

    public boolean isCheckmate(){
        //sprawdzenie czy król może się poruszyć
        int king = findKing(true);
        int kingX = king % 5, kingY = king/5;
        int attackerCount=0,attackerX=0,attackerY=0,attacker=0;
        for (int i = -1; i < 2; i++) {
            if(kingX+i<5 && kingX+i>-1) { //czy sprawdzany potencjalny ruch nie jest za szachownice
                for (int j = -1; j < 2; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    } else {
                        if (kingY + j < 5 && kingY + j > -1) { // tak jak dla x
                            if (kingMoveIsCorrect(kingX, kingY, kingX + i, kingY + j))
                                return false; //bo krol ma mozliwosc ruchu na nieatakowane pole
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 5; i++) {//y
            for (int j = 0; j < 5; j++) {//x
                if(data.getColor(j,i)==data.getXside()){
                    if(pieceAttackKing(j,i,king)){
                        attacker=data.getPiece(j,i);
                        attackerX=j;
                        attackerY=i;
                        attackerCount++;
                        if(attackerCount > 1) {
                            return true; //bo krol nie moze się ruszyc a atakowany jest przez 2 figury
                        }
                    }
                }
            }
        }
        if(attackerCanKilled(attackerX, attackerY)){
            return false;
        }
        switch (attacker){
            case 1:
            case 2:
                return true;
            case 3: {
                if (diagonalAttackCanDefend(attackerX, attackerY, king))
                    return false;
                break;
            }
            case 4: {
                if (fileOrRankAttackCanDefend(attackerX, attackerY, king))
                    return false;
                break;
            }
            case 5: {
                if (attackerX == kingX || attackerY == kingY) {
                    if (fileOrRankAttackCanDefend(attackerX, attackerY, king))
                        return false;
                } else if (attackerX - attackerY == kingX - kingY || attackerX + attackerY == kingX + kingY) {
                    if (diagonalAttackCanDefend(attackerX, attackerY, king))
                        return false;
                }
            }
        }
        return true;
    }

    public MoveCorrectEnum isMoveCorrect(int x1, int y1, int x2, int y2) { // kompletne (docelowo) sprawdzenie poprawności ruchu
        if (x1 == x2 && y1 == y2)
            return MoveCorrectEnum.FAIL;
        if (data.getColor(x2, y2) == data.getSide())
            return MoveCorrectEnum.FAIL;
        int piece1 = data.getPiece(x1, y1);
        if (data.getColor(x1, y1) != data.getSide()) //czy wybrana bierka jest przeciwnika lub puste pole
            return MoveCorrectEnum.FAIL;
        switch (piece1) { //sprawdzanie poprawnosci ruchu dla kazdej bierki
            case 0: {//puste (brak bierki na polu)
                return MoveCorrectEnum.FAIL;
            }
            case 1: {//pion (tylko do przodu o jedno pole)
                if (!pawnMoveIsCorrect(x1, y1, x2, y2)) {
                    return MoveCorrectEnum.FAIL;
                }
                break;
            }
            case 2: {//kon ruch typu 2 na 1 (dwa w pionie 1 w poziomie lub dwa w poziomie 1 w pionie)
                if (!knigthMoveIsCorrect(x1, y1, x2, y2)) {
                    return MoveCorrectEnum.FAIL;
                }
                break;
            }
            case 3: {//goniec
                if (!bishopMoveIsCorrect(x1, y1, x2, y2)) {
                    return MoveCorrectEnum.FAIL;
                }
                break;
            }
            case 4: {//wieza
                if (!rookMoveIsCorrect(x1, y1, x2, y2)) {
                    return MoveCorrectEnum.FAIL;
                }
                break;
            }
            case 5: {//hetman
                if (!queenMoveIsCorrect(x1, y1, x2, y2)) {
                    return MoveCorrectEnum.FAIL;
                }
                break;
            }
            case 6: {//krol
                if (!kingMoveIsCorrect(x1, y1, x2, y2)) {
                    return MoveCorrectEnum.FAIL;
                }
                break;
            }
        }
        if (data.getIsCheck()) {//w przypadku szacha
            data.makeMove(x1, y1, x2, y2);
            if (isCheckAfterMove(false)) {//sprawdzenie czy nadal król szachowany false bo makeMove zmienia stronę na ruchu
                data.undoMove();
                return MoveCorrectEnum.FAIL;// i zrócenie niepoprawności ruchu
            } else {
                data.setIsCheck(isCheckAfterMove(true));
                boolean mate = false;
                boolean promotion = isPromotionAfterMove();
                if (!promotion) {
                    if (data.getIsCheck()) {
                        mate = isCheckmate();
                    } else {
                        mate = isStalemate();
                    }
                }
                data.undoMove();
                if (promotion) {
                    return MoveCorrectEnum.PROMOTION;
                } else {
                    if (data.getIsCheck()) {
                        if (mate) {
                            return MoveCorrectEnum.CHECKMATE;
                        } else {
                            return MoveCorrectEnum.CHECK;
                        }
                    } else {
                        if (mate) {
                            return MoveCorrectEnum.STALEMATE;
                        } else {
                            return MoveCorrectEnum.GOOD;
                        }
                    }
                }
            }
        }
        //todo spytać o zachowywanie sekwencji ruchów
        data.makeMove(x1, y1, x2, y2);//wykonanie ruchu
        if (isCheckAfterMove(false)) {
            data.undoMove();
            return MoveCorrectEnum.FAIL;
        }
        data.setIsCheck(isCheckAfterMove(true));//sprawdzanie czy po ruchu jest szach i zmiana wartosci pola w data.
        boolean mate = false;
        boolean promotion = isPromotionAfterMove();
        if (!promotion) {
            if (data.getIsCheck()) {
                mate = isCheckmate();
            } else {
                mate = isStalemate();
            }
        }
        data.undoMove();// cofnięcie ruchu
        if (promotion) {
            return MoveCorrectEnum.PROMOTION;
        } else {
            if (data.getIsCheck()) {
                if (mate) {
                    return MoveCorrectEnum.CHECKMATE;
                } else {
                    return MoveCorrectEnum.CHECK;
                }
            } else {
                if (mate) {
                    return MoveCorrectEnum.STALEMATE;
                } else {
                    return MoveCorrectEnum.GOOD;
                }
            }
        }

    }
}