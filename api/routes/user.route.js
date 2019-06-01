const express = require("express");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");

const User = require("../model/user");

const router = express.Router();

router.post("/signup", (req, res, next) => {
    const user = new User({
      email: req.body.email,
      password: req.body.password
    });
    user.save().then(result => {
        res.status(201).json({
            message: "User created!",
            result: result
        });
    })
    .catch(err => {
        res.status(500).json({
            message: "Invalid authentication credentials!"
        });
    });

});

router.post("/login", (req, res, next) => {

  User.findOne({ email: req.body.email, password: req.body.password })
    .then(user => {
      if (!user) {
        res.json({ 
          code: "401",
          message: 'Account does not exists' });
      } else {
        res.json({ 
          code: "200",
          message: 'Login successful' });
      }
    })
});

router.get("/all", (req, res, next) => {
  User.find()
    .then(users => {
      res.json({
        message: 'Users fetched',
        users: users
      });
    });
});

router.get("/:id", (req, res, next) => {
  User.findById(req.params.id)
    .then(user => {
      if (user) {
        res.status(200).json(user);
      }
    })
});

module.exports = router;