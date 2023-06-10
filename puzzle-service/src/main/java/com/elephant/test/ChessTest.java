package com.elephant.test;

public class ChessTest {
    public static void main(String[] args) {
        System.out.println("CHESS");
        for(int i = 0; i < 10; i++){
            System.out.println();
            for(int j = 0; j < 9; j++){
                System.out.print("("+i+","+j+")"+ "   ");
            }
        }

    }
}
