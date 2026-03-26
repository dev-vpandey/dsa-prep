# 3Sum Closest — Medium
Problem Link: [https://leetcode.com/problems/3sum-closest/description/](https://leetcode.com/problems/3sum-closest/description/)
Solved Date: 2026-03-02
Pattern Tag: two-pointer / sorting / three-sum
Review Date: 2026-03-09

## SRS Tracking
- Stage: 1
- Review Date: 2026-03-23
- Last Rating: —
- Review Count: 0
- Graduated: No

---

# Real World Analogy
—

## Core Insight
Sort the array, fix one element, then use two pointers to greedily shrink the search space while tracking the closest sum seen.

## Approach
Sort the array. For each index `i`, run a two-pointer scan on the remaining right subarray. At each step, compute the triplet sum and update the result if it's closer to target than the current best. Move pointers based on whether the sum is too small or too large.

## Mental Model
```
┌─────────────────────┬──────────────────────────────────────────────┐
│ Decision            │ Why                                          │
├─────────────────────┼──────────────────────────────────────────────┤
│ Sort first          │ Enables two-pointer: predictable direction   │
│                     │ to move when sum is too big or too small     │
├─────────────────────┼──────────────────────────────────────────────┤
│ Seed res with a     │ Avoids silent corruption — res=0 could       │
│ real triplet sum    │ incorrectly beat actual triplets in          │
│ nums[0]+nums[1]     │ closeness comparison before loop runs        │
│ +nums[2]            │                                              │
├─────────────────────┼──────────────────────────────────────────────┤
│ sum < target →      │ Array is sorted, so moving left ptr right    │
│ left++              │ increases the sum                            │
├─────────────────────┼──────────────────────────────────────────────┤
│ sum > target →      │ Moving right ptr left decreases the sum      │
│ right--             │                                              │
├─────────────────────┼──────────────────────────────────────────────┤
│ return res,         │ sum holds only the LAST triplet computed;    │
│ not sum             │ res holds the closest one seen across all    │
└─────────────────────┴──────────────────────────────────────────────┘
```

## Pseudocode
```
sort(nums)
res = nums[0] + nums[1] + nums[2]

for i from 0 to n-3:
    left = i+1, right = n-1
    while left < right:
        sum = nums[i] + nums[left] + nums[right]
        if |target - sum| < |target - res|:
            res = sum
        if sum == target: return res
        else if sum < target: left++
        else: right--

return res
```

## Complexity
- Time: O(n²) — O(n log n) sort + O(n²) two-pointer scan
- Space: O(1) — no extra data structures

## Watch Out For
- Seeding `res = 0` silently corrupts results when 0 is not a real triplet sum
- Returning `sum` instead of `res` — `sum` is only the last computed triplet
- Loop bound must be `i < nums.length - 2` to leave room for left and right pointers

## Dry Run
```
nums = [-1, 2, 1, -4], target = 1
After sort: [-4, -1, 1, 2]
res = -4 + -1 + 1 = -4  (seed)

i=0 (num=-4), left=1, right=3
  sum = -4 + -1 + 2 = -3  |  |-3-1|=4 < |-4-1|=5  → res=-3, sum<target, left++
  sum = -4 +  1 + 2 = -1  |  |-1-1|=2 < |-3-1|=4  → res=-1, sum<target, left++
  left >= right → exit

i=1 (num=-1), left=2, right=3
  sum = -1 + 1 + 2 = 2    |  |2-1|=1  < |-1-1|=2  → res=2,  sum>target, right--
  left >= right → exit

return 2 ✅
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int threeSum_____(int[] nums, int target) {
        Arrays.sort(nums);
        int res = nums[0] + nums[1] + nums[2]; // seed with real triplet

        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1, right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (/* update condition */) res = sum;

                if (sum == target) return res;
                else if (sum < target) left++;
                else right--;
            }
        }

        return res;
    }
}
```
