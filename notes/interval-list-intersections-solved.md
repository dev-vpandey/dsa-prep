# Interval List Intersections — Medium
Problem Link: https://leetcode.com/problems/interval-list-intersections/
Solved Date: 2026-03-25
Pattern Tag: two-pointer / intervals / intersection

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like finding overlapping time slots between two people's calendars — walk through both calendars in order. When slots overlap, record the overlap. Always advance the calendar with the earlier-ending slot.

## Core Insight
Two pointers, one per list. Intersection exists when `max(start1, start2) <= min(end1, end2)`. Always advance the pointer whose interval ends earlier.

## Approach
`i = 0`, `j = 0`. For each pair `(A[i], B[j])`: compute overlap as `[max(a.start, b.start), min(a.end, b.end)]`. If valid (lo <= hi), add to result. Advance the pointer with smaller end time.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Intersection = [max(s1,s2),min(e1,e2)]│ Overlap starts at the later start    │
│                                      │ and ends at the earlier end          │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Advance pointer with smaller end     │ That interval can no longer intersect │
│                                      │ with any future intervals in the      │
│                                      │ other list (they all start later)     │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Valid intersection: lo <= hi         │ lo > hi means no overlap (disjoint)   │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
i = 0, j = 0, result = []
while i < A.length AND j < B.length:
    lo = max(A[i].start, B[j].start)
    hi = min(A[i].end, B[j].end)
    if lo <= hi: result.add([lo, hi])
    if A[i].end < B[j].end: i++
    else: j++
return result
```

## Complexity
- Time: O(m + n) — each pointer advances at most m/n times
- Space: O(m + n) — result list

## Watch Out For
- Advance based on end time, not start time
- Include both equal-end case: `if A[i].end < B[j].end` (else covers equal)
- No sorting needed — both lists are already sorted

## Dry Run
`A=[[0,2],[5,10],[13,23],[24,25]]`, `B=[[1,5],[8,12],[15,24],[25,26]]`
```
i=0,j=0: [0,2]∩[1,5]=[1,2] → result=[[1,2]], A.end=2<5 → i++
i=1,j=0: [5,10]∩[1,5]=[5,5] → result+=[[5,5]], A.end=10>5 → j++
i=1,j=1: [5,10]∩[8,12]=[8,10] → result+=[[8,10]], A.end=10<12 → i++
i=2,j=1: [13,23]∩[8,12]=lo=13>12, no overlap → j++
i=2,j=2: [13,23]∩[15,24]=[15,23] → result+=[[15,23]], A.end=23<24 → i++
...
Result: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]] ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < firstList.length && j < secondList.length) {
            int lo = Math.max(firstList[i][0], secondList[j][0]);
            int hi = Math.min(firstList[i][1], secondList[j][1]);
            if (lo <= hi) result.add(new int[]{lo, hi});
            // advance pointer with smaller end
            if (firstList[i][1] < secondList[j][1]) i++;
            else j++;
        }
        return result.toArray(new int[0][]);
    }
}
```
