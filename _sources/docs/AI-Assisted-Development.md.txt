# AI-assisted development with ODA Agent Skills

AI software engineering agents, such as Claude Code, GitHub Copilot, Codex or Cursor, are changing how software is built. But an agent is only as good as the context it is given, and the process it follows. This page explains how the Open Digital Architecture (ODA) gives agents that context, and the Agent Skills that are available for building ODA Components and the ODA Canvas.

## Why the ODA helps software engineering agents

Google's [DORA 2025 State of AI-assisted Software Development](https://dora.dev/research/ai/) report found that AI is an amplifier: it magnifies whatever practices a team already has. With a modular, API-first architecture and good engineering habits, agents make things better. With tightly coupled systems and poor practices, they produce more problems, faster.

The ODA gives agents two things they need:

* **Scope.** A loosely coupled ODA Component is a well-bounded task, and agents do much better with focused work.
* **Context.** The Open API specifications, ODA Component specifications and use cases describe exactly what to build. That suits a spec-driven approach: the specification is written and checked first, and only then is the code written.

Tests matter even more with agents, because they give the agent something to verify its work against. The Canvas's [BDD features](../canvas/feature-definition-and-test-kit/README.md) and the API conformance tests play that role.

## What an Agent Skill is

A skill is a written procedure that an agent follows, step by step. It is a folder with a `SKILL.md` file of instructions, and it can also hold reference material, templates and scripts for deterministic steps. The description at the top of `SKILL.md` tells the agent when to use it, so you rarely need to name a skill: describe the task, and the agent picks the skill that fits.

