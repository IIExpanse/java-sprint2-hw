import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class DataManager {

    public List<String> matchReports(Map<String, String> monthsList, MonthlyReport[] monthlyReports,
                                                                         YearlyReport yearlyReport) {
        List<String> mismatchingMonths = new ArrayList<>();

        for (String monthNumber : monthsList.keySet()) {
            int monthIndex = (Integer.parseInt(monthNumber) - 1);
            String monthName = monthsList.get(monthNumber);

            if ((monthlyReports[monthIndex] != null) && (yearlyReport.revenueList.get(monthName) != null)
                                                     && (yearlyReport.expensesList.get(monthName) != null)) {
                if (checkIfMonthsMismatch(monthIndex, monthName, monthlyReports, yearlyReport)) {
                    mismatchingMonths.add(monthName);
                }

            } else if ((monthlyReports[monthIndex] != null) || (yearlyReport.revenueList.get(monthName) != null)
                                                            || (yearlyReport.expensesList.get(monthName) != null)) {
                mismatchingMonths.add(monthName);
            }
        }
        return mismatchingMonths;
    }

    private boolean checkIfMonthsMismatch(int monthIndex, String monthName, MonthlyReport[] monthlyReports,
                                          YearlyReport yearlyReport) {

        Map<String, Integer> reviewedMonthRevenue = monthlyReports[monthIndex].revenueList;
        Map<String, Integer> reviewedMonthExpenses = monthlyReports[monthIndex].expensesList;
        int monthTotalRevenue = 0;
        int monthTotalExpenses = 0;
        boolean isRevenueMatching = true;
        boolean isExpensesMatching = true;

        for (int revenue : reviewedMonthRevenue.values()) {
            monthTotalRevenue += revenue;
        }
        for (int expenses : reviewedMonthExpenses.values()) {
            monthTotalExpenses += expenses;
        }

        if (monthTotalRevenue != yearlyReport.revenueList.get(monthName)) {
            isRevenueMatching = false;
        }
        if (monthTotalExpenses != yearlyReport.expensesList.get(monthName)) {
            isExpensesMatching = false;
        }

        return !isRevenueMatching || !isExpensesMatching;
    }

    public void displayMonthlyReports(Map<String, String> monthsList, MonthlyReport[] monthlyReports) {
        for (String monthNumber : monthsList.keySet()) {
            int monthIndex = (Integer.parseInt(monthNumber) - 1);

            if (monthlyReports[monthIndex] != null) {
                Map<String, Integer> reviewedMonthRevenue = monthlyReports[monthIndex].revenueList;
                Map<String, Integer> reviewedMonthExpenses = monthlyReports[monthIndex].expensesList;
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
                System.out.println("Самый прибыльный товар: " + maxRevenueItem + " на сумму " + maxRevenue + ".");
                System.out.println("Самая большая трата: " + maxExpenseItem + " на сумму " + maxExpense + ".");
                System.out.println();
            }
        }
    }

    public void displayYearlyReport(Map<String, String> monthsList, String year,
                                    Map<String, Integer> revenueList, Map<String, Integer> expensesList) {
        float expensesSum = 0;
        float revenueSum = 0;

        System.out.println("Рассматриваемый год: " + year);
        System.out.println();
        for (String monthName : monthsList.values()) {
            if ((expensesList.get(monthName) != null) && (expensesList.get(monthName) != null)) {
                System.out.println("Прибыль за " + monthName + " составила "
                                    + (revenueList.get(monthName) - expensesList.get(monthName)) + ".");
            }
        }
        System.out.println();

        for (String monthName : monthsList.values()) {
            if ((expensesList.get(monthName) != null) && (expensesList.get(monthName) != null)) {
                expensesSum += expensesList.get(monthName);
                revenueSum += revenueList.get(monthName);
            }
        }

        System.out.println("Средний расход за все месяцы в году: " + expensesSum / expensesList.size() + ".");
        System.out.println("Средний доход за все месяцы в году: " + revenueSum / revenueList.size() + ".");
        System.out.println();
    }
}
