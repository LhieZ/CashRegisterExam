package cashregister.model;

import java.util.List;

public class CashRegisterModel {

    private int total;
    private List<Integer> denominationCounts;

    public CashRegisterModel(List<Integer> denominationCounts) {
        this.denominationCounts = denominationCounts;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setDenominationCounts(List<Integer> denominationCounts) {
        this.denominationCounts = denominationCounts;
    }

    public List<Integer> getDenominationCounts() {
        return denominationCounts;
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (Integer count : denominationCounts) {
            temp.append(count).append(" ");
        }
        return "$" + total + " " + temp.toString();
    }
}
