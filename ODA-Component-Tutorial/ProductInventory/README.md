# Example Product Inventory component (including Security_role)

This is an example component that implements a TM Forum Product Inventory interface.

As part of the component implementation, it exposes role information to the Canvas using the TM Forum PartyRole API.

The component implements 4 micro-services:

* Product Inventory Microservice that implements the TMF637 Product Inventory Management API (based on the NodeJs reference implementation). This is deployed as a Kubernetes Deployment.
* Party Role Microservice that implements the TMF669 Party Role Management API (based on the NodeJs reference implementation). This is deployed as a Kubernetes Deployment.
* Role Initialization Microservice that bootstraps the initial Party Role interface. This is deployed as a Kubernetes Job that runs once when the component is initialised.
* a standard deployment of a mongoDb. This is deployed as a Kubernetes Deployment with a PersistentVolumeClaim.

The component envelope exposes the ProductInventory as a CoreFunction API and the PartyRole as a Security/PartyRole API.

The Kubernetes services adopt the Istio naming convention for the Port names.


 
