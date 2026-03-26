# Next Greater Element I — Easy
Problem Link: https://leetcode.com/problems/next-greater-element-i/
Solved Date: 2026-03-24
Pattern Tag: monotonic-stack / hashmap / next-greater

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like a crowd watching a parade — each person can only see the next taller person ahead. Process the line to pre-compute everyone's "next taller" answer.

## Core Insight
Process `nums2` with a monotonic decreasing stack. When a larger element is found, it's the "next greater" for all smaller elements currently on the stack. Store results in a map; then look up for each element in `nums1`.

## Approach
Iterate `nums2` with a stack. For each number: while stack top is smaller than current, pop and record `stackTop → current` in map. Push current. After processing, remaining elements in stack have no next greater (map to -1). Look up nums1 elements in map.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Maintain decreasing stack            │ Elements waiting for their next        │
│                                      │ greater — pop when larger arrives     │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Pop all smaller elements when larger │ Current element is the NGE for all    │
│ arrives                              │ those smaller ones                    │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Leftover in stack → -1               │ No larger element appeared to their   │
│                                      │ right in nums2                        │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
stack = [], map = {}
for each num in nums2:
    while stack not empty AND stack.top < num:
        map[stack.pop()] = num
    stack.push(num)
while stack not empty:
    map[stack.pop()] = -1

return [map[n] for n in nums1]
```

## Complexity
- Time: O(m + n) — each element pushed/popped from stack once
- Space: O(n) — stack and map for nums2

## Watch Out For
- All remaining elements in stack after the loop get -1 (not ignored)
- nums1 is a subset of nums2 — guaranteed all nums1 elements exist in map

## Dry Run
`nums1 = [4,1,2]`, `nums2 = [1,3,4,2]`
```
Process nums2:
1: stack=[1]
3: pop 1 → map{1:3}; stack=[3]
4: pop 3 → map{3:4}; stack=[4]
2: 2<4, no pop; stack=[4,2]
End: pop 2→map{2:-1}, pop 4→map{4:-1}

Lookup: nums1[4]=-1, nums1[1]=3, nums1[2]=-1
Result: [-1, 3, -1] ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> ngeMap = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();

        for (int num : nums2) {
            while (!stack.isEmpty() && stack.peek() < num) {
                ngeMap.put(stack.pop(), num); // current num is NGE for popped elements
            }
            stack.push(num);
        }
        while (!stack.isEmpty()) ngeMap.put(stack.pop(), -1); // no NGE

        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) result[i] = ngeMap.get(nums1[i]);
        return result;
    }
}
```
