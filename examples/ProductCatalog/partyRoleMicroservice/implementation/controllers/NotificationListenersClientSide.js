'use strict';

var url = require('url');

var NotificationListenersClientSide = require('../service/NotificationListenersClientSideService');

module.exports.listenToPartyRoleAttributeValueChangeEvent = function listenToPartyRoleAttributeValueChangeEvent (req, res, next) {
  NotificationListenersClientSide.listenToPartyRoleAttributeValueChangeEvent(req, res, next);
};

module.exports.listenToPartyRoleCreateEvent = function listenToPartyRoleCreateEvent (req, res, next) {
  NotificationListenersClientSide.listenToPartyRoleCreateEvent(req, res, next);
};

module.exports.listenToPartyRoleDeleteEvent = function listenToPartyRoleDeleteEvent (req, res, next) {
  NotificationListenersClientSide.listenToPartyRoleDeleteEvent(req, res, next);
};

module.exports.listenToPartyRoleStateChangeEvent = function listenToPartyRoleStateChangeEvent (req, res, next) {
  NotificationListenersClientSide.listenToPartyRoleStateChangeEvent(req, res, next);
};
