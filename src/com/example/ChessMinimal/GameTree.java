package com.example.ChessMinimal;

/**
 * Created by Mikolaj on 2015-10-31.
 */
public class GameTree {

    private static final int MAX_MOVES = 5000000;

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
        generateGameTreeLevel(generatedData);
        while (depth > 0) {
            int poczatek = nodeChildren[nodeChildrenArrayIndex - 1];
            int koniec = nodeChildren[nodeChildrenArrayIndex];
            for (int i = poczatek; i < koniec; i++) {
                generatedData.makeMove(moves[i], generatedData);
                generateGameTreeLevel(generatedData);
                generatedData.makeMove(moves[i], generatedData);
                nodeChildrenArrayIndex++;
                if(depth > 1) { //dla pozostalych
                    nodeChildren[nodeChildrenArrayIndex] = nodeChildren[nodeChildrenArrayIndex - 1];
                } else { //dla lisci
                    nodeChildren[nodeChildrenArrayIndex] = -1;
                }
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
                            nodeChildren[nodeChildrenArrayIndex] = movesIndex;
                            if(generatePawnMoves(j, i, generatedData)){
                                nodeChildrenArrayIndex++;
                            }
                            break;
                        }
                        case 2: {
                            nodeChildren[nodeChildrenArrayIndex] = movesIndex;
                            if (generateKnightMoves(j, i, generatedData)) {
                                nodeChildrenArrayIndex++;
                            }
                            break;
                        }
                        case 3:{
                            nodeChildren[nodeChildrenArrayIndex] = movesIndex;
                            if (generateBishopMoves(j, i, generatedData)) {
                                nodeChildrenArrayIndex++;
                            }
                            break;
                        }
                        case 4: {
                            nodeChildren[nodeChildrenArrayIndex] = movesIndex;
                            if (generateRookMoves(j, i, generatedData)) {
                                nodeChildrenArrayIndex++;
                            }
                            break;
                        }
                        case 5: {
                            nodeChildren[nodeChildrenArrayIndex] = movesIndex;
                            if (generateQueenMoves(j, i, generatedData)) {
                                nodeChildrenArrayIndex++;
                            }
                            break;
                        }
                        case 6:{
                            nodeChildren[nodeChildrenArrayIndex] = movesIndex;
                            if (generateKingMoves(j, i, generatedData)) {
                                nodeChildrenArrayIndex++;
                            }
                            break;
                        }
                    }
                }
            }
        }

    }

    private boolean generateRookMoves(int x, int y, Data generatedData){
        boolean wynik = false;
        for(int i=0; i<5; i++){
            int destX = i;
            if(generateMoveFromTo(x, y, destX, y, generatedData) != null) {
                moves[movesIndex++] = generateMoveFromTo(x, y, destX, y, generatedData);
                wynik = true;
            }
            int destY = i;
            if(generateMoveFromTo(x, y, x, destY, generatedData) != null) {
                moves[movesIndex++] = generateMoveFromTo(x, y, destX, y, generatedData);
                wynik = true;
            }
        }
        return wynik;

    }

    private boolean generateBishopMoves(int x, int y, Data generatedData){
        boolean wynik = false;
        for(int i=0; i<5; i++){
            int destX = i;
            if(generateMoveFromTo(x, y, destX, y, generatedData) != null) {
                moves[movesIndex++] = generateMoveFromTo(x, y, destX, y, generatedData);
                wynik = true;
            }
            int destY = i;
            if(generateMoveFromTo(x, y, x, destY, generatedData) != null) {
                moves[movesIndex++] = generateMoveFromTo(x, y, destX, y, generatedData);
                wynik = true;
            }
        }
        return wynik;

    }
    private boolean generateKingMoves(int x, int y, Data generatedData){
        int destX = x + 1;
        boolean wynik = false;

        if (generateMoveFromTo(x, y, destX, y, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, y, generatedData);
            wynik = true;
        }
        int destY = y + 1;
        if (generateMoveFromTo(x, y, destX, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData);
            wynik = true;
        }
        destY = y - 1;
        if (generateMoveFromTo(x, y, destX, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData);
            wynik = true;
        }
        destX = x - 1;
        if (generateMoveFromTo(x, y, destX, y, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, y, generatedData);
            wynik = true;
        }
        destY = y + 1;
        if (generateMoveFromTo(x, y, destX, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData);
            wynik = true;
        }
        if (generateMoveFromTo(x, y, destX, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, x, destY, generatedData);
            wynik = true;
        }
        destY = y - 1;
        if (generateMoveFromTo(x, y, destX, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData);
            wynik = true;
        }
        if (generateMoveFromTo(x, y, destX, y, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, x, destY, generatedData);
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

        if(data.getSide() == 1) {
            destY = y + 1;
        } else {
            destY = y - 1;
        }
        if (generateMoveFromTo(x, y, x, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, x, destY, generatedData);
            wynik = true;
        }

        int destX = x + 1;
        if (generateMoveFromTo(x, y, x, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData);
            wynik = true;
        }

        destX = x - 1;
        if (generateMoveFromTo(x, y, x, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData);
            wynik = true;
        }
        return wynik;

    }
    private boolean generateKnightMoves(int x, int y, Data generatedData){
        int destX, destY;
        boolean wynik = false;
        destX = x + 2;
        destY = y + 1;
        if (generateMoveFromTo(x, y, destX, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData);
            nodeChildrenArrayIndex++;
            wynik = true;
        }
        destY = y - 1;
        if (generateMoveFromTo(x, y, destX, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData);
            wynik = true;
        }
        destX = x - 2;
        destY = y + 1;
        if (generateMoveFromTo(x, y, destX, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData);
            wynik = true;
        }
        destY = y - 1;
        if (generateMoveFromTo(x, y, destX, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData);
            wynik = true;
        }
        destX = x + 1;
        destY = y + 2;
        if (generateMoveFromTo(x, y, destX, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData);
            wynik = true;
        }
        destY = y - 2;
        if (generateMoveFromTo(x, y, destX, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData);
            wynik = true;
        }
        destX = x - 1;
        destY = y + 2;
        if (generateMoveFromTo(x, y, destX, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData);
            wynik = true;
        }
        destY = y - 2;
        if (generateMoveFromTo(x, y, destX, destY, generatedData) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY, generatedData);
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
        nodeChildren[nodeChildrenArrayIndex]++;
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
