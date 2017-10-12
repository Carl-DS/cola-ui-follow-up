/**
 * Created by carl.li on 2017/2/16.
 */
(function () {
    cola(function (model) {
        model.dataType({
            name: "Product",
            properties: {
                productName: {
                    validators: ["required"]
                },
                id: {
                    dataType: "number"
                },
                categoryId: {
                    dataType: "number"
                },
                quantityPerUnit: {
                    dataType: "number"
                },
                unitPrice: {
                    dataType: "number"
                },
                unitsInStock: {
                    dataType: "number"
                },
                unitsOnOrder: {
                    dataType: "number"
                }
            }
        });
        model.dataType({
            name: "Category",
            properties: {
                categoryName: {
                    validators: ["required"]
                },
                products: {
                    dataType: "Product",
                    provider: {
                        url: "/service/product?categoryId={{@id}}",
                        pageSize: 5
                    }
                }
            }
        });
        model.describe("categories", {
            dataType: "Category",
            provider: {
                url: "/service/category",
                pageSize: 5
            }
        });

        model.action({

        });

        model.widgetConfig({
            tree: {
                $type: "tree",
                autoCollapse: false,
                autoExpand: true,
                highlightCurrentItem: true,
                bind: {
                    expandedProperty: "expanded",
                    textProperty: "categoryName",
                    expression: "category in categories",
                    child: {
                        expandedProperty: "expanded",
                        textProperty: "productName",
                        expression: "product in category.products"
                    }
                }
            }
        });

    });
}).call(this);