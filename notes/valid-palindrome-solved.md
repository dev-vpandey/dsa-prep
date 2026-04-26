# Valid Palindrome — Easy
Problem Link: https://leetcode.com/problems/valid-palindrome/
Solved Date: 2026-03-24
Pattern Tag: two-pointer / string / alphanumeric-filter

## SRS Tracking
- Stage: 6
- Review Date: 2026-07-20
- Last Rating: Strong
- Review Count: 5
- Graduated: Yes

---

# Real World Analogy
Like reading a license plate from both ends simultaneously, skipping any decorative symbols — if every alphanumeric character matches its mirror, it's a palindrome.

## Core Insight
Two pointers from both ends; skip non-alphanumeric characters; compare lowercased characters. Return false on first mismatch.

## Approach
`left = 0`, `right = s.length()-1`. Skip non-alphanumeric on both sides. Compare `toLowerCase(s[left]) == toLowerCase(s[right])`. If mismatch, return false. If pointers cross, return true.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Character.isLetterOrDigit(c)         │ Only alphanumeric counts — spaces,    │
│                                      │ commas, etc. are skipped              │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ toLowerCase before comparing         │ "A" and "a" are the same palindrome   │
│                                      │ character                             │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Skip BOTH pointers independently     │ Left might need multiple skips before │
│                                      │ right finds its alphanumeric char     │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
left = 0, right = n-1
while left < right:
    while left < right AND !isAlphanumeric(s[left]): left++
    while left < right AND !isAlphanumeric(s[right]): right--
    if toLowerCase(s[left]) != toLowerCase(s[right]): return false
    left++, right--
return true
```

## Complexity
- Time: O(n) — each pointer moves at most n steps total
- Space: O(1) — in-place, no converted string

## Watch Out For
- Skip condition must also check `left < right` — pointers could cross during skipping
- Don't convert entire string to char array unnecessarily; use `charAt()`
- Input with only non-alphanumeric characters (e.g. `" "`) → returns true (valid empty palindrome)

## Dry Run
Input: `"A man, a plan, a canal: Panama"`
```
left=0(A), right=29(a) → 'a'=='a' ✓ → left=1, right=28
skip '  ',' ',',' → left=2(m), right=27(m) → match ✓
...continues matching m-a-n-a-c-a-n-a-l-p-a-n-a-m-a
→ return true ✓
```

## Boilerplate Template
```java
class Solution {
    public boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right)))
                return false;
            left++;
            right--;
        }
        return true;
    }
}
```
