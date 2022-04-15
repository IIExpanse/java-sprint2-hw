import java.util.List;
import java.util.Scanner;

public class Main {

    public static final FileReader fileReader = new FileReader("2021");
    public static final DataManager dataManager = new DataManager();
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String userInput = "-1";

        while (!userInput.equals("0")) {
            printMenu();
            userInput = scanner.nextLine().trim();

            switch (userInput) {
                case "1":
                    fileReader.readMonthlyReports();

                    break;
                case "2":
                    fileReader.readYearlyReport();

                    break;
                case "3":
                    if (checkReportsAreReady()) {
                        printMismatchingList();
                    }

                    break;
                case "4":
                    if (fileReader.areMonthlyReportsReady) {
                        dataManager.displayMonthlyReports(fileReader.monthsList, fileReader.monthlyReports);
                    } else {
                        System.out.println("Пожалуйста, считайте месячные отчеты перед выводом месячных данных.");
                        System.out.println();
                    }

                    break;
                case "5":
                    if (fileReader.isYearlyReportReady) {
                        dataManager.displayYearlyReport(fileReader.monthsList, fileReader.yearlyReport.currentYear,
                                                                           fileReader.yearlyReport.revenueList,
                                                                           fileReader.yearlyReport.expensesList);
                    } else {
                        System.out.println("Пожалуйста, считайте годовой отчет перед выводом данных за год.");
                        System.out.println();
                    }

                    break;
                default:
                    System.out.println("Такой команды нет.");
                    System.out.println();
                    break;
            }
        }
        System.out.println("Программа завершена.");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Выйти из приложения");
    }

    private static boolean checkReportsAreReady() {
        if (fileReader.areMonthlyReportsReady && fileReader.isYearlyReportReady) {
            return true;

        } else if (!fileReader.areMonthlyReportsReady && !fileReader.isYearlyReportReady) {
            System.out.println("Пожалуйста, запустите считывание годового и месячных отчетов " +
                    "перед проведением сверки.");
            System.out.println();

        } else if (!fileReader.areMonthlyReportsReady) {
            System.out.println("Пожалуйста, считайте месячные отчеты перед проведением сверки.");
            System.out.println();

        } else {
            System.out.println("Пожалуйста, считайте годовой отчет перед проведением сверки.");
            System.out.println();
        }
        return false;
    }

    private static void  printMismatchingList() {
        List<String> misMatchingMonths = dataManager.matchReports(fileReader.monthsList,
                fileReader.monthlyReports,
                fileReader.yearlyReport);

        if (misMatchingMonths.isEmpty()) {
            System.out.println("Несоответствий нет.");
            System.out.println();

        } else {
            System.out.print("Обнаружены несоответствия в следующих месяцах: ");
            for (int i = 0; i < misMatchingMonths.size() - 1; i++) {
                System.out.print(misMatchingMonths.get(i) + ", ");
            }
            System.out.println(misMatchingMonths.get(misMatchingMonths.size() - 1) + ".");
            System.out.println();
        }
    }
}

