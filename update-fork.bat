rem Add the remote, call it "upstream":

git remote add upstream https://github.com/thingsboard/thingsboard

rem Fetch all the branches of that remote into remote-tracking branches,
rem such as upstream/master:

git fetch upstream

rem Make sure that you're on your master branch:

git checkout master

rem Rewrite your master branch so that any commits of yours that
rem aren't already in upstream/master are replayed on top of that
rem other branch:

git rebase upstream/master

pause