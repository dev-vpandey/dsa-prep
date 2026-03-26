# Flatten a Multilevel Doubly Linked List — Medium
Problem Link: https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/
Solved Date: 2026-03-24
Pattern Tag: linked-list / stack / multilevel / iterative-dfs

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like flattening a multi-level org chart into a linear reporting chain — whenever you encounter someone with a sub-team, insert the entire sub-team inline before continuing with the main team.

## Core Insight
When a node has a child: save next, flatten child into current position, connect tail of child to saved next, null the child pointer. Continue processing the flattened chain.

## Approach
Iterate. When current node has a child: save `next`. Find the tail of the child's chain. Connect `curr → child` and `tail → next`. Set `child = null`. Continue with `curr.next` (which is now the child).

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Save curr.next before changing links │ After connecting child, original next │
│                                      │ is lost if not saved                  │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Find tail of child chain             │ Need end of child's subtree to        │
│ (while tail.next != null)            │ reconnect to the saved next           │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Null the child pointer after inline  │ Problem requires child=null after     │
│                                      │ flattening                            │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
curr = head
while curr != null:
    if curr.child != null:
        next = curr.next
        child = curr.child
        tail = child
        while tail.next != null: tail = tail.next

        // connect curr → child
        curr.next = child
        child.prev = curr

        // connect tail → next
        tail.next = next
        if next != null: next.prev = tail

        curr.child = null  // clear child pointer

    curr = curr.next
return head
```

## Complexity
- Time: O(n) — each node visited at most twice (once in main loop, once finding tail)
- Space: O(1) — in-place

## Watch Out For
- Update `next.prev = tail` (doubly linked — both pointers must be correct)
- Handle `next == null` when connecting tail (tail.next = null is fine, but no prev update needed)
- Clear `child` pointer after reconnecting

## Dry Run
```
1 - 2 - 3 - 4 - 5
        |
        7 - 8 - 9

curr=1,2: no child
curr=3: child=7, next=4
  tail of 7-8-9 = 9
  3→7 (3.next=7, 7.prev=3)
  9→4 (9.next=4, 4.prev=9)
  3.child=null
List: 1-2-3-7-8-9-4-5 ✓
```

## Boilerplate Template
```java
class Solution {
    public Node flatten(Node head) {
        Node curr = head;
        while (curr != null) {
            if (curr.child != null) {
                Node next = curr.next;
                Node child = curr.child;

                // find tail of child's chain
                Node tail = child;
                while (tail.next != null) tail = tail.next;

                // connect curr → child
                curr.next = child;
                child.prev = curr;

                // connect tail → next
                tail.next = next;
                if (next != null) next.prev = tail;

                curr.child = null; // clear child pointer
            }
            curr = curr.next;
        }
        return head;
    }
}
```
