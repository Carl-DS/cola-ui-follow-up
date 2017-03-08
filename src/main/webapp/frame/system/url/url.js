(function () {
    cola(function (model) {
        model.dataType({
            name: "Url",
            properties: {
                label: {
                    validators: ["required", {
                        $type: "length",
                        min: 0,
                        max: 30
                    }]
                },
                path: {
                    validators: [{
                        $type: "length",
                        min: 0,
                        max: 60
                    }]
                },
                icon: {
                    validators: ["required", {
                        $type: "length",
                        min: 0,
                        max: 120
                    }]
                },
                desc: {
                    validators: [{
                        $type: "length",
                        min: 0,
                        max: 60
                    }]
                }
            }
        });

        model.describe("urls", {
            dataType: "Url",
            provider: {
                url: parent.App.prop("service.menus")
            }
        });
        model.describe("currentEditItem", "Url");
        model.set("currentEditItem", {});
        model.set("currentEditItem.forNavigation", "true");
        model.action({
            add: function () {
                var currentEditItem=model.get("currentEditItem");
                var nodes = currentEditItem.get("menus");
                if (!nodes) {
                    currentEditItem.set("menus", []);
                    nodes = currentEditItem.get("menus")
                }
                var tree = cola.widget("urlTree");
                var currentNode = tree.findNode(currentEditItem);
                tree.expand(currentNode);
                var entity=nodes.insert({
                    label : "<新菜单>",
                    parentId : tree.get("currentNode").get("data").get("id"),
                    order : 1,
                    icon : "iconfont icon-shoudan",
                    forNavigation : true,
                    companyId : ""
                });
                tree.set("currentItem",entity);
                event && event.stopPropagation();
            },
            // add : function() {
            //     debugger;
            //     var tree = cola.widget("urlTree");
            //     var currentNode = tree.get("currentNode");
            //     var nodeEntity = currentNode.get("data");
            //     if (nodeEntity.state == "new") {
            //         cola.NotifyTipManager.error({
            //             message : "添加失败",
            //             description : "您添加的节点未保存，请先保存再添加子节点！",
            //             showDuration : 1000
            //         });
            //     } else {
            //         var order;
            //         var nodes = nodeEntity.get("menus", "sync");
            //         if (!nodes) {
            //             nodeEntity.set("menus", []);
            //             nodes = nodeEntity.get("menus");
            //         }
            //         if (!nodeEntity._data.menus._last._last) {
            //             order = 1;
            //         } else {
            //             order = nodeEntity._data.nodes._last._last._data.order + 1;
            //         }
            //         var entity = nodes.insert({
            //             name : "<新菜单>",
            //             parentId : nodeEntity.get("id"),
            //             order : order,
            //             icon : "iconfont icon-shoudan",
            //             forNavigation : true,
            //             companyId : ""
            //
            //         });
            //         tree.expand(currentNode);
            //         tree.set("currentItem", entity);
            //         event && event.stopPropagation();
            //     }
            // },
            remove : function(node) {
                var currentEditItem = model.get("currentEditItem");
                var nodes = currentEditItem.get("nodes", "sync");
                cola.confirm("您确定要删除当前记录吗？", {
                    onApprove : function() {
                        if (nodes && nodes.entityCount > 0) {
                            cola.alert("存在子菜单，不可删除！");
                        } else {
                            $.ajax("./service/frame/url/"
                                + model.get("currentEditItem").get(
                                    "id") + "/", {
                                type : "DELETE",
                                success : function(arg, self) {
                                    node.remove();
                                    cola.NotifyTipManager.success({
                                        message : "删除成功",
                                        description : "后台任务执行成功！",
                                        showDuration : 1000
                                    });
                                }
                            });
                        }
                    }
                });
            },
            saveUrl: function () {
                debugger;
            }
        });

        model.widgetConfig({
            forNavigationRadioGroup: {
                items: ["true", "false"]
            },
            urlTree: {
                $type: "tree",
                lazyRenderChildNodes: true,
                autoExpand: true,
                autoCollapse: false,
                highlightCurrentItem: true,
                bind: {
                    expression: "url in urls",
                    popup: "url.label", // disabled
                    expanded: true,
                    child: {
                        recursive: true,
                        expression: "url in url.menus",
                        popup: "url.label", // disabled
                        expanded: true
                    }
                },
                currentNodeChange: function (self, arg) {
                    var current = self.get("currentNode");
                    if (current) {
                        model.set("currentEditItem", current ? current.get("data") : null);
                        // var entity = current.get("data");
                        //copyNodeDataToEdit(entity);
                    }
                }
            }
        });
    });
}).call(this);
