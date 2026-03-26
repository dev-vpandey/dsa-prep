# Find if Linked List is a Palindrome — Easy
Problem Link: https://leetcode.com/problems/palindrome-linked-list/
Solved Date: 2026-03-24
Pattern Tag: linked-list / slow-fast-pointer / reverse-second-half

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like checking if a word reads the same forwards and backwards — fold the list at the middle and compare the two halves directly.

## Core Insight
Find the middle using slow/fast, reverse the second half, compare with first half, then restore (optional). O(1) space.

## Approach
Find middle with slow/fast pointers. Reverse the second half. Compare first half and reversed second half. If all match, it's a palindrome.

## Mental Model
```
┌─────────────────────────────────────┬──────────────────────────────────────────┐
│ Decision                            │ Why                                      │
├─────────────────────────────────────┼──────────────────────────────────────────┤
│ Find middle, then reverse second    │ O(1) space — no need to copy to array   │
│                                     │ just reverse in-place                    │
├─────────────────────────────────────┼──────────────────────────────────────────┤
│ Compare until secondHalf is null    │ For odd-length: first half has extra     │
│                                     │ middle element — second half drives loop │
├─────────────────────────────────────┼──────────────────────────────────────────┤
│ Slow/fast to find middle            │ fast at end → slow at middle             │
│                                     │ Reverse from slow.next for even/odd safe │
└─────────────────────────────────────┴──────────────────────────────────────────┘
```

## Pseudocode
```
// Step 1: find middle
slow = fast = head
while fast != null AND fast.next != null:
    slow = slow.next
    fast = fast.next.next

// Step 2: reverse second half
prev = null, curr = slow.next
while curr != null:
    next = curr.next; curr.next = prev; prev = curr; curr = next
secondHead = prev

// Step 3: compare
p1 = head, p2 = secondHead
while p2 != null:
    if p1.val != p2.val: return false
    p1 = p1.next; p2 = p2.next
return true
```

## Complexity
- Time: O(n) — find middle O(n/2) + reverse O(n/2) + compare O(n/2)
- Space: O(1) — in-place reversal

## Watch Out For
- Reverse from `slow.next`, not `slow` — slow is the middle, second half starts after
- Compare while `p2 != null` (shorter second half drives the loop for odd-length)
- For production code: restore the second half after comparison

## Dry Run
`1 → 2 → 2 → 1`
```
Find middle: slow=2(index 1), fast=1(tail)
Reverse from slow.next: 2→1 becomes 1→2
Compare: 1==1 ✓, 2==2 ✓ → true ✓

1 → 2 → 3 → 2 → 1
Find middle: slow=3, fast=1(tail)
Reverse from slow.next: 2→1 becomes 1→2
Compare: 1==1 ✓, 2==2 ✓ → true ✓
```

## Boilerplate Template
```java
class Solution {
    public boolean isPalindrome(ListNode head) {
        // find middle
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // reverse second half
        ListNode prev = null, curr = slow.next;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        // compare
        ListNode p1 = head, p2 = prev;
        while (p2 != null) {
            if (p1.val != p2.val) return false;
            p1 = p1.next;
            p2 = p2.next;
        }
        return true;
    }
}
```
