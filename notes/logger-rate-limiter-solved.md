# Logger Rate Limiter — Easy
Problem Link: https://leetcode.com/problems/logger-rate-limiter/
Solved Date: 2026-03-24
Pattern Tag: hashmap / design / timestamp-tracking

## SRS Tracking
- Stage: 5
- Review Date: 2026-04-23
- Last Rating: Strong
- Review Count: 8
- Graduated: No

---

# Real World Analogy
Like a spam filter — if you received the same message within the last 10 seconds, block it. Only let it through if enough time has passed since last seen.

## Core Insight
Store each message's last-printed timestamp. Allow printing if the message hasn't been seen OR was last printed more than 10 seconds ago.

## Approach
HashMap from message → last timestamp. On `shouldPrintMessage(timestamp, message)`: if not in map or `timestamp - map.get(message) >= 10`, update map and return true. Otherwise return false.

## Mental Model
```
┌────────────────────────────────────┬─────────────────────────────────────────┐
│ Decision                           │ Why                                     │
├────────────────────────────────────┼─────────────────────────────────────────┤
│ Update map even when allowing      │ Must record the NEW timestamp as the    │
│ (not just when blocking)           │ baseline for next 10-second window      │
├────────────────────────────────────┼─────────────────────────────────────────┤
│ Condition: timestamp - last >= 10  │ "Not within 10 seconds" means at least  │
│ (not > 10)                         │ 10 seconds apart — >= is correct        │
└────────────────────────────────────┴─────────────────────────────────────────┘
```

## Pseudocode
```
map = {}

shouldPrint(timestamp, message):
    if message not in map OR timestamp - map[message] >= 10:
        map[message] = timestamp
        return true
    return false
```

## Complexity
- Time: O(1) per call — HashMap lookup/insert
- Space: O(n) — one entry per unique message

## Watch Out For
- Update the map on `true` — don't forget to record the new timestamp
- Condition is `>= 10`, not `> 10`

## Dry Run
```
shouldPrint(1, "foo")  → not in map → map{foo:1}  → true
shouldPrint(2, "bar")  → not in map → map{bar:2}  → true
shouldPrint(3, "foo")  → 3-1=2 < 10 → false
shouldPrint(11, "foo") → 11-1=10 >= 10 → map{foo:11} → true ✓
```

## Boilerplate Template
```java
import java.util.*;

class Logger {
    private final Map<String, Integer> lastSeen = new HashMap<>();

    public boolean shouldPrintMessage(int timestamp, String message) {
        if (!lastSeen.containsKey(message) || timestamp - lastSeen.get(message) >= 10) {
            lastSeen.put(message, timestamp);
            return true;
        }
        return false;
    }
}
```
