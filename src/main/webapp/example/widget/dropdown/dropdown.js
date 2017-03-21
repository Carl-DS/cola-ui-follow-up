/**
 * Created by carl.li on 2017/2/16.
 */
(function () {
    cola(function (model) {
        model.describe("categories", {
            provider: {
                url: "/service/category/",
                pageSize: 5
            }
        });

        model.describe("products", {
            provider: {
                url: "/service/product/",
                pageSize: 5,
                beforeSend: function(self, arg) {
                    arg.options.data.categoryId = model.get("categoryId")||1;
                }
            }
        });

        // 事件定义区
        model.action({

        });

        model.widgetConfig({
            // 标准下拉框
            standardDropdown: {
                $type: "dropdown",
                openMode: "drop",
                valueProperty: "id",
                textProperty: "categoryName",
                placeholder: "请选择",
                items: "{{category in categories}}",
                bind: "stanCurrVal"
            },
            // 自定义下拉框
            customDropdown: {
                $type: "customDropdown",
                openMode: "drop",
                valueProperty: "id",
                textProperty: "categoryName",
                placeholder: "请选择",
                bind: "customCurrVal",
                content: { // 此处内部可写不同形式的数据展现
                    $type: "table",
                    bind: "category in categories",
                    width: "174px",
                    columns: [{ caption: "分类名", bind: ".categoryName"},
                        {caption: "描述", bind: ".description"} ],
                    itemClick: function (self, arg) {
                        // 此处两行函数获取下拉框里选择的值
                        var dropdown = cola.findDropDown(self);
                        if (dropdown) dropdown.close(arg.item);
                    }
                }
            },
            // 级联下拉框 种类
            categoryDropdown: {
                $type: "dropdown",
                openMode: "drop",
                valueProperty: "id",
                textProperty: "categoryName",
                placeholder: "请选择",
                items: "{{category in categories}}",
                bind: "categoryId",
                post: function () {
                    model.flush("products");
                }
            },
            // 级联下拉框 商品
            productDropdown: {
                $type: "dropdown",
                openMode: "drop",
                valueProperty: "id",
                textProperty: "productName",
                items: "{{product in products}}",
                bind: "productId"
            }
        });



    });
}).call(this);