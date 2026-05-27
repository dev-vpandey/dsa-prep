# DSA Prep Plan — Reference

## Goal
Cover all remaining DSA topics at Staff Engineer depth.
Target: all MAANG. Timeline: Weeks 1–6 of 18-week overall plan.

---

## Daily Session Structure (90 min weekdays / 120 min weekends)

```
1. /start          → find what to work on next (5 min)
2. New problem     → follow coaching flow (60-70 min)
3. /review-dsa     → max 5 problems, after new problem only (20-25 min)
```

New problem always comes first. Reviews never crowd out learning.

---

## Topic Order (priority = MAANG frequency)

| Week | Topic | Target Problems | Notes |
|------|-------|----------------|-------|
| 1 | System fix (done ✓) | — | Review wave broken, cap=5 |
| 2–4 | DP | 12–15 | 1D DP → 2D DP → Mixed. Build cheatsheet after 2nd problem. |
| 5 | Backtracking | 8–10 | Permutations → Subsets → Combinations → Grid. Build cheatsheet after 2nd. |
| 6 | Greedy + MST | 8–10 | Jump Game II, Meeting Rooms II, Task Scheduler + Kruskal, Prim |

Low-priority (fold into review-light days, no dedicated week):
- Bit Manipulation: 3 problems (Single Number, Counting Bits, Reverse Bits)
- Math & Geometry: 3 problems (Rotate Image, Spiral Matrix, Happy Number)
- Sort & Search: covered by existing problems; add Merge Sort + Quick Select

---

## Pattern Mastery Mechanics

**3-Problem Rule**
After any new solve, coach checks how many problems share the pattern tag.
- < 3 → coach auto-suggests next same-pattern problem. Do not move on.
- ≥ 3 → pattern solid, move to next topic.

**Pattern Lock Sprint**
Every new solve triggers a 2-stage review sprint:
- Day +1 (Stage 1 review): full solution recall from scratch
- Day +3 (Stage 2 review): write ONLY the cheatsheet boilerplate cold, no peeking
  - Pass → advance to Stage 3 (pattern locked)
  - Fail → stay Stage 2, retry in 2 days

**Initial Stage (based on MAANG rating after solve)**
- 5/5 → Stage 3, Review Date = today + 7 (sprint skipped — already mastered)
- 4/5 → Stage 2, Review Date = today + 3
- ≤3/5 → Stage 1, Review Date = today + 1

---

## Review System Rules

**Session cap:** 5 problems max. No exceptions.

**Priority queue (per session):**
1. Blank rating → Full mode
2. Weak rating → Full mode
3. Overdue 3+ days → force Blitz (clear debt fast)
4. Overdue 1-2 days → normal mode
5. Due today → normal mode

**Double-Strong fast-track:** Stage 3 problem rated Strong twice in a row → jump to Stage 4 immediately.

**Timeout:** 3 min silence → coach prompts once → auto-Weak if stuck, show card, move on.

---

## Cheatsheets

| Group | File | Status |
|-------|------|--------|
| G1 Foundation | cheatsheet-group1-foundation.md | Done |
| G2 Linear | cheatsheet-group2-linear.md | Done |
| G3 Trees | cheatsheet-group3-trees.md | Done |
| G4 Graphs | cheatsheet-group4-graphs.md | Done |
| G5 Binary Search | cheatsheet-group5-binary-search.md | Done |
| G6 Backtracking | cheatsheet-group6-backtracking.md | Build after 2nd backtracking problem |
| G7 DP | cheatsheet-group7-dp.md | Build after 2nd DP problem |
| G8 Advanced | cheatsheet-group8-advanced.md | Done |

Cheatsheets are built during learning — not upfront.
Index for lookup: `notes/cheatsheets/cheatsheet-index.md`

---

## Commands Reference

| Command | When to use |
|---------|-------------|
| `/start` | Start of every session — finds what to work on |
| `/review-dsa` | After new problem — runs capped review session |
| `/pattern [tag]` | Jump to a specific pattern directly |
| `/retry [problem]` | Re-attempt a previously solved problem |
| `/status` | Check stage distribution and what's due |

---

## Unseen Problem Approach (Pattern Recognition Framework)

When stuck on a new problem, work through in order:
1. **SIZE** — What is n? → target time complexity
2. **SHAPE** — sorted/tree/graph/subarray? → which data structure?
3. **SMELL** — count ways/shortest/all combos/max-min? → DP/BFS/backtracking/greedy?
4. **MATCH** — which cheatsheet template fits 70%+? → start there
5. **BRUTE** — write brute force → find the bottleneck → bottleneck IS the pattern
