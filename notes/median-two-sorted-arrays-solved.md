# Median of Two Sorted Arrays — Hard
Problem Link: https://leetcode.com/problems/median-of-two-sorted-arrays/
Solved Date: 2026-03-25
Pattern Tag: binary-search / partition / O(log(min(m,n)))

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like finding the dividing line between two sorted groups that splits them equally — binary search on one group to find where the partition point falls, then verify the other partition is consistent.

## Core Insight
Binary search on the smaller array to find partition index `i` such that all elements on the left of both partitions (`A[0..i-1]` and `B[0..j-1]`) are less than all elements on the right. Then median is computed from the 4 boundary values.

## Approach
Binary search on smaller array (say `nums1`). Partition: `i` elements from `nums1` left, `j = (m+n+1)/2 - i` from `nums2` left. Valid partition when `nums1[i-1] <= nums2[j]` and `nums2[j-1] <= nums1[i]`. Adjust binary search based on violation.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Binary search on SMALLER array       │ Keeps binary search to O(log min(m,n))│
├──────────────────────────────────────┼───────────────────────────────────────┤
│ j = (m+n+1)/2 - i                    │ Left half must have (m+n+1)/2 elems   │
│                                      │ total; i from A means j from B        │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Use +INF/-INF for boundary values    │ When i=0 or i=m (partition at edges), │
│ (when i=0 or i=m)                    │ no left/right element exists          │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Median = max(leftA,leftB) if odd     │ Left half is bigger by 1 for odd      │
│ or avg of max(left),min(right) if even│ total count                          │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
ensure nums1 is the shorter array
m = nums1.length, n = nums2.length
left=0, right=m
while left <= right:
    i = (left + right) / 2         // partition in nums1
    j = (m + n + 1) / 2 - i       // partition in nums2

    maxLeftA = i==0 ? -INF : nums1[i-1]
    minRightA = i==m ? +INF : nums1[i]
    maxLeftB = j==0 ? -INF : nums2[j-1]
    minRightB = j==n ? +INF : nums2[j]

    if maxLeftA <= minRightB AND maxLeftB <= minRightA:
        // correct partition
        if (m+n) % 2 == 1: return max(maxLeftA, maxLeftB)
        else: return (max(maxLeftA,maxLeftB) + min(minRightA,minRightB)) / 2.0
    elif maxLeftA > minRightB: right = i - 1
    else: left = i + 1
```

## Complexity
- Time: O(log(min(m, n)))
- Space: O(1)

## Watch Out For
- Always binary search on the SHORTER array — swap if needed
- Use `Integer.MIN_VALUE` / `Integer.MAX_VALUE` for out-of-bounds sentinel
- `j = (m+n+1)/2 - i` uses `+1` to handle both odd and even total lengths
- Condition: `maxLeftA <= minRightB` AND `maxLeftB <= minRightA`

## Dry Run
`A=[1,3]`, `B=[2]` → sorted: [1,2,3], median=2
```
m=2, n=1 (swap to put shorter first if needed)
left=0, right=1
i=1, j=(2+1+1)/2-1=1
maxLeftA=A[0]=1, minRightA=A[1]=3
maxLeftB=B[0]=2, minRightB=+INF
1<=INF ✓, 2<=3 ✓ → partition found
(2+1)%2=1 (odd) → return max(1,2)=2 ✓
```

## Boilerplate Template
```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);
        int m = nums1.length, n = nums2.length;
        int left = 0, right = m;

        while (left <= right) {
            int i = (left + right) / 2;
            int j = (m + n + 1) / 2 - i;

            int maxLeftA  = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int minRightA = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int maxLeftB  = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int minRightB = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (maxLeftA <= minRightB && maxLeftB <= minRightA) {
                if ((m + n) % 2 == 1) return Math.max(maxLeftA, maxLeftB);
                return (Math.max(maxLeftA, maxLeftB) + Math.min(minRightA, minRightB)) / 2.0;
            } else if (maxLeftA > minRightB) right = i - 1;
            else left = i + 1;
        }
        return -1;
    }
}
```
