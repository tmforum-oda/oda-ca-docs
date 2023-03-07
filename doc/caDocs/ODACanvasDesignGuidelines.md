# Design Guidelines to design an ODA Canvas

> Note this is a v1alpha1 release of the ODA Canvas Design Guidelines and is subject to breaking changes.

ODA Canvas is the Deployment and Runtime platform aligned to the [TM Forum Open Digital Architecture](https://tmforum.org/oda).

The business drivers and conceptual model for ODA Canvas is described in TBC.

The ODA Canvas concept builds on top of open standards like Kubernetes, and adds custom APIs and controller-managers to enable the automated and secure end-to-end lifecycle management of ODA Components. 
The starting point for deploying an ODA canvas is a Kubernetes cluster. 
To turn this into an ODA Canvas we will:

1. Add mandatory Canvas services
2. Add optional Canvas services
3. Test the deployment of an ODA Component into the Canvas

## 0. Kubernetes Cluster
<!-- Note this is completely arbitrary at this point - real entries to be completed at later date. -->

- Kubernetes version: **must** be at least 1.18.
- Number of control plane nodes: **should** be 3.
- Number of worker nodes: **should** be more than one, each in different locations/availability zones.

## 1. Mandatory non-functional capabilities

Namespace: canvas-system
- Component controller
- Security controller
- API controller
- Component CRD Webhook

Cluster-wide:
- Custom resource definitions (CRDs)
  - APIs (namespaced)
  - Components (namespaced)
  - zalando.org/v1/Clusterkopfpeerings
  - zalando.org/v1/kopfpeerings (namespaced)

## 2. Optional non-functional capabilities

> To be completed with details of service mesh, monitoring/observability, API gateway, IAM, etc.
> 
> These are all required capabilities but not necessarily provided by the Canvas

## 3. Test the Canvas for compliance

> Potentially borrow some tests from the ODA Component CTK
