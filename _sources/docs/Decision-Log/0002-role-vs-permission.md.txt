# 2. Role vs Permissions

Date: 2023-04-06

## Status

Approved at Innovation Hub clinic on 2025-05-07

## Context

In the feedback issue for authentication use-cases https://github.com/tmforum-oda/oda-canvas/issues/62#issuecomment-1493726891 @gjo-satec raises the question of whether components should define roles or permissions (and have the roles defined in the IDM solution).
@brian-burton added a question of technical vs business roles and proposed the components expose technical roles with business roles defined in the IDM solution.

We held a deep-dive into the TMF672 UserRole API with [@JonathanGoldberg](https://www.github.com/JonathanGoldberg)
[@brian-burton](https://www.github.com/brian-burton)
[@gjo-satec](https://www.github.com/gjo-satec)
[@lesterthomas](https://www.github.com/lesterthomas).

In the alpha and early beta version of the Canvas, we used TMF669 Party Role Management to advertise roles from
components into IDM, but with v5 changes to TMF672 User Roles and Permissions and with Jonathan Goldberg's help we now
understand that it is a better fit for our use case in ODA.

The recomendation was to use the PermissionSpecificationSet resource within the v5 TMF672 Open-API

## Decision

ODA will use TMF672 User Roles and Permissions API and PermissionSpecificationSet resource for technical role management. This is an optional API, since you can also define static roles as part of the Component Resource (see [IdentityConfig with statically and dynamically defined roles](https://github.com/tmforum-oda/oda-canvas/tree/main/source/operators/identity-config#readme) 

The decision is to add TMF672 and then deprecate TMF669 (so for a period of time the Canvas will support either 672 or 669 and will synchronize roles fropm both into the Identity Platform).





## Consequences

- A new beta of the ODA Canvas will be required that swaps TMF669 for TMF672, with a defined period of dual running
to allow migration.
- TMF 672 in a component will expose Permission Specification Sets to be mapped as technical roles (what role, or
combination of permissions the user has in relation to the component) into identity management.
- TMF 669 may or may not be exposed by the IDM to map Party Role Specifications to business roles (what role a user
performs in the wider organisation), but the business roles in IDM will have a set of technical roles mapped into them.
- Identities will receive a JWT with an audience that lists context relevant (this is an assumption and needs a separate
decision) Permission Specification Sets, such that the component can validate that:
    1. The user is correctly authenticated
    2. The user has a Permission Set that matches the Permission Specification Set name passed in the JWT.
