## Post-Solve Checklist

Run this after the user solves a problem correctly (their own solution or after seeing yours).

### 1. Fill the card
- Fill out @templates/dsa-pattern-card.md with all sections complete
- **Problem Link is REQUIRED** — always include the actual URL, never leave blank
- Use YYYY-MM-DD format for ALL dates (Solved Date, Review Date)

### 2. Set SRS fields
Based on MAANG rating:
- 5/5 → Stage 3, Review Date = today + 7
- 4/5 → Stage 2, Review Date = today + 3
- ≤3/5 → Stage 1, Review Date = today + 1

Set: Last Rating = —, Review Count = 0, Graduated = No

### 3. Save
Save to @notes/[problem-name]-solved.md

### 4. Cheatsheet sync
Look up the card's Pattern Tag in @notes/cheatsheets/cheatsheet-index.md, open the mapped section:
1. If the exact sub-pattern variant **exists** → verify boilerplate matches card's Boilerplate Template exactly. If it differs, update cheatsheet to match — card is always source of truth.
2. If the sub-pattern variant **does not exist** → announce: "⚠️ New variant detected: [variant name] not found in [cheatsheet section]. Add it?" Wait for confirmation before writing.

### 5. Update REVIEW.md
Append a row to @notes/REVIEW.md:
| [file] | [Problem] | [Tag] | [Stage] | [Review Date] | — | 0 | No |

### 6. Pattern Lock announcement
- Stage 1 or 2: "🏃 Sprint active — /review-dsa will run Sprint Mode for the next 2 reviews."
- Stage 3 (5/5 clean solve): "Pattern locked — sprint skipped. Next review in 7 days."

### 7. 3-Problem Rule
Count problems in @notes/REVIEW.md that share this pattern tag.
- Tag matching = **broad/prefix match** — count any row whose tag starts with the same root word (e.g. `two-pointer`, `sliding-window`, `bfs`, `dfs`, `binary-search`). Sub-variants like `two-pointer / in-place` and `two-pointer / move shorter` both count toward the same root.
- If < 3: announce "Pattern has X/3 problems. Here's your next one:" and immediately give an unseen problem from same tag.
- If ≥ 3: announce "Pattern solid at 3+ problems. Ready for a new pattern when you are."

Always announce this — never wait for the user to ask.
