'use strict';

var url = require('url');

var ProductSpecification = require('../service/ProductSpecificationService');

module.exports.createProductSpecification = function createProductSpecification (req, res, next) {
  ProductSpecification.createProductSpecification(req, res, next);
};

module.exports.deleteProductSpecification = function deleteProductSpecification (req, res, next) {
  ProductSpecification.deleteProductSpecification(req, res, next);
};

module.exports.listProductSpecification = function listProductSpecification (req, res, next) {
  ProductSpecification.listProductSpecification(req, res, next);
};

module.exports.patchProductSpecification = function patchProductSpecification (req, res, next) {
  ProductSpecification.patchProductSpecification(req, res, next);
};

module.exports.retrieveProductSpecification = function retrieveProductSpecification (req, res, next) {
  ProductSpecification.retrieveProductSpecification(req, res, next);
};
