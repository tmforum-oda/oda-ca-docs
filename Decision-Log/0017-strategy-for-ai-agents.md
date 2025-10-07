# 17. Strategy for AI Agents: Modular, Self-Descriptive, CSP-Specific

Date: 2025-10-06

## Status

In progress

## Context

As AI Agents become increasingly relevant in digital transformation and automation, there is a growing need to define how they should be supported within the TM Forum Open Digital Architecture (ODA). The telco domain requires flexibility to reflect the diverse customer experiences and operational models of Communications Service Providers (CSPs).

This mirrors the approach taken in the Engagement Management domain, where TM Forum does not publish standard components. Instead, CSPs are expected to design and deploy CSP-specific Experience Management components tailored to their brand, strategy, and customer expectations.

## Decision

TM Forum will not define standard AI Agent implementations at this time.

Instead, the architecture will support:

• Modular, self-descriptive AI Agents that expose their capabilities, interfaces, and context via metadata (e.g., using MCP or A2A)
• Interoperability between AI Agents and ODA Components through standardized APIs
• CSP-specific agent implementations, allowing differentiation and alignment with unique customer experience strategies

This approach enables innovation and flexibility while maintaining architectural consistency and interoperability.

## Consequences

• TM Forum will focus on enabling agent interoperability and composability, rather than defining strict agent boundaries.
• Canvas and reference implementations may include example agents, but these are illustrative—not normative.
• CSPs are responsible for designing and deploying AI Agents aligned with their operational and customer experience goals.
