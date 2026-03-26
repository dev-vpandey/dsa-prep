# Longest Increasing Path in a Matrix — Hard
Problem Link: https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
Solved Date: 2026-03-25
Pattern Tag: graph / dfs / memoization / matrix / topological-sort

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like hiking — you can only walk uphill (strictly increasing values). From each cell, find the longest possible uphill trail. Cache the answer for each cell so you don't recompute the same path twice.

## Core Insight
DFS from every cell, but memoize results. `memo[r][c]` = length of longest increasing path starting at (r,c). A neighbor is valid only if its value is strictly greater. No "visited" array needed — strict increase prevents cycles.

## Approach
For each cell, if `memo[r][c] > 0` return it. Otherwise, try all 4 neighbors where `grid[nr][nc] > grid[r][c]`. Take `1 + max(dfs of valid neighbors)`. Memoize and return. Track global max.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ No visited array needed              │ Strict increase guarantees no cycles  │
│                                      │ — can't revisit a cell via increasing │
│                                      │ neighbors                             │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ memo[r][c] = longest path FROM here  │ Memoize so each cell computed once;   │
│                                      │ overlapping subproblems avoided       │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ DFS from EVERY cell                  │ The longest path may start from any   │
│                                      │ cell — no way to know in advance      │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
memo = int[m][n] initialized to 0
ans = 0

for each cell (r, c):
    ans = max(ans, dfs(r, c))

dfs(r, c):
    if memo[r][c] > 0: return memo[r][c]
    best = 1
    for each direction (dr, dc):
        nr, nc = r+dr, c+dc
        if in bounds AND grid[nr][nc] > grid[r][c]:
            best = max(best, 1 + dfs(nr, nc))
    memo[r][c] = best
    return best
```

## Complexity
- Time: O(m*n) — each cell computed exactly once due to memoization
- Space: O(m*n) — memo array + recursion stack depth up to m*n

## Watch Out For
- `memo[r][c] > 0` is the cache check (0 = uncomputed)
- NO visited array — strict increase prevents infinite recursion
- Return 1 as base (the cell itself counts as a path of length 1)
- Try ALL cells as starting points for the global max

## Dry Run
```
matrix = [[9,9,4],[6,6,8],[2,1,1]]

dfs(2,1)=1 (no increasing neighbors)
dfs(2,0)=2: neighbor (2,1)? 1<2✗. neighbor (1,0)? 6>2✓ → 1+dfs(1,0)
dfs(1,0)=3: neighbor (0,0)? 9>6✓ → 1+dfs(0,0)
dfs(0,0)=1 (no increasing neighbors > 9)
→ dfs(1,0) = 1+1 = 2
→ dfs(2,0) = 1+2 = 3

ans tracks max across all cells = 4 (path: 1→2→6→9)
```

## Boilerplate Template
```java
class Solution {
    private int[][] memo;
    private int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    private int m, n;

    public int longestIncreasingPath(int[][] matrix) {
        m = matrix.length;
        n = matrix[0].length;
        memo = new int[m][n];
        int ans = 0;
        for (int r = 0; r < m; r++)
            for (int c = 0; c < n; c++)
                ans = Math.max(ans, dfs(matrix, r, c));
        return ans;
    }

    private int dfs(int[][] matrix, int r, int c) {
        if (memo[r][c] > 0) return memo[r][c];
        int best = 1;
        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1];
            if (nr >= 0 && nr < m && nc >= 0 && nc < n
                    && matrix[nr][nc] > matrix[r][c]) {
                best = Math.max(best, 1 + dfs(matrix, nr, nc));
            }
        }
        return memo[r][c] = best;
    }
}
```
