/**
 * Created by carl.li on 2017/2/22.
 */
(function () {
    cola(function (model) {
        // 无校验,或使用数据类型时可省略dataType的声明
        /*model.dataType({
            name: "Role",
            properties: {
                name: {
                    validators: ["required"]
                },
                desc: {
                    $type: 'string'
                }
            }
        });*/
        model.describe("roles", {
            provider: {
                url: "/service/frame/role/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var contain = model.get("contain");
                    if (cola.defaultAction.isNotEmpty(contain)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.contain = encodeURI(contain);
                    }
                },
                complete: function () {
                    // 生成序号
                    var serialNo = 1;
                    model.get("roles").each(function(role){
                        role.set("serialNo",serialNo++);
                    });
                    if (!model.get("roleId")) {
                        model.set("roleId",model.get("roles").current.get("id"));
                    }
                }
            }
        });

        // 此处为了解决加载urls时无法拿到roleId
        setTimeout(function () {
            model.describe("urls", {
                provider: {
                    url: "./service/frame/url/resource/{{@roleId}}"
                }
            });
            model.flush("urls");
        }, 200);

        model.action({
            refreshUrl: function() {
                model.flush("urls");
            },
            selectedNodes: function (nodes, checkedNode, unCheckNode) {
                $.each(nodes, function () {
                    var node = this;
                    if (node.get("checked")) {
                        checkedNode.push(node.get("data.id"));
                    } else {
                        unCheckNode.push(node.get("data.id"));
                    }
                    var children = node.get("children");
                    if (children) {
                        model.action.selectedNodes(children, checkedNode, unCheckNode);
                    }
                });
            },
            saveUrlResource: function () {
                // 此处也可以mode.get(urls); 方式获取数据
                var nodes = cola.widget("urlTree").getItems();
                var checkedNode = []; // 选中的节点
                var noCheckNode = []; // 末选中的节点
                model.action.selectedNodes(nodes,checkedNode,noCheckNode);
                var roleId = model.get("roleId");
                model.set("roleResource", {
                    roleId: roleId,
                    urlIds: checkedNode,
                    excludeUrlIds: noCheckNode
                });
                var data = model.get("roleResource").toJSON();
                if (checkedNode.length > 0) {
                    $.ajax("./service/frame/roleresource/", {
                        type: "POST",
                        data: JSON.stringify(data),
                        contentType: "application/json; charset=utf-8",
                        success: function() {

                        }
                    });
                } else {
                    cola.alert("请选择树节点！");
                }
            }
        });

        model.widgetConfig({
            roleTable: {
                $type: "table",
                bind: "role in roles",
                showHeader: true,
                changeCurrentItem: true,
                highlightCurrentItem: true,
                currentPageOnly: true, // 只显示当前页,表现在分页时是表格数据的添加还是切换
                columns: [{
                    caption: "序号",
                    bind: ".serialNo"
                },{
                    caption: "角色名",
                    bind: ".name"
                },{
                    caption: "角色描述",
                    bind: ".desc"
                }],
                itemClick: function (self, arg) {
                    // 拿到当前行id,根据id获取后台功能数据
                    if (self.get("currentItem").get("id") != model.get("roleId")) {
                        model.set("roleId", self.get("currentItem").get("id"));
                        model.flush("urls");
                    }
                }
            },
            urlTree: {
                $type: "tree",
                lazyRenderChildNodes: false, // 懒装载子节点
                autoExpand: false, // 是否在用户点击某个树节点时自动展开该节点
                autoCollapse: false, // 是否在展开某个树节点时自动收缩与其同层的其他树节点
                highlightCurrentItem: true,
                bind: {
                    expression: "url in urls",
                    popup: "url.label", // disabled
                    valueProperty: "id",
                    textProperty: "label",
                    checkedProperty: "forNavigation",
                    expanded: true,
                    child: {
                        recursive: true,
                        expression: "url in sort(url.menus, 'order')",
                        popup: "url.label", // disabled
                        valueProperty: "id",
                        textProperty: "label",
                        checkedProperty: "forNavigation",
                        expanded: true
                    }
                }
            }
        });
    });
}).call(this);