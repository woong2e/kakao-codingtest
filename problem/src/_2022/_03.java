package _2022;

import java.util.*;

/*
제한 사항 덕분에 입/출차를 구분하지 않아도 됨
따라서, 홀수개의 입/출차 기록이 있으면 마지막 출차를 안 한 것이으로 "23:59"를 기록
이외엔 빡시게 구현, 카카오 코테 문제를 풀면서 느끼는데 Map을 상당히 많이 쓰게 되는 듯
 */
public class _03 {
    public int[] solution(int[] fees, String[] records) {
        int[] answer = {};

        // 차량 번호를 저장할 리스트
        List<String> numbers = new ArrayList<>();

        // 입/출차 기록을 저장할 Map
        Map<String, List<Integer>> recordMap = new HashMap<>();

        // 차량 번호에 대해 입/출차 상관없이 리스트에 넣는다
        for (String record : records) {
            String[] recordArray = record.split(" ");
            recordMap.putIfAbsent(recordArray[1], new ArrayList<>());
            recordMap.get(recordArray[1]).add(toMinuteTime(recordArray[0]));
            if (!numbers.contains(recordArray[1])) {
                numbers.add(recordArray[1]);
            }
        }

        // 차량번호를 오름차순으로 정렬
        Collections.sort(numbers);

        answer = new int[numbers.size()];

        // 차량번호에 대해 입/출차 기록이 홀수개인 경우 마지막 출차를 하지 않은 것 -> "23:59"을 기록해준다
        int lastTime = toMinuteTime("23:59");
        for (Map.Entry<String, List<Integer>> entry : recordMap.entrySet()) {
            if (entry.getValue().size() % 2 == 1) {
                entry.getValue().add(lastTime);
            }
        }

        // 차량번호에 대해 요금을 계산한다
        for (int i = 0; i < numbers.size(); i++) {
            answer[i] = caculateFee(fees, recordMap.get(numbers.get(i)));
        }

        return answer;
    }

    // 모든 시간을 저장하는 방식을 "00:00"을 기준으로 몇 분이 지났는지로 저장
    private int toMinuteTime(String time) {
        int[] timeArray = Arrays.stream(time.split(":"))
                .mapToInt(Integer::parseInt)
                .toArray();

        int minute = 60 * timeArray[0] + timeArray[1];

        return minute;
    }

    // 요금 계산
    private int caculateFee(int[] fees, List<Integer> record) {
        // 기본 요금을 세팅한다
        int fee = fees[1];
        int totalTime = 0;

        // 총 시간을 계산했을 때
        for (int i = 0; i < record.size(); i += 2) {
            totalTime += record.get(i + 1) - record.get(i);
        }

        // 기본 시간 보다 작거나 같을 경우 기본 요금 세팅한 것을 반환한다
        if (totalTime <= fees[0]) {
            return fee;
        }

        // 기본 시간 초과일 경우 단위 시간에 대해 올림해서 단위 요금을 추가한다
        int additionalTime = totalTime - fees[0];
        int num1 = additionalTime / fees[2];
        if (additionalTime % fees[2] != 0) {
            num1 += 1;
        }
        fee += num1 * fees[3];

        return fee;
    }
}
