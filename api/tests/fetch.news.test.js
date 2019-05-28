let axios = require("axios");
let xml2js = require('xml2js');
let fs = require('fs');
let https = require('https');
eyes = require('eyes');

var parser = new xml2js.Parser();

https.get('https://www.tvn24.pl/najnowsze.xml', (res) => {
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
                console.log('Done.');
            }
        });
    });
    res.on('error', function(err) {
        console.log('Got error: ' + err.message);
    });
});
