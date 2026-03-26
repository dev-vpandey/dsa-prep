# Cutting Wood (Binary Search on Answer) — Medium
Problem Link: Custom (similar to LC 1011 / Wood Cut on LintCode)
Solved Date: 2026-03-25
Pattern Tag: binary-search / answer-space / feasibility-check / maximize

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like setting a saw blade height — cut all trees taller than the blade height H. The wood you collect is the total excess. Find the maximum H that still gives you at least the required wood.

## Core Insight
Binary search on the cut height H (0 to max tree height). For each H, total wood = `sum of max(0, tree[i] - H)`. Find the maximum H where total wood >= required.

## Approach
`left = 0`, `right = max(trees)`. For each `mid` H: compute collected wood. If `wood >= required`, try higher H (`left = mid + 1`). Else try lower (`right = mid - 1`). Return `left - 1` (or track explicit answer).

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Binary search on H (not trees)       │ Answer space [0, max] is sorted;      │
│                                      │ higher H = less wood collected        │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ MAXIMIZE H (opposite of Koko)        │ Want the tallest allowed cut height   │
│                                      │ → when feasible, try higher           │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Collect = sum of (tree[i] - H) when  │ Only collect from trees taller than H │
│ tree[i] > H (else 0)                 │                                       │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
left = 0, right = max(trees), answer = 0
while left <= right:
    mid = (left + right) / 2
    wood = sum of max(0, tree - mid) for each tree
    if wood >= required:
        answer = mid          // H is feasible, try higher
        left = mid + 1
    else:
        right = mid - 1       // too high, not enough wood
return answer
```

## Complexity
- Time: O(n log m) — n trees, m = max tree height
- Space: O(1)

## Watch Out For
- Maximize H (opposite direction to Koko which minimizes speed)
- `answer = mid` when feasible, continue searching higher
- Use `long` for wood sum to prevent overflow with large trees

## Dry Run
`trees = [20,15,10,17]`, required = 7
```
left=0, right=20
mid=10: wood=(20-10)+(15-10)+(10-10)+(17-10)=10+5+0+7=22>=7 → answer=10, left=11
mid=15: wood=(20-15)+(15-15)+(17-15)=5+0+2=7>=7 → answer=15, left=16
mid=18: wood=(20-18)=2<7 → right=17
mid=16: wood=(20-16)+(17-16)=4+1=5<7 → right=15
left=16>right=15 → return 15 ✓
```

## Boilerplate Template
```java
class Solution {
    public int cutWood(int[] trees, int required) {
        int left = 0, right = 0;
        for (int t : trees) right = Math.max(right, t);

        int answer = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long wood = 0;
            for (int t : trees) wood += Math.max(0, t - mid);

            if (wood >= required) {
                answer = mid;  // feasible, try higher H
                left = mid + 1;
            } else {
                right = mid - 1; // not enough wood, lower H
            }
        }
        return answer;
    }
}
```
