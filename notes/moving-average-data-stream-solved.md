# Moving Average from Data Stream — Easy
Problem Link: https://leetcode.com/problems/moving-average-from-data-stream/
Solved Date: 2026-03-24
Pattern Tag: queue / sliding-window / design / running-sum

## SRS Tracking
- Stage: 5
- Review Date: 2026-04-23
- Last Rating: Strong
- Review Count: 8
- Graduated: No

---

# Real World Analogy
Like a stock ticker showing the average price over the last N trades — when a new trade comes in and the window is full, drop the oldest trade's contribution and add the new one.

## Core Insight
Maintain a fixed-size queue and a running sum. On add: if queue is full, subtract the front element from sum before removing it, then add the new value. Average = sum / queue.size().

## Approach
Queue of size `maxSize`. Track `runningSum`. On `next(val)`: if queue.size() == maxSize, dequeue front and subtract from sum. Enqueue val, add to sum. Return sum / queue.size().

## Mental Model
```
┌────────────────────────────────────┬─────────────────────────────────────────┐
│ Decision                           │ Why                                     │
├────────────────────────────────────┼─────────────────────────────────────────┤
│ Subtract before removing from      │ Remove head's value from running sum    │
│ queue (not after)                  │ before poll() — poll() loses the value  │
├────────────────────────────────────┼─────────────────────────────────────────┤
│ Divide by queue.size() not maxSize │ Window may not be full yet at the start │
└────────────────────────────────────┴─────────────────────────────────────────┘
```

## Pseudocode
```
queue = []
sum = 0
maxSize = given

next(val):
    if queue.size() == maxSize:
        sum -= queue.poll()   // remove oldest
    queue.offer(val)
    sum += val
    return sum / queue.size()
```

## Complexity
- Time: O(1) per call
- Space: O(maxSize) — queue holds at most maxSize elements

## Watch Out For
- Divide by `queue.size()` (actual elements), not `maxSize`
- Return as `double` — integer division loses precision

## Dry Run
`maxSize = 3`
```
next(1): queue=[1], sum=1, avg=1.0
next(10): queue=[1,10], sum=11, avg=5.5
next(3): queue=[1,10,3], sum=14, avg=4.67
next(5): full → subtract 1 → queue=[10,3,5], sum=18, avg=6.0 ✓
```

## Boilerplate Template
```java
import java.util.*;

class MovingAverage {
    private final Queue<Integer> window;
    private final int maxSize;
    private double sum = 0;

    public MovingAverage(int size) {
        this.maxSize = size;
        this.window = new ArrayDeque<>();
    }

    public double next(int val) {
        if (window.size() == maxSize) {
            sum -= window.poll(); // subtract outgoing element
        }
        window.offer(val);
        sum += val;
        return sum / window.size();
    }
}
```
