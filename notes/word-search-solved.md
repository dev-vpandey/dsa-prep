# Word Search — Medium
Link: [LeetCode](https://leetcode.com/problems/word-search/)
Solved Date: 2026-03-01
Review Date: 2026-03-21

## SRS Tracking
- Stage: 1
- Review Date: 2026-03-21
- Last Rating: Okay
- Review Count: 1
- Graduated: No

## Core Insight
Try every cell as a starting point and DFS with backtracking — mark cells visited to prevent reuse within a path, restore them after to allow reuse across different paths.

## Approach
For each cell in the board, launch a DFS that matches the word character by character. Mark the current cell with `#` before exploring neighbors so it can't be reused in the same path. After exploring all 4 directions, restore the cell (backtrack) regardless of success or failure.

## Mental Model
```
┌─────────────────────────────┬────────────────────────────────────────────────────────────┐
│ Decision                    │ Why                                                        │
├─────────────────────────────┼────────────────────────────────────────────────────────────┤
│ index == word.length()      │ Must come BEFORE bounds check — completing the word        │
│ check comes first           │ doesn't require a valid next cell; base case must fire     │
│                             │ before OOB access would crash                              │
├─────────────────────────────┼────────────────────────────────────────────────────────────┤
│ Restore board even on       │ Board is shared state across recursive calls; always       │
│ success (before return true)│ leave it clean so the caller's state is intact             │
├─────────────────────────────┼────────────────────────────────────────────────────────────┤
│ Use '#' for visited         │ Avoids a separate boolean[][] visited array; '#' can't     │
│                             │ match any letter so mismatch check handles it implicitly   │
├─────────────────────────────┼────────────────────────────────────────────────────────────┤
│ Bounds check before         │ Can't access board[row][col] if OOB —                     │
│ char mismatch check         │ ArrayIndexOutOfBoundsException otherwise                   │
├─────────────────────────────┼────────────────────────────────────────────────────────────┤
│ Return true on first        │ We only need ONE valid path — short-circuit as soon as     │
│ successful direction        │ any direction succeeds                                     │
├─────────────────────────────┼────────────────────────────────────────────────────────────┤
│ Return false after all      │ Must exhaust all 4 directions before declaring this cell   │
│ directions exhausted        │ a dead end — any one could hold the path                  │
└─────────────────────────────┴────────────────────────────────────────────────────────────┘
```

## Pseudocode
```
for each cell (r, c) in board:
    if dfs(r, c, index=0): return true
return false

dfs(row, col, index):
    if index == word.length: return true        ← base case: word complete
    if OOB: return false                        ← guard before board access
    if board[row][col] != word[index]: return false   ← mismatch (also catches '#')

    temp = board[row][col]
    board[row][col] = '#'                       ← mark visited

    for each direction (nr, nc):
        if dfs(nr, nc, index + 1):
            board[row][col] = temp              ← restore before returning
            return true

    board[row][col] = temp                      ← restore on failure
    return false
```

## Complexity
- Time: O(m × n × 4^L) — for each of m×n starting cells, DFS can branch into up to 4 directions at each of L steps (word length). In practice much less due to pruning on mismatch.
- Space: O(L) — recursion stack depth equals word length (no extra visited array needed)

## Watch Out For
- `index == word.length()` MUST come before the bounds check (Style A) — if bounds is first, single-char words like `[['a']], "a"` return false because the last-char recursive call (index=1) has OOB coords and never reaches the base case
- Restore board BEFORE returning true, not just on failure — easy to forget
- If you move bounds check inside the loop (Style B), you must add `if (index == word.length() - 1) return true` after marking visited, or the terminal case is never reached

## Dry Run
```
board = [['A','B','C','E'],
         ['S','F','C','S'],
         ['A','D','E','E']]
word = "ABCCED"

Start: dfs(0,0,'A', index=0)
  'A' == 'A' ✓ → mark '#'
  → dfs(0,1,'B', index=1)
      'B' == 'B' ✓ → mark '#'
      → dfs(0,2,'C', index=2)
          'C' == 'C' ✓ → mark '#'
          → dfs(1,2,'C', index=3)
              'C' == 'C' ✓ → mark '#'
              → dfs(2,2,'E', index=4)
                  'E' == 'E' ✓ → mark '#'
                  → dfs(2,1,'D', index=5)
                      'D' == 'D' ✓ → mark '#'
                      → index+1 == 6 == word.length() → return true ✓
                  ← restore 'D', return true
              ← restore 'E', return true
          ← restore 'C'[1,2], return true
      ← restore 'C'[0,2], return true
  ← restore 'B', return true
← restore 'A', return true

Path traced: [0,0]→[0,1]→[0,2]→[1,2]→[2,2]→[2,1]
```

## Pattern Tag
`backtracking` / `dfs` / `matrix`

## Boilerplate Template
```java
// Matrix DFS + Backtracking Template
class Solution {
    public boolean exist(char[][] board, String word) {
        int rows = board.length, cols = board[0].length;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (dfs(board, word, r, c, 0)) return true;
        return false;
    }

    private boolean dfs(char[][] board, String word, int row, int col, int index) {
        if (index == word.length()) return true;                                      // ← base case FIRST
        if (row < 0 || col < 0 || row >= board.length || col >= board[0].length)     // ← then bounds
            return false;
        if (board[row][col] != word.charAt(index)) return false;                      // ← then mismatch

        char temp = board[row][col];
        board[row][col] = '#';                                                         // ← mark visited

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] dir : dirs) {
            if (dfs(board, word, row + dir[0], col + dir[1], index + 1)) {
                board[row][col] = temp;                                                // ← restore before true
                return true;
            }
        }

        board[row][col] = temp;                                                        // ← restore on false
        return false;
    }
}
```
