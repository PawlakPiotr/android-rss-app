const axios = require("axios");
const xml2js = require('xml2js');
const fs = require('fs');
const https = require('https');
const express = require("express");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");

eyes = require('eyes');

const router = express.Router();
var parser = new xml2js.Parser();

const NEWS_URL = 'https://www.tvn24.pl/najnowsze.xml';

router.get("/all", (req, response, next) => {
    https.get(NEWS_URL, (res) => {
        var response_data = '';
        res.setEncoding('utf8');
        res.on('data', function(chunk) {
            response_data += chunk;
        });
        res.on('end', function() {
            parser.parseString(response_data, function(err, result) {
                if (err) {
                    console.log('Got error: ' + err.message);
                } else {
                    eyes.inspect(result);
                    response.json({
                        message: 'News fetched',
                        news: result
                    });
                }
            });
        });
        res.on('error', function(err) {
            console.log('Got error: ' + err.message);
        });
    });

});

module.exports = router;