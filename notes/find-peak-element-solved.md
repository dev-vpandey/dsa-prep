# Find Peak Element — Medium
Problem Link: https://leetcode.com/problems/find-peak-element/
Solved Date: 2026-03-24
Pattern Tag: binary-search / peak-finding / neighbor-comparison

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like finding a hilltop in a mountain range — if the slope goes up to the right, there's a peak ahead to the right; if the slope goes up to the left, the peak is to the left (or at mid).

## Core Insight
If `nums[mid] < nums[mid+1]`, the peak must be to the right (the rising slope continues). Otherwise peak is at mid or to the left.

## Approach
`left = 0`, `right = n-1`. At each step: if `nums[mid] < nums[mid+1]`, go right (`left = mid+1`). Else go left (`right = mid`). When `left == right`, that's a peak.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Compare mid with mid+1 only          │ Guaranteed no two adjacent equal      │
│                                      │ elements; just need slope direction   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ right = mid (not mid-1) when         │ Mid could be the peak — don't         │
│ nums[mid] >= nums[mid+1]             │ exclude it                            │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Boundaries treated as -infinity      │ Array "ends" drop off — peaks at      │
│                                      │ edges are valid                       │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
left = 0, right = n-1
while left < right:
    mid = (left + right) / 2
    if nums[mid] < nums[mid+1]: left = mid + 1   // peak to the right
    else: right = mid                             // peak at mid or to left
return left
```

## Complexity
- Time: O(log n)
- Space: O(1)

## Watch Out For
- `right = mid` not `mid-1` (mid might be peak)
- `nums[mid+1]` is safe because `left < right` implies `mid < right < n`
- Can return any valid peak (multiple peaks allowed)

## Dry Run
`nums = [1,2,3,1]`
```
left=0, right=3, mid=1: nums[1]=2 < nums[2]=3 → left=2
left=2, right=3, mid=2: nums[2]=3 > nums[3]=1 → right=2
left=2 = right=2 → return 2 (nums[2]=3 is peak) ✓
```

## Boilerplate Template
```java
class Solution {
    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[mid + 1]) left = mid + 1; // ascending → peak right
            else right = mid;                               // descending → peak at mid or left
        }
        return left;
    }
}
```
