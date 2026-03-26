# Implement Trie (Prefix Tree) — Medium
Problem Link: https://leetcode.com/problems/implement-trie-prefix-tree/
Solved Date: 2026-03-25
Pattern Tag: trie / design / tree / prefix-search

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like an autocomplete system — each character of a word branches off into a tree. To check if "apple" is a word, walk the branches a→p→p→l→e and check if the last node is marked as a complete word.

## Core Insight
Each TrieNode has `children[26]` and an `isEnd` flag. Insert: walk/create one node per character, mark last as end. Search: walk nodes per character; end flag must be true. StartsWith: walk; reaching end of prefix = true.

## Approach
`TrieNode` inner class. `Trie` has a `root` node. All three operations (`insert`, `search`, `startsWith`) share the same "walk character by character" logic.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ children[26] array (not HashMap)     │ Fixed alphabet: array is faster O(1)  │
│ indexed by char - 'a'                │ lookup vs HashMap                     │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ isEnd flag on nodes                  │ Marks whether this node is the last   │
│                                      │ character of an inserted word         │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ search vs startsWith:                │ search needs isEnd=true at last char; │
│ only differ at final check           │ startsWith only needs to reach there  │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
class TrieNode:
    children = new TrieNode[26]
    isEnd = false

insert(word):
    node = root
    for each char c:
        idx = c - 'a'
        if node.children[idx] == null: node.children[idx] = new TrieNode()
        node = node.children[idx]
    node.isEnd = true

search(word):
    node = walk(word)
    return node != null AND node.isEnd

startsWith(prefix):
    return walk(prefix) != null

walk(word):
    node = root
    for each char c:
        idx = c - 'a'
        if node.children[idx] == null: return null
        node = node.children[idx]
    return node
```

## Complexity
- Time: O(m) per operation where m = word length
- Space: O(n * m * 26) — n words, m average length, 26-branching

## Watch Out For
- `isEnd = true` on the LAST character node (not the root)
- `search` requires `isEnd == true` at the end — just reaching the last char isn't enough
- `startsWith` does NOT require `isEnd` — just reaching the prefix end is sufficient

## Dry Run
```
insert("apple"):
root → [a] → [p] → [p] → [l] → [e].isEnd=true

search("apple") → walk to 'e'.isEnd=true ✓
search("app")   → walk to 'p'.isEnd=false → false ✓
startsWith("app") → walk to 'p' != null → true ✓
```

## Boilerplate Template
```java
class Trie {
    private final TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) node.children[idx] = new TrieNode();
            node = node.children[idx];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        TrieNode node = walk(word);
        return node != null && node.isEnd;
    }

    public boolean startsWith(String prefix) {
        return walk(prefix) != null;
    }

    private TrieNode walk(String s) {
        TrieNode node = root;
        for (char c : s.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) return null;
            node = node.children[idx];
        }
        return node;
    }

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd;
    }
}
```
