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
sys.path.insert(0, os.path.abspath('../../oda-canvas/source/operators/securityController'))
sys.path.insert(0, os.path.abspath('../../oda-canvas/source/operators/componentOperator'))
sys.path.insert(0, os.path.abspath('../../oda-canvas/source/operators/apiOperatorIstio'))
sys.path.insert(0, os.path.abspath('../../oda-canvas/source/operators/apiOperatorKong'))
sys.path.insert(0, os.path.abspath('../../oda-canvas/source/operators/apiOperatorApisix'))
sys.path.insert(0, os.path.abspath('../../oda-canvas/source/operators/apiOperatorApig'))
sys.path.insert(0, os.path.abspath('../../oda-canvas/source/operators/securityController'))
sys.path.insert(0, os.path.abspath('../../oda-canvas/source/operators/securityListener-keycloak'))

# -- Project information -----------------------------------------------------

project = 'ODA-Component Accelerator'
copyright = '2024, TM Forum ODA-Component Accelerator project'
author = 'TM Forum ODA-Component Accelerator project'

# The full version, including alpha/beta/rc tags
release = 'v1beta3'


# -- General configuration ---------------------------------------------------

# Add any Sphinx extension module names here, as strings. They can be
# extensions coming with Sphinx (named 'sphinx.ext.*') or your custom
# ones.
extensions = ['sphinx.ext.autodoc', 'myst_parser', 'sphinx_markdown_tables', 'rst2pdf.pdfbuilder']

# Optional: PDF options
pdf_stylesheets = ['sphinx', 'kerning', 'a4']
pdf_use_index = True
pdf_use_coverpage = True

pdf_documents = [('index', 'ODA Accelerator', 'Open Digital Architecture - Component Acclerator', 'Lester Thomas')]


# Enable MyST extensions
myst_enable_extensions = [
    "linkify",   # Automatically turn URLs into links
    "substitution"
]

source_suffix = {
    '.rst': 'restructuredtext',
    '.md': 'markdown'
}
# Add any paths that contain templates here, relative to this directory.
templates_path = ['_templates']

# List of patterns, relative to source directory, that match files and
# directories to ignore when looking for source files.
# This pattern also affects html_static_path and html_extra_path.
exclude_patterns = ['_build', 'Thumbs.db', '.DS_Store']


# -- Options for HTML output -------------------------------------------------

# The theme to use for HTML and HTML Help pages.  See the documentation for
# a list of builtin themes.
#
html_theme = 'sphinx_rtd_theme'

# Add any paths that contain custom static files (such as style sheets) here,
# relative to this directory. They are copied after the builtin static files,
# so a file named "default.css" will overwrite the builtin "default.css".
html_static_path = ['_static']
master_doc = 'index'
autodoc_member_order = 'bysource'
html_css_files = [
    'css/custom.css'
]
print('Copying README and image files')
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
    if file.endswith(".jpg") or file.endswith(".png") or file.endswith(".gif") or file.endswith(".md") or file.endswith(".feature") or file.endswith(".puml"):
      # Construct the full path to the file
      src_path = os.path.join(src_dir, file)
      dest_path = os.path.join(dest_dir, file)
      # Copy the file
      shutil.copy(src_path, dest_path)


