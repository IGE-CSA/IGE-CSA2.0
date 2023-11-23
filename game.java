import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class game {

     // Function to fill random positions on the game board with 1s
     public static boolean fillRandPos(int[][] game, int rand_1) {
        ArrayList<Integer> freeXRange = new ArrayList<>();
        ArrayList<Integer> freeYRange = new ArrayList<>();

        // Find available (empty) positions on the game board
        for (int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[0].length; j++) {
                if (game[i][j] == 0) {
                    freeXRange.add(i);
                    freeYRange.add(j);
                }
            }
        }

        // If there are available positions, randomly fill them with 1s
        if (!freeXRange.isEmpty()) {
            Random random = new Random();
            for (int i = 0; i < rand_1; i++) {
                if (freeXRange.isEmpty()) {
                    break;
                }
                int randPos = random.nextInt(freeXRange.size());
                game[freeXRange.get(randPos)][freeYRange.get(randPos)] = 1;
                freeXRange.remove(randPos);
                freeYRange.remove(randPos);
            }
            return true;
        }
        return false;
    }

    // Function to generate an empty game board of size 4x4
    public static int[][] generateBoard() {
        return new int[3][3];
    }

    // Function to generate the Fibonacci series up to 4x4 terms
    public static Map<String, Object> generateFibCache() {
        int term = 3 * 3;
        ArrayList<Integer> fib = new ArrayList<>();
        fib.add(1);
        fib.add(1);

        // Map to store the position of each Fibonacci term in the series
        Map<Integer, Integer> fibTermNumMap = new HashMap<>();

        // Generate Fibonacci series and populate the map
        for (int i = 2; i < term; i++) {
            fib.add(fib.get(i - 1) + fib.get(i - 2));
            fibTermNumMap.put(fib.get(i), i);
        }

        // Create a dictionary to store the Fibonacci series and the term-to-position map
        Map<String, Object> fibDict = new HashMap<>();
        fibDict.put("fib_series", fib);
        fibDict.put("fib_map", fibTermNumMap);

        return fibDict;
    }

    
    // Function to print the current state of the game board
    public static void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            StringBuilder col = new StringBuilder();
            for (int j = 0; j < board[0].length; j++) {
                col.append(board[i][j]).append(" ");
            }
            System.out.println(col);
        }
    }

    // Function to find the sum list for a given array based on the Fibonacci series
    // sum list = summing adjacent non-zero elements in the input array w/constraint that sum must be part of fibonacci
    public static ArrayList<Integer> getSumList(int[] arr, Map<String, Object> fibDict) {
        int adjelem1 = -1;
        ArrayList<Integer> sumList = new ArrayList<>();
        for (int elem : arr) {
            if (elem != 0) {
                if (adjelem1 == -1) {
                    adjelem1 = elem;
                } else {
                    int sum = adjelem1 + elem;
                    if (fibDict.containsKey("fib_map") && ((Map<Integer, Integer>) fibDict.get("fib_map")).containsKey(sum)) {
                        sumList.add(sum);
                        adjelem1 = -1;
                    } else {
                        sumList.add(adjelem1);
                        adjelem1 = elem;
                    }
                }
            }
        }

        if (adjelem1 != -1) {
            sumList.add(adjelem1);
        }
        return sumList;
    }

    // Function to update the game board based on the movement direction
    public static void updateBoard(int[][] board, int strp, ArrayList<Integer> updList, String dir) {
        int k = 0;
        int rows = board.length;
        int cols = board[0].length;
        int updLen = updList.size();

        // Update the board based on the movement direction
        if ("down".equals(dir)) {
            int rowctr = rows - 1;
            while (rowctr >= 0) {
                if (k < updLen) {
                    board[rowctr][strp] = updList.get(k);
                    k++;
                } else {
                    board[rowctr][strp] = 0;
                }
                rowctr--;
            }
        } else if ("up".equals(dir)) {
            int rowctr = 0;
            while (rowctr < rows) {
                if (k < updLen) {
                    board[rowctr][strp] = updList.get(k);
                    k++;
                } else {
                    board[rowctr][strp] = 0;
                }
                rowctr++;
            }
        } else if ("right".equals(dir)) {
            int colctr = cols - 1;
            while (colctr >= 0) {
                if (k < updLen) {
                    board[strp][colctr] = updList.get(k);
                    k++;
                } else {
                    board[strp][colctr] = 0;
                }
                colctr--;
            }
        }
        if ("left".equals(dir)) {
            int colctr = 0;
            while (colctr < cols) {
                if (k < updLen) {
                    board[strp][colctr] = updList.get(k);
                    k++;
                } else {
                    board[strp][colctr] = 0;
                }
                colctr++;
            }
        }
    }

    // Function to perform movement based on the direction
    public static void movDir(String dir, int[][] board, Map<String, Object> fibDict) {
        int cols = board[0].length;
        int rows = board.length;

        // Handle movement for up and down directions
        if ("up".equals(dir) || "down".equals(dir)) {
            for (int j = 0; j < cols; j++) {
                int[] colArr = new int[rows];
                for (int i = 0; i < rows; i++) {
                    colArr[i] = board[i][j];
                }
                // Reverse the array if moving down
                if ("down".equals(dir)) {
                    for (int i = 0; i < rows / 2; i++) {
                        int temp = colArr[i];
                        colArr[i] = colArr[rows - 1 - i];
                        colArr[rows - 1 - i] = temp;
                    }
                }
                ArrayList<Integer> updList = getSumList(colArr, fibDict);

                // Debugging: Print the sum list
                //System.out.println("Sum List for column " + j + ": " + updList);

                updateBoard(board, j, updList, dir);
            }
        } else if ("left".equals(dir) || "right".equals(dir)) {
            // Handle movement for left and right directions
            for (int i = 0; i < rows; i++) {
                int[] colArr = new int[cols];
                for (int j = 0; j < cols; j++) {
                    colArr[j] = board[i][j];
                }
                // Reverse the array if moving right
                if ("right".equals(dir)) {
                    for (int j = 0; j < cols / 2; j++) {
                        int temp = colArr[j];
                        colArr[j] = colArr[cols - 1 - j];
                        colArr[cols - 1 - j] = temp;
                    }
                }
                ArrayList<Integer> updList = getSumList(colArr, fibDict);

                // Debugging: Print the sum list
                //System.out.println("Sum List for row " + i + ": " + updList);

                updateBoard(board, i, updList, dir);
            }
        }
    }

    // Function to check if the player has won by reaching the last Fibonacci number
    public static boolean checkWin(int[][] board, Map<String, Object> fibDict) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == ((ArrayList<Integer>) fibDict.get("fib_series")).get(((ArrayList<Integer>) fibDict.get("fib_series")).size() - 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Function to calculate the sum of each row in the game board
    public static ArrayList<Integer> calculateRowSums(int[][] board) {
        ArrayList<Integer> rowSums = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            int sum = 0;
            for (int j = 0; j < board[0].length; j++) {
                sum += board[i][j];
            }
            rowSums.add(sum);
        }
        return rowSums;
    }

    public static void main(String[] args) {
        // Generate the initial game board and Fibonacci series cache
        int[][] board = generateBoard();
        Map<String, Object> fibDict = generateFibCache();

        // Initial board: Fill random positions on the board with 2 1s
        fillRandPos(board, 2);

        // Print the initial board state
        System.out.println("Initial board state:");
        printBoard(board);

        String[] dirMoves = {"up", "down", "left", "right"};

        // Check if the player has already won
        if (checkWin(board, fibDict)) {
            System.out.println("You won!");
            return;
        }
        // Main game loop
        while (true) {
            // Get the user's move direction
            System.out.print("Please enter the direction (up/down/left/right). For exiting, enter 'exit': ");
            Scanner scanner = new Scanner(System.in);
            String dir = scanner.next().toLowerCase();

            // Check if the user wants to exit
            if ("exit".equals(dir)) {
                break;
            } else if (Arrays.asList(dirMoves).contains(dir)) {
                // Perform the move and update the board
                movDir(dir, board, fibDict);
            } else {
                System.out.println("Please enter a valid move.");
                printBoard(board);
                continue;
            }

            // Check if the player has won after the move
            if (checkWin(board, fibDict)) {
                System.out.println("You won!");

                 // Calculate row sums and total sum
                ArrayList<Integer> rowSums = calculateRowSums(board);
                int totalScore = rowSums.stream().mapToInt(Integer::intValue).sum();

                // Print row sums and total sum
                //System.out.println("Row Sums: " + rowSums);
                System.out.println("Your total score: " + totalScore);

                break;
            }

            // If no available spaces to fill with 1s, player lost
            if (!fillRandPos(board, 1)) {
                System.out.println("Game lost.");

                // Calculate row sums and total sum
                ArrayList<Integer> rowSums = calculateRowSums(board);
                int totalScore = rowSums.stream().mapToInt(Integer::intValue).sum();

                // Print row sums and total sum
                //System.out.println("Row Sums: " + rowSums);
                System.out.println("Your total score: " + totalScore);

                break;
            }

            // Print the updated board
            printBoard(board);
        }

        // Print the final state of the board
        System.out.println("Final board state:");
        printBoard(board);
        System.out.println("Thank you!");
    }
}
