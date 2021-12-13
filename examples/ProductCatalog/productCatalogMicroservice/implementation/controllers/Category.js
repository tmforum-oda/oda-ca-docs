'use strict';

var url = require('url');

var Category = require('../service/CategoryService');

module.exports.createCategory = function createCategory (req, res, next) {
  Category.createCategory(req, res, next);
};

module.exports.deleteCategory = function deleteCategory (req, res, next) {
  Category.deleteCategory(req, res, next);
};

module.exports.listCategory = function listCategory (req, res, next) {
  Category.listCategory(req, res, next);
};

module.exports.patchCategory = function patchCategory (req, res, next) {
  Category.patchCategory(req, res, next);
};

module.exports.retrieveCategory = function retrieveCategory (req, res, next) {
  Category.retrieveCategory(req, res, next);
};
