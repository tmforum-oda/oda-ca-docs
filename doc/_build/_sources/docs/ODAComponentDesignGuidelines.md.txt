# Design Guidelines to create a new ODA Component

ODA Components are self-contained, independently deployable software modules that confirm to the [TM Forum Open Digital Architecture](https://tmforum.org/oda).

The business drivers and conceptual model for ODA Components is described in [IG1171 ODA Component Definition](https://projects.tmforum.org/wiki/display/PUB/IG1171%20ODA%20Component%20Definition%20R19.0.1).


The ODA Component concept builds on top of open standards like Docker and Kubernetes, and adds Telco-domain knowledge and meta-data. The starting point for building an ODA component is containerized, micro-service software described in a kubernetes manifest YAML file. To turn this into an ODA Component we will:

1. Add meta-data to the Kubernetes manifest describing the *Core Function*, *Notification/Reporting*, *Security* and *Management & Operation* of the software component.
2. Add labels to all the standard Kubernetes resources to label them as belonging to the component.
3. Test the deployment of the Component in an operating platform that has the ODA Canvas installed (for example the TM FOrum Open-Digital Lab). See the [Getting Started](https://github.com/tmforum-rand/oda-component-definitions#get-started) section to see how to install the ODA Canvas onto a kubernetes cluster environmnet.


## Step 1: ODA-Component Metadata

This guide has been updated to conform to the [v1beta1 component specification](Component-OAS-Specification-v1beta1.yaml).

The ODA-Component metadata contains all the Telco-domain knowledge that makes the component a self-describing deployable software module. From [IG1171 ODA Component Definition](https://projects.tmforum.org/wiki/display/PUB/IG1171%20ODA%20Component%20Definition%20R19.0.1), this meta-data describes the Open-APIs, event data schemas as well as security and management & operations for the component. 

The meta-data is defined using a Kubernetes [CustomResourceDefinition](https://kubernetes.io/docs/tasks/access-kubernetes-api/custom-resources/custom-resource-definitions/). This allows us to extend the Kubernetes API with our custom-defined schema for Telco meta-data. The CustomResourceDefinition schema is in the [github.com/tmforum-oda/oda-canvas-charts](https://github.com/tmforum-oda/oda-canvas-charts/blob/master/canvas/charts/crds/templates) repository.

There is an example of the metadata for the [productcatalog example component](https://github.com/tmforum-oda/oda-ca-docs/blob/master/examples/ProductCatalog/productcatalog/templates/component-productcatalog.yaml) broken-down into sections below:

```yaml
apiVersion: oda.tmforum.org/v1beta1
kind: component
metadata:
  name: vodafone-productcatalog
  labels:
    oda.tmforum.org/componentName: vodafone-productcatalog
    oda.tmforum.org/funtionalBlockName: CoreCommerce
```

This is the header information for the component, specifying the version of the CRD (Custom Resource Definition) that it is using, and providing a name and a label for the component and a label describing which functional block the component belongs to.

```yaml
spec:
  type: TMFC001-productcatalogmanagement
  version: 0.0.3
  description: "Simple Product Catalog ODA-Component from Open-API reference implementation." 
  maintainers:
  - name: Lester Thomas
    email: lester.thomas@vodafone.com
  owners:
  - name: Lester Thomas
    email: lester.thomas@vodafone.com  
```

This next section starts the `spec` for the component.

The `type` allows us to specify that this is an implementation of a standard type of Component. If the `type` and `version` match one of the **Golden Components** then the Component CTK will test that the functional capability matches the requirements from that **Golden Component**. For a full list of all the standard ODA Components, see the [ODA Component Directory](https://oda-directory.labs.tmforum.org/)

The `description`, `maintainers` and `owners` are self-descriptive.

```yaml
  coreFunction:
    exposedAPIs:
    - name: productcatalogmanagement
      specification: https://raw.githubusercontent.com/tmforum-apis/TMF620_ProductCatalog/master/TMF620-ProductCatalog-v4.0.0.swagger.json
      implementation: {{.Release.Name}}-prodcatapi
      apitype: openapi
      path: /{{.Release.Name}}-{{.Values.component.type}}/tmf-api/productCatalogManagement/v4
      developerUI: /{{.Release.Name}}-{{.Values.component.type}}/tmf-api/productCatalogManagement/v4/docs
      port: 8080
    dependantAPIs:
    - name: party 
      apitype: openapi     
      specification: https://open-api.tmforum.org/TMF632-Party-v4.0.0.swagger.json     
    publishedEvents: 
    - name: TMF620-productspecification
      specification: https://open-api.tmforum.org/TMF620-Productcatalog-v4.0.0.swagger.json
      hub: /{{.Release.Name}}-{{.Values.component.type}}/tmf-api/productspecification/hub
      implementation: {{.Release.Name}}-prodspecevent
      apitype: openapi
      port: 80
    subscribedEvents: 
    - name: TMF633-servicespecification
      specification: https://open-api.tmforum.org/TMF633-Servicecatalog-v4.0.0.swagger.json
      call-back: /{{.Release.Name}}-{{.Values.component.type}}/tmf-api/servicepecification/call-back
      port: 80
      implementation: {{.Release.Name}}-servicespecevent
      apitype: openapi
      filter: CatalogStateChangeEvent&status=active
```

The `coreFunction` describes the core purpose of the software component. It describes the list of APIs and/or events that the component exposes as well as the APIs and/or events that it is dependant on. The definitions within the `publishedEvents` and `subscribedEvents` are experimental at this point, and we will modify and enhance them as we build-out the ODA Canvas and assemble a representative set of ODA Components. The current definition has:
* an `implementation` which links to the Kubernetes service that implements the API or event, including the `port` where the http service is exposed. 
* The `path` shows the API resource end-point, and can be used, for example, to automatically configure any API Gateway that is included as part of the Canvas. Note that the `path` points to the root of the API (and you need to append the relevant path from the swagger document to get to an implemented API resource). 
* The `specification` points to the swagger documentation for the API. The Component CTK (Compliance Test Kit) will look inside this swagger for the `basePath` determine which Open-API CTK to execute for that API. The `basePath` is of format `"/tmf-api/productCatalogManagement/v4/"` which shows it is a `tmf-api` for `productCatalogManagement` with major version `4`. The swagger can be one of the TM Forum published swagger files (e.g. [https://raw.githubusercontent.com/tmforum-rand/Open_API_And_Data_Model/v4.0-Sprint-2020-03/apis/TMF620_Product_Catalog/swaggers/TMF620-ProductCatalog-v4.1.0.swagger.json](https://raw.githubusercontent.com/tmforum-rand/Open_API_And_Data_Model/v4.0-Sprint-2020-03/apis/TMF620_Product_Catalog/swaggers/TMF620-ProductCatalog-v4.1.0.swagger.json?token=ACS2FQP4M3AEZBKQQEMIUQ3ABGIBM)) or can be an extension (conforming to the TMF630 Design Guidelines).
* The `hub` represents the path for configuring the destination for the events.
* The `call-back` represents the path to the call-back end-point for these events.
* The `filter` allows the component to define a filter to only subscribe to certain type of events.



```yaml
  management:
    exposedAPIs:
    - name: metrics
      apitype: open-metrics
      implementation: {{.Release.Name}}-{{.Values.component.name}}-sm
      path: /{{.Release.Name}}-{{.Values.component.name}}/metrics
      port: 4000    
    dependantAPIs: []
    publishedEvents: 
    - name: TMF642-alarmmanagement
      specification: https://open-api.tmforum.org/TMF642-alarmmanagement-v4.0.0.swagger.json
      hub: /{{.Release.Name}}-{{.Values.component.type}}/tmf-api/alarmmanagement/hub
      implementation: {{.Release.Name}}-alarmmgtevent
      apitype: openapi
      port: 80
    subscribedEvents: []
  security:
    controllerRole: secConAdmin
    exposedAPIs:
    - name: partyrole
      specification: https://raw.githubusercontent.com/tmforum-apis/TMF669_PartyRole/master/TMF669-PartyRole-v4.0.0.swagger.json
      implementation: {{.Release.Name}}-partyroleapi
      apitype: openapi
      path: /{{.Release.Name}}-{{.Values.component.name}}/tmf-api/partyRoleManagement/v4
      developerUI: /{{.Release.Name}}-{{.Values.component.name}}/tmf-api/partyRoleManagement/v4/docs
      port: 8080 
    dependantAPIs: []
```

The `management` and `security` sections describe management and security APIs the component exposes that are part of its management or security (rather than part of its core business function). Examples of management APIs are for self-testing, raising operational alarms, or configuring the component itself. The `security` section provides meta-data on the security mechanisms used by the component, for example, exposing the roles the component requires to be configured in the Identity Management service. Again, the current definitions within these sections are experimental and we will modify and enhance them as we build-out the ODA Canvas and assemble a representative set of ODA Components. As of version `v1alpha2` and later, the security definition should include a `partyrole` property that describes the TMF669 PartyRole Open-API that all components must support (future versions may support multiple mechanisms for components to expose the roles they support). As of version `v1alpha3` and layer, the security definition must include a `controllerRole` property that gives the name of a pre-existing role in the component that the security controller can use to a) POST a listener URL to the component partyRole notification endpoint so that it can receive notifications of events against party roles and b) GET the partyrole endpoint to query roles in the component.

## Step 2: Add labels to all the standard Kubernetes resources

By default, when you deploy a kubernetes manifest containing a number of different standard resources (Deployments, Pods, Services etc), they are deployed as independant resources with no reference to each-other. As part of the component standard, we add a label to every kubernetes resource to show that it belongs to a particular component.

In our productcatalog example component, if you look at the service definition below, it shows us adding the component label to this service.

```yaml
apiVersion: v1
kind: Service
metadata:
  name: productcatalog
  labels:
    app: productcatalog
    oda.tmforum.org/componentName: vodafone-productcatalog
spec:
  ports:
  - port: 8080
    targetPort: productcatalog
    name: productcatalog
  type: NodePort
  selector:
    app: productcatalog
```

When this component is deployed, the [Component Operator](https://github.com/tmforum-rand/oda-component-definitions/tree/master/controllers/componentOperator) will use this information to build a parent relationship between the running instances of the component. This also means that when we delete a component using the `kubectl delete component <componentname>` comand, the Kubernetes garbage-collection will also delete all the standard resources within the component.

The component operator will create a parent->child relationship with any labelled resources (services, deployments, persistentvolumeclaims, jobs, cronjobs, statefulsets, configmap, secret, serviceaccount, role, rolebinding) to make them part of the component (and help with things like cascading deletion)

We should not support certain resources:

* ingress - A developer should express a components intent via APIs and not via ingress. The canvas should create any required ingress
* pod, replicaset - a developer should use deployments (for stateless microservices) or statefulsets (for stateful microservices)
* demonset - a component developer should have no need for creating a demonset
* clusterrole, clusterrolebinding - a component developer should have no need for creating a clusterrole, clusterrolebinding they should be using role, rolebinding

This if you include these unsupported resources in a component specification, the component CTK will issue a warning.
