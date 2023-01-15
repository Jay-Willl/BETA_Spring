#!/bin/zsh

since="2015-09-03T20:23:38Z"
direction="asc"
state="all"

#for ((i=1;i<=1460;i++)) {
#    gh api --header 'Accept: application/vnd.github+json' --method GET /repos/microsoft/vscode/issues -F per_page=100 -F page="$i" -f since=${since} -f direction=${direction} -f state=${state} | tee -a ../static/vscode/issues/issue"${i}".json
#}




## issue
#gh api --header 'Accept: application/vnd.github+json' --method GET /repos/microsoft/vscode/issues -F per_page=100 -f sort=updated | tee ../static/vscode/issues.json
#gh api --header 'Accept: application/vnd.github+json' --method GET /repos/microsoft/monaco-editor/issues -F per_page=100 -f sort=updated | tee ../static/monaco-editor/issues.json
#gh api --header 'Accept: application/vnd.github+json' --method GET /repos/microsoft/TypeScript/issues -F per_page=100 -f sort=updated | tee ../static/TypeScript/issues.json
#
## contributors
#gh api --header 'Accept: application/vnd.github+json' --method GET /repos/microsoft/vscode/contributors -F per_page=100 -f sort=updated | tee ../static/vscode/contributors.json
#gh api --header 'Accept: application/vnd.github+json' --method GET /repos/microsoft/monaco-editor/contributors -F per_page=100 -f sort=updated | tee ../static/monaco-editor/contributors.json
#gh api --header 'Accept: application/vnd.github+json' --method GET /repos/microsoft/TypeScript/contributors -F per_page=100 -f sort=updated | tee ../static/TypeScript/contributors.json
#
## releases
#gh api --header 'Accept: application/vnd.github+json' --method GET /repos/microsoft/vscode/releases -F per_page=100 -f sort=updated | tee ../static/vscode/releases.json
gh api --header 'Accept: application/vnd.github+json' --method GET /repos/microsoft/monaco-editor/releases -F per_page=100 -f sort=updated | tee ../static/monaco-editor/releases.json
#gh api --header 'Accept: application/vnd.github+json' --method GET /repos/microsoft/TypeScript/releases -F per_page=100 -f sort=updated | tee ../static/TypeScript/releases.json
#
## commits
#gh api --header 'Accept: application/vnd.github+json' --method GET /repos/microsoft/vscode/commits -F per_page=100 -f sort=updated | tee ../static/vscode/commits.json
#gh api --header 'Accept: application/vnd.github+json' --method GET /repos/microsoft/monaco-editor/commits -F per_page=100 -f sort=updated | tee ../static/monaco-editor/commits.json
#gh api --header 'Accept: application/vnd.github+json' --method GET /repos/microsoft/TypeScript/commits -F per_page=100 -f sort=updated | tee ../static/TypeScript/commits.json