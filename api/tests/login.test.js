let axios = require("axios");

var data = {
    email: 'test@test',
    password: 'test'
  };
  
let axiosConfig = {
headers: {
    'Content-Type': 'application/json;charset=UTF-8',
    "Access-Control-Allow-Origin": "*",
}
};

axios.post('http://localhost:3000/api/user/login', data, axiosConfig)
.then((res) => {
console.log("RESPONSE RECEIVED: ", res);
})
.catch((err) => {
console.log("AXIOS ERROR: ", err);
})
