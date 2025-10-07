# 16. Model Context Protocol (MCP) Server for TM Forum Open-APIs

Date: 2025-10-06

## Status

Approved in 2025 Innovation Hub Elevate Workshop

## Context

The telecommunications industry is rapidly adopting AI agents to automate workflows, enhance customer experience, and optimize operations. To enable meaningful interaction between AI agents and telco systems, TM Forum Open-APIs must be made accessible in a context-aware, machine-consumable format.

The Model Context Protocol (MCP) is an open standard designed to connect AI applications to external systems—including APIs, tools, and data sources—through an open standard. MCP servers act as context providers, enabling AI agents to understand and interact with APIs in a domain-specific way.

## Decision

Establish Model Context Protocol (MCP) server(s) for TM Forum Open-APIs to enable AI agent interaction. The implementation approach may vary based on operational requirements.

Multiple MCP servers may be created for a single Open-API to represent different domain contexts (e.g., Retail, Wholesale, B2B, B2C), aligned with the Domain Context Specialization strategy.

It is also possible to build MCP tools on top of orchestration or composite APIs.

Each MCP server will:

• Serve contextual metadata and tool definitions for the Open-API(s)
• Be discoverable via the API Inventory (MCP is just a type of API in the Canvas)
• Support versioning, governance, and lifecycle management
• Enable AI agents to reason about and interact with TM Forum APIs in a safe, auditable, and policy-compliant manner


## Consequences

• TM Forum Open-API specifications must be accompanied by MCP server definitions for AI enablement.
• Canvas and reference implementations must support MCP server integration and discovery.
• AI agents will gain structured, contextual access to telco APIs, improving automation and interoperability.
• The telco industry aligns with cross-industry standards for AI integration, promoting ecosystem compatibility and reducing fragmentation.
