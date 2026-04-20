# Skill: SRS Interval Calculator
# Reusable by: any prep folder with pattern cards (DSA, behavioral, HLD, LLD)
# Loaded by: commands that need to calculate review intervals and assign review modes

---

## Stage → Base Interval
| Stage | Interval |
|---|---|
| 1 | 1 day |
| 2 | 3 days |
| 3 | 10 days |
| 4 | 21 days |
| 5 | 45 days |
| 6 | 90 days — graduated |

## Rating → Next Interval
| Rating | Next interval | Stage change |
|---|---|---|
| ✅ Strong | base × 1.5, round up | +1 |
| 🟡 Okay | base interval | no change |
| 🔴 Weak | base ÷ 2, min 1 day | no change |
| ❌ Blank | 1 day | reset to Stage 1 |

Graduated exception: Blank on a graduated card → reset to Stage 3 only, not Stage 1.

## Mode Assignment
Given card state, assign Full, Snippet, or Blitz:
- Last Rating Blank or Weak → Full (always, overrides everything)
- Stage 1, Last Rating — (never reviewed) → Full
- Stage 1–2, Last Rating Okay or Strong → Snippet
- Stage 3–4 → Snippet
- Stage 5–6 → Blitz
- Graduated → Blitz

## Snippet Mode — What It Means
Snippet mode = boilerplate as comments + full code only for tricky parts.
- User writes the overall structure/flow as comments (skeleton)
- User writes full code only for the sections listed in the card's Watch Out For
- Reviewer verifies: comments show correct understanding of flow AND code snippets are correct
- Rating applies to both combined

## SRS Tracking Block (required on every card)
```
## SRS Tracking
- Stage: 1
- Review Date: YYYY-MM-DD
- Last Rating: —
- Review Count: 0
- Graduated: No
```
If a card is missing this block, flag it before the session and show the default values above.

## After Each Review — What to Update
```
Stage: [old → new]
Review Date: [new date]
Last Rating: [Strong / Okay / Weak / Blank]
Review Count: [increment by 1]
Graduated: [Yes if Stage just hit 6 with Strong, otherwise No]
```

## Timeout Rule
If the user goes quiet for 3+ minutes during a review problem, prompt once: "Still working on it?"
If stuck or no response → rate Blank, show full card immediately, move on. One prompt only.

## Overdue Triage
Any problem overdue 3+ days → force Blitz mode regardless of Stage or Last Rating.
Goal: clear the debt fast. Correct core-insight recall counts as Okay minimum.

## Double-Strong Fast-Track (Stage 3 only)
If a Stage 3 problem was rated Strong last review AND is rated Strong again now → advance to Stage 4 immediately.
Do not wait for the remaining 10-day cycle. Update Stage in both the card and REVIEW.md.
Check: if Last Rating = Strong before this review, current Strong = fast-track.