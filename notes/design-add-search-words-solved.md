# Design Add and Search Words Data Structure — Medium
Problem Link: https://leetcode.com/problems/design-add-and-search-words-data-structure/
Solved Date: 2026-03-25
Pattern Tag: trie / backtracking / wildcard / dfs

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like a wildcard password search — `'.'` matches any single character, so `".og"` would match "dog", "fog", "log". Try all possibilities at each dot using backtracking on the trie.

## Core Insight
Standard trie insert. Search with `'.'` wildcard: when `'.'` is encountered, try all 26 children recursively. If any branch returns true, the whole search succeeds.

## Approach
Trie with standard insert. Search is a DFS/recursive function taking `(node, word, index)`. For each character: if `'.'`, try all non-null children and OR the results. If regular char, look up `children[c-'a']` and recurse.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ '.' → try all 26 children            │ Wildcard matches anything — must      │
│ (DFS backtracking)                   │ explore all possibilities             │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Regular char → single child branch   │ Deterministic navigation (no choice)  │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Return isEnd when index == length    │ Word fully consumed — check if valid  │
│                                      │ word ends here                        │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
addWord(word): standard trie insert

search(word): return dfs(root, word, 0)

dfs(node, word, i):
    if i == word.length: return node.isEnd
    char c = word[i]
    if c == '.':
        for each child in node.children:
            if child != null AND dfs(child, word, i+1): return true
        return false
    else:
        child = node.children[c-'a']
        if child == null: return false
        return dfs(child, word, i+1)
```

## Complexity
- Time: Insert O(m). Search O(26^m) worst case (all dots), but O(m) for no dots
- Space: O(n*m) trie + O(m) recursion stack

## Watch Out For
- For `'.'`, must check ALL children and return true if ANY succeeds
- Return false (not null) when no dot children match
- Base case: `i == word.length` → check `node.isEnd`

## Dry Run
```
addWord("bad"), addWord("dad"), addWord("mad")
search(".ad") → root, '.', try a→bad→ dfs(b,".ad",1)→dfs(a,".ad",2)→dfs(d,".ad",3)→isEnd=true ✓
search("b..") → root→b→a→d→isEnd=true ✓
search("pad") → root, p: no child 'p' → false ✓
```

## Boilerplate Template
```java
class WordDictionary {
    private final TrieNode root = new TrieNode();

    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) node.children[idx] = new TrieNode();
            node = node.children[idx];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        return dfs(root, word, 0);
    }

    private boolean dfs(TrieNode node, String word, int i) {
        if (i == word.length()) return node.isEnd;
        char c = word.charAt(i);
        if (c == '.') {
            for (TrieNode child : node.children) {
                if (child != null && dfs(child, word, i + 1)) return true;
            }
            return false;
        } else {
            TrieNode child = node.children[c - 'a'];
            return child != null && dfs(child, word, i + 1);
        }
    }

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd;
    }
}
```
