# ODA Canvas - use-case library

This use-case library defines the interactions between a generic **ODA Component** and the **ODA Canvas**. The ODA Canvas provides access to a range of common services (for identity management, authentication, observability etc) and has a set of **Software Operators** that automatically configure these services based on requirements defined in the ODA Component YAML specification. 

Software Operators are a key concept in the ODA Canvas. For more information, see the 2016 CoreOS blog post that introduced the concept: [Introducing Operators: Putting Operational Knowledge into Software](https://web.archive.org/web/20170129131616/https://coreos.com/blog/introducing-operators.html). There is a good definition of software operators at: [operatorhub.io/what-is-an-operator](https://operatorhub.io/what-is-an-operator).

The ODA canvas is itself a modular and extensible platform. The list below shows the operators that appear in the Canvas use-case inventory. The ODA-Component Accelerator is building a reference implementation of an ODA Canvas with a range of operators that are open-source and freely available for organizations to re-use, extend or replace with their own implementations. We expect a typical production implementation will use a combination of standard operators and custom operators that can implement that organizations specific operational policies.

## ODA Software Operators

This is a list of the Canvas operators (including status of whether this has been tested in the Canvas referernce implementation).

| Operator            | Description                     |
| ------------------- | ------------------------------- |
| Component Management | The Component operator manages the de-composition of an ODA component into APIs and Events (that are processed by their corresponding operators). |
| API Exposure | Configures the Service Providers API Gateway and/or Service Mesh to provide security, throttling and other non-functional services to allow API endpoints to be exposed to external consumers |
| API Discovery | Provides discovery for APIs where a component has declared a dependency. Internally it uses the Service Providers discovery capabilities to identify and give access to the appropriate API to fill a dependency. The component us updated via a ServiceActivationConfiguration Open-API call |
| License Manager | Audits compliance of component usage against licensing agreements |
| Identity | Provides identity management to grant system and user access to components. |
| Observability | Captures observability data and allows alarming, tracing and root-cause analysis of issues. |
| Event Hub | Enables components to publish and subscribe to asynchronous events |

