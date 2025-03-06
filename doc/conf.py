# Configuration file for the Sphinx documentation builder.
#
# This file only contains a selection of the most common options. For a full
# list see the documentation:
# https://www.sphinx-doc.org/en/master/usage/configuration.html

# -- Path setup --------------------------------------------------------------

# If extensions (or modules to document with autodoc) are in another directory,
# add these directories to sys.path here. If the directory is relative to the
# documentation root, use os.path.abspath to make it absolute, like shown here.
#
import os
import sys

# Adding all the source code to the path. Sphinx generates documentation from
# Docstrings in the source code.
sys.path.insert(
    0, os.path.abspath("../../oda-canvas/source/operators/identity-config/keycloak")
)
sys.path.insert(
    0,
    os.path.abspath(
        "../../oda-canvas/source/operators/identity-config/keycloak/identity-listener-keycloak"
    ),
)
sys.path.insert(
    0, os.path.abspath("../../oda-canvas/source/operators/component-management")
)
sys.path.insert(
    0, os.path.abspath("../../oda-canvas/source/operators/api-management/istio")
)
sys.path.insert(
    0, os.path.abspath("../../oda-canvas/source/operators/api-management/kong")
)
sys.path.insert(
    0, os.path.abspath("../../oda-canvas/source/operators/api-management/apache-apisix")
)
sys.path.insert(
    0, os.path.abspath("../../oda-canvas/source/operators/api-management/apigee")
)
sys.path.insert(
    0, os.path.abspath("../../oda-canvas/source/operators/api-management/azure-apim")
)
sys.path.insert(
    0,
    os.path.abspath("../../oda-canvas/source/operators/api-management/whalecloud-apim"),
)
sys.path.insert(
    0, os.path.abspath("../../oda-canvas/source/operators/credentials-management")
)
sys.path.insert(
    0,
    os.path.abspath(
        "../../oda-canvas/source/operators/secretsmanagementOperator-hc/docker"
    ),
)
sys.path.insert(
    0,
    os.path.abspath(
        "../../oda-canvas/source/operators/dependentApiSimpleOperator/docker/src"
    ),
)

# -- Project information -----------------------------------------------------

project = "ODA-Component Accelerator"
copyright = "2025, TM Forum ODA-Component Accelerator project"
author = "TM Forum ODA-Component Accelerator project"

# The full version, including alpha/beta/rc tags
release = "v1"


# -- General configuration ---------------------------------------------------

# Add any Sphinx extension module names here, as strings. They can be
# extensions coming with Sphinx (named 'sphinx.ext.*') or your custom
# ones.
extensions = [
    "sphinx.ext.autodoc",
    "myst_parser",
    "sphinx_markdown_tables",
    "rst2pdf.pdfbuilder",
]

# Optional: PDF options
pdf_stylesheets = ["sphinx", "kerning", "a4"]
pdf_use_index = True
pdf_use_coverpage = True

pdf_documents = [
    (
        "index",
        "ODA Accelerator",
        "Open Digital Architecture - Component Acclerator",
        "Lester Thomas",
    )
]


# Enable MyST extensions
myst_enable_extensions = [
    "linkify",  # Automatically turn URLs into links
    "substitution",
]

source_suffix = {".rst": "restructuredtext", ".md": "markdown"}
# Add any paths that contain templates here, relative to this directory.
templates_path = ["_templates"]

# List of patterns, relative to source directory, that match files and
# directories to ignore when looking for source files.
# This pattern also affects html_static_path and html_extra_path.
exclude_patterns = ["_build", "Thumbs.db", ".DS_Store"]


# -- Options for HTML output -------------------------------------------------

# The theme to use for HTML and HTML Help pages.  See the documentation for
# a list of builtin themes.
#
html_theme = "sphinx_rtd_theme"

# Add any paths that contain custom static files (such as style sheets) here,
# relative to this directory. They are copied after the builtin static files,
# so a file named "default.css" will overwrite the builtin "default.css".
html_static_path = ["_static"]
master_doc = "index"
autodoc_member_order = "bysource"
html_css_files = ["css/custom.css"]
print("Copying README and image files")
import shutil
import os


def copyImagesAndMarkdown(src_dir, dest_dir):
    # Check if the source directory exists
    if not os.path.exists(src_dir):
        print("Source directory does not exist - " + src_dir)
        raise ValueError("Source directory does not exist")

    # Check if the destination directory exists
    if not os.path.exists(dest_dir):
        print("Destination directory does not exist - " + dest_dir)
        raise ValueError("Destination directory does not exist")

    # Iterate through all the files in the source directory
    for file in os.listdir(src_dir):
        # Check if the file is an image
        if (
            file.endswith(".jpg")
            or file.endswith(".png")
            or file.endswith(".gif")
            or file.endswith(".md")
            or file.endswith(".feature")
            or file.endswith(".puml")
        ):
            # Construct the full path to the file
            src_path = os.path.join(src_dir, file)
            dest_path = os.path.join(dest_dir, file)
            # Copy the file
            shutil.copy(src_path, dest_path)


