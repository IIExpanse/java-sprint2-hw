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
            HashMap<String, Integer> revenueList = new HashMap<>();
            HashMap<String, Integer> expensesList = new HashMap<>();

            Path filePath = Path.of("./resources/" + "m." + CURRENT_YEAR + monthNumber + ".csv");

            try {
                String rawData = Files.readString(filePath);
//                System.out.println(rawData);
                String[] lines = rawData.split("\\n");


//                for (String line : lines) {
//                    System.out.println(line);
//                }

                for (short i = 1; i < lines.length; i++) {
                    String[] linesContent = lines[i].split(",");
//                    for (String content : linesContent) {
//                    System.out.println(content);
//                    }
//                    System.out.println("Строка 0:" + linesContent[0]);
//                    System.out.println("Строка 1:" + linesContent[1]);
//                    System.out.println("Строка 2:" + linesContent[2]);
//                    System.out.println("Строка 3:" + linesContent[3]);

                    String itemName = linesContent[0];
                    int itemSum = Integer.parseInt(linesContent[2]) * Integer.parseInt(linesContent[3]);
//                    System.out.println("Результат умножения: " + itemSum);

                    if (linesContent[1].equalsIgnoreCase("false")) {
                        revenueList.put(itemName, itemSum);
                    } else {
                        expensesList.put(itemName, itemSum);
//                        System.out.println("Помещаю в расходы: " + itemSum);
//                        System.out.println("В расходах размещено: " + itemName + ": " + expensesList.get(itemName));
                    }
                }
//                System.out.println("Доходы за месяц " + monthNumber + ":");
//                for (String item :  revenueList.keySet()) {
//                    System.out.println("Сумма по товару " + item + ":" + revenueList.get(item));
//                }
//
//                System.out.println("Расходы за месяц " + monthNumber + ":");
//                for (String item :  expensesList.keySet()) {
//                    System.out.println("Сумма по товару " + item + ":" + expensesList.get(item));
//                }

                monthsData[Integer.parseInt(monthNumber) - 1] = new MonthData(MONTHS_LIST.get(monthNumber),
                                                                                revenueList, expensesList);

//                for (String item : months[Integer.parseInt(monthNumber) - 1].expensesList.keySet()) {
//                    System.out.println(item + ": " + months[Integer.parseInt(monthNumber) - 1].expensesList.get(item));
//                }

                processedReports++;
                System.out.println("Отчет " + monthNumber + " прочитан");

            } catch (IOException e) {
                System.out.println("Ошибка при считывании файла " + filePath);
            }


        }
        System.out.println("Обработано отчетов: " + processedReports);

//        for (String monthNumber : MONTHS_LIST.keySet()) {
//            if ((Integer.parseInt(monthNumber) - 1) < 3) {
//                System.out.println("Доходы за месяц " + monthNumber + ":");
//                for (String item : monthsData[Integer.parseInt(monthNumber) - 1].revenueList.keySet()) {
//                    System.out.println("Сумма по товару " + item + ":"
//                            + monthsData[Integer.parseInt(monthNumber) - 1].revenueList.get(item));
//                }
//            }
//        }

//        for (String monthNumber : MONTHS_LIST.keySet()) {
//            if ((Integer.parseInt(monthNumber) - 1) < 3) {
//                System.out.println("Расходы за месяц " + monthNumber + ":");
//                for (String item : monthsData[Integer.parseInt(monthNumber) - 1].expensesList.keySet()) {
//                    System.out.println("Сумма по товару " + item + ":"
//                                      + monthsData[Integer.parseInt(monthNumber) - 1].expensesList.get(item));
//                }
//            }
//        }

        if (processedReports == 0) {
            System.out.println("Невозможно прочитать файлы с месячными отчётами. "
                                + "Возможно, они не находятся в нужной директории.");
        } else {
            isMonthsDataReady = true;
        }
    }

    public void readYearReport() {
        HashMap<String, Integer> revenueList = new HashMap<>();
        HashMap<String, Integer> expensesList = new HashMap<>();

        Path filePath = Path.of("./resources/" + "y." + CURRENT_YEAR + ".csv");

        try {

            String rawData = Files.readString(filePath);
//                System.out.println(rawData);
            String[] lines = rawData.split("\\n");


//                for (String line : lines) {
//                    System.out.println(line);
//                }

            for (short i = 1; i < lines.length; i++) {
                String[] linesContent = lines[i].split(",");
//                    for (String content : linesContent) {
//                    System.out.println(content);
//                    }
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

//            for (String month : yearData.expensesList.keySet()) {
//                System.out.println("Доход за " + month  + ": " + yearData.revenueList.get(month));
//                System.out.println("Расход за " + month  + ": " + yearData.expensesList.get(month));
//            }

        } catch (IOException e) {
            System.out.println("Ошибка при считывании файла " + filePath);
        }

    }

}
