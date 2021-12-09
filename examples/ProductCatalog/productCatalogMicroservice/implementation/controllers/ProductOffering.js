'use strict';

var url = require('url');

var ProductOffering = require('../service/ProductOfferingService');

module.exports.createProductOffering = function createProductOffering (req, res, next) {
  ProductOffering.createProductOffering(req, res, next);
};

module.exports.deleteProductOffering = function deleteProductOffering (req, res, next) {
  ProductOffering.deleteProductOffering(req, res, next);
};

module.exports.listProductOffering = function listProductOffering (req, res, next) {
  ProductOffering.listProductOffering(req, res, next);
};

module.exports.patchProductOffering = function patchProductOffering (req, res, next) {
  ProductOffering.patchProductOffering(req, res, next);
};

module.exports.retrieveProductOffering = function retrieveProductOffering (req, res, next) {
  ProductOffering.retrieveProductOffering(req, res, next);
};
