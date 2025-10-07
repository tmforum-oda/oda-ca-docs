# 21. Optimize ODA Canvas Repository for AI Coding Agents

Date: 2025-10-06

## Status

In progress

## Context

AI coding assistants (e.g., GitHub Copilot, Gemini Code Assist, Amazon Q, Claude Code, Cursor) are increasingly used to accelerate development, improve code quality, and lower the barrier to contribution. To fully leverage these tools, the ODA Canvas repository must be structured and documented in a way that is machine-ingestible, context-rich, and prompt-friendly.

The ai-coding-assistants.md document outlines best practices for integrating AI assistants, including:

• Use of Model Context Protocol (MCP) to provide structured context
• Adoption of tools like Context7
• Clear prompt engineering strategies and repo-specific instructions

## Decision

Optimize the ODA Canvas repository to support co-development by AI coding agents through the following actions:

1. **LLM friendly Documentation:**
   - Ensure all components, operators, and assets documented with clear architectural context and usage examples.
   - Agent-specific documentation for all popular coding agents.
   - Provide guidance for configuring AI assistants with Canvas-specific frameworks and standards.

2. **Promote Prompt Engineering Best Practices:**
   - Encourage developers to comment intent, use type hints, and break tasks into small units.
   - Include prompt templates and examples in the documentation.

## Consequences

• Improves onboarding and productivity for developers using AI coding assistants.
• Enhances consistency, traceability, and quality of AI-generated code.
• Aligns ODA Canvas with emerging cross-industry standards for AI-assisted development.
• Enables future automation and intelligent tooling across the TM Forum ecosystem.
