# Clone Graph — Medium
Problem Link: https://leetcode.com/problems/clone-graph/
Solved Date: 2026-03-25
Pattern Tag: graph / bfs / hashmap / deep-copy

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like photocopying a web of contacts — each person has a unique ID and a list of connections. You keep a log of who you've already copied so you don't make duplicates when the same person appears as a contact of multiple people.

## Core Insight
BFS/DFS the original graph. HashMap maps original node → cloned node. When processing a node's neighbors: if neighbor already in map, use existing clone; otherwise create new clone and add to queue.

## Approach
BFS from the given node. `clones` HashMap stores `original → clone`. For each polled node: for each neighbor, if not in `clones` → create clone and enqueue. Then add the clone of the neighbor to current clone's neighbors list.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ HashMap<Node, Node>                  │ Tracks which originals are already    │
│ original → clone                     │ cloned; prevents duplicates in        │
│                                      │ cyclic graphs                         │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Create clone BEFORE enqueuing        │ Must add to map before processing so  │
│                                      │ later references find it in map       │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Add to neighbors AFTER clone exists  │ Neighbors are built as we process;    │
│                                      │ the cloned node just needs val at     │
│                                      │ creation time                         │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
if node == null: return null

clones = HashMap
clones[node] = new Node(node.val)
queue = [node]

while queue not empty:
    cur = queue.poll()
    for each neighbor of cur:
        if neighbor not in clones:
            clones[neighbor] = new Node(neighbor.val)
            queue.add(neighbor)
        clones[cur].neighbors.add(clones[neighbor])

return clones[node]
```

## Complexity
- Time: O(V + E) — visit every node and edge once
- Space: O(V) — HashMap + BFS queue

## Watch Out For
- Handle `null` input (empty graph)
- Create clone and put in map BEFORE enqueuing (not after)
- Always add the cloned neighbor to the current clone's neighbors list (even if neighbor was already cloned)

## Dry Run
```
Original: 1 -- 2 -- 3 -- 4 -- 1 (cycle)

Start: clone[1]=Node(1), queue=[1]
Process 1: neighbors=[2,4]
  2 not in map → clone[2]=Node(2), enqueue
  4 not in map → clone[4]=Node(4), enqueue
  clone[1].neighbors = [clone[2], clone[4]]
Process 2: neighbors=[1,3]
  1 in map → just add
  3 not in map → clone[3]=Node(3), enqueue
  clone[2].neighbors = [clone[1], clone[3]]
Process 4: neighbors=[3,1]
  3 in map → just add; 1 in map → just add
  clone[4].neighbors = [clone[3], clone[1]]
Process 3: neighbors=[2,4]
  both in map
  clone[3].neighbors = [clone[2], clone[4]]
Return clone[1] ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) return null;

        Map<Node, Node> clones = new HashMap<>();
        clones.put(node, new Node(node.val));
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (Node neighbor : cur.neighbors) {
                if (!clones.containsKey(neighbor)) {
                    clones.put(neighbor, new Node(neighbor.val));
                    queue.offer(neighbor);
                }
                // always wire the neighbor into the current clone
                clones.get(cur).neighbors.add(clones.get(neighbor));
            }
        }
        return clones.get(node);
    }
}
```
