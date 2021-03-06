package com.example.ChessMinimal;

public class GameTree {

    private static final int MAX_MOVES = 100000;
    private static final int MAX_TRANSPOSITIONS = 100000;
    private static final int HASH_NUMBER = 12289;

    private byte [][]moves;

    private int [][]transpositons;

    private int movesIndex;

    private int nodeChildrenArrayIndex;

    private int []nodeChildren;

    private int []nodeFather;

    private Data data;

    private boolean checkTransposition;

    GameTree(Data data) {
        moves = new byte[MAX_MOVES][4];
        nodeChildren = new int[MAX_MOVES];
        transpositons = new int[MAX_TRANSPOSITIONS][3];
        nodeFather = new int[MAX_MOVES];
        this.data = data.clone();
        nodeChildrenArrayIndex = 0;
        movesIndex = 0;
    }

    public void generateGameTree(int depth, Data data) {
        resetGameTreeFields(data);
        int maxDepth = depth;
        if(movesIndex == 0) {
            byte []move = new byte[4];
            nodeFather[movesIndex] = -1;
            moves[movesIndex++] = move;
            nodeChildren[nodeChildrenArrayIndex++] = 1;
        }
        int lastMoveIndex = movesIndex;
        generateGameTreeLevel(data, 0);
        depth--;
        if(lastMoveIndex != movesIndex) {
            nodeChildren[nodeChildrenArrayIndex++] = movesIndex;
        }
        int poczatek = nodeChildren[nodeChildrenArrayIndex - 2];
        int koniec = nodeChildren[nodeChildrenArrayIndex - 1];
        while (depth > 0) {
            if(maxDepth - depth >= 2) {
                checkTransposition = true;
            } else {
                checkTransposition = false;
            }
            for (int i = poczatek; i < koniec; i++) {
                if(moves[i][2] != 2 && moves[i][2] != 16) {

                    makeAllMovesToNextPosition(getPathToTheRoot(i), data);

                    lastMoveIndex = movesIndex;
                    generateGameTreeLevel(data, i);
                    if (lastMoveIndex != movesIndex) {
                        nodeChildren[nodeChildrenArrayIndex++] = movesIndex;
                    }
                    undoAllMovesToPreviousPosition(getPathToTheRoot(i), data);
                } else {
                    nodeChildren[nodeChildrenArrayIndex - 1] = -1;
                    nodeChildren[nodeChildrenArrayIndex++] = movesIndex;
                }
            }
            poczatek = koniec;
            koniec = nodeChildren[nodeChildrenArrayIndex - 1];
            depth--;

        }

        int pom = nodeChildrenArrayIndex - 1;
        nodeChildrenArrayIndex--;
        boolean noSpecialMoves = false;
        while(!noSpecialMoves) {// poglebienie drzewa gry
            for(int i = poczatek; i < koniec; i++) {
                    if (moves[i][2] == 4 || moves[i][2] == 8) {
                        makeAllMovesToNextPosition(getPathToTheRoot(i), data);

                        lastMoveIndex = movesIndex;
                        generateGameTreeLevel(data, i);
                        if (lastMoveIndex != movesIndex) {
                            nodeChildren[nodeChildrenArrayIndex++] = lastMoveIndex;
                        }
                        undoAllMovesToPreviousPosition(getPathToTheRoot(i), data);
                    } else {
                        nodeChildren[nodeChildrenArrayIndex++] = -1;
                    }
            }
            poczatek = koniec;
            int j = 1;
            while (nodeChildren[nodeChildrenArrayIndex - j] == -1) {
                j++;
            }
            koniec = nodeChildren[nodeChildrenArrayIndex - j];
            if(poczatek >= koniec) {
                noSpecialMoves = true;
            }
        }
        for(int i = koniec; i < movesIndex; i++){
            nodeChildren[nodeChildrenArrayIndex++] = -1;
        }
    }

    private void generateGameTreeLevel(Data data, int father) {
        for(int i = 0; i < Fixed.YHEIGHT; i++){
            for(int j = 0 ; j < Fixed.XWIDTH; j++) {
                if(data.color[data.calculateArrayIndexForCoords(j, i)] == data.getSide()) {
                    switch (data.getPiece(j, i))
                    {
                        case 1: {
                            generatePawnMoves(j, i, data, father);
                            break;
                        }
                        case 2: {
                            generateKnightMoves(j, i, data, father);
                            break;
                        }
                        case 3:{
                            generateBishopMoves(j, i, data, father);
                            break;
                        }
                        case 4: {
                            generateRookMoves(j, i, data, father);
                            break;
                        }
                        case 5: {
                            generateQueenMoves(j, i, data, father);
                            break;
                        }
                        case 6:{
                            generateKingMoves(j, i, data, father);
                            break;
                        }
                    }
                }
            }
        }

    }

