# Middle of the Linked List — Easy
Problem Link: https://leetcode.com/problems/middle-of-the-linked-list/
Solved Date: 2026-03-24
Pattern Tag: linked-list / slow-fast-pointer / Floyd

## SRS Tracking
- Stage: 5
- Review Date: 2026-04-23
- Last Rating: Strong
- Review Count: 8
- Graduated: No

---

# Real World Analogy
Two runners on a track — one runs at double speed. When the fast runner hits the finish line, the slow runner is exactly at the halfway point.

## Core Insight
Fast pointer moves 2 nodes per step, slow moves 1. When fast can't advance further, slow is at the middle.

## Approach
Initialize both `slow` and `fast` at `head`. Move `fast` two steps and `slow` one step each iteration. Stop when `fast == null` or `fast.next == null`. Return `slow`.

## Mental Model
```
┌──────────────────────────────┬───────────────────────────────────────────────┐
│ Decision                     │ Why                                           │
├──────────────────────────────┼───────────────────────────────────────────────┤
│ Stop when fast==null OR      │ Even-length: fast lands on null.              │
│ fast.next==null              │ Odd-length: fast.next is null.                │
│                              │ Both conditions needed to avoid NPE           │
├──────────────────────────────┼───────────────────────────────────────────────┤
│ Return slow (second middle   │ For even-length [1,2,3,4], slow stops at 3   │
│ for even-length lists)       │ (second of two middles) — LeetCode expected  │
└──────────────────────────────┴───────────────────────────────────────────────┘
```

## Pseudocode
```
slow = head
fast = head
while fast != null AND fast.next != null:
    slow = slow.next
    fast = fast.next.next
return slow
```

## Complexity
- Time: O(n) — fast traverses full list
- Space: O(1) — two pointers

## Watch Out For
- Check `fast != null` before `fast.next != null` — short-circuit prevents NPE
- For even-length list, returns the SECOND middle node (LeetCode standard)

## Dry Run
`1 → 2 → 3 → 4 → 5`
```
slow=1, fast=1
slow=2, fast=3
slow=3, fast=5 → fast.next=null, stop
Return slow=3 ✓

[1 → 2 → 3 → 4]
slow=1, fast=1
slow=2, fast=3
slow=3, fast=null → stop
Return slow=3 (second middle) ✓
```

## Boilerplate Template
```java
class Solution {
    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
```
