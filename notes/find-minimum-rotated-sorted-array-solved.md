# Find Minimum in Rotated Sorted Array — Medium
Problem Link: https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
Solved Date: 2026-03-24
Pattern Tag: binary-search / rotated-array / pivot-finding

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like finding the bottom of a hill in a circular mountain range seen from above — the minimum is at the "drop" point where the array value resets to smallest.

## Core Insight
If `nums[mid] > nums[right]`, the minimum is in the right half (rotation point is there). Otherwise it's in the left half (including mid).

## Approach
Binary search where we compare `nums[mid]` with `nums[right]`. If mid > right, go right (min is right of mid). Otherwise go left (min is left of or at mid). When `left == right`, that's the minimum.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Compare mid with right (not left)    │ Comparing with left is ambiguous when │
│                                      │ array is fully sorted (no rotation)   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ nums[mid] > nums[right] → go right   │ Means the array rotates somewhere in │
│                                      │ right half — min is to the right      │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ right = mid (not mid-1) when         │ Mid could BE the minimum — don't      │
│ going left                           │ exclude it                            │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
left = 0, right = n-1
while left < right:
    mid = (left + right) / 2
    if nums[mid] > nums[right]: left = mid + 1   // min in right half
    else: right = mid                             // min in left half (including mid)
return nums[left]
```

## Complexity
- Time: O(log n)
- Space: O(1)

## Watch Out For
- `right = mid` not `mid - 1` (mid could be the answer)
- Loop condition `left < right` (not `<=`) because right = mid can cause infinite loop otherwise
- No rotation case works: always goes left, returns nums[0]

## Dry Run
`[4, 5, 6, 7, 0, 1, 2]`
```
left=0, right=6, mid=3(7): 7>2 → left=4
left=4, right=6, mid=5(1): 1<=2 → right=5
left=4, right=5, mid=4(0): 0<=1 → right=4
left=4, right=4 → exit → nums[4]=0 ✓
```

## Boilerplate Template
```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) left = mid + 1; // min in right half
            else right = mid;                             // mid could be minimum
        }
        return nums[left];
    }
}
```
