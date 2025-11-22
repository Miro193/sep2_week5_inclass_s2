package org.example;
import java.util.logging.Logger;
import java.util.Scanner;


public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());
    public static void main(String[] args) {
        logger.info("Hello and welcome!\n");
        runLoop();
    }

    public static int askInt() {
        Scanner input = new Scanner(System.in);
        logger.info(() -> "Please enter an number: ");
        return input.nextInt();
    }

    public static void runLoop() {
        int count = askInt();
        String firstFormatString = "i = %d";
        for (int i = 1; i <= count; i++) {
            int num = i;
            logger.info(() -> String.format(firstFormatString, num));
        }
    }
}