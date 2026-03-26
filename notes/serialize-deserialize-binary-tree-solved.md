# Serialize and Deserialize Binary Tree — Hard
Problem Link: https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
Solved Date: 2026-03-25
Pattern Tag: tree / preorder-dfs / string-encoding / queue / serialization

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like packing a family tree into a letter and unpacking it at the destination — you include a placeholder for "no child" so the recipient can reconstruct the exact structure.

## Core Insight
Serialize with preorder DFS, encoding null nodes as `"#"`. Deserialize by splitting on delimiter and using a queue; consume values in preorder sequence to reconstruct.

## Approach
**Serialize**: preorder DFS. Null → append `"#"`, else append `val` + separator, recurse left then right.
**Deserialize**: split serialized string by separator into queue. `build()`: poll front; if `"#"` return null; else create node, recurse left then right.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Encode nulls explicitly as "#"       │ Without nulls, can't tell if a node   │
│                                      │ has 0, 1, or 2 children              │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Queue for deserialization            │ Consume tokens in order (FIFO);       │
│                                      │ preorder traversal matches insertion  │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Preorder: root then left then right  │ Root first = reconstruct root before  │
│                                      │ deciding which tokens go to subtrees  │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
serialize(root):
    if root == null: return "#,"
    return root.val + "," + serialize(root.left) + serialize(root.right)

deserialize(data):
    queue = ArrayDeque(data.split(","))
    return build(queue)

build(queue):
    val = queue.poll()
    if val == "#": return null
    node = new TreeNode(parseInt(val))
    node.left = build(queue)
    node.right = build(queue)
    return node
```

## Complexity
- Time: O(n) — visit each node once for both serialize and deserialize
- Space: O(n) — recursion stack + queue

## Watch Out For
- Use `","` as delimiter (not `" "`) — node values can be negative (`-1` has no space issues)
- Queue ensures tokens are consumed in the correct order without index tracking
- Deserialize: build LEFT before RIGHT (mirrors preorder)

## Dry Run
```
Tree:     1
         / \
        2   3
           / \
          4   5

Serialize: "1,2,#,#,3,4,#,#,5,#,#,"

Deserialize:
queue: [1,2,#,#,3,4,#,#,5,#,#]
build: poll 1 → Node(1)
  left: build: poll 2 → Node(2)
    left: build: poll # → null
    right: build: poll # → null
  right: build: poll 3 → Node(3)
    left: build: poll 4 → Node(4)
      ...
Result: original tree ✓
```

## Boilerplate Template
```java
import java.util.*;

public class Codec {
    public String serialize(TreeNode root) {
        if (root == null) return "#,";
        return root.val + "," + serialize(root.left) + serialize(root.right);
    }

    public TreeNode deserialize(String data) {
        Queue<String> queue = new ArrayDeque<>(Arrays.asList(data.split(",")));
        return build(queue);
    }

    private TreeNode build(Queue<String> queue) {
        String val = queue.poll();
        if ("#".equals(val)) return null;
        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left = build(queue);  // reconstruct left before right
        node.right = build(queue);
        return node;
    }
}
```
