# Non-Overlapping Intervals — Medium
Problem Link: https://leetcode.com/problems/non-overlapping-intervals/
Solved Date: 2026-03-24
Pattern Tag: intervals / greedy / sorting / minimum-removal

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like scheduling meetings with a single conference room — when two meetings conflict, cancel the one that ends later (it monopolizes the room longer). Always prefer the meeting that ends soonest.

## Core Insight
Sort by end time. Greedily keep intervals with the earliest end time. When an overlap occurs, remove the current interval (later ending) and keep the previous boundary unchanged.

## Approach
Sort by end time. Track `prevEnd`. For each interval: if `start >= prevEnd`, no overlap — update prevEnd. Otherwise overlap — increment removal count, keep prevEnd (the earlier-ending interval stays).

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Sort by END time (not start)         │ Earliest-end-first greedy: leaves     │
│                                      │ most room for subsequent intervals    │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ On overlap: keep prevEnd unchanged   │ We're discarding the current interval │
│ (don't update to new interval's end) │ (which ends later) — keep the one    │
│                                      │ that ended earlier                    │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ count++ on overlap = count removals  │ We're counting how many to remove,   │
│                                      │ not how many to keep                  │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
sort intervals by end time
prevEnd = intervals[0].end
count = 0

for interval in intervals[1..]:
    if interval.start >= prevEnd:
        prevEnd = interval.end   // no overlap, accept this interval
    else:
        count++                   // overlap: remove current, keep earlier end
        // prevEnd stays unchanged (the earlier-ending is already recorded)

return count
```

## Complexity
- Time: O(n log n) — sort + O(n) pass
- Space: O(1) excluding sort

## Watch Out For
- Sort by END time (most common mistake: sorting by start instead)
- On overlap: do NOT update `prevEnd` — the current interval is removed
- Counting removals, not intervals kept

## Dry Run
`[[1,2],[2,3],[3,4],[1,3]]`
```
Sort by end: [[1,2],[2,3],[1,3],[3,4]]
prevEnd=2, count=0
[2,3]: 2>=2 ✓ → prevEnd=3
[1,3]: 1<3 overlap → count=1, prevEnd stays 3
[3,4]: 3>=3 ✓ → prevEnd=4
Result: 1 removal ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]); // sort by end time
        int count = 0, prevEnd = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= prevEnd) {
                prevEnd = intervals[i][1]; // no overlap, advance boundary
            } else {
                count++; // overlap — remove this interval (later-ending one)
                // prevEnd stays unchanged (earlier-ending interval is kept)
            }
        }
        return count;
    }
}
```
