import java.util.Map;

public class MonthlyReport {
    public final String monthName;
    public final Map<String, Integer> revenueList;
    public final Map<String, Integer> expensesList;

    MonthlyReport(String month, Map<String, Integer> revenue, Map<String, Integer> expenses) {
        monthName = month;
        revenueList = revenue;
        expensesList = expenses;
    }
}
