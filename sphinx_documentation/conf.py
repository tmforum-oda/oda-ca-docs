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

print('Copying README and image files')
import shutil

# Controllers
shutil.copy2('../../oda-ca/controllers/README.md', './caSource/controllers') 
shutil.copy2('../../oda-ca/controllers/componentOperator/README.md', './caSource/controllers/componentOperator') 
shutil.copy2('../../oda-ca/controllers/componentOperator/sequenceDiagrams/componentOperator.png', './caSource/controllers/componentOperator/sequenceDiagrams') 
shutil.copy2('../../oda-ca/controllers/apiOperatorSimpleIngress/README.md', './caSource/controllers/apiOperatorSimpleIngress') 
shutil.copy2('../../oda-ca/controllers/apiOperatorSimpleIngress/sequenceDiagrams/apiOperatorSimpleIngress.png', './caSource/controllers/apiOperatorSimpleIngress/sequenceDiagrams') 
shutil.copy2('../../oda-ca/controllers/apiOperatorIstio/README.md', './caSource/controllers/apiOperatorIstio') 
shutil.copy2('../../oda-ca/controllers/apiOperatorIstio/sequenceDiagrams/apiOperatorIstio.png', './caSource/controllers/apiOperatorIstio/sequenceDiagrams') 
shutil.copy2('../../oda-ca/controllers/apiOperatorApig/README.md', './caSource/controllers/apiOperatorApig') 
shutil.copy2('../../oda-ca/controllers/apiOperatorApig/sequenceDiagrams/apiOperatorApig.png', './caSource/controllers/apiOperatorApig/sequenceDiagrams') 
shutil.copy2('../../oda-ca/controllers/apiOperatorWSO2/README.md', './caSource/controllers/apiOperatorWSO2') 
shutil.copy2('../../oda-ca/controllers/securityController/README.md', './caSource/controllers/securityController') 
shutil.copy2('../../oda-ca/controllers/securityController/sequenceDiagrams/securitySequenceKeycloak.png', './caSource/controllers/securityController/sequenceDiagrams') 
shutil.copy2('../../oda-ca/controllers/securityController/sequenceDiagrams/securitySequenceKeycloakDetailed.png', './caSource/controllers/securityController/sequenceDiagrams') 

# Base Documentation files
shutil.copy2('../../oda-ca-docs/DocumentationProcess.md', './caDocs') 
shutil.copy2('../../oda-ca-docs/Playbook.md', './caDocs') 
shutil.copy2('../../oda-ca-docs/ContributionsGuide.md', './caDocs') 
shutil.copy2('../../oda-ca-docs/.github/Issues.PNG', './caDocs/.github') 
shutil.copy2('../../oda-ca-docs/ODAComponentDesignGuidelines.md', './caDocs') 

# Observability tutorial
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/README.md', './caDocs/Observability-Tutorial') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/BusinessMetric.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/ClusterExplorer.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/ComponentInKiali.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/ComponentInKiali2.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/ComponentPerformance.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/ComponentPerformance2.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/Containers.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/CPUGraph.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/getPods.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/Graph.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/Istio.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/Kiali.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/Kubeconfig.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/Monitoring.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/MonitoringArchitecture.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/ODA-RI.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/ODA-RI-Canvas.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/Pods.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/PrometheusTargets.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/PrometheusTargets2.png', './caDocs/Observability-Tutorial/images') 
shutil.copy2('../../oda-ca-docs/Observability-Tutorial/images/ReferenceExampleMetrics.png', './caDocs/Observability-Tutorial/images') 

# Component build tutorial
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

# Troubleshooting guide
shutil.copy2('../../oda-ca-docs/Troubleshooting-Guide/images/Controller-Logging.png', './caDocs/TroubleshootingGuide/images') 
shutil.copy2('../../oda-ca-docs/Troubleshooting-Guide/images/Get-Logs.png', './caDocs/TroubleshootingGuide/images') 
shutil.copy2('../../oda-ca-docs/Troubleshooting-Guide/images/Get-Pods.png', './caDocs/TroubleshootingGuide/images') 
shutil.copy2('../../oda-ca-docs/Troubleshooting-Guide/images/Logging-Output.png', './caDocs/TroubleshootingGuide/images') 
shutil.copy2('../../oda-ca-docs/Troubleshooting-Guide/images/Logging-Output-Definition.png', './caDocs/TroubleshootingGuide/images') 
shutil.copy2('../../oda-ca-docs/Troubleshooting-Guide/images/Metadata-Spec-Status.png', './caDocs/TroubleshootingGuide/images') 
shutil.copy2('../../oda-ca-docs/Troubleshooting-Guide/images/Metadata-Spec-Status-for-API.png', './caDocs/TroubleshootingGuide/images') 
shutil.copy2('../../oda-ca-docs/Troubleshooting-Guide/images/Rancher-Cluster-Explorer.png', './caDocs/TroubleshootingGuide/images') 
shutil.copy2('../../oda-ca-docs/Troubleshooting-Guide/images/Postman.png', './caDocs/TroubleshootingGuide/images') 
shutil.copy2('../../oda-ca-docs/Troubleshooting-Guide/Controller-Logging.md', './caDocs/TroubleshootingGuide') 
shutil.copy2('../../oda-ca-docs/Troubleshooting-Guide/CTK.md', './caDocs/TroubleshootingGuide') 
shutil.copy2('../../oda-ca-docs/Troubleshooting-Guide/Exploring-Custom-Resources.md', './caDocs/TroubleshootingGuide') 
shutil.copy2('../../oda-ca-docs/Troubleshooting-Guide/Internal-Kubernetes-Testing.md', './caDocs/TroubleshootingGuide') 
shutil.copy2('../../oda-ca-docs/Troubleshooting-Guide/Microservice-Logging.md', './caDocs/TroubleshootingGuide') 
shutil.copy2('../../oda-ca-docs/Troubleshooting-Guide/Postman-API-Tests.md', './caDocs/TroubleshootingGuide') 

# Canvas helm charts
shutil.copy2('../../oda-canvas-charts/README.md', './canvasCharts') 
shutil.copy2('../../oda-canvas-charts/Installation.md', './canvasCharts') 
shutil.copy2('../../oda-canvas-charts/Specification.md', './canvasCharts') 

# Component compliance test kits (CTKs)
shutil.copy2('../../oda-component-ctk/README.md', './ctk') 
shutil.copy2('../../oda-component-ctk/sampleOutput-L1-dynamic.png', './ctk') 
shutil.copy2('../../oda-component-ctk/sampleOutput-L1-static.png', './ctk') 
shutil.copy2('../../oda-component-ctk/sampleOutput-L2-dynamic.png', './ctk') 
shutil.copy2('../../oda-component-ctk/sampleOutput-L2-static.png', './ctk') 
shutil.copy2('../../oda-component-ctk/StagedClusters.png', './ctk') 

