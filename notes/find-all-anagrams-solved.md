# Find All Anagrams in a String — Medium
Problem Link: https://leetcode.com/problems/find-all-anagrams-in-a-string/
Solved Date: 2026-03-24
Pattern Tag: sliding-window / frequency-map / fixed-window

## SRS Tracking
- Stage: 4
- Review Date: 2026-05-02
- Last Rating: Weak
- Review Count: 5
- Graduated: No

---

# Real World Analogy
Like sliding a template cutout over a document looking for spots where all the letters in the template appear — window is valid when all character frequencies match exactly.

## Core Insight
Fixed window of size `p.length()`. Track `matched` — number of characters whose frequency exactly matches `p`'s frequency. When `matched == p's unique chars`, record position.

## Approach
Build `pFreq` from p. Slide a window of size `p.length()` over s: add right char (increment `matched` when freq matches), remove left char when window overflows (decrement `matched` when freq drops below). Record index when `matched == need`.

## Mental Model
```
┌────────────────────────────────────┬─────────────────────────────────────────┐
│ Decision                           │ Why                                     │
├────────────────────────────────────┼─────────────────────────────────────────┤
│ matched++ when sFreq[c] == pFreq[c]│ Only count a char as satisfied when its │
│                                    │ exact required count is reached          │
├────────────────────────────────────┼─────────────────────────────────────────┤
│ matched-- when sFreq[c] == pFreq[c]│ Before decrementing, check if we were   │
│ BEFORE decrement on removal        │ exactly matched — removing drops below  │
├────────────────────────────────────┼─────────────────────────────────────────┤
│ need = pFreq distinct char count   │ matched must equal number of unique     │
│                                    │ chars in p, not p.length()              │
└────────────────────────────────────┴─────────────────────────────────────────┘
```

## Pseudocode
```
pFreq = frequency map of p
sFreq = {}
need = pFreq.size(), matched = 0
result = []

for right in 0..s.length-1:
    rc = s[right]
    sFreq[rc]++
    if rc in pFreq AND sFreq[rc] == pFreq[rc]: matched++

    if right >= p.length():  // window too big, shrink left
        lc = s[right - p.length()]
        if lc in pFreq AND sFreq[lc] == pFreq[lc]: matched--
        sFreq[lc]--

    if matched == need:
        result.add(right - p.length() + 1)

return result
```

## Complexity
- Time: O(s + p) — single pass over s, O(p) to build pFreq
- Space: O(1) — alphabet size 26, fixed

## Watch Out For
- Decrement `matched` BEFORE decrementing `sFreq[lc]` on removal
- Increment `matched` only when freq EXACTLY equals pFreq (not just when present)
- Window starts shrinking when `right >= p.length()` (0-indexed)

## Dry Run
`s="cbaebacd"`, `p="abc"` → pFreq={a:1,b:1,c:1}, need=3
```
right=0(c): sFreq{c:1}==pFreq{c:1} → matched=1
right=1(b): sFreq{b:1}==pFreq{b:1} → matched=2
right=2(a): sFreq{a:1}==pFreq{a:1} → matched=3 == need → add index 0
right=3(e): shrink: remove s[0]=c, sFreq{c:1}==pFreq{c:1} → matched=2, sFreq{c:0}
            add e: not in pFreq. matched=2
right=4(b): sFreq{b:2} ≠ pFreq{b:1}. shrink: remove s[1]=b, sFreq{b:2}≠pFreq{b:1} no change, sFreq{b:1}
...
right=6(c): sFreq{c:1}==pFreq → matched=3 → add index 6-3+1=4 ✓
Result: [0, 6] ✓
```

## Boilerplate Template
```java
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        Map<Character, Integer> pmap = new HashMap<>();
        for (char c : p.toCharArray()) pmap.merge(c, 1, Integer::sum);

        List<Integer> res = new ArrayList<>();
        Map<Character, Integer> smap = new HashMap<>();
        int left = 0, right = 0;

        while (right < s.length()) {
            smap.merge(s.charAt(right), 1, Integer::sum);      // expand right
            if (right - left + 1 == p.length()) {              // window full
                if (pmap.equals(smap)) res.add(left);
                smap.merge(s.charAt(left), -1, Integer::sum);  // shrink left
                if (smap.get(s.charAt(left)) == 0) smap.remove(s.charAt(left));
                left++;
            }
            right++;
        }
        return res;
    }
}
```
