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
        model.action({
            uuid:function () {
                return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
                    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
                    return v.toString(16);
                });
            },
            addRootNode: function () {
                debugger;
                var nodes, order, entity;
                nodes = model.get("urls");
                if (!nodes) {
                    order = 1;
                } else {
                    order = nodes.last().get("order") + 1;
                }
                entity = nodes.insert({
                    id: model.action.uuid(),
                    label : "<新菜单>",
                    order : order,
                    icon : "icon edit",
                    forNavigation : true,
                    companyId : "bstek"
                });
                var tree = cola.widget("urlTree");
                var currentNode = tree.findNode(entity);
                tree.expand(currentNode);
                tree.set("currentItem", entity);
                event && event.stopPropagation();
            },
            add: function () {
                debugger;
                var tree, currentEditItem, currentNode, nodeEntity, nodes, order;
                tree = cola.widget("urlTree");
                currentEditItem=model.get("currentEditItem");
                currentNode = tree.findNode(currentEditItem);
                nodeEntity = currentNode.get("data");
                if (nodeEntity.state === "new") {
                    cola.NotifyTipManager.error({
                        message : "添加失败",
                        description : "您添加的节点未保存，请先保存再添加子节点！",
                        showDuration : 3000
                    });
                } else {
                    nodes = currentEditItem.get("menus");
                    order = 1;
                    if (!nodes) {
                        // 将currentEditItem设置成EntityList, 便于使用insert() 方法
                        currentEditItem.set("menus", []);
                        nodes = currentEditItem.get("menus")
                    } else {
                        if (nodes.entityCount>0)
                            order = nodes.last().get("order") + 1;
                    }

                    var entity=nodes.insert({
                        id: model.action.uuid(),
                        label : "<新菜单>",
                        parentId : nodeEntity.get("id"),
                        order : order,
                        icon : "icon edit",
                        forNavigation : true,
                        companyId : "bstek"
                    });

                    tree.expand(currentNode);
                    tree.set("currentItem",entity);
                    event && event.stopPropagation();
                }
            },
            remove : function(url) {
                debugger;
                var currentEditItem = model.get("currentEditItem");
                var nodes = currentEditItem.get("menus", "sync");
                cola.confirm("您确定要删除当前记录吗？", {
                    onApprove : function() {
                        if (nodes && nodes.entityCount > 0) {
                            cola.alert("存在子菜单，无法删除！");
                        } else {
                            if (url.state === "new")
                                return url.remove();
                            $.ajax("./service/frame/url/"+ url.get("id"), {
                                type : "DELETE",
                                success : function() {
                                    url.remove();
                                    cola.NotifyTipManager.success({
                                        message : "删除成功",
                                        description : "后台任务执行成功！",
                                        showDuration : 3000
                                    });
                                }
                            });
                        }
                    }
                });
            },
            saveUrl: function () {
                debugger;
                var url;
                url = model.get("currentEditItem").toJSON();
                delete url.menus;
                if (!model.get("currentEditItem").validate()) return;
                $.ajax("./service/frame/url/", {
                    type: "POST",
                    data: JSON.stringify(url),
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        debugger;
                        cola.NotifyTipManager.success({
                            message: "消息提示",
                            description: "保存成功!",
                            showDuration: 3000
                        });
                        model.get("currentEditItem").setState("none");
                    }
                });
            }

        });

        model.widgetConfig({
            forNavigationRadioGroup: {
                items: [{label:"是", value: true}, {label:"否", value: false}]
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
                        expression: "url in sort(url.menus, 'order')",
                        popup: "url.label", // disabled
                        expanded: true
                    }
                },
                currentNodeChange: function (self, arg) {
                    var current;
                    current = self.get("currentNode");
                    if (cola.defaultAction.isNotEmpty(current)) {
                        model.set("currentEditItem", current.get("data"));
                    }
                }
            }
        });
    });
}).call(this);
