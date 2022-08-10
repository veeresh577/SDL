Format python source code:

yapf --style='{based_on_style: pep8, COLUMN_LIMIT: 260}' -i -r <directory/filename>

Install pep8 and pyflake , more info @ http://cheng.logdown.com/posts/2015/06/14/sublime3-install-python-linters


To commit changes on old gerrit change-id:
git add
git commit --amend --no-edit
git fetch origin
git rebase origin/master
gitk  # Verify your changes
git review

To commit changes on new gerrit change-id:
git fetch origin
git checkout -b new_feature origin/master
git add <files>
git commit –m "Commit Message"
git rebase origin/master
gitk
git review