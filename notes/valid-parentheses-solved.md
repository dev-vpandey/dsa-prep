# Valid Parentheses — Easy
Problem Link: https://leetcode.com/problems/valid-parentheses/
Solved Date: 2026-03-24
Pattern Tag: stack / matching / bracket-validation

## SRS Tracking
- Stage: 5
- Review Date: 2026-04-23
- Last Rating: Strong
- Review Count: 8
- Graduated: No

---

# Real World Analogy
Like checking if all open folders on a computer have matching close operations — each time you close one, it must match the most recently opened one (LIFO order).

## Core Insight
Push open brackets onto a stack. On every closing bracket, the top of the stack must be its matching open bracket. Stack must be empty at the end.

## Approach
Use a stack and a map from closing → opening brackets. For each character: if open bracket, push it; if closing, check if stack top matches — if not, return false. After loop, stack must be empty.

## Mental Model
```
┌──────────────────────────────────┬───────────────────────────────────────────┐
│ Decision                         │ Why                                       │
├──────────────────────────────────┼───────────────────────────────────────────┤
│ Map closing → opening            │ Clean lookup: when you see ')', check if  │
│ ')' → '(', ']' → '[', '}' → '{' │ top == '('. No if-else chains.            │
├──────────────────────────────────┼───────────────────────────────────────────┤
│ Check stack empty before pop     │ "]" or ")..." with empty stack → false    │
│                                  │ avoids EmptyStackException                │
├──────────────────────────────────┼───────────────────────────────────────────┤
│ Stack must be empty at end       │ "((" is invalid — unclosed brackets must  │
│                                  │ fail even if nothing was mismatched       │
└──────────────────────────────────┴───────────────────────────────────────────┘
```

## Pseudocode
```
stack = []
map = {')':'(', ']':'[', '}':'{'}
for each char c in s:
    if c is open bracket: push c
    else:
        if stack is empty OR stack.top != map[c]: return false
        pop stack
return stack.isEmpty()
```

## Complexity
- Time: O(n) — single pass
- Space: O(n) — stack holds at most all open brackets

## Watch Out For
- Empty stack when encountering a closing bracket → false immediately
- Return `stack.isEmpty()`, not just `true`, to catch unclosed opens like `"(("`
- Input `""` (empty string) → stack empty → return true (valid)

## Dry Run
Input: `"({[]})"`
```
'(' → push → stack: ['(']
'{' → push → stack: ['(', '{']
'[' → push → stack: ['(', '{', '[']
']' → map[']']='[', top='[' ✓ → pop → stack: ['(', '{']
'}' → map['}']='{', top='{' ✓ → pop → stack: ['(']
')' → map[')']]='(', top='(' ✓ → pop → stack: []
return stack.isEmpty() = true ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        Map<Character, Character> map = Map.of(')', '(', ']', '[', '}', '{');
        for (char c : s.toCharArray()) {
            if (map.containsKey(c)) {
                // closing bracket — check match
                if (stack.isEmpty() || stack.peek() != map.get(c)) return false;
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }
}
```