    private boolean generateRookMoves(int x, int y, Data data, int father){
        boolean wynik = false;
        byte []move;
        for(int i=0; i<5; i++){
            int destX = i;
            move = generateMoveFromTo(x, y, destX, y, data, father);
            if(move != null) {
                moves[movesIndex++] = move;
                wynik = true;
            }
            int destY = i;
            move = generateMoveFromTo(x, y, x, destY, data, father);
            if(move != null) {
                moves[movesIndex++] = move;
                wynik = true;
            }
        }
        return wynik;

    }

    private boolean generateBishopMoves(int x, int y, Data data, int father){
        boolean wynik = false;
        byte []move;
        for(int i=0; i<5; i++){
            int destX = x - i;
            int destY = y - i;
            move = generateMoveFromTo(x, y, destX, destY, data, father);
            if(move != null) {
                moves[movesIndex++] = move;
                wynik = true;
            }
            destX = x + i;
            destY = y - i;
            move = generateMoveFromTo(x, y, destX, destY, data, father);
            if(move != null) {
                moves[movesIndex++] = move;
                wynik = true;
            }
            destY = y + i;
            destX = x - i;
            move = generateMoveFromTo(x, y, destX, destY, data, father);
            if(move != null) {
                moves[movesIndex++] = move;
                wynik = true;
            }
            destX = x + i;
            destY = y + i;
            move = generateMoveFromTo(x, y, destX, destY, data, father);
            if(move != null) {
                moves[movesIndex++] = move;
                wynik = true;
            }
        }
        return wynik;

    }
    private boolean generateKingMoves(int x, int y, Data data, int father){
        int destX = x + 1;
        boolean wynik = false;
        byte []move;

        move = generateMoveFromTo(x, y, destX, y, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        int destY = y + 1;
        move = generateMoveFromTo(x, y, destX, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 1;
        move = generateMoveFromTo(x, y, destX, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destX = x - 1;
        move = generateMoveFromTo(x, y, destX, y, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y + 1;
        move = generateMoveFromTo(x, y, destX, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y + 1;
        move = generateMoveFromTo(x, y, x, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 1;
        move = generateMoveFromTo(x, y, destX, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        move = generateMoveFromTo(x, y, x, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        return wynik;

    }
    private boolean generateQueenMoves(int x, int y, Data data, int father){
        return (generateBishopMoves(x, y, data, father) || generateRookMoves(x, y, data, father));
    }
    private boolean generatePawnMoves(int x, int y, Data data, int father){
        int destY;
        boolean wynik = false;
        byte []move;

        if(data.getSide() == 1) {
            destY = y - 1;
        } else {
            destY = y + 1;
        }
        move = generateMoveFromTo(x, y, x, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }

        int destX = x + 1;
        move = generateMoveFromTo(x, y, destX, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }

        destX = x - 1;
        move = generateMoveFromTo(x, y, destX, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        return wynik;

    }
    private boolean generateKnightMoves(int x, int y, Data data, int father){
        int destX, destY;
        boolean wynik = false;
        byte []move;

        destX = x + 2;
        destY = y + 1;
        move = generateMoveFromTo(x, y, destX, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 1;
        move = generateMoveFromTo(x, y, destX, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destX = x - 2;
        destY = y + 1;
        move = generateMoveFromTo(x, y, destX, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 1;
        move = generateMoveFromTo(x, y, destX, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destX = x + 1;
        destY = y + 2;
        move = generateMoveFromTo(x, y, destX, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 2;
        move = generateMoveFromTo(x, y, destX, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destX = x - 1;
        destY = y + 2;
        move = generateMoveFromTo(x, y, destX, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 2;
        move = generateMoveFromTo(x, y, destX, destY, data, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        return wynik;
    }

    private byte[] generateMoveFromTo(int x, int y, int destX, int destY, Data data, int father) {
        byte[] move = new byte[4];
        boolean moveCorrect = true;
        switch (ChessMechanic.isMoveCorrect(x, y, destX, destY, data)){
            case GOOD:{
                move[2] = 0;
                break;
            }
            case CHECK:{
                move[2] = 1;
                break;
            }
            case PROMOTION:{
                move[2] = 4;
                break;
            }
            case STALEMATE:{
                move[2] = 16;
                break;
            }
            case CHECKMATE:{
                move[2] = 2;
                break;
            }
            case FAIL:{
                return null;
            }
        }
        if(data.getCapture() != 0) {
            if(move[2] != 4 && move[2] != 16 && move[2] != 2) {
                move[2] = 8;
            }
            move[3] = (byte)data.getPiece(destX,destY);
        }
        if (moveCorrect) {
            move[0] = (byte)data.calculateArrayIndexForCoords(x, y);
            move[1] = (byte)data.calculateArrayIndexForCoords(destX, destY);
            nodeFather[movesIndex] = father;
        }
        if (checkTransposition) {
            data.makeMove(move);
            if(isTransposition(data)) {
                data.undoMove(move);
                return null;
            }
            else {
                data.undoMove(move);
            }
        }
        return move;

    }

    public int[] getPathToTheRoot(int i) {
        int []path = new int[20];
        int j = 0;
        if(nodeFather[i] != -1) {
            path[j] = i;
            j++;
        }
        while (nodeFather[i] != 0) {
            path[j] = nodeFather[i];
            i = nodeFather[i];
            j++;
        }
        return path;
    }

    public void makeAllMovesToNextPosition(int[] moveSequence, Data data) {
        for (int i = moveSequence.length - 1; i >= 0; i--) {
            if(moveSequence[i] != 0) {
                data.makeMove(moves[moveSequence[i]]);
            }
        }
    }

    public void undoAllMovesToPreviousPosition(int[] moveSequence, Data data) {
        for (int i = 0; i < moveSequence.length; i++) {
            if(moveSequence[i] == 0){
                break;
            }
            data.undoMove(moves[moveSequence[i]]);
        }
    }

    private boolean isTransposition(Data data) {
        int []position = data.convertPositionToNumber();
        int index = hash(position);
        int offset = 0;
        while(true) {
            if(transpositons[index][0] == position[0] && transpositons[index][1] == position[1] && transpositons[index][2] == position[2]) {
                return true;
            } else if(transpositons[index][0] == 0 && transpositons[index][1] == 0 && transpositons[index][2] == 0) {
                break;
            }
            offset++;
            index = hash(position) + 7 * offset * offset + 7 * offset;
            if(offset > 1000) {
                return false;
            }
        }
        transpositons[index] = position;
        return false;
    }

    public int hash(int position[]) {
        int result = ((position[2] % HASH_NUMBER) + (position[1] % HASH_NUMBER) + (position[0] % HASH_NUMBER) );
        if(result < 0) {
            result = (-1) * result;
        }
        return result;
    }

    public void resetGameTreeFields(Data data){
        this.data = data.clone();
        for(int i = 0; i < MAX_TRANSPOSITIONS; i++) {
            transpositons[i][0] = 0;
            transpositons[i][1] = 0;
            transpositons[i][2] = 0;
        }
        for (int i = 0; i< MAX_MOVES; i++) {
            moves[i][0] = 0;
            moves[i][1] = 0;
            moves[i][2] = 0;
            moves[i][3] = 0;
            nodeChildren[i]= 0;
        }
        movesIndex = 0;
        nodeChildrenArrayIndex = 0;
        checkTransposition = false;
    }

    public byte[][] getMoves() {
        return moves;
    }

    public void setMoves(byte[][] moves) {
        this.moves = moves;
    }

    public int getMovesIndex() {
        return movesIndex;
    }

    public void setMovesIndex(int movesIndex) {
        this.movesIndex = movesIndex;
    }

    public int getNodeChildrenArrayIndex() {
        return nodeChildrenArrayIndex;
    }

    public void setNodeChildrenArrayIndex(int nodeChildrenArrayIndex) {
        this.nodeChildrenArrayIndex = nodeChildrenArrayIndex;
    }

    public int[] getNodeChildren() {
        return nodeChildren;
    }

    public void setNodeChildren(int[] nodeChildren) {
        this.nodeChildren = nodeChildren;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getNodeChildrenAt(int i) {return nodeChildren[i];}

    public int getNodeFatherAt(int i) {return nodeFather[i];}

    public byte[] getMovesAt(int i) {return moves[i];}

    public int findFirstNodeWithChildrens(int node) {
        for(int i = node; i < movesIndex; i++) {
            if(nodeChildren[i] != -1 && nodeChildren[i] != movesIndex){
                return nodeChildren[i];
            }
        }
        return movesIndex;
    }

}
