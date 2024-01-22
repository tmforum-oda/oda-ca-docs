# Documentation Process

## Basic Process

The documentation (published at [https://tmforum-oda.github.io/oda-ca-docs/](https://tmforum-oda.github.io/oda-ca-docs/)) is generated from the README (and other markdown files), images, and code (including [DocStrings](https://en.wikipedia.org/wiki/Docstring)). All a normal contributor has to do is to ensure the code and other assets are documented in README files and code comments.

If you want to change the menu structure (e.g. to add a new section to the documentation), you need to follow the full process below which includes using Sphinx and .rst files (reStructuredText markup language). Feel free to ask for help in the slack channel if this is your first time!

## Full process

There is a video overview of the Documentation Process at:

 [![Documentation Process](https://img.youtube.com/vi/C_1WW0o0Z5o/0.jpg)](https://www.youtube.com/watch?v=C_1WW0o0Z5o)


The published home of the documentation is [https://tmforum-oda.github.io/oda-ca-docs/](https://tmforum-oda.github.io/oda-ca-docs/)

The documentation is published as GitHub pages from the [/docs](https://github.com/tmforum-oda/oda-ca-docs/tree/master/docs) folder. The documentation content is maintained as README and other content files in each of the TM Forum ODA repositories. The structure to the documentation uses the Sphinx framework (https://www.sphinx-doc.org/). The structure is maintained in the [/sphinx_documentation](https://github.com/tmforum-oda/oda-ca-docs/tree/master/sphinx_documentation) folder. Within this folder there is a `buildClean.bat` file that will re-build the documentation from the source.
