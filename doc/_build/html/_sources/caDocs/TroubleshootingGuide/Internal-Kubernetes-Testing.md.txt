# Internal Kubernetes Testing Troubleshooting Guide

If you are having difficulty troublieshooting a particular capability, and the external access is not working (e.g. through an external API), then you can troubleshoot from within the cluster by deplopying a test pod within the cluster and opening a terminal session within that test pod (This shold only be used within development and test clusters).

For example, if an API is not working through the external URL, you can check whether the problem is within the API micro-service, or whether it is in the configuration of the API Gateway and/or Service Mesh by connecting to the API microservice from within another pod in the same cluster.

Assuming you have kubectl configured atainst a running kubernetes cluster, the following command will create a container with an interactive shell in that cluster:

```
kubectl run -i --tty -rm debug --image=lightruncom/koolkits:node --restart=Never
```

Once you have the interactive shell, you can exercise the containers and services internally without being dependent on the API Gateway / Service Mesh / Ingress / Load Balancers etc. For example, you could use curl to directly call one of the apis:

```
 curl http://r8-prodcatapi.components.svc.cluster.local:8080/r8-productcatalog/tmf-api/productCatalogManagement/v4/category
```

Future enhancement
------------------

The `lightruncom/koolkits:node` image is part of the [KoolKits](https://github.com/lightrun-platform/koolkits/blob/main/nodejs/README.md) project to provide debugging tools for Kubernetes. A natural next-step would be to create a specific debug image for the ODA Canvas. This idea is captured here: https://github.com/tmforum-oda/oda-ca/issues/73