# Configuration file for the Sphinx documentation builder.
#
# Full list of options: https://www.sphinx-doc.org/en/master/usage/configuration.html
#
# The documentation content is not kept in this folder. When Sphinx starts, this
# file copies it in from two places:
#
#   * the oda-canvas repository (checked out next to oda-ca-docs) -> ./canvas
#   * the root of this repository (project pages and tutorials)     -> ./docs
#
# Then the hand-written navigation files in ./overlay are copied on top.
# ./canvas and ./docs are generated on every build and are not committed.

import fnmatch
import os
import shutil
import sys

# Local Sphinx extensions (the training videos pages).
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), "_ext"))

# -- Project information -----------------------------------------------------

project = "ODA-Component Accelerator"
copyright = "2026, TM Forum ODA-Component Accelerator project"
author = "TM Forum ODA-Component Accelerator project"
release = "v1"

# -- General configuration ---------------------------------------------------

extensions = [
    "myst_parser",
    "sphinx_markdown_tables",
    "oda_videos",
]

myst_enable_extensions = [
    "linkify",  # Automatically turn URLs into links
    "substitution",
]

source_suffix = {".rst": "restructuredtext", ".md": "markdown"}
templates_path = ["_templates"]
# Archived use cases are kept in oda-canvas for history, but not published.
exclude_patterns = ["_build", "_ext", "overlay", "canvas/usecase-library/archive", "Thumbs.db", ".DS_Store"]
master_doc = "index"

# -- Options for HTML output -------------------------------------------------

# Furo, styled to match the ODA Canvas training videos: the same palette (one
# colour per concept), Inter and JetBrains Mono, and a dark stage by default.
# The colours are defined in _static/css/custom.css.
html_theme = "furo"
html_title = "ODA Canvas and Components"
html_static_path = ["_static"]
html_css_files = [
    "https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800"
    "&family=JetBrains+Mono:wght@400;600&display=swap",
    "css/custom.css",
]
html_js_files = ["js/default-dark.js"]
html_theme_options = {
    "light_logo": "ODA-Canvas.svg",
    "dark_logo": "ODA-Canvas-dark.svg",
    "sidebar_hide_name": False,
    "navigation_with_keys": True,
    # No "edit this page" link: most pages are copied from oda-canvas, so the
    # source is not in this repository.
    "top_of_page_buttons": [],
}
pygments_style = "friendly"
pygments_dark_style = "github-dark"
# Google Search Console site verification file, served at the site root.
html_extra_path = ["../google6235ca3ee03a50cf.html"]

# -- Copy the documentation content in ---------------------------------------

DOC_DIR = os.path.dirname(os.path.abspath(__file__))
REPO_ROOT = os.path.dirname(DOC_DIR)
ODA_CANVAS = os.path.join(os.path.dirname(REPO_ROOT), "oda-canvas")

# File types copied from the source repositories (matched case-insensitively).
INCLUDE_FILES = [
    "*.md",
    "*.png",
    "*.jpg",
    "*.jpeg",
    "*.gif",
    "*.svg",
    "*.puml",
    "*.feature",
]

# Folders that are never copied (matched against each folder name).
EXCLUDE_DIRS = [
    ".git",
    ".github",
    ".claude",
    ".windsurf",
    ".vscode",
    ".devcontainer",
    "node_modules",
    "__pycache__",
    ".venv",
    "venv",
    "_build",
    "dist",
    "build",
]
# Files that are never copied.
EXCLUDE_FILES = [
    "AGENTS.md",  # instructions for AI coding agents, not reader documentation
    "CLAUDE.md",
]


def _wanted(filename):
    if filename in EXCLUDE_FILES:
        return False
    lower = filename.lower()
    return any(fnmatch.fnmatch(lower, pattern) for pattern in INCLUDE_FILES)


def copy_file(src, dest):
    if not os.path.isfile(src):
        print(f"WARNING: documentation source file not found: {src}")
        return
    os.makedirs(os.path.dirname(dest), exist_ok=True)
    shutil.copy2(src, dest)


def copy_tree(src_dir, dest_dir, wanted=_wanted):
    """Copy every documentation file under src_dir into dest_dir, keeping the
    folder structure. A missing source folder is reported but does not stop
    the build, so a renamed folder gives broken links rather than no site."""
    if not os.path.isdir(src_dir):
        print(f"WARNING: documentation source folder not found: {src_dir}")
        return 0
    count = 0
    for root, dirs, files in os.walk(src_dir):
        dirs[:] = [d for d in dirs if d not in EXCLUDE_DIRS]
        for filename in files:
            if not wanted(filename):
                continue
            rel = os.path.relpath(os.path.join(root, filename), src_dir)
            copy_file(os.path.join(root, filename), os.path.join(dest_dir, rel))
            count += 1
    return count


