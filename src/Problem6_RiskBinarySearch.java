import java.util.Arrays;

public class Problem6_RiskBinarySearch {

    static class SearchOutcome {
        final int value;
        final int comparisons;

        SearchOutcome(int value, int comparisons) {
            this.value = value;
            this.comparisons = comparisons;
        }

        @Override
        public String toString() {
            return "value=" + value + ", comparisons=" + comparisons;
        }
    }

    static SearchOutcome linearSearch(int[] riskBands, int target) {
        int comparisons = 0;
        for (int riskBand : riskBands) {
            comparisons++;
            if (riskBand == target) {
                return new SearchOutcome(riskBand, comparisons);
            }
        }
        return new SearchOutcome(-1, comparisons);
    }

    static int insertionPoint(int[] sortedRiskBands, int target) {
        int low = 0;
        int high = sortedRiskBands.length;

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (sortedRiskBands[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }

    static SearchOutcome floor(int[] sortedRiskBands, int target) {
        int low = 0;
        int high = sortedRiskBands.length - 1;
        int floor = -1;
        int comparisons = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            comparisons++;
            if (sortedRiskBands[mid] <= target) {
                floor = sortedRiskBands[mid];
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return new SearchOutcome(floor, comparisons);
    }

    static SearchOutcome ceiling(int[] sortedRiskBands, int target) {
        int low = 0;
        int high = sortedRiskBands.length - 1;
        int ceiling = -1;
        int comparisons = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            comparisons++;
            if (sortedRiskBands[mid] >= target) {
                ceiling = sortedRiskBands[mid];
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return new SearchOutcome(ceiling, comparisons);
    }

    public static void main(String[] args) {
        int[] unsortedBands = {50, 10, 100, 25};
        int[] sortedBands = Arrays.copyOf(unsortedBands, unsortedBands.length);
        Arrays.sort(sortedBands);
        int target = 30;

        System.out.println("Linear threshold lookup: " + linearSearch(unsortedBands, target));
        System.out.println("Sorted risks: " + Arrays.toString(sortedBands));
        System.out.println("Binary insertion point for 30: " + insertionPoint(sortedBands, target));
        System.out.println("Floor(30): " + floor(sortedBands, target));
        System.out.println("Ceiling(30): " + ceiling(sortedBands, target));
    }
}
