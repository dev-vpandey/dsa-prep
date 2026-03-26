# Koko Eating Bananas — Medium
Problem Link: https://leetcode.com/problems/koko-eating-bananas/
Solved Date: 2026-03-24
Pattern Tag: binary-search / answer-space / feasibility-check / minimize

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like finding the slowest acceptable eating pace — try every possible speed from slowest to fastest and pick the slowest one that still finishes within h hours. Binary search makes this efficient.

## Core Insight
Binary search on the eating speed (1 to max pile). For each candidate speed, check if all piles can be finished in ≤ h hours using `ceil(pile / speed)` per pile. Find the minimum valid speed.

## Approach
`left = 1`, `right = max(piles)`. For each `mid` speed, compute total hours = `sum(ceil(pile/mid))`. If hours ≤ h, this speed works — try slower (`right = mid`). Otherwise try faster (`left = mid + 1`). Return left.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Binary search on SPEED not piles     │ Searching the answer space (1 to max) │
│                                      │ not the input — classic BS on answer  │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ ceil(pile/speed) = (pile+speed-1)/speed│ Ceiling division without floating   │
│                                      │ point: integer math only             │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ right = mid (not mid-1) when valid   │ Speed mid might be the minimum — keep │
│                                      │ it as candidate                       │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
left = 1, right = max(piles)
while left < right:
    mid = (left + right) / 2
    hours = sum of ceil(pile/mid) for each pile
    if hours <= h: right = mid       // mid might be answer, try smaller
    else: left = mid + 1             // too slow, need faster
return left
```

## Complexity
- Time: O(n log m) — log m binary search iterations, each O(n) feasibility check
- Space: O(1)

## Watch Out For
- `right = max(piles)` not `sum(piles)` — at max speed, each pile takes 1 hour
- Use integer ceiling division `(pile + speed - 1) / speed`
- Loop exits when `left == right` — that's the minimum valid speed

## Dry Run
`piles = [3,6,7,11]`, `h = 8`
```
left=1, right=11
mid=6: hours=ceil(3/6)+ceil(6/6)+ceil(7/6)+ceil(11/6) = 1+1+2+2 = 6 ≤ 8 → right=6
mid=3: hours=1+2+3+4=10 > 8 → left=4
mid=5: hours=1+2+2+3=8 ≤ 8 → right=5
mid=4: hours=1+2+2+3=8 ≤ 8 → right=4
left=4=right → return 4 ✓
```

## Boilerplate Template
```java
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1, right = 0;
        for (int p : piles) right = Math.max(right, p);

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canFinish(piles, mid, h)) right = mid; // mid might be answer
            else left = mid + 1;
        }
        return left;
    }

    private boolean canFinish(int[] piles, int speed, int h) {
        int hours = 0;
        for (int pile : piles) hours += (pile + speed - 1) / speed; // ceiling division
        return hours <= h;
    }
}
```
