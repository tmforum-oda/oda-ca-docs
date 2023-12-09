'use strict';

var url = require('url');

var NotificationListenersClientSide = require('../service/NotificationListenersClientSideService');

module.exports.listenToPermissionAttributeValueChangeEvent = function listenToPermissionAttributeValueChangeEvent (req, res, next) {
  NotificationListenersClientSide.listenToPermissionAttributeValueChangeEvent(req, res, next);
};

module.exports.listenToPermissionCreateEvent = function listenToPermissionCreateEvent (req, res, next) {
  NotificationListenersClientSide.listenToPermissionCreateEvent(req, res, next);
};

module.exports.listenToPermissionDeleteEvent = function listenToPermissionDeleteEvent (req, res, next) {
  NotificationListenersClientSide.listenToPermissionDeleteEvent(req, res, next);
};

module.exports.listenToPermissionStateChangeEvent = function listenToPermissionStateChangeEvent (req, res, next) {
  NotificationListenersClientSide.listenToPermissionStateChangeEvent(req, res, next);
};

module.exports.listenToUserRoleAttributeValueChangeEvent = function listenToUserRoleAttributeValueChangeEvent (req, res, next) {
  NotificationListenersClientSide.listenToUserRoleAttributeValueChangeEvent(req, res, next);
};

module.exports.listenToUserRoleCreateEvent = function listenToUserRoleCreateEvent (req, res, next) {
  NotificationListenersClientSide.listenToUserRoleCreateEvent(req, res, next);
};

module.exports.listenToUserRoleDeleteEvent = function listenToUserRoleDeleteEvent (req, res, next) {
  NotificationListenersClientSide.listenToUserRoleDeleteEvent(req, res, next);
};

module.exports.listenToUserRoleStateChangeEvent = function listenToUserRoleStateChangeEvent (req, res, next) {
  NotificationListenersClientSide.listenToUserRoleStateChangeEvent(req, res, next);
};