# Controllers
copyImagesAndMarkdown("../../oda-canvas/source", "./canvas/source")
copyImagesAndMarkdown("../../oda-canvas/source/operators", "./canvas/source/operators")
copyImagesAndMarkdown(
    "../../oda-canvas/source/operators/component-management",
    "./canvas/source/operators/component-management",
)
copyImagesAndMarkdown(
    "../../oda-canvas/source/operators/api-management/istio",
    "./canvas/source/operators/api-management/istio",
)
copyImagesAndMarkdown(
    "../../oda-canvas/source/operators/api-management/apache-apisix",
    "./canvas/source/operators/api-management/apache-apisix",
)
copyImagesAndMarkdown(
    "../../oda-canvas/source/operators/api-management/apigee",
    "./canvas/source/operators/api-management/apigee",
)
copyImagesAndMarkdown(
    "../../oda-canvas/source/operators/api-management/azure-apim",
    "./canvas/source/operators/api-management/azure-apim",
)
copyImagesAndMarkdown(
    "../../oda-canvas/source/operators/api-management/kong",
    "./canvas/source/operators/api-management/kong",
)
copyImagesAndMarkdown(
    "../../oda-canvas/source/operators/api-management/whalecloud-apim",
    "./canvas/source/operators/api-management/whalecloud-apim",
)
copyImagesAndMarkdown(
    "../../oda-canvas/source/operators/identity-config/keycloak",
    "./canvas/source/operators/identity-config/keycloak",
)

copyImagesAndMarkdown(
    "../../oda-canvas/source/operators/component-management/sequenceDiagrams",
    "./canvas/source/operators/component-management/sequenceDiagrams",
)
copyImagesAndMarkdown(
    "../../oda-canvas/source/operators/api-management/istio/sequenceDiagrams",
    "./canvas/source/operators/api-management/istio/sequenceDiagrams",
)
copyImagesAndMarkdown(
    "../../oda-canvas/source/operators/api-management/apache-apisix/sequenceDiagrams",
    "./canvas/source/operators/api-management/apache-apisix/sequenceDiagrams",
)
copyImagesAndMarkdown(
    "../../oda-canvas/source/operators/api-management/apache-apisix/sequenceDiagrams",
    "./canvas/source/operators/api-management/apache-apisix/sequenceDiagrams",
)
copyImagesAndMarkdown(
    "../../oda-canvas/source/operators/api-management/kong/sequenceDiagrams",
    "./canvas/source/operators/api-management/kong/sequenceDiagrams",
)
copyImagesAndMarkdown(
    "../../oda-canvas/source/operators/api-management/whalecloud-apim/sequenceDiagrams",
    "./canvas/source/operators/api-management/whalecloud-apim/sequenceDiagrams",
)
copyImagesAndMarkdown(
    "../../oda-canvas/source/operators/identity-config/keycloak/sequenceDiagrams",
    "./canvas/source/operators/identity-config/keycloak/sequenceDiagrams",
)
copyImagesAndMarkdown("../../oda-canvas/source/webhooks", "./canvas/source/webhooks")
copyImagesAndMarkdown(
    "../../oda-canvas/source/utilities/canvas-log-viewer", "./canvas/source/utilities"
)
copyImagesAndMarkdown(
    "../../oda-canvas/source/utilities/component-viewer", "./canvas/source/utilities"
)
copyImagesAndMarkdown("../../oda-canvas/source/utilities", "./canvas/source/utilities")
copyImagesAndMarkdown("../../oda-canvas/usecase-library", "./canvas/usecase-library")
copyImagesAndMarkdown(
    "../../oda-canvas/usecase-library/pumlFiles", "./canvas/usecase-library/pumlFiles"
)
copyImagesAndMarkdown(
    "../../oda-canvas/feature-definition-and-test-kit",
    "./canvas/feature-definition-and-test-kit",
)
copyImagesAndMarkdown(
    "../../oda-canvas/feature-definition-and-test-kit/images",
    "./canvas/feature-definition-and-test-kit/images",
)
copyImagesAndMarkdown("../../oda-canvas/installation", "./canvas/installation")

# Base Documentation files
copyImagesAndMarkdown("../", "./docs")
shutil.copy2("../../oda-ca-docs/.github/Issues.PNG", "./docs/.github")
shutil.copy2(
    "../../oda-ca-docs/oda-component-oas-v1beta2.yaml",
    "./docs/oda-component-oas-v1beta2.yaml",
)
shutil.copy2(
    "../../oda-ca-docs/oda-component-oas-v1beta3.yaml",
    "./docs/oda-component-oas-v1beta3.yaml",
)
shutil.copy2(
    "../../oda-ca-docs/oda-component-oas-v1.yaml",
    "./docs/oda-component-oas-v1.yaml",
)

# Delete files that are not needed ./docs/ODACanvasDesignGuidelines.md ./docs/README.md
os.remove("./docs/ODACanvasDesignGuidelines.md")
os.remove("./docs/README.md")

# Observability tutorial
copyImagesAndMarkdown(
    "../../oda-ca-docs/Observability-Tutorial", "./docs/Observability-Tutorial"
)
copyImagesAndMarkdown(
    "../../oda-ca-docs/Observability-Tutorial/images",
    "./docs/Observability-Tutorial/images",
)

# Component build tutorial
copyImagesAndMarkdown(
    "../../oda-ca-docs/ODA-Component-Tutorial", "./docs/ODAComponentTutorial"
)
copyImagesAndMarkdown(
    "../../oda-ca-docs/ODA-Component-Tutorial/images",
    "./docs/ODAComponentTutorial/images",
)

# Troubleshooting guide

copyImagesAndMarkdown(
    "../../oda-ca-docs/Troubleshooting-Guide", "./docs/TroubleshootingGuide"
)
copyImagesAndMarkdown(
    "../../oda-ca-docs/Troubleshooting-Guide/images",
    "./docs/TroubleshootingGuide/images",
)

# Delete files that are not needed  /docs/TroubleshootingGuide/README.md
os.remove("./docs/TroubleshootingGuide/README.md")

# Canvas helm charts
copyImagesAndMarkdown("../../oda-canvas", "./canvas")


# Docs folder and developer subfolder
copyImagesAndMarkdown("../../oda-canvas/docs/developer", "./docs/developer")
copyImagesAndMarkdown(
    "../../oda-canvas/docs/developer/images", "./docs/developer/images"
)
