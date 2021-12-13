'use strict';

var url = require('url');

var ExportJob = require('../service/ExportJobService');

module.exports.createExportJob = function createExportJob (req, res, next) {
  ExportJob.createExportJob(req, res, next);
};

module.exports.deleteExportJob = function deleteExportJob (req, res, next) {
  ExportJob.deleteExportJob(req, res, next);
};

module.exports.listExportJob = function listExportJob (req, res, next) {
  ExportJob.listExportJob(req, res, next);
};

module.exports.retrieveExportJob = function retrieveExportJob (req, res, next) {
  ExportJob.retrieveExportJob(req, res, next);
};