def populate():
    canvas_out = os.path.join(DOC_DIR, "canvas")
    docs_out = os.path.join(DOC_DIR, "docs")
    for out in (canvas_out, docs_out):
        shutil.rmtree(out, ignore_errors=True)
        os.makedirs(out)

    # 1. The oda-canvas repository, in its own folder structure.
    n = copy_tree(ODA_CANVAS, canvas_out)
    print(f"Copied {n} files from oda-canvas")

    # 2. This repository's project pages (top-level files only).
    for filename in sorted(os.listdir(REPO_ROOT)):
        if not os.path.isfile(os.path.join(REPO_ROOT, filename)):
            continue
        if filename in ("README.md", "ODACanvasDesignGuidelines.md"):
            continue
        if _wanted(filename) or (
            filename.startswith("oda-component-oas-") and filename.endswith(".yaml")
        ):
            copy_file(os.path.join(REPO_ROOT, filename), os.path.join(docs_out, filename))
    copy_file(
        os.path.join(REPO_ROOT, ".github", "Issues.PNG"),
        os.path.join(docs_out, ".github", "Issues.PNG"),
    )

    # 3. Tutorials and guides in this repository. The published folder names
    #    differ from the source folder names, to keep existing URLs working.
    for src, dest in [
        ("Observability-Tutorial", "Observability-Tutorial"),
        ("ODA-Component-Tutorial", "ODAComponentTutorial"),
        ("Troubleshooting-Guide", "TroubleshootingGuide"),
        ("Decision-Log", "Decision-Log"),
    ]:
        copy_tree(os.path.join(REPO_ROOT, src), os.path.join(docs_out, dest))
    # The troubleshooting guide's landing page is overlay/docs/TroubleshootingGuide/index.rst
    readme = os.path.join(docs_out, "TroubleshootingGuide", "README.md")
    if os.path.exists(readme):
        os.remove(readme)

    # 4. Hand-written navigation (.rst) files, copied on top.
    copy_tree(os.path.join(DOC_DIR, "overlay"), DOC_DIR, wanted=lambda f: True)


populate()


# -- Fix up percent-encoded image paths --------------------------------------

from urllib.parse import unquote

from docutils import nodes


# GitHub resolves percent-encoded local image paths such as
# "images/AI-Native%20Canvas/diagram.PNG", but Sphinx looks for a file with
# "%20" in its name. Decode them before Sphinx collects the images.
def _decode_image_paths(app, doctree):
    for image in doctree.findall(nodes.image):
        uri = image.get("uri", "")
        if "%" in uri and "://" not in uri:
            image["uri"] = unquote(uri)


# -- Nest each README's sub-pages under it in the navigation ------------------

import re

from sphinx.util.matching import patfilter

# Pages whose folder is a section of its own: their sub-pages are nested under them.
INDEX_PAGES = ("README", "SKILL")
# Index pages that are not given nested sub-pages (their children are listed in
# index.rst, or they are the root of the whole repository).
NO_NESTING = ("canvas/README", "canvas/usecase-library/README")

_listed = None


def _listed_in_toctrees(app):
    """Docnames that index.rst (and the overlay .rst files) already put in the
    navigation, with globs expanded the way Sphinx expands them."""
    global _listed
    if _listed is None:
        found = sorted(app.env.found_docs)
        _listed = set()
        rst_files = [os.path.join(DOC_DIR, "index.rst")]
        for root, _dirs, files in os.walk(os.path.join(DOC_DIR, "overlay")):
            rst_files += [os.path.join(root, f) for f in files if f.endswith(".rst")]
        for path in rst_files:
            rel_dir = os.path.relpath(os.path.dirname(path), DOC_DIR)
            rel_dir = os.path.relpath(rel_dir, "overlay") if rel_dir.startswith("overlay") else rel_dir
            base = "" if rel_dir == "." else rel_dir.replace(os.sep, "/") + "/"
            for line in open(path, encoding="utf-8"):
                m = re.match(r"^   ([^\s:.][^:]*?)\s*$", line)
                if not m:
                    continue
                entry = base + re.sub(r"\.(md|rst)$", "", m.group(1))
                if "*" in entry or "?" in entry:
                    _listed.update(patfilter(found, entry))
                else:
                    _listed.add(entry)
    return _listed


def _is_index(docname):
    return docname.rsplit("/", 1)[-1] in INDEX_PAGES


def _owner(app, docname):
    """The nearest README (or SKILL) page that a page sits under."""
    folder = docname.rsplit("/", 1)[0] if "/" in docname else ""
    if _is_index(docname):
        folder = folder.rsplit("/", 1)[0] if "/" in folder else ""
    while folder:
        for name in INDEX_PAGES:
            if f"{folder}/{name}" in app.env.found_docs:
                return f"{folder}/{name}"
        folder = folder.rsplit("/", 1)[0] if "/" in folder else ""
    return None


def _nest_sub_pages(app, docname, source):
    if not _is_index(docname) or docname in NO_NESTING:
        return
    listed = _listed_in_toctrees(app)
    children = sorted(
        d
        for d in app.env.found_docs
        if d != docname
        and d.startswith(docname.rsplit("/", 1)[0] + "/")
        and d not in listed
        and _owner(app, d) == docname
    )
    if children:
        entries = "\n".join("/" + c for c in children)
        source[0] += _close_open_fence(source[0])
        source[0] += f"\n\n```{{toctree}}\n:hidden:\n\n{entries}\n```\n"


def _close_open_fence(text):
    """If a page ends inside an unclosed code fence, return the line that closes it."""
    fence = None
    for line in text.splitlines():
        m = re.match(r"^\s{0,3}(`{3,}|~{3,})", line)
        if not m:
            continue
        marker = m.group(1)
        if fence is None:
            fence = marker
        elif marker[0] == fence[0] and len(marker) >= len(fence) and not line.strip()[len(marker):]:
            fence = None
    return f"\n{fence}\n" if fence else ""


def setup(app):
    # Priority below the default (500) so this runs before the image collector.
    app.connect("doctree-read", _decode_image_paths, priority=400)
    app.connect("source-read", _nest_sub_pages)
