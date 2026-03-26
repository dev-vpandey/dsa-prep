# Min Heap Construction — Medium
Problem Link: https://leetcode.com/problems/design-a-heap/
Solved Date: 2026-03-25
Pattern Tag: heap / design / sift-down / heapify / priority-queue

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like organizing a tournament bracket from the bottom up — instead of inserting players one-by-one from the top (O(n log n)), you start at the last internal node and let each "bad" node sink to its correct position. The whole bracket is valid after one bottom-up pass.

## Core Insight
**Heapify (build heap)** in O(n) by calling sift-down on every non-leaf node from the bottom up. Sift-down: compare node with smallest child — if child is smaller, swap and continue down. Insert: append to end + sift-up. Remove min: replace root with last element + sift-down.

## Approach
Array-based heap (1-indexed or 0-indexed). Parent of i: `(i-1)/2`. Children: `2i+1`, `2i+2` (0-indexed).
- **Build**: call siftDown from `n/2 - 1` down to 0.
- **SiftDown**: find min child, swap if child < node, recurse/loop down.
- **SiftUp**: compare with parent, swap if node < parent, loop up.
- **Insert**: add to end, siftUp.
- **Poll**: save root, move last to root, siftDown.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Build with sift-down bottom-up       │ O(n) vs O(n log n) for n insertions;  │
│ start at n/2-1                       │ leaves need no sifting (no children)  │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ 0-indexed: parent=(i-1)/2,           │ Standard mapping; children always     │
│ children=2i+1, 2i+2                  │ exist if index < heap.size()          │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ SiftDown picks SMALLER child         │ Min-heap: must compare both children  │
│                                      │ and swap with the smallest to         │
│                                      │ maintain heap invariant               │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
heap = array

build(arr):
    heap = arr
    for i from (n/2 - 1) down to 0:
        siftDown(i)

siftDown(i):
    while true:
        smallest = i
        left = 2i+1, right = 2i+2
        if left < n AND heap[left] < heap[smallest]: smallest = left
        if right < n AND heap[right] < heap[smallest]: smallest = right
        if smallest == i: break
        swap(heap[i], heap[smallest])
        i = smallest

insert(val):
    heap.add(val)
    siftUp(heap.size()-1)

siftUp(i):
    while i > 0:
        parent = (i-1)/2
        if heap[i] < heap[parent]:
            swap(i, parent)
            i = parent
        else: break

pollMin():
    min = heap[0]
    heap[0] = heap.removeLast()
    siftDown(0)
    return min
```

## Complexity
- Time: Build O(n), Insert O(log n), Poll O(log n), Peek O(1)
- Space: O(n) — the array itself

## Watch Out For
- Build starts at `n/2 - 1` (last non-leaf), NOT at n-1
- SiftDown must pick the SMALLER child (not just left)
- After `pollMin`, place LAST element at index 0 before sifting
- Handle edge: poll from single-element heap (no sifting needed)

## Dry Run
```
Build [5, 3, 8, 1, 4]:
n=5, start at i=1 (n/2-1)

i=1: node=3, left=1(idx3), right=4(idx4)
  smallest=3(idx3): swap(3,1) → [5,1,8,3,4]
i=0: node=5, left=1(idx1), right=8(idx2)
  smallest=1(idx1): swap(5,1) → [1,5,8,3,4]
  now at i=1: node=5, left=3(idx3), right=4(idx4)
  smallest=3(idx3): swap(5,3) → [1,3,8,5,4]
  now at i=3: no children → done

Result: [1,3,8,5,4] ✓ (valid min-heap)
```

## Boilerplate Template
```java
import java.util.*;

class MinHeap {
    private List<Integer> heap = new ArrayList<>();

    public void build(int[] arr) {
        for (int v : arr) heap.add(v);
        for (int i = heap.size() / 2 - 1; i >= 0; i--) siftDown(i);
    }

    public void insert(int val) {
        heap.add(val);
        siftUp(heap.size() - 1);
    }

    public int pollMin() {
        int min = heap.get(0);
        int last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return min;
    }

    public int peekMin() { return heap.get(0); }

    private void siftDown(int i) {
        int n = heap.size();
        while (true) {
            int smallest = i, l = 2 * i + 1, r = 2 * i + 2;
            if (l < n && heap.get(l) < heap.get(smallest)) smallest = l;
            if (r < n && heap.get(r) < heap.get(smallest)) smallest = r;
            if (smallest == i) break;
            Collections.swap(heap, i, smallest);
            i = smallest;
        }
    }

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (heap.get(i) < heap.get(parent)) {
                Collections.swap(heap, i, parent);
                i = parent;
            } else break;
        }
    }
}
```
