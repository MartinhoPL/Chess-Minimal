package com.example.ChessMinimal;

public class AlfaBeta {

    GameTree gameTree;
    Evaluation evaluation;
    Data data;
    int bestMove;
    int sideToPlay;

    AlfaBeta(Data data){
        this.gameTree = new GameTree(data);
        evaluation = new Evaluation();
        this.data = data.clone();
        sideToPlay = data.getSide();
    }

    public byte[] getBestMove(Data data) {
        this.data = data.clone();
        gameTree.generateGameTree(2, this.data);
        byte []move;
        int eval = alfaBetaAlgorithm(0, -1000000, 1000000, true, data.getSide(), -1000001);
        move = gameTree.getMovesAt(bestMove);
        return move.clone();
    }

    public int alfaBetaAlgorithm(int node, int alfa, int beta, boolean player, int sideToPlay, int previousAlfa) {
        if (gameTree.getNodeChildrenAt(node) == -1 || gameTree.getNodeChildrenAt(node) == gameTree.getMovesIndex()) {
            int pathToTheRoot[] = gameTree.getPathToTheRoot(node);
            gameTree.makeAllMovesToNextPosition(pathToTheRoot, data);
            int result;
            if(gameTree.getMovesAt(node)[2] == 2) {
                if (sideToPlay == data.getXside()) {
                    gameTree.undoAllMovesToPreviousPosition(pathToTheRoot, data);
                    return 999999;
                } else {
                    gameTree.undoAllMovesToPreviousPosition(pathToTheRoot, data);
                    return -999999;
                }
            } else if(gameTree.getMovesAt(node)[2] == 2) {
                gameTree.undoAllMovesToPreviousPosition(pathToTheRoot, data);
                return 0;
            }
            result = evaluation.eval(data, sideToPlay);
            gameTree.undoAllMovesToPreviousPosition(pathToTheRoot, data);
            return result;
        }
        if(player) {
            for(int i = gameTree.findFirstNodeWithChildrens(node); i < gameTree.findFirstNodeWithChildrens(node + 1); i++) {
                alfa = Math.max(alfaBetaAlgorithm(i, alfa, beta, !player, sideToPlay, previousAlfa), alfa);
                if(gameTree.getNodeFatherAt(i) == 0 && previousAlfa < alfa) {
                    previousAlfa = alfa;
                    bestMove = i;
                }
                if(beta <= alfa){
                    break;
                }
            }
            return alfa;
        }
        else {
            for(int i = gameTree.findFirstNodeWithChildrens(node); i < gameTree.findFirstNodeWithChildrens(node + 1); i++) {
                beta = Math.min(beta, alfaBetaAlgorithm(i, alfa, beta, !player, sideToPlay, previousAlfa));
                if(beta <= alfa){
                    break;
                }
            }
            return beta;
        }
    }

}