# Controllers
copyImagesAndMarkdown('../../oda-canvas/source', './canvas/source')
copyImagesAndMarkdown('../../oda-canvas/source/operators', './canvas/source/operators')
copyImagesAndMarkdown('../../oda-canvas/source/operators/componentOperator', './canvas/source/operators/componentOperator')
copyImagesAndMarkdown('../../oda-canvas/source/operators/apiOperatorIstio', './canvas/source/operators/apiOperatorIstio')
copyImagesAndMarkdown('../../oda-canvas/source/operators/apiOperatorKong', './canvas/source/operators/apiOperatorKong')
copyImagesAndMarkdown('../../oda-canvas/source/operators/apiOperatorApisix', './canvas/source/operators/apiOperatorApisix')
copyImagesAndMarkdown('../../oda-canvas/source/operators/apiOperatorApig', './canvas/source/operators/apiOperatorApig')
copyImagesAndMarkdown('../../oda-canvas/source/operators/securityController', './canvas/source/operators/securityController')
copyImagesAndMarkdown('../../oda-canvas/source/operators/componentOperator/sequenceDiagrams', './canvas/source/operators/componentOperator/sequenceDiagrams')
copyImagesAndMarkdown('../../oda-canvas/source/operators/apiOperatorIstio/sequenceDiagrams', './canvas/source/operators/apiOperatorIstio/sequenceDiagrams')
copyImagesAndMarkdown('../../oda-canvas/source/operators/apiOperatorApisix/sequenceDiagrams', './canvas/source/operators/apiOperatorApisix/sequenceDiagrams')
copyImagesAndMarkdown('../../oda-canvas/source/operators/apiOperatorApig/sequenceDiagrams', './canvas/source/operators/apiOperatorApig/sequenceDiagrams')
copyImagesAndMarkdown('../../oda-canvas/source/operators/apiOperatorKong/sequenceDiagrams', './canvas/source/operators/apiOperatorKong/sequenceDiagrams')
copyImagesAndMarkdown('../../oda-canvas/source/operators/securityController/sequenceDiagrams', './canvas/source/operators/securityController/sequenceDiagrams')
copyImagesAndMarkdown('../../oda-canvas/source/webhooks', './canvas/source/webhooks')
copyImagesAndMarkdown('../../oda-canvas/source/webhooks/system-tests', './canvas/source/webhooks/system-tests')
copyImagesAndMarkdown('../../oda-canvas/source/webhooks/unit-tests', './canvas/source/webhooks/unit-tests')
copyImagesAndMarkdown('../../oda-canvas/source/utilities/canvas-log-viewer', './canvas/source/utilities')
copyImagesAndMarkdown('../../oda-canvas/source/utilities/component-viewer', './canvas/source/utilities')
copyImagesAndMarkdown('../../oda-canvas/source/utilities', './canvas/source/utilities')
copyImagesAndMarkdown('../../oda-canvas/usecase-library', './canvas/usecase-library')
copyImagesAndMarkdown('../../oda-canvas/usecase-library/pumlFiles', './canvas/usecase-library/pumlFiles')
copyImagesAndMarkdown('../../oda-canvas/feature-definition-and-test-kit', './canvas/feature-definition-and-test-kit')
copyImagesAndMarkdown('../../oda-canvas/feature-definition-and-test-kit/images', './canvas/feature-definition-and-test-kit/images')
copyImagesAndMarkdown('../../oda-canvas/installation', './canvas/installation')

# Delete files that are not needed ./docs/ODACanvasDesignGuidelines.md ./docs/README.md 
os.remove('./canvas/source/webhooks/system-tests/README.md')
os.remove('./canvas/source/webhooks/unit-tests/README.md')

# Base Documentation files
copyImagesAndMarkdown('../', './docs')
shutil.copy2('../../oda-ca-docs/.github/Issues.PNG', './docs/.github') 
shutil.copy2('../../oda-ca-docs/oda-component-oas-v1beta2.yaml', './docs/oda-component-oas-v1beta2.yaml') 
shutil.copy2('../../oda-ca-docs/oda-component-oas-v1beta3.yaml', './docs/oda-component-oas-v1beta3.yaml') 

# Delete files that are not needed ./docs/ODACanvasDesignGuidelines.md ./docs/README.md 
os.remove('./docs/ODACanvasDesignGuidelines.md')
os.remove('./docs/README.md')

# Observability tutorial
copyImagesAndMarkdown('../../oda-ca-docs/Observability-Tutorial', './docs/Observability-Tutorial')
copyImagesAndMarkdown('../../oda-ca-docs/Observability-Tutorial/images', './docs/Observability-Tutorial/images')

# Component build tutorial
copyImagesAndMarkdown('../../oda-ca-docs/ODA-Component-Tutorial', './docs/ODAComponentTutorial')
copyImagesAndMarkdown('../../oda-ca-docs/ODA-Component-Tutorial/images', './docs/ODAComponentTutorial/images')

# Troubleshooting guide

copyImagesAndMarkdown('../../oda-ca-docs/Troubleshooting-Guide', './docs/TroubleshootingGuide')
copyImagesAndMarkdown('../../oda-ca-docs/Troubleshooting-Guide/images', './docs/TroubleshootingGuide/images')

# Delete files that are not needed  /docs/TroubleshootingGuide/README.md
os.remove('./docs/TroubleshootingGuide/README.md')

# Canvas helm charts
copyImagesAndMarkdown('../../oda-canvas', './canvas')

# Component compliance test kits (CTKs)
copyImagesAndMarkdown('../../oda-component-ctk/', './ctk')

# Docs folder and developer subfolder
copyImagesAndMarkdown('../../oda-canvas/docs/developer', './docs/developer')
copyImagesAndMarkdown('../../oda-canvas/docs/developer/images', './docs/developer/images')
