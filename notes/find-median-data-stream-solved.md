# Find Median from Data Stream — Hard
Problem Link: https://leetcode.com/problems/find-median-from-data-stream/
Solved Date: 2026-03-25
Pattern Tag: heap / two-heap / max-heap / min-heap / dynamic-median

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like keeping a running list of salaries split in two — one pile sorted highest-to-lowest (left half) and one sorted lowest-to-highest (right half). The median is always at the boundary between the two piles.

## Core Insight
Two heaps: max-heap `lo` for the lower half, min-heap `hi` for the upper half. Sizes differ by at most 1. Median = `lo.top` (odd) or `(lo.top + hi.top) / 2.0` (even).

## Approach
`addNum`: add to `lo` (max-heap). Balance: if `lo.top > hi.top`, move `lo.top` to `hi`. If size difference > 1, rebalance. `findMedian`: if sizes equal → average tops; else → top of larger heap.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Max-heap for lower half, min-heap    │ lo.top = max of lower half;           │
│ for upper half                       │ hi.top = min of upper half            │
│                                      │ Together they give the median         │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Always add to lo first               │ Then rebalance: if lo's max > hi's    │
│                                      │ min, they need to swap                │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Rebalance to keep |lo|-|hi| <= 1     │ Median is defined at the size         │
│                                      │ boundary — can't have one heap        │
│                                      │ dominate by more than 1 element       │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
lo = max-heap, hi = min-heap

addNum(num):
    lo.push(num)
    if hi not empty AND lo.top > hi.top:
        hi.push(lo.pop())      // fix ordering
    if lo.size > hi.size + 1:
        hi.push(lo.pop())      // rebalance: lo too big
    if hi.size > lo.size:
        lo.push(hi.pop())      // rebalance: hi too big

findMedian():
    if lo.size == hi.size: return (lo.top + hi.top) / 2.0
    return lo.top              // lo has one more
```

## Complexity
- Time: O(log n) per addNum, O(1) findMedian
- Space: O(n)

## Watch Out For
- Java: max-heap = `new PriorityQueue<>(Collections.reverseOrder())`
- Rebalance after both ordering fix AND size rebalance
- Return `double` for even count median (integer division loses precision)
- Always let `lo` be the potentially larger heap (by 1)

## Dry Run
```
addNum(1): lo=[1], hi=[]
addNum(2): lo=[1], push to lo→[2,1], lo.top=2>hi.top=∞? no. lo.size=2>hi.size+1=1 → hi.push(lo.pop=2)=[2], lo=[1]
findMedian: sizes equal → (1+2)/2.0=1.5 ✓
addNum(3): push to lo→[3,1], lo.top=3>hi.top=2 → hi=[2,3]... wait
  lo.push(3)→lo=[3,1], lo.top=3>hi.top=2 → hi.push(lo.pop=3)=[2,3], lo=[1]
  hi.size=2>lo.size=1 → lo.push(hi.pop=2)=[2,1], hi=[3]
findMedian: lo.size(2)>hi.size(1) → return lo.top=2 ✓
```

## Boilerplate Template
```java
import java.util.*;

class MedianFinder {
    private final PriorityQueue<Integer> lo = new PriorityQueue<>(Collections.reverseOrder()); // max-heap
    private final PriorityQueue<Integer> hi = new PriorityQueue<>(); // min-heap

    public void addNum(int num) {
        lo.offer(num);
        // fix ordering: lo's max should not exceed hi's min
        if (!hi.isEmpty() && lo.peek() > hi.peek()) hi.offer(lo.poll());
        // rebalance sizes: lo can have at most 1 more than hi
        if (lo.size() > hi.size() + 1) hi.offer(lo.poll());
        if (hi.size() > lo.size()) lo.offer(hi.poll());
    }

    public double findMedian() {
        if (lo.size() == hi.size()) return (lo.peek() + hi.peek()) / 2.0;
        return lo.peek(); // lo has one more element
    }
}
```
