package _2023;

/*
딱 보자마자 Greedy로 풀어야겠다는 생각을 했다
갈 때 배달을 다하고, 올 때 픽업을 다하는 것으로 생각하면 됨
 */
public class _02 {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0;

        // 배달,픽업할 택배상자가 있는 집의 최대 인덱스를 구한다
        int i = -1;
        int j = -1;
        for (int k = n - 1; k >= 0; k--) {
            if (deliveries[k] > 0) {
                i = k;
                break;
            }
        }
        for (int k = n - 1; k >= 0; k--) {
            if (pickups[k] > 0) {
                j = k;
                break;
            }
        }

        // 배달 or 픽업이 하나라도 끝날 때까지 Greedy하게 배달, 수거를 진행한다
        while (i > -1 && j > -1) {
            // 배달 or 픽업 중 더 멀리 있는 집의 거리를 더해준다
            if (i >= j) {
                answer += 2 * (i + 1);
            } else {
                answer += 2 * (j + 1);
            }

            // count가 cap과 같을 때에도 배달할 것이 없는 것 경우를 미리 없애야 한다
            int countI = 0;
            while (countI <= cap && i > -1) { // countI == cap일 때
                if (deliveries[i] == 0) {
                    i--;
                    continue;
                }
                if (countI == cap) {  // 이 부분
                    break;
                }
                deliveries[i]--;
                countI++;
            }

            int countJ = 0;
            while (countJ <= cap && j > -1) {
                if (pickups[j] == 0) {
                    j--;
                    continue;
                }
                if (countJ == cap) {
                    break;
                }
                pickups[j]--;
                countJ++;
            }
        }

        // 배달 or 픽업을 하나라도 완료 한 경우 이제 나머지 한가지만 수행하면 된다
        int tmp = i;
        int[] tmpArray = deliveries;
        if (tmp == -1) {
            tmp = j;
            tmpArray = pickups;
        }

        while (tmp > -1) {
            answer += 2 * (tmp + 1);

            int count = 0;
            while (count <= cap && tmp > -1) {
                if (tmpArray[tmp] == 0) {
                    tmp--;
                    continue;
                }
                if (count == cap) {
                    break;
                }
                tmpArray[tmp]--;
                count++;
            }
        }

        return answer;
    }
}
