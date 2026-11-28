# 13. No certification for canvas implementation

Date: 2025-04-30

## Status

Pending ODA-CA board approval

## Context

The ODA Canvas serves as a platform to support certified components. There is a need to clarify the certification approach for canvas implementations versus components to ensure proper governance and quality assurance.

## Decision

There will be no formal certification of specific canvas implementations. Only the components that run on the canvas will be subject to certification. Instead of certification, functional tests will be created and used to verify that canvas implementations are functioning as expected.

## Rationale

From the beginning, the strategic decision was made that:

1. Components should be certified against the component specification.
2. A canvas must be provided as a platform that supports any certified component.
3. The canvas itself is only the net result of ensuring the components can run and not a certifiable entity in itself

## Consequences

1. BDD (Behavior-Driven Development) scripts will be written for canvas functionality to direct the functionality of the Canvas to meet the component specification.
2. Providers of canvas services will need to write step definitions to ensure their canvas services operate as expected. This includes the Innovation Hub for the reference implementation of the canvas.
3. While these tests can be run in conjunction with the component compliance test kits to help ensure/diagnose the correct functioning of each canvas implementation, there will be no formal certification for the canvas itself.
4. This approach maintains focus on component certification while ensuring canvas implementations can be validated through functional testing.
