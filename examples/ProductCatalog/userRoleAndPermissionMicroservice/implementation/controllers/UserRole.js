'use strict';

var url = require('url');

var UserRole = require('../service/UserRoleService');

module.exports.createUserRole = function createUserRole (req, res, next) {
  UserRole.createUserRole(req, res, next);
};

module.exports.listUserRole = function listUserRole (req, res, next) {
  UserRole.listUserRole(req, res, next);
};

module.exports.patchUserRole = function patchUserRole (req, res, next) {
  UserRole.patchUserRole(req, res, next);
};

module.exports.retrieveUserRole = function retrieveUserRole (req, res, next) {
  UserRole.retrieveUserRole(req, res, next);
};
