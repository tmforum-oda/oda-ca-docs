# Example Product Catalog component (including Security_role)

This is an example component that implements a TM Forum Product Catalog interface.

As part of the component implementation, it exposes role information to the Canvas using the TM Forum PartyRole API.

The component implements 4 micro-services:

* productCatalogMicroservice that implements the TMF620 Product Catalog Management API (based on the NodeJs reference implementation). This is depoyed as a Kubernetes Deployment.
* partyRoleMicroservice that implements the TMF669 Party Role Management API (based on the NodeJs reference implementation). This is depoyed as a Kubernetes Deployment.
* roleInitializationMicroservice that bootstraps the initial Party Role interface. This is depoyed as a Kubernetes Job that runs once when the component is initialised.
* a standard deployment of a mongoDb. This is deployed as a Kubernetes Deployment with a PersistentVolumeClaim.

The component envelope exposes the ProductCatalog as a CoreFunction API and the PartyRole as a Security/PartyRole API.

The Kubernetes services adopt the Istio naming convention for the Port names.

## Installation

Install this component (assuming the kubectl config is connected to a Kubernetes cluster with an operational ODA Canvas) using:
```
helm install r1 .\productcatalog -n components
```

You can test the component has deployed successfully using
```
kubectl get components -n components
```

You should get an output like 
```
NAME                          DEPLOYMENT_STATUS
r1-productcatalogmanagement   Complete
```

(The DEPLOYMENT_STATUS will cycle through a number of interim states as part of the deployment). 
If the deployment fails, refer to the [Troubleshooting-Guide](https://github.com/tmforum-oda/oda-ca-docs/tree/master/Troubleshooting-Guide).

 
## Configuration
You can configure the following aspects of the component:
- OpenTelemetry tracing and metrics
  - Any OTL endpoint with HTTP traces will do. By default, the component is configured to send traces to the Datadog agent.
- MongoDB Database connection

You can do that  by changing the values in the values.yaml file, or by setting the values on the command line when you install the component using the --set parameter.

relevant variables:

| Variable Name    	                           | Default                          	                               | Explanation                                                                                	                                                                                                  |
|----------------------------------------------|------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `mongodb.port`     	                         | 27017                            	                               | the port to connect to the mongodb instance the Host will be derived from the Release name 	                                                                                                  |
| `mongodb.database` 	                           | tmf                              	                               | the database name to connect to the mongodb instance                                       	                                                                                                  |
| `api.image`        	                           | csotiriou/productcatalogapi:0.10 	                               | The image for the implementation of the main api microservice                              	                                                                                                  |
| `api.otlp.console.enabled`        	            | false 	                                                          | Whether OpenTelemetry traces will be recorded in the console instead of being sent to the collector                              	                                                            |
| `api.otlp.protobuffCollector.enabled`        	 | true 	                                                           | Whether OpenTelemetry traces will be recorded in the OTL Collector instead of the console. Does not work if `api.otlp.console.enabled` is `true`                                              |
| `api.otlp.protobuffCollector.url`        	     | http://datadog-agent.default.svc.cluster.local:4318/v1/traces 	  | The host of the OTL Collector. Only used if `api.otlp.protobuffCollector.enabled` is `true`. By default it's set to the url of the collector. However, any OTL collector endpoint will suffice |
| `partyrole.image`        	                     | The image for the implementation of the partyrole microservice 	 | |

Note that in the above configuration, MongoDB configuration is shared among the partyrole and the main microservice. The host of the MongoDB database is set automatically, since it depends on the release name (it's being installed along the rest of the microservices inside the cluster).
