# Random Pick with Weight — Medium
Problem Link: https://leetcode.com/problems/random-pick-with-weight/
Solved Date: 2026-03-25
Pattern Tag: prefix-sum / binary-search / randomized / weighted-sampling

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like a weighted lottery — a ticket pool where index 0 has `w[0]` tickets, index 1 has `w[1]` tickets, etc. Draw a random ticket number and find which person owns it (binary search on the prefix sum).

## Core Insight
Build prefix sum array. Generate a random number in `[1, totalWeight]`. Binary search for the leftmost prefix sum >= that random number. That index is the selected pick.

## Approach
Constructor: build prefix sums. `pickIndex`: generate `target = random.nextInt(total) + 1` (range 1 to total). Binary search prefix array for leftmost value >= target. Return that index.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Random in [1, total] (not [0,total)) │ Prefix sum starts at w[0]; target=0   │
│                                      │ would never map correctly             │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Binary search for LEFTMOST >= target │ Prefix sum at index i represents the  │
│                                      │ upper boundary of i's weight range    │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Prefix[i] = running sum of weights   │ Weight range of index i is            │
│                                      │ (prefix[i-1], prefix[i]]              │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
Constructor:
    prefix = prefix sum of w

pickIndex():
    target = random.nextInt(prefix[last]) + 1
    // binary search for leftmost prefix >= target
    left=0, right=n-1
    while left < right:
        mid = (left+right)/2
        if prefix[mid] < target: left=mid+1
        else: right=mid
    return left
```

## Complexity
- Time: O(n) build, O(log n) per pick
- Space: O(n) — prefix array

## Watch Out For
- Random target in `[1, total]` (1-indexed), not `[0, total)`
- Binary search finds leftmost prefix >= target (upper bound search pattern: `right = mid`)
- `random.nextInt(total)` gives `[0, total-1]`, so add 1 to get `[1, total]`

## Dry Run
`w = [1, 3, 2]` → prefix = [1, 4, 6], total=6
```
Range: index 0 → [1,1], index 1 → [2,4], index 2 → [5,6]
target=3: BS → left=0,right=2,mid=1: prefix[1]=4>=3 → right=1
           left=0,right=1,mid=0: prefix[0]=1<3 → left=1
           left=1=right → return 1 ✓ (falls in index 1's range [2,4])
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    private final int[] prefix;
    private final Random rand = new Random();

    public Solution(int[] w) {
        prefix = new int[w.length];
        prefix[0] = w[0];
        for (int i = 1; i < w.length; i++) prefix[i] = prefix[i - 1] + w[i];
    }

    public int pickIndex() {
        int target = rand.nextInt(prefix[prefix.length - 1]) + 1; // [1, total]
        int left = 0, right = prefix.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (prefix[mid] < target) left = mid + 1;
            else right = mid; // find leftmost prefix >= target
        }
        return left;
    }
}
```
