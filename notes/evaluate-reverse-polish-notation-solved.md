# Evaluate Reverse Polish Notation — Medium
Problem Link: https://leetcode.com/problems/evaluate-reverse-polish-notation/
Solved Date: 2026-03-24
Pattern Tag: stack / expression-evaluation / postfix

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like a calculator that processes instructions from a recipe: when you see ingredients (numbers), put them on the counter (stack). When you see a cooking step (operator), take two items from the counter, combine them, and put the result back.

## Core Insight
Push numbers onto stack. On operator: pop two operands (order matters for `-` and `/`), apply operator, push result. Final stack top is the answer.

## Approach
Iterate tokens. If token is a number, push. If operator, pop `b` then `a` (so `a` is the left operand), compute `a op b`, push result.

## Mental Model
```
┌────────────────────────────────────┬─────────────────────────────────────────┐
│ Decision                           │ Why                                     │
├────────────────────────────────────┼─────────────────────────────────────────┤
│ Pop b FIRST, then a                │ Stack is LIFO — b was pushed after a.   │
│ (b = second operand)               │ For "4 3 -": a=4, b=3, result=4-3=1   │
├────────────────────────────────────┼─────────────────────────────────────────┤
│ Integer division truncates toward  │ Java default int division truncates     │
│ zero (not floor)                   │ toward zero — matches problem spec      │
├────────────────────────────────────┼─────────────────────────────────────────┤
│ Use equals() for string comparison │ Token is a String — use .equals()      │
│                                    │ not == for operator check               │
└────────────────────────────────────┴─────────────────────────────────────────┘
```

## Pseudocode
```
stack = []
for each token in tokens:
    if token is number:
        stack.push(parseInt(token))
    else:
        b = stack.pop()   // second operand (pushed later)
        a = stack.pop()   // first operand
        if token == "+": stack.push(a + b)
        if token == "-": stack.push(a - b)
        if token == "*": stack.push(a * b)
        if token == "/": stack.push(a / b)
return stack.pop()
```

## Complexity
- Time: O(n) — process each token once
- Space: O(n) — stack holds at most n/2 numbers

## Watch Out For
- Pop order: `b = pop()`, `a = pop()` → compute `a op b` (not `b op a`)
- Use `Integer.parseInt` (handles negative numbers like "-3")
- Use `switch` or `.equals()` for operator comparison

## Dry Run
`["2","1","+","3","*"]` → expected 9
```
"2" → stack: [2]
"1" → stack: [2,1]
"+" → b=1, a=2, 2+1=3 → stack: [3]
"3" → stack: [3,3]
"*" → b=3, a=3, 3*3=9 → stack: [9]
Return 9 ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String token : tokens) {
            switch (token) {
                case "+" -> { int b = stack.pop(), a = stack.pop(); stack.push(a + b); }
                case "-" -> { int b = stack.pop(), a = stack.pop(); stack.push(a - b); }
                case "*" -> { int b = stack.pop(), a = stack.pop(); stack.push(a * b); }
                case "/" -> { int b = stack.pop(), a = stack.pop(); stack.push(a / b); }
                default  -> stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }
}
```
