# DSA Prep — MAANG Coaching System

> **AI-coached DSA prep in Java 21. Combines active problem-solving with Spaced Repetition (SRS) to lock in patterns**

If this saves you time, a ⭐ on the repo helps others find it.

---

## Daily Rhythm

```
/start        →  New problem (30–40 min)
/review-dsa   →  Spaced repetition reviews (max 5, ~20 min)
```

New problem first. Reviews after. Never mix.

---

## Commands

╔════════════════╦══════════════════════════════════════════════════════╗
║ Command        ║ What it does                                         ║
╠════════════════╬══════════════════════════════════════════════════════╣
║ /start         ║ Finds what to work on next                           ║
║ /review-dsa    ║ Runs today's SRS review queue (max 5)                ║
║ /pattern X     ║ Jump to a specific pattern (e.g. /pattern dp)        ║
║ /retry X       ║ Re-attempt a previously solved problem               ║
║ /status        ║ Stage distribution + what's overdue                  ║
╚════════════════╩══════════════════════════════════════════════════════╝

---

## Hints During Solving

| You say          | You get                                               |
|------------------|-------------------------------------------------------|
| `hint`           | One-sentence intuition — no algorithm name            |
| `hint hint hint` | Pattern name + relevant cheatsheet section            |
| `stuck`          | Full Pattern Recognition Framework (SIZE/SHAPE/SMELL) |
| `code`           | Full Java 21 solution + dry run + complexity          |

---

## After You Solve

Claude rates you 1–5 (MAANG interviewer scale), saves a pattern card to `notes/[problem]-solved.md`, and schedules the next review:

╔══════════╦═════════╦═════════════╗
║ Rating   ║ Stage   ║ Next Review ║
╠══════════╬═════════╬═════════════╣
║ 5/5      ║ Stage 3 ║ +7 days     ║
║ 4/5      ║ Stage 2 ║ +3 days     ║
║ ≤3/5     ║ Stage 1 ║ +1 day      ║
╚══════════╩═════════╩═════════════╝

**3-Problem Rule**: if fewer than 3 problems share your pattern tag, Claude immediately gives you the next one from the same pattern. Pattern mastery = 3+ reps.

---

## Key Files

```
notes/REVIEW.md              ←  Master SRS tracking table (source of truth)
notes/[problem]-solved.md    ←  Pattern cards for every solved problem
notes/cheatsheets/           ←  8 pattern group cheatsheets + keyword index
DSA-PLAN.md                  ←  Topic roadmap + SRS mechanics detail
```

---

## Quick Start

```
claude          # open Claude Code in this repo
/start          # system tells you what's next
```
