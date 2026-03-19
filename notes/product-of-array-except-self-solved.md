# Product of Array Except Self — Medium
Link: https://leetcode.com/problems/product-of-array-except-self/description/
Solved Date: 2026-03-03
Review Date: 2026-03-06

## Core Insight
Every answer is just (product of everything LEFT of i) × (product of everything RIGHT of i) — store the left pass in the result array, then fold in the right pass with a single running variable.

## Approach
First pass left→right: fill `res[i]` with the product of all elements to the left of index i. Second pass right→left: multiply each `res[i]` by a running `rightProd` variable that accumulates the product of all elements to the right of i. No extra array needed because you consume each right product before you need the next.

## Mental Model
```
┌─────────────────────────────────────────────────────────────────┐
│ Decision                  │ Why                                  │
├───────────────────────────┼──────────────────────────────────────┤
│ res[0] = 1 to start       │ No elements to the left of index 0   │
├───────────────────────────┼──────────────────────────────────────┤
│ First pass: res[i] =      │ Builds prefix product — each slot    │
│ res[i-1] * nums[i-1]      │ holds product of all LEFT neighbors  │
├───────────────────────────┼──────────────────────────────────────┤
│ rightProd starts at 1     │ No elements to the right of last idx │
├───────────────────────────┼──────────────────────────────────────┤
│ Second pass: multiply     │ rightProd IS right[i] — built        │
│ res[i] by rightProd first,│ incrementally, one slot at a time    │
│ then update rightProd     │ so no array needed                   │
├───────────────────────────┼──────────────────────────────────────┤
│ Order matters in pass 2:  │ Must use current rightProd BEFORE    │
│ res[i] *= rightProd first │ folding nums[i] into it              │
└───────────────────────────┴──────────────────────────────────────┘
```

## Pseudocode
```
res[0] = 1
for i from 1 to n-1:
    res[i] = res[i-1] * nums[i-1]   // left products

rightProd = 1
for i from n-1 down to 0:
    res[i] = res[i] * rightProd      // inject right product
    rightProd = rightProd * nums[i]  // accumulate for next iteration
return res
```

## Complexity
- Time: O(n) — two linear passes
- Space: O(1) extra — output array doesn't count; no left/right auxiliary arrays

## Watch Out For
- `res[0] = 1` must be set before the first loop — index 0 has no left neighbor
- In pass 2: multiply `res[i]` by `rightProd` BEFORE updating `rightProd` with `nums[i]`
- Don't confuse `nums[i-1]` in pass 1 — you want the element to the left, not the current

## Dry Run
```
nums = [1, 2, 3, 4]

--- Pass 1 (left products) ---
res[0] = 1
res[1] = res[0] * nums[0] = 1 * 1 = 1
res[2] = res[1] * nums[1] = 1 * 2 = 2
res[3] = res[2] * nums[2] = 2 * 3 = 6

res = [1, 1, 2, 6]

--- Pass 2 (fold in right products) ---
rightProd = 1

i=3: res[3] = 6 * 1 = 6    | rightProd = 1 * 4 = 4
i=2: res[2] = 2 * 4 = 8    | rightProd = 4 * 3 = 12
i=1: res[1] = 1 * 12 = 12  | rightProd = 12 * 2 = 24
i=0: res[0] = 1 * 24 = 24  | rightProd = 24 * 1 = 24

res = [24, 12, 8, 6] ✓
```

## Pattern Tag
`prefix-product` / `two-pass` / `space-optimization` / `array`

---

## Boilerplate Template
```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];

        // Pass 1: fill res with left products
        res[0] = 1;
        for (int i = 1; i < n; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }

        // Pass 2: multiply by right products on the fly
        int rightProd = 1;
        for (int i = n - 1; i >= 0; i--) {
            res[i] *= rightProd;
            rightProd *= nums[i];
        }

        return res;
    }
}
```
