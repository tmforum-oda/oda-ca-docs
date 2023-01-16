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
sys.path.insert(0, os.path.abspath('../../oda-ca/controllers/securityController'))
sys.path.insert(0, os.path.abspath('../../oda-ca/controllers/componentOperator'))
sys.path.insert(0, os.path.abspath('../../oda-ca/controllers/apiOperatorSimpleIngress'))
sys.path.insert(0, os.path.abspath('../../oda-ca/controllers/apiOperatorWSO2'))
sys.path.insert(0, os.path.abspath('../../oda-ca/controllers/apiOperatorApig'))
sys.path.insert(0, os.path.abspath('../../oda-ca/controllers/apiOperatorIstio'))
sys.path.insert(0, os.path.abspath('../../oda-ca/controllers/securityController'))
sys.path.insert(0, os.path.abspath('../../oda-ca/controllers/securityListener-keycloak'))

# -- Project information -----------------------------------------------------

project = 'ODA-Component Accelerator'
copyright = '2022, TM Forum ODA-Component Accelerator project'
author = 'TM Forum ODA-Component Accelerator project'

# The full version, including alpha/beta/rc tags
release = 'v1alpha4'


# -- General configuration ---------------------------------------------------

# Add any Sphinx extension module names here, as strings. They can be
# extensions coming with Sphinx (named 'sphinx.ext.*') or your custom
# ones.
extensions = ['sphinx.ext.autodoc', 'recommonmark']
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
    raise ValueError("Source directory does not exist")

  # Check if the destination directory exists
  if not os.path.exists(dest_dir):
    raise ValueError("Destination directory does not exist")

  # Iterate through all the files in the source directory
  for file in os.listdir(src_dir):
    # Check if the file is an image
    if file.endswith(".jpg") or file.endswith(".png") or file.endswith(".gif") or file.endswith(".md"):
      # Construct the full path to the file
      src_path = os.path.join(src_dir, file)
      dest_path = os.path.join(dest_dir, file)
      # Copy the file
      shutil.copy(src_path, dest_path)


# Controllers
copyImagesAndMarkdown('../../oda-ca/controllers', './caSource/controllers')
copyImagesAndMarkdown('../../oda-ca/controllers/componentOperator', './caSource/controllers/componentOperator')
copyImagesAndMarkdown('../../oda-ca/controllers/apiOperatorSimpleIngress', './caSource/controllers/apiOperatorSimpleIngress')
copyImagesAndMarkdown('../../oda-ca/controllers/apiOperatorIstio', './caSource/controllers/apiOperatorIstio')
copyImagesAndMarkdown('../../oda-ca/controllers/apiOperatorApig', './caSource/controllers/apiOperatorApig')
copyImagesAndMarkdown('../../oda-ca/controllers/apiOperatorWSO2', './caSource/controllers/apiOperatorWSO2')
copyImagesAndMarkdown('../../oda-ca/controllers/securityController', './caSource/controllers/securityController')
copyImagesAndMarkdown('../../oda-ca/controllers/componentOperator/sequenceDiagrams', './caSource/controllers/componentOperator/sequenceDiagrams')
copyImagesAndMarkdown('../../oda-ca/controllers/apiOperatorSimpleIngress/sequenceDiagrams', './caSource/controllers/apiOperatorSimpleIngress/sequenceDiagrams')
copyImagesAndMarkdown('../../oda-ca/controllers/apiOperatorIstio/sequenceDiagrams', './caSource/controllers/apiOperatorIstio/sequenceDiagrams')
copyImagesAndMarkdown('../../oda-ca/controllers/apiOperatorApig/sequenceDiagrams', './caSource/controllers/apiOperatorApig/sequenceDiagrams')
copyImagesAndMarkdown('../../oda-ca/controllers/securityController/sequenceDiagrams', './caSource/controllers/securityController/sequenceDiagrams')

# Base Documentation files
copyImagesAndMarkdown('../', './caDocs')
shutil.copy2('../../oda-ca-docs/.github/Issues.PNG', './caDocs/.github') 

# Observability tutorial
copyImagesAndMarkdown('../../oda-ca-docs/Observability-Tutorial', './caDocs/Observability-Tutorial')
copyImagesAndMarkdown('../../oda-ca-docs/Observability-Tutorial/images', './caDocs/Observability-Tutorial/images')

# Component build tutorial
copyImagesAndMarkdown('../../oda-ca-docs/ODA-Component-Tutorial', './caDocs/ODAComponentTutorial')
copyImagesAndMarkdown('../../oda-ca-docs/ODA-Component-Tutorial/images', './caDocs/ODAComponentTutorial/images')

# Troubleshooting guide

copyImagesAndMarkdown('../../oda-ca-docs/Troubleshooting-Guide', './caDocs/TroubleshootingGuide')
copyImagesAndMarkdown('../../oda-ca-docs/Troubleshooting-Guide/images', './caDocs/TroubleshootingGuide/images')

# Canvas helm charts
copyImagesAndMarkdown('../../oda-canvas-charts/', './canvasCharts')

# Component compliance test kits (CTKs)
copyImagesAndMarkdown('../../oda-component-ctk/', './ctk')
