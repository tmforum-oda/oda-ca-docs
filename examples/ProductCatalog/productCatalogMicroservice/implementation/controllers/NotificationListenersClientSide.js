'use strict';

var url = require('url');

var NotificationListenersClientSide = require('../service/NotificationListenersClientSideService');

module.exports.listenToCatalogBatchEvent = function listenToCatalogBatchEvent (req, res, next) {
  NotificationListenersClientSide.listenToCatalogBatchEvent(req, res, next);
};

module.exports.listenToCatalogCreateEvent = function listenToCatalogCreateEvent (req, res, next) {
  NotificationListenersClientSide.listenToCatalogCreateEvent(req, res, next);
};

module.exports.listenToCatalogDeleteEvent = function listenToCatalogDeleteEvent (req, res, next) {
  NotificationListenersClientSide.listenToCatalogDeleteEvent(req, res, next);
};

module.exports.listenToCategoryCreateEvent = function listenToCategoryCreateEvent (req, res, next) {
  NotificationListenersClientSide.listenToCategoryCreateEvent(req, res, next);
};

module.exports.listenToCategoryDeleteEvent = function listenToCategoryDeleteEvent (req, res, next) {
  NotificationListenersClientSide.listenToCategoryDeleteEvent(req, res, next);
};

module.exports.listenToProductOfferingAttributeValueChangeEvent = function listenToProductOfferingAttributeValueChangeEvent (req, res, next) {
  NotificationListenersClientSide.listenToProductOfferingAttributeValueChangeEvent(req, res, next);
};

module.exports.listenToProductOfferingCreateEvent = function listenToProductOfferingCreateEvent (req, res, next) {
  NotificationListenersClientSide.listenToProductOfferingCreateEvent(req, res, next);
};

module.exports.listenToProductOfferingDeleteEvent = function listenToProductOfferingDeleteEvent (req, res, next) {
  NotificationListenersClientSide.listenToProductOfferingDeleteEvent(req, res, next);
};

module.exports.listenToProductOfferingPriceAttributeValueChangeEvent = function listenToProductOfferingPriceAttributeValueChangeEvent (req, res, next) {
  NotificationListenersClientSide.listenToProductOfferingPriceAttributeValueChangeEvent(req, res, next);
};

module.exports.listenToProductOfferingPriceCreateEvent = function listenToProductOfferingPriceCreateEvent (req, res, next) {
  NotificationListenersClientSide.listenToProductOfferingPriceCreateEvent(req, res, next);
};

module.exports.listenToProductOfferingPriceDeleteEvent = function listenToProductOfferingPriceDeleteEvent (req, res, next) {
  NotificationListenersClientSide.listenToProductOfferingPriceDeleteEvent(req, res, next);
};

module.exports.listenToProductOfferingPriceStateChangeEvent = function listenToProductOfferingPriceStateChangeEvent (req, res, next) {
  NotificationListenersClientSide.listenToProductOfferingPriceStateChangeEvent(req, res, next);
};

module.exports.listenToProductOfferingStateChangeEvent = function listenToProductOfferingStateChangeEvent (req, res, next) {
  NotificationListenersClientSide.listenToProductOfferingStateChangeEvent(req, res, next);
};

module.exports.listenToProductSpecificationCreateEvent = function listenToProductSpecificationCreateEvent (req, res, next) {
  NotificationListenersClientSide.listenToProductSpecificationCreateEvent(req, res, next);
};

module.exports.listenToProductSpecificationDeleteEvent = function listenToProductSpecificationDeleteEvent (req, res, next) {
  NotificationListenersClientSide.listenToProductSpecificationDeleteEvent(req, res, next);
};
