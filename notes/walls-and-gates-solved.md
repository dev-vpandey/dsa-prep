# Walls and Gates — Medium
Problem Link: [https://leetcode.com/problems/walls-and-gates/](https://leetcode.com/problems/walls-and-gates/)
Solved Date: 2026-03-04
Pattern Tag: multi-source-bfs / graph / matrix / shortest-distance
Review Date: 2026-03-21

## SRS Tracking
- Stage: 3
- Review Date: 2026-04-08
- Last Rating: Strong
- Review Count: 2
- Graduated: No

---

# Real World Analogy
—

## Core Insight
Start BFS simultaneously from ALL gates — the first wave to reach any room is guaranteed to be the shortest distance.

## Approach
Pre-load every gate (0) into the queue before starting BFS. Expand outward in 4 directions, updating each INF cell with current cell's value + 1. Skip walls (-1) and already-visited cells (non-INF).

## Mental Model
```
┌─────────────────────┬──────────────────────────────────────────────┐
│ Decision            │ Why                                          │
├─────────────────────┼──────────────────────────────────────────────┤
│ Add ALL gates first │ Ensures all sources expand simultaneously    │
│                     │ — no conflict between nearby gates           │
├─────────────────────┼──────────────────────────────────────────────┤
│ Skip if != INF      │ Acts as visited check — walls and already    │
│                     │ updated cells are both skipped               │
├─────────────────────┼──────────────────────────────────────────────┤
│ rooms[nr][nc] =     │ Distance carries forward from parent cell,   │
│ rooms[r][c] + 1     │ no separate level tracking needed            │
├─────────────────────┼──────────────────────────────────────────────┤
│ Add neighbor to     │ Neighbor becomes next "walker" — chain       │
│ queue after update  │ breaks if you forget this                    │
└─────────────────────┴──────────────────────────────────────────────┘
```

## Pseudocode
```
scan grid
  if cell == 0 → add to queue

while queue not empty
  poll curr (r, c)
  for each of 4 directions
    compute (nr, nc)
    if out of bounds → skip
    if wall (-1) → skip
    if not INF → skip
    rooms[nr][nc] = rooms[r][c] + 1
    add (nr, nc) to queue
```

## Complexity
- Time: O(m × n) — each cell is enqueued and processed at most once
- Space: O(m × n) — queue can hold all cells in worst case

## Watch Out For
- Don't call BFS inside the gate-scanning loop — add ALL gates first, then BFS once
- Use `curr[0], curr[1]` inside BFS loop, NOT the original gate coordinates
- Don't hardcode distance — derive it from `rooms[currRow][currCol] + 1`
- The skip condition `rooms[nr][nc] != INF` is your implicit visited check

## Dry Run
**Input:**
```
INF  -1   0  INF
INF INF INF  -1
INF  -1  INF  -1
 0   -1  INF INF
```

**Queue starts:** `[(0,2), (3,0)]`

| Poll     | Neighbor | New Value | Queue after             |
|----------|----------|-----------|-------------------------|
| (0,2)=0  | (0,3)    | 1         | [(3,0),(0,3),(1,2)]     |
| (3,0)=0  | (2,0)    | 1         | [(0,3),(1,2),(2,0)]     |
| (0,3)=1  | none     | —         | [(1,2),(2,0)]           |
| (1,2)=1  | (1,1),(2,2) | 2      | [(2,0),(1,1),(2,2)]     |
| (2,0)=1  | (1,0)    | 2         | [(1,1),(2,2),(1,0)]     |
| (1,1)=2  | (0,0)    | 3         | [(2,2),(1,0),(0,0)]     |
| (2,2)=2  | (3,2)    | 3         | [(1,0),(0,0),(3,2)]     |
| (1,0)=2  | none     | —         | [(0,0),(3,2)]           |
| (0,0)=3  | none     | —         | [(3,2)]                 |
| (3,2)=3  | (3,3)    | 4         | [(3,3)]                 |
| (3,3)=4  | none     | —         | []                      |

**Final:**
```
 3   -1   0   1
 2    2   1  -1
 1   -1   2  -1
 0   -1   3   4
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public void multiSourceBFS(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        int SOURCE = 0, BLOCKED = -1, UNVISITED = Integer.MAX_VALUE;

        Queue<int[]> queue = new LinkedList<>();

        // Step 1: Add all sources
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == SOURCE)
                    queue.add(new int[]{r, c});
            }
        }

        // Step 2: BFS outward
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1];

            for (int[] dir : directions) {
                int nr = r + dir[0], nc = c + dir[1];
                if (nr < 0 || nc < 0 || nr >= rows || nc >= cols)
                    continue;
                if (grid[nr][nc] != UNVISITED)
                    continue;
                grid[nr][nc] = grid[r][c] + 1;
                queue.add(new int[]{nr, nc});
            }
        }
    }
}
```
