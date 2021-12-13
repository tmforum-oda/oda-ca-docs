'use strict';

var url = require('url');

var ProductOfferingPrice = require('../service/ProductOfferingPriceService');

module.exports.createProductOfferingPrice = function createProductOfferingPrice (req, res, next) {
  ProductOfferingPrice.createProductOfferingPrice(req, res, next);
};

module.exports.deleteProductOfferingPrice = function deleteProductOfferingPrice (req, res, next) {
  ProductOfferingPrice.deleteProductOfferingPrice(req, res, next);
};

module.exports.listProductOfferingPrice = function listProductOfferingPrice (req, res, next) {
  ProductOfferingPrice.listProductOfferingPrice(req, res, next);
};

module.exports.patchProductOfferingPrice = function patchProductOfferingPrice (req, res, next) {
  ProductOfferingPrice.patchProductOfferingPrice(req, res, next);
};

module.exports.retrieveProductOfferingPrice = function retrieveProductOfferingPrice (req, res, next) {
  ProductOfferingPrice.retrieveProductOfferingPrice(req, res, next);
};
