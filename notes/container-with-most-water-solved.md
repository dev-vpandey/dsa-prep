# Container With Most Water — Medium
Problem Link: https://leetcode.com/problems/container-with-most-water/
Solved Date: 2026-03-24
Pattern Tag: two-pointer / greedy / array

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Two people holding up the ends of a tarp to catch rain — the water is limited by the shorter person. To potentially catch more, the shorter person steps inward (moving the taller one can only make things worse).

## Core Insight
Always move the pointer with the shorter height inward — moving the taller pointer can never increase the area (height is already bounded by the shorter side).

## Approach
Two pointers at both ends. Area = min(height[left], height[right]) * (right - left). Track max. Move the pointer with the smaller height inward. Repeat until pointers meet.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Move the shorter pointer             │ Width always decreases when you move  │
│                                      │ inward. Only chance to increase area  │
│                                      │ is finding a taller line on that side │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Area = min(h[l],h[r]) * (r - l)     │ Water height is capped at the shorter │
│                                      │ wall; width = distance between walls  │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ If heights equal, move either        │ Doesn't matter — both sides bounded   │
│                                      │ by same height; try both directions   │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
left = 0, right = n-1, maxArea = 0
while left < right:
    area = min(height[left], height[right]) * (right - left)
    maxArea = max(maxArea, area)
    if height[left] < height[right]: left++
    else: right--
return maxArea
```

## Complexity
- Time: O(n) — each pointer moves at most n times total
- Space: O(1)

## Watch Out For
- Move the SHORTER pointer, not the taller one
- Compute area before moving pointers
- Don't compare `height[left]` and `height[right]` after moving

## Dry Run
`height = [1, 8, 6, 2, 5, 4, 8, 3, 7]`
```
left=0(1), right=8(7): area = min(1,7)*8 = 8. h[0]<h[8] → left++
left=1(8), right=8(7): area = min(8,7)*7 = 49. h[8]<h[1] → right--
left=1(8), right=7(3): area = min(8,3)*6 = 18. h[7]<h[1] → right--
...
max = 49 ✓
```

## Boilerplate Template
```java
class Solution {
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1, maxArea = 0;
        while (left < right) {
            int area = Math.min(height[left], height[right]) * (right - left);
            maxArea = Math.max(maxArea, area);
            if (height[left] < height[right]) left++;
            else right--;
        }
        return maxArea;
    }
}
```
