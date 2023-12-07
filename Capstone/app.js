const express = require('express')
const app = express()
const port = 3000
const mysql = require('mysql')
const connection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '123Keren',
  database: 'sellsmartly'
})
app.get('/kategori', (req, res) => {
  connection.connect()

connection.query('SELECT * from kategori', (err, rows, fields) => {
  if (err) throw err
  res.send(rows)
})

connection.end()
})
app.get('/produk', (req, res) => {
  connection.connect()

connection.query('SELECT * from produk', (err, rows, fields) => {
  if (err) throw err
  res.send(rows)
})

connection.end()
})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})