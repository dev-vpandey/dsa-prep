# Evaluate Expression — Hard
Link: [Evaluate Expression](https://bytebytego.com/exercises/coding-patterns/stacks/evaluate-expression)
Solved Date: 2026-03-02
Review Date: 2026-03-09

## Core Insight
Use a stack to save and restore the outer expression's state (`res` + `sign`) each time you enter a parenthesized sub-expression, then merge the sub-result back using the saved sign when you hit `)`.

## Approach
Scan character by character: build multi-digit numbers in `curr`, flush into `res` on every operator. On `(`, push the current `res` and `sign` onto the stack and reset both for the inner level. On `)`, finalize the inner result, multiply by the saved sign, and add to the saved outer `res`. At the end, `curr` may hold the last number that was never flushed by an operator — the return statement handles it.

## Mental Model
```
┌───────────────────────────┬──────────────────────────────────────────────────────┐
│ Decision                  │ Why                                                  │
├───────────────────────────┼──────────────────────────────────────────────────────┤
│ curr builds digit-by-     │ Numbers can be multi-digit; multiply by 10 before    │
│ digit (curr*10 + digit)   │ adding each new digit shifts previous digits left    │
├───────────────────────────┼──────────────────────────────────────────────────────┤
│ sign is set BEFORE the    │ sign belongs to the next number, not the current one;│
│ number, not after         │ it is read when you finally flush curr               │
├───────────────────────────┼──────────────────────────────────────────────────────┤
│ On '(': push res & sign,  │ You need a fresh accumulator for the inner level;    │
│ reset both to 0 / +1      │ the outer state must be preserved for later merge    │
├───────────────────────────┼──────────────────────────────────────────────────────┤
│ Sign pushed before '('    │ The '-' before '(' applies to the ENTIRE result of   │
│ is the sub-expression sign│ the sub-expression, so it must be saved as a unit    │
├───────────────────────────┼──────────────────────────────────────────────────────┤
│ On ')': res*pop() +pop()  │ pop() gives saved sign → flip inner result if needed;│
│                           │ second pop() gives saved outer res → merge back in   │
├───────────────────────────┼──────────────────────────────────────────────────────┤
│ return res + curr * sign  │ The last number is never followed by an operator, so │
│                           │ curr is never flushed inside the loop — must add it  │
│                           │ manually; if string ends with ')', curr is 0 anyway  │
└───────────────────────────┴──────────────────────────────────────────────────────┘
```

## Pseudocode
```
curr = 0, res = 0, sign = +1

for each char c:
  if digit:
    curr = curr * 10 + digit_value(c)

  if '+' or '-':
    res = res + curr * sign     // flush curr into res
    sign = (c == '-') ? -1 : 1  // update sign for NEXT number
    curr = 0

  if '(':
    stack.push(res)             // save outer accumulator
    stack.push(sign)            // save sign that applies to sub-expression
    res = 0                     // fresh accumulator for inner level
    sign = +1                   // inner level starts positive

  if ')':
    res = res + curr * sign     // flush last number of sub-expression
    res = res * stack.pop()     // apply saved sub-expression sign
    res = res + stack.pop()     // merge into outer accumulator
    curr = 0

return res + curr * sign        // catch the last number if never flushed
```

## Complexity
- Time: O(n) — each character is visited exactly once
- Space: O(n) — stack grows proportional to nesting depth; worst case all chars are `(`

## Watch Out For
- Multi-digit numbers: `curr = curr * 10 + (c - '0')`, not just `curr = c - '0'`
- `curr` is only flushed on `+`, `-`, `)` — if the string ends with a number, the return must add `curr * sign`
- `sign` pushed onto the stack is the sign *before* the `(`, not inside it — reset sign to `+1` after pushing
- On `)`, reset `curr = 0` but do NOT reset `sign` — the next char will set sign correctly

## Dry Run
Input: `"18-(7+(2-4))"`

```
char  curr  sign  res       stack              action
──────────────────────────────────────────────────────────────────
'1'    1     +1    0        []                 build curr
'8'    18    +1    0        []                 build curr
'-'    0     -1    18       []                 flush: res=0+18*1=18, sign=-1, curr=0
'('    0     +1    0        [18, -1]           push res=18, push sign=-1; reset res=0 sign=+1
'7'    7     +1    0        [18, -1]           build curr
'+'    0     +1    7        [18, -1]           flush: res=0+7*1=7, sign=+1, curr=0
'('    0     +1    0        [18,-1, 7,+1]      push res=7, push sign=+1; reset res=0 sign=+1
'2'    2     +1    0        [18,-1, 7,+1]      build curr
'-'    0     -1    2        [18,-1, 7,+1]      flush: res=0+2*1=2, sign=-1, curr=0
'4'    4     -1    2        [18,-1, 7,+1]      build curr
')'    0      -    5→-2     [18, -1]           flush: res=2+4*(-1)=-2
                                               res=-2 * pop(+1) = -2
                                               res=-2 + pop(7)  = 5, curr=0
')'    0      -    13       []                 flush: res=5+0*sign=5
                                               res=5  * pop(-1) = -5
                                               res=-5 + pop(18) = 13, curr=0
──────────────────────────────────────────────────────────────────
return: res + curr * sign = 13 + 0 = 13 ✓
```

## Why `return res + curr * sign`
The last number in any expression is NEVER followed by an operator, so `curr` never gets flushed inside the loop. Example — `"1+2"`:
```
'1' → curr=1
'+' → res=1, curr=0, sign=+1
'2' → curr=2   ← sits here forever, loop ends
return: 1 + 2*1 = 3 ✓   (just `return res` would give 1 — wrong)
```
If the string ends with `)`, the `)` block resets `curr=0`, so `curr*sign = 0` and only `res` matters — safe either way.

## Pattern Tag
`stack` / `expression-evaluation` / `parentheses` / `sign-tracking`

## Boilerplate Template
```java
import java.util.*;

public class Main {
    public Integer evaluate_expression(String s) {
        Stack<Integer> stack = new Stack<>();
        int curr = 0, res = 0, sign = 1;

        for (var c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                curr = curr * 10 + (c - '0');

            } else if (c == '+' || c == '-') {
                res = res + curr * sign;
                sign = (c == '-') ? -1 : 1;
                curr = 0;

            } else if (c == '(') {
                stack.push(res);
                stack.push(sign);
                res = 0;
                sign = 1;

            } else { // ')'
                res = res + curr * sign;
                res = res * stack.pop();  // saved sub-expression sign
                res = res + stack.pop();  // saved outer res
                curr = 0;
            }
        }

        return res + curr * sign; // flush last number if string didn't end with ')'
    }
}
```
