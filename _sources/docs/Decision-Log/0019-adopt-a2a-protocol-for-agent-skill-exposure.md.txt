# 19. Adopt A2A Protocol as Open Standard for Agent Skill Exposure

Date: 2025-10-06

## Status

In progress

## Context

As AI agents become central to telco automation, orchestration, and customer interaction, there is a growing need for a standardized way to describe and expose agent capabilities. The A2A Protocol (Agent-to-Agent Protocol) is an open standard designed to enable secure, interoperable communication between agents, including the structured exposure of agent skills via Agent Cards.

The protocol supports:

• Skill registration and discovery
• Secure RPC-style task execution
• Streaming and asynchronous operations
• Governance via the Linux Foundation

This aligns with TM Forum's goal to expose all standard data and processes to AI agents following open standards.

## Decision

Adopt the A2A Protocol as the open standard for agents to expose their skills and capabilities. All TM Forum-compliant agents must:

• Publish an Agent Card at a well-known endpoint (/.well-known/agent-card.json)
• Describe their skills, input/output modes, and capabilities using A2A-compliant schemas
• Support secure task execution and interoperability with other agents and orchestration platforms

## Consequences

• TM Forum agents must implement A2A-compliant interfaces and publish Agent Cards.
• Canvas and reference implementations must include A2A server and client examples.
• Agent registries and validation tools (e.g., A2A Inspector) must be adopted for compliance.
• The telco industry aligns with a Linux Foundation-governed protocol, promoting cross-industry interoperability and future-proofing agent ecosystems.
