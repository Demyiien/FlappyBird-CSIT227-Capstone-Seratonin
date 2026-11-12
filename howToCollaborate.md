## 🤝 How to Collaborate (Team Workflow)

We use a protected two-branch workflow (`main` and `dev`). **Never push or open a PR directly to `main`.**

- **`main`** — Stable production branch. Only the project lead merges reviewed changes from `dev` into `main`.
- **`dev`** — Main development branch. Teammates create working branches from `dev` and open Pull Requests back into `dev`.
- **`<your-name>/<type>-<short-description>`** — Your personal working branch, created from `dev`.

### Naming Conventions ([namingconvention.org/git](https://namingconvention.org/git/))

1. **Branch names:** `<your-name>/<type>-<kebab-case-description>`
   - `feat`: new feature (for example, `juan/feat-pipe-scrolling` or `maria/feat-start-dialog`)
   - `fix`: bug fix (for example, `juan/fix-pipe-gap-bounds`)
   - `refactor`: code restructuring (for example, `maria/refactor-package-structure`)
   - `docs`: documentation (for example, `juan/docs-claim-tick-03`)
   - `chore`: build or configuration updates
2. **Commit messages:** `<type>(<scope>): <short imperative description>`
   - `docs(readme): document the capstone project`
   - `feat(pipe): add continuous leftward pipe scrolling`
   - `fix(collision): check the bottom row boundary`

Use lowercase letters and hyphens in branch names. Do not use spaces in a branch name.

---

### Step-by-Step Collaboration Guide

The commands below work in Terminal, Git Bash, and PowerShell. Replace example names and ticket IDs with your own. In PowerShell, put quotes around folder paths that contain spaces.

#### Step 1: Open the project folder and check Git

If you have not downloaded the project yet, clone it once:

```bash
git clone https://github.com/Demyiien/FlappyBird-CSIT227-Capstone-Seratonin.git
cd FlappyBird-CSIT227-Capstone-Seratonin
```

If the project is already on your computer, go to its folder. For example, in PowerShell:

```powershell
cd "D:\Java Capstone 227\Practice"
```

Check that you are in the project and that Git sees it:

```bash
pwd
git status
```

You should see a branch name and a list of changed files (or a message that the working tree is clean).

**Common problem — `fatal: not a git repository`**

Git cannot find a `.git` folder in your current folder or its parent folders. You are usually in the wrong folder. Use `cd` to enter the cloned project folder, then run `git status` again:

```powershell
cd "full\path\to\FlappyBird-CSIT227-Capstone-Seratonin"
git status
```

Do not run `git init` inside a project you cloned. If you have not cloned the repository, use the `git clone` commands above. If you are unsure where the project folder is, locate it in File Explorer and open a terminal in that folder.

#### Step 2: Update your local `dev` branch

Fetch the latest branch information from GitHub, switch to `dev`, then rebase your local `dev` onto the remote `dev`:

```bash
git fetch origin
git switch dev
git pull --rebase origin dev
```

If this is your first time using the project, Git may not have a local `dev` branch yet. Create one that tracks GitHub's `dev` branch:

```bash
git switch --track origin/dev
```

Confirm which branch you are on before continuing:

```bash
git branch --show-current
```

It should print `dev`.

**Common problem — `origin` is not recognized or no remote named `origin` exists**

Check your configured remote:

```bash
git remote -v
```

If no remote is listed, you may be in the wrong project folder, or the project may not have been cloned from GitHub. For a cloned project, return to the clone folder. If you intentionally initialized a new local repository, add this project's remote:

```bash
git remote add origin https://github.com/Demyiien/FlappyBird-CSIT227-Capstone-Seratonin.git
```

**Common problem — `pathspec 'dev' did not match any file(s)`**

Fetch the remote branches first. Then create the local tracking branch:

```bash
git fetch origin
git switch --track origin/dev
```

**Common problem — Git says your local branch and `origin/dev` have diverged**

Do not use `git push --force`. First make sure you are on your own feature branch, then fetch and rebase it onto the newest `dev` as shown in Step 6. If you have uncommitted work, commit it or save it before rebasing.

#### Step 3: Create your own branch from `dev`

Choose a ticket and create a branch using your name, a type, and a short description. Use your first name in lowercase:

