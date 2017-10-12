/**
 * Created by carl.li on 2017/2/16.
 */
(function() {
cola(function (model) {
    model.dataType({
        name: "Area",
        properties: {
            areas: {
                dataType: "Area",
                provider: {
                    url: "./service/area/recursion?parentId={{@id}}"
                }
            }
        }
    });
    model.describe("areas",{
        dataType: "Area",
        provider: {
            url: "./service/area/recursion?parentId=-1"
        }
    });

    model.action({
        selectedNodes: function (nodes, arrayNode) {
            $.each(nodes, function () {
                var node = this;
                if (node.get("checked")) {
                    arrayNode.push(node.get("data.areaName"));
                }
                var children = node.get("children");
                if (children) {
                    model.action.selectedNodes(children, arrayNode);
                }
            });
            model.set("arrayNode", arrayNode);
        },
        getNodes: function () {
            var nodes = cola.widget("recursiveTree").getItems();
            var arrayNode = [];
            model.action.selectedNodes(nodes,arrayNode);
            model.get("arrayNode");
            if (arrayNode.length > 0) {
                cola.alert("选中的节点有："+model.get("arrayNode"));
            } else {
                cola.alert("请选择树节点！");
            }
        }
    });

    model.widgetConfig({
        recursiveTree: {
            $type: "tree",
            bind: {
                checkedProperty: "checked",
                textProperty: "areaName",
                expression: "area in areas",
                child: {
                    recursive: true,
                    checkedProperty: "checked",
                    textProperty: "areaName",
                    expression: "area in area.areas"
                }
            }
        }
    });

});
}).call(this);