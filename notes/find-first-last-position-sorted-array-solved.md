# Find First and Last Position of Element in Sorted Array — Medium
Problem Link: https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
Solved Date: 2026-03-24
Pattern Tag: binary-search / lower-bound / upper-bound / two-pass

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like finding the first and last page of a chapter in a book — binary search to the first occurrence (leftmost), then binary search to the last (rightmost).

## Core Insight
Two separate binary searches: one for the leftmost occurrence (when found, keep searching left) and one for the rightmost (when found, keep searching right).

## Approach
`findFirst`: standard binary search; when `nums[mid] == target`, record `mid` as candidate and set `right = mid - 1`. `findLast`: when equal, record and set `left = mid + 1`. Return `[-1,-1]` if first == -1.

## Mental Model
```
┌────────────────────────────────────────┬─────────────────────────────────────┐
│ Decision                               │ Why                                 │
├────────────────────────────────────────┼─────────────────────────────────────┤
│ Save candidate and continue on match   │ Don't stop at first match — keep    │
│                                        │ searching that direction             │
├────────────────────────────────────────┼─────────────────────────────────────┤
│ Two separate passes                    │ Cleaner than a single pass that      │
│                                        │ tries to find both simultaneously    │
├────────────────────────────────────────┼─────────────────────────────────────┤
│ Return [-1,-1] if first == -1          │ If target not found, skip second     │
│                                        │ search entirely                      │
└────────────────────────────────────────┴─────────────────────────────────────┘
```

## Pseudocode
```
findFirst(nums, target):
    left=0, right=n-1, result=-1
    while left <= right:
        mid = (l+r)/2
        if nums[mid] == target: result=mid; right=mid-1   // go left
        elif nums[mid] < target: left=mid+1
        else: right=mid-1
    return result

findLast(nums, target):
    left=0, right=n-1, result=-1
    while left <= right:
        mid = (l+r)/2
        if nums[mid] == target: result=mid; left=mid+1    // go right
        elif nums[mid] < target: left=mid+1
        else: right=mid-1
    return result
```

## Complexity
- Time: O(log n) — two binary searches
- Space: O(1)

## Watch Out For
- Don't return immediately on match — save and continue searching
- `right = mid - 1` for leftmost, `left = mid + 1` for rightmost
- Check if target exists at all (first == -1) before running second search

## Dry Run
`nums = [5,7,7,8,8,10]`, target = 8
```
findFirst: l=0,r=5→mid=2(7)<8→l=3; mid=4(8)=8→result=4,r=3; mid=3(8)=8→result=3,r=2; exit → 3
findLast: l=0,r=5→mid=2(7)<8→l=3; mid=4(8)=8→result=4,l=5; mid=5(10)>8→r=4; exit → 4
Result: [3, 4] ✓
```

## Boilerplate Template
```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int first = findBound(nums, target, true);
        if (first == -1) return new int[]{-1, -1};
        int last = findBound(nums, target, false);
        return new int[]{first, last};
    }

    private int findBound(int[] nums, int target, boolean findFirst) {
        int left = 0, right = nums.length - 1, result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                result = mid;
                if (findFirst) right = mid - 1; // search left for first occurrence
                else left = mid + 1;             // search right for last occurrence
            } else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return result;
    }
}
```
