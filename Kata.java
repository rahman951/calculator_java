import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Kata {

    public static void main(String[] args) throws Exception {
        System.out.println("Привет, это калькулятор, который умеет работать с римскими и арабскими цифрами."); // Выводит текст
        System.out.println("Введите математическое выражение (через пробел):");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] inputArr = input.split(" ");


        if (3 < inputArr.length) {
            throw new Exception("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)"); //
        }

        if (3 > inputArr.length) {
            throw new Exception("Строка не является математической операцией"); //
        }

        //определение типа данных (римские или арабские цифры)
        boolean isRoman = inputArr[0].matches("[IVX]+");
        boolean secondIsRoman = inputArr[2].matches("[IVX]+");
        if (isRoman != secondIsRoman) {
            throw new Exception("Используются одновременно разные системы счисления"); //
        }

        String operation = inputArr[1];

        int[] digits = new int[2];
        digits[0] = isRoman ? romanToInt(inputArr[0]) : Integer.parseInt(inputArr[0]);//------Определяется, является ли входное значение строкой в римском формате
        digits[1] = isRoman ? romanToInt(inputArr[2]) : Integer.parseInt(inputArr[2]);//------Определяется, является ли входное значение строкой в римском формате

        if ((digits[0] < 1 || digits[0] > 10 || digits[1] < 1 || digits[1] > 10)) {
            throw new IllegalArgumentException("Число находится за пределами допустимого диапазона от 1 до 10 включительно"); // Проверяет что числа находятся в нужном диапазоне (от 1 до 10)
        }


        int result = arithmeticOperation(digits[0], digits[1], operation);  //передает ему три параметра
        String output = isRoman ? intToRoman(result) : Integer.toString(result);//---I---
        System.out.println(output);


    }

    private static int arithmeticOperation(int a, int b, String operation) {
        int result = switch (operation) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> throw new IllegalArgumentException("Некорректная операция: " + operation);
        };
        return result;
    }

    private static final List<RomanNumeral> ROMAN_NUMERAL_LIST = Arrays.asList(RomanNumeral.values());   //создает список всех возможных римских цифр


    //преобразовать римское число в арабское число.

    private static int romanToInt(String romanNumber) {
        int result = 0; // объявляем переменную для хранения результата и инициализируем ее нулем
        for (int i = 0; i < romanNumber.length(); i++) { // начинаем цикл по символам римской записи
            char currentChar = romanNumber.charAt(i); // получаем текущий символ
            RomanNumeral currentRomanNumeral = RomanNumeral.valueOf(String.valueOf(currentChar)); // определяем текущую цифру в римской записи, используя перечисление RomanNumeral
            if (i != romanNumber.length() - 1) { // проверяем, является ли текущий символ последним
                char nextChar = romanNumber.charAt(i + 1); // получаем следующий символ
                RomanNumeral nextRomanNumeral = RomanNumeral.valueOf(String.valueOf(nextChar)); // определяем следующую цифру в римской записи, используя перечисление RomanNumeral
                if (currentRomanNumeral.getValue() < nextRomanNumeral.getValue()) { // проверяем, является ли текущая цифра меньше следующей
                    result -= currentRomanNumeral.getValue(); // если да, то вычитаем ее значение из результата
                } else {
                    result += currentRomanNumeral.getValue(); // если нет, то добавляем ее значение к результата
                }
            } else {
                result += currentRomanNumeral.getValue(); // для последней цифры в римской записи, просто добавляем ее значение к результата
            }
        }
        return result; // возвращаем итоговый результат в виде числа
    }

    private static String intToRoman(int number) throws Exception {//------
        StringBuilder builder = new StringBuilder();

        while (number > 0) {
            for (int i = ROMAN_NUMERAL_LIST.size() - 1; i >= 0; i--) {
                RomanNumeral current = ROMAN_NUMERAL_LIST.get(i);
                if (current.getValue() <= number) {
                    builder.append(current);
                    number -= current.getValue();
                    break;
                }
            }
        }
        if (number < 0) {
            throw new Exception("В римской системе нет отрицательных чисел"); //
        }
        return builder.toString();
    }
    private enum RomanNumeral {//------
        I(1),
        IV(4),
        V(5),
        IX(9),
        X(10),
        XL(40),
        L(50),
        XC(90),
        C(100);

        private final int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
