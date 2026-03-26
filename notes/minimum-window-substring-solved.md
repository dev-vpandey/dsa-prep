# Minimum Window Substring — Hard
Problem Link: [https://leetcode.com/problems/minimum-window-substring/](https://leetcode.com/problems/minimum-window-substring/)
Solved Date: 2026-03-01
Pattern Tag: sliding-window / two-pointer / frequency-map
Review Date: 2026-03-08

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-30
- Last Rating: Strong
- Review Count: 1
- Graduated: No

---

# Real World Analogy
—

## Core Insight
Use two pointers to maintain a window; expand right to make it valid, shrink left to make it minimal — but only track `formed` when a character's **count** (not just presence) satisfies the requirement.

## Approach
Build a frequency map of `t`. Expand `right` until all characters are frequency-satisfied (`formed == need`). Then shrink `left` to find the smallest valid window. Record `minStart` only when the window actually improves — `minStart` and `minLength` must always describe the same window.

## Mental Model

```
┌──────────────────────────────────┬─────────────────────────────────────────────────┐
│ Decision                         │ Why                                             │
├──────────────────────────────────┼─────────────────────────────────────────────────┤
│ need = tmap.size()               │ Count distinct chars, not t.length().           │
│ (not t.length())                 │ t="AAB" → need=2, not 3                         │
├──────────────────────────────────┼─────────────────────────────────────────────────┤
│ formed++ only when               │ Need ALL required copies of a char,             │
│ smap[c] == tmap[c]               │ not just the first occurrence                   │
├──────────────────────────────────┼─────────────────────────────────────────────────┤
│ formed-- only when               │ Removing left char drops its window count       │
│ smap[c] < tmap[c] after remove   │ below required → window is no longer valid.     │
│                                  │ Extra copies (smap > tmap) can be safely lost   │
├──────────────────────────────────┼─────────────────────────────────────────────────┤
│ minStart updates only when       │ minLength and minStart must point to the         │
│ windowSize < minLength           │ SAME window — decoupling returns a wrong window │
├──────────────────────────────────┼─────────────────────────────────────────────────┤
│ Shrink from left when valid      │ Window is already valid — eliminate left chars   │
│ (formed == need)                 │ to find the smallest valid window               │
└──────────────────────────────────┴─────────────────────────────────────────────────┘
```

## Pseudocode
```
build tmap (char → freq) from t
need = tmap.size(), formed = 0, left = 0
minLength = MAX, minStart = 0

for each right in 0..s.length-1:
    add s[right] to smap
    if s[right] in tmap AND smap[s[right]] == tmap[s[right]]:
        formed++

    while formed == need:
        if (right - left + 1) < minLength:
            minLength = right - left + 1
            minStart = left

        remove s[left] from smap
        if s[left] in tmap AND smap[s[left]] < tmap[s[left]]:
            formed--
        left++

return minLength == MAX ? "" : s[minStart .. minStart + minLength]
```

## Complexity
- Time: O(|s| + |t|) — right and left each traverse s at most once; tmap build is O(|t|)
- Space: O(|s| + |t|) — bounded by alphabet size in practice (O(1) for fixed alphabet)

## Watch Out For
- `minStart` and `minLength` **must update together** — only inside `if (windowSize < minLength)`
- `formed++` only when count **exactly matches**, not just when char appears
- `formed--` only when removing left char drops count **below** required (extra copies are safe to lose)
- `need = tmap.size()` — distinct char count, not `t.length()`
- Return `""` when no window found (`minLength == Integer.MAX_VALUE`)

## Dry Run
s = `"ADOBECODEBANC"`, t = `"ABC"`

```
tmap = {A:1, B:1, C:1}, need = 3

Expand phase (right moves →):
right=0  A → smap{A:1}  A satisfies → formed=1
right=1  D → smap{A:1,D:1}
right=2  O → smap{...,O:1}
right=3  B → smap{...,B:1}  B satisfies → formed=2
right=4  E → ...
right=5  C → smap{...,C:1}  C satisfies → formed=3 ✓

Shrink phase (left moves →):
[0..5] "ADOBEC" len=6  → 6 < MAX ✓ → minLength=6, minStart=0
  remove A → smap{A:0} → 0 < tmap{A:1} → formed=2, left=1  exit shrink

Expand to right=10 (second A found):
right=10 A → smap{A:1} → formed=3 ✓

Shrink phase:
[1..10] "DOBECODEBA" len=10 → 10 > 6, no update
  remove D, O, B, E, C, O, D, E (not in tmap or count still ok)
  [9..10] "BA" → remove B → smap{B:0} → 0 < tmap{B:1} → formed=2, left=10  exit shrink

Expand to right=12 (C found):
right=12 C → formed=3 ✓

Shrink phase:
[10..12] "ANC" — wait, A:1,N:1,C:1... B missing → formed would not be 3 here
Correct: left=6 after previous shrink
[6..12] "ODEBANC" len=7 → 7 > 6, no update
  remove O, D, E (not in tmap)
  [9..12] "BANC" len=4 → 4 < 6 ✓ → minLength=4, minStart=9
  remove B → smap{B:0} → 0 < tmap{B:1} → formed=2, left=10  exit shrink

Result: s.substring(9, 9+4) = "BANC" ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public String minWindow(String s, String t) {
        Map<Character, Integer> tmap = new HashMap<>();
        for (var c : t.toCharArray()) tmap.merge(c, 1, Integer::sum);

        int need = tmap.size(), formed = 0;
        int left = 0, minStart = 0, minLength = Integer.MAX_VALUE;
        Map<Character, Integer> smap = new HashMap<>();

        for (int right = 0; right < s.length(); right++) {
            char rc = s.charAt(right);
            smap.merge(rc, 1, Integer::sum);
            if (tmap.containsKey(rc) && tmap.get(rc).equals(smap.get(rc)))
                formed++;

            while (formed == need) {
                int windowSize = right - left + 1;
                if (windowSize < minLength) {
                    minLength = windowSize;
                    minStart = left;
                }
                char lc = s.charAt(left);
                smap.merge(lc, -1, Integer::sum);
                if (tmap.containsKey(lc) && smap.get(lc) < tmap.get(lc))
                    formed--;
                left++;
            }
        }

        return minLength == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLength);
    }
}
```
