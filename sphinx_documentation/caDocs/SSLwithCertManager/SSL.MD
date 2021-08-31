# SSL certificates on Kubernetes with Certbot


### Getting a new certificate on an environment that has the issuer already running:
Assuming an App called "kuard", a namespace called "app" and a domain "yourdomain.com":
<script src="https://gist.github.com/hrodrigues-tmforum/8374cff60d8936af973395475b4e31a7.js"></script>

you can fork the above gist, change the information to match yours and apply it with:
``` kubectl apply -f <url-of-your.yaml>```


### Quick guide based on:
https://cert-manager.io/docs/tutorials/acme/ingress/
https://jmrobles.medium.com/free-ssl-certificate-for-your-kubernetes-cluster-with-rancher-2cf6559adeba