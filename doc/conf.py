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

# -- Project information -----------------------------------------------------

project = "ODA-Component Accelerator"
copyright = "2025, TM Forum ODA-Component Accelerator project"
author = "TM Forum ODA-Component Accelerator project"
release = "v1"

# -- General configuration ---------------------------------------------------

extensions = [
    "myst_parser",
    "sphinx_markdown_tables",
]

myst_enable_extensions = [
    "linkify",  # Automatically turn URLs into links
    "substitution",
]

source_suffix = {".rst": "restructuredtext", ".md": "markdown"}
templates_path = ["_templates"]
exclude_patterns = ["_build", "overlay", "Thumbs.db", ".DS_Store"]
master_doc = "index"

# -- Options for HTML output -------------------------------------------------

html_theme = "sphinx_rtd_theme"
html_static_path = ["_static"]
html_css_files = ["css/custom.css"]
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


def setup(app):
    # Priority below the default (500) so this runs before the image collector.
    app.connect("doctree-read", _decode_image_paths, priority=400)
