import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class FileReader {
    private final String currentYear;
    public final Map<String, String> monthsList = new TreeMap<>();

    public MonthlyReport[] monthlyReports;
    public boolean areMonthlyReportsReady = false;

    public YearlyReport yearlyReport;
    public boolean isYearlyReportReady = false;

    FileReader(String year) {
        currentYear = year;
        monthsList.put("01", "Январь");
        monthsList.put("02", "Февраль");
        monthsList.put("03", "Март");
        monthsList.put("04", "Апрель");
        monthsList.put("05", "Май");
        monthsList.put("06", "Июнь");
        monthsList.put("07", "Июль");
        monthsList.put("08", "Август");
        monthsList.put("09", "Сентябрь");
        monthsList.put("10", "Октябрь");
        monthsList.put("11", "Ноябрь");
        monthsList.put("12", "Декабрь");
        monthlyReports = new MonthlyReport[monthsList.size()];
    }

    public void readMonthlyReports() {
        byte processedReports = 0;

        for (String monthNumber : monthsList.keySet()) {
            Path filePath = Path.of("./resources/" + "m." + currentYear + monthNumber + ".csv");

            if (Files.exists(filePath)) {
                try {
                    String rawData = Files.readString(filePath);
                    String[] lines = rawData.split("\\n");
                    Map<String, Integer> revenueList = new HashMap<>();
                    Map<String, Integer> expensesList = new HashMap<>();

                    for (int i = 1; i < lines.length; i++) {
                        String[] linesContent = lines[i].split(",");
                        String itemName = linesContent[0];
                        int itemSum = Integer.parseInt(linesContent[2]) * Integer.parseInt(linesContent[3]);

                        if (linesContent[1].equalsIgnoreCase("false")) {
                            revenueList.put(itemName, itemSum);
                        } else {
                            expensesList.put(itemName, itemSum);
                        }
                    }
                    monthlyReports[Integer.parseInt(monthNumber) - 1] = new MonthlyReport(monthsList.get(monthNumber),
                                                                                           revenueList, expensesList);
                    processedReports++;

                } catch (IOException e) {
                    System.out.println("Не найден файл " + "m." + currentYear + monthNumber + ".csv");
                }
            }
        }

        if (processedReports == 0) {
            System.out.println("Невозможно прочитать файлы с месячными отчётами. "
                                + "Возможно, они не находятся в нужной директории.");
            System.out.println();
        } else {
            areMonthlyReportsReady = true;
            System.out.println("Обработано месячных отчетов: " + processedReports);
            System.out.println();
        }
    }

    public void readYearlyReport() {
        Map<String, Integer> revenueList = new HashMap<>();
        Map<String, Integer> expensesList = new HashMap<>();
        Path filePath = Path.of("./resources/" + "y." + currentYear + ".csv");

        try {
            String rawData = Files.readString(filePath);
            String[] lines = rawData.split("\\n");

            for (int i = 1; i < lines.length; i++) {
                String[] linesContent = lines[i].split(",");
                String monthNumber = linesContent[0];
                int monthSum = Integer.parseInt(linesContent[1]);

                if (linesContent[2].equalsIgnoreCase("false")) {
                    revenueList.put(monthsList.get(monthNumber), monthSum);
                } else {
                    expensesList.put(monthsList.get(monthNumber), monthSum);
                }
            }
            yearlyReport = new YearlyReport(currentYear, revenueList, expensesList);
            isYearlyReportReady = true;
            System.out.println("Годовой отчет считан.");
            System.out.println();

        } catch (IOException e) {
            System.out.println("Не найден файл " + "y." + currentYear + ".csv");
            System.out.println("Возможно, он не находится в нужной директории.");
            System.out.println();
        }

    }

}
