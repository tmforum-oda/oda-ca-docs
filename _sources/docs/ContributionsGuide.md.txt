# Contribution guidance

We welcome contributions to this initiative. The following outlines the process and governance for contributing.

* Contributions will include contributing new components, updates to the CRDs (Custom Resource Definitions) that kubernetes uses to define the structure of a component, updates to the kubernetes operators that use these CRDs and updates to the CTKs (Compliance Test Kits). 
* Before you create a contribution, you should create an issue to describe what you plan to address and make any work visible to the whole team. You should also check that there are no open issues that may conflict with your planned contribution.
* There are multiple issue types to use:

![Issues](./.github/Issues.PNG)


Each issue type has its own template. e.g. a Feature uses:
```
<scope>: <subject>

<body>
```

Where `scope` is one of:
1. component (for new/updated component)
2. operator (for new or updates to the controller or custom resource definition)
3. ctk (for new/updated CTKs)


e.g. 
```
component: Sigscale OCS

Add envelope for Sigscale OCS component (https://github.com/sigscale/ocs-k8s)
```

* Once the issues have been created, create a feature-branch.
* For the git commit messages we will follow the format in http://karma-runner.github.io/4.0/dev/git-commit-msg.html . For the allowed `scope` we should use:
1. component (for new/updated component)
2. operator (for new or updates to the controller or custom resource definition)
3. test (for new/updated CTKs)

example commit message
```
feat(component): Sigscale OCS

Add envelope for Sigscale OCS component (https://github.com/sigscale/ocs-k8s)

fixes #6
```

* Finally, once a feature-branch is complete and tested, issue a pull-request to merge the feature-branch into the main branch. The pull request should mention the issues it addresses by including `closes #<issue_number>` in the pull request body (You have to hit the <return> key after the issue number to create the link). This will link and then automatically close the issue (if you want to link without closing an issue, include `references #<issue_number>`.)  

The `github-actions bot` will analyse the pull request and automatically merge if the pull request relates to `documentation`, `bug-fix` or `refactor`. For `feature` pull requests, we will review and approve as part of the weekly ODA-CA governance meeting.


