.. raw:: html

   <p class="oda-kicker">TM Forum Open Digital Architecture</p>

ODA Canvas and Components
=========================

.. raw:: html

   <div class="oda-hero">
     <p class="oda-lede">
       The <strong>ODA Canvas</strong> is the execution environment for ODA Components. It reads each
       component's specification and configures API gateways, identity, observability, secrets and
       more for it, automatically. This site documents the Canvas reference implementation and how to
       build ODA Components that run on it.
     </p>
   </div>

   <a class="oda-banner" href="docs/training-videos/index.html">
     <span class="oda-card-kicker">New: the ODA Canvas training series</span>
     <span class="oda-banner-title">Watch the training videos</span>
     <span class="oda-card-text">Short videos on the Canvas, security, AI, observability and agent skills, each with chapters and a transcript.</span>
   </a>

   <div class="oda-cards">
     <a class="oda-card" style="--accent: var(--oda-canvas)" href="canvas/README.html">
       <span class="oda-card-kicker">Get started</span>
       <span class="oda-card-title">What is the ODA Canvas?</span>
       <span class="oda-card-text">The Canvas, its operators and the reference implementation.</span>
     </a>
     <a class="oda-card" style="--accent: var(--oda-component)" href="canvas/Canvas-design.html">
       <span class="oda-card-kicker">Concepts and design</span>
       <span class="oda-card-title">Canvas design</span>
       <span class="oda-card-text">Security, observability, events and the AI-Native Canvas.</span>
     </a>
     <a class="oda-card" style="--accent: var(--oda-api)" href="canvas/usecase-library/README.html">
       <span class="oda-card-kicker">Use cases</span>
       <span class="oda-card-title">Use case library</span>
       <span class="oda-card-text">How the Canvas and components interact, step by step.</span>
     </a>
     <a class="oda-card" style="--accent: var(--oda-operator)" href="canvas/installation/README.html">
       <span class="oda-card-kicker">Install and operate</span>
       <span class="oda-card-title">Install the Canvas</span>
       <span class="oda-card-text">Prerequisites, installation, upgrades and release notes.</span>
     </a>
     <a class="oda-card" style="--accent: var(--oda-identity)" href="canvas/source/operators/README.html">
       <span class="oda-card-kicker">Reference</span>
       <span class="oda-card-title">Canvas operators</span>
       <span class="oda-card-text">TMFOP001 to TMFOP011: what each operator does and how.</span>
     </a>
     <a class="oda-card" style="--accent: var(--oda-ai)" href="docs/ODAComponentDesignGuidelines.html">
       <span class="oda-card-kicker">Build</span>
       <span class="oda-card-title">Build ODA Components</span>
       <span class="oda-card-text">Design guidelines, tutorials and AI coding assistants.</span>
     </a>
   </div>

.. Each toctree below is one section of the sidebar. Pages in the sub-folders of a
   README.md (or SKILL.md) are nested under it automatically by doc/conf.py, so only
   the top page of each area is listed here.

.. toctree::
   :hidden:
   :caption: Get started

   canvas/README.md
   docs/training-videos/index
   docs/Getting_started.md
   docs/Playbook.md

.. toctree::
   :hidden:
   :caption: Concepts and design

   canvas/Canvas-design.md
   canvas/SecurityPrinciples.md
   canvas/Authentication-design.md
   canvas/Observability-design.md
   canvas/Event-based-integration-design.md
   canvas/AI-Native-Canvas-design.md

.. toctree::
   :hidden:
   :glob:
   :caption: Use cases

   canvas/usecase-library/README.md
   canvas/usecase-library/UC*
   canvas/usecase-library/use-case-naming-conventions.md

.. toctree::
   :hidden:
   :caption: Install and operate

   canvas/installation/README.md
   canvas/installation/local-demo-canvas.md
   canvas/release-notes/ReleaseNotes.md

.. toctree::
   :hidden:
   :glob:
   :caption: Canvas operators

   canvas/source/operators/README.md
   canvas/source/operators/TMFOP*/README

.. toctree::
   :hidden:
   :glob:
   :caption: Helm charts

   canvas/charts/*/README

.. toctree::
   :hidden:
   :glob:
   :caption: Canvas services and tools

   canvas/source/README.md
   canvas/canvas-portal-v2/README.md
   canvas/canvas-portal/README.md
   canvas/docs/component-registry/README.md
   canvas/source/services/ComponentRegistry/README.md
   canvas/source/tmf-services/*/README
   canvas/source/webhooks/README.md
   canvas/source/utilities/README.md
   canvas/demos/multi-canvas-service-discovery/README.md

.. toctree::
   :hidden:
   :caption: Testing

   canvas/feature-definition-and-test-kit/README.md

.. toctree::
   :hidden:
   :caption: Building ODA Components

   docs/ODAComponentDesignGuidelines.md
   docs/ODAComponentTutorial/README.md
   docs/Observability-Tutorial/README.md

.. toctree::
   :hidden:
   :glob:
   :caption: AI-assisted development

   docs/AI-Assisted-Development.md
   canvas/ai-coding-assistants.md
   canvas/skills/*/SKILL
   canvas/docs/custom-copilot-documentation-agent.md
   canvas/docs/bdd-feature-generator-agent-guide.md

.. toctree::
   :hidden:
   :caption: Architecture decisions

   docs/Decision-Log/README.md

.. toctree::
   :hidden:
   :caption: Contributing

   canvas/CONTRIBUTING.md
   docs/CONTRIBUTING.md
   canvas/code-of-conduct.md
   canvas/docs/README.md
   canvas/devcontainer.md
   canvas/docs/developer/work-with-dockerimages.md
   canvas/source/operators/Installing KOPF development environment.md
   docs/TroubleshootingGuide/index.rst
   docs/DocumentationProcess.md
