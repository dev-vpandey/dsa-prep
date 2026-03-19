# Course Schedule — Medium
Link: https://leetcode.com/problems/course-schedule/
Solved Date: 2026-03-18
Review Date: 2026-03-25

## Real World Analogy
University course registration — you can't take Algorithms without taking Data Structures first.
If the catalog has a circular dependency (A needs B, B needs A), no student can ever graduate.

## Core Insight
The problem is cycle detection in a directed graph — if a valid course order exists, no cycle exists.

## Approach
Build a directed graph where prereq → course. Use Kahn's algorithm (BFS): repeatedly remove
courses with no remaining prerequisites. If all courses are processed, no cycle exists.
If any courses remain stuck (in-degree never hits 0), a cycle exists.

## Mental Model
┌─────────────────────────────────┬────────────────────────────────────────────┐
│ Decision                        │ Why                                        │
├─────────────────────────────────┼────────────────────────────────────────────┤
│ Edge direction: prereq → course │ Finishing prereq unlocks course            │
├─────────────────────────────────┼────────────────────────────────────────────┤
│ inDegree[course]++ per prereq   │ Tracks how many prereqs a course still     │
│                                 │ needs before it can be taken               │
├─────────────────────────────────┼────────────────────────────────────────────┤
│ Start queue with inDegree == 0  │ These have no prereqs — safe to take first │
├─────────────────────────────────┼────────────────────────────────────────────┤
│ processed == numCourses check   │ If stuck, a cycle exists — no explicit     │
│                                 │ cycle detection needed                     │
├─────────────────────────────────┼────────────────────────────────────────────┤
│ getOrDefault in BFS loop        │ Courses with no outgoing edges won't exist │
│                                 │ in map if using computeIfAbsent            │
└─────────────────────────────────┴────────────────────────────────────────────┘

## Pseudocode
```
build adj map: prereq → [courses it unlocks]
build inDegree[]: count prereqs per course

queue ← all courses with inDegree == 0
processed = 0

while queue not empty:
    curr = queue.poll()
    processed++
    for each course unlocked by curr:
        inDegree[course]--
        if inDegree[course] == 0: queue.add(course)

return processed == numCourses
```

## Complexity
- Time: O(V + E) — each course and each prereq edge processed exactly once
- Space: O(V + E) — adjacency map + inDegree array + queue

## Watch Out For
- pair[0] = course, pair[1] = prereq (easy to swap variable names)
- Use getOrDefault when using computeIfAbsent (missing keys for sink nodes)
- Graph may be disconnected — outer for loop handles all components

## Dry Run
prerequisites = [[1,0],[2,1],[3,2]], n = 4

adj:      {0:[1], 1:[2], 2:[3]}
inDegree: [0, 1, 1, 1]

Queue: [0]
  poll 0 → processed=1, inDegree[1]=0 → Queue:[1]
  poll 1 → processed=2, inDegree[2]=0 → Queue:[2]
  poll 2 → processed=3, inDegree[3]=0 → Queue:[3]
  poll 3 → processed=4, no neighbors

processed(4) == numCourses(4) → true ✅

Cycle case: add [1,3] → inDegree=[0,2,1,1]
Queue: [0]
  poll 0 → processed=1, inDegree[1]=1 → not added
Queue empty. processed(1) != 4 → false ✅

## Pattern Tag
topological-sort / kahn's-algorithm / cycle-detection / directed-graph / bfs

## Boilerplate Template
```java
public boolean canFinish(int numCourses, int[][] prerequisites) {
    int[] inDegree = new int[numCourses];
    Map<Integer, List<Integer>> adj = new HashMap<>();

    for (int[] pre : prerequisites) {
        int course = pre[0], prereq = pre[1];
        adj.computeIfAbsent(prereq, k -> new ArrayList<>()).add(course);
        inDegree[course]++;
    }

    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
        if (inDegree[i] == 0) queue.add(i);
    }

    int processed = 0;
    while (!queue.isEmpty()) {
        int curr = queue.poll();
        processed++;
        for (int next : adj.getOrDefault(curr, Collections.emptyList())) {
            if (--inDegree[next] == 0) queue.add(next);
        }
    }

    return processed == numCourses;
}
```
