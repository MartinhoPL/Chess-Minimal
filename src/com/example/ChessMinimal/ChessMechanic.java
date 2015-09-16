package com.example.ChessMinimal;
//todo przeniesc funkcje
// todo commit z tym co teraz mam
public class ChessMechanic {
    Data data; //wskaznik na klase data by moc sprawdzac polozenie pionow
    public boolean isEmpty(int x, int y){  //czy pole puste lub zajete przez figure drugiego gracza
        if(data.getPiece(x,y)!=0){
            if(data.getSide()==data.getColor(x,y))
                return false;
        }
        return true;
    }
    public int findMyKing(){ // jak w nazwie
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++)
                if(data.getPiece(i,j)==6)
                    if(data.getSide()==data.getColor(i,j))
                        return i*5+j;
        }
        return -1;
    }
    public int findNotMyKing(){
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++)
                if(data.getPiece(i,j)==6)
                    if(data.getSide()!=data.getColor(i,j))
                        return i*5+j;
        }
        return -1;
    }
    public boolean kingInMyColummn(int a,int y){
        if(a%5==y)
            return true;
        return false;
    }
    public boolean kingInMyRow(int a,int x){
        if(a/5==x)
            return true;
        return false;
    }
    public boolean kingInMyDiagonal(int a,int x, int y){
        int kingX=a/5;
        int kingY=a%5;
        if(kingX-x==kingY-y || kingX-x==y-kingY)
            return true;
        return false;
    }
    public boolean pieceBetweenMeKingInFile(int a, int x, int y){//czy cos jest miedzy przesuwana figura a krolem w kolumnie
        int kingX=a/5;
        if(kingX>x) {
            if (kingX - x == 1)
                return false;
            for(int i=x+1;i<kingX;i++){
                if(data.getPiece(i,y)!=0){
                    return true;
                }
            }
        }else{
            if(x - kingX == -1)
                return false;
            for(int i=kingX+1;i<x;i++){
                if(data.getPiece(i,y)!=0){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean pieceBetweenMeKingInRank(int a, int x, int y){//czy cos jest miedzy przesuwana figura a krolem we wierszu
        int kingY=a%5;
        if(kingY>y) {
            if (kingY - y == 1)
                return false;
            for(int i=y+1;i<kingY;i++){
                if(data.getPiece(x,i)!=0){
                    return true;
                }
            }
        }else{
            if(y - kingY == -1)
                return false;
            for(int i=kingY+1;i<y;i++){
                if(data.getPiece(x,i)!=0){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean pieceBetweenMeKingInDiagonal(int a, int x, int y){
        int kingX=a/5, kingY=a%5;
        if(kingX>x){
                if(kingX-x==1){
                    return false;
                }else {
                    if(kingY>y){
                        for (int i = 1; i+x < kingX ; i++) {
                            if(data.getPiece(x+i,y+i)!=0)
                                return true;
                        }
                    }else {
                        for (int i = 1; i+x < kingX; i++) {
                            if(data.getPiece(x+i,y-i)!=0)
                                return true;
                        }
                    }
                }
        }else {
            if(kingX-x==-1){
                return false;
            }else {
                if(kingY>y){
                    for (int i = 1; x-i < kingX ; i++) {
                        if(data.getPiece(x-i,y+i)!=0)
                            return true;
                    }
                }else {
                    for (int i = 1; x-i < kingX; i++) {
                        if(data.getPiece(x-i,y-i)!=0)
                            return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean kingIsCheck(int x1, int y1, int x2 , int y2){ // sprawdzenie czy nie odslaniamy krola
        int a=findMyKing();
        if(kingInMyColummn(a,y1)){
            if(pieceBetweenMeKingInFile(a, x1, y1)){
                return false;
            }else{
                int kingX = a/5;
                if(kingX>x1){
                    for (int i = x1-1; i > -1 ; i--) {
                        int z=data.getPiece(i,y1);
                        if(z==4 || z== 5) return true;
                    }
                }
                else{
                    for (int i = x1+1; i <5 ; i++) {
                        int z=data.getPiece(i,y1);
                        if(z==4 || z== 5) return true;
                    }
                }
                return false;
                //hetman i wieza w kolumnie
            }
        }else
        if(kingInMyRow(a,x1)){
            if(pieceBetweenMeKingInRank(a,x1,y1)){
                return false;
            }else {
                int kingY = a%5;
                if(kingY>y1){
                    for (int i = y1-1; i > -1 ; i--) {
                        int z=data.getPiece(x1,i);
                        if(z==4 || z== 5) return true;
                    }
                }
                else{
                    for (int i = y1+1; i <5 ; i++) {
                        int z=data.getPiece(x1,i);
                        if(z==4 || z== 5) return true;
                    }
                }
                return false;
                // hetman i wieza we wierszu
            }
        }else
        if(kingInMyDiagonal(a,x1,y1)){
            if(pieceBetweenMeKingInDiagonal(a, x1, y1)){
                return false;
            } else {
                int kingY=a%5, kingX=a/5,xminus=1,yminus=1,i=x1,j=y1;
                if(x1>kingX)xminus=-1;
                if(y1>kingY)yminus=-1;
                while(true){
                    i+=xminus;
                    j+=yminus;
                    if(i==-1 || i==5) break;
                    if(j==-1 || j==5) break;
                    int z=data.getPiece(i,j);
                    if(z==3 || z==5)
                        return true;
                }
                return false;
            }
        }
        return false;
    }
    public boolean kingMoveIsCorrect(int x,int y){
        for (int i = x+1; i < 5 ; i++) {
            int z=data.getPiece(i,y);  //czy w pionie (bezposrednio) jest wroga wieza lub hetman
            if((z==4 || z==5) && data.getColor(i,y)!=data.getSide())
                return false;
            if(z!=0)
                break;
        }
        for (int i = x-1; i >-1 ; i--) {
            int z=data.getPiece(i,y);  // jw.
            if((z==4 || z==5) && data.getColor(i,y)!=data.getSide())
                return false;
            if(z!=0)
                break;
        }
        for (int i = y+1; i < 5; i++) {
            int z=data.getPiece(x,i);  // czy w poziomie (bezposrednio) jest wroga wieza lub hetman
            if((z==4 || z==5) && data.getColor(x,i)!=data.getSide())
                return false;
            if(z!=0)
                break;
        }
        for (int i = y-1; i >-1; i++) {
            int z=data.getPiece(x,i);  // jw.
            if((z==4 || z==5) && data.getColor(x,i)!=data.getSide())
                return false;
            if(z!=0)
                break;
        }
        //skosy czy wrogi hetman lub goniec
        boolean skosy[]=new boolean[4];
        for (int i = 0; i <4 ; i++) {
            skosy[i]=true;
        }
        for (int i = 1; i < 5; i++) {
            int z=0;
            if(y-i>-1){
                if(x-i>-1 && skosy[0]){
                    z=data.getPiece(x-i,y-i);
                    if((z==3 || z==5) && data.getColor(x-i,y-i)!=data.getSide())
                        return false;
                    if(z!=0)
                        skosy[0]=false;
                }
                if(x+i<5 && skosy[1]){
                    z=data.getPiece(x+i,y-i);
                    if((z==3 || z==5) && data.getColor(x+i,y-i)!=data.getSide())
                        return false;
                    if(z!=0)
                        skosy[1]=false;
                }
            }
            if(y+i<5){
                if(x-i>-1 && skosy[2]){
                    z=data.getPiece(x-i,y+i);
                    if((z==3 || z==5) && data.getColor(x-i,y+i)!=data.getSide())
                        return false;
                    if(z!=0)
                        skosy[2]=false;
                }
                if(x+i<5 && skosy[3]){
                    z=data.getPiece(x+i,y+i);
                    if((z==3 || z==5) && data.getColor(x+i,y+i)!=data.getSide())
                        return false;
                    if(z!=0)
                        skosy[3]=false;
                }
            }
        }
        //todo czy kuce atakujo po ruchu krola + pion + drugi krol
        return true;//false;
    }
    public boolean isMoveCorrect(int x1, int y1, int x2, int y2){ // kompletne (docelowo) sprawdzenie poprawno�ci ruchu
        int a=data.getPiece(x1,y1);
        if(data.getColor(x1,y1)!=data.getSide()) //wybrana bierka jest przeciwnika
            return false;
        switch (a) { //sprawdzanie poprawnosci ruchu dla kazdej bierki
            case 0: {//puste (brak bierki na polu)
                return false;
            }
            case 1: {//pion (tylko do przodu o jedno pole)
                if (data.getSide() == 1) {
                    if (x1 - x2 != 1)
                        return false;
                } else {
                    if (x2 - x1 != 1)
                        return false;
                }
            }
            case 2: {//kon  ruch typu 2 na 1 (dwa w pionie 1 w poziomie lub dwa w poziomie 1 w pionie)
                if (x1 > x2) {
                    if (y1 > y2) {
                        if ((x1 - x2 == 1 && y1 - y2 == 2) || (x1 - x2 == 2 && y1 - y2 == 1)) {
                            break;
                        } else return false;
                    } else if ((x1 - x2 == 1 && y1 - y2 == -2) || (x1 - x2 == 2 && y1 - y2 == -1)) {
                        break;
                    } else return false;
                } else {
                    if (y1 > y2) {
                        if ((x1 - x2 == -1 && y1 - y2 == 2) || (x1 - x2 == -2 && y1 - y2 == 1)) {
                            break;
                        } else return false;
                    } else if ((x1 - x2 == -1 && y1 - y2 == -2) || (x1 - x2 == -2 && y1 - y2 == -1)) {
                        break;
                    } else return false;

                }
            }
            case 3:{//goniec
                if(x1-x2!=y1-y2 && x1-x2!=y2-y1)
                    return false;  //czy ruch po skosie
                int xminus=1,yminus=1,i=x1,j=y1; //czy linia ruchu jest pusta
                if(x1>x2) xminus=-1; //czy do gory?
                if(y1>y2) yminus=-1;  // czy w lewo?
                while(true){
                    i+=xminus;
                    j+=yminus;
                    if(i==x2) break;
                    if(j==y2) break;
                    if(data.getPiece(i,j)!=0)
                        return false;
                }
            }
            case 4:{//wieza
                if(x1!=x2 && y1!=y2)  // ruch w pionie lub poziomie
                    return false;
                if(x1!=x2){ //czy ruch w pionie
                    int minus=1;
                    if(x1>x2) minus=-1;
                    for (int i = x1; i !=x2 ; i+=minus) {
                        if(data.getPiece(i,y1)!=0)
                            return false;
                    }
                }
                else { //ruch w poziomie
                    int minus=1;
                    if(y1>y2) minus=-1;
                    for (int i = y1; i !=y2 ; i+=minus) {
                        if(data.getPiece(x1,i)!=0)
                            return false;
                    }
                }
            }
            case 5:{//hetman
                if(x1!=x2 && y1!=y2) { // polaczenie powyzszych dwoch
                    if (x1 - x2 != y1 - y2 && x1 - x2 != y2 - y1)
                        return false;
                    else{
                        int xminus=1,yminus=1,i=x1,j=y1; //czy linia ruchu jest pusta (tu skos)
                        if(x1>x2) xminus=-1;
                        if(y1>y2) yminus=-1;
                        while(true){
                            i+=xminus;
                            j+=yminus;
                            if(i==x2) break;
                            if(j==y2) break;
                            if(data.getPiece(i,j)!=0)
                                return false;
                        }

                    }
                }
                else{
                    if(x1!=x2){ //czy linia ruchu jest pusta (tu pion lub poziom)
                        int minus=1;
                        if(x1>x2) minus=-1;
                        for (int i = x1; i !=x2 ; i+=minus) {
                            if(data.getPiece(i,y1)!=0)
                                return false;
                        }
                    }
                    else {
                        int minus=1;
                        if(y1>y2) minus=-1;
                        for (int i = y1; i !=y2 ; i+=minus) {
                            if(data.getPiece(x1,i)!=0)
                                return false;
                        }
                    }
                }
            }
            // todo czy krol nie wchodzi pod bicie
            case 6:{//krol
                if(x1-x2>1 || x1-x2<-1)
                    return false;
                if(y1-y2>1 || y1-y2<-1)
                    return false;  // bo ruch tylko o jedno pole

            }
        }//todo sprawdzanie pozniejsze co jeszcze sprawdzac?
        if(isEmpty(x2,y2)) {  //czy pole ruchu jest wolne lub zajete przez przeciwnika
            if(kingIsCheck(x1,y1,x2,y2)) { // czy odslaniamy krola
                return false;
            }
        }
        return false;
    }
    //todo sprawdzanie przy szachu czy ruch poprawny
    //todo sprawdzanie czy po ruchu jest szach
}