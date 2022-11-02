# Internal Kubernetes Testing Troubleshooting Guide

If you are having difficulty troublieshooting a particular capability, and the external access is not working (e.g. through an external API), then you can troubleshoot from within the cluster by deplopying a test pod within the cluster and opening a terminal session within that test pod (This shold only be used within development and test clusters).

For example, if an API is not working through the external URL, you can check whether the problem is within the API micro-service, or whether it is in the configuration of the API Gateway and/or Service Mesh by connecting to the API microservice from within another pod in the same cluster.

