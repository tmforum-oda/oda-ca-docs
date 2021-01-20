## Tutorial to build ODA-Component from Open-API Reference Implemenation

This tutorial shows the complete process to package, test and deploy an ODA-Component, using the nodejs reference implementation of the TMF620 Product Catalog Management API as the source code. You should be able to follow the process below using an existing software application as source (the process should work for simple applications - it is intended as a tutorial to get you started; For more complex applications you may have to decompose to multiple containers/micro-services and even multiple ODA-Components).


### 1. Download Reference Implementation

Download Reference Implementation from `https://projects.tmforum.org/wiki/display/API/Open+API+Table`.


### 2. (optionally) test locally with local MongoDb.

In the `utils/mongoUtils.js` file, you will need to replace the connectHelper with a helper function that uses local connection string:

```
/* connection helper for running MongoDb locally */
function connectHelper(callback) {

  var credentials_uri = "mongodb://localhost:27017/tmf";
  let options = {
    useNewUrlParser: true 
  };
  MongoClient.connect(credentials_uri, options, function (err, db) {
    if (err) {
      mongodb = null;
      callback(err,null);
    } else {
      mongodb = db.db("tmf");
      callback(null,mongodb);
    }
  });
}
```


## 3. Configure mongoDb connection url to use within Kubernetes.

In the `utils/mongoUtils.js` file, you will need to update the local connection string to a url that wil work within Kubernetes (this will match the kubernetes service name for mongoDb)

```
/* connection helper for running MongoDb locally */
function connectHelper(callback) {

  var credentials_uri = "mongodb://mongodb:27017/tmf";
  let options = {
    useNewUrlParser: true 
  };
  MongoClient.connect(credentials_uri, options, function (err, db) {
    if (err) {
      mongodb = null;
      callback(err,null);
    } else {
      mongodb = db.db("tmf");
      callback(null,mongodb);
    }
  });
}
```


## 4. Package the nodejs implementation into a docker image

