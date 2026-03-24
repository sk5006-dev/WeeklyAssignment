import java.util.*;

public class Problem5_AccountSearch {

    static int linearSearch(String[] arr, String target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(target)) return i;
        }
        return -1;
    }

    static int binarySearch(String[] arr, String target) {
        int low = 0, high = arr.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid].equals(target)) return mid;
            else if (arr[mid].compareTo(target) < 0) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        String[] logs = {"accA", "accB", "accB", "accC"};

        System.out.println("Linear: " + linearSearch(logs, "accB"));
        System.out.println("Binary: " + binarySearch(logs, "accB"));
    }
}