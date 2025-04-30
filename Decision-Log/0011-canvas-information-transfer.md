# 0011: Uniform Method for Canvas-to-Component Information Transfer

## Status

Proposed by [@ferenc-hechler](https://www.github.com/ferenc-hechler)

## Context

ODA Components need to receive both static and dynamic information from the ODA Canvas:

- Endpoints for supporting functions
- Communication parameters for dependent APIs
- Service discovery information
- Configuration data

Current manual configuration via `values.yaml` is:

- Error-prone
- Breaks automation
- Difficult to maintain
- Not suitable for dynamic data

## Options Considered

1. **Manual Configuration**
   - Manual updates to `values.yaml`
   - Requires component redeployment
   - High maintenance overhead

2. **Mutating Webhook**
   - Patches deployment with environment variables
   - Requires component restart
   - Complex implementation

3. **ConfigMap/Secret as Environment Variables**
   - Kubernetes native solution
   - Requires restart for updates
   - Limited to string data

4. **ConfigMap/Secret as Mounted Filesystem**
   - Real-time updates possible
   - More flexible data formats
   - Potential file permission issues

5. **Canvas Information API**
   - RESTful API approach
   - Dynamic queries
   - No restarts needed
   - Clean separation of concerns

## Decision

Implement Option 5: Canvas Information API

Key characteristics:

- OpenAPI specification
- Default endpoint: `info.canvas.svc.cluster.local`
- Query-based information retrieval
- Separation of sensitive data (credentials handled separately)

## Implementation Details

The initial API for the Dependent API operator will use the TM Forum Service Inventory Management API `http://info.canvas.svc.cluster.local/tmf-api/serviceInventoryManagement/v5/`

Future Canvas to Component data couuld extend the use of Service Inventory Management API or use other APIs (with a default approach to use TM Forum Open-APIs).
