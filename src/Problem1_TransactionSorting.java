import java.util.ArrayList;
import java.util.List;

class Transaction {
    final String id;
    final double fee;
    final String timestamp;

    Transaction(String id, double fee, String timestamp) {
        this.id = id;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return id + ":" + fee + "@" + timestamp;
    }
}

public class Problem1_TransactionSorting {

    static class BubbleSortMetrics {
        final int passes;
        final int swaps;

        BubbleSortMetrics(int passes, int swaps) {
            this.passes = passes;
            this.swaps = swaps;
        }
    }

    static BubbleSortMetrics bubbleSortByFee(List<Transaction> transactions) {
        int passes = 0;
        int swaps = 0;

        for (int i = 0; i < transactions.size() - 1; i++) {
            boolean swapped = false;
            passes++;
            for (int j = 0; j < transactions.size() - i - 1; j++) {
                if (transactions.get(j).fee > transactions.get(j + 1).fee) {
                    Transaction current = transactions.get(j);
                    transactions.set(j, transactions.get(j + 1));
                    transactions.set(j + 1, current);
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }

        return new BubbleSortMetrics(passes, swaps);
    }

    static void insertionSortByFeeThenTimestamp(List<Transaction> transactions) {
        for (int i = 1; i < transactions.size(); i++) {
            Transaction key = transactions.get(i);
            int j = i - 1;

            while (j >= 0 && compareByFeeThenTimestamp(transactions.get(j), key) > 0) {
                transactions.set(j + 1, transactions.get(j));
                j--;
            }
            transactions.set(j + 1, key);
        }
    }

    static int compareByFeeThenTimestamp(Transaction left, Transaction right) {
        int feeComparison = Double.compare(left.fee, right.fee);
        if (feeComparison != 0) {
            return feeComparison;
        }
        return left.timestamp.compareTo(right.timestamp);
    }

    static List<Transaction> findHighFeeOutliers(List<Transaction> transactions) {
        List<Transaction> outliers = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.fee > 50.0) {
                outliers.add(transaction);
            }
        }
        return outliers;
    }

    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));
        transactions.add(new Transaction("id4", 25.0, "09:45"));
        transactions.add(new Transaction("id5", 75.0, "11:00"));

        List<Transaction> bubbleSorted = new ArrayList<>(transactions);
        BubbleSortMetrics metrics = bubbleSortByFee(bubbleSorted);
        System.out.println("BubbleSort (fee asc): " + bubbleSorted);
        System.out.println("Bubble metrics: passes=" + metrics.passes + ", swaps=" + metrics.swaps);

        List<Transaction> insertionSorted = new ArrayList<>(transactions);
        insertionSortByFeeThenTimestamp(insertionSorted);
        System.out.println("InsertionSort (fee+timestamp asc): " + insertionSorted);

        List<Transaction> outliers = findHighFeeOutliers(insertionSorted);
        System.out.println("High-fee outliers (> 50): " + (outliers.isEmpty() ? "none" : outliers));
    }
}
