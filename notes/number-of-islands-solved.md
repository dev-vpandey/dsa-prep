# Number of Islands — Medium
Problem Link: https://leetcode.com/problems/number-of-islands/
Solved Date: 2026-03-24
Pattern Tag: graph / dfs / matrix / flood-fill / connected-components

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like counting continents on a map by satellite — each time you spot land, count it and flood-fill all connected land to mark it as explored, then count the next unexplored landmass.

## Core Insight
For every unvisited '1', increment count and DFS to mark all connected '1's as '0' (visited). Number of DFS calls = number of islands.

## Approach
Double loop over grid. On finding '1': increment count, call DFS. DFS marks current cell '0' and recurses to all 4 valid '1' neighbors.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Mark '0' before recursing            │ Prevents infinite loops and double    │
│ (not after)                          │ counting — cell is "consumed"         │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ DFS over BFS here                    │ Both work; DFS is simpler code for    │
│                                      │ flood-fill — no queue needed          │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Boundary check inside DFS            │ Cleaner to check at DFS entry vs      │
│ (not before call)                    │ 4 separate condition checks per cell  │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
count = 0
for r, c in all cells:
    if grid[r][c] == '1':
        count++
        dfs(r, c)
return count

dfs(r, c):
    if out-of-bounds OR grid[r][c] != '1': return
    grid[r][c] = '0'    // mark visited
    dfs(r+1,c); dfs(r-1,c); dfs(r,c+1); dfs(r,c-1)
```

## Complexity
- Time: O(m*n) — each cell visited at most once
- Space: O(m*n) — recursion stack worst case (all land)

## Watch Out For
- Grid uses `char` ('1'/'0') not `int` (1/0) — compare with `== '1'`
- Mark BEFORE recursing (not after) to prevent revisits
- Can solve with BFS (multi-source) or Union-Find too

## Dry Run
```
Grid: [['1','1','0'],
       ['0','1','0'],
       ['0','0','1']]

(0,0)='1' → count=1, DFS marks (0,0),(0,1),(1,1) as '0'
(2,2)='1' → count=2, DFS marks (2,2) as '0'
Result: 2 ✓
```

## Boilerplate Template
```java
class Solution {
    public int numIslands(char[][] grid) {
        int count = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == '1') {
                    count++;
                    dfs(grid, r, c);
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid, int r, int c) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] != '1') return;
        grid[r][c] = '0'; // mark as visited
        dfs(grid, r + 1, c); dfs(grid, r - 1, c);
        dfs(grid, r, c + 1); dfs(grid, r, c - 1);
    }
}
```
