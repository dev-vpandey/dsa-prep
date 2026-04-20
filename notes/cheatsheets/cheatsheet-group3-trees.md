# Group 3 — Trees
# Patterns: Tree DFS · Post-order · Global Variable · Tree BFS · BST · Construct Trees
# Prerequisite: Group 2 (Stack for iterative DFS, Queue for BFS)
# Daily read: open this when working on any tree pattern (3 min)

---

## PREREQUISITE CHAIN
```
DFS recursive (Group 1 concept)
    │
    ├──► Tree DFS (pre/in/post order)
    │       │
    │       ├──► Post-order with global max (diameter, max path sum)
    │       ├──► DFS with bounds (validate BST)
    │       └──► DFS with return value (balanced tree, LCA)
    │
    └──► Tree BFS (level order) ← uses Queue from Group 2
            │
            └──► Vertical order traversal (BFS + TreeMap)
```

---

## ── TREE DFS — RECURSIVE ────────────────────────────────────────────────────
# Real world: exploring a family tree — go as deep as possible before backtracking.

```java
// Pre-order: root → left → right (good for copying/serializing)
void preorder(TreeNode node) {
    if (node == null) return;
    process(node);
    preorder(node.left);
    preorder(node.right);
}

// In-order: left → root → right (gives sorted order for BST)
void inorder(TreeNode node) {
    if (node == null) return;
    inorder(node.left);
    process(node);
    inorder(node.right);
}

// Post-order: left → right → root (good for bottom-up info — height, size)
void postorder(TreeNode node) {
    if (node == null) return;
    postorder(node.left);
    postorder(node.right);
    process(node);
}

// DFS returning a value (height pattern)
int height(TreeNode node) {
    if (node == null) return 0;
    int left = height(node.left);
    int right = height(node.right);
    return 1 + Math.max(left, right);
}
```

⚠️ Watch out: always handle `node == null` as the FIRST line. Don't check `node.left == null` before recursing — let the recursive call handle null.

---

## ── POST-ORDER WITH GLOBAL VARIABLE ────────────────────────────────────────
# Use when: the answer combines info from both subtrees (diameter, max path sum, LCA).
# The trick: return something useful to the parent, track global answer separately.

```java
// Diameter of Binary Tree
int maxDiameter = 0;   // global — updated at each node

int depth(TreeNode node) {
    if (node == null) return 0;
    int left = depth(node.left);
    int right = depth(node.right);
    maxDiameter = Math.max(maxDiameter, left + right);   // path through this node
    return 1 + Math.max(left, right);                    // return height to parent
}

// Binary Tree Maximum Path Sum
int maxSum = Integer.MIN_VALUE;

int maxGain(TreeNode node) {
    if (node == null) return 0;
    int left = Math.max(0, maxGain(node.left));          // ignore negative branches
    int right = Math.max(0, maxGain(node.right));
    maxSum = Math.max(maxSum, node.val + left + right);  // path through this node
    return node.val + Math.max(left, right);             // return best single path
}
```

⚠️ Watch out: the global variable tracks the answer; the return value serves the parent. These are two different things — mixing them up is the most common mistake on path sum problems.

---

## ── DFS WITH SENTINEL (-1 pattern) ─────────────────────────────────────────
# Use when: invalid subtree needs to short-circuit the entire recursion.

```java
// Balanced Binary Tree
int checkHeight(TreeNode node) {
    if (node == null) return 0;
    int left = checkHeight(node.left);
    if (left == -1) return -1;                           // short-circuit
    int right = checkHeight(node.right);
    if (right == -1) return -1;                          // short-circuit
    if (Math.abs(left - right) > 1) return -1;          // this node unbalanced
    return 1 + Math.max(left, right);
}
// call: checkHeight(root) != -1
```

⚠️ Watch out: check for -1 sentinel IMMEDIATELY after each recursive call. Don't let it propagate through more computation.

---

## ── BST PATTERNS ────────────────────────────────────────────────────────────
# Real world: a phonebook sorted by name — left is smaller, right is larger at every step.

