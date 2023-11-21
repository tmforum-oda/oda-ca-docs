const opentelemetry = require('@opentelemetry/sdk-node');
const {getNodeAutoInstrumentations} = require('@opentelemetry/auto-instrumentations-node')
const {ConsoleSpanExporter} = require("@opentelemetry/sdk-trace-node")
const {OTLPTraceExporter} = require("@opentelemetry/exporter-trace-otlp-proto")

function generateExporter() {
    if (process.env.OTL_EXPORTER_TRACE_PROTO_ENABLED === 'true') {
        return new OTLPTraceExporter({
            url: process.env.OTL_EXPORTER_TRACE_PROTO_COLLECTOR_URL,
        })
    }
    return new ConsoleSpanExporter()
}

const sdk = new opentelemetry.NodeSDK({
    traceExporter: generateExporter(),
    resource: new opentelemetry.resources.Resource({
        "service.name": process.env.COMPONENT_NAME
    }),
    instrumentations: [getNodeAutoInstrumentations()]
});

sdk.start();

module.exports = {
    sdk,
    init() {}
}
