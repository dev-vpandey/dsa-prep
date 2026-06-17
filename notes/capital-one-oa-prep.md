# Capital One CodeSignal OA Prep
# OA Date: 2026-06-21 (Saturday)
# Active: 2026-06-17 through 2026-06-20 — review coach must read this before each session this week.

---

## OA Format

- 4 questions, 70 minutes
- Q1 + Q2: Easy — solve in < 20 min combined
- Q3: Medium-Hard — Grid/String implementation, 15–20 min
- Q4: Medium — HashMap / Subarray trick, algorithmic; **DP will NOT appear**
- Partial credit on Q4 — always write something even if brute-force only

**Strategy:** Finish Q1+Q2 fast → skip to Q4 → brute-force Q4 for partial → return to Q3.

---

## Problem List by Slot

### Q1 / Q2 — Easy

| Problem | LC # | In Deck | Action |
|---|---|---|---|
| Two Sum | #1 | ❌ | Cold solve LC — 5 min |
| Valid Parentheses | #20 | ✅ | Review card |
| Reverse Linked List | #206 | ✅ (graduated) | Review card |
| Best Time to Buy and Sell Stock | #121 | ❌ | Cold solve LC — 10 min |
| Contains Duplicate | #217 | ❌ | Cold solve LC — 5 min |
| Merge Two Sorted Lists | #21 | ❌ | Cold solve LC — 10 min |
| Fizz Buzz | #412 | ❌ | Skip — trivial |

### Q3 — Medium-Hard (Grid BFS / String Implementation)

| Problem | LC # | In Deck | Action |
|---|---|---|---|
| Number of Islands | #200 | ✅ | Review card |
| Flood Fill | #733 | ❌ | Covered by Number of Islands pattern |
| Find All Anagrams in a String | #438 | ✅ | Review card |
| **Image Smoother** | **#661** | **❌** | **Must learn — exact Grid BFS match to reported Q3** |
| Spiral Matrix | #54 | ❌ | Low priority |
| Rotate Image | #48 | ❌ | Low priority |

### Q4 — Medium (HashMap / Subarray — most likely trick)

| Problem | LC # | In Deck | Action |
|---|---|---|---|
| **Subarray Sum Equals K** | **#560** | **✅** | **Review card — most commonly reported Q4** |
| Minimum Window Substring | #76 | ✅ | Review card |
| Longest Consecutive Sequence | #128 | ✅ | Review card |
| Top K Frequent Elements | #347 | ⚠️ | K-Most-Frequent card is close — review that |
| Group Anagrams | #49 | ❌ | Learn sort-key trick |
| 3Sum | #15 | ✅ (Triplet Sum) | Review card if time |
| Max Size Subarray Sum Equals K | #325 | ❌ | Covered by #560 pattern |

---

## Deck Coverage Map

Cards already in deck that are Capital One relevant:

| Card File | Problem | Slot | Priority |
|---|---|---|---|
| valid-parentheses-solved.md | Valid Parentheses | Q1/Q2 | HIGH |
| reverse-linked-list-solved.md | Reverse Linked List | Q1/Q2 | HIGH |
| number-of-islands-solved.md | Number of Islands | Q3 | HIGH |
| find-all-anagrams-solved.md | Find All Anagrams | Q3 | HIGH |
| subarray-sum-equals-k-solved.md | Subarray Sum Equals K | Q4 | CRITICAL |
| minimum-window-substring-solved.md | Minimum Window Substring | Q4 | HIGH |
| longest-consecutive-sequence-solved.md | Longest Consecutive Sequence | Q4 | HIGH |
| k-most-frequent-strings-solved.md | K Most Frequent Strings | Q4 (Top K proxy) | MEDIUM |
| triplet-sum-to-zero-solved.md | Triplet Sum to Zero | Q4 (3Sum) | MEDIUM |

Not in deck — cold solve on LeetCode (do NOT add to SRS this week):
- #1 Two Sum, #121 Best Time to Buy/Sell Stock, #217 Contains Duplicate, #21 Merge Two Sorted Lists
- #661 Image Smoother (must practice — reported exact Q3 match)
- #49 Group Anagrams (learn sort-key trick: `Arrays.sort(s.toCharArray())` as HashMap key)

---

## 3-Day Review Schedule

| Day | /review-dsa Queue (override SRS priority) | Cold LC Practice |
|---|---|---|
| Tue Jun 17 | Valid Parentheses, Reverse Linked List, Number of Islands, Subarray Sum Equals K, Longest Consecutive Sequence | #1, #121, #217, #21 |
| Wed Jun 18 | Find All Anagrams, Min Window Substring, K-Most-Frequent, Triplet Sum to Zero + 1 more | LC #661 Image Smoother |
| Thu Jun 19 | Light review of any Weak/Blank from prior 2 days | LC #49 Group Anagrams |
| Fri Jun 20 | 70-min mock: 1 easy + 1 easy + #661 variant + #560 variant | Rest |

---

## Review Coach Instructions (this week only)

1. **Before building the SRS queue:** check this file first.
2. **Override SRS priority** — pick Capital One-listed cards from deck first, even if not overdue.
3. **Hard cap still 5 problems per session.**
4. **Blitz all Capital One cards** regardless of Stage — time pressure > thoroughness.
5. After OA (Jun 21), discard this override and return to standard SRS priority.
6. Remind user to cold-solve the non-deck LC problems after each session.
