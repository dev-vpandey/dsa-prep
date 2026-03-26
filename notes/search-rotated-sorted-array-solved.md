# Search in Rotated Sorted Array — Medium
Problem Link: https://leetcode.com/problems/search-in-rotated-sorted-array/
Solved Date: 2026-03-24
Pattern Tag: binary-search / rotated-array / sorted-half-identification

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like searching a spiral notebook where pages are out of order — one half is always in sequence, so determine which half is ordered and decide which side the target could be on.

## Core Insight
One of the two halves is always sorted. Identify which half is sorted, then check if target lies in that half. Binary search accordingly.

## Approach
Standard binary search template. At each step: check if left half `[left..mid]` is sorted (nums[left] <= nums[mid]). If target is in that sorted range, go left; else go right. Otherwise right half `[mid..right]` is sorted — apply same logic.

## Mental Model
```
┌────────────────────────────────────────┬─────────────────────────────────────┐
│ Decision                               │ Why                                 │
├────────────────────────────────────────┼─────────────────────────────────────┤
│ Check nums[left] <= nums[mid]          │ Determines which half is sorted     │
│ to identify sorted half                │ without needing to find pivot first │
├────────────────────────────────────────┼─────────────────────────────────────┤
│ target >= nums[left] AND              │ Both bounds needed to confirm target │
│ target < nums[mid]                    │ is IN the sorted left half           │
├────────────────────────────────────────┼─────────────────────────────────────┤
│ Same logic for right half             │ If left half not sorted, right half  │
│ with symmetric conditions             │ must be sorted — check target in it  │
└────────────────────────────────────────┴─────────────────────────────────────┘
```

## Pseudocode
```
left = 0, right = n-1
while left <= right:
    mid = (left + right) / 2
    if nums[mid] == target: return mid

    if nums[left] <= nums[mid]:  // left half is sorted
        if nums[left] <= target < nums[mid]: right = mid - 1
        else: left = mid + 1
    else:                         // right half is sorted
        if nums[mid] < target <= nums[right]: left = mid + 1
        else: right = mid - 1

return -1
```

## Complexity
- Time: O(log n)
- Space: O(1)

## Watch Out For
- Use `<=` in `nums[left] <= nums[mid]` (handles single element case where left==mid)
- Both bounds must be checked when testing if target is in sorted half
- Array with no rotation still works correctly (left half always sorted)

## Dry Run
`nums = [4,5,6,7,0,1,2]`, target = 0
```
left=0, right=6, mid=3(7): nums[0]=4 <= nums[3]=7 → left sorted
  target=0 not in [4..7) → right → left=4
left=4, right=6, mid=5(1): nums[4]=0 <= nums[5]=1 → left sorted
  target=0 in [0..1) → right=4
left=4, right=4, mid=4(0): nums[4]==0 → return 4 ✓
```

## Boilerplate Template
```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;

            if (nums[left] <= nums[mid]) { // left half sorted
                if (nums[left] <= target && target < nums[mid]) right = mid - 1;
                else left = mid + 1;
            } else { // right half sorted
                if (nums[mid] < target && target <= nums[right]) left = mid + 1;
                else right = mid - 1;
            }
        }
        return -1;
    }
}
```
