# Dream: Memory Consolidation

You are performing a dream — a reflective pass over the memory files for this DSA prep project. Synthesize what you've learned recently into durable, well-organized memories so that future sessions can orient quickly.

Memory directory: `/Users/vicky/.claude/projects/-Users-vicky-Java-Projects-dsa-prep/memory/`

This memory tracks: the user's DSA problem-solving progress (solved problems, SRS stages, pattern tags), recurring mistakes, preferences for how coaching sessions are run, and the project structure.

Session transcripts: `/Users/vicky/.claude/projects/-Users-vicky-Java-Projects-dsa-prep/` (large JSONL files — grep narrowly, don't read whole files)

---

## Phase 1 — Orient

- List the memory directory to see what files already exist
- Read `/Users/vicky/.claude/projects/-Users-vicky-Java-Projects-dsa-prep/memory/MEMORY.md` to understand the current index
- Skim existing topic memory files to improve them rather than create duplicates

## Phase 2 — Gather recent signal

Look for new information worth persisting. Sources in rough priority order:

1. **Solved problem notes** (`/Users/vicky/Java_Projects/dsa-prep/notes/`) — scan for any newly added `*-solved.md` files that aren't yet reflected in MEMORY.md's solved problems table
2. **REVIEW.md** (`/Users/vicky/Java_Projects/dsa-prep/notes/REVIEW.md`) — check for stage changes, new problems, or rating patterns not captured in memory
3. **Existing memories that drifted** — facts that contradict what you see in the notes now
4. **Transcript search** — if you need specific context (e.g., a mistake pattern or a coaching preference the user expressed), grep the JSONL transcripts narrowly:
   `grep -rn "<narrow term>" /Users/vicky/.claude/projects/-Users-vicky-Java-Projects-dsa-prep/ --include="*.jsonl" | tail -50`

Don't exhaustively read transcripts. Look only for things you already suspect matter.

## Phase 3 — Consolidate

For each thing worth remembering, write or update a memory file at the top level of the memory directory. Use the memory file format and type conventions from the auto-memory section of the system prompt — it's the source of truth for what to save, how to structure it, and what NOT to save.

Memory types available:
- **user** — role, preferences, knowledge level, coaching preferences
- **feedback** — guidance the user gave about how sessions should run (corrections and validated approaches)
- **project** — solved problem counts, stage distributions, weak patterns, ongoing work
- **reference** — where to find things (REVIEW.md, notes/, template paths)

Focus on:
- Updating the solved problems table in MEMORY.md / project memory when new problems have been added to notes/
- Capturing any recurring mistake patterns observed across recent sessions
- Converting relative dates to absolute dates (e.g. "yesterday" → 2026-03-24)
- Deleting or correcting contradicted facts — if a problem's stage has advanced, update it

Do NOT save:
- Code patterns or Java implementation details — those live in the notes files
- Individual problem solutions — those are in `notes/*-solved.md`
- Ephemeral session state or in-progress work

## Phase 4 — Prune and index

Update `/Users/vicky/.claude/projects/-Users-vicky-Java-Projects-dsa-prep/memory/MEMORY.md` so it stays under 200 lines. It's an **index**, not a dump — link to memory files with one-line descriptions. Never write memory content directly into it.

- Remove pointers to memories that are stale, wrong, or superseded
- Demote verbose entries: keep the gist in the index, move detail into topic files
- Add pointers to newly important memories
- Resolve contradictions — if two files disagree, fix the wrong one

---

Return a brief summary of what you consolidated, updated, or pruned. If nothing changed (memories are already tight), say so.
