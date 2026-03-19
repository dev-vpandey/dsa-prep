# Pacific Atlantic Water Flow — Medium
Link: https://leetcode.com/problems/pacific-atlantic-water-flow/description/
Solved Date: 2026-03-14
Review Date: 2026-03-21

## Real World Analogy
Imagine two seas on opposite sides of a mountain range. You want to find all mountain peaks where rainfall drains into BOTH seas. Instead of standing at every peak and tracing water downhill (slow), you flood both seas uphill simultaneously and find where both floods meet.

## Core Insight
Reverse the flow direction — BFS uphill from both ocean borders simultaneously. Any cell touched by both BFS runs can drain to both oceans.

## Approach
Seed Pacific BFS with all top-row + left-column cells. Seed Atlantic BFS with all bottom-row + right-column cells. Run both BFS runs independently going uphill (height >= current). Answer is the intersection of both visited matrices.

## Mental Model
```
┌──────────────────────────────┬───────────────────────────────────────────────────────┐
│ Decision                     │ Why                                                   │
├──────────────────────────────┼───────────────────────────────────────────────────────┤
│ Reverse flow (uphill BFS)    │ Forward = O(m²n²), Reverse = O(mn). Same answer,     │
│ instead of forward (downhill)│ 10000x faster on large grids                         │
├──────────────────────────────┼───────────────────────────────────────────────────────┤
│ Two separate BFS runs        │ Conditions are independent — solve each separately   │
│ not one combined run         │ then intersect. Combining adds complexity for no gain │
├──────────────────────────────┼───────────────────────────────────────────────────────┤
│ Mark visited BEFORE enqueue  │ Prevents same cell entering queue multiple times,    │
│ not after poll               │ especially for corner/border seed cells               │
├──────────────────────────────┼───────────────────────────────────────────────────────┤
│ Seed entire border at once   │ Multi-source BFS — all borders expand simultaneously │
│ before starting BFS          │ so no cell is processed from wrong starting point     │
├──────────────────────────────┼───────────────────────────────────────────────────────┤
│ heights[nr][nc] >= heights   │ Reverse flow means we climb — equal height is valid  │
│ [r][c] as move condition     │ (water can flow between equal cells)                 │
└──────────────────────────────┴───────────────────────────────────────────────────────┘
```

## Why NOT Backtracking / Forward DFS

```
FORWARD DFS (Backtracking) — O(m²×n²):

for each cell (r,c):                    ← O(m×n) cells
    canReachPacific(r, c)?              ← O(m×n) DFS from scratch
    canReachAtlantic(r, c)?             ← O(m×n) DFS from scratch

Problems:
1. Fresh visited[][] allocated per cell — no shared memory across cells
2. Same subpaths walked repeatedly — cell (2,2) explored once per upstream cell
3. Backtrack (visited[r][c] = false) undoes all progress — forgets everything

REVERSE BFS — O(m×n):

Pacific BFS touches each cell at most once  → O(m×n)
Atlantic BFS touches each cell at most once → O(m×n)

Key difference:
  Backtracking asks  "can I get there?"  and FORGETS the answer
  BFS asks           "what can I reach?" and REMEMBERS everything
```

## Pseudocode
```
seed Pacific queue: top row + left column → mark pacific[r][c] = true
seed Atlantic queue: bottom row + right column → mark atlantic[r][c] = true

bfs(pacQueue, pacific):
  while queue not empty:
    poll (r, c)
    for each 4 directions:
      if out of bounds → skip
      if visited → skip
      if heights[nr][nc] < heights[r][c] → skip (can't go downhill in reverse)
      mark visited, enqueue

bfs(atlQueue, atlantic): same

result = all (r,c) where pacific[r][c] && atlantic[r][c]
```

## Complexity
- Time: O(m×n) — each cell visited at most once per BFS, two BFS runs total
- Space: O(m×n) — two visited matrices + queue size at most m×n

## Why this is optimal
Every cell must be examined at least once to determine reachability — O(m×n) is a lower bound and we hit it exactly.

## Watch Out For
- Mark visited AND enqueue together — never one without the other
- Corner cells (e.g. `[0][0]`) get seeded twice — harmless since visited check skips re-processing
- Condition is `>=` not `>` — equal height means water CAN flow between cells
- Do NOT combine both oceans in one BFS — intersection logic breaks

## Dry Run
**Grid:**
```
1  2  2  3  5
3  2  3  4  4
2  4  5  3  1
6  7  1  4  5
5  1  1  2  4
```

**Pacific reachable (uphill from top row + left col):**
```
T  T  T  T  T
T  T  T  T  T
T  T  T  .  .
T  T  .  .  .
T  .  .  .  .
```
Key: (2,3)=3 is NOT reachable — both neighbors (1,3)=4 and (2,2)=5 are higher, reverse BFS cannot climb to them from (2,3).

**Atlantic reachable (uphill from bottom row + right col):**
```
.  .  .  .  T
.  .  .  T  T
.  .  T  T  T
T  T  T  T  T
T  T  T  T  T
```
Key: (2,3)=3 IS reachable — from (2,4)=1, neighbor (2,3)=3 ≥ 1 ✓

**Intersection:**
```
.  .  .  .  T   → (0,4)
.  .  .  T  T   → (1,3), (1,4)
.  .  T  .  .   → (2,2)
T  T  .  .  .   → (3,0), (3,1)
T  .  .  .  .   → (4,0)
```
**Answer: `[[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]`**

## Pattern Tag
`multi-source-bfs / reverse-flow / matrix / intersection / graph`

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int rows = heights.length, cols = heights[0].length;
        boolean[][] pacific = new boolean[rows][cols];
        boolean[][] atlantic = new boolean[rows][cols];

        Queue<int[]> pacQueue = new LinkedList<>();
        Queue<int[]> atlQueue = new LinkedList<>();

        // Seed borders — left col + right col
        for (var r = 0; r < rows; r++) {
            pacific[r][0] = true;
            pacQueue.add(new int[]{r, 0});
            atlantic[r][cols - 1] = true;
            atlQueue.add(new int[]{r, cols - 1});
        }

        // Seed borders — top row + bottom row
        for (var c = 0; c < cols; c++) {
            pacific[0][c] = true;
            pacQueue.add(new int[]{0, c});
            atlantic[rows - 1][c] = true;
            atlQueue.add(new int[]{rows - 1, c});
        }

        bfs(pacQueue, heights, pacific, rows, cols);
        bfs(atlQueue, heights, atlantic, rows, cols);

        List<List<Integer>> res = new ArrayList<>();
        for (var r = 0; r < rows; r++) {
            for (var c = 0; c < cols; c++) {
                if (pacific[r][c] && atlantic[r][c])
                    res.add(List.of(r, c));
            }
        }
        return res;
    }

    private void bfs(Queue<int[]> queue, int[][] heights, boolean[][] visited, int rows, int cols) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            var curr = queue.poll();
            int r = curr[0], c = curr[1];

            for (int[] dir : directions) {
                int nr = r + dir[0], nc = c + dir[1];

                if (nr < 0 || nc < 0 || nr >= rows || nc >= cols || visited[nr][nc])
                    continue;

                // ← customize move condition here for similar problems
                if (heights[nr][nc] >= heights[r][c]) {
                    visited[nr][nc] = true;
                    queue.add(new int[]{nr, nc});
                }
            }
        }
    }
}
```
