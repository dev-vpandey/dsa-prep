# Tesco SDE-III Prep — Priority List

**Active until: 2026-07-10.** Interview-sourced list (LeetCode company tag). Delete this file + its CLAUDE.md reference once interview is done or list is cleared.

**Rule for every `/review-dsa` and `/start` session until 2026-07-10:** pull from the "Not Yet Solved" table below FIRST, before normal SRS queue or new-pattern picks. Once a row is solved, move it to "Solved" and add its row to `notes/REVIEW.md` as usual via the post-solve checklist.

## Solved (already in rotation — just re-drill via normal SRS)

╔══════╦═══════════════════╦══════════════════════════════╦════════════════════════════╗
║  #   ║ Problem           ║ Existing Note                 ║ Pattern                     ║
╠══════╬═══════════════════╬══════════════════════════════╬════════════════════════════╣
║ 1    ║ Two Sum           ║ two-sum-solved.md              ║ hashmap / complement-lookup ║
║ 56   ║ Merge Intervals   ║ merge-intervals-solved.md      ║ sort by start / max end     ║
║ 207  ║ Course Schedule   ║ course-schedule-solved.md      ║ topo-sort / kahn's          ║
║ 2824 ║ Count Pairs < Tgt ║ count-pairs-sum-less-than-target-solved.md ║ two-pointer / sorted-count-diff ║
╚══════╩═══════════════════╩══════════════════════════════╩════════════════════════════╝

## Not Yet Solved — priority queue

╔══════╦═══════════════════════════════════════╦══════╦══════════════════════════════════════╦═══════════════════════════════════════════╗
║  #   ║ Problem                                ║ Diff ║ Closest Existing Note (reference only)║ Why it's new / gap                         ║
╠══════╬═══════════════════════════════════════╬══════╬══════════════════════════════════════╬═══════════════════════════════════════════╣
║ 2812 ║ Find the Safest Path in a Grid         ║ Med  ║ walls-and-gates (multi-src BFS) +      ║ combo pattern: multi-source BFS distance   ║
║      ║                                        ║      ║ koko-eating-bananas (binary search     ║ map feeding a binary-search-on-answer /    ║
║      ║                                        ║      ║ on answer)                              ║ max-min path check. Not covered yet.       ║
╠══════╬═══════════════════════════════════════╬══════╬══════════════════════════════════════╬═══════════════════════════════════════════╣
║ 42   ║ Trapping Rain Water                    ║ Hard ║ container-with-most-water              ║ same two-pointer family, but needs         ║
║      ║                                        ║      ║ (two-pointer / move shorter)           ║ leftMax/rightMax tracking — new variant.   ║
╠══════╬═══════════════════════════════════════╬══════╬══════════════════════════════════════╬═══════════════════════════════════════════╣
║ 253  ║ Meeting Rooms II                       ║ Med  ║ maximum-overlapping-intervals          ║ near-identical pattern (heap of end-times) ║
║      ║                                        ║      ║ (heap / end-times)                     ║ — should be a fast solve, confirms pattern.║
╠══════╬═══════════════════════════════════════╬══════╬══════════════════════════════════════╬═══════════════════════════════════════════╣
║ 198  ║ House Robber                           ║ Med  ║ none — no DP card exists yet           ║ true gap: first 1D DP problem. Ties to     ║
║      ║                                        ║      ║                                         ║ known weak spot (DP).                      ║
╠══════╬═══════════════════════════════════════╬══════╬══════════════════════════════════════╬═══════════════════════════════════════════╣
║ 57   ║ Insert Interval                        ║ Med  ║ merge-intervals (sort by start /       ║ same family as Merge Intervals, adds       ║
║      ║                                        ║      ║ max end)                               ║ insert-before-merge step — new variant.    ║
╠══════╬═══════════════════════════════════════╬══════╬══════════════════════════════════════╬═══════════════════════════════════════════╣
║ 1204 ║ Last Person to Fit in the Bus          ║ Med  ║ none close                             ║ simple greedy/two-pointer simulation —     ║
║      ║                                        ║      ║                                         ║ no existing card, quick add.                ║
╚══════╩═══════════════════════════════════════╩══════╩══════════════════════════════════════╩═══════════════════════════════════════════╝

## Suggested solve order (easy wins first, gap last)
1. ~~2824 · Count Pairs Whose Sum is Less than Target~~ — SOLVED 2026-07-01, rated 3/5, card: count-pairs-sum-less-than-target-solved.md
2. 253 · Meeting Rooms II (near-identical to existing card)
3. 57 · Insert Interval (variant of solved Merge Intervals)
4. 42 · Trapping Rain Water (two-pointer variant, Hard but pattern-adjacent)
5. 2812 · Find the Safest Path in a Grid (combo pattern, needs full teach)
6. 198 · House Robber (true DP gap — needs full teach, no shortcut)
7. 1204 · Last Person to Fit in the Bus (simple, do whenever there's a spare slot)
