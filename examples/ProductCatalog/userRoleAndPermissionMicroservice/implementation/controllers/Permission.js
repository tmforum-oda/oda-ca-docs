'use strict';

var url = require('url');

var Permission = require('../service/PermissionService');

module.exports.createPermission = function createPermission (req, res, next) {
  Permission.createPermission(req, res, next);
};

module.exports.listPermission = function listPermission (req, res, next) {
  Permission.listPermission(req, res, next);
};

module.exports.patchPermission = function patchPermission (req, res, next) {
  Permission.patchPermission(req, res, next);
};

module.exports.retrievePermission = function retrievePermission (req, res, next) {
  Permission.retrievePermission(req, res, next);
};
