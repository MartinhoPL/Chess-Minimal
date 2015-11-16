package com.example.ChessMinimal;

/**
 * Created by Dell on 2015-11-16.
 */
public final class Fixed {
    private Fixed() {
    }

    public static final int XWIDTH = 5;
    public static final int YHEIGHT = 5;
    public static final int[] INIT_COLOR = {
            2, 2, 2, 2, 2,
            2, 2, 2, 2, 2,
            0, 0, 0, 0, 0,
            1, 1, 1, 1, 1,
            1, 1, 1, 1, 1,
    };
    public static final int[] INIT_PIECE = {// wie?a ko? goniec hetman kr?l
            4, 2, 3, 5, 6,
            1, 1, 1, 1, 1,
            0, 0, 0, 0, 0,
            1, 1, 1, 1, 1,
            4, 2, 3, 5, 6,
    };
}