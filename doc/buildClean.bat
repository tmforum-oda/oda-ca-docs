sphinx-build -E -a . _build/html
powershell -Command "& {rm -r ..\docs\*}"
powershell -Command "& {Copy-Item -Path .\_build\html\* -Recurse -Destination ..\docs\}"
