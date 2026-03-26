# Set Matrix Zeroes — Medium
Problem Link: https://leetcode.com/problems/set-matrix-zeroes/description/
Solved Date: 2026-03-03
Pattern Tag: matrix / two-pass / row-col-marking / hashset
Review Date: 2026-03-10

## SRS Tracking
- Stage: 6
- Review Date: 2026-06-21
- Last Rating: —
- Review Count: 0
- Graduated: No

---

# Real World Analogy
—

## Core Insight
Record which rows and columns contain a zero first, then zero them out — never modify in-place during discovery or you'll create false zeros.

## Approach
Two-pass approach: first scan the matrix to collect all row and column indices containing zeros. Second scan sets any cell to zero if its row or column was flagged. Uses HashSets to deduplicate and allow O(1) lookup.

## Mental Model
```
┌──────────────────────────┬───────────────────────┬──────────────────────────────────┐
│ Decision                 │ Choice                │ Why                              │
├──────────────────────────┼───────────────────────┼──────────────────────────────────┤
│ Why two passes?          │ Separate discover     │ In-place zeroing during pass 1   │
│                          │ from modify           │ corrupts zero discovery          │
├──────────────────────────┼───────────────────────┼──────────────────────────────────┤
│ What do we store?        │ Row/col indices,      │ All cells in that row/col need   │
│                          │ not cell coords       │ zeroing — index is enough        │
├──────────────────────────┼───────────────────────┼──────────────────────────────────┤
│ HashSet vs boolean[]?    │ Either works          │ boolean[rows]/[cols] is O(m+n)   │
│                          │                       │ same space, slightly faster      │
├──────────────────────────┼───────────────────────┼──────────────────────────────────┤
│ Space trade-off?         │ O(m+n) sets here vs   │ O(1) uses first row/col as       │
│                          │ O(1) optimal          │ markers (trickier to implement)  │
└──────────────────────────┴───────────────────────┴──────────────────────────────────┘
```

## Pseudocode
```
create rowSet, colSet

for each cell (r, c):
  if matrix[r][c] == 0:
    add r to rowSet
    add c to colSet

for each cell (r, c):
  if r in rowSet OR c in colSet:
    matrix[r][c] = 0
```

## Complexity
- Time: O(m × n) — two full passes over the matrix, each visiting every cell once
- Space: O(m + n) — rowSet holds at most m entries, colSet at most n entries

## Why this is optimal (for this approach)
You must visit every cell at least once to find all zeros, so O(m×n) time is unavoidable; O(1) space is achievable by using the first row and column as markers but adds implementation complexity.

## Watch Out For
- Don't zero cells during the first pass — you'll propagate false zeros and corrupt discovery
- The O(1) space solution uses the first row/column as markers but needs special handling to avoid overwriting those markers before you read them
- `matrix[0].length` for column count, not `matrix.length`

## Dry Run
Input:
```
[1, 1, 1]
[1, 0, 1]
[1, 1, 1]
```

Pass 1 — discovery:
- Cell (1,1) = 0 → rowSet = {1}, colSet = {1}

Pass 2 — apply zeroes:
| Cell | Row in set? | Col in set? | Result |
|------|-------------|-------------|--------|
| (0,0)| No          | No          | 1      |
| (0,1)| No          | Yes (col 1) | 0      |
| (0,2)| No          | No          | 1      |
| (1,0)| Yes (row 1) | No          | 0      |
| (1,1)| Yes         | Yes         | 0      |
| (1,2)| Yes (row 1) | No          | 0      |
| (2,0)| No          | No          | 1      |
| (2,1)| No          | Yes (col 1) | 0      |
| (2,2)| No          | No          | 1      |

Output:
```
[1, 0, 1]
[0, 0, 0]
[1, 0, 1]
```

## Boilerplate Template
```java
public void setZeroes(int[][] matrix) {
    Set<Integer> rowSet = new HashSet<>();
    Set<Integer> colSet = new HashSet<>();
    int rows = matrix.length, cols = matrix[0].length;

    // Pass 1: collect zero positions
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (matrix[r][c] == 0) {
                rowSet.add(r);
                colSet.add(c);
            }
        }
    }

    // Pass 2: apply zeroes
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (rowSet.contains(r) || colSet.contains(c)) {
                matrix[r][c] = 0;
            }
        }
    }
}
```
