const express = require('express');
const server = express();
server.use(express.json());      
var componentName = process.env.COMPONENT_NAME; 
if (!componentName) {
  componentName = 'productcatalog'
}
console.log('ComponentName:'+componentName);

const client = require('prom-client');
const counter = new client.Counter({
  name: 'product_catalog_api_counter',
  help: 'Count of Notification Events from TMF 620 Product Catalog Management API',
  labelNames: ['NotificationEvent']
});

/*'ProductOfferingPriceAttributeValueChangeNotification',
          'ProductOfferingPriceCreationNotification',
        'ProductSpecificationCreationNotification',
        'ProductOfferingCreationNotification'
*/

/*
const histogram = new client.Histogram({
    name: 'test_histogram',
    help: 'Example of a histogram'
  });
  
const gauge = new client.Gauge({
    name: 'test_gauge',
    help: 'Example of a gauge'
});

const counter = new client.Counter({
    name: 'test_counter',
    help: 'Example of a counter'
});

gauge.set(15);
var index=0
setInterval(function(){
    console.log(index)
    index++
    gauge.set(index);
    histogram.observe(index/10);
    counter.inc(index)
}, 10000)
*/


server.get('/' + componentName + '/', async function(req, res) {
    res.send('Microservice to report prometheus metrics from ODA Components. Go to /metrics to see the metrics')
  })
server.get('/' + componentName + '/metrics', async function(req, res) {
    const metrics = await client.register.metrics()
    res.send(metrics)
  })
server.post('/listener', async function(req, res) {
    console.log(req.body)
    const eventType = req.body.eventType
    counter.inc({ NotificationEvent: eventType })

    res.send({success:true})
  })
const port = process.env.PORT || 4000;
console.log(
	`Server listening to ${port}, metrics exposed on /` + componentName + `/metrics endpoint. Listens for Open-API events on /listener (POST) endpoint.`,
);
server.listen(port);
