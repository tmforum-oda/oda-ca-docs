'use strict';

var url = require('url');

var PartyRole = require('../service/PartyRoleService');

module.exports.createPartyRole = function createPartyRole (req, res, next) {
  PartyRole.createPartyRole(req, res, next);
};

module.exports.deletePartyRole = function deletePartyRole (req, res, next) {
  PartyRole.deletePartyRole(req, res, next);
};

module.exports.listPartyRole = function listPartyRole (req, res, next) {
  PartyRole.listPartyRole(req, res, next);
};

module.exports.patchPartyRole = function patchPartyRole (req, res, next) {
  PartyRole.patchPartyRole(req, res, next);
};

module.exports.retrievePartyRole = function retrievePartyRole (req, res, next) {
  PartyRole.retrievePartyRole(req, res, next);
};
