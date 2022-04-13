import java.util.ArrayList;
import java.util.HashMap;

public class DataManager {

    public ArrayList<String> matchReports(HashMap<String, String> monthsList,
                                          MonthData[] monthsData, YearData yearData) {
        ArrayList<String> mismatchingMonths = new ArrayList<>();

        for (String monthNumber : monthsList.keySet()) {
            byte monthIndex = (byte) (Integer.parseInt(monthNumber) - 1);
            String monthName = monthsList.get(monthNumber);

            if ((monthsData[monthIndex] != null) && (yearData.revenueList.get(monthName) != null)
                    && (yearData.expensesList.get(monthName) != null)) {
                HashMap<String, Integer> reviewedMonthRevenue = monthsData[monthIndex].revenueList;
                HashMap<String, Integer> reviewedMonthExpenses = monthsData[monthIndex].expensesList;
                int monthTotalRevenue = 0;
                int monthTotalExpenses = 0;
                boolean isRevenueMatching = true;
                boolean isExpensesMatching = true;

//                System.out.println("Индекс месяца: " + monthIndex);


                for (int revenue : reviewedMonthRevenue.values()) {
                    monthTotalRevenue += revenue;
//                    System.out.println(revenue);
//                    System.out.println(monthTotalRevenue);
                }
//                System.out.println("Общий доход за месяц " + (monthIndex + 1) + ": " + monthTotalRevenue);

                for (int expenses : reviewedMonthExpenses.values()) {
                    monthTotalExpenses += expenses;
//                    System.out.println(expenses);
//                    System.out.println(monthTotalExpenses);
//                System.out.println(monthTotalExpenses);
                }
//                System.out.println("Общий расход за месяц " + (monthIndex + 1) + ": " + monthTotalExpenses);

                if (monthTotalRevenue != yearData.revenueList.get(monthName)) {
                    isRevenueMatching = false;
                }
                if (monthTotalExpenses != yearData.expensesList.get(monthName)) {
                    isExpensesMatching = false;
                }

                if (!isRevenueMatching || !isExpensesMatching) {
                    mismatchingMonths.add(monthName);
                }

            } else if ((monthsData[monthIndex] != null) || (yearData.revenueList.get(monthName) != null)
                    || (yearData.expensesList.get(monthName) != null)) {
                mismatchingMonths.add(monthName);
            }
        }
        return mismatchingMonths;
    }

    public void displayMonthsData(HashMap<String, String> monthsList, MonthData[] monthsData) {
        for (String monthNumber : monthsList.keySet()) {
            byte monthIndex = (byte) (Integer.parseInt(monthNumber) - 1);

            if (monthsData[monthIndex] != null) {
                HashMap<String, Integer> reviewedMonthRevenue = monthsData[monthIndex].revenueList;
                HashMap<String, Integer> reviewedMonthExpenses = monthsData[monthIndex].expensesList;
                int maxRevenue = 0;
                int maxExpense = 0;
                String maxRevenueItem = null;
                String maxExpenseItem = null;

                for (String item : reviewedMonthRevenue.keySet()) {
                    int revenue = reviewedMonthRevenue.get(item);

                    if (maxRevenue < revenue) {
                        maxRevenue = revenue;
                        maxRevenueItem = item;
                    }
                }
                for (String item : reviewedMonthExpenses.keySet()) {
                    int revenue = reviewedMonthExpenses.get(item);

                    if (maxExpense < revenue) {
                        maxExpense = revenue;
                        maxExpenseItem = item;
                    }
                }
                System.out.println("Месяц: " + monthsList.get(monthNumber));
                System.out.println("Самый прибыльный товар: " + maxRevenueItem + ", " + maxRevenue + ".");
                System.out.println("Самая большая трата: " + maxExpenseItem + ", " + maxExpense + ".");
                System.out.println();
            }
        }
    }

    public void displayYearData(HashMap<String, String> monthsList, String year, HashMap<String, Integer> revenueList,
                                            HashMap<String, Integer> expensesList) {
        float expensesSum = 0;
        float revenueSum = 0;

        System.out.println("Рассматриваемый год: " + year);
        System.out.println();
        for (String monthName : monthsList.values()) {
            if ((expensesList.get(monthName) != null) && (expensesList.get(monthName) != null)) {
                System.out.println("Прибыль за месяц " + monthName + " составила " + "."
                                    + (revenueList.get(monthName) - expensesList.get(monthName)));
            }
        }
        System.out.println();

        for (String monthName : monthsList.values()) {
            if ((expensesList.get(monthName) != null) && (expensesList.get(monthName) != null)) {
                expensesSum += expensesList.get(monthName);
                revenueSum += revenueList.get(monthName);
            }
        }

//        System.out.println("Расход: " + expensesSum + ".");
        System.out.println("Средний расход за все месяцы в году: " + expensesSum / expensesList.size() + ".");
//        System.out.println("Доход: " + revenueSum + ".");
        System.out.println("Средний доход за все месяцы в году: " + revenueSum / revenueList.size() + ".");
        System.out.println();
    }
}
