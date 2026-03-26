# LRU Cache — Medium
Problem Link: https://leetcode.com/problems/lru-cache/
Solved Date: 2026-03-01
Pattern Tag: hashmap / doubly-linked-list / design / O(1)-data-structure
Review Date: 2026-03-04

## SRS Tracking
- Stage: 5
- Review Date: 2026-04-22
- Last Rating: Strong
- Review Count: 1
- Graduated: No

---

# Real World Analogy
—

## Core Insight
O(1) removal requires knowing the predecessor — DLL's `prev` pointer gives every node self-contained context to remove itself without traversal.

## Approach
Use a HashMap for O(1) key lookup and a Doubly Linked List to maintain recency order. The most recently used node lives just before a dummy tail; the least recently used lives just after a dummy head. On every get/put, move the accessed node to the tail-end; on eviction, remove from the head-end.

## Mental Model
```
┌─────────────────────┬──────────────────────────────────────────────────────────────────┐
│ Decision            │ Why                                                              │
├─────────────────────┼──────────────────────────────────────────────────────────────────┤
│ HashMap             │ O(1) lookup — "which node holds key K?"                         │
├─────────────────────┼──────────────────────────────────────────────────────────────────┤
│ DLL over SLL        │ SLL removal needs predecessor; DLL node carries prev so it can  │
│                     │ unlink itself in O(1) — HashMap's O(1) lookup would be wasted   │
│                     │ if removal were O(N)                                             │
├─────────────────────┼──────────────────────────────────────────────────────────────────┤
│ Dummy head & tail   │ Every node always has valid prev/next — no null edge-case        │
│                     │ checks; removeNode works identically for all positions           │
├─────────────────────┼──────────────────────────────────────────────────────────────────┤
│ LRU = head.next     │ Least recently used is always first real node after dummy head  │
├─────────────────────┼──────────────────────────────────────────────────────────────────┤
│ MRU = tail.prev     │ Most recently used is always last real node before dummy tail   │
├─────────────────────┼──────────────────────────────────────────────────────────────────┤
│ Node stores key     │ On eviction we must remove from HashMap too — node needs its    │
│                     │ own key to do keyToNodeMap.remove(node.key)                      │
└─────────────────────┴──────────────────────────────────────────────────────────────────┘
```

## Pseudocode
```
get(key):
  if key not in map → return -1
  moveToEnd(node)
  return node.value

put(key, value):
  if key exists → removeLru(existingNode)   // remove old position + map entry
  if size >= capacity → removeLru(head.next) // evict LRU
  create new node
  insertBeforeTail(newNode)
  map.put(key, newNode)

removeNode(node):
  node.prev.next = node.next
  node.next.prev = node.prev

insertBeforeTail(node):
  tailPrev.next = node
  node.prev = tailPrev
  node.next = tail
  tail.prev = node

moveToEnd(node):
  removeNode(node)
  insertBeforeTail(node)
```

## Complexity
- Time: O(1) — get and put are both O(1); HashMap lookup is O(1), all DLL pointer manipulations are a fixed number of steps
- Space: O(capacity) — HashMap and DLL each hold at most `capacity` entries

## Watch Out For
- Node must store its own **key** (not just value) — needed to remove from HashMap during eviction
- On `put` for an existing key: remove the old node FIRST, then check capacity, then insert new — otherwise capacity check is off by one
- `removeLru` must remove from the HashMap AND the list — easy to forget one
- `insertBeforeTail` order matters: set all four pointers or you'll lose a reference

## Dry Run
```
LRUCache(2)   → head ↔ tail,  map={}

put(1,1):
  insert node(1,1) before tail
  head ↔ [1,1] ↔ tail,  map={1→node1}

put(2,2):
  insert node(2,2) before tail
  head ↔ [1,1] ↔ [2,2] ↔ tail,  map={1→node1, 2→node2}

get(1):
  found node1, moveToEnd
  head ↔ [2,2] ↔ [1,1] ↔ tail,  map={1→node1, 2→node2}
  return 1

put(3,3):
  capacity full → evict head.next = node(2,2)
    map.remove(2), removeNode(node2)
  head ↔ [1,1] ↔ tail
  insert node(3,3) before tail
  head ↔ [1,1] ↔ [3,3] ↔ tail,  map={1→node1, 3→node3}

get(2):
  not in map → return -1  ✓

get(3):
  found node3, moveToEnd (already at end)
  return 3  ✓
```

## Boilerplate Template
```java
class DoublyLinkedListNode {
    int key, value;
    DoublyLinkedListNode next, prev;

    public DoublyLinkedListNode(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

class Solution {
    private int capacity;
    private Map<Integer, DoublyLinkedListNode> map;
    private DoublyLinkedListNode head, tail;

    public Solution(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new DoublyLinkedListNode(-1, -1);
        this.tail = new DoublyLinkedListNode(-1, -1);
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    private void removeNode(DoublyLinkedListNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insertBeforeTail(DoublyLinkedListNode node) {
        var tailPrev = tail.prev;
        tailPrev.next = node;
        node.prev = tailPrev;
        node.next = tail;
        tail.prev = node;
    }

    private void moveToEnd(DoublyLinkedListNode node) {
        removeNode(node);
        insertBeforeTail(node);
    }

    private void removeLru(DoublyLinkedListNode node) {
        map.remove(node.key);
        removeNode(node);
    }
}
```
