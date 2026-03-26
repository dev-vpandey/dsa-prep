# Longest Consecutive Sequence — Medium
Problem Link: https://leetcode.com/problems/longest-consecutive-sequence/
Solved Date: 2026-03-24
Pattern Tag: hashset / sequence-start / O(n)

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like finding the longest unbroken chain of integers in a bag of numbers — only start counting a chain from the smallest number in it (the one with no left neighbor in the set).

## Core Insight
Only start counting from a sequence's beginning (when `n-1` is NOT in the set). This ensures each element is counted at most twice total → O(n).

## Approach
Put all numbers in a HashSet. For each number `n`, if `n-1` is NOT in the set (sequence start), count forward (`n, n+1, n+2...`) while the next exists. Track max length.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Only count from sequence starts      │ Without this, nested loops make O(n²);│
│ (n-1 not in set)                     │ each element counted at most twice    │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ HashSet for O(1) lookup              │ Checking n-1 and counting forward     │
│                                      │ requires O(1) contains()             │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Skip if n-1 is in set                │ Not a sequence start — would re-count │
│                                      │ a sequence already covered           │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
set = HashSet(nums)
maxLen = 0

for each num in set:
    if num-1 NOT in set:   // sequence start
        curr = num
        len = 1
        while curr+1 in set:
            curr++
            len++
        maxLen = max(maxLen, len)

return maxLen
```

## Complexity
- Time: O(n) — each number entered/exited the while loop at most once
- Space: O(n) — HashSet

## Watch Out For
- Iterate over the `set` (not the array) to avoid duplicates causing repeated counting
- The O(n) claim is amortized — total inner while loop iterations = n total across all starts
- Handle empty array: maxLen starts at 0 (correct)

## Dry Run
`nums = [100, 4, 200, 1, 3, 2]`
```
set = {100, 4, 200, 1, 3, 2}

100: 99 not in set → count: 100 → len=1, max=1
4:   3 in set → skip
200: 199 not in set → count: 200 → len=1
1:   0 not in set → count: 1,2,3,4 → len=4, max=4
3:   2 in set → skip
2:   1 in set → skip

Result: 4 ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) set.add(n);

        int maxLen = 0;
        for (int n : set) {
            if (!set.contains(n - 1)) { // only start counting from sequence beginning
                int curr = n, len = 1;
                while (set.contains(curr + 1)) {
                    curr++;
                    len++;
                }
                maxLen = Math.max(maxLen, len);
            }
        }
        return maxLen;
    }
}
```
