# Rotting Oranges — Medium
Problem Link: https://leetcode.com/problems/rotting-oranges/
Solved Date: 2026-03-24
Pattern Tag: graph / multi-source-bfs / matrix / time-simulation

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like a disease spreading through a population simultaneously from multiple infected patients — each minute, every infected person infects all adjacent healthy ones. Count minutes until all are infected (or detect if some are unreachable).

## Core Insight
Multi-source BFS from all initially rotten oranges simultaneously. Each BFS level = 1 minute. After BFS, if any fresh oranges remain, return -1. Otherwise return the number of levels processed.

## Approach
Enqueue all initially rotten oranges. Count fresh oranges. BFS level by level: at each level, spread rot to 4-directional fresh neighbors, decrement fresh count. Return minutes when fresh count hits 0 (or -1 if not).

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Multi-source BFS (not single-source) │ All rotten oranges spread at the same │
│                                      │ time — simulate simultaneity          │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Count fresh oranges separately       │ After BFS, check if any are left over │
│                                      │ (isolated from all rotten ones)       │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ minutes++ only when level non-empty  │ Don't count time for the last empty   │
│                                      │ level — increment per BFS wave        │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
queue = [], fresh = 0
for each cell:
    if cell == 2: queue.add(cell)
    if cell == 1: fresh++

minutes = 0
while queue not empty AND fresh > 0:
    for each in current level:
        for each valid fresh neighbor:
            mark as 2, fresh--, queue.add
    minutes++

return fresh == 0 ? minutes : -1
```

## Complexity
- Time: O(m*n) — each cell enqueued/processed at most once
- Space: O(m*n) — queue

## Watch Out For
- Start with ALL rotten oranges in queue (multi-source, not single)
- Increment `minutes` per BFS level, not per cell
- If no fresh oranges initially, return 0 (already done)
- Isolated fresh oranges (not reachable from any rotten) → return -1

## Dry Run
```
[[2,1,1],[1,1,0],[0,1,1]]
Initial queue: [(0,0)], fresh=6

Minute 1: rot (0,1),(1,0) → fresh=4, queue=[(0,1),(1,0)]
Minute 2: rot (0,2),(1,1) → fresh=2, queue=[(0,2),(1,1)]
Minute 3: rot (2,1) → fresh=1, queue=[(2,1)]
Minute 4: rot (2,2) → fresh=0, queue=[(2,2)]
Return 4 ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int orangesRotting(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int fresh = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 2) queue.offer(new int[]{r, c});
                if (grid[r][c] == 1) fresh++;
            }
        }

        int minutes = 0;
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        while (!queue.isEmpty() && fresh > 0) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                for (int[] d : dirs) {
                    int nr = cell[0] + d[0], nc = cell[1] + d[1];
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == 1) {
                        grid[nr][nc] = 2;
                        fresh--;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
            minutes++;
        }
        return fresh == 0 ? minutes : -1;
    }
}
```
