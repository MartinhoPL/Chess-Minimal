package com.example.ChessMinimal;

/**
 * Created by Dell on 2015-11-16.
 */
public final class Fixed {
    private Fixed() {
    }

    public static final int XWIDTH = 4;
    public static final int YHEIGHT = 5;
    public static final int[] INIT_COLOR = {
            2, 2, 2, 2,
            2, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 1,
            1, 1, 1, 1,
    };
    public static final int[] INIT_PIECE = {// wie?a ko? goniec hetman kr?l
            6, 2, 3, 4,
            1, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 1,
            4, 3, 2, 6,
    };
}