# Longest Repeating Character Replacement — Medium
Problem Link: https://leetcode.com/problems/longest-repeating-character-replacement/
Solved Date: 2026-03-24
Pattern Tag: sliding-window / frequency-map / window-validity

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like editing a stretch of text to make it all one letter using k edits — the window is valid when the characters you'd need to replace (non-dominant ones) is at most k.

## Core Insight
A window is valid when `windowSize - maxFreq <= k`. Expand right freely; when invalid, slide left by 1 (don't shrink aggressively — keep window at max valid size).

## Approach
Maintain `freq[]` array and `maxFreq`. Expand right, update freq and maxFreq. If `(right - left + 1) - maxFreq > k`, slide left by 1 (decrease freq[left] and advance left). Track max window size.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Slide by 1 when invalid, not shrink  │ We never need a shorter window than   │
│ to valid                             │ our current best — only need to grow  │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ maxFreq may be stale (not exact)     │ We only care when maxFreq grows — a   │
│ after shrinking                      │ smaller maxFreq can't give bigger     │
│                                      │ window, so safe to not recompute      │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ windowSize - maxFreq <= k            │ windowSize chars minus the dominant   │
│                                      │ char's count = chars to replace       │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
freq = int[26], maxFreq = 0, left = 0, result = 0
for right in 0..n-1:
    freq[s[right]]++
    maxFreq = max(maxFreq, freq[s[right]])
    if (right - left + 1) - maxFreq > k:
        freq[s[left]]--
        left++
    result = max(result, right - left + 1)
return result
```

## Complexity
- Time: O(n) — right moves n steps, left moves at most n steps total
- Space: O(1) — 26-char frequency array

## Watch Out For
- Don't recompute maxFreq when shrinking — it's safe to let it be stale (only matters when it grows)
- The window size equals `result` at the end — don't separately track it
- `k = 0` case: window must be all same character — still works

## Dry Run
`s = "AABABBA"`, `k = 1`
```
right=0(A): freq{A:1}, maxFreq=1, window=1, 1-1=0<=1 ✓ result=1
right=1(A): freq{A:2}, maxFreq=2, window=2, 2-2=0<=1 ✓ result=2
right=2(B): freq{B:1}, maxFreq=2, window=3, 3-2=1<=1 ✓ result=3
right=3(A): freq{A:3}, maxFreq=3, window=4, 4-3=1<=1 ✓ result=4
right=4(B): freq{B:2}, maxFreq=3, window=5, 5-3=2>1 → slide: freq{A:2}, left=1 result=4
right=5(B): freq{B:3}, maxFreq=3, window=5, 5-3=2>1 → slide: freq{A:1}, left=2 result=4
right=6(A): freq{A:2}, maxFreq=3, window=5, 5-3=2>1 → slide: freq{B:2}, left=3 result=4
Result: 4 ✓ ("AABA" with 1 replacement → "AAAA")
```

## Boilerplate Template
```java
class Solution {
    public int characterReplacement(String s, int k) {
        int[] freq = new int[26];
        int left = 0, maxFreq = 0, result = 0;
        for (int right = 0; right < s.length(); right++) {
            freq[s.charAt(right) - 'A']++;
            maxFreq = Math.max(maxFreq, freq[s.charAt(right) - 'A']);
            // if invalid window, slide left by 1
            if ((right - left + 1) - maxFreq > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
            }
            result = Math.max(result, right - left + 1);
        }
        return result;
    }
}
```
