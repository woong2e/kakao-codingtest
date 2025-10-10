package _2023;

import java.util.*;

/*
모든 날짜를 day로 계산한다
split은 정규표현식을 사용해야하는데 .은 메타문지여서 이스케이프 해야됨
fuck 이거땜에 해맴
 */
public class _01 {
    private Map<String, Integer> termsMap;
    private int todayDays;

    public int[] solution(String today, String[] terms, String[] privacies) {
        List<Integer> tmpAnswer = new ArrayList<>();
        termsMap = new HashMap<>();
        todayDays = toDays(today);

        for (String term : terms) {
            String type = term.split(" ")[0];
            int duration = Integer.parseInt(term.split(" ")[1]) * 28;
            termsMap.put(type, duration);
        }

        int index = 1;
        for (String privacy : privacies) {
            String date = privacy.split(" ")[0];
            String type = privacy.split(" ")[1];

            int expirationDay = getExpirationDay(date, type);
            if (expirationDay < todayDays) {
                tmpAnswer.add(index);
            }
            index++;
        }

        int[] answer = new int[tmpAnswer.size()];
        for (int i = 0; i < tmpAnswer.size(); i++) {
            answer[i] = tmpAnswer.get(i);
        }

        return answer;
    }

    private int getExpirationDay(String date, String type) {
        int duration = termsMap.get(type);
        int expirationDay = toDays(date) + duration - 1;

        return expirationDay;
    }

    private int toDays(String date) {
        int[] array = Arrays.stream(date.split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray();

        int days = array[0] * 12 * 28 + array[1] * 28 + array[2];

        return days;
    }
}
