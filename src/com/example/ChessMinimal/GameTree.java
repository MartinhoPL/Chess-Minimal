package com.example.ChessMinimal;

public class GameTree {

    private static final int MAX_MOVES = 10000;

    private byte [][]moves;

    private int movesIndex;

    private int nodeChildrenArrayIndex;

    private int []nodeChildren;

    private Data data;

    private ChessMechanic chessMechanic;

    GameTree(Data data) {
        moves = new byte[MAX_MOVES][4];
        nodeChildren = new int[MAX_MOVES];
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
            moves[movesIndex++] = move;
            nodeChildren[nodeChildrenArrayIndex++] = 1;
        }
        int lastMoveIndex = movesIndex;
        generateGameTreeLevel(generatedData);
        if(lastMoveIndex != movesIndex) {
            nodeChildren[nodeChildrenArrayIndex++] = movesIndex;
        }
        while (depth > 0) {
            int poczatek = nodeChildren[nodeChildrenArrayIndex - 2];
            int koniec = nodeChildren[nodeChildrenArrayIndex - 1];
            for (int i = poczatek; i < koniec; i++) {
                generatedData.makeMove(moves[i], generatedData);

                lastMoveIndex = movesIndex;
                generateGameTreeLevel(generatedData);
                if(lastMoveIndex != movesIndex) {
                    nodeChildren[nodeChildrenArrayIndex++] = movesIndex;
                }

                generatedData.undoMove(moves[i], generatedData);
            }
            depth--;
        }
    }

    private void generateGameTreeLevel(Data generatedData) {
        for(int i = 0; i < 5; i++){
            for(int j = 0 ; j < 5; j++) {
                if(generatedData.color[generatedData.calculateArrayIndexForCoords(j, i)] == generatedData.getSide()) {
                    switch (generatedData.getPiece(j, i))
                    {
                        case 1: {
                            generatePawnMoves(j, i, generatedData);
                            break;
                        }
                        case 2: {
                            generateKnightMoves(j, i, generatedData);
                            break;
                        }
                        case 3:{
                            generateBishopMoves(j, i, generatedData);
                            break;
                        }
                        case 4: {
                            generateRookMoves(j, i, generatedData);
                            break;
                        }
                        case 5: {
                            generateQueenMoves(j, i, generatedData);
                            break;
                        }
                        case 6:{
                            generateKingMoves(j, i, generatedData);
                            break;
                        }
                    }
                }
            }
        }

    }

    private boolean generateRookMoves(int x, int y, Data generatedData){
        boolean wynik = false;
        byte []move;
        for(int i=0; i<5; i++){
            int destX = i;
            move = generateMoveFromTo(x, y, destX, y, generatedData);
            if(move != null) {
                moves[movesIndex++] = move;
                wynik = true;
            }
            int destY = i;
            move = generateMoveFromTo(x, y, destX, y, generatedData);
            if(move != null) {
                moves[movesIndex++] = move;
                wynik = true;
            }
        }
        return wynik;

    }

    private boolean generateBishopMoves(int x, int y, Data generatedData){
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
    private boolean generateKingMoves(int x, int y, Data generatedData){
        int destX = x + 1;
        boolean wynik = false;
        byte []move;

        move = generateMoveFromTo(x, y, destX, y, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        int destY = y + 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destX = x - 1;
        move = generateMoveFromTo(x, y, destX, y, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y + 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        move = generateMoveFromTo(x, y, destX, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        move = generateMoveFromTo(x, y, destX, y, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        return wynik;

    }
    private boolean generateQueenMoves(int x, int y, Data generatedData){
        return (generateBishopMoves(x, y, generatedData) || generateRookMoves(x, y, generatedData));
    }
    private boolean generatePawnMoves(int x, int y, Data generatedData){
        int destY;
        boolean wynik = false;
        byte []move;

        if(data.getSide() == 1) {
            destY = y - 1;
        } else {
            destY = y + 1;
        }
        move = generateMoveFromTo(x, y, x, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }

        int destX = x + 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }

        destX = x - 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        return wynik;

    }
    private boolean generateKnightMoves(int x, int y, Data generatedData){
        int destX, destY;
        boolean wynik = false;
        byte []move;

        destX = x + 2;
        destY = y + 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destX = x - 2;
        destY = y + 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 1;
        move = generateMoveFromTo(x, y, destX, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destX = x + 1;
        destY = y + 2;
        move = generateMoveFromTo(x, y, destX, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 2;
        move = generateMoveFromTo(x, y, destX, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData);
            wynik = true;
        }
        destX = x - 1;
        destY = y + 2;
        move = generateMoveFromTo(x, y, destX, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        destY = y - 2;
        move = generateMoveFromTo(x, y, destX, destY, generatedData);
        if (move != null) {
            moves[movesIndex++] = move;
            wynik = true;
        }
        return wynik;
    }

    private byte[] generateMoveFromTo(int x, int y, int destX, int destY, Data generatedData) {
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
                moveCorrect= false;
                return null;
            }
        }
        if (moveCorrect) {
            move[0] = (byte)data.calculateArrayIndexForCoords(x, y);
            move[1] = (byte)data.calculateArrayIndexForCoords(destX, destY);
        }
        return move;
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
