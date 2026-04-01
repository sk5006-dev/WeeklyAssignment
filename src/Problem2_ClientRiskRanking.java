import java.util.Arrays;

class Client {
    final String name;
    final int riskScore;
    final double accountBalance;

    Client(String name, int riskScore, double accountBalance) {
        this.name = name;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return name + "(" + riskScore + ", " + accountBalance + ")";
    }
}

public class Problem2_ClientRiskRanking {

    static int bubbleSortAscending(Client[] clients) {
        int swaps = 0;
        for (int i = 0; i < clients.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < clients.length - i - 1; j++) {
                if (clients[j].riskScore > clients[j + 1].riskScore) {
                    Client temp = clients[j];
                    clients[j] = clients[j + 1];
                    clients[j + 1] = temp;
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        return swaps;
    }

    static void insertionSortDescending(Client[] clients) {
        for (int i = 1; i < clients.length; i++) {
            Client key = clients[i];
            int j = i - 1;

            while (j >= 0 && compareDescending(clients[j], key) > 0) {
                clients[j + 1] = clients[j];
                j--;
            }
            clients[j + 1] = key;
        }
    }

    static int compareDescending(Client left, Client right) {
        int riskComparison = Integer.compare(right.riskScore, left.riskScore);
        if (riskComparison != 0) {
            return riskComparison;
        }
        return Double.compare(right.accountBalance, left.accountBalance);
    }

    static Client[] topHighestRiskClients(Client[] clients, int limit) {
        return Arrays.copyOf(clients, Math.min(limit, clients.length));
    }

    public static void main(String[] args) {
        Client[] clients = {
                new Client("clientC", 80, 1500),
                new Client("clientA", 20, 4000),
                new Client("clientB", 50, 2500),
                new Client("clientD", 80, 3000)
        };

        Client[] ascending = Arrays.copyOf(clients, clients.length);
        int swaps = bubbleSortAscending(ascending);
        System.out.println("Bubble (asc): " + Arrays.toString(ascending));
        System.out.println("Bubble swaps: " + swaps);

        Client[] descending = Arrays.copyOf(clients, clients.length);
        insertionSortDescending(descending);
        System.out.println("Insertion (risk desc + balance desc): " + Arrays.toString(descending));
        System.out.println("Top risks: " + Arrays.toString(topHighestRiskClients(descending, 10)));
    }
}
