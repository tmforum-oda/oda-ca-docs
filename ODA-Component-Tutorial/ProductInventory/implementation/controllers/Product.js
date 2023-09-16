'use strict';

var url = require('url');

var Product = require('../service/ProductService');

module.exports.createProduct = function createProduct (req, res, next) {
  Product.createProduct(req, res, next);
};

module.exports.deleteProduct = function deleteProduct (req, res, next) {
  Product.deleteProduct(req, res, next);
};

module.exports.listProduct = function listProduct (req, res, next) {
  Product.listProduct(req, res, next);
};

module.exports.patchProduct = function patchProduct (req, res, next) {
  Product.patchProduct(req, res, next);
};

module.exports.retrieveProduct = function retrieveProduct (req, res, next) {
  Product.retrieveProduct(req, res, next);
};
