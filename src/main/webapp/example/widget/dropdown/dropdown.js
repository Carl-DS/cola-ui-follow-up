/**
 * Created by carl.li on 2017/2/16.
 */
(function () {
    cola(function (model) {
        model.set("provinces", {nodes: [{name: "中国", nodes: [
            {name: "上海", nodes: [{name: "浦东新区"}, {name: "杨浦"}, {name: "松江"}, {name: "徐汇区"}] },
            {name: "内蒙古", nodes: [{name: "兴安盟"}, {name: "通辽"}, {name: "锡林郭勒"}, {name: "呼伦贝尔"}, {name: "赤峰"}]}
            ] }] });

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
                    width: "180px",
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
            },
            // 三级联动
            provinceDropdown: {
                $type: "dropdown",
                openMode: "drop",
                items: "{{province in provinces.nodes}}",
                textProperty: "name",
                bind: "provinceValue",
                post: function (self) {
                    model.set("cities", self.get("value").get("nodes"));
                }
            },
            // 三级联动
            cityDropdown: {
                $type: "dropdown",
                openMode: "drop",
                items: "{{city in cities}}",
                textProperty: "name",
                bind: "cityValue",
                post: function (self) {
                    model.set("areas", self.get("value").get("nodes"));
                }
            },
            // 三级联动
            areaDropdown: {
                $type: "dropdown",
                openMode: "drop",
                items: "{{area in areas}}",
                textProperty: "name",
                bind: "areaValue"
            }
        });



    });
}).call(this);