package _2022;

import java.util.*;

/*
적절한 자료구조를 활용해 문자열을 처리하는 문제인 듯
Map의 사용, 중복 신고가 안된다는 점을 생각하면 Set자료구조를 활용하는 방식도 가능할듯
단순한 구현문제
 */
public class _01 {
    public int[] solution(String[] id_list, String[] report, int k) {
        int[] answer = new int[id_list.length];
        // id에 대한 인덱스를 사용하기 위해 idMap을 만든다
        Map<String, Integer> idMap = new HashMap<>();
        for (int i = 0; i < id_list.length; i++) {
            idMap.put(id_list[i], i);
        }

        // 신고를 당한 ID기준으로 Map을 만든다
        // key -> 신고당한 ID, value의 key -> 신고한 사람
        Map<Integer, Map<Integer, Integer>> reported = new HashMap<>();
        for (int i = 0; i < id_list.length; i++) {
            reported.put(i, new HashMap<>());
            for (int j = 0; j < id_list.length; j++) {
                reported.get(i).put(j, 0);
            }
        }

        // 신고 당한 ID에 대해 신고한 ID를 1로 replace하여 중복 신고를 카운트 하지 않도록 한다
        for (String rep : report) {
            String subject = rep.split(" ")[0];
            String object = rep.split(" ")[1];
            reported.get(idMap.get(object)).replace(idMap.get(subject), 1);
        }

        for (int i = 0; i < id_list.length; i++) {
            // ID별로 신고당한 횟수를 카운트
            int tmpCount = 0;
            for (int count : reported.get(i).values()) {
                tmpCount += count;
            }

            // k 번 이상 신고를 당한 ID에 대해 신고를 한 ID의 answer값을 증가시킨다
            if (tmpCount >= k) {
                for (Map.Entry<Integer, Integer> entry : reported.get(i).entrySet()) {
                    if (entry.getValue() == 1) {
                        answer[entry.getKey()]++;
                    }
                }
            }
        }

        return answer;
    }
}
