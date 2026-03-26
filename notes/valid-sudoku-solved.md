# Valid Sudoku — Medium
Problem Link: https://leetcode.com/problems/valid-sudoku/
Solved Date: 2026-03-24
Pattern Tag: hashset / matrix / constraint-validation

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like a Sudoku referee checking each row, column, and 3×3 box for duplicate numbers in a single pass — instead of checking three times, stamp each number with its location context.

## Core Insight
Use separate sets for rows, columns, and boxes. Box index = `(row/3)*3 + (col/3)`. Single pass through the grid. If any digit already exists in its row, col, or box set → invalid.

## Approach
Three arrays of sets: `rows[9]`, `cols[9]`, `boxes[9]`. For each cell with a digit: check/add to `rows[r]`, `cols[c]`, `boxes[(r/3)*3+(c/3)]`. Return false on duplicate.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Box index = (r/3)*3 + (c/3)          │ Maps each of the 9 3×3 boxes to a    │
│                                      │ unique index 0-8                      │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Three independent sets per position  │ Row 1 having two 5s is independent of │
│                                      │ column 1 having two 5s               │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Skip '.' cells                       │ Empty cells don't cause violations    │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
rows[9], cols[9], boxes[9] = arrays of HashSets

for r in 0..8:
    for c in 0..8:
        if board[r][c] == '.': continue
        digit = board[r][c]
        boxIdx = (r/3)*3 + (c/3)
        if digit in rows[r] OR cols[c] OR boxes[boxIdx]:
            return false
        rows[r].add(digit)
        cols[c].add(digit)
        boxes[boxIdx].add(digit)
return true
```

## Complexity
- Time: O(81) = O(1) — fixed 9×9 grid
- Space: O(81) = O(1) — fixed number of sets

## Watch Out For
- Box index formula: `(r/3)*3 + (c/3)` — integer division gives 0,1,2 for each group of 3
- Check ALL three sets before adding (not just one)
- Use char as the key in sets, not string (faster)

## Dry Run
```
board[0][0] = '5', r=0, c=0, boxIdx=0
rows[0]={5}, cols[0]={5}, boxes[0]={5}

board[0][3] = '5' → rows[0] already has 5 → return false ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public boolean isValidSudoku(char[][] board) {
        Set<Character>[] rows = new HashSet[9];
        Set<Character>[] cols = new HashSet[9];
        Set<Character>[] boxes = new HashSet[9];
        for (int i = 0; i < 9; i++) {
            rows[i] = new HashSet<>();
            cols[i] = new HashSet<>();
            boxes[i] = new HashSet<>();
        }

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char d = board[r][c];
                if (d == '.') continue;

                int boxIdx = (r / 3) * 3 + (c / 3);
                if (!rows[r].add(d) || !cols[c].add(d) || !boxes[boxIdx].add(d))
                    return false; // add() returns false if already present
            }
        }
        return true;
    }
}
```
