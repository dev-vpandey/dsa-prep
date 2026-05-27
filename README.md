# DSA Prep — MAANG Coaching System

> **AI-coached DSA prep in Java 21. Combines active problem-solving with Spaced Repetition (SRS) to lock in patterns**

If this saves you time, a ⭐ on the repo helps others find it.

---

## How This Works

This repo turns Claude Code into a DSA interview coach. `CLAUDE.md` defines the coaching rules — it auto-loads every time you run `claude` in this directory. Commands like `/start` and `/review-dsa` are slash commands *inside Claude Code*, not shell scripts. Your progress lives in `notes/REVIEW.md` and persists across sessions.

No build step. No server. Just `claude` in the repo root.

---

## Prerequisites

**Required**
- [Claude Code](https://claude.ai/code) — install via `npm install -g @anthropic-ai/claude-code` or download the desktop app
- Anthropic account — run `claude login` once after install

**Optional**
- Java 21 — only needed if you want to compile/run solutions locally. Claude writes and dry-runs code without it.

---

## Quick Start

```bash
git clone https://github.com/dev-vpandey/dsa-prep.git
cd dsa-prep
claude login        # one-time — skips if already logged in
claude              # opens Claude Code with the coaching system loaded
/start              # coach tells you what to work on
```

**Starting fresh?** The repo ships with 95 solved problems already in `notes/REVIEW.md`. To start your own clean slate:

```bash
# keep the cheatsheets and templates, wipe the progress tables
echo "# SRS Review Index
| File | Problem | Tag | Stage | Review Date | Last Rating | Review Count | Graduated |
|---|---|---|---|---|---|---|---|" > notes/REVIEW.md

> notes/GRADUATED.md
> notes/GAP-DRILLS.md
```

Then `/start` picks a first problem for you.

---

## Daily Rhythm

```
/start        →  New problem (30–40 min)
/review-dsa   →  Spaced repetition reviews (max 5, ~20 min)
```

New problem first. Reviews after. Never mix.

---

## Commands

╔════════════════╦══════════════════════════════════════════════════════════════╗
║ Command        ║ What it does                                                 ║
╠════════════════╬══════════════════════════════════════════════════════════════╣
║ /start         ║ Finds what to work on next                                   ║
║ /review-dsa    ║ Runs today's SRS review queue (max 5); Sprint Mode Stage 1–2 ║
║ /pattern X     ║ Jump to a specific pattern (e.g. /pattern dp)                ║
║ /retry X       ║ Re-attempt a previously solved problem                       ║
║ /status        ║ Stage distribution + what's overdue                          ║
╚════════════════╩══════════════════════════════════════════════════════════════╝

---

## Hints During Solving

╔══════════════════╦═══════════════════════════════════════════════════════╗
║ You say          ║ You get                                               ║
╠══════════════════╬═══════════════════════════════════════════════════════╣
║ `hint`           ║ One-sentence intuition — no algorithm name            ║
║ `hint hint`      ║ Slightly more concrete — still no pattern name        ║
║ `hint hint hint` ║ Pattern name + relevant cheatsheet section            ║
║ `stuck`          ║ Full Pattern Recognition Framework (SIZE/SHAPE/SMELL) ║
║ `code`           ║ Full Java 21 solution + dry run + complexity          ║
╚══════════════════╩═══════════════════════════════════════════════════════╝

---

## After You Solve

Claude rates you 1–5 (MAANG interviewer scale), saves a pattern card to `notes/[problem]-solved.md`, and schedules the next review:

╔══════════╦═════════╦═════════════╦══════════════════════════════════════╗
║ Rating   ║ Stage   ║ Next Review ║ Sprint Mode                          ║
╠══════════╬═════════╬═════════════╬══════════════════════════════════════╣
║ 5/5      ║ Stage 3 ║ +7 days     ║ Skipped — pattern locked             ║
║ 4/5      ║ Stage 2 ║ +3 days     ║ Active for next 2 reviews            ║
║ ≤3/5     ║ Stage 1 ║ +1 day      ║ Active for next 2 reviews            ║
╚══════════╩═════════╩═════════════╩══════════════════════════════════════╝

Sprint Mode runs Day+1 and Day+3 reviews as timed recall drills before showing the solution. Say `skip`, `bail`, or `next` to exit Sprint Mode mid-review.

**3-Problem Rule**: tag matching uses broad/prefix match — `two-pointer / in-place` and `two-pointer / move shorter` both count toward `two-pointer`. If fewer than 3 problems share the root tag, Claude immediately queues the next one from the same pattern. Pattern mastery = 3+ reps.

---

## Key Files

```
notes/REVIEW.md                   ←  Master SRS tracking table (source of truth)
notes/GRADUATED.md                ←  Problems cycled out of active review
notes/GAP-DRILLS.md               ←  Pattern gap tracker — muscle vs conceptual drills
notes/[problem]-solved.md         ←  Pattern cards for every solved problem (95+)
notes/cheatsheets/                ←  8 pattern group cheatsheets + keyword index
templates/post-solve-checklist.md ←  Post-solve procedure (card, SRS, cheatsheet sync)
```

**GAP-DRILLS.md** — when a review surfaces a weak pattern, the coach logs it here with a targeted drill. Two types: *Muscle* (write boilerplate cold) and *Conceptual* (fresh unseen problem). Rows close once the pattern is strong.

**GRADUATED.md** — problems that have reached Stage 6 (90-day interval) move here. They re-enter active review if a future problem surfaces the same pattern as weak.

---

## License

GPL-3.0 — see [LICENSE](LICENSE).
