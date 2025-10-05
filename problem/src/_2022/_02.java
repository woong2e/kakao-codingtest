package _2022;

import java.lang.StringBuilder;
import java.lang.Math;
import java.util.*;

/*
long 타입이 필요할 수도 있다는 생각을 가져야한다. 생각없이 int타입으로 했다가 런타임에러로 고생했다
문자열 split시 빈 문자열이 들어올 수도 있다는 생각을 가져야한다 - stream에 filter를 걸어준다
소수 판별을 하기 위해서는 제곱근까지 확인하면 된다
k진수를 구하기 위해서 10 이상의 진수를 구할 필요가 있다면 배열을 만들어두자
 */
public class _02 {
    public int solution(int n, int k) {
        int answer = 0;

        // k진수로 변환된 숫자
        String transformedNumber = String.valueOf(transform(n, k));
        // 타입을 long으로 해주는 것이 핵심
        long[] array = Arrays.stream(transformedNumber.split("0"))
                .filter(s -> !s.isEmpty())  // "0"을 기준으로 split하기 때문에 빈 문자열이 들어갈 수도 있다
                .mapToLong(Long::parseLong)
                .toArray();

        // 계산 반복을 줄이기위해 소수인지 아닌지 계산해둔 것은 리스트에 넣어둔다
        List<Long> primary = new ArrayList<>();
        List<Long> notPrimary = new ArrayList<>();
        primary.add(2L);
        notPrimary.add(1L);

        for (long num : array) {
            if (primary.contains(num)) {
                answer += 1;
                continue;
            } else if (notPrimary.contains(num)) {
                continue;
            }

            // 소수인지 판별하기 위해 Math.sqrt(num) + 1 범위까지만 체크하면 된다
            boolean isPrim = true;
            for (long i = 2; i < Math.sqrt(num) + 1; i++) {
                if (num % i == 0) {
                    isPrim = false;
                    notPrimary.add(num);
                    break;
                }
            }

            if (isPrim) {
                answer += 1;
                primary.add(num);
            }
        }

        return answer;
    }

    // k진수로 변환하는 메서드
    private StringBuilder transform(int n, int k) {
        StringBuilder sb = new StringBuilder();
        // 10진수 일 때 굳이 변환할 필요가 없다
        if (k == 10) {
            sb.append(n);
            return sb;
        }

        // 나머지를 StringBuilder에 추가해주고
        while (n >= k) {
            sb.append(n % k);
            n = (int)(n / k);
        }
        sb.append(n);

        // 뒤집어서 k진수를 완성한다
        return sb.reverse();
    }
}
