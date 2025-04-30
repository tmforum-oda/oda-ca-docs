# 5. Which languages and libraries should be used for Software Operators

Date: 2023-05-24

## Status

In progress

## Context

The canvas uses The ODA Canvas provides access to a range of common services (for identity management, authentication, observability etc) and has a set of
[Software Operators](operators/README.md) that automatically configure these services based on requirements defined in each ODA Component YAML specification. The operators are packaged as docker images and are deployed as part of the canvas.

Operators can be created in many different programming languages and frameworks: These are the ones linked from the [Extending Kubernetes with Operators](https://kubernetes.io/docs/concepts/extend-kubernetes/operator/) site:

* Charmed Operator Framework https://juju.is/
* Java Operator SDK https://github.com/java-operator-sdk/java-operator-sdk
* Kopf (Kubernetes Operator Pythonic Framework) https://github.com/nolar/kopf
* kube-rs (Rust) https://kube.rs/
* kubebuilder https://book.kubebuilder.io/
* KubeOps (.NET operator SDK) https://buehler.github.io/dotnet-operator-sdk/
* KUDO (Kubernetes Universal Declarative Operator) https://kudo.dev/
* Mast https://docs.ansi.services/mast/user_guide/operator/
* Metacontroller along with WebHooks that you implement yourself https://metacontroller.github.io/metacontroller/intro.html
* Operator Framework https://operatorframework.io/
* shell-operator https://github.com/flant/shell-operator

There are many other frameworks - for example, one team is creating Java operators using Quarkus https://github.com/quarkiverse/quarkus-operator-sdk

## Decision

We actively encourage a polyglot approach using the best framework and language for each task. As a reference implementation we want to demonstrate that the operators are themselves modular independently deployable
pieces of software. We also want so show good practice in using some of the operator frameworks

We have written Kubernetes Operators in python (using KOPF framework). We have teams building operators in Java using Quarkus/Fabric8. If teams want to use alternative frameworks for the Operators
then that is welcome.

## Consequences

The Canvas implementation source https://github.com/tmforum-oda/oda-canvas/tree/master/source will contain software operators using many different frameworks and languages.

