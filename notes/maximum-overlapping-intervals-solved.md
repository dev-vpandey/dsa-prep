# Maximum Number of Overlapping Intervals — Medium
Problem Link: https://leetcode.com/problems/meeting-rooms-ii/ (Meeting Rooms II variant)
Solved Date: 2026-03-25
Pattern Tag: intervals / event-sweep / sorting / timeline / min-heap

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like counting the peak number of concurrent meetings in a building — the answer is the maximum number of conference rooms you need at any single moment.

## Core Insight
Sort by start time. Use a min-heap of end times. For each interval: if the earliest-ending interval is done (heap top ≤ current start), reuse that room (pop it). Always add current end to heap. Max heap size = answer.

## Approach
Sort intervals by start. Min-heap of end times. For each interval: if `heap.top <= start`, pop (room freed). Push `end`. Max heap size throughout = max overlaps.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Min-heap of end times                │ Always check the earliest-ending room │
│                                      │ first — it's the most likely to free  │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Pop if heap.top <= current start     │ Room is free — meeting ended before   │
│ (not strict <)                       │ or exactly when new one starts        │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Track max heap size                  │ Max rooms needed = max concurrent     │
│                                      │ intervals at any point                │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
sort intervals by start
minHeap = []  // stores end times
maxOverlap = 0
for each interval [start, end]:
    if heap not empty AND heap.top <= start:
        heap.pop()  // free a room
    heap.push(end)
    maxOverlap = max(maxOverlap, heap.size())
return maxOverlap
```

## Complexity
- Time: O(n log n) — sort + n heap operations
- Space: O(n) — heap

## Watch Out For
- Sort by START time (not end time — that's for the remove-minimum-intervals variant)
- Check `heap.top <= start` (not `<`): a meeting ending at 2 and starting at 2 don't overlap
- Don't pop if heap empty — guard with isEmpty()

## Dry Run
`intervals = [[0,30],[5,10],[15,20]]`
```
Sort by start: [[0,30],[5,10],[15,20]]
[0,30]: heap=[], push 30 → heap=[30], max=1
[5,10]: heap.top=30>5, push 10 → heap=[10,30], max=2
[15,20]: heap.top=10<=15, pop 10, push 20 → heap=[20,30], max=2
Result: 2 ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // sort by start
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // stores end times

        for (int[] interval : intervals) {
            if (!minHeap.isEmpty() && minHeap.peek() <= interval[0]) {
                minHeap.poll(); // free a room
            }
            minHeap.offer(interval[1]);
        }
        return minHeap.size(); // rooms still occupied = max overlaps
    }
}
```
