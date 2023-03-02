package Calculator;
import java.util.Scanner;

import Calculator.RomanNumber;
public class App {
    public static void main(String[]args) {
        Scanner input = new Scanner(System.in);
        int a,b;
        String act, a1, b1;
        System.out.print("       1. Калькулятор арабских цифр\n");
        System.out.print(" ");
        System.out.print("      2. Калькулятор римских цифр\n");
        System.out.print(" ");
        System.out.print("      Выберете вариант калькулятора (1 или 2): ");
        System.out.print(" ");
        int var = input.nextInt();
        if (var == 1){
           System.out.print(" ");
            System.out.print("      Введите первое число: ");
            a = input.nextInt();
            System.out.print(" ");
            System.out.print("      Введите знак: +;-;*;/: ");
            act = new String(new Scanner(System.in).nextLine());
            System.out.print(" ");
            System.out.print("      Введите второе число: ");
            b = input.nextInt();

            switch (act) {
                case "+": calc1sum(a,b);
                    break;
                case "-": calc1diff(a,b);
                    break;
                case "*": calc1comp(a,b);
                    break;
                case "/": calc1div(a,b);
                    break;
                default: System.out.print("      Ошибка, такого оператора нет");
                    break;
            }
        }

        else if (var==2) {
            System.out.print(" ");
            System.out.print("      Введите первую римскую цифру: ");
            a1 = new String(new Scanner(System.in).nextLine());
            a=calc2(a1);
            System.out.print(" ");
            System.out.print("      Введите знак: +;-;*;/: ");
            act = new String(new Scanner(System.in).nextLine());
            System.out.print(" ");
            System.out.print("      Введите вторую римскую цифру: ");
            b1 = new String(new Scanner(System.in).nextLine());
            b=calc2(b1);
            switch (act) {
                case "+": calc2sum(a,b);
                    break;
                case "-": calc2diff(a,b);
                    break;
                case "*": calc2comp(a,b);
                    break;
                case "/": calc2div(a,b);
                    break;
                default: System.out.print("      Ошибка, такого оператора нет");
                    break;
            }
        }

        else System.out.print("         Ошибка, такого оператора нет");
    }

    private static int calc2(String a1) {
            int decimal = 0;

           // String romanNumeral = a1.toUpperCase();
            for(int x = 0;x<a1.length();x++)
            {
                char convertToDecimal = a1.charAt(x);

                switch (convertToDecimal)
                {
                    case 'M':
                        decimal += 1000;
                        break;

                    case 'D':
                        decimal += 500;
                        break;

                    case 'C':
                        decimal += 100;
                        break;

                    case 'L':
                        decimal += 50;
                        break;

                    case 'X':
                        decimal += 10;
                        break;

                    case 'V':
                        decimal += 5;
                        break;

                    case 'I':
                        decimal += 1;
                        break;
                    case 'm':
                        decimal += 1000;
                        break;

                    case 'd':
                        decimal += 500;
                        break;

                    case 'c':
                        decimal += 100;
                        break;

                    case 'l':
                        decimal += 50;
                        break;

                    case 'x':
                        decimal += 10;
                        break;

                    case 'v':
                        decimal += 5;
                        break;

                    case 'i':
                        decimal += 1;
                        break;
                }
            }
            if (a1.contains("IV") || a1.contains("iv"))
            {
                decimal-=2;
            }
            if (a1.contains("IX") || a1.contains("ix"))
            {
                decimal-=2;
            }
            if (a1.contains("XL") || a1.contains("xl"))
            {
                decimal-=10;
            }
            if (a1.contains("XC") || a1.contains("xc"))
            {
                decimal-=10;
            }
            if (a1.contains("CD") || a1.contains("cd"))
            {
                decimal-=100;
            }
            if (a1.contains("CM") || a1.contains("cm"))
            {
                decimal-=100;
            }

            return decimal;
        }


    private static void calc1div(int a, int b) {
        System.out.print("       "+a+" / "+b+" = " + a /b);
    }

    private static void calc1comp(int a, int b) {
        System.out.print("       "+a+" * "+b+" = " + a *b);
    }

    private static void calc1diff(int a, int b) {

        System.out.print("       "+a+" - "+b+" = " + (a-b));
    }

    private static void calc1sum(int a, int b) {
        System.out.print("       "+a+" + "+b+" = " + (a+b));
    }

    private static void calc2div(int a, int b) {
        int des = a /b;
        RomanNumber conv = new RomanNumber();
        System.out.println(conv.toRoman(des));
    }

    private static void calc2comp(int a, int b) {
        int des = a*b;
        RomanNumber conv = new RomanNumber();
        System.out.println(conv.toRoman(des));
    }

    private static void calc2diff(int a, int b) {
        int des = a-b;
        RomanNumber conv = new RomanNumber();
        System.out.println(conv.toRoman(des));
    }

    private static void calc2sum(int a, int b) {
        int des = a+b;
        RomanNumber conv = new RomanNumber();
        System.out.println(conv.toRoman(des));
    }
}