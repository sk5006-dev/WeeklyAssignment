import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Asset {
    final String name;
    final double returnRate;
    final double volatility;

    Asset(String name, double returnRate, double volatility) {
        this.name = name;
        this.returnRate = returnRate;
        this.volatility = volatility;
    }

    @Override
    public String toString() {
        return name + ":" + returnRate + "%/" + volatility;
    }
}

public class Problem4_PortfolioSorting {

    static List<Asset> mergeSortByReturnRate(List<Asset> assets) {
        if (assets.size() <= 1) {
            return new ArrayList<>(assets);
        }

        int mid = assets.size() / 2;
        List<Asset> left = mergeSortByReturnRate(assets.subList(0, mid));
        List<Asset> right = mergeSortByReturnRate(assets.subList(mid, assets.size()));
        return merge(left, right);
    }

    static List<Asset> merge(List<Asset> left, List<Asset> right) {
        List<Asset> merged = new ArrayList<>(left.size() + right.size());
        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).returnRate <= right.get(j).returnRate) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }

        while (i < left.size()) {
            merged.add(left.get(i++));
        }
        while (j < right.size()) {
            merged.add(right.get(j++));
        }

        return merged;
    }

    static void quickSortByReturnDescVolatilityAsc(List<Asset> assets, PivotStrategy strategy) {
        quickSortByReturnDescVolatilityAsc(assets, 0, assets.size() - 1, strategy);
    }

    static void quickSortByReturnDescVolatilityAsc(List<Asset> assets, int low, int high, PivotStrategy strategy) {
        if (low >= high) {
            return;
        }

        if (high - low < 10) {
            insertionSortRange(assets, low, high);
            return;
        }

        int pivotIndex = selectPivot(assets, low, high, strategy);
        int partitionIndex = partition(assets, low, high, pivotIndex);
        quickSortByReturnDescVolatilityAsc(assets, low, partitionIndex - 1, strategy);
        quickSortByReturnDescVolatilityAsc(assets, partitionIndex + 1, high, strategy);
    }

    static int selectPivot(List<Asset> assets, int low, int high, PivotStrategy strategy) {
        if (strategy == PivotStrategy.RANDOM) {
            return low + new Random(42 + low + high).nextInt(high - low + 1);
        }

        int mid = low + (high - low) / 2;
        Asset a = assets.get(low);
        Asset b = assets.get(mid);
        Asset c = assets.get(high);

        if (compareForDescendingQuickSort(a, b) > 0) {
            Asset temp = a;
            a = b;
            b = temp;
        }
        if (compareForDescendingQuickSort(b, c) > 0) {
            Asset temp = b;
            b = c;
            c = temp;
        }
        if (compareForDescendingQuickSort(a, b) > 0) {
            b = a;
        }

        if (b == assets.get(low)) {
            return low;
        }
        if (b == assets.get(mid)) {
            return mid;
        }
        return high;
    }

    static int partition(List<Asset> assets, int low, int high, int pivotIndex) {
        Asset pivot = assets.get(pivotIndex);
        swap(assets, pivotIndex, high);
        int storeIndex = low;

        for (int i = low; i < high; i++) {
            if (compareForDescendingQuickSort(assets.get(i), pivot) < 0) {
                swap(assets, i, storeIndex);
                storeIndex++;
            }
        }

        swap(assets, storeIndex, high);
        return storeIndex;
    }

    static void insertionSortRange(List<Asset> assets, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Asset key = assets.get(i);
            int j = i - 1;
            while (j >= low && compareForDescendingQuickSort(assets.get(j), key) > 0) {
                assets.set(j + 1, assets.get(j));
                j--;
            }
            assets.set(j + 1, key);
        }
    }

    static int compareForDescendingQuickSort(Asset left, Asset right) {
        int returnComparison = Double.compare(right.returnRate, left.returnRate);
        if (returnComparison != 0) {
            return returnComparison;
        }
        return Double.compare(left.volatility, right.volatility);
    }

    static void swap(List<Asset> assets, int first, int second) {
        Asset temp = assets.get(first);
        assets.set(first, assets.get(second));
        assets.set(second, temp);
    }

    enum PivotStrategy {
        RANDOM,
        MEDIAN_OF_THREE
    }

    public static void main(String[] args) {
        List<Asset> assets = List.of(
                new Asset("AAPL", 12.0, 5.0),
                new Asset("TSLA", 8.0, 7.0),
                new Asset("GOOG", 15.0, 4.0),
                new Asset("NFLX", 12.0, 3.0)
        );

        List<Asset> mergeSorted = mergeSortByReturnRate(assets);
        System.out.println("Merge sort (stable asc): " + mergeSorted);

        List<Asset> quickSorted = new ArrayList<>(assets);
        quickSortByReturnDescVolatilityAsc(quickSorted, PivotStrategy.MEDIAN_OF_THREE);
        System.out.println("Quick sort (desc + low volatility first): " + quickSorted);
        System.out.println("Pivot strategies supported: RANDOM, MEDIAN_OF_THREE");
    }
}
