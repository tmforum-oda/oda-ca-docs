# 1. Reference Implementation API Gateway

Date: 2013-05-03

## Status

In progress

## Context

The authentication use cases [UC001,UC002,UC003,UC007,UC008](https://github.com/tmforum-oda/oda-canvas/issues/62) require an API Gateway. A given canvas
implementation can choose its own API Gateway; For the Reference Implementation we need an API Gatway with a freely downloadable implementation.


## Decision

The Proposal is to use the Kong API Gatway, because of its open-source model, cloud-native implementation and support for Kubernetes Operators.

## Consequences

The Canvas implementation needs to be extended to:
* install the Kong API Gatway
* Configure the specific authentication use-case, requiring integration to Keycloak
* Extend the API Operator to configure Keycloak

