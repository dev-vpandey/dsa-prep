# Group 8 — Advanced Structures
# Patterns: Trie · Heap Design · Segment Tree (future)
# Prerequisite: Group 1 (HashMap), Group 2 (Heap/Queue), Group 3 (Tree DFS)
# Daily read: open when working on Trie or design problems (3 min)

---

## PREREQUISITE CHAIN
```
Tree DFS (Group 3) + HashMap (Group 1)
    │
    └──► Trie (prefix tree — 26-ary tree of characters)
              │
              └──► Trie + DFS backtracking (Word Search II)

Heap (Group 2)
    │
    └──► Heap Design (Min Heap from scratch — sift-up / sift-down)

(future) Segment Tree — range queries with updates
```

---

## ── TRIE ────────────────────────────────────────────────────────────────────
# Real world: phone autocomplete — each character is a branch, words end with a flag.

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEnd = false;
}

class Trie {
    TrieNode root = new TrieNode();

    void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) node.children[idx] = new TrieNode();
            node = node.children[idx];
        }
        node.isEnd = true;
    }

    boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) return false;
            node = node.children[idx];
        }
        return node.isEnd;                              // must be end of a word
    }

    boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) return false;
            node = node.children[idx];
        }
        return true;                                    // just needs to exist, no isEnd check
    }
}
```

⚠️ Watch out: `search` checks `node.isEnd`. `startsWith` does NOT. Mixing these up is the most common Trie bug under pressure.

---

## ── TRIE WITH WILDCARD (DFS) ────────────────────────────────────────────────
# Use when: search with '.' wildcard that matches any character.

```java
boolean searchWithWildcard(String word, int idx, TrieNode node) {
    if (idx == word.length()) return node.isEnd;
    char c = word.charAt(idx);
    if (c == '.') {
        for (TrieNode child : node.children) {
            if (child != null && searchWithWildcard(word, idx + 1, child)) return true;
        }
        return false;
    } else {
        TrieNode next = node.children[c - 'a'];
        return next != null && searchWithWildcard(word, idx + 1, next);
    }
}
```

---

## ── HEAP DESIGN (from scratch) ──────────────────────────────────────────────
# Real world: a self-organizing pile — the smallest always floats to the top.

```java
class MinHeap {
    int[] heap;
    int size;

    MinHeap(int capacity) { heap = new int[capacity + 1]; size = 0; }

    void insert(int val) {
        heap[++size] = val;
        siftUp(size);
    }

    int poll() {
        int min = heap[1];
        heap[1] = heap[size--];
        siftDown(1);
        return min;
    }

    // Build heap from array — O(n) using sift-down from last parent
    void buildHeap(int[] arr) {
        size = arr.length;
        System.arraycopy(arr, 0, heap, 1, size);
        for (int i = size / 2; i >= 1; i--) siftDown(i);  // start from last parent
    }

    void siftUp(int i) {
        while (i > 1 && heap[i] < heap[i / 2]) {
            swap(i, i / 2);
            i /= 2;
        }
    }

    void siftDown(int i) {
        while (2 * i <= size) {
            int child = 2 * i;                              // left child
            if (child < size && heap[child + 1] < heap[child]) child++;  // pick smaller child
            if (heap[i] <= heap[child]) break;
            swap(i, child);
            i = child;
        }
    }

    void swap(int a, int b) { int tmp = heap[a]; heap[a] = heap[b]; heap[b] = tmp; }
}
```

⚠️ Watch out: `buildHeap` starts sift-down from `size / 2` (last parent), NOT from the last element. Going bottom-up with sift-down is O(n). Going top-down with sift-up is O(n log n).

---

## Your Recurring Mistakes — Group 8
- **Trie search vs startsWith**: checking `isEnd` in startsWith (should NOT be there)
- **Wildcard search**: returning true immediately instead of checking all children when `c == '.'`
- **Heap buildHeap**: starting sift-down from last element instead of `size / 2`
- **Trie insert**: forgetting `node.isEnd = true` at the end — word never found

---

## Solved Card Examples
- Trie: `implement-trie-solved.md`
- Trie + wildcard: `design-add-search-words-solved.md`
- Trie + backtracking: `word-search-ii-solved.md`
- Heap design: `min-heap-construction-solved.md`
