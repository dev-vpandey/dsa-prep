# SRS Review Index
# Source of truth for review scheduling. Update this file whenever:
# - A new card is added (append row)
# - A review is completed (update Stage, Review Date, Last Rating, Review Count)
# - A card graduates (mark Graduated=Yes)
#
# Format: | File | Problem | Tag | Stage | Review Date | Last Rating | Review Count | Graduated |

| File | Problem | Tag | Stage | Review Date | Last Rating | Review Count | Graduated |
|---|---|---|---|---|---|---|---|
| move-zeroes-solved.md | Move Zeroes | two-pointer / in-place | 5 | 2026-04-23 | Strong | 8 | No |
| reverse-linked-list-solved.md | Reverse Linked List | three-pointer / iterative | 5 | 2026-04-23 | Strong | 8 | No |
| middle-of-linked-list-solved.md | Middle of Linked List | slow-fast pointer | 5 | 2026-04-23 | Strong | 8 | No |
| linked-list-cycle-solved.md | Linked List Cycle | Floyd's cycle detection | 5 | 2026-04-23 | Strong | 8 | No |
| valid-parentheses-solved.md | Valid Parentheses | stack / matching | 5 | 2026-04-23 | Strong | 8 | No |
| invert-binary-tree-solved.md | Invert Binary Tree | dfs / swap | 5 | 2026-04-23 | Strong | 8 | No |
| max-depth-binary-tree-solved.md | Max Depth Binary Tree | dfs / recursion | 5 | 2026-04-23 | Strong | 8 | No |
| same-tree-solved.md | Same Tree | structural dfs | 5 | 2026-04-23 | Strong | 8 | No |
| logger-rate-limiter-solved.md | Logger Rate Limiter | hashmap / timestamp | 5 | 2026-04-23 | Strong | 8 | No |
| moving-average-data-stream-solved.md | Moving Average from Data Stream | queue / running-sum | 5 | 2026-04-23 | Strong | 8 | No |
| valid-palindrome-solved.md | Valid Palindrome | two-pointer / skip non-alnum | 4 | 2026-04-05 | Strong | 4 | No |
| container-with-most-water-solved.md | Container with Most Water | two-pointer / move shorter | 4 | 2026-04-05 | Strong | 4 | No |
| triplet-sum-to-zero-solved.md | Triplet Sum to Zero | sort + two-pointer + dedup | 4 | 2026-04-05 | Strong | 4 | No |
| happy-number-solved.md | Happy Number | hashset / cycle detection | 4 | 2026-04-05 | Strong | 4 | No |
| find-all-anagrams-solved.md | Find All Anagrams in String | fixed sliding window / matched counter | 4 | 2026-04-05 | Strong | 4 | No |
| longest-substring-without-repeating-solved.md | Longest Substring Without Repeating | sliding window / lastIndex | 4 | 2026-04-06 | Strong | 4 | No |
| longest-repeating-character-replacement-solved.md | Longest Repeating Character Replacement | sliding window / maxFreq | 4 | 2026-04-06 | Strong | 4 | No |
| sliding-window-maximum-solved.md | Sliding Window Maximum | monotonic deque of indices | 4 | 2026-04-06 | Strong | 4 | No |
| intersection-of-linked-lists-solved.md | Intersection of Linked Lists | two-pointer / redirect | 4 | 2026-04-06 | Strong | 4 | No |
| linked-list-palindrome-solved.md | Linked List Palindrome | reverse second half / compare | 4 | 2026-04-06 | Strong | 4 | No |
| next-greater-element-i-solved.md | Next Greater Element I | monotonic stack + HashMap | 4 | 2026-04-07 | Strong | 4 | No |
| evaluate-reverse-polish-notation-solved.md | Evaluate Reverse Polish Notation | stack / pop b then a | 4 | 2026-04-07 | Strong | 4 | No |
| implement-stack-using-queues-solved.md | Implement Stack Using Queues | queue / rotate n-1 | 4 | 2026-04-07 | Strong | 4 | No |
| implement-queue-using-stacks-solved.md | Implement Queue Using Stacks | two stacks / lazy transfer | 4 | 2026-04-07 | Strong | 4 | No |
| remove-adjacent-duplicates-solved.md | Remove Adjacent Duplicates | StringBuilder as stack | 4 | 2026-04-07 | Strong | 4 | No |
| balanced-binary-tree-solved.md | Balanced Binary Tree | dfs / -1 sentinel | 4 | 2026-04-08 | Strong | 4 | No |
| diameter-binary-tree-solved.md | Diameter of Binary Tree | dfs / return height / global max | 4 | 2026-04-08 | Strong | 4 | No |
| symmetric-tree-solved.md | Symmetric Tree | dfs / isMirror | 4 | 2026-04-08 | Strong | 4 | No |
| maximum-width-binary-tree-solved.md | Maximum Width of Binary Tree | bfs / index / normalize | 4 | 2026-04-08 | Strong | 4 | No |
| kth-smallest-bst-solved.md | Kth Smallest in BST | iterative inorder / stop at k | 4 | 2026-04-08 | Strong | 4 | No |
| validate-bst-solved.md | Validate BST | min/max bounds / Long | 4 | 2026-04-09 | Strong | 4 | No |
| search-rotated-sorted-array-solved.md | Search in Rotated Sorted Array | binary search / sorted half | 4 | 2026-04-09 | Strong | 4 | No |
| find-minimum-rotated-sorted-array-solved.md | Find Minimum in Rotated Sorted Array | binary search / right=mid | 4 | 2026-04-09 | Strong | 4 | No |
| find-first-last-position-sorted-array-solved.md | Find First and Last Position | binary search / save+continue | 4 | 2026-04-09 | Strong | 4 | No |
| search-2d-matrix-solved.md | Search a 2D Matrix | binary search / row=mid/cols | 4 | 2026-04-09 | Strong | 4 | No |
| koko-eating-bananas-solved.md | Koko Eating Bananas | binary search on answer / ceiling div | 4 | 2026-04-10 | Strong | 4 | No |
| find-peak-element-solved.md | Find Peak Element | binary search / compare mid+1 | 4 | 2026-04-10 | Strong | 4 | No |
| merge-intervals-solved.md | Merge Intervals | sort by start / max end | 4 | 2026-04-10 | Strong | 4 | No |
| non-overlapping-intervals-solved.md | Non-overlapping Intervals | sort by end / prevEnd | 4 | 2026-04-10 | Strong | 4 | No |
| number-of-islands-solved.md | Number of Islands | dfs flood fill | 4 | 2026-04-10 | Strong | 4 | No |
| max-area-of-island-solved.md | Max Area of Island | dfs / return area | 4 | 2026-04-11 | Strong | 4 | No |
| rotting-oranges-solved.md | Rotting Oranges | multi-source bfs / fresh count | 4 | 2026-04-11 | Strong | 4 | No |
| range-sum-query-immutable-solved.md | Range Sum Query Immutable | prefix sum | 4 | 2026-04-11 | Strong | 4 | No |
| sort-k-sorted-array-solved.md | Sort K-Sorted Array | min-heap size k+1 | 4 | 2026-04-11 | Strong | 4 | No |
| merge-k-sorted-lists-solved.md | Merge K Sorted Lists | min-heap of ListNodes | 4 | 2026-04-11 | Strong | 4 | No |
| k-most-frequent-strings-solved.md | K Most Frequent Strings | min-heap size k / reverse lex | 4 | 2026-04-12 | Strong | 4 | No |
| lowest-common-ancestor-binary-tree-solved.md | Lowest Common Ancestor Binary Tree | tree / dfs | 6 | 2026-08-08 | Strong | 2 | Yes |
| possible-bipartition-solved.md | Possible Bipartition | graph / bfs-dfs / bipartite | 4 | 2026-04-06 | Strong | 3 | No |
| word-ladder-solved.md | Word Ladder | graph / bfs | 3 | 2026-04-02 | Okay | 3 | No |
| number-of-connected-components-solved.md | Number of Connected Components | graph / dfs | 4 | 2026-04-06 | Strong | 3 | No |
| min-max-stack-solved.md | Min Max Stack | stack / design / two-stack | 4 | 2026-04-06 | Strong | 3 | No |
| word-search-solved.md | Word Search | backtracking / dfs / matrix | 3 | 2026-03-30 | Weak | 3 | No |
| lru-cache-solved.md | LRU Cache | hashmap / doubly-linked-list | 3 | 2026-03-27 | Okay | 2 | No |
| minimum-window-substring-solved.md | Minimum Window Substring | sliding-window / frequency-map | 3 | 2026-03-27 | Okay | 2 | No |
| subarray-sum-equals-k-solved.md | Subarray Sum Equals K | prefix-sum / hashmap | 3 | 2026-03-27 | Okay | 2 | No |
| evaluate-expression-solved.md | Evaluate Expression | stack / sign-tracking | 3 | 2026-03-27 | Okay | 2 | No |
| search-insert-position-solved.md | Search Insert Position | binary-search / lower-bound | 3 | 2026-03-28 | Okay | 2 | No |
| word-search-ii-solved.md | Word Search II | trie / dfs / backtracking | 3 | 2026-03-28 | Okay | 2 | No |
| 3sum-closest-solved.md | 3Sum Closest | two-pointer / three-sum | 3 | 2026-03-28 | Okay | 2 | No |
| nested-list-weight-sum-solved.md | Nested List Weight Sum | bfs / weighted-depth | 3 | 2026-03-28 | Okay | 2 | No |
| nested-list-weight-sum-ii-solved.md | Nested List Weight Sum II | bfs / carry-forward / running-sum | 3 | 2026-03-28 | Okay | 2 | No |
| set-matrix-zeroes-solved.md | Set Matrix Zeroes | matrix / two-pass | 3 | 2026-03-29 | Okay | 2 | No |
| product-of-array-except-self-solved.md | Product of Array Except Self | prefix-product / two-pass | 3 | 2026-03-29 | Okay | 2 | No |
| walls-and-gates-solved.md | Walls and Gates | multi-source-bfs / matrix | 3 | 2026-03-29 | Okay | 2 | No |
| vertical-order-traversal-solved.md | Vertical Order Traversal | bfs / treemap / column-tracking | 3 | 2026-03-29 | Okay | 2 | No |
| binary-tree-maximum-path-sum-solved.md | Binary Tree Maximum Path Sum | dfs / post-order / global-max | 3 | 2026-03-29 | Okay | 2 | No |
| binary-tree-right-side-view-solved.md | Binary Tree Right Side View | bfs / reverse-preorder | 3 | 2026-03-30 | Okay | 2 | No |
| pacific-atlantic-water-flow-solved.md | Pacific Atlantic Water Flow | multi-source-bfs / reverse-flow | 3 | 2026-03-30 | Okay | 2 | No |
| surrounded-regions-solved.md | Surrounded Regions | multi-source-bfs / boundary-seed | 3 | 2026-03-30 | Okay | 2 | No |
| course-schedule-solved.md | Course Schedule | topological-sort / kahn's / cycle-detection | 3 | 2026-03-30 | Okay | 2 | No |
| graph-valid-tree-solved.md | Graph Valid Tree | dfs / union-find / cycle-detection | 3 | 2026-03-30 | Okay | 2 | No |
| remove-nth-node-from-end-solved.md | Remove Nth Node from End | dummy node / advance fast n+1 | 3 | 2026-03-31 | Okay | 2 | No |
| flatten-binary-tree-to-linked-list-solved.md | Flatten Binary Tree to Linked List | tree / rightmost-of-left | 3 | 2026-03-31 | Okay | 2 | No |
| flatten-doubly-linked-list-solved.md | Flatten Doubly Linked List | linked-list / child-tail | 3 | 2026-03-31 | Okay | 2 | No |
| next-permutation-solved.md | Next Permutation | array / find-descent / reverse-suffix | 3 | 2026-03-31 | Okay | 2 | No |
| longest-consecutive-sequence-solved.md | Longest Consecutive Sequence | hashset / sequence-start | 3 | 2026-03-31 | Okay | 2 | No |
| valid-sudoku-solved.md | Valid Sudoku | matrix / box-index / set | 3 | 2026-04-01 | Okay | 2 | No |
| maximum-overlapping-intervals-solved.md | Maximum Overlapping Intervals | heap / end-times | 3 | 2026-04-01 | Okay | 2 | No |
| interval-list-intersections-solved.md | Interval List Intersections | two-pointer / advance smaller end | 3 | 2026-04-01 | Okay | 2 | No |
| find-median-data-stream-solved.md | Find Median from Data Stream | two-heap / rebalance | 3 | 2026-04-01 | Okay | 2 | No |
| random-pick-with-weight-solved.md | Random Pick with Weight | prefix-sum / binary-search | 3 | 2026-04-01 | Okay | 2 | No |
| cutting-wood-solved.md | Cutting Wood / Capacity to Ship | binary-search on answer | 3 | 2026-04-02 | Okay | 2 | No |
| median-two-sorted-arrays-solved.md | Median of Two Sorted Arrays | binary-search / partition | 3 | 2026-04-02 | Okay | 2 | No |
| construct-binary-tree-preorder-inorder-solved.md | Construct Binary Tree Pre+Inorder | tree / recursion / HashMap | 3 | 2026-04-02 | Okay | 2 | No |
| serialize-deserialize-binary-tree-solved.md | Serialize and Deserialize Binary Tree | tree / preorder / queue | 3 | 2026-04-02 | Okay | 2 | No |
| closest-bst-value-ii-solved.md | Closest BST Value II | bst / inorder / deque | 3 | 2026-04-02 | Okay | 2 | No |
| implement-trie-solved.md | Implement Trie | trie / design | 3 | 2026-04-03 | Okay | 2 | No |
| design-add-search-words-solved.md | Design Add and Search Words | trie / dfs / wildcard | 3 | 2026-04-03 | Okay | 2 | No |
| geometric-sequence-triplets-solved.md | Geometric Sequence Triplets | array / fix-middle / count | 3 | 2026-04-03 | Okay | 2 | No |
| is-graph-bipartite-solved.md | Is Graph Bipartite | graph / bfs / 2-coloring | 3 | 2026-04-03 | Okay | 2 | No |
| longest-increasing-path-matrix-solved.md | Longest Increasing Path in Matrix | graph / dfs / memoization | 3 | 2026-04-03 | Okay | 2 | No |
| clone-graph-solved.md | Clone Graph | graph / bfs / hashmap | 3 | 2026-04-04 | Okay | 2 | No |
| min-heap-construction-solved.md | Min Heap Construction | heap / design / sift-down | 3 | 2026-04-04 | Okay | 2 | No |
