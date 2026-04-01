import java.util.Arrays;

class Trade {
    final String id;
    final int volume;

    Trade(String id, int volume) {
        this.id = id;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return id + ":" + volume;
    }
}

public class Problem3_TradeVolume {

    static void mergeSort(Trade[] trades) {
        Trade[] temp = new Trade[trades.length];
        mergeSort(trades, temp, 0, trades.length - 1);
    }

    static void mergeSort(Trade[] trades, Trade[] temp, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort(trades, temp, left, mid);
        mergeSort(trades, temp, mid + 1, right);
        merge(trades, temp, left, mid, right);
    }

    static void merge(Trade[] trades, Trade[] temp, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int index = left;

        while (i <= mid && j <= right) {
            if (trades[i].volume <= trades[j].volume) {
                temp[index++] = trades[i++];
            } else {
                temp[index++] = trades[j++];
            }
        }

        while (i <= mid) {
            temp[index++] = trades[i++];
        }
        while (j <= right) {
            temp[index++] = trades[j++];
        }

        for (int k = left; k <= right; k++) {
            trades[k] = temp[k];
        }
    }

    static void quickSortDescending(Trade[] trades) {
        quickSortDescending(trades, 0, trades.length - 1);
    }

    static void quickSortDescending(Trade[] trades, int low, int high) {
        if (low >= high) {
            return;
        }
        int pivotIndex = partitionDescending(trades, low, high);
        quickSortDescending(trades, low, pivotIndex - 1);
        quickSortDescending(trades, pivotIndex + 1, high);
    }

    static int partitionDescending(Trade[] trades, int low, int high) {
        int mid = low + (high - low) / 2;
        Trade pivot = trades[mid];
        swap(trades, mid, high);
        int storeIndex = low;

        for (int i = low; i < high; i++) {
            if (trades[i].volume > pivot.volume) {
                swap(trades, i, storeIndex);
                storeIndex++;
            }
        }

        swap(trades, storeIndex, high);
        return storeIndex;
    }

    static void swap(Trade[] trades, int first, int second) {
        Trade temp = trades[first];
        trades[first] = trades[second];
        trades[second] = temp;
    }

    static Trade[] mergeSortedLists(Trade[] first, Trade[] second) {
        Trade[] merged = new Trade[first.length + second.length];
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < first.length && j < second.length) {
            if (first[i].volume <= second[j].volume) {
                merged[k++] = first[i++];
            } else {
                merged[k++] = second[j++];
            }
        }

        while (i < first.length) {
            merged[k++] = first[i++];
        }
        while (j < second.length) {
            merged[k++] = second[j++];
        }

        return merged;
    }

    static long totalVolume(Trade[] trades) {
        long total = 0;
        for (Trade trade : trades) {
            total += trade.volume;
        }
        return total;
    }

    public static void main(String[] args) {
        Trade[] trades = {
                new Trade("trade3", 500),
                new Trade("trade1", 100),
                new Trade("trade2", 300),
                new Trade("trade4", 300)
        };

        Trade[] mergeSorted = Arrays.copyOf(trades, trades.length);
        mergeSort(mergeSorted);
        System.out.println("MergeSort: " + Arrays.toString(mergeSorted));

        Trade[] quickSorted = Arrays.copyOf(trades, trades.length);
        quickSortDescending(quickSorted);
        System.out.println("QuickSort (desc): " + Arrays.toString(quickSorted));

        Trade[] morning = {
                new Trade("m1", 100),
                new Trade("m2", 250)
        };
        Trade[] afternoon = {
                new Trade("a1", 150),
                new Trade("a2", 400)
        };
        Trade[] mergedSessions = mergeSortedLists(morning, afternoon);
        System.out.println("Merged sessions: " + Arrays.toString(mergedSessions));
        System.out.println("Total volume: " + totalVolume(mergedSessions));
    }
}
