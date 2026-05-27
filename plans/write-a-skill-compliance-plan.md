# DSA Prep — write-a-skill Compliance Fixes

## Context

Personal DSA coaching system in a single repo. Works well but has compliance gaps against write-a-skill rules + two runtime bugs causing dropped steps during review sessions (missing problem links, forgotten cheatsheet prime).

**Architecture stays unchanged:** single repo, CLAUDE.md coaching flow (always-on, correct), project-local commands, project-local srs-revision-coach skill (DSA-specific content makes user-level extraction wrong), notes/REVIEW.md as SRS source of truth.

---

## Decisions Made

╔══════════════════════════════╦═══════════════════════════════════════════════╗
║ Decision                     ║ Outcome                                       ║
╠══════════════════════════════╬═══════════════════════════════════════════════╣
║ CLAUDE.md coaching flow      ║ Stay as-is. Always-on = correct placement.    ║
║ srs-revision-coach location  ║ Stay project-local. DSA-specific content      ║
║                              ║ entangled with mode execution.                ║
║ SKILL.md frontmatter         ║ Add. Critical compliance fix.                 ║
║ SKILL.md split               ║ Yes — mode execution moves to REFERENCE.md   ║
║ review-dsa.md mode steps     ║ Move to REFERENCE.md. Command = orchestrator. ║
║ Commands set                 ║ Sufficient. No additions.                     ║
║ dream.md path bug            ║ Deferred — skip this change.                  ║
╚══════════════════════════════╩═══════════════════════════════════════════════╝

---

## Changes

### 1 — Add frontmatter to SKILL.md

File: `.claude/skills/srs-revision-coach/SKILL.md`

Replace the current 3 comment-lines header at top of file with YAML frontmatter:

```yaml
---
name: srs-revision-coach
description: SRS interval calculator and review mode assignment for spaced repetition pattern cards. Use when running /review-dsa, calculating next review dates, assigning Full/Snippet/Blitz review modes, or when Sprint Mode rules are needed.
---
```

Remove the 3 existing `# comment` header lines below it.

### 2 — Create REFERENCE.md with mode execution + advanced rules

File: `.claude/skills/srs-revision-coach/REFERENCE.md` (new file)

Move these sections **from SKILL.md** into REFERENCE.md:
- `## Snippet Mode — What It Means`
- `## Sprint Mode (Stage 1 and Stage 2 only)`
- `## Overdue Triage`
- `## Double-Strong Fast-Track (Stage 3 only)`

Move these **from review-dsa.md Step 4** into REFERENCE.md:
- Full mode execution steps (problem link source, hint rules, test case rules, complexity check)
- Snippet mode execution steps (skeleton prompt, coverage check, rating rules)
- Blitz mode execution steps (core insight prompt, yes/no flow)
- Cheatsheet Prime trigger: Full mode only → lookup Pattern Tag in `@notes/cheatsheets/cheatsheet-index.md`, output one line

Add at end of SKILL.md after removals:
```
## Mode Execution
See [REFERENCE.md](REFERENCE.md) for Full / Snippet / Blitz step-by-step execution,
Cheatsheet Prime, Sprint Mode, Overdue Triage, and Double-Strong Fast-Track rules.
```

**Result:** SKILL.md drops from 90 → ~45 lines. REFERENCE.md ~65 lines.

### 3 — Rewrite review-dsa.md Step 4 as orchestrator

File: `.claude/commands/review-dsa.md`

Replace current Step 4 dense prose block with lean orchestrator that delegates to REFERENCE.md:

```
## Step 4 — Per Problem
Load card from @notes/[file]-solved.md before starting each problem.
Run mode per @.claude/skills/srs-revision-coach/REFERENCE.md — follow mode steps exactly.
Mode determines: what to show, when to show it, how to rate, when to move on.

After every problem output:
  Rating: [✅/🟡/🔴/❌]
  ✅ Got: ...  ❌ Missed: ...
  Next review: YYYY-MM-DD (Stage X → Y)
  Update @notes/[file]-solved.md: Stage / Review Date / Last Rating / Review Count
  Update @notes/REVIEW.md: same row
  📄 Card: @notes/[file].md — say "move on" to continue.
  Wait for "move on" before starting next problem.
```

**Result:** review-dsa.md drops from 106 → ~65 lines.

---

## Why This Fixes the Bugs

- **Problem link dropped:** REFERENCE.md Full mode step 1 = "Read Problem Link from card's `Problem Link:` field before showing anything." Explicit, can't be skipped.
- **Cheatsheet Prime forgotten:** Cheatsheet Prime now lives in REFERENCE.md alongside other Full mode steps — no cross-file drop.

---

## Critical Files

- `.claude/skills/srs-revision-coach/SKILL.md` — add frontmatter, remove 4 sections
- `.claude/skills/srs-revision-coach/REFERENCE.md` — new file, receives moved sections
- `.claude/commands/review-dsa.md` — Step 4 replaced with orchestrator

---

## Verification

1. Run `/review-dsa` → problem link appears for first problem
2. Run `/review-dsa` with a Full mode problem → cheatsheet prime line appears before problem is shown
3. Rate a Stage 1 problem Weak → Sprint Mode triggers correctly (now from REFERENCE.md)
4. SKILL.md line count under 50 after removals
