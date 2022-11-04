# Microservice Logging Troubleshooting Guide

For any microservice running in Kubernetes, it is possible to view the logs for that microservice through any kubernetes tool. The example below will show the `kubectl` command-line tool.

Get the running pods in the `components` namespace using:

```
kubectl get pods -n component
```

![Get-Pods](images/Get-Pods.png)

Then get the log for particular pod using: 

```
kubectl logs r2-productinventoryapi-6fd8849bd-hdxw4
```

![Get-Logs](images/Get-Logs.png)

