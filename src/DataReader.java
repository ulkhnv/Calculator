import java.util.InputMismatchException;
import java.util.Scanner;

public class DataReader  {
    private int number1,number2;
    private char operation;
    private double result;
    private boolean isArabicNumber;
    private Converter converter = new Converter();

    public void readData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input: ");
        String data = scanner.nextLine();
        data = data.toUpperCase();

        if (data.isEmpty()) {
            throw new NullPointerException("Пустой ввод");
        }
        if (checkOperation(data)) {
            if (checkNumber1(data)) {
                if (checkNumber2(data)) {
                    calculate();
                }
                else {
                    throw new NumberFormatException("Некорректный ввод чисел.Числа должны быть целыми, и в диапазоне 1..10 или I..X ");
                }
            }
            else {
                throw new NumberFormatException("Некорректный ввод чисел.Числа должны быть целыми, и в диапазоне 1..10 или I..X ");
            }
        }
        else {
             throw new InputMismatchException("Некорректный ввод операции");
        }

    }

    private boolean checkOperation(String data) {
        //Проверка данных на наличие знака операции
        if (data.contains("+") && !data.startsWith("+") && !data.endsWith("+")) {
            //Повторная проверка на наличие дублирования или других знаков операции
            data = data.replaceFirst("[+]"," ");
            if (!data.contains("+") && !data.contains("-") && !data.contains("*") && !data.contains("/")) {
                data = data.replace(" ", "+");
                operation = '+';
                return true;
            }
        }
        else if (data.contains("-") && !data.startsWith("-") && !data.endsWith("-")) {
            data = data.replaceFirst("[-]"," ");
            if (!data.contains("+") && !data.contains("-") && !data.contains("*") && !data.contains("/")) {
                data = data.replace("", "-");
                operation = '-';
                return true;
            }
        }
        else if (data.contains("*") && !data.startsWith("*") && !data.endsWith("*")) {
            data = data.replaceFirst("[*]"," ");
            if (!data.contains("+") && !data.contains("-") && !data.contains("*") && !data.contains("/")) {
                data = data.replace(" ", "*");
                operation = '*';
                return true;
            }
        }
        else if (data.contains("/") && !data.startsWith("/") && !data.endsWith("/")) {
            data = data.replaceFirst("[/]"," ");
            if (!data.contains("+") && !data.contains("-") && !data.contains("*") && !data.contains("/")) {
                data = data.replace(" ", "/");
                operation = '/';
                return true;
            }
        }
            return false;
    }


    private boolean checkNumber1(String data){
        //Проверка первого числа которое возвращает истину если число в диапазоне 1..10 или I..X
        String currentNumber = data.substring(0,data.indexOf(operation));
        for (int i = 1;i <= 10; i++){
            if (currentNumber.equals(String.valueOf(i))){
                number1 = Integer.valueOf(currentNumber);
                isArabicNumber = true;
                return true;
            }
        }
        for (String romanNumber : converter.getRomanNumbers().keySet()){
            if (currentNumber.equals(romanNumber)){
                number1 = converter.romanToArabic(currentNumber);
                isArabicNumber = false;
                return true;
            }
        }
        return false;
    }

    private boolean checkNumber2(String data){
        //Проверка второго числа которое возвращает истину если число в диапазоне 1..10 или I..X
        String currentNumber = data.substring(data.indexOf(operation)+1);
        for (int i = 1;i <= 10; i++){
            if (currentNumber.equals(String.valueOf(i))){
                if (isArabicNumber) {
                    number2 = Integer.valueOf(currentNumber);
                    return true;
                }
                else {
                    //В случае если числа разного формата выбрасывает исключение
                    throw new NumberFormatException("Числа должны быть одного формата");
                }
            }
        }

        for (String romanNumber : converter.getRomanNumbers().keySet()){
            if (currentNumber.equals(romanNumber)){
                if (!isArabicNumber) {
                    number2 = converter.romanToArabic(currentNumber);
                    return true;
                }
                else {
                    throw new NumberFormatException("Числа должны быть одного формата");
                }
            }
        }
        return false;
    }

    private void calculate(){
       switch (operation){
           case '+' : result = number1 + number2; break;
           case '-' : result = number1 - number2; break;
           case '*' : result = number1 * number2; break;
           case '/' : result = (double) number1 / number2; break;
       }
       if (result%1==0){
           if (isArabicNumber) {
               System.out.println("Output: \n"+ (int)result);
           }
           else{
               System.out.println("Output: \n "+ converter.arabicToRoman((int)result));
           }
       }
       else {
           if (isArabicNumber) {
               System.out.println("Output: \n"+ result);
           }
           else {
               throw new NumberFormatException("Результат не может быть дробным числом");
           }
       }
    }
}
