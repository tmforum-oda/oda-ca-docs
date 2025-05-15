# 3. Reference Implementation API Gateway

Date: 2023-05-03

## Status

Approved at Innovation Hub clinic on 2025-05-07

## Context

The authentication use cases [UC001,UC002,UC003,UC007,UC008](https://github.com/tmforum-oda/oda-canvas/issues/62) require an API Gateway. A given canvas implementation can choose its own API Gateway; For the Reference Implementation we need an API Gatway with a freely downloadable implementation.

## Decision

The decision is to support two open-source API Gateways: Kong API Gatway and Apache APISIX. Both use the open-source model, cloud-native implementation and support for Kubernetes Operators. 

## Consequences

Having two API Gateways demonstrates the portability of components: Components declare their requirements for API Exposure and whe APIs will be exposed in either gateway.

The [API Management Operators](https://github.com/tmforum-oda/oda-canvas/tree/main/source/operators/api-management#api-management-operators) has links to the two operators that will be maintained as part of Reference Implementation, as well as other vendor supported alternatives.
