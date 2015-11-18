package com.example.ChessMinimal;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public final class TestUtils {

    private TestUtils(){};
    public static String convertMoveToPGNFormat(SimpleMove move){
        String wynik = "";
        char a = (char)('a' + (move.from % 5));
        String b = Integer.toString(move.from/5);
        wynik = a + b;
        a = (char)('a' + (move.to % 5));
        b = Integer.toString(move.to/5);

        wynik += "-" + a + b;

        return wynik;
    }

    public static void saveMovesToFile(SimpleMove[] moves, int[] nodeChildrenNumbers, String filename) throws IOException{
        int i = 0;
        int j = 0;
        int pom = nodeChildrenNumbers[i];

        PrintWriter printWriter = new PrintWriter(filename);
        for(SimpleMove move : moves ) {
            printWriter.print(convertMoveToPGNFormat(move)+ ", ");
            j++;
            if(j >= pom) {
                printWriter.print('\n');
                i++;
                pom = nodeChildrenNumbers[i];
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


}
