# Subarray Sum Equals K — Medium
Link: https://leetcode.com/problems/subarray-sum-equals-k/description/
Solved Date: 2026-03-01

## Core Insight
At each index j, the number of valid subarrays ending at j equals how many
times (currPrefix - k) has appeared as a prefix sum before j.

## Approach
Maintain a running prefix sum and a frequency map of all prefix sums seen so far.
At each position, look up (currPrefix - k) in the map — each past occurrence
represents a valid subarray ending here. Seed the map with {0:1} to handle
subarrays starting from index 0.

## Mental Model
┌─────────────────────────────────┬──────────────────────────────────────────┐
│ Decision                        │ Why                                      │
├─────────────────────────────────┼──────────────────────────────────────────┤
│ Seed map with {0:1}             │ Handles subarrays starting at index 0    │
│                                 │ (prefix[-1] = 0, nothing consumed yet)   │
├─────────────────────────────────┼──────────────────────────────────────────┤
│ Check map BEFORE inserting      │ Prevents a single element matching       │
│ currPrefix                      │ itself (zero-length subarray)            │
├─────────────────────────────────┼──────────────────────────────────────────┤
│ Store frequency, not just       │ Duplicate prefix sums = multiple valid   │
│ existence                       │ subarrays ending at same position        │
├─────────────────────────────────┼──────────────────────────────────────────┤
│ Subarray = stored_index+1 → j   │ Stored index is last index CONSUMED by   │
│                                 │ the prefix — including it double-counts  │
└─────────────────────────────────┴──────────────────────────────────────────┘

## Pseudocode
```
map = {0: 1}
currPrefix = 0, count = 0

for each num in nums:
    currPrefix += num
    pastPrefix = currPrefix - k
    if pastPrefix in map:
        count += map[pastPrefix]
    map[currPrefix]++

return count
```

## Complexity
- Time: O(n)
- Space: O(n)

## Watch Out For
- Forgetting the {0:1} seed → misses subarrays starting at index 0
- Inserting currPrefix before checking → zero-length subarray false match
- Using existence check (containsKey only) instead of frequency → undercounts when same prefix sum appears multiple times

## Dry Run
nums = [1, 2, -1, 1, 2], k = 3

| i | nums[i] | currPrefix | pastPrefix | map hit? | count |
|---|---------|------------|------------|----------|-------|
| - | -       | 0          | -          | seed     | 0     |
| 0 | 1       | 1          | -2         | no       | 0     |
| 1 | 2       | 3          | 0          | yes(1)   | 1     |
| 2 | -1      | 2          | -1         | no       | 1     |
| 3 | 1       | 3          | 0          | yes(1)   | 2     |
| 4 | 2       | 5          | 2          | yes(1)   | 3     |

Key clarification — stored index is the last index CONSUMED by the prefix.
Subarray starts at stored_index+1, not stored_index.
Example: prefix=2 found at index 2 → subarray is nums[3..4]=[1,2]=3, NOT nums[2..4]=[-1,1,2]=2

## Pattern Tag
prefix-sum / hashmap / subarray-sum

## Boilerplate Template
```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0, 1); // seed: empty prefix

    int currPrefix = 0, count = 0;

    for (int num : nums) {
        currPrefix += num;
        count += prefixCount.getOrDefault(currPrefix - k, 0);
        prefixCount.merge(currPrefix, 1, Integer::sum);
    }

    return count;
}
```
