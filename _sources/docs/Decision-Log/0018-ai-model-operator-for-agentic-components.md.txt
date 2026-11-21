# 18. AI Model Operator for Agentic Components

Date: 2025-10-06

## Status

Approved in 2025 Innovation Hub Elevate Workshop

## Context

Agentic Components require access to AI models—particularly Large Language Models (LLMs)—to fulfil their tasks. These components must be able to declare their model requirements in a standardized way, enabling dynamic and policy-compliant fulfilment.

To support this, we introduce an AI Model Operator, following the pattern already established for other Canvas operators. This AI Model operator reads the declarative requirements from the Component YAML and manages the underlying AI infrastructure, surfacing LLMs and other models in a controlled, auditable, and scalable manner. It complements the AI Gateway, which enforces policy and guardrails on model usage.

## Decision

Agentic Components must declare their AI model requirements (e.g., model type, guardrails) via a standardized YAML definition. These requirements are fulfilled by an AI Model Operator, which:

• Interfaces with the AI Gateway to enforce usage policies
• Selects and provisions appropriate models from the underlying platform (e.g., Google Gemini, Azure OpenAI, Anthropic, Llama etc.)
• Supports model versioning, lifecycle management, and observability
• Enables multi-model orchestration and fallback strategies

## Consequences

• Agentic Components must externalize model requirements in a declarative format.
• Canvas and reference implementations must include AI Model Operator integration.
• The AI Gateway and Model Operator must coordinate to ensure safe, compliant model access.

