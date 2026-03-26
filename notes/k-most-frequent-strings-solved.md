# Top K Frequent Words — Medium
Problem Link: https://leetcode.com/problems/top-k-frequent-words/
Solved Date: 2026-03-25
Pattern Tag: heap / frequency-map / custom-comparator / top-k

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like finding the k most popular songs on a playlist — count plays, keep a leaderboard of size k, and when two songs are tied, prefer alphabetical order.

## Core Insight
Build a frequency map. Use a min-heap of size k with a custom comparator: lower frequency gets polled first; on equal frequency, lexicographically larger gets polled first (so the heap retains the alphabetically smaller ones).

## Approach
Frequency map. Min-heap with comparator `(a, b) -> freqA == freqB ? b.compareTo(a) : freqA - freqB`. Add all words; when heap size > k, poll. Result = heap in reverse order.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Min-heap (not max-heap) of size k    │ Evict lowest-priority when > k;       │
│                                      │ keeps top k most frequent             │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ On equal freq: poll lex-LARGER       │ We want to KEEP lex-smaller words;    │
│ (comparator: b.compareTo(a))         │ min-heap polls "smallest" first       │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Reverse heap to get result           │ Heap order is least-priority first;   │
│                                      │ result needs most-frequent first      │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
freq = count each word
heap = min-heap with comparator:
    (a, b) -> freq[a]==freq[b] ? b.compareTo(a) : freq[a]-freq[b]

for each word:
    heap.offer(word)
    if heap.size() > k: heap.poll()

result = []
while heap not empty: result.addFirst(heap.poll())
return result
```

## Complexity
- Time: O(n log k) — n words, each heap op O(log k)
- Space: O(n + k) — frequency map + heap

## Watch Out For
- Tie-breaking: on same frequency, keep LOWER lexicographic word → poll HIGHER lex → comparator reverses string comparison
- Result must be reversed (heap gives ascending priority, result needs descending)

## Dry Run
`words = ["i","love","leetcode","i","love","coding"]`, k=2
```
freq: {i:2, love:2, leetcode:1, coding:1}
Heap (min by freq then rev-lex):
Add "i"→heap={i}, add "love"→heap={i,love}
heap size=k=2: don't evict
Add "leetcode"→heap size=3 → evict min: comparator picks "love" vs "leetcode" vs "i"
...eventually heap={i,love}
Reversed: ["i","love"] ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> freq = new HashMap<>();
        for (String w : words) freq.merge(w, 1, Integer::sum);

        // min-heap: poll lowest freq; on tie, poll lex-larger (keep lex-smaller)
        PriorityQueue<String> heap = new PriorityQueue<>((a, b) ->
            freq.get(a).equals(freq.get(b)) ? b.compareTo(a) : freq.get(a) - freq.get(b));

        for (String w : freq.keySet()) {
            heap.offer(w);
            if (heap.size() > k) heap.poll();
        }

        LinkedList<String> result = new LinkedList<>();
        while (!heap.isEmpty()) result.addFirst(heap.poll()); // reverse order
        return result;
    }
}
```
