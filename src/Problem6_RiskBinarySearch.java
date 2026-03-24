public class Problem6_RiskBinarySearch {

    static int floor(int[] arr, int target) {
        int res = -1;
        for (int val : arr) {
            if (val <= target) res = val;
        }
        return res;
    }

    static int ceiling(int[] arr, int target) {
        for (int val : arr) {
            if (val >= target) return val;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] risks = {10, 25, 50, 100};

        int target = 30;

        System.out.println("Floor: " + floor(risks, target));
        System.out.println("Ceiling: " + ceiling(risks, target));
    }
}