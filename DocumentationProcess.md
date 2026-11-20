# Documentation Process

The ODA Canvas documentation is published at [https://tmforum-oda.github.io/oda-ca-docs/](https://tmforum-oda.github.io/oda-ca-docs/). This page explains where each page comes from, how the site is built, and how to add a page or a video.

Most contributors only need [Add a page](#add-a-page): write or update the Markdown files next to your code, and the site picks them up. Feel free to ask for help in the [Slack channel](https://app.slack.com/client/T01C5A3NAU9/C01CGGDDLCQ) if this is your first time.

There is also a [video overview of the documentation process](https://www.youtube.com/watch?v=C_1WW0o0Z5o). It was recorded for an earlier version of the process, so its folder names and build steps are out of date. Follow this page instead.

## Where each page's source lives

The site is built from two repositories:

| Pages | Source | Published under |
|---|---|---|
| The Canvas itself: installation, operators, use cases, the BDD tests, charts, the Portal, AI-assisted development | The [oda-canvas](https://github.com/tmforum-oda/oda-canvas) repository, in the same folder structure | `canvas/` |
| The project pages: Getting started, the Playbook, the design guidelines, the contribution guide, this page | The root of this repository (oda-ca-docs) | `docs/` |
| The tutorials and guides: Component tutorial, Observability tutorial, Troubleshooting guide, Decision log | Folders in this repository. `ODA-Component-Tutorial` is published as `ODAComponentTutorial`, and `Troubleshooting-Guide` as `TroubleshootingGuide`, to keep their old URLs. | `docs/` |
| The Training videos pages | [`doc/videos.yaml`](https://github.com/tmforum-oda/oda-ca-docs/blob/main/doc/videos.yaml) in this repository | `docs/training-videos/` |
| The home page and the menu | [`doc/index.rst`](https://github.com/tmforum-oda/oda-ca-docs/blob/main/doc/index.rst) in this repository | the site root |

So to change a Canvas page, raise a pull request on oda-canvas. To change a project page, a tutorial, the menu or the video pages, raise a pull request on oda-ca-docs.

## How the site is built

The site is built with [Sphinx](https://www.sphinx-doc.org/) and [MyST](https://myst-parser.readthedocs.io/), which lets Sphinx read Markdown. It uses the [Furo](https://pradyunsg.me/furo/) theme, styled in [`doc/_static/css/custom.css`](https://github.com/tmforum-oda/oda-ca-docs/blob/main/doc/_static/css/custom.css) to match the ODA Canvas training videos: the same colour for each concept, the Inter and JetBrains Mono fonts, and dark mode by default.

The pages aren't kept in the `doc` folder. When Sphinx starts, [`doc/conf.py`](https://github.com/tmforum-oda/oda-ca-docs/blob/main/doc/conf.py) copies them in:

1. Every Markdown file and image (`.md`, `.png`, `.jpg`, `.gif`, `.svg`, `.puml`, `.feature`) in the oda-canvas checkout goes into `doc/canvas`, keeping its folder structure. Hidden and build folders (`.git`, `.github`, `node_modules`, `venv` and the like) and the instructions for AI coding agents (`AGENTS.md`, `CLAUDE.md`) are skipped.
2. The project pages at the root of this repository go into `doc/docs`.
3. The tutorial and guide folders of this repository go into `doc/docs`, under their published names.
4. The hand-written navigation files in `doc/overlay` are copied on top. Today this is only the Troubleshooting guide's landing page, which replaces its `README.md`.

`doc/canvas`, `doc/docs` and `doc/_build` are generated, so don't edit or commit them.

The [Docs workflow](https://github.com/tmforum-oda/oda-ca-docs/blob/main/.github/workflows/documentation.yaml) builds the site and publishes it to the `gh-pages` branch. It runs on every push to `main`, every night, and on request. So a change merged into oda-canvas is published without a commit to this repository, by the next morning at the latest.

## How the menu works

Each `toctree` in [`doc/index.rst`](https://github.com/tmforum-oda/oda-ca-docs/blob/main/doc/index.rst) is one section of the sidebar, such as *Canvas operators* or *Contributing*. Only the top page of each area needs to be listed there.

When the build reads a `README.md` (or a skill's `SKILL.md`), it adds every page in that folder and its sub-folders to the menu underneath it, down to the next `README.md`. So a new page in oda-canvas appears in the menu without any change here, as long as it sits in a folder with a README.

The use case archive (`usecase-library/archive`) isn't published.

## Add a page

1. Write the page in Markdown, in the repository and folder it belongs to (see the table above). Start it with a single `#` heading: that heading is the page's title in the menu.
2. Link to it from the README of its folder, so that readers browsing GitHub can find it too. Use relative links, such as `[Secrets management](secrets-management.md)`. The build turns them into links between site pages.
3. Put its images next to it, or in an `images` folder beside it, and link them with relative paths.
4. If the page starts a new area of the site, rather than sitting under an existing README, add it to the right `toctree` in `doc/index.rst`.
5. Preview the site locally (see below), and check the page, its links and its place in the menu.

## Add a training video

The [Training videos](https://tmforum-oda.github.io/oda-ca-docs/docs/training-videos/index.html) pages are generated from [`doc/videos.yaml`](https://github.com/tmforum-oda/oda-ca-docs/blob/main/doc/videos.yaml) by [`doc/_ext/oda_videos.py`](https://github.com/tmforum-oda/oda-ca-docs/blob/main/doc/_ext/oda_videos.py): an overview, and a page per video with the player, chapters, related documentation and the transcript.

To add a video, add an entry to `videos.yaml`, or re-export the file from the training-videos project. List the pages the video covers under `related`, as site page names such as `canvas/Observability-design`. Each of those pages then gets a *Watch* box under its title, unless the page already embeds the video.

## Preview the site locally

Check out oda-canvas next to oda-ca-docs, so that both folders sit in the same parent folder. The build reads the oda-canvas checkout as it is, so you can also preview a change to an oda-canvas page before you push it.

Then, from oda-ca-docs:

```bash
pip install -r doc/requirements.txt
sphinx-build -E -a doc doc/_build
```

Open `doc/_build/index.html` in a browser. The `-E -a` options rebuild every page, so that menus and links stay right when a page has moved.

The build prints a warning for each link or page it can't resolve. Check that your change adds none.

Every pull request on oda-ca-docs also builds the site, and uploads it as a `docs-site` artifact on the workflow run. Download it to preview the change without building it yourself.
