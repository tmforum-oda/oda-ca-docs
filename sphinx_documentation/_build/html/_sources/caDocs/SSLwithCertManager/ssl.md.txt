# SSL certificates on the Open Digital Lab with Certbot


### Getting a new certificate on an environment that has the issuer already running:
Assuming an App called "kuard", a namespace called "app" and a domain "yourdomain.com":
<script src="https://gist.github.com/hrodrigues-tmforum/8374cff60d8936af973395475b4e31a7.js"></script>

you can fork the above gist, change the information to match yours and apply it with:
``` kubectl apply -f <url-of-your.yaml>```

Check the certificate:

``` kubectl get certificate -n app ```

If you have a similar output:

```
NAME                     READY   SECRET                   AGE
quickstart-example-tls   True    quickstart-example-tls   2m 
```

The process worked, now you need to change:

```     cert-manager.io/issuer: "letsencrypt-staging" ```

On line 7 of the yaml to:

```     cert-manager.io/issuer: "letsencrypt-production" ```






### Quick guide based on:

https://cert-manager.io/docs/tutorials/acme/ingress/

https://jmrobles.medium.com/free-ssl-certificate-for-your-kubernetes-cluster-with-rancher-2cf6559adeba