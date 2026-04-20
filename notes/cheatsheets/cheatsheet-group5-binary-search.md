# Group 5 — Binary Search
# Patterns: Standard · First/Last Occurrence · Search on Answer · Rotated Array · 2D Matrix
# Prerequisite: Group 1 (sorted arrays) — binary search only works on monotonic/sorted spaces
# Daily read: open this when working on any binary search pattern (3 min)

---

## PREREQUISITE CHAIN
```
Sorted Array (Group 1)
    │
    ├──► Standard Binary Search (find target)
    │       │
    │       ├──► First occurrence (leftmost)
    │       ├──► Last occurrence (rightmost)
    │       └──► Lower bound / insertion point
    │
    ├──► Rotated Sorted Array variants
    │
    ├──► Search on Answer (binary search on value range, not index)
    │       │
    │       └──► Koko Eating Bananas · Capacity to Ship · Cutting Wood
    │
    └──► 2D Matrix binary search
```

---

## ── STANDARD BINARY SEARCH ──────────────────────────────────────────────────
# Real world: guessing a number — always cut the remaining range in half.

```java
int lo = 0, hi = arr.length - 1;
while (lo <= hi) {                                  // lo <= hi — runs until range is empty
    int mid = lo + (hi - lo) / 2;                  // NEVER (lo + hi) / 2 — overflow
    if (arr[mid] == target) return mid;
    else if (arr[mid] < target) lo = mid + 1;
    else hi = mid - 1;
}
return -1;
```

⚠️ Watch out: `mid = lo + (hi - lo) / 2` always. `(lo + hi) / 2` overflows when both are near Integer.MAX_VALUE.

---

## ── FIRST OCCURRENCE (leftmost target) ──────────────────────────────────────
# Keep searching LEFT after finding target.

```java
int lo = 0, hi = arr.length - 1, result = -1;
while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;
    if (arr[mid] == target) {
        result = mid;       // save answer
        hi = mid - 1;       // keep searching LEFT for earlier occurrence
    } else if (arr[mid] < target) lo = mid + 1;
    else hi = mid - 1;
}
return result;
```

---

## ── LAST OCCURRENCE (rightmost target) ──────────────────────────────────────
# Keep searching RIGHT after finding target.

```java
int lo = 0, hi = arr.length - 1, result = -1;
while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;
    if (arr[mid] == target) {
        result = mid;       // save answer
        lo = mid + 1;       // keep searching RIGHT for later occurrence
    } else if (arr[mid] < target) lo = mid + 1;
    else hi = mid - 1;
}
return result;
```

---

## ── LOWER BOUND (insertion point) ───────────────────────────────────────────
# Smallest index where arr[index] >= target. Used for: Search Insert Position.

```java
int lo = 0, hi = arr.length;                        // hi = length (not length-1) — can insert at end
while (lo < hi) {                                   // lo < hi (not lo <= hi)
    int mid = lo + (hi - lo) / 2;
    if (arr[mid] < target) lo = mid + 1;
    else hi = mid;                                  // arr[mid] >= target: mid could be answer
}
return lo;                                          // lo = hi = insertion point
```

⚠️ Watch out: `hi = arr.length` (not `length - 1`) and `lo < hi` (not `lo <= hi`). This is the lower bound template — different from standard search.

---

## ── SEARCH ON ANSWER ─────────────────────────────────────────────────────────
# Real world: adjusting a dial — find the minimum setting that satisfies a condition.
# Use when: "find minimum/maximum X such that condition(X) is true"
# Signal words: "minimum speed", "minimum capacity", "minimum days"

```java
// Template
int lo = MIN_POSSIBLE_ANSWER, hi = MAX_POSSIBLE_ANSWER;
while (lo < hi) {                                   // lo < hi — converges to single answer
    int mid = lo + (hi - lo) / 2;
    if (feasible(mid)) hi = mid;                    // mid works → try smaller (minimize)
    else lo = mid + 1;                              // mid doesn't work → need larger
}
return lo;

// feasible() checks: "can we achieve the goal with value = mid?"

// Example — Koko Eating Bananas: can Koko eat all piles in H hours at speed k?
boolean feasible(int[] piles, int k, int H) {
    int hours = 0;
    for (int pile : piles) hours += (pile + k - 1) / k;   // ceiling division
    return hours <= H;
}
// lo = 1, hi = max(piles)
```

