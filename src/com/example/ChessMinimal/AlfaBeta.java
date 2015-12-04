package com.example.ChessMinimal;

public class AlfaBeta {

    GameTree gameTree;
    Evaluation evaluation;
    Data data;
    Data generatedData;
    int bestMove;

    AlfaBeta(Data data){
        this.gameTree = new GameTree(data);
        gameTree.generateGameTree(2);
        evaluation = new Evaluation();
        generatedData = data;
        this.data = data;
    }

    public byte[] getBestMove()
    {
        byte []move;
        alfaBetaAlgorithm(0, -99999999, 99999999, true, 0);
        move = gameTree.getMovesAt(bestMove);
        return move;
    }

    public  void convertMovesFromPrincipalVariationToBytes(){
        ;
    }

    public int alfaBetaAlgorithm(int node, int alfa, int beta, boolean player, int depth) {
        if (gameTree.getNodeChildrenAt(node) == -1 || gameTree.getNodeChildrenAt(node) == gameTree.getMovesIndex()) {
            int pathToTheRoot[] = gameTree.getPathToTheRoot(node);
            gameTree.makeAllMovesToNextPosition(pathToTheRoot, generatedData);
            int result = evaluation.eval(generatedData, data.getSide());
            gameTree.undoAllMovesToPreviousPosition(pathToTheRoot, generatedData);
            return result;
        }
        if(player) {
            for(int i = gameTree.getNodeChildrenAt(node); i < gameTree.getNodeChildrenAt(node + 1); i++) {
                int previousAlfa = alfa;
                int xxxx = alfaBetaAlgorithm(i, alfa, beta, !player, depth + 1);
                alfa = Math.max(alfa, xxxx);
                if(alfa != previousAlfa) {
                    bestMove = i;
                }
                if(beta <= alfa){
                    break;
                }
            }
            return alfa;
        }
        else {
            for(int i = gameTree.getNodeChildrenAt(node); i < gameTree.getNodeChildrenAt(node + 1); i++) {
                int previousBeta = beta;
                beta = Math.min(beta, alfaBetaAlgorithm(i, alfa, beta, !player, depth + 1));
                if(previousBeta != beta) {
                    bestMove = i;
                }
                if(beta <= alfa){
                    break;
                }
            }
            return beta;
        }
    }

}
