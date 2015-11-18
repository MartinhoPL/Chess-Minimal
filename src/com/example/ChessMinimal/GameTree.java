package com.example.ChessMinimal;

/**
 * Created by Mikolaj on 2015-10-31.
 */
public class GameTree {

    private static final int MAX_MOVES = 5000000;

    private byte [][]moves;

    private int movesIndex;

    private int nodeChildrenArrayIndex;

    private int []nodeChildrenNumber;

    private Data data;

    private ChessMechanic chessMechanic;

    GameTree(Data data, ChessMechanic chessMechanic) {
        moves = new byte[MAX_MOVES][4];
        nodeChildrenNumber = new int[MAX_MOVES];
        this.data = data;
        this.chessMechanic = chessMechanic;
        nodeChildrenArrayIndex = 0;
        movesIndex = 0;
    }

//    public void generateGameTree(int depth) {
////        for (int i = 0; i < depth; i++){
//            generateGameTreeLevel();
////        }
//    }

    private void generateGameTreeLevel(int node) {
        for(int i = 0; i < 5; i++){
            for(int j = 0 ; j < 5; j++) {
                if(data.color[data.calculateArrayIndexForCoords(j, i)] == data.getSide()) {
                    switch (data.getPiece(j, i))
                    {
                        case 1: {
                            nodeChildrenNumber[node] = movesIndex;
                            if(generatePawnMoves(j, i)){
                                node++;
                            }
                            break;
                        }
                        case 2: {
                            nodeChildrenNumber[node] = movesIndex;
                            if (generateKnightMoves(j, i)) {
                                node++;
                            }
                            break;
                        }
                        case 3:{
                            nodeChildrenNumber[node] = movesIndex;
                            if (generateBishopMoves(j, i)) {
                                node++;
                            }
                            break;
                        }
                        case 4: {
                            nodeChildrenNumber[node] = movesIndex;
                            if (generateRookMoves(j, i)) {
                                node++;
                            }
                            break;
                        }
                        case 5: {
                            nodeChildrenNumber[node] = movesIndex;
                            if (generateQueenMoves(j, i)) {
                                node++;
                            }
                            break;
                        }
                        case 6:{
                            nodeChildrenNumber[node] = movesIndex;
                            if (generateKingMoves(j, i)) {
                                node++;
                            }
                            break;
                        }
                    }
                }
            }
        }

    }

    private boolean generateRookMoves(int x, int y){
        boolean anyMovePossible = false;
        for(int i=0; i<5; i++){
            int destX = i;
            if(generateMoveFromTo(x, y, destX, y) != null) {
                moves[movesIndex++] = generateMoveFromTo(x, y, destX, y);
                anyMovePossible = true;
            }
            int destY = i;
            if(generateMoveFromTo(x, y, x, destY) != null) {
                moves[movesIndex++] = generateMoveFromTo(x, y, destX, y);
                anyMovePossible = true;
            }
        }
        return anyMovePossible;

    }

    private boolean generateBishopMoves(int x, int y){
        boolean anyMovePossible = false;
        int difference = x - y;
        for(int i=0; i<5; i++){
            int destX = i;
            if(generateMoveFromTo(x, y, destX, y) != null) {
                moves[movesIndex++] = generateMoveFromTo(x, y, destX, y);
                anyMovePossible = true;
            }
            int destY = i;
            if(generateMoveFromTo(x, y, x, destY) != null) {
                moves[movesIndex++] = generateMoveFromTo(x, y, destX, y);
                anyMovePossible = true;
            }
        }
        return anyMovePossible;

    }
    private boolean generateKingMoves(int x, int y){
        int destX = x + 1;
        if (generateMoveFromTo(x, y, destX, y) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, y);
            return true;
        }
        int destY = y + 1;
        if (generateMoveFromTo(x, y, destX, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY);
            return true;
        }
        destY = y - 1;
        if (generateMoveFromTo(x, y, destX, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY);
            return true;
        }
        destX = x - 1;
        if (generateMoveFromTo(x, y, destX, y) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, y);
            return true;
        }
        destY = y + 1;
        if (generateMoveFromTo(x, y, destX, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY);
            return true;
        }
        if (generateMoveFromTo(x, y, destX, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, x, destY);
            return true;
        }
        destY = y - 1;
        if (generateMoveFromTo(x, y, destX, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY);
            return true;
        }
        if (generateMoveFromTo(x, y, destX, y) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, x, destY);
            return true;
        }
        return true;

    }
    private boolean generateQueenMoves(int x, int y){
        return (generateBishopMoves(x, y) || generateRookMoves(x, y));
    }
    private boolean generatePawnMoves(int x, int y){
        int destY;
        if(data.getSide() == 1) {
            destY = y + 1;
        } else {
            destY = y - 1;
        }
        if (generateMoveFromTo(x, y, x, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, x, destY);
            return true;
        }

        int destX = x + 1;
        if (generateMoveFromTo(x, y, x, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY);
            return true;
        }

        destX = x - 1;
        if (generateMoveFromTo(x, y, x, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY);
            return true;
        }
        return false;

    }
    private boolean generateKnightMoves(int x, int y){
        int destX, destY;
        destX = x + 2;
        destY = y + 1;
        if (generateMoveFromTo(x, y, destX, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY);
            return true;
        }
        destY = y - 1;
        if (generateMoveFromTo(x, y, destX, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY);
            return true;
        }
        destX = x - 2;
        destY = y + 1;
        if (generateMoveFromTo(x, y, destX, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY);
            return true;
        }
        destY = y - 1;
        if (generateMoveFromTo(x, y, destX, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY);
            return true;
        }
        destX = x + 1;
        destY = y + 2;
        if (generateMoveFromTo(x, y, destX, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY);
            return true;
        }
        destY = y - 2;
        if (generateMoveFromTo(x, y, destX, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY);
            return true;
        }
        destX = x - 1;
        destY = y + 2;
        if (generateMoveFromTo(x, y, destX, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY);
            return true;
        }
        destY = y - 2;
        if (generateMoveFromTo(x, y, destX, destY) != null) {
            moves[movesIndex++] = generateMoveFromTo(x, y, destX, destY);
            return true;
        }
        return false;
    }

    private byte[] generateMoveFromTo(int x, int y, int destX, int destY) {
        byte[] move = new byte[4];
        boolean moveCorrect = true;
        switch (chessMechanic.isMoveCorrect(x, y, destX, destY)){
            case GOOD:{
                move[3] = 0;
                break;
            }
            case CHECK:{
                move[3] = 1;
                break;
            }
            case PROMOTION:{
                move[3] = 8;
                break;
            }
            case STALEMATE:{
                move[3] = 4;
                break;
            }
            case CHECKMATE:{
                move[3] = 2;
                break;
            }
            case FAIL:{
                moveCorrect= false;
                return null;
            }
        }
        if (moveCorrect) {
            move[0] = (byte)data.calculateArrayIndexForCoords(x,y);
            move[1] = (byte)data.calculateArrayIndexForCoords(destX, destY);
        }
        return move;
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

    public int[] getNodeChildrenNumber() {
        return nodeChildrenNumber;
    }

    public void setNodeChildrenNumber(int[] nodeChildrenNumber) {
        this.nodeChildrenNumber = nodeChildrenNumber;
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
