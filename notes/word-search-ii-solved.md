# Word Search II — Hard
Link: https://leetcode.com/problems/word-search-ii/description/
Solved Date: 2026-03-02
Review Date: 2026-03-09

## Core Insight
Build a Trie from all target words, then DFS from every board cell — the Trie guides which paths are worth exploring without checking every word independently.

## Approach
Insert all words into a Trie so prefix matching is O(1) per character. Run DFS from every cell on the board, walking the Trie simultaneously. When you reach a Trie node that marks a complete word, collect it and null it out to avoid duplicates.

## Mental Model
```
┌─────────────────────┬──────────────────────────────────────────────────────┐
│ Decision            │ Why                                                  │
├─────────────────────┼──────────────────────────────────────────────────────┤
│ Why Trie?           │ Checking all words per path = O(words × path length) │
│                     │ Trie lets us prune entire subtrees in one step       │
├─────────────────────┼──────────────────────────────────────────────────────┤
│ Why DFS from every  │ Any cell can be the start of a valid word            │
│ cell?               │                                                      │
├─────────────────────┼──────────────────────────────────────────────────────┤
│ Why mark '#'?       │ Prevents reusing the same cell within one word path  │
│                     │ Restored after backtracking for other word paths     │
├─────────────────────┼──────────────────────────────────────────────────────┤
│ Why node.word=null? │ Same word can be found via different paths;          │
│                     │ nulling prevents adding it twice to result           │
├─────────────────────┼──────────────────────────────────────────────────────┤
│ Why store word at   │ Avoids reconstructing the word character by          │
│ Trie leaf?          │ character during DFS — just grab it directly         │
└─────────────────────┴──────────────────────────────────────────────────────┘
```

## Pseudocode
```
build Trie from all words (store full word at end node)

for each cell (row, col) in board:
    dfs(board, row, col, trieRoot, result)

dfs(board, row, col, node, result):
    ch = board[row][col]
    if ch == '#' OR ch not in node.children → return

    board[row][col] = '#'          // mark visited
    node = node.children[ch]       // descend Trie

    if node.word != null:
        result.add(node.word)
        node.word = null           // deduplicate

    for each of 4 directions:
        if in bounds → dfs(board, nr, nc, node, result)

    board[row][col] = ch           // restore (backtrack)
```

## Complexity
- Time: O(M · 4 · 3^(L-1)) where M = board cells, L = max word length. From each cell we branch into 4 directions initially, then 3 (can't go back where we came from). Trie pruning cuts dead branches early.
- Space: O(N) for the Trie where N = total characters across all words. O(L) recursion stack depth.

## Watch Out For
- Using `row`/`col` consistently — easy to mix up with short `r`/`c` aliases mid-function
- Forgetting to restore the board cell after DFS (breaks other word searches)
- Not nulling `node.word` → duplicate entries if same word reachable via multiple paths
- Off-by-one on boundary check: `nr >= board.length` and `nc >= board[0].length`

## Dry Run
```
Board:
┌───┬───┬───┬───┐
│ o │ a │ a │ n │  row 0
├───┼───┼───┼───┤
│ e │ t │ a │ e │  row 1
├───┼───┼───┼───┤
│ i │ h │ k │ r │  row 2
├───┼───┼───┼───┤
│ i │ f │ l │ v │  row 3
└───┴───┴───┴───┘

Words: ["eat", "oath"]
Trie built:
  root → e → a → t (word="eat")
       → o → a → t → h (word="oath")

--- Finding "eat" ---
Start DFS at (1,3) 'e':
  Trie: root has 'e' ✓ → mark '#', descend to node(e)
  Try (0,3) 'n': node(e) has no 'n' → return
  Try (1,2) 'a': node(e) has 'a' ✓ → mark '#', descend to node(a)
    Try (1,1) 't': node(a) has 't' ✓ → mark '#', descend to node(t)
      node(t).word = "eat" → add to result, null it
      No further Trie children → backtrack
    Restore (1,1) = 't'
    ... other directions pruned by Trie
  Restore (1,2) = 'a'
  Restore (1,3) = 'e'

--- Finding "oath" ---
Start DFS at (0,0) 'o':
  Trie: root has 'o' ✓ → mark '#', descend to node(o)
  Try (0,1) 'a': node(o) has 'a' ✓ → mark '#', descend to node(a)
    Try (1,1) 't': node(a) has 't' ✓ → mark '#', descend to node(t)
      Try (2,1) 'h': node(t) has 'h' ✓ → mark '#', descend to node(h)
        node(h).word = "oath" → add to result, null it
      Restore (2,1) = 'h'
    Restore (1,1) = 't'
  Restore (0,1) = 'a'
  Restore (0,0) = 'o'

Result: ["eat", "oath"] ✓
```

## Pattern Tag
`trie / dfs / backtracking / matrix`

## Boilerplate Template
```java
class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        TrieNode root = new TrieNode();
        for (String word : words)
            buildTrie(root, word);

        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board[0].length; col++)
                dfs(board, row, col, root, res);

        return res;
    }

    private void dfs(char[][] board, int row, int col, TrieNode node, List<String> res) {
        char ch = board[row][col];
        if (ch == '#' || !node.children.containsKey(ch)) return;

        board[row][col] = '#';
        node = node.children.get(ch);

        if (node.word != null) {
            res.add(node.word);
            node.word = null; // deduplicate
        }

        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] dir : dirs) {
            int nr = row + dir[0], nc = col + dir[1];
            if (nr < 0 || nc < 0 || nr >= board.length || nc >= board[0].length) continue;
            dfs(board, nr, nc, node, res);
        }

        board[row][col] = ch; // restore
    }

    private void buildTrie(TrieNode root, String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
        node.word = word;
    }
}

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    String word;
}
```
