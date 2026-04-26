# Group 1 — Foundation
# Patterns: Array · HashMap · Two Pointer · Sliding Window · Intervals · TreeMap · Utilities
# Prerequisite: none — this is the base everything else builds on
# Daily read: open this when working on any Group 1 pattern (3 min)

---

## ── ARRAY ───────────────────────────────────────────────────────────────────
# Real world: numbered lockers in a row — instant access by position.

```java
Arrays.sort(arr);                                        // ascending
Arrays.sort(arr, (a, b) -> a[0] - b[0]);                // 2D array by col 0
Arrays.fill(arr, -1);                                    // fill entire array
Arrays.fill(arr, from, to, -1);                          // fill range [from, to)
Arrays.copyOf(arr, newLength);
int[][] deepCopy = Arrays.stream(grid).map(int[]::clone).toArray(int[][]::new);

// Prefix sum — O(1) range queries after O(n) build
int[] prefix = new int[n + 1];
for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + nums[i];
int rangeSum = prefix[r + 1] - prefix[l];               // sum of nums[l..r]
```

⚠️ Watch out: prefix array is size n+1, not n. `prefix[i+1] = prefix[i] + nums[i]`. Range query is `prefix[r+1] - prefix[l]`.

---

## ── HASHMAP ─────────────────────────────────────────────────────────────────
# Real world: a dictionary — look up any word instantly by its spelling.

```java
Map<Integer, Integer> map = new HashMap<>();
map.put(key, val);
map.get(key);                                            // null if missing
map.getOrDefault(key, 0);                                // safe default
map.containsKey(key);
map.remove(key);
map.merge(key, 1, Integer::sum);                         // increment count cleanly
map.computeIfAbsent(key, k -> new ArrayList<>());        // init if missing
map.getOrDefault(key, new ArrayList<>());                // does NOT store default

// Iterate entries
for (Map.Entry<Integer, Integer> e : map.entrySet()) {
    e.getKey(); e.getValue();
}

// Frequency map pattern
Map<Character, Integer> freq = new HashMap<>();
for (char c : s.toCharArray()) freq.merge(c, 1, Integer::sum);

// Prefix sum + HashMap (subarray sum = k)
Map<Integer, Integer> prefixCount = new HashMap<>();
prefixCount.put(0, 1);                                   // empty prefix
int sum = 0, result = 0;
for (int num : nums) {
    sum += num;
    result += prefixCount.getOrDefault(sum - k, 0);
    prefixCount.merge(sum, 1, Integer::sum);
}
```

⚠️ Watch out: `getOrDefault` with a new list does NOT store it — use `computeIfAbsent` when you need to add to the list afterward.

---

## ── TWO POINTER ─────────────────────────────────────────────────────────────
# Real world: two people walking toward each other — they meet somewhere in the middle.

```java
// Opposite ends (sorted array problems)
int left = 0, right = arr.length - 1;
while (left < right) {
    if (arr[left] + arr[right] == target) { /* found */ left++; right--; }
    else if (arr[left] + arr[right] < target) left++;
    else right--;
}

// Skip non-valid characters (palindrome pattern)
while (left < right) {
    while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
    while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
    if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) return false;
    left++; right--;
}

// Slow / Fast pointer (middle of list, cycle detection)
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
// slow = middle when fast reaches end

// Three pointer (sort colors / Dutch flag)
int lo = 0, mid = 0, hi = arr.length - 1;
while (mid <= hi) {
    if (arr[mid] == 0) swap(arr, lo++, mid++);
    else if (arr[mid] == 1) mid++;
    else swap(arr, mid, hi--);
}
```

⚠️ Watch out: skip condition must ALSO check `left < right` — pointers can cross during skipping. Always guard both inner while loops.

---

## ── SLIDING WINDOW ──────────────────────────────────────────────────────────
# Real world: a camera panning across a scene — expand right to see more, shrink left when invalid.

```java
// Variable window — expand until invalid, shrink until valid again
int left = 0, result = 0;
Map<Character, Integer> freq = new HashMap<>();

for (int right = 0; right < s.length(); right++) {
    freq.merge(s.charAt(right), 1, Integer::sum);        // expand right

    while (/* window invalid */) {                        // shrink left
        freq.merge(s.charAt(left), -1, Integer::sum);
        if (freq.get(s.charAt(left)) == 0) freq.remove(s.charAt(left));
        left++;
    }
    result = Math.max(result, right - left + 1);
}

// Fixed window — size k (frequency map variant)
int left = 0;
Map<Character, Integer> smap = new HashMap<>();
while (right < s.length()) {
    smap.merge(s.charAt(right), 1, Integer::sum);          // expand right
    if (right - left + 1 == k) {                           // window full
        // record answer (e.g. pmap.equals(smap))
        smap.merge(s.charAt(left), -1, Integer::sum);      // shrink left
        if (smap.get(s.charAt(left)) == 0) smap.remove(s.charAt(left));
        left++;
    }
    right++;
}

// Fixed window — size k (array/index variant)
for (int right = 0; right < arr.length; right++) {
    // add arr[right] to window
    if (right >= k) { /* remove arr[right - k] from window */ }
    if (right >= k - 1) { /* record answer */ }
}

// Matched counter trick (find all anagrams)
int matched = 0;
// matched == total unique chars needed → valid window
```

