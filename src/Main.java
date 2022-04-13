import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FileReader fileReader = new FileReader("2021");
        DataManager dataManager = new DataManager();
        Scanner scanner = new Scanner(System.in);

        String userInput;

        while (true) {
            printMenu();
            userInput = scanner.nextLine();

            if (userInput.equals("1")) {
                fileReader.readMonthReports();

            } else if (userInput.equals("2")) {
                fileReader.readYearReport();

            } else if (userInput.equals("3")) {
                if (fileReader.isMonthsDataReady && fileReader.isYearDataReady) {
                    ArrayList<String> misMatchingMonths = dataManager.matchReports(fileReader.MONTHS_LIST,
                                                                                    fileReader.monthsData,
                                                                                    fileReader.yearData);

                    if (misMatchingMonths.isEmpty()) {
                        System.out.println("Несоответствий нет.");
                        System.out.println();

                    } else {
                        System.out.print("Обнаружены несоответствия в следующих месяцах: ");

                        for (int i = 0; i < misMatchingMonths.size() - 1; i++) {
                            System.out.print(misMatchingMonths.get(i) + ", ");
                        }
                        System.out.println(misMatchingMonths.get(misMatchingMonths.size() - 1) + ".");
                    }

                } else if (!fileReader.isMonthsDataReady && !fileReader.isYearDataReady) {
                    System.out.println("Пожалуйста, запустите считывание годового и месячных отчетов " +
                            "перед проведением сверки.");
                    System.out.println();

                } else if (!fileReader.isMonthsDataReady) {
                    System.out.println("Пожалуйста, считайте месячные отчеты перед проведением сверки.");
                    System.out.println();

                } else {
                    System.out.println("Пожалуйста, считайте годовой отчет перед проведением сверки.");
                    System.out.println();
                }


            } else if (userInput.equals("4")) {
                if (fileReader.isMonthsDataReady) {
                    dataManager.displayMonthsData(fileReader.MONTHS_LIST, fileReader.monthsData);
                } else {
                    System.out.println("Пожалуйста, считайте месячные отчеты перед выводом месячных данных.");
                    System.out.println();
                }

            } else if (userInput.equals("5")) {
                if (fileReader.isYearDataReady) {
                    dataManager.displayYearData(fileReader.MONTHS_LIST, fileReader.yearData.currentYear,
                                                fileReader.yearData.revenueList, fileReader.yearData.expensesList);
                } else {
                    System.out.println("Пожалуйста, считайте годовой отчет перед выводом данных за год.");
                    System.out.println();
                }

            } else if (userInput.equals("0")) {
                System.out.println("Программа завершена.");
                return;

            } else {
                System.out.println("Такой команды нет.");
            }
        }
    }

    static void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Выйти из приложения");
    }
}