Agent Skills are an [open standard](https://agentskills.io/), created by Anthropic. The same skill works in Claude Code, GitHub Copilot or another software engineering agent. They are not the same as the skills listed in an A2A agent card.

## The ODA Agent Skills Marketplace

The [ODA Agent Skills Marketplace](https://github.com/tmforum-oda/oda-agent-skills-marketplace) packages the ODA as an agent-friendly knowledge base, with skills that use it across the whole software delivery lifecycle. It is an **alpha**, released to show the concept and gather feedback.

### Knowledge as a data product

Many ODA documents are published as PDF or Word files, with diagrams as pictures. The marketplace converts them once into a form agents can read efficiently:

* Each use case (`TMFSxxx`), ODA Component (`TMFCxxx`) and Open API (`TMFxxx`) is a Markdown file, with its version, maturity and links to other assets in a YAML header.
* Sequence diagrams are PlantUML text, and other pictures have a written description, so an agent reads a diagram as text instead of spending tokens interpreting an image.
* JSON index files for use cases, components and APIs link them in both directions, so a skill can go straight from an API to every use case that depends on it. A formal OWL ontology was tried first; it is kept as a derived export, but the JSON indexes are simpler and use fewer tokens.

The knowledge is refreshed as TM Forum publishes new releases.

### Two plugins

| Plugin | For | Skills |
|---|---|---|
| `tm-forum-oda-consumer` | Teams building software that uses the ODA | 19 |
| `tm-forum-oda-creator` | People who extend the ODA itself, such as use case and component authors | 6 |

Each plugin carries its own copy of the knowledge, so it works from any project.

The consumer skills cover the whole lifecycle:

| Stage | Skills |
|---|---|
| Discover | `check-usecase-maturity`, `recommend-oda-components-for-requirement`, `decompose-requirement-against-oda`, `explain-etom-process` |
| Design | `capture-requirements-from-usecase`, `draft-architecture-diagram-from-usecase`, `generate-component-svg-diagram`, `draft-event-design-for-component`, `validate-design-against-oda` |
| Build | `implement-oda-component`, `generate-implementation-scaffold-from-usecase`, `generate-api-mocks-from-usecase` |
| Test | `generate-test-cases-from-usecase`, `generate-api-conformance-tests` |
| Run | `audit-implementation-against-usecase`, `audit-implementation-against-component`, `review-architecture-against-oda`, `assess-change-impact` |
| End to end | `deliver-oda-requirement` runs the whole chain, from a requirement to a delivered result |

The creator skills draft new use cases in the standard template (`draft-new-usecase-from-scenario`) and check drafts before they are submitted (`lint-usecase-draft`), propose component or API extensions for capability gaps (`propose-component-or-api-extension`), and harvest gaps and inconsistencies from published documents (`feedback-harvest-gaps-from-lessons-learned`, `feedback-propose-matrix-correction`). The creator plugin also includes `generate-component-svg-diagram`.

### Installing it

In Claude Code, add the marketplace and install the plugin you need. Each step also works as a plain-English prompt, for example "Add https://github.com/tmforum-oda/oda-agent-skills-marketplace as a plugin marketplace".

```
/plugin marketplace add https://github.com/tmforum-oda/oda-agent-skills-marketplace
/plugin install tm-forum-oda-consumer@tm-forum-oda-marketplace
```

Restart Claude Code to pick up the new skills. Typing `/tm-forum-oda-consumer:` lists every skill in the plugin, but you can also just describe the task, for example "Is TMFS030 safe to build against?". See the marketplace README for the other ways to install it, and for using the skills with other agents.

## Building ODA Components with an agent

The `implement-oda-component` skill started life as `create-oda-component`, in the [reference example components](https://github.com/tmforum-oda/reference-example-components) repository. It writes down how an engineer builds a component:

1. Select the component, from its published specification.
2. Choose its optional APIs.
3. Gather the image registry details.
4. Generate the source code: one microservice for each API, plus metrics, role set-up and an optional MCP server.
5. Generate the Helm chart.
6. Verify the result.
7. Build and push the images.
8. Write the READMEs.
9. Summarize what was built.

The Canvas used to be tested with a single, hand-built product catalog component. An agent following this skill scaffolded the six other reference components: TMFC002 Product Order Capture and Validation, TMFC005 Product Inventory, TMFC006 Service Catalog Management, TMFC007 Service Order Management, TMFC008 Service Inventory and TMFC028 Party Management. The `generate-component-svg-diagram` skill drew their architecture diagrams from their Helm charts.

These components are **scaffolding**: they provide the Open API interoperability, with create, read, update and delete operations, but not the component's business functionality. They are a starting point for a functional component, and a way to test a Canvas. To learn what the skill automates, follow the [ODA Component tutorial](ODAComponentTutorial/README.md), which builds a component by hand.

## Developing the Canvas with an agent

The [oda-canvas](https://github.com/tmforum-oda/oda-canvas) repository documents itself for AI coding agents.

**`AGENTS.md` files**, an open format that most coding agents read, are at the root of the repository and in each main area (`canvas-portal`, `charts`, `feature-definition-and-test-kit`, `source`, `source/operators` and `usecase-library`). They tell an agent what the project is, how it is structured, and the do's and don'ts.

**Agent Skills** for Canvas development are in its `skills` folder:

| Skill | Use it to |
|---|---|
| [canvas-ops-tutorial](../canvas/skills/canvas-ops-tutorial/SKILL.md) | Learn and explore a running Canvas: architecture, CRDs, deploying a component, APIs, dependencies, observability and BDD tests |
| [create-oda-operator](../canvas/skills/create-oda-operator/SKILL.md) | Build or change a Canvas operator with the Python KOPF framework |
| [helm-chart-development](../canvas/skills/helm-chart-development/SKILL.md) | Develop the Canvas Helm charts |
| [write-bdd-feature](../canvas/skills/write-bdd-feature/SKILL.md) | Write BDD features and step definitions for the test kit |
| [canvas-usecase-documentation](../canvas/skills/canvas-usecase-documentation/SKILL.md) | Write Canvas documentation, use cases and PlantUML diagrams |
| [github-actions-debugging](../canvas/skills/github-actions-debugging/SKILL.md) | Debug the GitHub Actions workflows |
| [skill-creator](../canvas/skills/skill-creator/SKILL.md) | Create and improve skills |

See [AI Coding Assistants](../canvas/ai-coding-assistants.md) for how to set up GitHub Copilot, Claude Code and other agents to use them, and the [AI-Native Canvas design](../canvas/AI-Native-Canvas-design.md) for how the Canvas supports AI agents at run time.

## Get involved

The best way to improve the skills is to use them. Try them, and share your feedback by opening an issue in the [marketplace repository](https://github.com/tmforum-oda/oda-agent-skills-marketplace/issues).