```bash
git switch -c josh/feat-bird-physics
```

For example, documentation work could use:

```bash
git switch -c josh/docs-claim-tick-03
```

Check that the branch was created:

```bash
git branch --show-current
```

The output should be your new branch name, not `dev` or `main`.

**Common problem — `a branch named ... already exists`**

Pick a different short description, or switch to the existing branch if it is your work:

```bash
git switch your-name/feat-short-description
```

#### Step 4: Claim your ticket and update the checklist

Open [sprintTickets.md](sprintTickets.md). In the Ticket Summary Board, replace `(Name)` with your name and set the ticket to `🚧 In Progress (YourName)`. For example:

```text
Before: 📝 Ready for Dev (Name)
After:  🚧 In Progress (Josh)
```

As you finish acceptance criteria, change `[ ]` to `[x]`. When all criteria are complete, change the ticket status to `✅ Done (YourName)`.

#### Step 5: Review, stage, and commit your changes

Before staging, check which files changed:

```bash
git status
```

Stage only the files for your ticket. Replace the example paths with your changed files:

```bash
git add src/path/to/YourFile.java sprintTickets.md
```

Review exactly what will be committed, then create a commit using the naming convention:

```bash
git diff --cached
git commit -m "feat(scope): describe what you implemented"
```

For ticket documentation, an example is:

```bash
git commit -m "docs(sprints): claim TICK-03"
```

**Common problem — `nothing to commit`**

Check `git status`. You may have forgotten to stage your files, or the file may not have changed. Stage the intended file with `git add path/to/file`, then check `git status` again.

**Common problem — Git asks you to set your name or email**

Set the author details Git should use for your commits, then retry the commit:

```bash
git config --global user.name "Your Name"
git config --global user.email "you@example.com"
```

Use the name and email associated with your GitHub account.

#### Step 6: Rebase your branch on the latest `dev`

Before pushing, fetch the latest GitHub changes and replay your branch's commits on top of `origin/dev`:

```bash
git fetch origin
git rebase origin/dev
```

If Git reports conflicts, open each listed file and resolve the sections marked `<<<<<<<`, `=======`, and `>>>>>>>`. Keep the correct combined changes, remove those marker lines, then stage the resolved files and continue:

```bash
git add path/to/resolved-file
git rebase --continue
```

Repeat until the rebase finishes. If you need to cancel and return to how your branch looked before rebasing:

```bash
git rebase --abort
```

**Common problem — `cannot rebase: You have unstaged changes`**

Commit the changes first (Step 5), or temporarily save them, rebase, then restore them:

```bash
git stash push -m "work in progress"
git rebase origin/dev
git stash pop
```

Resolve any conflicts from `stash pop` before continuing. Do not use `git reset --hard` to try to fix conflicts; it can discard your work.

#### Step 7: Push your branch to GitHub

Push your branch and set its GitHub tracking branch. Replace the example with your exact branch name:

```bash
git push -u origin josh/feat-bird-physics
```

The `-u` option connects your local branch to its GitHub branch. Later pushes can use the shorter command:

```bash
git push
```

**Common problem — `rejected` or `non-fast-forward`**

Someone may have updated your remote branch, or you may have forgotten Step 6. Fetch and rebase again, then push:

```bash
git fetch origin
git rebase origin/dev
git push
```

If you already pushed this same feature branch and then rebased its commits, use `--force-with-lease` only on your feature branch. It checks that nobody else changed the remote branch unexpectedly:

```bash
git push --force-with-lease
```

Never use plain `--force`, and never force-push to `dev` or `main`.

**Common problem — `Permission denied` or authentication failed**

Make sure you are signed in to GitHub in your Git credential manager or that your SSH key is configured. GitHub does not accept your account password for HTTPS Git operations. Retry `git push` after signing in, and check that you have access to this repository.

#### Step 8: Open a Pull Request into `dev`

1. Open the repository on GitHub and select **Compare & pull request** for your pushed branch.
2. Set **base** to `dev` and **compare** to your feature branch. Do not select `main` as the base.
3. Use a clear title that follows the commit convention, and describe your changes and completed ticket criteria.
4. Submit the PR and wait for review and approval. Do not merge your own PR unless the project lead asks you to.

---