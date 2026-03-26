Load @.claude/skills/srs-revision-coach/SKILL.md for interval calculations and mode assignment.

Then run a revision session over @dsa-prep/notes/ using this flow:

## Step 1 — Setup
Ask: "How much time do you have? (minutes)"
Read @dsa-prep/notes/REVIEW.md — this is the index of all problems with Stage, Review Date, Last Rating, Pattern Tag, and file reference.
Flag any card whose row is missing required fields before proceeding.

## Step 2 — Build Queue
Priority: Blank first → Weak → due today → overdue. Max 5 per session. Max 2 per pattern tag. Hard daily cap: 10 problems total per day.
Assign Full or Blitz per problem using the mode assignment rules from the skill.
Overflow handling: if more than 10 problems are due today, defer the lowest-priority ones by 1 day — update their Review Date in REVIEW.md immediately, don't just note them as overflow.
List deferred problems so the user knows what moved.

## Step 3 — Session Plan
Show before starting, always:
```
⏱ [N] min — [date]

#  Problem          Stage  Mode   Reason
1  Two Sum          2      Full   Stage 1-2
2  Word Search II   4      Full   last: Blank
3  Clone Graph      5      Blitz  Stage 5+

Overflow (tomorrow): X, Y
```
Ask: "Ready? Starting with #1."

## Step 4 — Per Problem

Full mode:
- Show problem name, problem link + pattern tag only. No card yet.
- Wait for recall attempt.
- "hint" → one nudge, direction only, no algorithm name
- "blank" → mark Blank, show card immediately, move on
- "give me a test case" or any request for a test case → ALWAYS use the exact test case from the card's Dry Run section. Never invent one.
- After attempt: reveal card, compare what was right vs missed, assign rating

Snippet mode:
- Show problem name, problem link + pattern tag only. Nothing else.
- Prompt: "Write comments for parts you know cold + full code for parts you think are tricky."
- "blank" → mark Blank, show full card, move on
- After attempt:
  - Verify comments are correct
  - Verify code snippets are correct
  - If a critical part has no comment AND no code → ask user to cover it before rating
  - Once coverage is complete and aligned → rate immediately, no follow-up
- If comments are wrong on flow → Weak regardless of code
- If critical code snippet has a bug → Okay at best, Weak if severe
- If submission is complete and correct → rate immediately and move on

Blitz mode:
- Show: "Problem: [name] — [tag] · Core insight in one sentence: ___?"
- "yes" → Strong, next problem instantly
- "no" → show core insight from card, Blank, move on
- No pseudocode, no discussion

After every problem output:
```
Rating: [✅/🟡/🔴/❌]
✅ Got: ...
❌ Missed: ...
Next review: YYYY-MM-DD (Stage X → Y)
Update @dsa-prep/notes/[file].md: Stage / Review Date / Last Rating / Review Count
Update @dsa-prep/notes/REVIEW.md: same row — Stage / Review Date / Last Rating / Review Count
```

## Initial Stage for Newly Solved Problems
When saving a card after a first solve (not a review), set Stage based on MAANG rating:
- 5/5 → Stage 3 (nailed it clean, 7-day first review)
- 4/5 → Stage 2 (one minor miss, 3-day first review)
- 3/5 and below → Stage 1 (standard 1-day review)

Graduation: when Stage hits 6 with Strong, output graduation notice and
append to @dsa-prep/notes/GRADUATED.md: name | tag | date | next ping +90 days

## Step 5 — Session Summary
Always show at the end:
```
📊 Session Summary
✅ Strong: ...  🟡 Okay: ...  🔴 Weak: ...  ❌ Blank: ...
🎓 Graduated: ...
Weakest pattern this week: ...
Next session: YYYY-MM-DD — N problems due
```