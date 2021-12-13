'use strict';

//Minimal Service with filtering (equality match only) and attribute selection
//Error Handing Need to define a global error hqndler
//Paging and Range based Iterator to be added
//Notification to be added add listener and implement hub

const util = require('util');
const uuid = require('uuid');

const mongoUtils = require('../utils/mongoUtils');
const swaggerUtils = require('../utils/swaggerUtils');
const notificationUtils = require('../utils/notificationUtils');

const {sendDoc} = require('../utils/mongoUtils');

const {setBaseProperties, traverse, 
       addHref, processCommonAttributes } = require('../utils/operationsUtils');

const {validateRequest} = require('../utils/ruleUtils');

const {processAssignmentRules} = require('../utils/operations');

const {getPayloadType, getPayloadSchema, getResponseType} = require('../utils/swaggerUtils');

const {updateQueryServiceType, updatePayloadServiceType, cleanPayloadServiceType} = require('../utils/swaggerUtils');

const {TError, TErrorEnum, sendError} = require('../utils/errorUtils');

const MongoClient = require('mongodb').MongoClient;
const assert = require('assert');

exports.listenToPartyRoleAttributeValueChangeEvent = function(req, res, next) {
  /**
   * Client listener for entity PartyRoleAttributeValueChangeEvent
   * Example of a client listener for receiving the notification PartyRoleAttributeValueChangeEvent
   *
   * data PartyRoleAttributeValueChangeEvent The event data
   * returns EventSubscription
   **/

  console.log('listenToPartyRoleAttributeValueChangeEvent :: ' + req.method + ' ' + req.url + ' ' + req.headers.host);

  /* matching isRestfulCreate - argument notificationListenersClientSide */
  
  const resourceType = getResponseType(req);
  const requestSchema = getPayloadSchema(req);

  swaggerUtils.getPayload(req)
    .then(payload => validateRequest(req, 'listenToPartyRoleAttributeValueChangeEvent', payload))
    .then(payload => traverse(req, requestSchema, payload,[],getPayloadType(req)))
    .then(payload => processCommonAttributes(req, resourceType, payload))
    .then(payload => processAssignmentRules('listenToPartyRoleAttributeValueChangeEvent', payload))
    .then(payload => {

      const internalError =  new TError(TErrorEnum.INTERNAL_SERVER_ERROR, "Internal database error");

      payload = swaggerUtils.updatePayloadServiceType(payload, req,'');

      mongoUtils.connect().then(db => {
        db.collection(resourceType)
          .insertOne(payload)
          .then(() => {

            payload = cleanPayloadServiceType(payload);

            sendDoc(res, 201, payload);
            notificationUtils.publish(req,payload);
          })
          .catch((error) => {
            console.log("listenToPartyRoleAttributeValueChangeEvent: error=" + error);
            sendError(res, internalError);
          })
      })
      .catch((error) => {
        console.log("listenToPartyRoleAttributeValueChangeEvent: error=" + error);
        sendError(res, internalError);
      })
    })
    .catch( error => {
      console.log("listenToPartyRoleAttributeValueChangeEvent: error=" + error.toString());
      sendError(res, error);
    });



};

exports.listenToPartyRoleCreateEvent = function(req, res, next) {
  /**
   * Client listener for entity PartyRoleCreateEvent
   * Example of a client listener for receiving the notification PartyRoleCreateEvent
   *
   * data PartyRoleCreateEvent The event data
   * returns EventSubscription
   **/

  console.log('listenToPartyRoleCreateEvent :: ' + req.method + ' ' + req.url + ' ' + req.headers.host);

  /* matching isRestfulCreate - argument notificationListenersClientSide */
  
  const resourceType = getResponseType(req);
  const requestSchema = getPayloadSchema(req);

  swaggerUtils.getPayload(req)
    .then(payload => validateRequest(req, 'listenToPartyRoleCreateEvent', payload))
    .then(payload => traverse(req, requestSchema, payload,[],getPayloadType(req)))
    .then(payload => processCommonAttributes(req, resourceType, payload))
    .then(payload => processAssignmentRules('listenToPartyRoleCreateEvent', payload))
    .then(payload => {

      const internalError =  new TError(TErrorEnum.INTERNAL_SERVER_ERROR, "Internal database error");

      payload = swaggerUtils.updatePayloadServiceType(payload, req,'');

      mongoUtils.connect().then(db => {
        db.collection(resourceType)
          .insertOne(payload)
          .then(() => {

            payload = cleanPayloadServiceType(payload);

            sendDoc(res, 201, payload);
            notificationUtils.publish(req,payload);
          })
          .catch((error) => {
            console.log("listenToPartyRoleCreateEvent: error=" + error);
            sendError(res, internalError);
          })
      })
      .catch((error) => {
        console.log("listenToPartyRoleCreateEvent: error=" + error);
        sendError(res, internalError);
      })
    })
    .catch( error => {
      console.log("listenToPartyRoleCreateEvent: error=" + error.toString());
      sendError(res, error);
    });



};

