# Move Zeroes — Easy
Problem Link: https://leetcode.com/problems/move-zeroes/
Solved Date: 2026-03-24
Pattern Tag: two-pointer / array / in-place

## SRS Tracking
- Stage: 5
- Review Date: 2026-04-23
- Last Rating: Strong
- Review Count: 8
- Graduated: No

---

# Real World Analogy
Like sliding all the empty seats in a row to the end — you shift every occupied seat forward without changing their relative order, and all the empty slots naturally fill the back.

## Core Insight
Use a slow pointer `insertPos` to mark where the next non-zero goes; walk a fast pointer through the array swapping non-zeros into position.

## Approach
Walk `right` from 0 to end. Whenever `nums[right] != 0`, swap it with `nums[left]` and advance `left`. This preserves relative order of non-zeros and pushes all zeros to the back in a single pass.

## Mental Model
```
┌─────────────────────────────────┬──────────────────────────────────────────────┐
│ Decision                        │ Why                                          │
├─────────────────────────────────┼──────────────────────────────────────────────┤
│ Swap, don't overwrite           │ Overwriting loses the zero; swap moves it    │
│                                 │ to the right side naturally                  │
├─────────────────────────────────┼──────────────────────────────────────────────┤
│ left only advances on non-zero  │ left tracks the "next empty slot" — only     │
│                                 │ fill it when you have a non-zero to place    │
├─────────────────────────────────┼──────────────────────────────────────────────┤
│ Single pass                     │ right scans everything; left only moves      │
│                                 │ when useful — O(n) guaranteed                │
├─────────────────────────────────┼──────────────────────────────────────────────┤
│ Relative order preserved        │ left never overtakes right; non-zeros are    │
│                                 │ placed in the order they appear              │
└─────────────────────────────────┴──────────────────────────────────────────────┘
```

## Pseudocode
```
left = 0
for right in 0..n-1:
    if nums[right] != 0:
        swap(nums[left], nums[right])
        left++
```

## Complexity
- Time: O(n) — single pass with two pointers
- Space: O(1) — in-place, no extra space

## Watch Out For
- Don't use a separate zero-fill pass — swapping handles it in one pass
- Input `[0]` or `[1]` (all zeros / no zeros) — both work correctly
- Swapping an element with itself (when left == right) is harmless

## Dry Run
Input: `[0, 1, 0, 3, 12]`
```
left=0, right=0: nums[0]=0, skip
left=0, right=1: nums[1]=1 ≠ 0 → swap([0],[1]) → [1,0,0,3,12], left=1
left=1, right=2: nums[2]=0, skip
left=1, right=3: nums[3]=3 ≠ 0 → swap([1],[3]) → [1,3,0,0,12], left=2
left=2, right=4: nums[4]=12 ≠ 0 → swap([2],[4]) → [1,3,12,0,0], left=3
Result: [1, 3, 12, 0, 0] ✓
```

## Boilerplate Template
```java
class Solution {
    public void moveZeroes(int[] nums) {
        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] != 0) {
                int tmp = nums[left];
                nums[left] = nums[right];
                nums[right] = tmp;
                left++;
            }
        }
    }
}
```
