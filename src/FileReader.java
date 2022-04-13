import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.HashMap;

public class FileReader {
    private final String CURRENT_YEAR;
    public final HashMap<String, String> MONTHS_LIST = new HashMap<>();

    public MonthData[] monthsData;
    public boolean isMonthsDataReady = false;

    public YearData yearData;
    public boolean isYearDataReady = false;

    FileReader(String year) {
        CURRENT_YEAR = year;
        MONTHS_LIST.put("01", "Январь");
        MONTHS_LIST.put("02", "Февраль");
        MONTHS_LIST.put("03", "Март");
        MONTHS_LIST.put("04", "Апрель");
        MONTHS_LIST.put("05", "Май");
        MONTHS_LIST.put("06", "Июнь");
        MONTHS_LIST.put("07", "Июль");
        MONTHS_LIST.put("08", "Август");
        MONTHS_LIST.put("09", "Сентябрь");
        MONTHS_LIST.put("10", "Октябрь");
        MONTHS_LIST.put("11", "Ноябрь");
        MONTHS_LIST.put("12", "Декабрь");
        monthsData = new MonthData[MONTHS_LIST.size()];
    }

    public void readMonthReports() {
        byte processedReports = 0;

        for (String monthNumber : MONTHS_LIST.keySet()) {
            try {
                Path filePath = Path.of("./resources/" + "m." + CURRENT_YEAR + monthNumber + ".csv");
                String rawData = Files.readString(filePath);
                String[] lines = rawData.split("\\n");
                HashMap<String, Integer> revenueList = new HashMap<>();
                HashMap<String, Integer> expensesList = new HashMap<>();

                for (short i = 1; i < lines.length; i++) {
                    String[] linesContent = lines[i].split(",");
                    String itemName = linesContent[0];
                    int itemSum = Integer.parseInt(linesContent[2]) * Integer.parseInt(linesContent[3]);

                    if (linesContent[1].equalsIgnoreCase("false")) {
                        revenueList.put(itemName, itemSum);
                    } else {
                        expensesList.put(itemName, itemSum);
                    }
                }
                monthsData[Integer.parseInt(monthNumber) - 1] = new MonthData(MONTHS_LIST.get(monthNumber),
                                                                                revenueList, expensesList);
                processedReports++;

            } catch (IOException e) {
                System.out.println("Не найден файл " + "m." + CURRENT_YEAR + monthNumber + ".csv");
            }
        }

        if (processedReports == 0) {
            System.out.println("Невозможно прочитать файлы с месячными отчётами. "
                                + "Возможно, они не находятся в нужной директории.");
            System.out.println();
        } else {
            isMonthsDataReady = true;
            System.out.println("Обработано месячных отчетов: " + processedReports);
            System.out.println();
        }
    }

    public void readYearReport() {
        HashMap<String, Integer> revenueList = new HashMap<>();
        HashMap<String, Integer> expensesList = new HashMap<>();
        Path filePath = Path.of("./resources/" + "y." + CURRENT_YEAR + ".csv");

        try {
            String rawData = Files.readString(filePath);
            String[] lines = rawData.split("\\n");

            for (short i = 1; i < lines.length; i++) {
                String[] linesContent = lines[i].split(",");
                String monthNumber = linesContent[0];
                int monthSum = Integer.parseInt(linesContent[1]);

                if (linesContent[2].equalsIgnoreCase("false")) {
                    revenueList.put(MONTHS_LIST.get(monthNumber), monthSum);
                } else {
                    expensesList.put(MONTHS_LIST.get(monthNumber), monthSum);
                }
            }
            yearData = new YearData(CURRENT_YEAR, revenueList, expensesList);
            isYearDataReady = true;
            System.out.println("Годовой отчет считан.");
            System.out.println();

        } catch (IOException e) {
            System.out.println("Не найден файл " + "y." + CURRENT_YEAR + ".csv");
            System.out.println("Возможно, он не находится в нужной директории.");
            System.out.println();
        }

    }

}
