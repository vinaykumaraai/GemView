@echo off
SET jirauser=svnadmin@gemstonepay.com
SET jirapass=Development2016
SET jiraurl=https://gemstonepay.atlassian.net/rest/api/2/issue

cls
SET command=curl -u %jirauser%:%jirapass% -X POST -d "@jira-issue.json" -H "Content-Type: application/json" -H "Accept: application/json" %jiraurl%
echo %command%
%command%