exports.listenToPartyRoleDeleteEvent = function(req, res, next) {
  /**
   * Client listener for entity PartyRoleDeleteEvent
   * Example of a client listener for receiving the notification PartyRoleDeleteEvent
   *
   * data PartyRoleDeleteEvent The event data
   * returns EventSubscription
   **/

  console.log('listenToPartyRoleDeleteEvent :: ' + req.method + ' ' + req.url + ' ' + req.headers.host);

  /* matching isRestfulCreate - argument notificationListenersClientSide */
  
  const resourceType = getResponseType(req);
  const requestSchema = getPayloadSchema(req);

  swaggerUtils.getPayload(req)
    .then(payload => validateRequest(req, 'listenToPartyRoleDeleteEvent', payload))
    .then(payload => traverse(req, requestSchema, payload,[],getPayloadType(req)))
    .then(payload => processCommonAttributes(req, resourceType, payload))
    .then(payload => processAssignmentRules('listenToPartyRoleDeleteEvent', payload))
    .then(payload => {

      const internalError =  new TError(TErrorEnum.INTERNAL_SERVER_ERROR, "Internal database error");

      payload = swaggerUtils.updatePayloadServiceType(payload, req,'');

      mongoUtils.connect().then(db => {
        db.collection(resourceType)
          .insertOne(payload)
          .then(() => {

            payload = cleanPayloadServiceType(payload);

            sendDoc(res, 201, payload);
            notificationUtils.publish(req,payload);
          })
          .catch((error) => {
            console.log("listenToPartyRoleDeleteEvent: error=" + error);
            sendError(res, internalError);
          })
      })
      .catch((error) => {
        console.log("listenToPartyRoleDeleteEvent: error=" + error);
        sendError(res, internalError);
      })
    })
    .catch( error => {
      console.log("listenToPartyRoleDeleteEvent: error=" + error.toString());
      sendError(res, error);
    });



};

exports.listenToPartyRoleStateChangeEvent = function(req, res, next) {
  /**
   * Client listener for entity PartyRoleStateChangeEvent
   * Example of a client listener for receiving the notification PartyRoleStateChangeEvent
   *
   * data PartyRoleStateChangeEvent The event data
   * returns EventSubscription
   **/

  console.log('listenToPartyRoleStateChangeEvent :: ' + req.method + ' ' + req.url + ' ' + req.headers.host);

  /* matching isRestfulCreate - argument notificationListenersClientSide */
  
  const resourceType = getResponseType(req);
  const requestSchema = getPayloadSchema(req);

  swaggerUtils.getPayload(req)
    .then(payload => validateRequest(req, 'listenToPartyRoleStateChangeEvent', payload))
    .then(payload => traverse(req, requestSchema, payload,[],getPayloadType(req)))
    .then(payload => processCommonAttributes(req, resourceType, payload))
    .then(payload => processAssignmentRules('listenToPartyRoleStateChangeEvent', payload))
    .then(payload => {

      const internalError =  new TError(TErrorEnum.INTERNAL_SERVER_ERROR, "Internal database error");

      payload = swaggerUtils.updatePayloadServiceType(payload, req,'');

      mongoUtils.connect().then(db => {
        db.collection(resourceType)
          .insertOne(payload)
          .then(() => {

            payload = cleanPayloadServiceType(payload);

            sendDoc(res, 201, payload);
            notificationUtils.publish(req,payload);
          })
          .catch((error) => {
            console.log("listenToPartyRoleStateChangeEvent: error=" + error);
            sendError(res, internalError);
          })
      })
      .catch((error) => {
        console.log("listenToPartyRoleStateChangeEvent: error=" + error);
        sendError(res, internalError);
      })
    })
    .catch( error => {
      console.log("listenToPartyRoleStateChangeEvent: error=" + error.toString());
      sendError(res, error);
    });



};



