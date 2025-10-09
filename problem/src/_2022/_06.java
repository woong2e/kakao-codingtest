package _2022;
/*
단순히 브루트포스로 하면 효율성 문제는 해결할 수 없음
누적합을 이용해 최대 25만번의 스킬을 한번에 계산하여 한번만 board에 더해주는 방법이 있음
원래는 K개의 스킬을 N * M 배열에 적용하여 O(K*N*M)의 시간복잡도
하지만 누적합을 이용하여 O(K + N*M)으로 해결 가능
 */
public class _06 {
    public int solution(int[][] board, int[][] skill) {
        // 변화량을 처리하는 방법 - 누적합
        int[][] nuzuckhap = new int[board.length + 1][board[0].length + 1];
        for (int[] what : skill) {
            int degree = what[5];
            if (what[0] == 1) {
                degree *= -1;
            }

            nuzuckhap[what[1]][what[2]] += degree;
            nuzuckhap[what[1]][what[4] + 1] -= degree;
            nuzuckhap[what[3] + 1][what[2]] -= degree;
            nuzuckhap[what[3] + 1][what[4] + 1] += degree;
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                nuzuckhap[i][j + 1] += nuzuckhap[i][j];
            }
        }
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board.length; j++) {
                nuzuckhap[j + 1][i] += nuzuckhap[j][i];
            }
        }


        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] + nuzuckhap[i][j] > 0) {
                    count++;
                }
            }
        }
        return count;
    }
}
