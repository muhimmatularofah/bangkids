const { 
    create, 
    getAll, 
    getAllById, 
    updateById, 
    deleteById, 
  } = require("./product.service");
  
  module.exports = {
      create: async (req, res) => {
        try {
          const body = req.body;
      
          create(body, async (err, results) => {
            if (err) {
              console.log(err);
              return res.status(500).json({
                success: 0,
                message: "data input failed"
              });
            }
            
            return res.status(200).json({
              success: 1,
              data: results
            });
          });
        } catch (error) {
          console.error(error);
          return res.status(500).json({
            success: 0,
            message: "Internal server error"
          });
        }
      },    
      getAll: (req, res) => {
          getAll((err, results) => {
            if (err) {
              console.log(err);
              return;
            }
             return res.json({
              success: 1,
              data: results
            });
          });
      },
      getAllById: (req, res) => {
          const id = req.params.id;
          getAllById(id, (err, results) => {
            if (err) {
              console.log(err);
              return;
            }
            if (!results) {
              return res.json({
                success: 0,
                message: "Record not Found"
              });
            }
            results.password = undefined;
            return res.json({
              success: 1,
              data: results
            });
          });
      },
      updateById: (req, res) => {
          const body = req.body;
          updateById(body, (err, results) => {
            if (err) {
              console.log(err);
              return;
            }
            if (!results) {
              return res.json({
                success: 0,
                message: "Update unsuccessful"
              });
            }
            return res.json({
              success: 1,
              message: "Updated successfully"
            });
          });
      },
      deleteById: (req, res) => {
          const data = req.body;
          deleteById(data, (err, results) => {
            if (err) {
              console.log(err);
              return;
            }
            if (!results) {
              return res.json({
                success: 0,
                message: "Record Not Found"
              });
            }
            return res.json({
              success: 1,
              message: "user deleted successfully"
            });
          });
      },
  }