⚠️ Watch out: window size = `right - left + 1`. For matched counter, decrement matched only when count goes from 1→0 (just became unmatched), not every time you remove.

---

## ── INTERVALS ───────────────────────────────────────────────────────────────
# Real world: meeting time slots — find conflicts, merge overlaps, count rooms needed.

```java
// Sort by start time (merge intervals)
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

// Sort by end time (greedy — minimize removals)
Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

// Overlap check: [a,b] and [c,d] overlap when c <= b (after sorting by start)
// Merge: new end = Math.max(current[1], next[1])

// Merge intervals pattern
List<int[]> result = new ArrayList<>();
result.add(intervals[0]);
for (int i = 1; i < intervals.length; i++) {
    int[] last = result.get(result.size() - 1);
    if (intervals[i][0] <= last[1]) last[1] = Math.max(last[1], intervals[i][1]);
    else result.add(intervals[i]);
}

// Count rooms / max overlapping (heap of end times)
PriorityQueue<Integer> endTimes = new PriorityQueue<>();
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
for (int[] interval : intervals) {
    if (!endTimes.isEmpty() && endTimes.peek() <= interval[0]) endTimes.poll();
    endTimes.offer(interval[1]);
}
return endTimes.size();   // rooms needed
```

⚠️ Watch out: merge end = `Math.max(current[1], next[1])` NOT just `next[1]`. A smaller interval can be fully contained inside a larger one.

---

## ── TREEMAP ─────────────────────────────────────────────────────────────────
# Real world: a sorted phone book — you can jump to any entry and look left or right instantly.

```java
TreeMap<Integer, Integer> map = new TreeMap<>();
map.ceilingKey(x);    // smallest key >= x  → null if none
map.floorKey(x);      // largest key <= x   → null if none
map.higherKey(x);     // smallest key > x   → null if none
map.lowerKey(x);      // largest key < x    → null if none
map.firstKey();       // minimum key
map.lastKey();        // maximum key
map.pollFirstEntry(); // remove and return min entry
map.pollLastEntry();  // remove and return max entry
map.headMap(x);       // submap with keys strictly < x
map.tailMap(x);       // submap with keys >= x
```

⚠️ Watch out: `ceilingKey` / `floorKey` return null if no such key exists. Always null-check: `if (map.ceilingKey(x) != null)`.

---

## ── COMMON JAVA UTILITIES ───────────────────────────────────────────────────

```java
// String / char
char[] chars = s.toCharArray();
new String(chars);
s.charAt(i);
s.substring(lo, hi);          // [lo, hi) exclusive end
s.indexOf(c);
s.contains(sub);
String.valueOf(intVal);
StringBuilder sb = new StringBuilder();
sb.append(c); sb.deleteCharAt(sb.length() - 1); sb.toString();

// Character checks
Character.isLetterOrDigit(c);
Character.isDigit(c); Character.isLetter(c);
Character.toLowerCase(c); Character.toUpperCase(c);
c - 'a';    // char to 0-based index
(char)('a' + i);   // index to char

// Integer limits
Integer.MAX_VALUE;   // ~2.1 billion
Integer.MIN_VALUE;
Long.MAX_VALUE;      // use when multiplying large ints — overflow guard
Math.max(a, b); Math.min(a, b); Math.abs(x);

// Collections
Collections.reverse(list);
Collections.sort(list);
List.copyOf(list);            // immutable copy
new ArrayList<>(list);        // mutable copy
new ArrayList<>(Arrays.asList(1, 2, 3));
```

---

## Your Recurring Mistakes — Group 1
- **Prefix sum**: forgetting size is `n+1`, not `n`
- **Sliding window size**: using `right - left` instead of `right - left + 1`
- **Skip condition in two pointer**: inner while missing `left < right` guard
- **Merge intervals**: using `next[1]` as new end instead of `Math.max(current[1], next[1])`

---

## Solved Card Examples
- Two Pointer: `valid-palindrome-solved.md` · `triplet-sum-to-zero-solved.md`
- Sliding Window: `minimum-window-substring-solved.md` · `find-all-anagrams-solved.md`
- Prefix Sum: `subarray-sum-equals-k-solved.md` · `range-sum-query-immutable-solved.md`
- Intervals: `merge-intervals-solved.md` · `non-overlapping-intervals-solved.md`
