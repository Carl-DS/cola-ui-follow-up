/**
 * Created by carl.li on 2017/2/14.
 */
(function() {
    cola(function(model) {
        // 声明地区类型
        model.describe("areas", {
            dataType: {
                name: "Area",
                properties: {
                    id: {
                        validators: ["required"]
                    },
                    areaName: {
                        validators: ["required"]
                    },
                    level: {
                        dataType: "number"
                    },
                    parentId: {
                        dataType: "number"
                    }
                }
            },
            provider: {
                url: "./service/area",
                pageSize: 20
            }
        });

        // 事件区域
        model.action({

        });

        return model.widgetConfig({
            areaTable: {
                $type: "table",
                showHeader: true,
                bind: "area in areas",
                highlightCurrentItem: true,
                currentPageOnly: false,
                autoLoadPage: true,
                sortMode: "remote",
                height: "300px",
                columns: [
                    {
                        caption: "地区编号",
                        bind: ".id",
                        sortable: true,
                        property: "id"
                    }, {
                        caption: "地区名称",
                        bind: ".areaName",
                        sortable: true,
                        property: "areaName"
                    }, {
                        caption: "等级",
                        bind: ".level",
                        sortable: true,
                        property: "level",
                        align: "right"
                    }, {
                        caption: "父级地区",
                        bind: ".parentId",
                        sortable: true,
                        property: "parentId",
                        align: "center"
                    }
                ]
            }
        });


    });
}).call(this);
