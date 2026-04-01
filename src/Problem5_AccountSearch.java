import java.util.Arrays;

public class Problem5_AccountSearch {

    static class SearchResult {
        final int index;
        final int comparisons;

        SearchResult(int index, int comparisons) {
            this.index = index;
            this.comparisons = comparisons;
        }

        @Override
        public String toString() {
            return "index=" + index + ", comparisons=" + comparisons;
        }
    }

    static SearchResult linearSearchFirst(String[] accounts, String target) {
        int comparisons = 0;
        for (int i = 0; i < accounts.length; i++) {
            comparisons++;
            if (accounts[i].equals(target)) {
                return new SearchResult(i, comparisons);
            }
        }
        return new SearchResult(-1, comparisons);
    }

    static SearchResult linearSearchLast(String[] accounts, String target) {
        int comparisons = 0;
        for (int i = accounts.length - 1; i >= 0; i--) {
            comparisons++;
            if (accounts[i].equals(target)) {
                return new SearchResult(i, comparisons);
            }
        }
        return new SearchResult(-1, comparisons);
    }

    static SearchResult binarySearchAny(String[] sortedAccounts, String target) {
        int low = 0;
        int high = sortedAccounts.length - 1;
        int comparisons = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            comparisons++;
            int comparison = sortedAccounts[mid].compareTo(target);
            if (comparison == 0) {
                return new SearchResult(mid, comparisons);
            }
            if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return new SearchResult(-1, comparisons);
    }

    static int lowerBound(String[] sortedAccounts, String target) {
        int low = 0;
        int high = sortedAccounts.length;

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (sortedAccounts[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    static int upperBound(String[] sortedAccounts, String target) {
        int low = 0;
        int high = sortedAccounts.length;

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (sortedAccounts[mid].compareTo(target) <= 0) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    static int countOccurrences(String[] sortedAccounts, String target) {
        return upperBound(sortedAccounts, target) - lowerBound(sortedAccounts, target);
    }

    public static void main(String[] args) {
        String[] logs = {"accB", "accA", "accB", "accC", "accB"};

        SearchResult linearFirst = linearSearchFirst(logs, "accB");
        SearchResult linearLast = linearSearchLast(logs, "accB");
        System.out.println("Linear first accB: " + linearFirst);
        System.out.println("Linear last accB: " + linearLast);

        String[] sortedLogs = Arrays.copyOf(logs, logs.length);
        Arrays.sort(sortedLogs);
        SearchResult binaryMatch = binarySearchAny(sortedLogs, "accB");
        System.out.println("Sorted logs: " + Arrays.toString(sortedLogs));
        System.out.println("Binary accB: " + binaryMatch + ", count=" + countOccurrences(sortedLogs, "accB"));
        System.out.println("Time complexity: linear O(n), binary O(log n) after sorting");
    }
}
