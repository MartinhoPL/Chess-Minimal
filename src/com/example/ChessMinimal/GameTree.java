package com.example.ChessMinimal;

public class GameTree {

    private static final int MAX_MOVES = 1000000;

    private byte [][]moves;

    private int movesIndex;

    private int nodeChildrenArrayIndex;

    private int []nodeChildren;

    private int []nodeFather;

    private Data data;

    private ChessMechanic chessMechanic;

    GameTree(Data data) {
        moves = new byte[MAX_MOVES][4];
        nodeChildren = new int[MAX_MOVES];
        nodeFather = new int[MAX_MOVES];
        this.data = data;
        this.chessMechanic = new ChessMechanic(data);
        nodeChildrenArrayIndex = 0;
        movesIndex = 0;
    }

    public void generateGameTree(int depth) {
        if(depth == 0) {
            return;
        }
        Data generatedData = data;
        if(movesIndex == 0) {
            byte []move = new byte[4];
            nodeFather[movesIndex] = -1;
            moves[movesIndex++] = move;
            nodeChildren[nodeChildrenArrayIndex++] = 1;
        }
        int lastMoveIndex = movesIndex;
        generateGameTreeLevel(generatedData, 0);
        if(lastMoveIndex != movesIndex) {
            nodeChildren[nodeChildrenArrayIndex++] = movesIndex;
        }
        int poczatek = nodeChildren[nodeChildrenArrayIndex - 2];
        int koniec = nodeChildren[nodeChildrenArrayIndex - 1];
        while (depth > 0) {
            for (int i = poczatek; i < koniec; i++) {
                makeAllMovesToNextPosition(getPathToTheRoot(i), generatedData);

                lastMoveIndex = movesIndex;
                generateGameTreeLevel(generatedData, i);
                if(lastMoveIndex != movesIndex) {
                    nodeChildren[nodeChildrenArrayIndex++] = movesIndex;
                }
                undoAllMovesToPreviousPosition(getPathToTheRoot(i), generatedData);
            }
            poczatek = koniec + 1;
            koniec = nodeChildren[nodeChildrenArrayIndex - 1];
            depth--;
        }
        int xxx= movesIndex;
    }

    private void generateGameTreeLevel(Data generatedData, int father) {
        for(int i = 0; i < 5; i++){
            for(int j = 0 ; j < 5; j++) {
                if(generatedData.color[generatedData.calculateArrayIndexForCoords(j, i)] == generatedData.getSide()) {
                    switch (generatedData.getPiece(j, i))
                    {
                        case 1: {
                            generatePawnMoves(j, i, generatedData, father);
                            break;
                        }
                        case 2: {
                            generateKnightMoves(j, i, generatedData, father);
                            break;
                        }
                        case 3:{
                            generateBishopMoves(j, i, generatedData, father);
                            break;
                        }
                        case 4: {
                            generateRookMoves(j, i, generatedData, father);
                            break;
                        }
                        case 5: {
                            generateQueenMoves(j, i, generatedData, father);
                            break;
                        }
                        case 6:{
                            generateKingMoves(j, i, generatedData, father);
                            break;
                        }
                    }
                }
            }
        }

    }

    private boolean generateRookMoves(int x, int y, Data generatedData, int father){
        boolean wynik = false;
        byte []move;
        for(int i=0; i<5; i++){
            int destX = i;
            move = generateMoveFromTo(x, y, destX, y, generatedData, father);
            if(move != null) {
                moves[movesIndex++] = move;
                wynik = true;
            }
            int destY = i;
            move = generateMoveFromTo(x, y, x, destY, generatedData, father);
            if(move != null) {
                moves[movesIndex++] = move;
                wynik = true;
            }
        }
        return wynik;

    }

    private boolean generateBishopMoves(int x, int y, Data generatedData, int father){
        boolean wynik = false;
//        byte []move;
//        for(int i=0; i<5; i++){
//            int destX = i;
//            move = generateMoveFromTo(x, y, destX, y, generatedData);
//            if(generateMoveFromTo(x, y, destX, y, generatedData) != null) {
//                moves[movesIndex++] = generateMoveFromTo(x, y, destX, y, generatedData);
//                wynik = true;
//            }
//            int destY = i;
//            move = generateMoveFromTo(x, y, x, destY, generatedData);
//            if(generateMoveFromTo(x, y, x, destY, generatedData) != null) {
//                moves[movesIndex++] = generateMoveFromTo(x, y, destX, y, generatedData);
//                wynik = true;
//            }
//        }
        return wynik;

    }
    private boolean generateKingMoves(int x, int y, Data generatedData, int father){
        int destX = x + 1;
        boolean wynik = false;
        byte []move;

        move = generateMoveFromTo(x, y, destX, y, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        int destY = y + 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destX = x - 1;
        move = generateMoveFromTo(x, y, destX, y, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y + 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        move = generateMoveFromTo(x, y, destX, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        move = generateMoveFromTo(x, y, destX, y, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        return wynik;

    }
    private boolean generateQueenMoves(int x, int y, Data generatedData, int father){
        return (generateBishopMoves(x, y, generatedData, father) || generateRookMoves(x, y, generatedData, father));
    }
    private boolean generatePawnMoves(int x, int y, Data generatedData, int father){
        int destY;
        boolean wynik = false;
        byte []move;

        if(data.getSide() == 1) {
            destY = y - 1;
        } else {
            destY = y + 1;
        }
        move = generateMoveFromTo(x, y, x, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }

        int destX = x + 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }

        destX = x - 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        return wynik;

    }
    private boolean generateKnightMoves(int x, int y, Data generatedData, int father){
        int destX, destY;
        boolean wynik = false;
        byte []move;

        destX = x + 2;
        destY = y + 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destX = x - 2;
        destY = y + 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destX = x + 1;
        destY = y + 2;
        move = generateMoveFromTo(x, y, destX, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 2;
        move = generateMoveFromTo(x, y, destX, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData, father);
            wynik = true;
        }
        destX = x - 1;
        destY = y + 2;
        move = generateMoveFromTo(x, y, destX, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 2;
        move = generateMoveFromTo(x, y, destX, destY, generatedData, father);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        return wynik;
    }

    private byte[] generateMoveFromTo(int x, int y, int destX, int destY, Data generatedData, int father) {
        byte[] move = new byte[4];
        boolean moveCorrect = true;
        switch (chessMechanic.isMoveCorrect(x, y, destX, destY)){
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
                move[3] = 5;
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
        if(generatedData.getCapture() != 0) {
            move[2] = 8;
        }
        if (moveCorrect) {
            move[0] = (byte)data.calculateArrayIndexForCoords(x, y);
            move[1] = (byte)data.calculateArrayIndexForCoords(destX, destY);
            nodeFather[movesIndex] = father;
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

    private void makeAllMovesToNextPosition(int[] moveSequence, Data data) {
        for (int i = moveSequence.length - 1; i >= 0; i--) {
            if(moveSequence[i] != 0) {
                data.makeMove(moves[moveSequence[i]], data);
            }
        }
    }

    private void undoAllMovesToPreviousPosition(int[] moveSequence, Data data) {
        for (int i = 0; i < moveSequence.length; i++) {
            if(moveSequence[i] == 0){
                break;
            }
            data.undoMove(moves[moveSequence[i]], data);
        }
    }
    private boolean isTransposition() {
        return false;
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

    public ChessMechanic getChessMechanic() {
        return chessMechanic;
    }

    public void setChessMechanic(ChessMechanic chessMechanic) {
        this.chessMechanic = chessMechanic;
    }
}
