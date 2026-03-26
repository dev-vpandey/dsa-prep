# Intersection of Two Linked Lists — Easy
Problem Link: https://leetcode.com/problems/intersection-of-two-linked-lists/
Solved Date: 2026-03-24
Pattern Tag: linked-list / two-pointer / length-equalization

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Two people walk different-length paths that merge into the same road — if each person walks both paths (A then B, B then A), they'll meet exactly at the merge point, having walked the same total distance.

## Core Insight
Two pointers traverse both lists (A then B, B then A). They walk the same total distance so meet at the intersection or both reach null simultaneously (no intersection).

## Approach
`pA = headA`, `pB = headB`. Each step: advance pointer; if null, redirect to other list's head. When `pA == pB`, that's the intersection (or null if no intersection).

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Redirect to other head (not restart) │ Equalize path length: A+B == B+A      │
│ when reaching null                   │ Both travel same total distance       │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Compare nodes (not values)           │ Intersection = same node object,      │
│                                      │ not just same value                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ If no intersection, both reach null  │ Both still travel A+B steps →         │
│ simultaneously → return null         │ pA==pB==null → loop exits correctly   │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
pA = headA, pB = headB
while pA != pB:
    pA = (pA == null) ? headB : pA.next
    pB = (pB == null) ? headA : pB.next
return pA  // intersection node or null
```

## Complexity
- Time: O(m + n) — each pointer walks at most m+n nodes
- Space: O(1)

## Watch Out For
- Redirect on `null` (end of list), not on last node
- Works even with no intersection — both end up at null

## Dry Run
```
A: 1→2→3→c1→c2→c3
B: 4→5→c1→c2→c3

pA walks: 1,2,3,c1,c2,c3,null→4,5,c1 ✓
pB walks: 4,5,c1,c2,c3,null→1,2,3,c1 ✓
Both reach c1 at same step → intersection = c1
```

## Boilerplate Template
```java
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            pA = (pA == null) ? headB : pA.next;
            pB = (pB == null) ? headA : pB.next;
        }
        return pA; // null if no intersection
    }
}
```
