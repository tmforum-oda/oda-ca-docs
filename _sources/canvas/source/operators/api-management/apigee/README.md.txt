# Kubernetes Apigee API Lifecycle Management Operator

## Overview

The Kubernetes Apigee API Lifecycle Management Operator is a component of the ODA Canvas, optimized for environments that utilize the Apigee API Gateway. It is built using the Kopf Kubernetes operator framework to effectively manage API custom resources. This operator ensures seamless integration with the Apigee API Gateway, facilitating the creation, management, and exposure of APIs through HTTPRoute configurations.

This operator also uses Istio to control traffic between components and from the components to the API Gateway. This is a zero-trust model where internal traffic within the Canvas is controlled and communication is only allowed if it is explicitly declared in the Component specification.