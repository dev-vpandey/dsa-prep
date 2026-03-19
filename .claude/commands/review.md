Load @.claude/skills/srs-revision-coach/SKILL.md for interval calculations and mode assignment.

Then run a revision session over @dsa-prep/notes/ using this flow:

## Step 1 — Setup
Ask: "How much time do you have? (minutes)"
Scan @dsa-prep/notes/ — extract Stage, Review Date, Last Rating, Pattern Tag from every card.
Flag any card missing the SRS Tracking block before proceeding.

## Step 2 — Build Queue
Priority: Blank first → Weak → due today → overdue. Max 5. Max 2 per pattern tag.
Assign Full or Blitz per problem using the mode assignment rules from the skill.
List overflow problems and tell me they're queued for tomorrow.

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
- Show problem name + pattern tag only. No card yet.
- Wait for recall attempt.
- "hint" → one nudge, direction only, no algorithm name
- "blank" → mark Blank, show card immediately, move on
- After attempt: reveal card, compare what was right vs missed, assign rating

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
```

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