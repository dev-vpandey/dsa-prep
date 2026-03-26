# Sliding Window Maximum — Hard
Problem Link: https://leetcode.com/problems/sliding-window-maximum/
Solved Date: 2026-03-24
Pattern Tag: monotonic-deque / sliding-window / max-tracking

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like a group of people in a queue where shorter people stand behind taller ones — when a taller person arrives, all shorter people behind them leave the queue. The tallest person is always at the front.

## Core Insight
Maintain a decreasing deque of indices. The front is always the index of the max in the current window. Remove front when out of window; remove back when new element is larger.

## Approach
For each index `right`: remove from back all indices where `nums[deque.back] <= nums[right]` (they're useless). Add `right` to back. Remove from front if out of window (`front < right - k + 1`). Record `nums[deque.front]` as window max.

## Mental Model
```
┌───────────────────────────────────────┬──────────────────────────────────────┐
│ Decision                              │ Why                                  │
├───────────────────────────────────────┼──────────────────────────────────────┤
│ Store indices, not values             │ Need to check if front index is out  │
│                                       │ of the current window                │
├───────────────────────────────────────┼──────────────────────────────────────┤
│ Remove back when nums[back] <=        │ Smaller elements can never be max    │
│ nums[right]                           │ as long as nums[right] is in window  │
├───────────────────────────────────────┼──────────────────────────────────────┤
│ Remove front when front < right-k+1  │ Front is out of the k-size window    │
├───────────────────────────────────────┼──────────────────────────────────────┤
│ Record result only when right >= k-1  │ Window not full yet for first k-1    │
│                                       │ iterations                           │
└───────────────────────────────────────┴──────────────────────────────────────┘
```

## Pseudocode
```
deque = ArrayDeque (stores indices)
result = []
for right in 0..n-1:
    // remove smaller elements from back
    while deque not empty AND nums[deque.back] <= nums[right]:
        deque.pollLast()
    deque.addLast(right)
    // remove out-of-window index from front
    if deque.front < right - k + 1:
        deque.pollFirst()
    // record max when window is full
    if right >= k - 1:
        result.add(nums[deque.front])
return result
```

## Complexity
- Time: O(n) — each index added and removed from deque at most once
- Space: O(k) — deque holds at most k indices

## Watch Out For
- Remove from back BEFORE adding new element
- Remove from front AFTER adding (check window validity)
- Use `<=` when removing from back (remove equals too — they're redundant)
- Record result only when `right >= k - 1`

## Dry Run
`nums = [1,3,-1,-3,5,3,6,7]`, `k = 3`
```
right=0(1): deque=[0]
right=1(3): 1<=3 → pop 0; deque=[1]
right=2(-1): deque=[1,2]; right>=2 → max=nums[1]=3
right=3(-3): deque=[1,2,3]; front=1>=3-3+1=1 ✓ → max=nums[1]=3
right=4(5): pop 3,pop 2,pop 1; deque=[4]; front=4>=4-3+1=2 ✓ → max=nums[4]=5
right=5(3): deque=[4,5]; max=nums[4]=5
right=6(6): pop 5,pop 4; deque=[6]; max=nums[6]=6
right=7(7): pop 6; deque=[7]; max=nums[7]=7
Result: [3,3,5,5,6,7] ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>(); // stores indices

        for (int right = 0; right < n; right++) {
            // remove smaller elements from back
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[right])
                deque.pollLast();
            deque.addLast(right);

            // remove out-of-window index from front
            if (deque.peekFirst() < right - k + 1) deque.pollFirst();

            // record when window is full
            if (right >= k - 1) result[right - k + 1] = nums[deque.peekFirst()];
        }
        return result;
    }
}
```
