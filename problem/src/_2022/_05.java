package _2022;
/*
처음에 방문한 노드들에 대해 하나의 묶음으로 하면 될거같다는 생각을 하고 어떻게 구현할 지 생각을 못했는데
visited 배열로 가능했다. 그리고 좌우 어느쪽으로 먼저 가는지에 따라 결과가 달라지는데 이 부분은 dfs탐색을 할 때마다 그 상황에서의 visited를 복사해서 따로
적용을 해야했다.
여러 풀이 방법을 찾아보니 모두 양인 경우 시간초과가 발생한다.(카카오에서도 이거 테스트 케이스에 없었음) 비트마스킹을 통해 풀이를 할 수 있는데 .... 다음번에
 */
public class _05 {
    private int[] globalInfo;
    private int[][] globalEdges;
    private int maxSheepCount;

    public int solution(int[] info, int[][] edges) {
        globalInfo = info;
        globalEdges = edges;
        maxSheepCount = 0;
        boolean[] initVisited = new boolean[info.length];
        dfs(0, initVisited, 0, 0);

        return maxSheepCount;
    }

    private void dfs(int idx, boolean[] visited, int sheepCount, int wolfCount) {
        visited[idx] = true;

        if (globalInfo[idx] == 0) {
            sheepCount++;
            if (sheepCount > maxSheepCount) {
                maxSheepCount = sheepCount;
            }
        } else {
            wolfCount++;
        }

        if (wolfCount >= sheepCount) {
            return;
        }

        for (int[] edge : globalEdges) {
            // 시작 정점을 방문한 노드 전체 아무거나
            if (visited[edge[0]] && !visited[edge[1]]) {
                // 새롭게 탐색할 노드에 대해 이전까지 방문한 기록을 새로 넘겨줘야한다 그래야 좌우 순서 모두를 고려할 수 있음
                boolean[] cloned = new boolean[visited.length];
                for (int i = 0; i < visited.length; i++) {
                    cloned[i] = visited[i];
                }
                dfs(edge[1], cloned, sheepCount, wolfCount);
            }
        }
    }
}
