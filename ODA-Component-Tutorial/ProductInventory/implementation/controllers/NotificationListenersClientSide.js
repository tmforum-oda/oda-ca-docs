'use strict';

var url = require('url');

var NotificationListenersClientSide = require('../service/NotificationListenersClientSideService');

module.exports.listenToProductAttributeValueChangeEvent = function listenToProductAttributeValueChangeEvent (req, res, next) {
  NotificationListenersClientSide.listenToProductAttributeValueChangeEvent(req, res, next);
};

module.exports.listenToProductBatchEvent = function listenToProductBatchEvent (req, res, next) {
  NotificationListenersClientSide.listenToProductBatchEvent(req, res, next);
};

module.exports.listenToProductCreateEvent = function listenToProductCreateEvent (req, res, next) {
  NotificationListenersClientSide.listenToProductCreateEvent(req, res, next);
};

module.exports.listenToProductDeleteEvent = function listenToProductDeleteEvent (req, res, next) {
  NotificationListenersClientSide.listenToProductDeleteEvent(req, res, next);
};

module.exports.listenToProductStateChangeEvent = function listenToProductStateChangeEvent (req, res, next) {
  NotificationListenersClientSide.listenToProductStateChangeEvent(req, res, next);
};
