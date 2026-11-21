# 0010: AI4Canvas Operator Scope Decision

## Status
Proposed

## Context
A proposal has been made to create an AI4Canvas Operator that would:
- Predict ODA Component workload patterns
- Make cognitive recommendations for resource allocation
- Provide goal-driven resource optimization
- Enable continuous optimization through machine learning

## Decision
We propose to **not** create a TMF-specific AI4Canvas Operator because:

1. **Existing Kubernetes Solutions**:
   - Kubernetes already has mature autoscaling capabilities (HPA, VPA, Cluster Autoscaler)
   - Projects like [Keda](https://keda.sh/) provide event-driven autoscaling
   - [Kubernetes Event-driven Autoscaling (KEDA)](https://keda.sh/) supports multiple scalers and metrics
   - Tools like [Prometheus Operator](https://github.com/prometheus-operator/prometheus-operator) handle monitoring

2. **Industry Standards**:
   - Resource optimization is not telco-specific
   - Following cloud-native practices promotes interoperability
   - Many cloud providers offer AI-driven optimization tools:
     - AWS Auto Scaling
     - Google Cloud's Autopilot
     - Azure's AKS auto-scaling

3. **Scope Consideration**:
   - TMF should focus on telco-specific aspects
   - Resource optimization is a general cloud computing concern
   - Reinventing existing solutions could lead to fragmentation

## Consequences

### Positive
- Leverage battle-tested Kubernetes ecosystem
- Reduce maintenance burden
- Benefit from broader community support
- Stay aligned with cloud-native practices

### Negative
- May require integration work to connect existing tools
- Less direct control over optimization algorithms
- Need to adapt to Kubernetes-native approaches

## Recommendation
Instead of creating a new operator, we recommend:
1. Document best practices for using existing Kubernetes autoscaling
2. Create guidelines for integrating with cloud provider optimization tools
3. Focus TMF efforts on telco-specific aspects of ODA Components

## References
- [Kubernetes Autoscaling](https://kubernetes.io/docs/tasks/run-application/horizontal-pod-autoscale/)
- [KEDA](https://keda.sh/)
- [Prometheus Operator](https://github.com/prometheus-operator/prometheus-operator)