```java
// Validate BST — pass bounds down, not just compare with parent
boolean isValid(TreeNode node, long min, long max) {
    if (node == null) return true;
    if (node.val <= min || node.val >= max) return false;
    return isValid(node.left, min, node.val) &&
           isValid(node.right, node.val, max);
}
// call: isValid(root, Long.MIN_VALUE, Long.MAX_VALUE)
// use Long — node.val can be Integer.MIN_VALUE or MAX_VALUE

// Iterative in-order (Kth smallest)
Deque<TreeNode> stack = new ArrayDeque<>();
int count = 0;
TreeNode curr = root;
while (curr != null || !stack.isEmpty()) {
    while (curr != null) { stack.push(curr); curr = curr.left; }
    curr = stack.pop();
    if (++count == k) return curr.val;
    curr = curr.right;
}

// BST search
TreeNode search(TreeNode root, int val) {
    if (root == null || root.val == val) return root;
    return val < root.val ? search(root.left, val) : search(root.right, val);
}
```

⚠️ Watch out: validate BST using Long.MIN_VALUE / Long.MAX_VALUE as initial bounds — not Integer bounds. Integer.MIN_VALUE as a node value would fail the check incorrectly.

---

## ── TREE BFS — LEVEL ORDER ──────────────────────────────────────────────────
# Real world: reading a pyramid row by row from top to bottom.

```java
Queue<TreeNode> queue = new LinkedList<>();
queue.offer(root);
List<List<Integer>> result = new ArrayList<>();

while (!queue.isEmpty()) {
    int size = queue.size();                              // snapshot before inner loop
    List<Integer> level = new ArrayList<>();
    for (int i = 0; i < size; i++) {
        TreeNode node = queue.poll();
        level.add(node.val);
        if (node.left != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
    }
    result.add(level);
}

// Right side view — take last element of each level
// OR reverse pre-order DFS: root → right → left, first node at each depth
```

---

## ── CONSTRUCT TREE FROM TRAVERSALS ─────────────────────────────────────────
# Key insight: pre-order first element = root. In-order root position splits left/right.

```java
Map<Integer, Integer> inorderIndex = new HashMap<>();
// build: inorderIndex[val] = index in inorder array

int[] preorder, inorder;

TreeNode build(int preStart, int preEnd, int inStart, int inEnd) {
    if (preStart > preEnd) return null;
    int rootVal = preorder[preStart];
    TreeNode root = new TreeNode(rootVal);
    int mid = inorderIndex.get(rootVal);
    int leftSize = mid - inStart;
    root.left = build(preStart + 1, preStart + leftSize, inStart, mid - 1);
    root.right = build(preStart + leftSize + 1, preEnd, mid + 1, inEnd);
    return root;
}
// call: build(0, n-1, 0, n-1)
```

⚠️ Watch out: `leftSize = mid - inStart` (distance from left boundary to root in inorder). Use this to correctly split the preorder range.

---

## ── LCA — LOWEST COMMON ANCESTOR ───────────────────────────────────────────
# Real world: finding the closest common ancestor of two people in a family tree.

```java
TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) return root;
    TreeNode left = lca(root.left, p, q);
    TreeNode right = lca(root.right, p, q);
    if (left != null && right != null) return root;   // split here → root is LCA
    return left != null ? left : right;               // both on one side
}
```

⚠️ Watch out: if both left and right are non-null, the current root IS the LCA. Don't recurse further.

---

## Your Recurring Mistakes — Group 3
- **Global variable**: updating it in the return value instead of separately
- **BST validation**: using Integer bounds instead of Long — fails on extreme values
- **Sentinel pattern**: not checking -1 immediately after recursive call
- **Level-order**: forgetting `int size = queue.size()` snapshot — corrupts level boundary
- **LCA**: not handling the case where left AND right are both non-null

---

## Solved Card Examples
- Post-order global: `diameter-binary-tree-solved.md` · `binary-tree-maximum-path-sum-solved.md`
- BST: `validate-bst-solved.md` · `kth-smallest-bst-solved.md`
- Tree BFS: `maximum-width-binary-tree-solved.md` · `vertical-order-traversal-solved.md`
- Construct: `construct-binary-tree-preorder-inorder-solved.md`
- LCA: `lowest-common-ancestor-binary-tree-solved.md`
- Serialize: `serialize-deserialize-binary-tree-solved.md`
