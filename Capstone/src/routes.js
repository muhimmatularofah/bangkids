const router = require("express").Router();
const { 
    create, 
    getAll, 
    getAllById, 
    updateById, 
    deleteById, 
} = require("./api/users/product.controller");


// product
router.post("/input", create);
router.get("/product", getAll);
router.get("/product/:id", getAllById);
router.patch("/product/update", updateById);
router.delete("/product/delete",deleteById);

module.exports = router;
