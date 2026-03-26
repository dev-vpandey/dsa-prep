# Happy Number — Easy
Problem Link: https://leetcode.com/problems/happy-number/
Solved Date: 2026-03-24
Pattern Tag: hashset / cycle-detection / math

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like following a treasure map that either leads to treasure (1) or loops forever in a circle — track visited spots to detect if you're going in circles.

## Core Insight
Sum of squared digits either eventually reaches 1 (happy) or enters a cycle. Use a HashSet to detect repeated sums → cycle detected → not happy.

## Approach
Repeatedly compute sum of squares of digits. If it becomes 1, return true. If we've seen this sum before (HashSet), return false — we're in a cycle.

## Mental Model
```
┌───────────────────────────────────┬──────────────────────────────────────────┐
│ Decision                          │ Why                                      │
├───────────────────────────────────┼──────────────────────────────────────────┤
│ HashSet to detect cycles          │ Non-happy numbers cycle; seeing same     │
│                                   │ number twice means infinite loop         │
├───────────────────────────────────┼──────────────────────────────────────────┤
│ Check for 1 in the loop condition │ If n becomes 1, we're done — don't      │
│                                   │ add 1 to seen and continue              │
├───────────────────────────────────┼──────────────────────────────────────────┤
│ Extract digits with % 10 / 10     │ Standard digit extraction without        │
│                                   │ converting to string                     │
└───────────────────────────────────┴──────────────────────────────────────────┘
```

## Pseudocode
```
seen = {}
while n != 1:
    if n in seen: return false
    seen.add(n)
    n = sumOfSquaredDigits(n)
return true

sumOfSquaredDigits(n):
    sum = 0
    while n > 0:
        digit = n % 10
        sum += digit * digit
        n /= 10
    return sum
```

## Complexity
- Time: O(log n) per step, bounded cycles — effectively O(log n)
- Space: O(log n) — HashSet stores the cycle length worth of numbers

## Watch Out For
- Don't check `if seen.contains(n)` AFTER adding to seen in same iteration
- The cycle for non-happy numbers always includes 4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4

## Dry Run
`n = 19`
```
19 → 1² + 9² = 1 + 81 = 82
82 → 64 + 4 = 68
68 → 36 + 64 = 100
100 → 1 + 0 + 0 = 1 ✓ → return true
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        while (n != 1) {
            if (!seen.add(n)) return false; // add returns false if already present
            n = sumSquares(n);
        }
        return true;
    }

    private int sumSquares(int n) {
        int sum = 0;
        while (n > 0) {
            int d = n % 10;
            sum += d * d;
            n /= 10;
        }
        return sum;
    }
}
```
