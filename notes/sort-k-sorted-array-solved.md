# Sort a K-Sorted Array — Medium
Problem Link: https://leetcode.com/problems/sort-an-almost-sorted-array/ (GeeksForGeeks classic)
Solved Date: 2026-03-25
Pattern Tag: heap / min-heap / sliding-window / nearly-sorted

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like sorting a nearly-ordered queue where no person is more than k spots from their correct position — a window of k+1 people always contains the next correct person at the front.

## Core Insight
Min-heap of size k+1. At any point, the minimum in the current window of k+1 elements is guaranteed to be the next element in sorted order. Extract min, add next element from array.

## Approach
Fill heap with first `k+1` elements. Then for each remaining element: poll min to output, offer new element. After array exhausted, drain the heap.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Heap size = k+1                      │ Each element is at most k positions   │
│                                      │ away — window of k+1 always includes  │
│                                      │ the next correct element              │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Poll before offering new element     │ Maintain sliding window of k+1;       │
│                                      │ extract sorted output one at a time   │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
heap = min-heap
add first k+1 elements to heap
result = []
for i in k+1..n-1:
    result.add(heap.poll())
    heap.offer(arr[i])
while heap not empty:
    result.add(heap.poll())
return result
```

## Complexity
- Time: O(n log k) — n poll/offer operations on heap of size k
- Space: O(k) — heap

## Watch Out For
- Initial heap size is `min(k+1, n)` — array might be shorter than k+1
- Poll FIRST, then offer (not the other way around — heap size stays constant at k+1)

## Dry Run
`arr = [6,5,3,2,8,10,9]`, k=3
```
Initial heap (k+1=4): {2,3,5,6}
i=4(8): poll 2→[2], offer 8 → heap={3,5,6,8}
i=5(10): poll 3→[2,3], offer 10 → heap={5,6,8,10}
i=6(9): poll 5→[2,3,5], offer 9 → heap={6,8,9,10}
Drain: 6,8,9,10
Result: [2,3,5,6,8,9,10] ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int[] sortKSortedArray(int[] arr, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int n = arr.length;

        // fill initial window of k+1
        for (int i = 0; i <= Math.min(k, n - 1); i++) minHeap.offer(arr[i]);

        int[] result = new int[n];
        int idx = 0;
        for (int i = k + 1; i < n; i++) {
            result[idx++] = minHeap.poll();
            minHeap.offer(arr[i]);
        }
        while (!minHeap.isEmpty()) result[idx++] = minHeap.poll();
        return result;
    }
}
```
