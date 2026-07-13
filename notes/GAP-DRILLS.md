# Gap Drills
# Classify every Weak/Blank here. Muscle = cold-write drill. Conceptual = fresh problem drill.
# Purge Closed rows older than 7 days.
#
# Format: | Pattern | Type | Drill / Notes | Status | Added |

| Pattern | Type | Drill / Notes | Status | Added |
|---|---|---|---|---|
| monotonic-stack | Muscle | Re-read Next Greater Element I card cold — left→right traversal, pop-when-larger mechanic, leftover→-1 | Closed | 2026-06-29 |
| tree / dfs | Muscle | Cold-write checkHeight — must check `if (left == -1) return -1` and `if (right == -1) return -1` BEFORE computing Math.abs(left - right). Null returns 0, not -1. | Closed | 2026-07-01 |
| hashmap / timestamp | Muscle | Cold-write shouldPrintMessage — condition is `timestamp - last >= 10`, not `last + timestamp > 10`. Update map on true only. | Closed | 2026-07-01 |
| graph / mst / prim's | Conceptual | Solve 2 unseen MST/Prim's problems (Full mode, no hints) — misunderstood why cost filter breaks push step. Correct rule: push ALL unvisited nbrs, let heap+lazy-delete decide, never gate on current node's cost. | Open | 2026-07-02 |
