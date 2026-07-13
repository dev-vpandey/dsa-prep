# Count Pairs Whose Sum is Less than Target — Easy
Problem Link: https://leetcode.com/problems/count-pairs-whose-sum-is-less-than-target/
Solved Date: 2026-07-01
Pattern Tag: two-pointer / sorted-count-diff

## SRS Tracking
- Stage: 2
- Review Date: 2026-07-04
- Last Rating: Strong
- Review Count: 1
- Graduated: No
 
---

# Real World Analogy
Two people standing at opposite ends of a sorted line of weights — if the heaviest-still-available pairs light enough with the lightest, everyone in between also pairs light enough with that lightest one.

## Core Insight
Once sorted, if `nums[left] + nums[right] < target`, every index between `left+1` and `right` also satisfies the sum with `left` — count them all at once (`right - left`), don't check one at a time.

## Approach
Sort the array. Two pointers at ends. If sum < target, all `right-left` pairs with current `left` are valid — add that count, advance `left` (nothing more to check for it). Else shrink from the right to reduce the sum.

## Mental Model

╔══════════════════════════════════════════╦══════════════════════════════════════════════════════╗
║ Decision                                 ║ Why                                                  ║
╠══════════════════════════════════════════╬══════════════════════════════════════════════════════╣
║ sum < target → count += (right-left)     ║ sorted asc → every index in (left, right] also < target with this left ║
║ sum < target → left++ (not right--)      ║ this left is fully resolved — every remaining right already counted ║
║ sum >= target → right--                  ║ need a smaller partner, shrink from the big end       ║
╚══════════════════════════════════════════╩══════════════════════════════════════════════════════╝

## Pseudocode
```
sort(nums)
left = 0, right = n-1, count = 0
while left < right:
  sum = nums[left] + nums[right]
  if sum < target:
    count += right - left
    left++
  else:
    right--
return count
```

## Complexity

### Time: O(n log n)

╔═══════════════════════╦════════════════╦══════════════════════════════════════════════╗
║ Component             ║ Cost           ║ Why                                          ║
╠═══════════════════════╬════════════════╬══════════════════════════════════════════════╣
║ Sort                  ║ O(n log n)     ║ Collections.sort                              ║
║ Two-pointer scan      ║ O(n)           ║ left and right each move at most n times      ║
║ Total                 ║ O(n log n)     ║ sort dominates                                ║
╚═══════════════════════╩════════════════╩══════════════════════════════════════════════╝

### Space: O(1)

╔══════════════════╦══════════╦══════════════════════════════════════════════╗
║ Structure        ║ Size     ║ Why                                          ║
╠══════════════════╬══════════╬══════════════════════════════════════════════╣
║ pointers/count    ║ O(1)     ║ no extra structures, in-place sort            ║
╚══════════════════╩══════════╩══════════════════════════════════════════════╝

### Why Optimal
Can't count pairs faster than O(n log n) here since sorting is required to unlock the batch-count trick; without sorting you're stuck at O(n²) brute force.

╔══════════════════════╦══════════════╦════════════════════════════════════════════╗
║ Algorithm / Approach ║ Time         ║ Note                                       ║
╠══════════════════════╬══════════════╬════════════════════════════════════════════╣
║ This solution        ║ O(n log n)   ║ sort + two-pointer batch count             ║
╠══════════════════════╬══════════════╬════════════════════════════════════════════╣
║ Brute force          ║ O(n²)        ║ check every pair directly                 ║
╚══════════════════════╩══════════════╩════════════════════════════════════════════╝

## Watch Out For
- First-attempt bug: only counting 1 pair per true-branch hit instead of `right-left`, and never advancing `left` — silently undercounts.
- `while(left < right)` strictly, not `<=` — avoid pairing an index with itself.

## Dry Run
Array sorted: `[-7,-6,-2,-1,2,3,5]`, target=-2
- left=0(-7), right=6(5): sum=-2, not < target → right-- → right=5
- left=0(-7), right=5(3): sum=-4 < -2 → count += 5 = 5, left++ → left=1
- left=1(-6), right=5(3): sum=-3 < -2 → count += 4 = 9, left++ → left=2
- left=2(-2), right=5(3): sum=1, not < target → right-- → right=4
- left=2(-2), right=4(2): sum=0, not < target → right-- → right=3
- left=2(-2), right=3(-1): sum=-3 < -2 → count += 1 = 10, left++ → left=3
- left=3, right=3 → loop ends → **count = 10**

## Boiler Plate Template
```java
int left = 0, right = nums.size() - 1, count = 0;
Collections.sort(nums);
while (left < right) {
    int sum = nums.get(left) + nums.get(right);
    if (sum < target) {
        count += right - left;
        left++;
    } else {
        right--;
    }
}
return count;
```
