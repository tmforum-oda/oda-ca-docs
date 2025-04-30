# 0012: Handling of Exposed APIs by Different Operators

## Status

Proposed

## Context

Currently, all APIs in the ODA Canvas are surfaced as exposed APIs through a single mechanism. However, different types of APIs (core business APIs, observability endpoints, identity endpoints, etc.) may require different treatment by various operators. This was discussed in the Canvas Spec Jam workshop on 2024-04-11, specifically around whether observability APIs should have their own CRD or use annotations within existing CRDs.

## Decision

Use `apiType` on ExposedAPI CRDs to indicate special handling requirements, rather than creating separate CRDs for different API types.

Key characteristics:
- Single ExposedAPI CRD maintains simplicity
- `apiType` indicate special handling requirements
- Multiple operators can act on the same ExposedAPI based on `apiType`
- Consistent with Kubernetes labeling patterns

Examples:
```yaml
  coreFunction:
    exposedAPIs: 
    - name: productcatalogmanagement
      specification:
      - url: "https://raw.githubusercontent.com/tmforum-apis/TMF620_ProductCatalog/master/TMF620-ProductCatalog-v4.0.0.swagger.json"
      implementation: {{.Release.Name}}-prodcatapi
      apiType: openapi
```

```yaml
  managementFunction: 
    exposedAPIs: 
    - name: metrics
      apiType: open-metrics
```

## Consequences

### Positive
- Simpler CRD landscape (no proliferation of API-related CRDs)
- Flexible and extensible approach
- Consistent with Kubernetes patterns
- Operators can selectively process APIs based on `apiType`
- Multiple operators can handle the same API for different purposes

### Negative
- Operators need to be label-aware
- Need to maintain label documentation and standards
- Potential for label conflicts or inconsistencies
- Requires coordination between operators

## Implementation Details

At present there isn't a separate operator for the open-metrics (to configure Prometheus, for example). The API Operators (for istio, Kong, APISIX and others) also create the configuration for the metrics, based on the `apiType`. This mechanism works for Prometheus open-source as well as DataDog and the Azure Managed Prometheus service.

In the future, if the requirements for observibility require it, we could separate this into a separate Observability operator, but still using the `apiType` of the ExposedAPI resource.