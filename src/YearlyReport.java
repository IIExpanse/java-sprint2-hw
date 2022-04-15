import java.util.Map;

public class YearlyReport {
    public final String currentYear;
    public final Map<String, Integer> revenueList;
    public final Map<String, Integer> expensesList;

    YearlyReport(String year, Map<String, Integer> revenue, Map<String, Integer> expenses) {
        currentYear = year;
        revenueList = revenue;
        expensesList = expenses;
    }
}
