# 20. ML Model Integration via ML Ops Pipeline

Date: 2025-10-06

## Status

In progress

## Context

As TM Forum components evolve to support intelligent behaviour, there is a growing need to integrate custom Machine Learning (ML) models. These models may be developed by vendors, operators, or third parties and must be deployed in a secure, governed, and interoperable manner.

To support this, a standardized ML Ops pipeline is proposed. Components will include link to their ML model in their Component YAML definition, and a dedicated ML Model Operator will manage the lifecycle of these models—from download to deployment, testing, and approval.

## Decision

Enable TM Forum components to use custom ML models by:

• Including a link to the ML model in the component's YAML definition.
• Introducing an ML Model Operator responsible for:
  - Downloading the model from the declared source
  - Deploying it into an ML Ops pipeline for testing, validation, and approval
  - Publishing the model as an API once approved

This approach ensures that ML models are:

• Version-controlled
• Audited and validated before use
• Accessible to components via standardized APIs

## Consequences

• Component developers must declare ML models in YAML metadata.
• ML Model Operators must be included in Canvas and reference implementations.
• Governance processes must be defined for model approval, versioning, and rollback.
• Enables safe and scalable integration of AI capabilities across TM Forum components.
