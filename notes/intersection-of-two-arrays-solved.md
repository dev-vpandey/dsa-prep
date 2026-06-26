# Intersection of Two Arrays — Easy
Problem Link: https://leetcode.com/problems/intersection-of-two-arrays/
Solved Date: 2026-06-21
Pattern Tag: hashset / intersection

## SRS Tracking
- Stage: 2
- Review Date: 2026-06-25
- Last Rating: Strong
- Review Count: 1
- Graduated: No

---

# Real World Analogy
Two guest lists at a party — find names that appear on both lists, no duplicates in the final list.

## Core Insight
Put one array in a HashSet, iterate the other and collect matches into a result Set — the result Set deduplicates for free.

## Approach
Add all elements of nums1 to a HashSet. Iterate nums2; for each element found in the set, add it to a result HashSet (handles duplicates automatically). Stream result to int[].

## Mental Model

╔══════════════════════════════════════════╦══════════════════════════════════════════════════════╗
║ Decision                                 ║ Why                                                  ║
╠══════════════════════════════════════════╬══════════════════════════════════════════════════════╣
║ HashSet for nums1                        ║ O(1) contains() lookup vs O(n) linear scan           ║
║ Result is also a Set                     ║ Deduplicates nums2 duplicates automatically           ║
║ Put SMALLER array in the set             ║ Saves space — set size = min(n, m)                   ║
╚══════════════════════════════════════════╩══════════════════════════════════════════════════════╝

## Pseudocode
```
set1 = HashSet(nums1)
res  = HashSet()

for n in nums2:
    if n in set1:
        res.add(n)

return res as int[]
```

## Complexity

### Time: O(n + m)

╔═══════════════════════╦════════════════╦══════════════════════════════════════════════╗
║ Component             ║ Cost           ║ Why                                          ║
╠═══════════════════════╬════════════════╬══════════════════════════════════════════════╣
║ Build set1            ║ O(n)           ║ Insert n elements                            ║
║ Iterate nums2         ║ O(m)           ║ One pass, O(1) lookup per element            ║
║ Total                 ║ O(n + m)       ║ Two independent passes                       ║
╚═══════════════════════╩════════════════╩══════════════════════════════════════════════╝

### Space: O(min(n, m))

╔══════════════════╦══════════════╦══════════════════════════════════════════════╗
║ Structure        ║ Size         ║ Why                                          ║
╠══════════════════╬══════════════╬══════════════════════════════════════════════╣
║ set1             ║ O(min(n,m))  ║ Build from smaller array to save space       ║
║ res set          ║ O(min(n,m))  ║ At most min(n,m) elements in intersection    ║
╚══════════════════╩══════════════╩══════════════════════════════════════════════╝

## Watch Out For
- Use a result **Set** not List — List accumulates duplicates from nums2
- `List.toArray(new int[0])` won't compile — needs `stream().mapToInt(Integer::intValue).toArray()`
- Put the **smaller** array in the set, iterate the other — saves space

## Dry Run
`nums1 = [4,9,5]`, `nums2 = [9,4,9,8,4]`
```
set1 = {4, 9, 5}
res  = {}

n=9 → in set1 → res={9}
n=4 → in set1 → res={9,4}
n=9 → in set1 → already in res (Set dedup) → res={9,4}
n=8 → not in set1 → skip
n=4 → already in res → res={9,4}

Result: [9,4] ✓
```

## Boilerplate Template
```java
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        // put smaller array in set to save space
        if (nums1.length > nums2.length) return intersection(nums2, nums1);

        Set<Integer> set1 = new HashSet<>();
        for (int n : nums1) set1.add(n);

        Set<Integer> res = new HashSet<>();
        for (int n : nums2) {
            if (set1.contains(n)) res.add(n);
        }

        return res.stream().mapToInt(Integer::intValue).toArray();
    }
}
```
