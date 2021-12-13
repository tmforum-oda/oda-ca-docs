'use strict';

var url = require('url');

var Catalog = require('../service/CatalogService');

module.exports.createCatalog = function createCatalog (req, res, next) {
  Catalog.createCatalog(req, res, next);
};

module.exports.deleteCatalog = function deleteCatalog (req, res, next) {
  Catalog.deleteCatalog(req, res, next);
};

module.exports.listCatalog = function listCatalog (req, res, next) {
  Catalog.listCatalog(req, res, next);
};

module.exports.patchCatalog = function patchCatalog (req, res, next) {
  Catalog.patchCatalog(req, res, next);
};

module.exports.retrieveCatalog = function retrieveCatalog (req, res, next) {
  Catalog.retrieveCatalog(req, res, next);
};
