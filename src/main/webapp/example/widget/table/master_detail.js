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
                        url: "/service/product/",
                        pageSize: 5,
                        beforeSend: function (self, arg) {
                            arg.options.data.categoryId = arg.model.get("categories.id");
                            return NProgress.start();
                        },
                        complete: function (sefl, arg) {
                            return NProgress.done();
                        }
                    }
                }
            }
        });
        model.describe("categories", {
            dataType: "Category",
            provider: {
                url: "/service/category/",
                pageSize: 5
            }
        });
        model.describe("customProduct", "Product");
        model.describe("customCategory", "Category");
        model.describe("products", []);

        model.action({
            addCategory: function () {
                model.set("customCategory", {});
                return cola.widget("categoryLayer").show();
            },
            addProduct: function () {
                var current;
                current = model.get("categories").current;
                model.set("customProduct", {
                    categoryId: current.get("id")
                });
                return cola.widget("productLayer").show();
            },
            productCancel: function () {
                model.set("customProduct", {});
                return cola.widget("productLayer").hide();
            },
            productSave: function () {
                var data, product, result;
                product = model.get("customProduct");
                result = product.validate();
                if (result) {
                    data = product.toJSON();
                    return $.ajax("./service/product/", {
                        data: JSON.stringify(data),
                        type: data.id ? "PUT" : "POST",
                        contentType: "application/json",
                        complete: function () {
                            model.get("categories").current.get("products").flush();
                            cola.widget("productLayer").hide();
                        }
                    });
                }
            },
            categoryCancel: function () {
                model.set("customCategory", {});
                return cola.widget("categoryLayer").hide();
            },
            categoriesave: function () {
                var category, data, result;
                category = model.get("customCategory");
                result = category.validate();
                if (result) {
                    data = category.toJSON();
                    return $.ajax("./service/category/", {
                        data: JSON.stringify(data),
                        type: data.id ? "PUT" : "POST",
                        contentType: "application/json",
                        complete: function () {
                            cola.widget("categoryLayer").hide();
                        }
                    });
                }
            }
        });
        model.widgetConfig({
            productLayer: {
                $type: "Layer"
            },
            categoryLayer: {
                $type: "Layer"
            },
            categoryList: {
                $type: "table",
                bind: "item in categories",
                showHeader: true,
                currentPageOnly: true,
                autoLoadPage: false,
                changeCurrentItem: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".id",
                        caption: "分类编号"
                    }, {
                        bind: ".categoryName",
                        caption: "分类名"
                    }, {
                        bind: ".description",
                        caption: "描述信息"
                    }
                ]
            },
            productList: {
                $type: "table",
                bind: "item in categories.products",
                showHeader: true,
                columns: [
                    {
                        bind: ".id",
                        caption: "编号"
                    }, {
                        bind: ".productName",
                        caption: "产品名称"
                    }, {
                        bind: ".quantityPerUnit",
                        caption: "规格"
                    }, {
                        bind: ".unitPrice",
                        caption: "单价"
                    }, {
                        bind: ".unitsInStock",
                        caption: "库存"
                    }, {
                        bind: ".unitsOnOrder",
                        caption: "订单量"
                    }
                ]
            }
        });

    });
}).call(this);
