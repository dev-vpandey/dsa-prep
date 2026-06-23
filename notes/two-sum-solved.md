# Two Sum — Easy
Problem Link: https://leetcode.com/problems/two-sum/description/
Solved Date: 2026-06-20
Pattern Tag: hashmap / complement-lookup

## SRS Tracking
- Stage: 3
- Review Date: 2026-06-28
- Last Rating: Strong
- Review Count: 1
- Graduated: No

---

# Real World Analogy
You're looking for two puzzle pieces that fit together. Instead of comparing every pair, you remember each piece you've seen. When you pick up a new piece, instantly check if its matching piece is already in your memory.

## Core Insight
For each element x, you need target - x. Store seen elements in a map so complement lookup is O(1) instead of scanning again.

## Approach
Iterate once. For each number x, compute complement y = target - x. If y is already in the map, return both indices. Otherwise, store x → index and continue.

## Mental Model

╔══════════════════════════════════════════╦══════════════════════════════════════════════════════╗
║ Decision                                 ║ Why                                                  ║
╠══════════════════════════════════════════╬══════════════════════════════════════════════════════╣
║ HashMap<value, index>                    ║ O(1) complement lookup                               ║
║ Check BEFORE inserting                   ║ Avoid using same element twice                       ║
║ Store index as value                     ║ Answer needs indices, not values                     ║
╚══════════════════════════════════════════╩══════════════════════════════════════════════════════╝

## Pseudocode
```
map = {}
for i, x in nums:
    y = target - x
    if y in map:
        return [i, map[y]]
    map[x] = i
return null
```

## Complexity

### Time: O(n)

╔═══════════════════════╦════════════════╦══════════════════════════════════════════════╗
║ Component             ║ Cost           ║ Why                                          ║
╠═══════════════════════╬════════════════╬══════════════════════════════════════════════╣
║ Single pass           ║ O(n)           ║ Visit each element once                      ║
║ HashMap lookup/insert ║ O(1) avg       ║ Hash-based access                            ║
║ Total                 ║ O(n)           ║ n iterations × O(1) each                     ║
╚═══════════════════════╩════════════════╩══════════════════════════════════════════════╝

### Space: O(n)

╔══════════════════╦══════════╦══════════════════════════════════════════════╗
║ Structure        ║ Size     ║ Why                                          ║
╠══════════════════╬══════════╬══════════════════════════════════════════════╣
║ HashMap          ║ O(n)     ║ Worst case: store all n elements before hit  ║
╚══════════════════╩══════════╩══════════════════════════════════════════════╝

## Watch Out For
- Check complement BEFORE inserting current element — prevents using nums[i] + nums[i]
- Problem guarantees exactly one solution, so null return is safety only
- Map stores value → index (not index → value)

## Dry Run
nums = [2, 7, 11, 15], target = 9

i=0: x=2, y=7 → map empty → put {2:0}
i=1: x=7, y=2 → map has 2 at index 0 → return [1, 0] ✓

## Boiler Plate Template

```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int x = nums[i], y = target - x;
        if (map.containsKey(y))
            return new int[] {i, map.get(y)};
        map.put(x, i);
    }
    return null;
}
```
