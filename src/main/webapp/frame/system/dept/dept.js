(function () {
    cola(function (model) {
        model.dataType({
            name: "Dept",
            properties: {
                name: {
                    validators: "required"
                },
                desc: {
                    $type: "string"
                }
            }
        });

        model.describe("depts", {
            dataType: "Dept",
            provider: {
                url: "service/frame/dept/depts"
            }
        });
        model.describe("currentEditItem", "Dept");
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
                nodes = model.get("depts");
                if (!nodes) {
                    order = 1;
                } else {
                    order = nodes.last().get("order") + 1;
                }
                entity = nodes.insert({
                    id: model.action.uuid(),
                    name : "<新部门>",
                    order : order,
                    icon : "icon empire",
                    companyId : "bstek"
                });
                var tree = cola.widget("deptTree");
                var currentNode = tree.findNode(entity);
                tree.expand(currentNode);
                tree.set("currentItem", entity);
                event && event.stopPropagation();
            },
            add: function () {
                debugger;
                var tree, currentEditItem, currentNode, nodeEntity, nodes, order;
                tree = cola.widget("deptTree");
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
                    nodes = currentEditItem.get("depts");
                    order = 1;
                    if (!nodes) {
                        // 将currentEditItem设置成EntityList, 便于使用insert() 方法
                        currentEditItem.set("depts", []);
                        nodes = currentEditItem.get("depts")
                    } else {
                        if (nodes.entityCount>0)
                            order = nodes.last().get("order") + 1;
                    }

                    var entity=nodes.insert({
                        id: model.action.uuid(),
                        name : "<新部门>",
                        parentId : nodeEntity.get("id"),
                        order : order,
                        icon : "icon empire",
                        companyId : "bstek"
                    });

                    tree.expand(currentNode);
                    tree.set("currentItem",entity);
                    event && event.stopPropagation();
                }
            },
            remove : function(dept) {
                debugger;
                var currentEditItem = model.get("currentEditItem");
                var nodes = currentEditItem.get("depts", "sync");
                cola.confirm("您确定要删除当前记录吗？", {
                    onApprove : function() {
                        if (nodes && nodes.entityCount > 0) {
                            cola.alert("存在子菜单，无法删除！");
                        } else {
                            if (dept.state === "new")
                                return dept.remove();
                            $.ajax("./service/frame/dept/"+ dept.get("id"), {
                                type : "DELETE",
                                success : function() {
                                    dept.remove();
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
            saveDept: function () {
                debugger;
                var dept;
                dept = model.get("currentEditItem").toJSON();
                delete dept.depts;
                if (!model.get("currentEditItem").validate()) return;
                $.ajax("./service/frame/dept/", {
                    type: "POST",
                    data: JSON.stringify(dept),
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
            deptTree: {
                $type: "tree",
                lazyRenderChildNodes: true,
                autoExpand: true,
                autoCollapse: false,
                highlightCurrentItem: true,
                bind: {
                    expression: "dept in depts",
                    expanded: true,
                    child: {
                        recursive: true,
                        expression: "dept in sort(dept.depts, 'order')",
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
