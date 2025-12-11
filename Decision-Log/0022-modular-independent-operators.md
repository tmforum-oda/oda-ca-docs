# 22. Modular and Independent Operators

Date: 2025-12-11

## Status

In progress

## Context

The current architecture of the ODA Canvas has a monolithic Component Custom Resource Definition (CRD) that embeds all the structure of sub-CRDs including ExposedAPI, DependentAPI, IdentityConfig, PublishedNotification, SubscribedNotification, SecretsManagement, and others. The Component Operator decomposes this Component CRD and creates the sub-CRDs dynamically. While each sub-CRD already has its own dedicated operator to manage the relevant Canvas services, this approach creates tight coupling between the Component CRD structure and the sub-resource definitions.

This tight coupling has several implications:
- Changes to any sub-CRD structure require modifications to the Component CRD schema
- The Component Operator must be updated whenever sub-resource specifications change
- Independent development and versioning of sub-operators is complicated
- Organizations cannot easily replace or extend individual operators without forking the entire Component Operator
- Testing and validation of individual operators is more complex due to dependencies on the Component Operator

The ODA Canvas is designed to be a modular and extensible platform. To fully realize this vision, we need a more decoupled architecture that allows:
- Independent development cycles for each operator
- Separate versioning and release management for each operator
- Organizations to mix and match operators based on their needs
- Easier contribution and maintenance by different teams or organizations
- Clear separation of concerns between component lifecycle management and specific resource management

## Decision

We will restructure the Custom Resource Definitions (CRDs) and Software Operators to be more modular and independent:

### 1. Simplify the Component CRD

The Component CRD will be significantly reduced in size and scope. It will only contain:
- Component metadata (name, version, description, etc.)
- References to the sub-resources rather than embedding their full specifications
- Core component lifecycle information

The Component CRD will no longer embed the complete structure of sub-CRDs. Instead, sub-resources will be declared as independent Kubernetes resources.

### 2. Package Sub-CRDs as Independent Resources

Rather than having the Component Operator dynamically create sub-CRDs, these resources will be:
- Defined as independent Kubernetes custom resources
- Packaged directly in the Component's Helm chart
- Deployed alongside the Component resource during installation

This approach follows standard Kubernetes patterns and allows standard Helm chart management practices.

### 3. Separate GitHub Repositories

Each sub-CRD and its corresponding Operator will be moved to its own dedicated GitHub repository. This enables:
- Independent development and versioning
- Separate CI/CD pipelines
- Clear ownership and maintenance boundaries
- Easier contribution from different organizations
- Technology-specific implementations (e.g., different API gateway operators)

### 4. Affected Sub-CRD/Operators

The following sub-resources and operators will be separated into independent repositories:

- **Exposed API Management** - Manages API exposure through various API gateways (Kong, Istio, APISIX, Azure APIM, etc.)
- **Carbon Management** - Measures and manages energy and carbon usage by components
- **Credentials Management** - Manages credentials and authentication configuration
- **Dependent API Management** - Provides API dependency discovery services
- **Identity Config** - Configures identity management services for components
- **PDB Management** - Manages Pod Disruption Budgets for component workloads
- **Secrets Management** - Manages secure secrets vault integration for components

Each repository will contain:
- The CRD definition (YAML schema)
- The operator implementation
- Helm chart for deployment
- Documentation and examples
- Independent tests and CI/CD pipelines

### 5. Component Operator Responsibilities

The Component Operator will retain responsibility for:
- Overall component lifecycle management (install, upgrade, delete)
- Validation of component specification
- Status aggregation from sub-resources
- Coordinating component-level operations

The Component Operator will no longer:
- Embed sub-CRD schemas in the Component CRD
- Dynamically create sub-CRDs from Component specifications
- Manage the lifecycle of sub-resources beyond initial references

## Consequences

### Positive Consequences

1. **Modularity and Flexibility**
   - Organizations can choose which operators to deploy based on their requirements
   - Operators can be independently updated without affecting the entire Canvas
   - Multiple implementations of the same operator type can coexist (e.g., different API gateway operators)

2. **Independent Development**
   - Each operator team can work independently with their own release cycles
   - Reduces coordination overhead between different operator development teams
   - Clear ownership boundaries for each operator

3. **Easier Customization**
   - Organizations can replace individual operators with custom implementations
   - Technology-specific operators can be developed without modifying core Canvas
   - Simpler to extend with new operator types

4. **Better Versioning**
   - Each operator can follow semantic versioning independently
   - Breaking changes in one operator don't affect others
   - Helm chart dependencies clearly express version requirements

5. **Simplified Testing**
   - Each operator can be tested in isolation
   - Reduced test complexity for the Component Operator
   - Faster CI/CD pipelines for individual operators

6. **Improved Documentation**
   - Each repository contains focused documentation for its operator
   - Examples and use cases are co-located with the implementation
   - Clearer API contracts between components and operators

### Negative Consequences

1. **Initial Migration Effort**
   - Existing Component specifications will need to be refactored
   - Helm charts must be updated to include sub-resources
   - Documentation and examples need updates
   - Migration path needed for existing deployments

2. **Increased Repository Management**
   - More repositories to maintain and manage
   - Need for consistent governance across repositories
   - Potential for version incompatibilities if not carefully managed

3. **Helm Chart Complexity**
   - Component Helm charts become larger with embedded sub-resources
   - Chart developers must understand sub-resource schemas
   - More resources created during component installation

4. **Coordination Required**
   - Breaking changes to CRD schemas require coordination across repositories
   - Need for clear API contracts and versioning policies
   - Integration testing becomes more complex with multiple repositories

5. **Discovery and Learning Curve**
   - Developers need to know about multiple repositories
   - Understanding the full Canvas requires navigating more locations
   - Need for strong cross-repository documentation

### Mitigation Strategies

1. Maintain a clear catalog/index of all operators with their repositories
2. Establish versioning and compatibility guidelines across operators
3. Provide migration tools and documentation for existing deployments
4. Use Helm chart dependencies to manage operator versions
5. Implement comprehensive integration testing across the Canvas
6. Create clear documentation on the modular architecture
7. Maintain backwards compatibility during the transition period

## Implementation Notes

This decision aligns with Decision 0009 (Monorepo Helm and Container Versioning) which already established that every operator should be deployed using its own Helm chart with aligned container image versions.

The implementation should be phased:
1. Define the new simplified Component CRD schema
2. Create separate repositories for each operator, starting with the most mature ones
3. Update example Component Helm charts to include sub-resources
4. Provide migration documentation and tools
5. Gradually deprecate the old embedded sub-CRD approach
6. Update all documentation and examples

The Canvas master Helm chart will continue to declare the available operators as chart dependencies, maintaining a cohesive installation experience while enabling the flexibility of modular operators.
