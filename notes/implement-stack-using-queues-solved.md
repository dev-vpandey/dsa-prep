# Implement Stack Using Queues — Easy
Problem Link: https://leetcode.com/problems/implement-stack-using-queues/
Solved Date: 2026-03-24
Pattern Tag: stack / queue / design

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like a single-door room — to put something on "top" of the pile, you first have to temporarily move everyone else out, put the new person in first, then bring everyone back in behind them.

## Core Insight
On each `push`, enqueue and then rotate all previously enqueued elements to the back. The front of the queue always holds the most recently pushed element.

## Approach
Single queue. On `push(x)`: enqueue x, then rotate all `size-1` previous elements to the back (dequeue and re-enqueue). Now x is at the front. `pop`/`top` just operate on the front.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Make push O(n), pop/top O(1)         │ Reads are frequent; rotation cost paid │
│                                      │ once at insert                        │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Rotate exactly (size-1) elements     │ The new element (just added) stays at │
│ after enqueuing x                    │ front; all others cycle to the back   │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
queue = []

push(x):
    queue.enqueue(x)
    for i in 0..queue.size()-2:
        queue.enqueue(queue.dequeue())  // rotate old elements to back

pop(): return queue.dequeue()
top(): return queue.peek()
empty(): return queue.isEmpty()
```

## Complexity
- Time: push O(n), pop O(1), top O(1), empty O(1)
- Space: O(n)

## Watch Out For
- Rotate exactly `size - 1` times (after adding x, before size was already updated)
- `top()` is just peek — don't pop

## Dry Run
```
push(1): queue=[1], rotate 0 times → [1]
push(2): queue=[1,2], rotate 1 time: dequeue 1 → [2,1]
push(3): queue=[2,1,3], rotate 2 times: dequeue 2→[1,3,2], dequeue 1→[3,2,1]
top() → 3 ✓
pop() → 3, queue=[2,1]
top() → 2 ✓
```

## Boilerplate Template
```java
import java.util.*;

class MyStack {
    private final Queue<Integer> queue = new LinkedList<>();

    public void push(int x) {
        queue.offer(x);
        // rotate all previous elements to the back
        for (int i = 0; i < queue.size() - 1; i++) {
            queue.offer(queue.poll());
        }
    }

    public int pop() { return queue.poll(); }
    public int top() { return queue.peek(); }
    public boolean empty() { return queue.isEmpty(); }
}
```
