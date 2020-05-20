package readability;

import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private static String readFromFile(String path){
        try(FileReader reader = new FileReader(path)) {
            var scan = new Scanner(reader);
            return scan.nextLine();
        } catch(IOException ex){
            System.out.println("File not found.");
        }
        return "ERROR";
    }

    public static void main(String[] args) {
        var text = readFromFile(args[0]);
        var calculator = new ReadabilityCalculator(text);
        System.out.println("The text is:\n" + text);
        calculator.printTextInfo();
        var scan = new Scanner(System.in);
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        var type = scan.next();
        System.out.println();
        switch (type){
            case "ARI":{
                var score = calculator.calculateARI();
                System.out.print("Automated Readability Index: " + new DecimalFormat("##.##").format(score) + " ");
                printAge(score);
                break;
            }
            case "FK":{
                var score = calculator.calculateFK();
                System.out.print("Flesch–Kincaid readability tests: " + new DecimalFormat("##.##").format(score) + " ");
                printAge(score);
                break;
            }
            case "SMOG":{
                var score = calculator.calculateSMOG();
                System.out.print("Simple Measure of Gobbledygook: " + new DecimalFormat("##.##").format(score) + " ");
                printAge(score);
                break;
            }
            case "CL": {
                var score = calculator.calculateCL();
                System.out.print("Coleman–Liau index: " + new DecimalFormat("##.##").format(score) + " ");
                printAge(score);
                break;
            }
            case "all":{
                var scoreTotal = 0.0;
                var score = calculator.calculateARI();

                System.out.print("Automated Readability Index: " + new DecimalFormat("##.##").format(score) + " ");
                scoreTotal += printAge(score);
                score = calculator.calculateFK();
                System.out.print("Flesch–Kincaid readability tests: " + new DecimalFormat("##.##").format(score) + " ");
                scoreTotal += printAge(score);
                score = calculator.calculateSMOG();
                System.out.print("Simple Measure of Gobbledygook: " + new DecimalFormat("##.##").format(score) + " ");
                scoreTotal += printAge(score);
                score = calculator.calculateCL();
                System.out.print("Coleman–Liau index: " + new DecimalFormat("##.##").format(score) + " ");
                scoreTotal += printAge(score);

                System.out.println("This text should be understood in average by " + new DecimalFormat("##.##").format(scoreTotal / 4.0) + " year olds.");
            }
        }
    }

    private static int printAge(double score) {
        String[] ages = new String[]{"6", "7", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "24", "24"};
        var ceilScore = (int) Math.ceil(score);
        ceilScore++;
        if (ceilScore < 0) ceilScore = 0;
        if (ceilScore >= 14) ceilScore = 13;
        System.out.println("(about " + ages[ceilScore] + " year olds).");
        return Integer.parseInt(ages[ceilScore]);
    }
}
