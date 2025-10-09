package _2022;

/*
조건이 개빡센 완전탐색 구현문제
처음에 N = 10으로 백트래킹을 생각했으나, 화살을 쏘는 경우는 무조건 어피치보다 1개 많게, 단 하나의 경우이다.
화살을 쏘거나 쏘지 않거나 두가지 갈래로 생각하면 되어 완전탐색으로 풀이가 가능하다는 것을 알게되었다
그 뒤론 쉽게 풀림
 */
public class _04 {
    private int[] tmpAnswer;
    private int tmpScoreDiff;
    public int[] solution(int n, int[] info) {
        int[] answer = new int[11];
        int[] apeach = new int[11];
        // 작은 수 부터 맞히는 경우를 고려하기위해 뒤집고 시작한다 -> 라이언이 우승할 수 있는 방법이 여러 가지 일 경우, 가장 낮은 점수를 더 많이 맞힌 경우를 return 하기 위해
        for (int i = 0; i < 11; i++) {
            apeach[i] = info[10 - i];
        }

        int[] lion = new int[11];
        // 중간중간 최대차이로 우승할 수 있는 경우를 저장하기 위한 배열, 점수차 변수
        tmpAnswer = new int[11];
        tmpScoreDiff = 0;
        finalGame(apeach, lion, n, 1);

        // 합이 0인경우 -> 배열이 갱신이 되지 않은 경우 -> 라이언이 우승을 할 수 없다
        int hap = 0;
        for (int i = 0; i < 11; i++) {
            answer[i] = tmpAnswer[10 - i];
            hap += answer[i];
        }

        // 라이언이 우승을 할 수 없어, [-1] 반환
        if (hap == 0) {
            int[] result = {-1};
            return result;
        }

        return answer;
    }

    // 백트래킹을 고려했으나 점수를 계산하는 방식이 서로 연관되어 있어 제대로된 조건을 정할 수 없어, 완전탐색으로 확인한다.
    private void finalGame(int[] apeach, int[] lion, int arrows, int target) {
        // 화살을 다 쏘거나, 10까지 다 쏜 경우
        if (arrows == 0 || target == 11) {
            int scoreDiff = getScoreDiff(lion, apeach);
            // 남은 화살은 제일 작은 수 0에 몰아준다.
            lion[0] = arrows;
            // 점수차가 기존의 최고점수차보다 클 경우 라이언의 점수별 화살 개수를 갱신한다
            if (tmpScoreDiff < scoreDiff) {
                for (int i = 0; i < 11; i++) {
                    tmpAnswer[i] = lion[i];
                }
                // 최고점수차를 갱신
                tmpScoreDiff = scoreDiff;
            // 기존의 최고점수차와 같을 경우 tmpScoreDiff이 0이 아닌 경우여야 한다. 0인 경우는 어피치와 라이언이 동점이란 소리
            } else if (tmpScoreDiff == scoreDiff && tmpScoreDiff != 0) {
                for (int i = 0; i < 11; i++) {
                    // 라이언이 큰 경우가 나오는 순간 라이언의 점수별 화살 개수를 갱신한다. 작은 수부터 보기 때문에, 이 경우 가장 낮은 점수를 더 많이 맞힌 경우이다
                    if (tmpAnswer[i] < lion[i]) {
                        for (int j = 0; j < 11; j++) {
                            tmpAnswer[i] = lion[i];
                        }
                        break;
                    // 기존의 화살개수가 더 많을 경우 가장 낮은 점수를 더 많이 맞힌 경우가 아니게된다
                    } else if (tmpAnswer[i] > lion[i]) {
                        break;
                    }
                    // 같을 경우는 다음 점수를 확인한다
                }
            }

            return;
        }

        // 남은 화살의 개수가 해당 점수에 어피치를 상대하여 점수를 얻기위한 화살의 개수 이상일 때 쏠 수 있다.
        if (apeach[target] < arrows) {
            // lion[target]을 쏜 상태 apeach[target] + 1
            lion[target] = apeach[target] + 1;
            finalGame(apeach, lion, arrows - lion[target], target + 1);
            // 쏘고나서 다시 0으로 바꿔주고
            lion[target] = 0;
        }
        // 쏘지 않은 경우를 탐색한다
        finalGame(apeach, lion, arrows, target + 1);
    }

    private int getScoreDiff(int[] lion, int[] apeach) {
        int score = 0;

        for (int i = 1; i < 11; i++) {
            if (lion[i] > apeach[i]) {
                score += i;
                continue;
            }
            if (apeach[i] > 0) {
                score -= i;
            }
        }

        return score;
    }
}
