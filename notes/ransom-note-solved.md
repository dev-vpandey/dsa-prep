# Ransom Note — Easy
Problem Link: https://leetcode.com/problems/ransom-note/
Solved Date: 2026-06-03
Pattern Tag: hashmap / frequency-count

## SRS Tracking
- Stage: 3
- Review Date: 2026-07-03
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Scrabble tile bag: check if your bag has enough of each letter tile to spell the target word.

## Core Insight
Build a frequency map of magazine letters; for each ransomNote letter, consume one — if the bucket is empty, return false.

## Approach
Count frequency of every character in magazine. Scan ransomNote and decrement the count for each char. If count hits zero before we finish, magazine can't cover it.

## Mental Model

╔══════════════════════════════════════╦══════════════════════════════════════════════╗
║ Decision                             ║ Why                                          ║
╠══════════════════════════════════════╬══════════════════════════════════════════════╣
║ One map (magazine only)              ║ ransomNote needs no freq map — just consume  ║
║ Decrement as we scan ransomNote      ║ Catches shortage the moment it happens       ║
║ int[26] beats HashMap                ║ Lowercase only → array index = c - 'a'      ║
╚══════════════════════════════════════╩══════════════════════════════════════════════╝

## Pseudocode
```
build freq[] for magazine (size 26)
for each char c in ransomNote:
    if freq[c - 'a'] == 0 → return false
    freq[c - 'a']--
return true
```

## Complexity

### Time: O(m + n)

╔═══════════════════════╦════════════════╦══════════════════════════════════════════════╗
║ Component             ║ Cost           ║ Why                                          ║
╠═══════════════════════╬════════════════╬══════════════════════════════════════════════╣
║ Build magazine freq   ║ O(m)           ║ one pass over magazine                       ║
║ Scan ransomNote       ║ O(n)           ║ one pass over ransomNote                     ║
║ Total                 ║ O(m + n)       ║ two linear passes                            ║
╚═══════════════════════╩════════════════╩══════════════════════════════════════════════╝

### Space: O(1)

╔══════════════════╦══════════╦══════════════════════════════════════════════╗
║ Structure        ║ Size     ║ Why                                          ║
╠══════════════════╬══════════╬══════════════════════════════════════════════╣
║ freq array       ║ O(1)     ║ always 26 ints regardless of input size      ║
╚══════════════════╩══════════╩══════════════════════════════════════════════╝

## Watch Out For
- Condition direction: check if magazine count is 0 (exhausted), not if ransom count exceeds
- Two maps is over-engineering — you only need to track what magazine provides

## Dry Run
```
ransomNote = "aa", magazine = "aab"
freq after magazine: [a=2, b=1]
scan 'a': freq[a]=2 → ok, freq[a]=1
scan 'a': freq[a]=1 → ok, freq[a]=0
return true ✓

ransomNote = "aa", magazine = "ab"
freq after magazine: [a=1, b=1]
scan 'a': freq[a]=1 → ok, freq[a]=0
scan 'a': freq[a]=0 → return false ✓
```

## Boiler Plate Template
```java
// Frequency count with int[26] — lowercase English only
int[] freq = new int[26];
for (char c : source.toCharArray()) freq[c - 'a']++;
for (char c : target.toCharArray()) {
    if (freq[c - 'a'] == 0) return false;
    freq[c - 'a']--;
}
return true;
```
