import java.util.HashMap;

public class YearData {
    public String currentYear;
    public HashMap<String, Integer> revenueList;
    public HashMap<String, Integer> expensesList;

    YearData(String year, HashMap<String, Integer> revenue, HashMap<String, Integer> expenses) {
        currentYear = year;
        revenueList = revenue;
        expensesList = expenses;
    }
}
