const mysql = require("mysql2");

const pool = mysql.createConnection({
    port: 3306,
    host : 'localhost',
    user : 'root',
    password : '', 
    database : 'sellsmartly',
    // socketPath : process.env.DB_SOCKET_PATH
});

module.exports = pool;