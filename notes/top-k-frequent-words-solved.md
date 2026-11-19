# Top K Frequent Words — Medium
Problem Link: https://leetcode.com/problems/top-k-frequent-words/
Solved Date: 2026-06-21
Pattern Tag: heap / top-k / custom-comparator

## SRS Tracking
- Stage: 4
- Review Date: 2026-07-17
- Last Rating: Strong
- Review Count: 1
- Graduated: No

---

# Real World Analogy
Like a talent show keeping the best k performers — a bouncer at the door kicks out the weakest act whenever the room gets too full.

## Core Insight
A min-heap of size k keeps the top k candidates by always evicting the weakest one. The comparator must define "weakest" = lowest freq, then largest lex for ties.

## Approach
Build a frequency map. Push each word into a min-heap; evict (poll) when size > k. The comparator puts the least-wanted word at the top. Drain the heap into a list and reverse — heap drains ascending, output must be descending.

## Mental Model

╔══════════════════════════════════════════╦══════════════════════════════════════════════════════╗
║ Decision                                 ║ Why                                                  ║
╠══════════════════════════════════════════╬══════════════════════════════════════════════════════╣
║ Min-heap of size k                       ║ Evict worst; keep best k in O(n log k)               ║
║ Ascending freq in comparator             ║ Lowest freq = weakest = at heap top = evicted first  ║
║ b.word.compareTo(a.word) for equal freq  ║ See below — larger lex evicted first                 ║
║ Reverse after draining                   ║ Heap drains ascending; output must be descending     ║
╚══════════════════════════════════════════╩══════════════════════════════════════════════════════╝

**Why `b.word.compareTo(a.word)` and not `a.word.compareTo(b.word)`:**

Comparator returning negative → a is "smallest" → a gets evicted first.
We want the larger lex word evicted. So larger lex must return negative.

```
a="love", b="i"  →  "i".compareTo("love") = negative  →  a("love") evicted ✓
a="i",  b="love" →  "love".compareTo("i") = positive  →  a("i") stays    ✓
```

Flip to `a.compareTo(b)` → "i" evicted, "love" stays → wrong.
Rule: **comparator returns negative → a evicted. Want larger lex evicted → use `b.compareTo(a)`.**

## Pseudocode
```
freq = frequency map of words

minHeap = PriorityQueue with comparator:
    sort by freq ascending
    for equal freq: larger lex word is "smaller" (evicted first)

for each (word, count) in freq:
    push to heap
    if size > k: poll (evict weakest)

drain heap into list
reverse list
return list
```

## Complexity

### Time: O(n log k)

╔═══════════════════════╦════════════════╦══════════════════════════════════════════════╗
║ Component             ║ Cost           ║ Why                                          ║
╠═══════════════════════╬════════════════╬══════════════════════════════════════════════╣
║ Build freq map        ║ O(n)           ║ One pass over words                          ║
║ Heap inserts          ║ O(n log k)     ║ n inserts, heap size capped at k             ║
║ Drain + reverse       ║ O(k log k)     ║ k polls                                      ║
║ Total                 ║ O(n log k)     ║ Dominates                                    ║
╚═══════════════════════╩════════════════╩══════════════════════════════════════════════╝

### Space: O(n + k)

╔══════════════════╦══════════╦══════════════════════════════════════════════╗
║ Structure        ║ Size     ║ Why                                          ║
╠══════════════════╬══════════╬══════════════════════════════════════════════╣
║ freq map         ║ O(n)     ║ At most n unique words                       ║
║ heap             ║ O(k)     ║ Capped at k elements                         ║
╚══════════════════╩══════════╩══════════════════════════════════════════════╝

## Watch Out For
- **Drain order is inverted** — min-heap polls ascending (worst first); reverse before returning
- Comparator for equal freq: `b.word.compareTo(a.word)` → larger lex word is "smaller" → evicted first
- Evict with `if (heap.size() > k) heap.poll()` — poll AFTER adding, not before

## Dry Run
`words = ["the","day","is","sunny","the","the","the","sunny","is","is"], k = 4`
```
freq = {the:4, is:3, sunny:2, day:1}

heap after all inserts (size ≤ 4, no eviction):
  top → day(1), sunny(2), is(3), the(4)

drain: ["day","sunny","is","the"]
reverse: ["the","is","sunny","day"] ✓
```

## My Solution
```java
class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        List<String> res = new ArrayList<>();

        Map<String, Integer> wordMap = new HashMap<>();
        for (var word : words) wordMap.merge(word, 1, Integer::sum);

        PriorityQueue<Pair> minHeap = new PriorityQueue<>((a, b) -> {
            if (a.freq != b.freq) return a.freq - b.freq;
            return b.word.compareTo(a.word);
        });

        for (var entrySet : wordMap.entrySet()) {
            minHeap.offer(new Pair(entrySet.getKey(), entrySet.getValue()));
            if (minHeap.size() > k) minHeap.poll();
        }

        while (!minHeap.isEmpty()) res.add(minHeap.poll().word);

        Collections.reverse(res);
        return res;
    }
    record Pair(String word, int freq) {}
}
```

## Boilerplate Template
```java
class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> freq = new HashMap<>();
        for (String w : words) freq.merge(w, 1, Integer::sum);

        PriorityQueue<Map.Entry<String, Integer>> heap = new PriorityQueue<>((a, b) -> {
            if (!a.getValue().equals(b.getValue())) return a.getValue() - b.getValue(); // asc freq
            return b.getKey().compareTo(a.getKey());                                    // larger lex first
        });

        for (var e : freq.entrySet()) {
            heap.offer(e);
            if (heap.size() > k) heap.poll();
        }

        List<String> res = new ArrayList<>();
        while (!heap.isEmpty()) res.add(heap.poll().getKey());
        Collections.reverse(res);
        return res;
    }
}
```
