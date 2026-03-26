# Implement Queue Using Stacks — Easy
Problem Link: https://leetcode.com/problems/implement-queue-using-stacks/
Solved Date: 2026-03-24
Pattern Tag: queue / stack / amortized-O(1) / two-stack

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like a cafeteria tray dispenser — new trays go into the back stack. When the front stack (for serving) is empty, you flip the whole back stack into the front. Each tray is moved at most twice total.

## Core Insight
Two stacks: `inbox` for push, `outbox` for pop/peek. Transfer all from inbox to outbox ONLY when outbox is empty. Each element moves at most twice → amortized O(1).

## Approach
`push`: always push to `inbox`. `pop`/`peek`: if `outbox` is empty, transfer ALL from inbox to outbox (reverses order = FIFO). Then pop/peek from outbox.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Transfer only when outbox is empty   │ Transferring mid-stream would reorder │
│                                      │ elements incorrectly                  │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Transfer ALL, not just one           │ Amortized O(1): each element moved    │
│                                      │ exactly once from inbox to outbox     │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Amortized O(1) not worst-case O(1)   │ Single pop may cost O(n) but averaged │
│                                      │ over n operations = O(1) each         │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
inbox = [], outbox = []

push(x): inbox.push(x)

pop():
    if outbox.isEmpty():
        while inbox not empty: outbox.push(inbox.pop())
    return outbox.pop()

peek():
    if outbox.isEmpty():
        while inbox not empty: outbox.push(inbox.pop())
    return outbox.peek()

empty(): return inbox.isEmpty() AND outbox.isEmpty()
```

## Complexity
- Time: push O(1), pop/peek amortized O(1), empty O(1)
- Space: O(n)

## Watch Out For
- Transfer only when outbox is empty, not every time
- `empty()` checks BOTH stacks
- `peek()` and `pop()` share the same transfer logic — extract to a helper

## Dry Run
```
push(1): inbox=[1]
push(2): inbox=[1,2]
peek(): outbox empty → transfer: outbox=[2,1], inbox=[] → peek=1 ✓
push(3): inbox=[3], outbox=[2,1]
pop(): outbox not empty → pop=1 ✓, outbox=[2]
pop(): outbox not empty → pop=2 ✓, outbox=[]
pop(): outbox empty → transfer: outbox=[3], inbox=[] → pop=3 ✓
```

## Boilerplate Template
```java
import java.util.*;

class MyQueue {
    private final Deque<Integer> inbox = new ArrayDeque<>();
    private final Deque<Integer> outbox = new ArrayDeque<>();

    public void push(int x) { inbox.push(x); }

    public int pop() {
        transfer();
        return outbox.pop();
    }

    public int peek() {
        transfer();
        return outbox.peek();
    }

    public boolean empty() { return inbox.isEmpty() && outbox.isEmpty(); }

    private void transfer() {
        if (outbox.isEmpty()) {
            while (!inbox.isEmpty()) outbox.push(inbox.pop());
        }
    }
}
```
