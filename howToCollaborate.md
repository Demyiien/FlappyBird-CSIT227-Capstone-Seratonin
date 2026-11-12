## 🤝 How to Collaborate (Team Workflow)

We use a protected two-branch workflow (`main` and `dev`). **Never push or open a PR directly to `main`.**

- **`main`** — Stable production branch. Only the project lead merges `dev` into `main`.
- **`dev`** — Main development branch. All teammates branch off `dev` and open Pull Requests back into `dev`.
- **`<name>/<type>-<short-description>`** — Your personal working branch created from `dev`.

### Naming Conventions ([namingconvention.org/git](https://namingconvention.org/git/))

1. **Branch Names:** `<your-name>/<type>-<kebab-case-description>`
   - `feat`: New feature (e.g., `juan/feat-pipe-scrolling`, `maria/feat-start-dialog`)
   - `fix`: Bug fix (e.g., `juan/fix-pipe-gap-bounds`)
   - `refactor`: Code restructuring (e.g., `maria/refactor-package-structure`)
   - `docs`: Documentation changes (e.g., `juan/docs-claim-tick-03`)
   - `chore`: Build/config updates
2. **Commit Messages:** `<type>(<scope>): <short imperative description>`
   - Examples:
     - `docs(readme): claim TICK-03 and mark sprite slicing complete`
     - `feat(pipe): add continuous leftward pipe scrolling`
     - `fix(collision): check bottom row boundary for 2x2 bird`

---

### Step-by-Step Collaboration Guide & Git Commands

#### Step 1: Sync with the `dev` branch
Always start from an up-to-date `dev` branch using `--rebase`:

```bash
# If you already cloned the repository:
git fetch origin
git checkout dev
git pull --rebase origin dev
```

#### Step 2: Create your branch off `dev`
Create your branch using your name and the [Git naming convention](https://namingconvention.org/git/):

```bash
git checkout -b yourname/feat-short-description
# Example:
# git checkout -b josh/feat-bird-physics
```

#### Step 3: Claim your ticket & update the checklist in `README.md`
1. In the **Ticket Summary Board** below, replace `(Name)` with your name and change the status to `🚧 In Progress (YourName)`:
   - Before: `| **TICK-03** | Pipe Generation, Slicing & Leftward Movement | model, view | High | 📝 Ready for Dev (Name) |`
   - After: `| **TICK-03** | Pipe Generation, Slicing & Leftward Movement | model, view | High | 🚧 In Progress (Juan) |`
2. As you finish items under your ticket's **Acceptance Criteria**, add an `x` inside `[ ]` to mark them done:
   - Before: `- [ ] Move active pipes leftward by 1 column every N ticks.`
   - After: `- [x] Move active pipes leftward by 1 column every N ticks.`
3. Once all items in your ticket are finished, update the board status to `✅ Done (YourName)`.

#### Step 4: Stage & commit your changes
```bash
# Check which files you modified
git status

# Stage your changes
git add .

# Commit following https://namingconvention.org/git/
git commit -m "feat(scope): describe what you implemented"
```

#### Step 5: Rebase onto the latest `dev` before pushing
To prevent unnecessary merge commits or accidentally overwriting a teammate's code that was merged into `dev` while you were working, **always rebase your branch onto `origin/dev` before pushing**:

```bash
# 1. Fetch the latest changes from GitHub
git fetch origin

# 2. Rebase your commits on top of the latest origin/dev
git rebase origin/dev

# If there are any conflicts:
#   - Open the conflicting file(s) and keep both teammates' changes properly
#   - Stage the resolved file(s): git add <filename>
#   - Continue the rebase:        git rebase --continue
#   - (Or cancel if needed):      git rebase --abort

# 3. Push your branch to GitHub
# (Use --force-with-lease if you already pushed this branch before rebasing)
git push -u origin yourname/feat-short-description
# git push --force-with-lease origin yourname/feat-short-description
```

#### Step 6: Open a Pull Request (PR) into `dev`
1. Go to the GitHub repository in your browser.
2. Click **Compare & pull request** on your pushed branch.
3. Set the branches to:
   - **base:** `dev` ← **compare:** `yourname/feat-short-description` *(Make sure base is `dev`, NOT `main`!)*
4. Add a clear PR title (matching our commit convention) and list what you completed.
5. Submit the PR and wait for review and approval. Direct merges without approval are disabled.

---
