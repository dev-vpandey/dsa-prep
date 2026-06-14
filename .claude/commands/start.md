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
