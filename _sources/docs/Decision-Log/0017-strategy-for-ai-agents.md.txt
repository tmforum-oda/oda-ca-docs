# 17. Strategy for AI Agents: Modular, Autonomous, Self-Descriptive, CSP-Specific

Date: 2025-10-06

## Status

Approved in 2025 Innovation Hub Elevate Workshop

## Context

As AI Agents become increasingly relevant in digital transformation and automation, there is a growing need to define how they should be supported within the TM Forum Open Digital Architecture (ODA). The telco domain requires flexibility to reflect the diverse customer experiences and operational models of Communications Service Providers (CSPs).

This mirrors the approach taken in the Engagement Management domain, where TM Forum does not publish standard components. Instead, CSPs are expected to design and deploy CSP-specific Experience Management components tailored to their brand, strategy, and customer expectations.

The AI industry is rapidly evolving, with multiple frameworks and patterns emerging. TM Forum recognizes the need to observe industry developments before standardizing specific agent architectures or patterns.

## Decision

TM Forum will not define standard AI Agent implementations at this time, but will clarify how agents fit within the Canvas architecture.

The ODA Canvas supports five classes of architectural elements:

1. **Systems-of-record**: Traditional stateful backend systems
2. **Agents**: Autonomous AI-driven components
3. **Proxy components**: Integration adapters and gateways
4. **Assembly of components**: Composite solutions (e.g., ODA in a box)
5. **Assembly of components and agents**: Agentic ODA systems combining traditional components with AI agents

Additionally, **serverless computing patterns** will be added to Canvas to support event-driven, stateless execution models commonly used in agentic architectures.

The architecture will support:

* Modular, self-descriptive AI Agents that expose their capabilities, interfaces, and context via metadata (e.g., using MCP or A2A)
* Interoperability between AI Agents and ODA Components through standardized APIs
* Autonomous: AI agents can reason to gather context, take actions (e.g. via tools accessed by MCP), can run in parallel (to speed up execution), can be used for validation.
* Human in the loop: AI agents output should be improved by working iteratively in loops. Human are needed to check the output and steer the loops.
* CSP-specific agent implementations, allowing differentiation and alignment with unique customer experience strategies

This approach enables innovation and flexibility while maintaining architectural consistency and interoperability. The recommendation is to observe developments in the AI industry and assess the impact on Canvas and components before committing to specific standardization frameworks for agents.

## Consequences

* TM Forum will focus on enabling agent interoperability and composability, rather than defining strict agent boundaries.
* Canvas and reference implementations may include example agents, but these are illustrative—not normative.
* CSPs are responsible for designing and deploying AI Agents aligned with their operational and customer experience goals.
* The Canvas architecture will be extended to explicitly recognize and support serverless patterns.
* TM Forum will continue to monitor AI industry trends to inform future standardization decisions, avoiding premature commitments to specific frameworks.
