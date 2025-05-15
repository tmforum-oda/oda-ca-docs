# 7. Provisioning identities across identity providers

Date: 2023-05-24

## Status

Approved at Innovation Hub clinic on 2025-05-07

## Context

From the authentication workshop, it was identified that we may need to provision identities across multiple identity providers, primarily where those identities are federated between a CSP and their outsource/franchise partners.

## Decision

One of the guiding principles of the ODA to date is that we should decide what belongs in a copmponent, what belongs in the Canvas and what belongs in the wider organisation. In this case, authorisation at the PermissionSpecificationSet level ("technical roles") is done by the component, authorisation at the PartyRole level ("Business/job roles") is done within the identity management platform, and the identity management platform itself is in the wider organisation. This places federation and provision of identities across identity providers out of scope, but we should test the ability for integration with multiple identity providers in the Canvas if clear business rules can be defined.

## Consequences

The Canvas will not facilitate provision of identities at all, other than those required to bootstrap the component onto the Canvas.

The identity providers will remain out of scope, so no provision for identities across identity platforms wil lbe made.

If a use case arises for integration with multiple identity platforms, we'll raise an issue for that and add it to a future sprint.
