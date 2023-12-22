require("dotenv").config()
const express = require('express')
const app = express()
const userRouter = require("./src/routes")
// const pool = require("./src/config/database");

app.use(express.urlencoded({ extended: true }));
app.use(express.json());
app.use(userRouter);

app.get("/sell", (req, res) => {
    res.json({
        success: 1,
        message: "This is rest Api"
    });
});

app.listen
const port = 8000

app.listen(port, () => {
  console.log(`App running on port ${port}`)
})