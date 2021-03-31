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

sys.path.insert(0, os.path.abspath('../../oda-ca/controllers/'))
print(os.path.abspath('../../oda-ca/controllers/'))
print('********************************************')
# -- Project information -----------------------------------------------------

project = 'ODA-Component Accelerator'
copyright = '2021, TM Forum ODA-Component Accelerator project'
author = 'TM Forum ODA-Component Accelerator project'

# The full version, including alpha/beta/rc tags
release = 'v1alpha2'


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

print('Copying README and image files')
import shutil
shutil.copy2('../../oda-ca/controllers/README.md', './caSource/controllers') 
shutil.copy2('../../oda-ca/controllers/componentOperator/README.md', './caSource/controllers/componentOperator') 
shutil.copy2('../../oda-ca/controllers/componentOperator/sequenceDiagrams/componentOperator.png', './caSource/controllers/componentOperator/sequenceDiagrams') 
shutil.copy2('../../oda-ca/controllers/apiOperatorSimpleIngress/README.md', './caSource/controllers/apiOperatorSimpleIngress') 
shutil.copy2('../../oda-ca/controllers/apiOperatorSimpleIngress/sequenceDiagrams/apiOperatorSimpleIngress.png', './caSource/controllers/apiOperatorSimpleIngress/sequenceDiagrams') 
shutil.copy2('../../oda-ca/controllers/securityController/README.md', './caSource/controllers/securityController') 
shutil.copy2('../../oda-ca/controllers/securityController/sequenceDiagrams/securitySequenceKeycloak.png', './caSource/controllers/securityController/sequenceDiagrams') 
shutil.copy2('../../oda-ca/controllers/securityController/sequenceDiagrams/securitySequenceKeycloakDetailed.png', './caSource/controllers/securityController/sequenceDiagrams') 
shutil.copy2('../../oda-ca-docs/README.md', './caDocs') 
shutil.copy2('../../oda-ca-docs/ContributionsGuide.md', './caDocs') 
shutil.copy2('../../oda-ca-docs/.github/Issues.PNG', './caDocs/.github') 
shutil.copy2('../../oda-ca-docs/ODAComponentDesignGuidelines.md', './caDocs') 
shutil.copy2('../../oda-ca-docs/ODA-Component-Tutorial/README.md', './caDocs/ODAComponentTutorial') 
shutil.copy2('../../oda-ca-docs/ODA-Component-Tutorial/images/api-entrypoint.png', './caDocs/ODAComponentTutorial/images') 
shutil.copy2('../../oda-ca-docs/ODA-Component-Tutorial/images/ctkdynamicsuccess.png', './caDocs/ODAComponentTutorial/images') 
shutil.copy2('../../oda-ca-docs/ODA-Component-Tutorial/images/ctksuccess.png', './caDocs/ODAComponentTutorial/images') 
shutil.copy2('../../oda-ca-docs/ODA-Component-Tutorial/images/DTW-Video.png', './caDocs/ODAComponentTutorial/images') 
shutil.copy2('../../oda-ca-docs/ODA-Component-Tutorial/images/kubectl-get-components.png', './caDocs/ODAComponentTutorial/images') 
shutil.copy2('../../oda-ca-docs/ODA-Component-Tutorial/images/Open-API-Table.png', './caDocs/ODAComponentTutorial/images') 
shutil.copy2('../../oda-ca-docs/ODA-Component-Tutorial/images/Rancher.png', './caDocs/ODAComponentTutorial/images') 
shutil.copy2('../../oda-ca-docs/ODA-Component-Tutorial/images/Reference-Implementation.png', './caDocs/ODAComponentTutorial/images') 
shutil.copy2('../../oda-ca-docs/ODA-Component-Tutorial/images/swagger-ui.png', './caDocs/ODAComponentTutorial/images') 
shutil.copy2('../../oda-canvas-charts/README.md', './canvasCharts') 


#mike cartwright
