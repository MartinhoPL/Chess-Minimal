package com.example.ChessMinimal;

public class AlfaBeta {

    GameTree gameTree;
    Evaluation evaluation;
    Data data;
    Data generatedData;
    int bestMove;
    int sideToPlay;

    AlfaBeta(Data data){
        this.gameTree = new GameTree(data);
        gameTree.generateGameTree(2);
        evaluation = new Evaluation();
        generatedData = data;
        this.data = data;
        sideToPlay = data.getSide();
    }

    public byte[] getBestMove()
    {
        byte []move;
        int eval = alfaBetaAlgorithm(0, -99999999, 99999999, true, 0);
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
            int result = evaluation.eval(generatedData, sideToPlay);
            gameTree.undoAllMovesToPreviousPosition(pathToTheRoot, generatedData);
            return result;
        }
        if(player) {
            for(int i = gameTree.findFirstNodeWithChildrens(node); i < gameTree.findFirstNodeWithChildrens(node + 1); i++) {
//                int previousAlfa = alfa;
//                int xxxx = alfaBetaAlgorithm(i, alfa, beta, !player, depth + 1);
                int tmp = alfaBetaAlgorithm(i, alfa, beta, !player, depth + 1);
//                alfa =
                if(tmp > alfa) {
                    alfa = tmp;
                    if(gameTree.getNodeFatherAt(i) == 0) {
                        bestMove = i;
                    }
                }
                if(beta <= alfa){
                    break;
                }
            }
            return alfa;
        }
        else {
            for(int i = gameTree.findFirstNodeWithChildrens(node); i < gameTree.findFirstNodeWithChildrens(node + 1); i++) {
                beta = Math.min(beta, alfaBetaAlgorithm(i, alfa, beta, !player, depth + 1));
                if(beta <= alfa){
                    break;
                }
            }
            return beta;
        }
    }

}
