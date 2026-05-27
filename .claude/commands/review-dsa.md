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
3. Overdue 3+ days (force Blitz regardless of Stage — see Overdue Triage rule in REFERENCE.md)
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
Load card from @notes/[file]-solved.md before starting each problem.
Run mode per @.claude/skills/srs-revision-coach/REFERENCE.md — follow mode steps exactly.
Mode determines: what to show, when to show it, how to rate, when to move on.

After every problem output:
```
Rating: [✅/🟡/🔴/❌]
✅ Got: ...  ❌ Missed: ...
Next review: YYYY-MM-DD (Stage X → Y)
Update @notes/[file]-solved.md: Stage / Review Date / Last Rating / Review Count
Update @notes/REVIEW.md: same row
📄 Card: @notes/[file].md — say "move on" to continue.
```
Wait for "move on" before starting next problem.

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