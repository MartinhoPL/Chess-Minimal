package com.example.ChessMinimal;

public class AlfaBeta {

    GameTree gameTree;
    Data data;

    AlfaBeta(Data data){
        this.data = data;
        this.gameTree = new GameTree(data);
        gameTree.generateGameTree(3);
    }

    public int alfaBetaAlgorithm(int node, int depth, int alfa, int beta, boolean player) {
        int score = -9999999;
        if (depth == 0 || gameTree.getNodeChildren()[node] == -1) {
            return score;
        }
        if(player) {
            for(int i = gameTree.getNodeChildren()[node]; i < gameTree.getNodeChildren()[node + 1]; i++) {
                alfa = Math.max(alfa, alfaBetaAlgorithm(i, depth-1, alfa, beta, !player));
                if(beta <= alfa){
                    break;
                }
            }
            return alfa;
        }
        else {
            for(int i = gameTree.getNodeChildren()[node]; i < gameTree.getNodeChildren()[node + 1]; i++) {
                beta = Math.min(beta, alfaBetaAlgorithm(i, depth-1, alfa, beta, !player));
                if(beta <= alfa){
                    break;
                }
            }
            return beta;
        }
    }

}
