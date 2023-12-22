const pool = require("../../config/database");
// const {nanoid} = require("nanoid");

module.exports = {
    create: (data, callBack) => {
        // const id = nanoid(5);
        pool.query(
            `insert into product(product_name, price, user_id) 
            values(?,?,?)`,
            [
                data.product_name,
                data.price,
                data.user_id
            ],
            (error, results, fields) =>{
                if(error){
                    callBack(error)
                }
                return callBack(null, results);
            }
        );
    },

    
    getAll: callBack => {
        pool.query(
          `select id, product_name, price, user_id from product`,
          [],
          (error, results, fields) => {
            if (error) {
              callBack(error);
            }
            return callBack(null, results);
          }
        );
    },
    getAllById: (id, callBack) => {
        pool.query(
          `select id,product_name,price,user_id from product where id = ?`,
          [id],
          (error, results, fields) => {
            if (error) {
              callBack(error);
            }
            return callBack(null, results[0]);
          }
        );
    },
    updateById: (data, callBack) => {
        pool.query(
          `update product set product_name=?, price=?, user_id=? where id = ?`,
          [
            data.product_name,
            data.price,
            data.user_id,
            data.id
          ],
          (error, results, fields) => {
            if (error) {
              callBack(error);
            }
            return callBack(null, results);
          }
        );
    },
    deleteById: (data, callBack) => {
        pool.query(
          `delete from product where id = ?`,
          [data.id],
          (error, results, fields) => {
            if (error) {
              callBack(error);
            }
            return callBack(null, results);
          }
        );
    },

}