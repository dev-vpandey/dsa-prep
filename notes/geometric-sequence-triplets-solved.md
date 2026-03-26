# Geometric Sequence Triplets — Medium
Problem Link: Custom (similar to AlgoExpert / interview variant)
Solved Date: 2026-03-25
Pattern Tag: two-pointer / math / sorted-array / count-pairs

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like finding three people A, B, C where B earns exactly r times A's salary and C earns exactly r times B's — count all valid triples where the ratio between consecutive elements is constant.

## Core Insight
Sort the array. For each middle element `b`, count how many `a`s satisfy `b = a*r` (i.e., `b % a == 0 && b/a == r`) and how many `c`s satisfy `c = b*r`. Multiply those counts.

## Approach
Sort. For each index `j` (middle of triplet): count `left[j]` = number of elements in `nums[0..j-1]` where `nums[j] % nums[i] == 0 && nums[j]/nums[i] == r`; count `right[j]` = number of elements in `nums[j+1..]` where `nums[k] % nums[j] == 0 && nums[k]/nums[j] == r`. Answer += `left[j] * right[j]`.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Fix middle element                   │ Easier to count valid left and right  │
│                                      │ independently and multiply            │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Check divisibility before ratio      │ Avoids floating point errors —        │
│ (b % a == 0 first, then b/a == r)    │ integer arithmetic only              │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Multiply counts (not add)            │ Each left element pairs with each     │
│                                      │ right element independently          │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
sort(arr)
count = 0
for j in 1..n-2:
    leftCount = 0
    for i in 0..j-1:
        if arr[j] % arr[i] == 0 AND arr[j] / arr[i] == r:
            leftCount++
    rightCount = 0
    for k in j+1..n-1:
        if arr[k] % arr[j] == 0 AND arr[k] / arr[j] == r:
            rightCount++
    count += leftCount * rightCount
return count
```

## Complexity
- Time: O(n²) — for each middle element, scan both sides
- Space: O(1)

## Watch Out For
- Integer division: check `arr[j] % arr[i] == 0` before checking ratio to avoid truncation issues
- Negative numbers or r=1 edge cases
- Can optimize to O(n) with prefix count maps if arr has no duplicates

## Dry Run
`arr = [1, 2, 4, 8]`, r = 2
```
Sort: [1,2,4,8]
j=1(2): left: 2%1==0,2/1==2 → leftCount=1. right: 4%2==0,4/2==2 → rightCount=1. count=1
j=2(4): left: 4%1,4/1≠2; 4%2==0,4/2==2 → leftCount=1. right: 8%4==0,8/4==2 → rightCount=1. count=2
j=3(8): middle at end, no right → rightCount=0
Result: 2 ✓ (triplets: [1,2,4] and [2,4,8])
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int countTriplets(int[] arr, int r) {
        Arrays.sort(arr);
        int count = 0;
        for (int j = 1; j < arr.length - 1; j++) {
            int leftCount = 0, rightCount = 0;
            for (int i = 0; i < j; i++) {
                if (arr[j] % arr[i] == 0 && arr[j] / arr[i] == r) leftCount++;
            }
            for (int k = j + 1; k < arr.length; k++) {
                if (arr[k] % arr[j] == 0 && arr[k] / arr[j] == r) rightCount++;
            }
            count += leftCount * rightCount;
        }
        return count;
    }
}
```
