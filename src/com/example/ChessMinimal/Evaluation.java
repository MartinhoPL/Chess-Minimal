package com.example.ChessMinimal;

/**
 * Created by Dell on 2015-11-25.
 */
public class Evaluation { // klasa odpowiedzialna za ocene ustawienia figur itp.
    final static int  pawnValue = 100;
    final static int  knightValue = 280;
    final static int  bishopValue = 320;
    final static int  rookValue = 500;
    final static int  queenValue = 880;
    final static int pawnEvalPos[] = {
            0, 0, 0, 0,
            5, 5, 5, 5,
            20, 20, 20, 20,
            40, 40, 40, 40,
            0, 0, 0, 0
    };
    final static int knightEvalPos[] = {
            -20, -10, -10, -20,
            -10,   5,   5, -10,
            -10,  10,  10, -10,
            -10,   5,   5, -10,
            -20, -10, -10, -20,
    };
    final static int bishopEvalPos[] = {
            -20, -10, -10, -20,
            -10,   5,   5, -10,
            -10,  10,  10, -10,
            -10,   5,   5, -10,
            -20, -10, -10, -20,
    };
    final static int kingEvalPos[] = {
            0, 0, 0, 0,
            2, 5, 5, 2,
            3, 10, 10, 3,
            2, 5, 5, 2,
            0, 0, 0, 0,
    };
    final static int flips[] = {
            19, 18, 17, 16,
            15, 14, 13, 12,
            11, 10,  9,  8,
             7,  6,  5,  4,
             3,  2,  1,  0,
    };
    public int eval(Data data, int sideToPlay){
        int evalStat[] = {0, 0};
        int color[] = data.getColor(), piece[] = data.getPiece() , side = data.getXside(), flip = 0;
        for (int i = 0; i < Fixed.XWIDTH * Fixed.YHEIGHT; i++) {
            if(color[i] == 0){
                continue;
            }else {
                if(color[i] == 1){
                    flip = flips[i];
                }else {
                    flip = i;
                }
                switch (piece[i]){
                    case 1:
                        evalStat[color[i]-1] += pawnEvalPos[flip] + pawnValue;
                        break;
                    case 2:
                        evalStat[color[i]-1] += knightEvalPos[flip] + knightValue;
                        break;
                    case 3:
                        evalStat[color[i]-1] += bishopEvalPos[flip] + bishopValue;
                        break;
                    case 4:
                        evalStat[color[i]-1] += rookValue;
                        break;
                    case 5:
                        evalStat[color[i]-1] += queenValue;
                        break;
                    case 6:
                        evalStat[color[i]-1] += kingEvalPos[flip];
                        break;
                }
            }
        }
        int result;
        if(side == 1) {
            result = evalStat[0] - evalStat[1];
        }else {
            result = evalStat[1] - evalStat[0];
        }
        if(sideToPlay != side);
        {
            result = -result;
        }
        return result;
    }
}
