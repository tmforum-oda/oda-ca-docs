# 9. Monorepo Helm and Container versioning

Date: 2024-05-15

## Status

Pending ODA-CA board approval

## Context

We need to decide how to version the container image for each operator, and how they fit into the overall Canvas versioning

## Decision

The following was proposed in Innovation Hub weekly clinic 2024-05-15:

The Canvas is a chart of charts. The master Canvas helm chart [tmforum-oda/oda-canvas/charts/canvas-oda/Chart.yaml](https://github.com/tmforum-oda/oda-canvas/blob/master/charts/canvas-oda/Chart.yaml) contains all the modular parts of the Canvas as helm chart dependencies.

The decision is to ensure every operator is deployed using its own Helm chart, and to align the container image version with the chart version. This will allow each operator to be developed independently. It also allows organisations to create their own operatror implementations, again deployed as helm charts.

## Consequences

We need to refactor the existing component operator to separate out the API management, Identity Management and Observability parts into separate Helm charte. 