Create a dockerfile with the instructions to build our image. We are starting with the official [node](https://hub.docker.com/_/node) docker image.

```
FROM node
```

This image comes with Node.js and NPM already installed so the next thing we need to do is to install the app dependencies.

```
COPY implementation/package*.json .
RUN npm install
```

Then we copy the source code.

```
COPY implementation ./
```

The app binds to port 8080 so we'll use the EXPOSE instruction to have it mapped by the docker daemon:

```
EXPOSE 8080
```

Finally we define the command that will run the app.

```
CMD ["node", "index.js"]
```


The complete dockerfile should look like:

```
FROM node
COPY implementation/package*.json .
RUN npm install
COPY implementation ./
EXPOSE 8080
CMD ["node", "index.js"]
```

Before we build this dockerfile, we create a `.dockerignore` file (so the at node_modules packages are not copied into the docker image - this would make the image very large. The `RUN npm install` command inside the dockerfile will install these on demand).

```
node_modules
npm-debug.log
```

To build the docker image, we use the command:

```
docker build -t lesterthomas/productcatalog:0.1 -t lesterthomas/productcatalog:latest -f dockerfile .
```

Note: we use the -t to tag the image. We give the image two tags, one with a version number and the other with a `latest` tag that will overwrite any previously uploaded images.

Finally we upload the docker image to a Docker repository. I'm using the default [DockerHub](https://hub.docker.com) with an account `lesterthomas` that I have created previously. If this is the first time accessing the docker repository you will need to login first with the `docker login` command.

```
docker push lesterthomas/productcatalog --all-tags
```


## 5. Create Component Envelope 

The Component Envelope contains the meta-data required to automatically deploy and manage the component in an ODA-Canvas environment. The Component Envelope is a `.yaml` document extending the  Kubernetes Manifest standard. There is a detailed breakdown of the Component Envelope in [ODAComponentDesignGuidelines](https://github.com/tmforum-oda/oda-ca-docs/blob/master/ODAComponentDesignGuidelines.md).

We will first create the standard Kubernetes resources. We use a `Deployment` resource to deploy the docker image we created in step 4. Create a new file `productcatalog.component.yaml`.


```
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: productcatalog
  labels:
    oda.tmforum.org/componentName: productcatalog
spec:
  replicas: 1
  selector:
    matchLabels:
      app: productcatalog
  template:
    metadata:
      labels:
        app: productcatalog
    spec:
      containers:
      - name: productcatalog
        image: lesterthomas/productcatalog:latest
        ports:
        - name: productcatalog
          containerPort: 8080
```

We also need to deploy a mongoDb. We will use the standard mongoDb image from dockerhub.

```
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mongodb
  labels:
    oda.tmforum.org/componentName: productcatalog
    app: mongodb
spec:
  replicas: 1
  selector:
    matchLabels:
      app: mongodb
  template:
    metadata:
      labels:
        app: mongodb
    spec:
      containers:
      - name: mongodb
        image: mongo:latest
        ports:
        - name: mongodb
          containerPort: 27017
        volumeMounts:
        - name: mongodb-pv-storage
          mountPath: "/data/db"
      volumes:
      - name: mongodb-pv-storage
        persistentVolumeClaim:
          claimName: mongodb-pv-claim
```

The mongoDb requires a persistentVolume and so we create a persistentVolumeClaim resource.

```
---
kind: PersistentVolumeClaim
apiVersion: v1
metadata:
  name: mongodb-pv-claim
  labels:
    oda.tmforum.org/componentName: productcatalog
spec:
  accessModes:
  - ReadWriteOnce
  resources:
    requests:
      storage: 5Gi   
```


We need to make the mongoDb available to the nodejs productcatalog image, so we create a Kubernetes Service resource. Note the service matches the connection url we created in step 3.

```
---
apiVersion: v1
kind: Service
metadata:
  name: mongodb
  labels:
    oda.tmforum.org/componentName: productcatalog
    app: mongodb
spec:
  ports:
  - port: 27017
    targetPort: mongodb
    name: mongodb
  type: NodePort
  selector:
    app: mongodb
```

We need to expose the API using a Service as well.

```
---
apiVersion: v1
kind: Service
metadata:
  name: productcatalog
  labels:
    app: productcatalog
    oda.tmforum.org/componentName: productcatalog
spec:
  ports:
  - port: 8080
    targetPort: productcatalog
    name: productcatalog
  type: NodePort
  selector:
    app: productcatalog
```


We have created all the Kubernetes resources to deploy the mongoDb and productcatalog nodejs containers and expose as services. The final step is to add the ODA-Component meta-data. This meta-data will be used at run-time to configure the canvas services.


```
---
apiVersion: oda.tmforum.org/v1alpha1
kind: component
metadata:
  name: productcatalog
  labels:
    oda.tmforum.org/componentName: productcatalog
spec:
  type: ocs
  selector:
    matchLabels:
     oda.tmforum.org/componentName: productcatalog
  componentKinds:
    - group: core
      kind: Service
    - group: apps
      kind: Deployment  
  version: "0.0.1"
  description: "Simple Product Catalog ODA-Component from Open-API reference implementation." 
  maintainers:
    - name: Lester Thomas
      email: lester.thomas@vodafone.com
  owners:
    - name: Lester Thomas
      email: lester.thomas@vodafone.com     
  coreFunction:
    exposedAPIs: 
    - name: productcatalogmanagement
      specification: https://raw.githubusercontent.com/tmforum-apis/TMF620_ProductCatalog/master/TMF620-ProductCatalog-v4.0.0.swagger.json
      implementation: productcatalog
      path: /tmf-api/productCatalogManagement/v4
      developerUI: /docs
      port: 8080
    dependantAPIs: []
  eventNotification:
    publishedEvents: []
    subscribedEvents: []
  management: []
  security:
    securitySchemes: []
```


## 6. Test component envelope using component CTK


Download the ODA-Component CTK from [https://github.com/tmforum-oda/oda-component-ctk/](https://github.com/tmforum-oda/oda-component-ctk/).


Within the opa-component-ctk folder, install the ctk.

```
npm install
```

Then run the ctk against the component envelope.

```
npm start ../oda-component-tutorial/productcatalog.yaml
```

You shold get an output like the image below. If you receive any errors, fix the issue in the component envelope yaml file and try again.

![CTK image](./images/ctksuccess.png)


## 7. Deploy the component envelope into Open Digital Lab canvas










## ISSUES & resolution

1. Kubernetes ingress expect a 200 response at the root of the API. Without this, they do not create an ingress and instead return a 503 error. I've created an additional 'catch-all' middleware hook in the index.js. This returns a helpful link to the swagger docs for the API.
```
  // for all other requests, show links
  app.use(function (req, res) {
    res.end('<!DOCTYPE html><html><body><p>The API docs are at <a href="/tmf-api/productCatalogManagement/v4/docs">/tmf-api/productCatalogManagement/v4/docs</a></p></body></html>');  
  })  
```
2. Due to a node version issue (i think!) the generated API RI does not work on the latest v15 of node. I have created container based on node v10. The issue is with the fs.copyFileSync function: The v15 expects the third parameter to be an optional `mode` whilst the current implementation has a call-back error function.
3. api-docs are exposed at /api-docs which means that you cant host multiple apis on the same server. I've moved to host them at tmf-api/productCatalogManagement/v4/api-docs instead (and the swagger-ui at api/productCatalogManagement/v4/docs).
