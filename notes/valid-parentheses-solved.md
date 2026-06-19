# Valid Parentheses — Easy
Problem Link: https://leetcode.com/problems/valid-parentheses/
Solved Date: 2026-03-24
Pattern Tag: stack / matching / bracket-validation

## SRS Tracking
- Stage: 5
- Review Date: 2026-08-02
- Last Rating: Okay
- Review Count: 9
- Graduated: No

---

# Real World Analogy
Like checking if all open folders on a computer have matching close operations — each time you close one, it must match the most recently opened one (LIFO order).

## Core Insight
Push open brackets onto a stack. On every closing bracket, the top of the stack must be its matching open bracket. Stack must be empty at the end.

## Approach
Use a stack and a map from opening → closing brackets. For each character: if `containsKey(c)` it's an open bracket → push; else it's closing → check `bmap.get(stack.peek()).equals(c)`, pop if match else return false. After loop, stack must be empty.

## Mental Model
```
┌──────────────────────────────────┬───────────────────────────────────────────┐
│ Decision                         │ Why                                       │
├──────────────────────────────────┼───────────────────────────────────────────┤
│ Map open → closing               │ containsKey(c) tells you it's an open     │
│ '(' → ')', '[' → ']', '{' → '}' │ bracket — avoids NPE on closing keys      │
├──────────────────────────────────┼───────────────────────────────────────────┤
│ Check stack empty before peek    │ "]" or ")..." with empty stack → false    │
│                                  │ avoids EmptyStackException                │
├──────────────────────────────────┼───────────────────────────────────────────┤
│ Stack must be empty at end       │ "((" is invalid — unclosed brackets must  │
│                                  │ fail even if nothing was mismatched       │
└──────────────────────────────────┴───────────────────────────────────────────┘
```

## Pseudocode
```
stack = []
map = {'(':')', '[':']', '{':'}'}
for each char c in s:
    if map.containsKey(c): push c       // open bracket
    else:
        if stack empty OR map.get(stack.peek()) != c: return false
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
        Map<Character, Character> bmap = Map.of('(', ')', '{', '}', '[', ']');
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            if (bmap.containsKey(c)) stack.push(c);
            else {
                if (!stack.isEmpty() && bmap.get(stack.peek()).equals(c)) stack.pop();
                else return false;
            }
        }
        return stack.isEmpty();
    }
}
```
