# Next Permutation — Medium
Problem Link: https://leetcode.com/problems/next-permutation/
Solved Date: 2026-03-24
Pattern Tag: array / in-place / two-phase / descending-suffix

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like incrementing a number in a mixed-base system — find the rightmost digit that can be increased, increase it minimally, then reset everything after it to the smallest possible order (ascending).

## Core Insight
Three steps: (1) Find the rightmost descent point `i` where `nums[i] < nums[i+1]` scanning from right. (2) Find the smallest number to the right of `i` that is larger than `nums[i]` and swap. (3) Reverse the suffix after `i`.

## Approach
Scan from right to find `i` where `nums[i] < nums[i+1]` (first "dip"). If no such `i`, whole array is descending — just reverse. Otherwise: scan from right to find first `j` where `nums[j] > nums[i]`, swap them, then reverse suffix `[i+1..]`.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Find rightmost i where nums[i] <     │ Changing digits further left makes    │
│ nums[i+1]                            │ a much larger jump — must be rightmost│
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Swap with rightmost j > i where      │ Smallest increase to nums[i] —        │
│ nums[j] > nums[i]                    │ find smallest valid replacement       │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Reverse suffix after i               │ Suffix is in descending order after   │
│                                      │ the swap — reverse to get smallest    │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
// Step 1: find rightmost descent
i = n-2
while i >= 0 AND nums[i] >= nums[i+1]: i--

if i >= 0:
    // Step 2: find smallest num > nums[i] to the right
    j = n-1
    while nums[j] <= nums[i]: j--
    swap(nums[i], nums[j])

// Step 3: reverse suffix [i+1..]
reverse(nums, i+1, n-1)
```

## Complexity
- Time: O(n) — three linear passes
- Space: O(1) — in-place

## Watch Out For
- If no descent found (fully descending), `i = -1` — skip step 2, just reverse whole array
- Step 2: scan from RIGHT for `j` (suffix is sorted descending after step 1, so rightmost valid is the smallest valid swap)
- Reverse suffix AFTER the swap (not before)

## Dry Run
`nums = [1,2,3]`
```
i=1: nums[1]=2 >= nums[2]=3? No → i=1
j=2: nums[2]=3 > nums[1]=2 ✓
swap([1,3]): [1,3,2]... wait swap indices 1 and 2: [1,3,2]
reverse [i+1=2..2]: already single → [1,3,2] ✓

nums = [3,2,1]:
i: 3>=2>=1 all descending → i=-1
skip step 2
reverse whole array: [1,2,3] ✓
```

## Boilerplate Template
```java
class Solution {
    public void nextPermutation(int[] nums) {
        int n = nums.length;

        // Step 1: find rightmost descent
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) i--;

        if (i >= 0) {
            // Step 2: find rightmost element > nums[i]
            int j = n - 1;
            while (nums[j] <= nums[i]) j--;
            swap(nums, i, j);
        }

        // Step 3: reverse suffix after i
        reverse(nums, i + 1, n - 1);
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i]; nums[i] = nums[j]; nums[j] = tmp;
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) swap(nums, left++, right--);
    }
}
```
