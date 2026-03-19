# Skill: SRS Interval Calculator
# Reusable by: any prep folder with pattern cards (DSA, behavioral, HLD, LLD)
# Loaded by: commands that need to calculate review intervals and assign review modes

---

## Stage → Base Interval
| Stage | Interval |
|---|---|
| 1 | 1 day |
| 2 | 3 days |
| 3 | 7 days |
| 4 | 14 days |
| 5 | 30 days |
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
Given time available and card state, assign Full or Blitz:
- Stage 1–2 → Full (always, ignore time)
- Last rating Blank or Weak → Full (always, ignore time)
- Stage 3–4, time ≥ 20 min → Full
- Stage 3–4, time < 20 min → Blitz
- Stage 5–6 → Blitz
- Graduated → Blitz

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