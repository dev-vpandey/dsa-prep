# Merge Intervals — Medium
Problem Link: https://leetcode.com/problems/merge-intervals/
Solved Date: 2026-03-24
Pattern Tag: intervals / sorting / greedy / overlap-merge

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like merging overlapping calendar appointments — sort them by start time, then fold each overlapping appointment into the previous one.

## Core Insight
Sort by start time. For each interval, if it overlaps with the last merged (start ≤ last.end), extend the end to max. Otherwise add as a new separate interval.

## Approach
Sort intervals by start. Maintain a result list. For each interval: compare its start with the last interval's end in result. If overlapping, update end. Otherwise append.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Sort by start time                   │ After sorting, only need to compare   │
│                                      │ each interval with the last one       │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Overlap: new.start <= last.end       │ ≤ means touching intervals merge too: │
│                                      │ [1,2],[2,3] → [1,3]                  │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Update end with max(last.end,new.end)│ New interval might be contained in    │
│                                      │ last: [1,10] absorbs [2,5]           │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
sort intervals by intervals[i][0]
result = [intervals[0]]
for interval in intervals[1..]:
    last = result.last
    if interval[0] <= last[1]:
        last[1] = max(last[1], interval[1])  // extend
    else:
        result.add(interval)                  // no overlap, new entry
return result
```

## Complexity
- Time: O(n log n) — dominated by sort; merge pass is O(n)
- Space: O(n) — result list

## Watch Out For
- Sort before processing — unordered input breaks the approach
- `max(last.end, new.end)` — don't just set to new.end (contained case)
- Handle single interval input (result list starts with intervals[0])

## Dry Run
`[[1,3],[2,6],[8,10],[15,18]]`
```
Sort: already sorted by start
result = [[1,3]]
[2,6]: 2<=3 → extend → result=[[1,6]]
[8,10]: 8>6 → append → result=[[1,6],[8,10]]
[15,18]: 15>10 → append → result=[[1,6],[8,10],[15,18]] ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // sort by start
        List<int[]> result = new ArrayList<>();
        result.add(intervals[0]);

        for (int i = 1; i < intervals.length; i++) {
            int[] last = result.get(result.size() - 1);
            if (intervals[i][0] <= last[1]) {
                last[1] = Math.max(last[1], intervals[i][1]); // merge
            } else {
                result.add(intervals[i]); // no overlap
            }
        }
        return result.toArray(new int[0][]);
    }
}
```
