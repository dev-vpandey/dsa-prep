# Remove All Adjacent Duplicates In String — Easy
Problem Link: https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/
Solved Date: 2026-03-24
Pattern Tag: stack / string / character-matching

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like popping bubble wrap — when two adjacent bubbles are the same, they both pop and their neighbors might now be adjacent and match, causing a chain reaction.

## Core Insight
Use a stack. If the current character equals the top of the stack, pop (they annihilate). Otherwise push. The remaining stack characters form the answer.

## Approach
Iterate through the string. For each char: if stack is non-empty and top == current char, pop. Otherwise push. At the end, join the stack into a string.

## Mental Model
```
┌──────────────────────────────────┬─────────────────────────────────────────────┐
│ Decision                         │ Why                                         │
├──────────────────────────────────┼─────────────────────────────────────────────┤
│ Pop on match (annihilation)      │ Matching adjacent pair eliminated →         │
│                                  │ their outer neighbors become adjacent       │
├──────────────────────────────────┼─────────────────────────────────────────────┤
│ Stack preserves processing order │ Stack bottom = leftmost surviving char;     │
│                                  │ build result by iterating bottom to top     │
├──────────────────────────────────┼─────────────────────────────────────────────┤
│ Use StringBuilder as stack       │ Efficient: append = push, deleteCharAt =    │
│                                  │ pop, directly builds result string          │
└──────────────────────────────────┴─────────────────────────────────────────────┘
```

## Pseudocode
```
stack = []
for each char c in s:
    if stack not empty AND stack.top == c:
        stack.pop()
    else:
        stack.push(c)
return join(stack)
```

## Complexity
- Time: O(n) — each character pushed/popped at most once
- Space: O(n) — stack

## Watch Out For
- Use `StringBuilder` as the stack for O(1) string building at the end
- Chain reactions are handled naturally — each pop might expose the next pair

## Dry Run
`s = "abbaca"`
```
'a' → stack: [a]
'b' → 'b'!='a' → stack: [a,b]
'b' → 'b'=='b' → pop → stack: [a]
'a' → 'a'=='a' → pop → stack: []
'c' → stack: [c]
'a' → 'a'!='c' → stack: [c,a]
Result: "ca" ✓
```

## Boilerplate Template
```java
class Solution {
    public String removeDuplicates(String s) {
        StringBuilder sb = new StringBuilder(); // use as stack
        for (char c : s.toCharArray()) {
            int len = sb.length();
            if (len > 0 && sb.charAt(len - 1) == c) {
                sb.deleteCharAt(len - 1); // pop — annihilate pair
            } else {
                sb.append(c); // push
            }
        }
        return sb.toString();
    }
}
```
