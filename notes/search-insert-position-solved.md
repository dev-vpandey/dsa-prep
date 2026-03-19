# Search Insert Position — Easy
Link: https://leetcode.com/problems/search-insert-position/
Solved Date: 2026-03-02
Review Date: 2026-03-09

## Core Insight
Binary search for the **lower bound** — the leftmost position where target fits.

## Approach
Use binary search with `right = nums.length` to account for insertion at the end.
When `mid >= target`, set `right = midIdx` (not midIdx-1) because mid could be the answer.
When `mid < target`, set `left = midIdx + 1` because mid is definitely too small.
Loop ends when `left == right` — that's the insertion index.

## Mental Model
┌─────────────────────┬────────────────────────┬──────────────────────────────────────┐
│ Decision            │ Move                   │ Why                                  │
├─────────────────────┼────────────────────────┼──────────────────────────────────────┤
│ mid < target        │ left = midIdx + 1      │ mid is too small, can't be answer    │
│ mid >= target       │ right = midIdx         │ mid could be the answer, keep it     │
│ right init          │ nums.length (not n-1)  │ target may insert past last element  │
│ loop condition      │ left < right           │ terminates when window collapses     │
│ return              │ left (== right)        │ insertion point when loop ends       │
└─────────────────────┴────────────────────────┴──────────────────────────────────────┘

## Pseudocode
```
left = 0, right = nums.length
while left < right:
    mid = left + (right - left) / 2
    if nums[mid] < target:
        left = mid + 1
    else:
        right = mid
return left
```

## Complexity
- Time: O(log n) — halve the search space each iteration
- Space: O(1) — only pointers, no extra memory

## Watch Out For
- `right = nums.length`, NOT `nums.length - 1` — target could go at the very end
- `right = midIdx` NOT `midIdx - 1` — mid is a candidate when `mid >= target`
- Loop is `left < right`, not `left <= right` — avoids infinite loop with this pattern

## Dry Run
nums = [1, 3, 5, 6], target = 2

Iteration 1: midIdx=2, nums[2]=5 ≥ 2 → right=2   | left=0, right=2
Iteration 2: midIdx=1, nums[1]=3 ≥ 2 → right=1   | left=0, right=1
Iteration 3: midIdx=0, nums[0]=1 < 2 → left=1    | left=1, right=1
Loop ends → return 1 ✓ (2 inserts between index 0 and 1)

## Pattern Tag
binary-search / lower-bound / sorted-array

## Boilerplate Template
```java
// Lower-bound binary search template
// Use when: sorted array, find index of target OR insertion point
class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length; // right = length for insertion-at-end case

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;   // mid too small, exclude it
            } else {
                right = mid;      // mid could be answer, keep it
            }
        }
        return left; // left == right == insertion point
    }
}
```
