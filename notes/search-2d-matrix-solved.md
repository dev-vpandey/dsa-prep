# Search a 2D Matrix — Medium
Problem Link: https://leetcode.com/problems/search-a-2d-matrix/
Solved Date: 2026-03-24
Pattern Tag: binary-search / 2d-matrix / index-mapping

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like looking up a word in a multi-page sorted dictionary where each page continues from the last — you can treat all pages as one long sorted list and binary search it.

## Core Insight
Treat the m×n matrix as a flat sorted array of length m*n. Map virtual index `mid` to `row = mid / cols`, `col = mid % cols`.

## Approach
Binary search from `left = 0` to `right = m*n - 1`. At each step: `mid` maps to `matrix[mid/n][mid%n]`. Standard binary search comparisons from there.

## Mental Model
```
┌─────────────────────────────────┬──────────────────────────────────────────────┐
│ Decision                        │ Why                                          │
├─────────────────────────────────┼──────────────────────────────────────────────┤
│ row = mid / cols                │ Integer division gives which row the flat    │
│ col = mid % cols                │ index falls in; remainder gives column       │
├─────────────────────────────────┼──────────────────────────────────────────────┤
│ right = m*n - 1 (not m*n)       │ 0-indexed: valid indices are 0 to m*n-1    │
└─────────────────────────────────┴──────────────────────────────────────────────┘
```

## Pseudocode
```
left = 0, right = m*n - 1
while left <= right:
    mid = (left + right) / 2
    val = matrix[mid/cols][mid%cols]
    if val == target: return true
    elif val < target: left = mid + 1
    else: right = mid - 1
return false
```

## Complexity
- Time: O(log(m*n))
- Space: O(1)

## Watch Out For
- `right = m*n - 1` not `m*n`
- Distinguish this problem (each row continues from last) from LC 240 (each row/col sorted independently)

## Dry Run
`matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]]`, target = 3
```
m=3, n=4, left=0, right=11
mid=5 → matrix[5/4][5%4] = matrix[1][1] = 11 > 3 → right=4
mid=2 → matrix[2/4][2%4] = matrix[0][2] = 5 > 3 → right=1
mid=0 → matrix[0][0] = 1 < 3 → left=1
mid=1 → matrix[0][1] = 3 == 3 → return true ✓
```

## Boilerplate Template
```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int left = 0, right = m * n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int val = matrix[mid / n][mid % n]; // map flat index to 2D
            if (val == target) return true;
            else if (val < target) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }
}
```
