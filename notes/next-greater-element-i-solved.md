# Next Greater Element I — Easy
Problem Link: https://leetcode.com/problems/next-greater-element-i/
Solved Date: 2026-03-24
Pattern Tag: monotonic-stack / hashmap / next-greater

## SRS Tracking
- Stage: 1
- Review Date: 2026-06-30
- Last Rating: Blank
- Review Count: 5
- Graduated: No

---

# Real World Analogy
Like a crowd watching a parade — each person can only see the next taller person ahead. Process the line to pre-compute everyone's "next taller" answer.

## Core Insight
Process `nums2` with a monotonic decreasing stack. When a larger element is found, it's the "next greater" for all smaller elements currently on the stack. Store results in a map; then look up for each element in `nums1`.

## Approach
Iterate `nums2` right-to-left. Stack holds candidates to the right. For each element: pop all stack elements ≤ current (they can't be NGE — current blocks them). Stack top is now the NGE (if any). Record result for nums1 elements via index map. Push current.

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
indexMap = {nums1[i] → i}
result = [-1] * nums1.length
stack = []

for i from nums2.length-1 down to 0:
    curr = nums2[i]
    while stack not empty AND stack.top <= curr:
        stack.pop()
    if stack not empty AND curr in indexMap:
        result[indexMap[curr]] = stack.top
    stack.push(curr)

return result
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
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < nums1.length; i++) indexMap.put(nums1[i], i);

        int[] result = new int[nums1.length];
        Arrays.fill(result, -1);

        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = nums2.length - 1; i >= 0; i--) {
            int curr = nums2[i];
            while (!stack.isEmpty() && stack.peek() <= curr) stack.pop();
            if (!stack.isEmpty() && indexMap.containsKey(curr))
                result[indexMap.get(curr)] = stack.peek();
            stack.push(curr);
        }
        return result;
    }
}
```
