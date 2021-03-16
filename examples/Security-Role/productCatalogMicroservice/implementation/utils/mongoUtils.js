'use strict';

const util = require('util')
const assert = require('assert');

const queryToMongo = require('query-to-mongo');
const querystring = require('querystring');

const MongoClient = require('mongodb').MongoClient;

const {getResponseType, getPayloadType, getTypeDefinition} = require('./swaggerUtils');

var mongodb = null; 

/* connection helper for running MongoDb from url */
function connectHelper(callback) {
  var releaseName = process.env.RELEASE_NAME; // Release name from Helm deployment

  var credentials_uri = "mongodb://" + releaseName + "-mongodb:27017/tmf";
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

/* connection helper for running MongoDb in CloudFoundary */
function connectHelperCF(callback) {

  // Now lets get cfenv and ask it to parse the environment variable
  var cfenv = require('cfenv');

  // load local VCAP configuration  and service credentials
  var vcapLocal;
  try {
      vcapLocal = require('./vcap-local.json');
      console.log("Loaded local VCAP");
  } catch (e) {
      // console.log(e)
  }

  const appEnvOpts = vcapLocal ? {
      vcap: vcapLocal
  } : {}

  const appEnv = cfenv.getAppEnv(appEnvOpts);

  // Within the application environment (appenv) there's a services object
  let services = appEnv.services;

  // The services object is a map named by service so we extract the one for MongoDB
  let mongodb_services = services["compose-for-mongodb"];

  // This check ensures there is a services for MongoDB databases
  assert(!util.isUndefined(mongodb_services), "App must be bound to compose-for-mongodb service");

  // We now take the first bound MongoDB service and extract it's credentials object
  let credentials = mongodb_services[0].credentials;

  // We always want to make a validated TLS/SSL connection
  let options = {
      ssl: true,
      sslValidate: true,
      useNewUrlParser: true
  };

  // If there is a certificate available, use that, otherwise assume Lets Encrypt certifications.
  if (credentials.hasOwnProperty("ca_certificate_base64")) {
      let ca = [new Buffer(credentials.ca_certificate_base64, 'base64')];
      options.sslCA = ca;
  }

  // This is the MongoDB connection. From the application environment, we got the
  // credentials and the credentials contain a URI for the database. Here, we
  // connect to that URI, and also pass a number of SSL settings to the
  // call. Among those SSL settings is the SSL CA, into which we pass the array
  // wrapped and now decoded ca_certificate_base64,
  MongoClient.connect(credentials.uri, options, function (err, db) {
      if (err) {
        mongodb = null;
        callback(err,null);
      } else {
        mongodb = db.db("tmf");
        callback(null,mongodb);
      }
    }
  );
};



function getMongoQuery(req) {
  var res;
  if(req instanceof Object) {
    res = queryToMongo(req._parsedUrl.query);
  } else {
    res = queryToMongo(querystring.parse(req));
  }

  if(res!==undefined && res.options!==undefined && res.options.fields!==undefined) {
    res.options.fields.href = true;
    res.options.fields.id = true;
  }

  //
  // test for date-time in query and allow partial equality matching, e.g. ="2018-08-21"
  //
  try {
    const requestType = getPayloadType(req);
    const properties = Object.keys(res.criteria);

    var typeDefinition = getTypeDefinition(requestType);
    if(typeDefinition.properties!==undefined) {
      typeDefinition = typeDefinition.properties;
    }

    properties.forEach(prop => {
      var paramDef = typeDefinition[prop];
      if(paramDef!==undefined && paramDef.type === "string" && paramDef.format === "date-time") {
        const propVal = res.criteria[prop];
        // equality test if not the value is an object
        if(!(propVal instanceof Object)) {
          if(!isNaN(Date.parse(propVal))) {
            res.criteria[prop] = {$regex: '^' + propVal + '.*' };
          }
        }
      }
    });
  }
  catch(err) {
    // ignore for now
  }

  res.options.projection = res.options.fields;
  delete res.options.fields;

  return(res);

};

function quotedString(s) {
  return s;
};

function connectDb(callback) {
  if(mongodb) {
      mongodb.stats(function(err, stats) {
        if(stats != null) {
          callback(null,mongodb);
        } else {
          connectHelper(callback);
        }
      });
  } else {
    connectHelper(callback);
  }
};

function connect() {
  return new Promise(function(resolve,reject) {
      connectDb(function(err,db) {
        if(err!=null || db==null) {
          reject(err);
        } else {
          resolve(db);
        };
      });
    });
};

function sendDoc(res,code,doc) {
  // delete internal mongo _id from all documents
  if(Array.isArray(doc)) {
    // remove _id from all documents
    doc.forEach(x => {
      delete x._id;
    });
  } else {
    delete doc._id;
  }

  if(doc.href) {
    res.setHeader('Location',  doc.href);
  }

  res.statusCode = code;
  res.setHeader('Content-Type', 'application/json');
  res.end(JSON.stringify(doc));
}


module.exports = { connect, connectDb, getMongoQuery, sendDoc };

