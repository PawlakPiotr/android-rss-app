const mongoose = require("mongoose");

const newsSchema = mongoose.Schema({
  email: { type: String, required: true },
  title: { type: String },
  description: { type: String },
  date: { type: String },
});

module.exports = mongoose.model("News", newsSchema);