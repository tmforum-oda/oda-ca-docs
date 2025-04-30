# 8. Identity Propagation from Component to Component

Date: 2023-05-31

## Status

In progress

## Context

From the authentication workshop, it was proposed that we should propagate (pass-through) identities from component to component.

In contrast, [UC007](https://github.com/tmforum-oda/oda-canvas/blob/master/usecase-library/UC007-Authentication-external.md) states that we will not support pass-through authorisation.

Consider the example of an action being performed with three parties involved: an end-user who makes a request via a front-end system that, in turn, makes a request of a back-end system to fulfil the end-user request.

Performing pass-through authorisation means that the front-end system is authenticated against the back-end system as itself, but authorisation to perform an action against the back-end system is based on permissions sets associated with the identity of the end-user and not permission sets associated with the identity of the front-end. The advantage of this is that each system is able to control access to data within its own scope in a very fine-grained way. The disadvantage is that the logic for determining whether a request can be made to the back-end is executed by every back-end and every back-end must also be aware of the identity and permission sets of every single possible end-user.

Not performing pass-through authorisation means that the front-end system is authenticated against the back-end system as itself and authorisation to perform an action against the back-end system is based on a broad permission set associated with the identity of the front-end and not permission sets associated with the identity of the end-user. The advantage of this is that the logic for determining whether a request can be made to the back-end can be made before accessing the back end, and knowledge of individual identities is concentrated in a smaller number of places. The disadvantages are that the front-end must be much more aware of end-to-end business processes and the back-end has less fine-grained control over what actions are performed against data in its custody.

## Decision

This is an area where different approaches have their merits and drawbacks, but the decision is also relatively easily reversed. The proposal is to follow the recommendation already made in [UC007](https://github.com/tmforum-oda/oda-canvas/blob/master/usecase-library/UC007-Authentication-external.md) but to test the practicality of the decision with the help of interested component vendors.

## Consequences

Both options have their own consequences, but so far none of the known disadvantages rules either out and none of the known advantages favours one option comprehensively. As we learn more through experimentation there is a risk that we may have to reverse this decision.
