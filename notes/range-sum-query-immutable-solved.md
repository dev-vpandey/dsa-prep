# Range Sum Query - Immutable — Easy
Problem Link: https://leetcode.com/problems/range-sum-query-immutable/
Solved Date: 2026-03-25
Pattern Tag: prefix-sum / design / O(1)-query

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like a running odometer — precompute cumulative mileage at each city. To find distance between city A and city B, just subtract their odometer readings.

## Core Insight
Build a prefix sum array where `prefix[i] = sum of nums[0..i-1]`. Query `[l, r]` = `prefix[r+1] - prefix[l]`. O(1) per query after O(n) build.

## Approach
Constructor: build `prefix[]` of size `n+1`. `prefix[0] = 0`, `prefix[i] = prefix[i-1] + nums[i-1]`. `sumRange(l, r)` = `prefix[r+1] - prefix[l]`.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ prefix size = n+1 (not n)            │ prefix[0]=0 as sentinel; avoids       │
│                                      │ special case when l=0                 │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ sumRange = prefix[r+1] - prefix[l]   │ prefix[r+1] = sum[0..r],              │
│                                      │ prefix[l] = sum[0..l-1]               │
│                                      │ difference = sum[l..r]                │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
build: prefix[0]=0; prefix[i] = prefix[i-1] + nums[i-1] for i in 1..n
query(l, r): return prefix[r+1] - prefix[l]
```

## Complexity
- Time: O(n) build, O(1) query
- Space: O(n) — prefix array

## Watch Out For
- `prefix[r+1] - prefix[l]` not `prefix[r] - prefix[l-1]` (off-by-one)
- prefix array is size n+1 to handle l=0 queries cleanly

## Dry Run
`nums = [−2, 0, 3, −5, 2, −1]`
```
prefix = [0, −2, −2, 1, −4, −2, −3]
sumRange(0,2) = prefix[3] - prefix[0] = 1 - 0 = 1 ✓
sumRange(2,5) = prefix[6] - prefix[2] = −3 − (−2) = −1 ✓
```

## Boilerplate Template
```java
class NumArray {
    private final int[] prefix;

    public NumArray(int[] nums) {
        prefix = new int[nums.length + 1]; // prefix[0] = 0 sentinel
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        return prefix[right + 1] - prefix[left];
    }
}
```
