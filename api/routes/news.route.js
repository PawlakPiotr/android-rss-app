const xml2js = require('xml2js');
const https = require('https');
const express = require("express");
eyes = require('eyes');

const News = require("../model/news");

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

router.post("/favourites/add", (req, response, next) => {
    const news = new News({
        email: req.body.email,
        title: req.body.title,
        description: req.body.description,
        date: req.body.date
      });
      news.save().then(result => {
        response.status(201).json({
              message: "Article added to favourites",
              result: result
          });
      })

});

router.post("/favourites", (req, response, next) => {
    
    News.find({email: req.body.email}, (res) => {
        console.log(res);
    }).then(x => {
        console.log(x);
        response.json({
            message: "Favourites news fetched",
            news: x
        });
    })

});

module.exports = router;