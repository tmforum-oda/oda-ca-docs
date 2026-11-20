# A component is stuck at In-Progress

When you install a component, the Component Management operator reports its progress in the component's `deployment_status`. Each status names the step it is waiting for:

| Status | Waiting for |
|---|---|
| `In-Progress-CompCon` | Every exposed API to have a URL and a ready implementation |
| `In-Progress-IDConfOp` | The identity configuration operator to set up the component's roles in Keycloak |
| `In-Progress-SecretMan` | Secrets management (HashiCorp Vault), if the component asks for it |
| `In-Progress-DepApi` | Every dependent API to be resolved |
| `Complete` | Nothing: the component is ready |

A healthy component passes through these in one to two minutes. If it stays at one, look at that step.

Watch the status with:

```bash
kubectl get components -n components -w
```

## Stuck at In-Progress-CompCon

List the component's exposed APIs, with their URLs and whether they are ready:

```bash
kubectl get exposedapis -n components \
  -o custom-columns=NAME:.metadata.name,URL:.status.apiStatus.url,READY:.status.implementation.ready
```

### The URLs are empty

The API operator builds each URL from the address of the API gateway. With Istio, that is the external address of the `istio-ingress` service:

```bash
kubectl get svc istio-ingress -n istio-ingress
```

If `EXTERNAL-IP` is `<pending>`, the cluster has no load balancer to give the gateway an address. This is the usual case on kind, and on other local clusters without a load balancer.

To fix it:

1. Give the gateway an address. On a local cluster, either run [cloud-provider-kind](https://github.com/kubernetes-sigs/cloud-provider-kind), or set the address yourself and forward the gateway ports to it:

   ```bash
   kubectl patch svc istio-ingress -n istio-ingress --subresource=status --type=merge \
     -p '{"status":{"loadBalancer":{"ingress":[{"ip":"127.0.0.1"}]}}}'
   kubectl port-forward -n istio-ingress svc/istio-ingress 80:80 443:443
   ```

2. **Reinstall the component.** The API operator reads the gateway address only when it creates the APIs, so components installed before the address arrived keep their empty URLs ([oda-canvas#621](https://github.com/tmforum-oda/oda-canvas/issues/621)):

   ```bash
   helm uninstall <release> -n components
   helm install <release> <chart> -n components
   ```

Alternatively, set a fixed public hostname for the API operator, so that it doesn't depend on the gateway's address: `--set api-operator-istio.configmap.publicHostname=<hostname>` when you install the Canvas.

[Run a local demo Canvas](../../canvas/installation/local-demo-canvas.md) sets up a kind cluster in the right order, so this doesn't happen.

### The URLs are set, but the APIs aren't ready

The implementation isn't answering its health check. Check that the component's pods are running, and that each API answers a `GET` at its root with HTTP 200. See [Microservice logging](Microservice-Logging.md) and [Exploring custom resources](Exploring-Custom-Resources.md).

## Stuck at a later status

Check the logs of the operator for that step (see [Controller logging](Controller-Logging.md)):

| Status | Operator deployment in the `canvas` namespace |
|---|---|
| `In-Progress-IDConfOp` | `identityconfig-operator-keycloak` |
| `In-Progress-SecretMan` | `canvas-smanop`. If HashiCorp Vault isn't installed, this operator fails with `CreateContainerConfigError`. |
| `In-Progress-DepApi` | `canvas-depapi-op` |

For example:

```bash
kubectl logs -n canvas deployment/identityconfig-operator-keycloak --all-containers --tail=100
```

The component operator itself (`component-operator`) logs a summary each time a component's status changes, with the number of ready APIs (for example `completed API count4/4`) and the new deployment status.
