# Min Max Stack — Medium
Problem Link: [Min Stack](https://leetcode.com/problems/min-stack/description/) | [Max Stack](https://leetcode.com/problems/max-stack/description/)
Solved Date: 2026-03-01
Pattern Tag: stack / design / monotonic-tracking / two-stack
Review Date: 2026-03-23

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-06
- Last Rating: Strong
- Review Count: 3
- Graduated: No

---

# Real World Analogy
—

## Core Insight
Maintain a parallel stack that tracks the running min AND max at every level — so any push/pop keeps them in sync with zero extra lookup cost.

## Approach
Push to both `mainStack` and a parallel `minMaxStack` (each entry holds the current min/max up to that point). For `popMax()`, temporarily pop elements into a temp stack until you reach the max, remove it, then push everything back — the parallel stack auto-updates on each push/pop.

## Mental Model
```
┌──────────────────────┬───────────────────────────────────────────────────┐
│ Decision             │ Why                                               │
├──────────────────────┼───────────────────────────────────────────────────┤
│ Parallel minMaxStack │ O(1) getMin/getMax without scanning mainStack      │
├──────────────────────┼───────────────────────────────────────────────────┤
│ Each entry stores    │ When you pop, the previous min/max is immediately  │
│ running min AND max  │ restored — no recomputation needed                │
├──────────────────────┼───────────────────────────────────────────────────┤
│ temp stack in        │ Preserves original order while getting to the max  │
│ popMax()             │ element buried in the stack                        │
├──────────────────────┼───────────────────────────────────────────────────┤
│ Use .equals() not != │ Integer is an object — != compares references,     │
│ for Integer compare  │ not values; breaks outside [-128, 127] cache range │
├──────────────────────┼───────────────────────────────────────────────────┤
│ Call push() not      │ push() updates minMaxStack too — raw mainStack     │
│ mainStack.push() in  │ .push() skips that, leaving stacks out of sync     │
│ popMax() restore     │                                                    │
└──────────────────────┴───────────────────────────────────────────────────┘
```

## Pseudocode
```
push(val):
  mainStack.push(val)
  newMinMax = { min: min(lastMin, val), max: max(lastMax, val) }
  minMaxStack.push(newMinMax)

pop():
  minMaxStack.pop()
  return mainStack.pop()

getMin(): return minMaxStack.peek().min
getMax(): return minMaxStack.peek().max

popMax():
  max = getMax()
  temp = empty stack
  while mainStack.peek() != max:
    temp.push(pop())          // pop() syncs both stacks
  pop()                       // remove the max from both stacks
  while temp not empty:
    push(temp.pop())          // push() re-syncs minMaxStack
  return max
```

## Complexity
- Time: O(1) for push, pop, peek, getMin, getMax — O(n) for popMax (may traverse full stack)
- Space: O(n) — parallel minMaxStack grows with every push

## Watch Out For
- `mainStack.peek() != max` → use `.equals(max)` — Integer reference equality breaks for values outside [-128, 127]
- In `popMax()` restore loop, call `push()` not `mainStack.push()` — otherwise `minMaxStack` goes out of sync
- `peekMax()` doesn't exist — use `getMax()`

## Dry Run
```
push(5) → main:[5]        minMax:[{min:5,max:5}]
push(1) → main:[5,1]      minMax:[{5,5},{min:1,max:5}]
push(5) → main:[5,1,5]    minMax:[{5,5},{1,5},{min:1,max:5}]
push(3) → main:[5,1,5,3]  minMax:[{5,5},{1,5},{1,5},{min:1,max:5}]

popMax():
  max = 5
  temp = []
  peek=3, 3.equals(5)? No → temp:[3], pop both stacks
    main:[5,1,5]  minMax:[{5,5},{1,5},{1,5}]
  peek=5, 5.equals(5)? Yes → stop
  pop() → removes 5 from both
    main:[5,1]    minMax:[{5,5},{1,5}]
  push(3) back:
    main:[5,1,3]  minMax:[{5,5},{1,5},{min:1,max:5}]
  return 5 ✓
```

## Boilerplate Template
```java
import java.util.*;

class MinMaxStack {
    Stack<Integer> mainStack = new Stack<>();
    Stack<Map<String, Integer>> minMaxStack = new Stack<>();

    public int peek() {
        return mainStack.peek();
    }

    public int pop() {
        minMaxStack.pop();
        return mainStack.pop();
    }

    public void push(Integer number) {
        mainStack.push(number);
        updateMinMax(number);
    }

    private void updateMinMax(int val) {
        Map<String, Integer> entry = new HashMap<>();
        if (minMaxStack.isEmpty()) {
            entry.put("min", val);
            entry.put("max", val);
        } else {
            Map<String, Integer> last = minMaxStack.peek();
            entry.put("min", Math.min(last.get("min"), val));
            entry.put("max", Math.max(last.get("max"), val));
        }
        minMaxStack.push(entry);
    }

    public int getMin() {
        return minMaxStack.peek().get("min");
    }

    public int getMax() {
        return minMaxStack.peek().get("max");
    }

    public int popMax() {
        int max = getMax();
        Stack<Integer> temp = new Stack<>();

        while (!mainStack.peek().equals(max)) {
            temp.push(pop()); // pop() keeps both stacks in sync
        }
        pop(); // remove the max from both stacks

        while (!temp.isEmpty()) {
            push(temp.pop()); // push() re-syncs minMaxStack
        }

        return max;
    }
}
```
