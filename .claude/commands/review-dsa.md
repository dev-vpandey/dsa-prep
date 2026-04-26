Load @.claude/skills/srs-revision-coach/SKILL.md for interval calculations and mode assignment.

Then run a revision session over @dsa-prep/notes/ using this flow:

## Step 1 — Setup
Ask: "Done a new problem today? (Recommended: do one first before reviews.)"
Ask: "How much time do you have? (minutes)"
Read @dsa-prep/notes/REVIEW.md and @dsa-prep/notes/GAP-DRILLS.md.
If GAP-DRILLS.md has open gaps, show them before the session plan so the user is aware.
Flag any card whose row is missing required fields before proceeding.

## Step 2 — Build Queue
Hard cap: 5 problems per session. No exceptions.
Priority order:
1. Blank rating (Full mode)
2. Weak rating (Full mode)
3. Overdue 3+ days (force Blitz regardless of Stage — see Overdue Triage rule in SKILL.md)
4. Overdue 1-2 days (normal mode assignment)
5. Due today (normal mode assignment)
Max 2 per pattern tag.
If more than 5 qualify, the lowest-priority ones simply stay for the next session. Do NOT defer by updating REVIEW.md dates.

## Step 3 — Session Plan
Show before starting, always:
```
⏱ [N] min — [date]

#  Problem          Stage  Mode   Reason
1  Two Sum          2      Full   Blank
2  Word Search II   3      Blitz  Overdue 4d
3  Clone Graph      5      Blitz  Stage 5+

Remaining due: X more — next session.
```
Ask: "Ready? Starting with #1."

## Step 4 — Per Problem

Full mode:
- Show problem name and problem link only. No tag, no card yet.
- Wait for recall attempt.
- "hint" → one nudge, direction only, no algorithm name
- "blank" → mark Blank, show card immediately, move on
- "give me a test case" or any request for a test case → ALWAYS use the exact test case from the card's Dry Run section. Never invent one.
- After attempt: ask "What's the time and space complexity?" — wait for answer, then reveal card, compare what was right vs missed, factor complexity into rating

Snippet mode:
- Show problem name and problem link only. No tag, nothing else.
- Prompt: "Write comments for parts you know cold + full code for parts you think are tricky."
- "blank" → mark Blank, show full card, move on
- After attempt:
  - Verify comments are correct
  - Verify code snippets are correct
  - If a critical part has no comment AND no code → ask user to cover it before rating
  - Ask "What's the time and space complexity?" — wait for answer
  - Once coverage + complexity are complete and correct → rate immediately, no follow-up
- If comments are wrong on flow → Weak regardless of code
- If critical code snippet has a bug → Okay at best, Weak if severe
- Wrong complexity with correct code → cap at Okay
- If submission is complete and correct → rate immediately and move on

Blitz mode:
- Show: "Problem: [name] — [tag] · Core insight in one sentence: ___?"
- "yes" → follow up: "Time and space?" — correct → Strong, next problem instantly; wrong → Okay
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

Then always show:
📄 Card: @notes/[file].md — say "move on" to continue, or review the card first.

Wait for "move on" before starting the next problem.

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

Then for every Weak or Blank, classify and log to GAP-DRILLS.md:
- **Muscle** (right approach, fumbled same mechanic again) → prescribe specific cold-write drill. No extra problems.
- **Conceptual** (wrong approach or missed core insight) → after reviews, give up to 2 fresh problems from same tag (Full mode, no hints unless asked). Strong = closed. Still weak after 2 → defer to tomorrow.
- When in doubt → conceptual.
- Update GAP-DRILLS.md: add row on new gap, mark Closed when mastered, purge Closed rows older than 7 days.