import java.util.HashMap;

public class MonthData {
    public String monthName;
    public HashMap<String, Integer> revenueList;
    public HashMap<String, Integer> expensesList;

    MonthData(String month, HashMap<String, Integer> revenue, HashMap<String, Integer> expenses) {
        monthName = month;
        revenueList = revenue;
        expensesList = expenses;
    }
}
