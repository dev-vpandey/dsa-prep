# Surrounded Regions — Medium
Problem Link: https://leetcode.com/problems/surrounded-regions/
Solved Date: 2026-03-15
Pattern Tag: multi-source-bfs / reverse-thinking / flood-fill / matrix / boundary-seed
Review Date: 2026-03-22

## SRS Tracking
- Stage: 1
- Review Date: 2026-03-23
- Last Rating: —
- Review Count: 0
- Graduated: No

## Real World Analogy
Think of a Go board game. Stones on the edge of the board can never be captured
because they're not fully enclosed. Only stones completely surrounded in the middle
get captured. Start from the edge stones to find everything that's safe.

## Core Insight
Instead of finding surrounded O's (hard), find border-connected O's (easy) —
everything else is surrounded.

## Approach
Seed BFS from all 'O's on the border. Flood-fill inward marking all reachable
'O's as safe ('S'). Then sweep the board: remaining 'O's are surrounded → flip
to 'X', safe cells 'S' → restore to 'O'.

## Mental Model

┌─────────────────────┬────────────────────────────────────────────┐
│ Decision            │ Why                                        │
├─────────────────────┼────────────────────────────────────────────┤
│ Start from border   │ Border cells can never be surrounded —     │
│ O's, not interior   │ they're open on at least one side          │
├─────────────────────┼────────────────────────────────────────────┤
│ Mark safe as 'S',   │ Need to distinguish "safe O" from          │
│ not 'O'             │ "surrounded O" during the sweep            │
├─────────────────────┼────────────────────────────────────────────┤
│ 3-step process      │ Can't flip in-place during BFS — you'd     │
│ (mark → flip → fix) │ corrupt cells you haven't visited yet      │
├─────────────────────┼────────────────────────────────────────────┤
│ Reverse thinking    │ "What's safe?" is easier than              │
│                     │ "What's surrounded?"                       │
└─────────────────────┴────────────────────────────────────────────┘

## Pseudocode
```
step 1 — seed
  for each border cell:
    if cell == 'O': mark 'S', add to queue

step 2 — BFS flood fill
  while queue not empty:
    pop cell
    for each neighbor:
      if neighbor == 'O': mark 'S', enqueue

step 3 — final sweep
  for each cell:
    if 'O'  → 'X'   (surrounded, capture it)
    if 'S'  → 'O'   (safe, restore it)
```

## Complexity
- Time: O(m×n) — every cell is visited at most once in BFS + once in sweep
- Space: O(m×n) — queue can hold all cells in worst case (all O's on border)

## Watch Out For
- `&&` vs `||` in border condition — must wrap border check in `()` before `&&`
- Don't use `var cell = board[r][c]` in sweep — modifies local copy, not the board
- BFS should spread to `'O'` neighbors only, not `'X'`

## Dry Run
Board:
```
X X X X
X O O X
X X O X
X O X X
```

Step 1 — seed: no border O's → queue is empty

Step 2 — BFS does nothing

Step 3 — sweep: all O's are surrounded → flip to X
```
X X X X
X X X X
X X X X
X X X X
```

Now with a border O:
```
X X X X
X O O X
X O X X
O O X X   ← border O at [3][0]
```

Step 1 — seed: [3][0] marked 'S', added to queue

Step 2 — BFS:
  [3][0] → neighbor [3][1]='O' → mark 'S', enqueue
  [3][1] → neighbor [2][1]='O' → mark 'S', enqueue
  [2][1] → neighbor [1][1]='O' → mark 'S', enqueue
  [1][1] → neighbor [1][2]='O' → mark 'S', enqueue
  no more O neighbors

Step 3 — sweep: no remaining O's, all S's restore to O
```
X X X X
X O O X
X O X X
O O X X   (unchanged — all were connected to border)
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public void solve(char[][] board) {
        int rows = board.length, cols = board[0].length;

        Queue<int[]> queue = new LinkedList<>();

        // Step 1: seed from border O's
        for (var r = 0; r < rows; r++) {
            for (var c = 0; c < cols; c++) {
                if ((r == 0 || r == rows - 1 || c == 0 || c == cols - 1) && board[r][c] == 'O') {
                    board[r][c] = 'S';
                    queue.add(new int[]{r, c});
                }
            }
        }

        // Step 2: BFS flood fill — mark all border-connected O's as safe
        bfs(queue, board, rows, cols);

        // Step 3: final sweep
        for (var r = 0; r < rows; r++) {
            for (var c = 0; c < cols; c++) {
                if (board[r][c] == 'S') board[r][c] = 'O';       // restore safe
                else if (board[r][c] == 'O') board[r][c] = 'X';  // capture surrounded
            }
        }
    }

    private void bfs(Queue<int[]> queue, char[][] board, int rows, int cols) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            var curr = queue.poll();
            int r = curr[0], c = curr[1];

            for (int[] dir : directions) {
                int nr = r + dir[0], nc = c + dir[1];

                if (nr < 0 || nc < 0 || nr >= rows || nc >= cols) continue;

                if (board[nr][nc] == 'O') {
                    board[nr][nc] = 'S';
                    queue.add(new int[]{nr, nc});
                }
            }
        }
    }
}
```
