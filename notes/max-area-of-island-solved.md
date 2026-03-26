# Max Area of Island — Medium
Problem Link: https://leetcode.com/problems/max-area-of-island/
Solved Date: 2026-03-24
Pattern Tag: graph / dfs / matrix / flood-fill / area-counting

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like a land surveyor counting the area of each island — walk every connected piece of land and tally it up. Record the largest territory found.

## Core Insight
DFS from each unvisited `1`, counting cells visited in this DFS call and returning the total. Track global max across all islands.

## Approach
Same as Number of Islands but DFS returns the island's area. At each cell: mark as `0` (visited), return `1 + sum of DFS on 4 neighbors` (each returns 0 if out-of-bounds or water). Track max.

## Mental Model
```
┌────────────────────────────────────┬─────────────────────────────────────────┐
│ Decision                           │ Why                                     │
├────────────────────────────────────┼─────────────────────────────────────────┤
│ Return 1 + dfs(4 neighbors)        │ Current cell = 1; add area from all    │
│                                    │ connected neighbors                     │
├────────────────────────────────────┼─────────────────────────────────────────┤
│ Mark as 0 BEFORE recursing         │ Prevents double-counting and infinite   │
│                                    │ loops (same as NumIslands)              │
├────────────────────────────────────┼─────────────────────────────────────────┤
│ Return 0 for invalid cells         │ Water or out-of-bounds contributes 0    │
│                                    │ to area — clean base case              │
└────────────────────────────────────┴─────────────────────────────────────────┘
```

## Pseudocode
```
maxArea = 0
for r, c in all cells:
    if grid[r][c] == 1:
        maxArea = max(maxArea, dfs(r, c))
return maxArea

dfs(r, c):
    if out-of-bounds OR grid[r][c] != 1: return 0
    grid[r][c] = 0  // mark visited
    return 1 + dfs(r+1,c) + dfs(r-1,c) + dfs(r,c+1) + dfs(r,c-1)
```

## Complexity
- Time: O(m*n) — each cell visited at most once
- Space: O(m*n) — recursion stack

## Watch Out For
- Grid uses `int` (1/0), not `char` ('1'/'0') — check problem constraints
- Mark as `0` before recursion, not after
- Return `0` for out-of-bounds/water base case (not -1 or some other sentinel)

## Dry Run
```
[[0,0,1,0,0],
 [0,1,1,1,0],
 [0,0,1,0,0]]

DFS from (0,2): explores (0,2)→(1,2)→(1,1),(1,3),(2,2)
Area = 5. maxArea = 5 ✓
```

## Boilerplate Template
```java
class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        int rowLength = grid.length, colLength = grid[0].length, maxArea = 0;

        for(var row = 0; row < rowLength; row++) {
            for(var col = 0; col < colLength; col++)
                if(grid[row][col] == 1) { // if we found a land cell, form and island by finding connected neighbors
                    var area = dfs(grid, row, col);
                    maxArea = Math.max(maxArea, area);
                }
        }
        return maxArea;
    }

    private int dfs(int[][] grid, int row, int col) {
        if(grid[row][col] != 1) // if its not a land cell return
            return 0;
        
        grid[row][col] = -1; // mark the land cell to be visited, so that we don't recount it
        
        int areaSoFar = 1; // current cell area wih only one cell it will aways be 1
       
       // calculate the area by adding all land cells from all directions 
       int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for(int[] dir : directions) {
            int nr = row + dir[0], nc = col + dir[1];
            
            if(nr < 0 || nc < 0 || nr >= grid.length || nc >= grid[0].length) // Boudary check
                continue;
            
            areaSoFar += dfs(grid, nr, nc);  
        }
        return areaSoFar;
    }
}
```
