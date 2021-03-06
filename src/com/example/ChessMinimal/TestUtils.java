package com.example.ChessMinimal;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public final class TestUtils {

    private TestUtils(){};
    public static String convertMoveToPGNFormat(byte []move){
        String wynik = "";
        char a = (char)('a' + (move[0] % 5));
        String b = Integer.toString(move[0] / 5);
        wynik = a + b;
        a = (char)('a' + (move[1] % 5));
        b = Integer.toString(move[1] / 5);

        wynik += "-" + a + b;

        return wynik;
    }

    public static void saveMovesToFile(byte[][] moves, int[] nodeChildren, String filePath) throws IOException{
        int j = 0;
        int pom = nodeChildren[0];
        File file = new File(filePath);

        PrintWriter printWriter = new PrintWriter(file);
        for(int i = 0; i < moves.length; i++) {
            printWriter.print(convertMoveToPGNFormat(moves[i])+ ", ");
            j++;
            if(j >= pom) {
                printWriter.print('\n');
                pom = nodeChildren[i];
                j=0;
            }
        }
        printWriter.close();
    }

    public static int[] loadPositionFromFile(String filename) throws IOException {
        int []wynik = new int[100];

        Scanner scanner = new Scanner(new File(filename));
        int i = 0;
        while(scanner.hasNextInt()){
            wynik[i++] = scanner.nextInt();
        }
        return wynik;
    }

    public static void savePositionToFile(String filename, int piece[], int color[]) throws IOException {
        int j = 0;

        PrintWriter printWriter = new PrintWriter(filename);
        for(int i = 0; i < piece.length; i++) {
            printWriter.print(piece[i] + " ");
            if(i%5 == 0) {
                printWriter.print('\n');
            }
            j++;
        }
        printWriter.print('\n');
        printWriter.print('\n');
        for(int i = 0; i < color.length; i++) {
            printWriter.print(piece[i] + " ");
            if(i%5 == 0) {
                printWriter.print('\n');
            }
            j++;
        }
        printWriter.close();
    }

}
