# 6. Create common set of minimum mandatory JWT claims for interoperability

Date: 2023-05-24 (updated on 2025-05-07)

## Status

In progress

## Context

From the authentication workshop, it was identified that we may need to agree a common set of minimum mandatory JWT claims for interoperability.
The canvas will add PermissionSpecSet information to the JWT tokens (based on the authenticated user) and the component will need to
interpret this data.

We should examine if there are open standards for standard JWT claims that we should adopt, and if not, create a simple minimum set that we will adopt. 

## Decision

Comment: This decision needs some experimentation before a recommendation can be made.

## Consequences

The Canvas and any Vendor components will use a standard set of JWT claims. 
The Canvas CTK will include a tesable BDD feature for this.