⚠️ Watch out: ceiling division = `(value + divisor - 1) / divisor`. Don't use `Math.ceil((double)value / divisor)` — floating point errors will haunt you.

---

## ── ROTATED SORTED ARRAY ────────────────────────────────────────────────────
# Real world: a sorted list that got cut and rearranged — one half is still sorted.
# Key insight: one of the two halves [lo,mid] or [mid,hi] is ALWAYS sorted. Check which one.

```java
// Search in Rotated Sorted Array
int lo = 0, hi = arr.length - 1;
while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;
    if (arr[mid] == target) return mid;

    if (arr[lo] <= arr[mid]) {                       // LEFT half is sorted
        if (arr[lo] <= target && target < arr[mid]) hi = mid - 1;   // target in left
        else lo = mid + 1;                           // target in right
    } else {                                         // RIGHT half is sorted
        if (arr[mid] < target && target <= arr[hi]) lo = mid + 1;   // target in right
        else hi = mid - 1;                           // target in left
    }
}
return -1;

// Find Minimum in Rotated Array
int lo = 0, hi = arr.length - 1;
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (arr[mid] > arr[hi]) lo = mid + 1;            // min is in right half
    else hi = mid;                                    // mid could be min
}
return arr[lo];
```

⚠️ Watch out: use `arr[lo] <= arr[mid]` with `<=` to handle the case where `lo == mid` (2-element array). Using `<` misses this edge case.

---

## ── 2D MATRIX BINARY SEARCH ─────────────────────────────────────────────────
# Treat the entire 2D matrix as a 1D sorted array.

```java
// Matrix where each row is sorted AND first element of next row > last of current
int m = matrix.length, n = matrix[0].length;
int lo = 0, hi = m * n - 1;

while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;
    int val = matrix[mid / n][mid % n];              // convert 1D index to 2D
    if (val == target) return true;
    else if (val < target) lo = mid + 1;
    else hi = mid - 1;
}
return false;
```

⚠️ Watch out: `row = mid / n`, `col = mid % n`. This only works when the matrix is fully sorted end-to-end (LeetCode 74). Not for LeetCode 240 (each row/col sorted separately).

---

## ── FIND PEAK ELEMENT ────────────────────────────────────────────────────────
# Key insight: if arr[mid] < arr[mid+1], a peak exists in the right half (always move toward uphill).

```java
int lo = 0, hi = arr.length - 1;
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (arr[mid] < arr[mid + 1]) lo = mid + 1;      // uphill → peak is right
    else hi = mid;                                    // downhill → peak is here or left
}
return lo;
```

---

## Your Recurring Mistakes — Group 5
- **Overflow**: using `(lo + hi) / 2` instead of `lo + (hi - lo) / 2`
- **Lower bound**: using `hi = arr.length - 1` instead of `hi = arr.length`
- **Lower bound**: using `lo <= hi` instead of `lo < hi`
- **Search on answer**: using floating point ceiling instead of integer ceiling `(v + d - 1) / d`
- **Rotated array**: using `<` instead of `<=` in `arr[lo] <= arr[mid]` — misses 2-element edge case
- **First occurrence**: returning immediately on match instead of saving + continuing left

---

## Solved Card Examples
- Standard: `search-2d-matrix-solved.md`
- First/Last: `find-first-last-position-sorted-array-solved.md`
- Lower bound: `search-insert-position-solved.md`
- Search on answer: `koko-eating-bananas-solved.md` · `cutting-wood-solved.md`
- Rotated: `search-rotated-sorted-array-solved.md` · `find-minimum-rotated-sorted-array-solved.md`
- Peak: `find-peak-element-solved.md`
- Hard: `median-two-sorted-arrays-solved.md`
