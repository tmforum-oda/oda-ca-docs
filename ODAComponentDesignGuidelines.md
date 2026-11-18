# Design Guidelines to create a new ODA Component

ODA Components are self-contained, independently deployable software modules that conform to the [TM Forum Open Digital Architecture](https://www.tmforum.org/oda/). The business drivers and conceptual model for ODA Components are described in [IG1171 ODA Component Definition](https://www.tmforum.org/resources/how-to-guide/ig1171-oda-component-definition/).

An ODA Component builds on open standards such as containers, Kubernetes and Helm, and adds telco-domain metadata. You start with containerized microservices described in Kubernetes manifests (usually packaged as a Helm chart). To turn them into an ODA Component you:

1. Add a `Component` resource that describes the component's *core function*, *event notification*, *management function* and *security function*.
2. Label every standard Kubernetes resource as belonging to the component.
3. Deploy and test the component on a Kubernetes cluster with the [ODA Canvas](../canvas/README.md) installed.

There are two ways to do this: let an AI coding agent do it with the **ODA Agent Skill**, or follow the **manual steps** below. Both produce a component that conforms to the **v1** component specification.

## Option 1: Use the ODA Agent Skill

The **implement-oda-component** Agent Skill builds a new ODA Component for you. It is in the `tm-forum-oda-consumer` plugin of the [ODA Agent Skills Marketplace](https://github.com/tmforum-oda/oda-agent-skills-marketplace). The skills work with Claude Code and most other popular AI coding agents, such as GitHub Copilot.

For a demonstration, watch [Part 7 of the training videos, The ODA Agent Skills Marketplace](training-videos/part-7.md) (or [on YouTube](https://youtu.be/6ONDbSo3RfY)).

### What the skill does

1. It asks which standard component to build, from TMFC001 to TMFC062 (for example *TMFC001 Product Catalog Management*).
2. It reads the component's published specification from the TM Forum ODA component catalogue: its mandatory and optional APIs, its events and its security API.
3. It implements the mandatory Open APIs, the security API the specification asks for (TMF669 Party Role or TMF672 Roles and Permissions), a Prometheus metrics endpoint and, if you want them, the optional APIs and an MCP server.
4. It generates Node.js microservices, a Dockerfile for each one, and a Helm chart that contains a **v1** `Component` resource with every resource labelled.
5. It can build and push the container images and install the chart on your Canvas.

The result is scaffolding: it gives you a component that the Canvas can deploy, expose and secure, with working API stubs. You still add your own business logic.

```{note}
The marketplace is an alpha release. Review what the agent generates, and check what it is about to do before it builds and pushes images or runs `helm install`.
```

### Prerequisites

- An AI coding agent that supports Agent Skills, for example [Claude Code](https://claude.com/claude-code).
- Docker, Helm and `kubectl`, and a container registry you can push images to.
- A Kubernetes cluster with the ODA Canvas installed, if you want to deploy the result. See the [installation guide](../canvas/installation/README.md).

### Install and run the skill

In Claude Code, add the marketplace and install the plugin:

```text
/plugin marketplace add https://github.com/tmforum-oda/oda-agent-skills-marketplace
/plugin install tm-forum-oda-consumer@tm-forum-oda-marketplace
```

From a terminal you can use `claude plugin marketplace add …` and `claude plugin install …` instead. Restart Claude Code after installing.

Then run the skill:

```text
/tm-forum-oda-consumer:implement-oda-component
```

Or describe what you want, and the agent picks the skill: for example *"Build an ODA Component for TMFC001 Product Catalog Management"*.

For other AI coding agents, see the [marketplace README](https://github.com/tmforum-oda/oda-agent-skills-marketplace) for installation steps. The skill was first developed as `create-oda-component` in the [reference-example-components](https://github.com/tmforum-oda/reference-example-components/tree/master/skills/create-oda-component) repository. The marketplace also has skills to audit an implementation against a component specification, generate API conformance tests, and draw component diagrams.

## Option 2: Build the component manually

These steps follow the **v1** component specification. The authoritative schema is the `Component` CustomResourceDefinition in the ODA Canvas, at [charts/oda-crds](https://github.com/tmforum-oda/oda-canvas/tree/main/charts/oda-crds/templates). There is also an OpenAPI version of the schema, [oda-component-oas-v1.yaml](oda-component-oas-v1.yaml).

The examples come from the [ProductCatalog reference component](https://github.com/tmforum-oda/reference-example-components/blob/master/charts/ProductCatalog/templates/component-productcatalog.yaml). The reference uses Helm template values; they are replaced here with the values for a release called `r1`.

### Step 1: Add the Component metadata

The `Component` resource holds the telco-domain knowledge that makes the component self-describing. It lists the Open APIs and events the component exposes and depends on, and the APIs it uses for security and management. The Canvas operators read it to configure the API gateway, identity management, observability and dependencies.

The resource has five sections under `spec`: `componentMetadata`, `coreFunction`, `eventNotification`, `managementFunction` and `securityFunction`.

#### Header and componentMetadata

```yaml
apiVersion: oda.tmforum.org/v1
kind: Component
metadata:
  name: r1-productcatalogmanagement
  labels:
    oda.tmforum.org/componentName: r1-productcatalogmanagement
spec:
  componentMetadata:
    id: TMFC001
    name: productcatalogmanagement
    functionalBlock: CoreCommerce
    publicationDate: 2024-09-17T00:00:00.000Z
    status: specified
    version: "0.0.1"
    description: "Simple Product Catalog ODA-Component from Open-API reference implementation."
    maintainers:
    - name: Lester Thomas
      email: lester.thomas@vodafone.com
    owners:
    - name: Lester Thomas
      email: lester.thomas@vodafone.com
```

- `apiVersion` is `oda.tmforum.org/v1` and `kind` is `Component`.
- The `oda.tmforum.org/componentName` label matches the resource name. Use the same label on every resource in the component (Step 2).
- `componentMetadata` identifies the component. `id` is the standard component it implements (for example `TMFC001`), and `functionalBlock` is its ODA functional block (`CoreCommerce`, `Production`, `PartyManagement`, `IntelligenceManagement` and so on).
- You can also add `eTom`, `SID` and `functionalFramework` lists, to say which eTOM processes, SID entities and functional framework functions the component covers.

The published specifications for all the standard components are in the TM Forum ODA component catalogue. Use them to find the APIs, events and security API that your component must implement.

#### coreFunction

```yaml
  coreFunction:
    exposedAPIs:
    - name: productcatalogmanagement
      id: TMF620
      apiType: openapi
      specification:
      - url: "https://raw.githubusercontent.com/tmforum-apis/TMF620_ProductCatalog/master/TMF620-ProductCatalog-v4.0.0.swagger.json"
      implementation: r1-prodcatapi
      path: /r1-productcatalogmanagement/tmf-api/productCatalogManagement/v4
      developerUI: /r1-productcatalogmanagement/tmf-api/productCatalogManagement/v4/docs
      port: 8080
      gatewayConfiguration:
        rateLimit:
          enabled: true
          identifier: IP
          limit: "100"
          interval: pm
        CORS:
          enabled: true
          allowCredentials: false
          allowOrigins: "*"
    - name: productcatalogmcp
      apiType: mcp
      implementation: r1-prodcatmcp
      path: /r1-productcatalogmanagement/mcp
      port: 8080
    dependentAPIs:
    - name: downstreamproductcatalog
      id: TMF620
      apiType: openapi
      specification:
      - url: "https://raw.githubusercontent.com/tmforum-apis/TMF620_ProductCatalog/master/TMF620-ProductCatalog-v4.0.0.swagger.json"
      required: true
```

`coreFunction` describes the business purpose of the component: the APIs it exposes and the APIs it depends on.

For each **exposed API**:

- `name` is unique within the component, and `id` is the TM Forum Open API number (for example `TMF620`).
- `apiType` is `openapi` for TM Forum Open APIs. The Canvas also accepts `mcp` (a Model Context Protocol server) and `a2a` (an Agent-to-Agent endpoint). These are experimental: see the [AI-Native Canvas design](../canvas/AI-Native-Canvas-design.md).
- `specification` is a list of one or more specifications, each with a `url` (and optionally a `version`). It is a list so that a component can offer more than one version, for example Gen-4 and Gen-5 of the same Open API.
- `implementation` is the name of the Kubernetes Service that implements the API, and `port` is the port it listens on.
- `path` is the root of the API, which the Canvas exposes through its API gateway. Append the resource paths from the specification to reach a resource. `developerUI` is an optional path to API documentation, such as Swagger UI.
- `gatewayConfiguration` is optional. It asks the API gateway to apply policies to the API: `apiKeyVerification`, `rateLimit`, `quota`, `OASValidation`, `CORS`, or a gateway-specific `template`.
- `resources` is an optional list of the API resources the component implements, and `required` says whether the API is mandatory.

For each **dependent API**, give the `name`, `id`, `apiType` and `specification` of an API the component calls, and whether it is `required`. The Canvas [Dependency Management operator](../canvas/source/operators/TMFOP005-Dependency-Management/README.md) finds an exposed API that matches and tells your component where it is.

#### eventNotification

```yaml
  eventNotification:
    publishedEvents: []
    subscribedEvents: []
```

`eventNotification` lists the events the component publishes (`publishedEvents`, with a `hub` for registering listeners) and the events it subscribes to (`subscribedEvents`, with a `callback` path and an optional `query` to filter them). Event support in the Canvas is still being developed, so most components leave these lists empty. Check the CRD for the current fields.

#### managementFunction

```yaml
  managementFunction:
    exposedAPIs:
    - name: metrics
      apiType: prometheus
      implementation: r1-productcatalogmanagement-sm
      path: /r1-productcatalogmanagement/metrics
      port: 4000
    dependentAPIs: []
```

`managementFunction` describes the APIs the component exposes to operate and manage it, rather than for its business function. The most common is a metrics endpoint with `apiType: prometheus` (or `openmetrics`), which the Canvas uses to configure monitoring. See the [Observability design](../canvas/Observability-design.md) and the [Observability tutorial](Observability-Tutorial/README.md).

#### securityFunction

```yaml
  securityFunction:
    canvasSystemRole: canvasRole
    exposedAPIs:
    - name: partyrole
      id: TMF669
      apiType: openapi
      specification:
      - url: "https://raw.githubusercontent.com/tmforum-apis/TMF669_PartyRole/master/TMF669-PartyRole-v4.0.0.swagger.json"
      implementation: r1-partyroleapi
      path: /r1-productcatalogmanagement/tmf-api/partyRoleManagement/v4
      developerUI: /r1-productcatalogmanagement/tmf-api/partyRoleManagement/v4/docs
      port: 8080
```

`securityFunction` describes how the Canvas secures the component.

- `exposedAPIs` holds the API that publishes the component's roles, so that the Canvas can configure them in the identity service. Use either **TMF669 Party Role** or **TMF672 Roles and Permissions** (`permissionSpecificationSet`), whichever your component's specification asks for.
- `canvasSystemRole` is the name of a role in the component that the Canvas uses to call that API: to query the roles and to register for notifications when they change.
- `secretsManagement` is optional. With `type: sideCar`, the Canvas injects a sidecar into the pods you select, so that the component can read secrets from the Canvas secrets store.
- `componentRole` is an optional list of static roles, for components that don't expose a role API.

See [Authentication design](../canvas/Authentication-design.md) and the [Identity Config operator](../canvas/source/operators/TMFOP003-Identity-Config/README.md).

### Step 2: Label all the standard Kubernetes resources

When you deploy a Kubernetes manifest that contains several standard resources (Deployments, Services and so on), they are independent resources with no reference to each other. The component standard adds a label to every resource to show which component it belongs to.

In the ProductCatalog example, the Service definition has the component label:

```yaml
apiVersion: v1
kind: Service
metadata:
  name: r1-prodcatapi
  labels:
    app: r1-prodcatapi
    oda.tmforum.org/componentName: r1-productcatalogmanagement
spec:
  ports:
  - port: 8080
    targetPort: prodcatapi
    name: http-prodcatapi
  type: ClusterIP
  selector:
    app: r1-prodcatapi
```

When the component is deployed, the [Component Management operator](../canvas/source/operators/TMFOP001-Component-Management/README.md) uses the label to make the labelled resources children of the component. It adopts services, deployments, persistentvolumeclaims, jobs, cronjobs, statefulsets, configmaps, secrets, serviceaccounts, roles and rolebindings. Then, when you delete the component (`kubectl delete component <componentname>`, or `helm uninstall`), Kubernetes garbage collection deletes all its resources too.

Don't use these resources in a component:

- **Ingress**: a component expresses its intent through its APIs, and the Canvas creates the ingress or API gateway routes.
- **Pod, ReplicaSet**: use a Deployment for stateless microservices, or a StatefulSet for stateful ones.
- **DaemonSet**: a component has no need for one.
- **ClusterRole, ClusterRoleBinding**: use a Role and RoleBinding instead.

The component conformance tests give a warning if a component includes them.

### Step 3: Deploy and test on an ODA Canvas

1. Install the ODA Canvas on a Kubernetes cluster: see the [installation guide](../canvas/installation/README.md).
2. Install your component's Helm chart into the `components` namespace, then check that the Canvas has deployed it:

   ```bash
   helm install r1 ./productcatalog -n components
   kubectl get components -n components
   kubectl get exposedapis -n components
   ```

   The component's status moves to `Complete` when all its exposed APIs are configured and its security roles are registered.
3. Run the conformance test kit for your component. Each standard component in the ODA component catalogue has its own CTK, which checks the component metadata and runs the Open API CTKs against the exposed APIs. See [Component CTK](TroubleshootingGuide/CTK.md).

For working examples, see the [ProductCatalog reference component](https://github.com/tmforum-oda/reference-example-components/tree/master/charts/ProductCatalog) and the v1 test components in the Canvas [feature-definition-and-test-kit/testData](https://github.com/tmforum-oda/oda-canvas/tree/main/feature-definition-and-test-kit/testData) folder. The [ODA Component tutorial](ODAComponentTutorial/README.md) walks through building and deploying one.

## Changes between versions

The Canvas still accepts older versions (v1beta2, v1beta3 and v1beta4) and converts them to v1. v1beta2 is deprecated. For new components, use v1.

**v1beta4 to v1** (December 2024)

- v1 is the first generally available version. It has the same schema as v1beta4.

**v1beta3 to v1beta4**

- The component identity fields (`id`, `name`, `version`, `description`, `functionalBlock`, `publicationDate`, `status`, `maintainers`, `owners`) moved from the top of `spec` into `spec.componentMetadata`.
- `eTom`, `SID` and `functionalFramework` were added to `componentMetadata`.
- Each exposed and dependent API gained an `id` (the TMF Open API number), and each specification a `version`.
- The API gateway settings (`apiKeyVerification`, `rateLimit` and so on) moved into `gatewayConfiguration`.
- `securityFunction.controllerRole` was renamed `canvasSystemRole`.

**v1beta2 to v1beta3**

- `specification` became a list (with at least one item) instead of a single string, so that a component can expose more than one version of an Open API, for example Gen-4 and Gen-5.
- `apitype` was renamed `apiType`, to use camelCase like the other properties.
- The `prometheus` API type is now allowed only in `managementFunction`.
- `securityFunction.secretsManagement` was added.
- Published and subscribed events gained properties such as `hub`, `callback`, `implementation`, `port` and `query`.

**v1beta1 to v1beta2**

- The security and management sections were aligned with the core function section.
- `functionalBlock`, `version` and `status` were added, so that a directory of components could be generated from the component definitions.
- `security` was renamed `securityFunction`, and `management` was renamed `managementFunction`.

For the details of every change, see the Canvas [release notes](../canvas/release-notes/ReleaseNotes.md).
