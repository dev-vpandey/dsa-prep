# Triplet Sum to Zero (3Sum) — Medium
Problem Link: https://leetcode.com/problems/3sum/
Solved Date: 2026-03-24
Pattern Tag: two-pointer / sorting / deduplication / three-sum

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like finding three people in a room whose debts cancel out — sort them by debt, fix one person, then use two pointers to find the pair whose combined debt negates the first.

## Core Insight
Sort first. Fix one element `nums[i]`, then use two-pointer on the rest to find pairs summing to `-nums[i]`. Skip duplicates at both the fix level and pointer level.

## Approach
Sort array. For each `i` from 0 to n-3 (skip duplicates): set `left = i+1`, `right = n-1`. Move pointers based on sum. When sum == 0, record triplet and advance both pointers skipping duplicates.

## Mental Model
```
┌─────────────────────────────────────┬──────────────────────────────────────────┐
│ Decision                            │ Why                                      │
├─────────────────────────────────────┼──────────────────────────────────────────┤
│ Sort first                          │ Enables two-pointer and systematic       │
│                                     │ duplicate skipping                       │
├─────────────────────────────────────┼──────────────────────────────────────────┤
│ Skip i when nums[i] == nums[i-1]    │ Same anchor = same triplets generated    │
│ (i > 0 guard)                       │ → duplicates in result                   │
├─────────────────────────────────────┼──────────────────────────────────────────┤
│ Skip left when sum==0 and           │ Moving to same value gives same triplet  │
│ nums[left] == nums[left-1]          │ → duplicate result                       │
├─────────────────────────────────────┼──────────────────────────────────────────┤
│ Break early if nums[i] > 0          │ Sorted array: if anchor is positive,     │
│                                     │ no two numbers can sum to negative       │
└─────────────────────────────────────┴──────────────────────────────────────────┘
```

## Pseudocode
```
sort(nums)
result = []
for i from 0 to n-3:
    if i > 0 AND nums[i] == nums[i-1]: skip  // skip dup anchors
    if nums[i] > 0: break                     // optimization
    left = i+1, right = n-1
    while left < right:
        sum = nums[i] + nums[left] + nums[right]
        if sum == 0:
            add [nums[i], nums[left], nums[right]] to result
            while left < right AND nums[left] == nums[left+1]: left++   // skip dup left
            while left < right AND nums[right] == nums[right-1]: right-- // skip dup right
            left++, right--
        else if sum < 0: left++
        else: right--
return result
```

## Complexity
- Time: O(n²) — O(n log n) sort + O(n) for each of n anchors
- Space: O(1) excluding output

## Watch Out For
- Duplicate skipping at BOTH anchor level (`i`) and pointer level
- `i > 0` guard before checking `nums[i] == nums[i-1]`
- Skip duplicates AFTER recording the triplet, not before

## Dry Run
`[-1, 0, 1, 2, -1, -4]` → sorted: `[-4, -1, -1, 0, 1, 2]`
```
i=0 (-4): left=1(-1), right=5(2): sum=-3 < 0 → left++
          left=2(-1), right=5(2): sum=-3 < 0 → left++
          left=3(0), right=5(2): sum=-2 < 0 → left++
          left=4(1), right=5(2): sum=-1 < 0 → left++, done
i=1 (-1): left=2(-1), right=5(2): sum=0 → add[-1,-1,2], skip dups, left=3, right=4
          left=3(0), right=4(1): sum=0 → add[-1,0,1], left=4, right=3, done
i=2 (-1): nums[2]==nums[1] → skip
i=3 (0): left=4(1), right=5(2): sum=3 > 0 → right--, done
Result: [[-1,-1,2],[-1,0,1]] ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // skip dup anchor
            if (nums[i] > 0) break;                         // early exit
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    result.add(List.of(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++; right--;
                } else if (sum < 0) left++;
                else right--;
            }
        }
        return result;
    }
}
```
