Read @notes/REVIEW.md.
Run these checks in order and show the first one that applies:

## Link Rule (applies to ALL checks)
Always display the Problem Link for every problem announced — sprint due, suggested, or gap.
Fetch the link from the problem's solved note file (grep "Problem Link").
Never announce a problem without its link.

## Check 1 — Pattern Lock Sprint Active?
Find any Stage 1 or Stage 2 problem whose Review Date is today or overdue.
If found → show a table with columns: Problem | Stage | Due | Link
Include ALL overdue/due sprint problems, each with its Problem Link.
Announce: "Sprint active. Run /review-dsa first before starting a new problem."

## Check 1b — Work Queue (runs right after Check 1, then continue to Check 2)
Read `notes/QUEUE.md`. If the file is missing, skip this check. No error.
Data rows are the table rows after the header row. Column 1 is the slug.
1. Collect the slug of every `Problem Link:` line in `notes/*-solved.md`. The slug is the text after `/problems/` up to the next `/`. Skip lines with no `/problems/` (for example ByteByteGo links or "Custom"). Match by slug only, never by file name.
2. Delete each queue row whose slug is in that set. Save the file. Say: "Removed N solved rows from the queue."
3. If rows are left, announce: "Work queue has N items. Oldest: [Problem] (added [Added date])." Oldest = the first row.
4. If no rows are left, say nothing.
This check never replaces Check 1, 2 or 3. Show it in addition to them.

## Check 2 — 3-Problem Rule: Pattern in Progress?
Find the most recently added pattern tag (last few rows of REVIEW.md).
Count how many problems share that tag across all rows.
If < 3 → announce: "Pattern [tag] has [N]/3 problems. Suggested next:" and give one unseen problem from that same tag — include its Problem Link.
Follow the full coaching flow from CLAUDE.md once I confirm.

## Check 3 — Pattern Gap?
Find pattern tags not seen in any Review Date within the last 7 days.
Pick the tag with the oldest last-review date as the gap.
Announce: "Gap found: [tag] — last practiced [date]. Suggested:" and give one unseen problem from that tag — include its Problem Link.
Follow the full coaching flow from CLAUDE.md once I confirm.

## If no checks fire
All patterns are fresh and at 3+ problems.
Announce: "All patterns solid. Time to start a new one. Picking from your weakest area..." and suggest the next pattern based on MAANG frequency (DP > Backtracking > Greedy > advanced graph > rest).
