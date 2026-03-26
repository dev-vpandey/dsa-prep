# Longest Substring Without Repeating Characters — Medium
Problem Link: https://leetcode.com/problems/longest-substring-without-repeating-characters/
Solved Date: 2026-03-24
Pattern Tag: sliding-window / hashmap / two-pointer

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like scanning a conveyor belt collecting unique items — when you see a duplicate, slide the left end past the previous occurrence of that item.

## Core Insight
Expand right until a duplicate is found; when duplicate found, jump `left` directly to one past the previous occurrence of that character (using a map storing last-seen index).

## Approach
HashMap `lastIndex` from char → last seen index. `left = 0`. For each `right`: if char at right was seen at index >= left (inside current window), update left = lastIndex[char] + 1. Update lastIndex. Track max window size.

## Mental Model
```
┌─────────────────────────────────────┬──────────────────────────────────────────┐
│ Decision                            │ Why                                      │
├─────────────────────────────────────┼──────────────────────────────────────────┤
│ left = max(left, lastIndex[c]+1)    │ lastIndex might be BEFORE current window │
│ (not just lastIndex[c]+1)           │ — don't move left backward              │
├─────────────────────────────────────┼──────────────────────────────────────────┤
│ Jump left directly (not slide)      │ More efficient than moving left one by   │
│                                     │ one; map gives exact position to jump to │
├─────────────────────────────────────┼──────────────────────────────────────────┤
│ Update lastIndex always             │ Even after a duplicate detection — need  │
│                                     │ most recent position for future checks   │
└─────────────────────────────────────┴──────────────────────────────────────────┘
```

## Pseudocode
```
lastIndex = {}
left = 0, maxLen = 0
for right in 0..n-1:
    c = s[right]
    if c in lastIndex AND lastIndex[c] >= left:
        left = lastIndex[c] + 1
    lastIndex[c] = right
    maxLen = max(maxLen, right - left + 1)
return maxLen
```

## Complexity
- Time: O(n) — right pointer moves n times, left jumps but never moves backward
- Space: O(min(n, alphabet)) — map size bounded by character set

## Watch Out For
- `left = max(left, lastIndex[c]+1)` — crucial to not move left backward
- Characters outside current window can exist in `lastIndex` — the `>= left` check handles this
- Empty string → returns 0 (correct)

## Dry Run
`s = "abcabcbb"`
```
right=0(a): lastIndex{a:0}, left=0, max=1
right=1(b): lastIndex{b:1}, left=0, max=2
right=2(c): lastIndex{c:2}, left=0, max=3
right=3(a): a in map, lastIndex[a]=0 >= left=0 → left=1. lastIndex{a:3}, max=3
right=4(b): b in map, lastIndex[b]=1 >= left=1 → left=2. lastIndex{b:4}, max=3
right=5(c): c in map, lastIndex[c]=2 >= left=2 → left=3. lastIndex{c:5}, max=3
right=6(b): b in map, lastIndex[b]=4 >= left=3 → left=5. max=2
right=7(b): b in map, lastIndex[b]=6 >= left=5 → left=7. max=1
Result: 3 ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastIndex = new HashMap<>();
        int left = 0, maxLen = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (lastIndex.containsKey(c) && lastIndex.get(c) >= left) {
                left = lastIndex.get(c) + 1; // jump past the duplicate
            }
            lastIndex.put(c, right);
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
}
```
