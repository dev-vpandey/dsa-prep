# Reverse a Singly Linked List — Easy
Problem Link: https://leetcode.com/problems/reverse-linked-list/
Solved Date: 2026-03-24
Pattern Tag: linked-list / iterative / three-pointer

## SRS Tracking
- Stage: 5
- Review Date: 2026-04-23
- Last Rating: Strong
- Review Count: 8
- Graduated: No

---

# Real World Analogy
Like reversing a chain of paper clips — detach each one from its right neighbor and clip it to the growing reversed chain on the left. When you reach the end, the whole chain is reversed.

## Core Insight
At each node: save `next`, flip `curr.next` to point backward (`prev`), then advance both `prev` and `curr` forward.

## Approach
Three pointers: `prev = null`, `curr = head`. At each step: save `curr.next`, set `curr.next = prev`, advance `prev = curr`, advance `curr = savedNext`. When `curr` is null, `prev` is the new head.

## Mental Model
```
┌────────────────────────────┬─────────────────────────────────────────────────┐
│ Decision                   │ Why                                             │
├────────────────────────────┼─────────────────────────────────────────────────┤
│ Save next BEFORE flipping  │ Once you flip curr.next = prev, you lose the    │
│                            │ reference to the rest of the list               │
├────────────────────────────┼─────────────────────────────────────────────────┤
│ prev starts as null        │ The old head becomes the new tail — its next    │
│                            │ must point to null                              │
├────────────────────────────┼─────────────────────────────────────────────────┤
│ Return prev, not curr      │ Loop ends when curr == null; prev is the last   │
│                            │ non-null node = new head                        │
└────────────────────────────┴─────────────────────────────────────────────────┘
```

## Pseudocode
```
prev = null
curr = head
while curr != null:
    next = curr.next      // save forward link
    curr.next = prev      // flip pointer backward
    prev = curr           // advance prev
    curr = next           // advance curr
return prev
```

## Complexity
- Time: O(n) — visit each node once
- Space: O(1) — three pointers, no extra space

## Watch Out For
- Always save `next` before flipping — classic mistake
- Return `prev`, not `curr` (curr is null at loop end)
- Empty list (head == null): loop never runs, prev = null returned — correct

## Dry Run
Input: `1 → 2 → 3 → null`
```
prev=null, curr=1: next=2, 1.next=null, prev=1, curr=2
prev=1,    curr=2: next=3, 2.next=1,    prev=2, curr=3
prev=2,    curr=3: next=null, 3.next=2, prev=3, curr=null
Return prev=3 → 3 → 2 → 1 → null ✓
```

## Boilerplate Template
```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next; // save before breaking link
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev; // new head
    }
}
```
