/**
 * Created by carl.li on 2017/2/22.
 */
(function () {
    cola(function (model) {
        model.dataType({
            name: "Role",
            properties: {
                name: {
                    validators: ["required"]
                },
                desc: {
                    $type: 'string'
                }
            }
        });
        model.describe("roles", {
            dataType: "Role",
            provider: {
                url: "./service/frame/role/",
                pageSize: 5,
                beforeSend: function (self, arg) {
                    var contain = model.get("contain");
                    if (cola.defaultAction.isNotEmpty(contain)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.contain = encodeURI(contain);
                    }
                }
            }
        });
        model.get("roles", "sync");

        model.describe("urls", {
            provider: {
                url: "./service/frame/url/roleurls",
                beforeSend: function (self, arg) {
                    var roleId = cola.widget("roleTable").get("currentItem").get("id");
                    if (cola.defaultAction.isNotEmpty(roleId)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.roleId = encodeURI(roleId);
                    }
                }
            }
        });


        var addable, makeBody, makeNode, recursive;
        addable = function(node) {
            return node.nodes.length >= 1 || node.id || node.comment;
        };
        recursive = function(childNode) {
            var child, comment, n, nodes;
            nodes = [];
            child = childNode.firstChild;
            comment = "";
            while (child) {
                if (child.nodeType === 8) {
                    comment = child.nodeValue;
                } else if (child.nodeType === 1) {
                    n = makeNode(child, comment);
                    addable(n) && nodes.push(n);
                    comment = "";
                }
                child = child.nextSibling;
            }
            return nodes;
        };
        makeNode = function(element, comment) {
            var result;
            var visible=element.getAttribute('visible')=="true";
            var editable=element.getAttribute('editable')=="true";
            result = {
                tagName: element.nodeName,
                comment: comment,
                nodes: recursive(element),
                id: element.id,
                visible: visible,
                editable: editable,
                nodeType: element.nodeType
            };
            if (result.tagName === "TEMPLATE") {
                result.name = element.getAttribute("name");
            }
            return result;
        };
        makeBody = function(nodes) {
            debugger;
            var body, child, comment, i, len, node;
            body = {
                tagName: "body",
                nodes: []
            };
            comment = "";
            for (i = 0, len = nodes.length; i < len; i++) {
                child = nodes[i];
                if (child.nodeType === 8) {
                    comment = child.nodeValue;
                } else if (child.nodeType === 1) {
                    node = makeNode(child, comment);
                    addable(node) && body.nodes.push(node);
                    comment = "";
                }
            }
            return body;
        };
        model.set("nodeList", []);
        model.set("node", {
            tagName: "body",
            nodes: []
        });

        model.action({
            showPageDomTree: function(url){
                $.ajax("./service/frame/component/load", {data:{"url":url}}).done(function(result) {
                    debugger;
                    var body, components, findComponent, i, len, n, nodes, ref;
                    nodes = jQuery.parseHTML(result);
                    body = makeBody(nodes);
                    model.set("node", body);
                    components = [];
                    findComponent = function(el) {
                        var i, len, n, ref, results;
                        if (el.id) {
                            components.push(el);
                        }
                        ref = el.nodes;
                        results = [];
                        for (i = 0, len = ref.length; i < len; i++) {
                            n = ref[i];
                            results.push(findComponent(n));
                        }
                        return results;
                    };
                    ref = body.nodes;
                    for (i = 0, len = ref.length; i < len; i++) {
                        n = ref[i];
                        findComponent(n);
                    }
                    return model.set("nodeList", components);
                });
            },
            switchView: function(self, arg) {
                var body, findComponent, handler, index, mapping, nodeList;
                index = parseInt(self.get("userData"));
                nodeList = model.get("nodeList");
                body = model.get("node");
                if (index) {
                    handler = function(el) {
                        var id;
                        id = el.get("id");
                        return nodeList.each(function(n) {
                            if (n.get("id") === id) {
                                n.set("visible", el.get("visible"));
                                n.set("editable", el.get("editable"));
                                return false;
                            }
                        });
                    };
                    findComponent = function(el) {
                        var id, ref;
                        id = el.get("id");
                        if (id) {
                            handler(el);
                        }
                        return (ref = el.get("nodes")) != null ? ref.each(findComponent) : void 0;
                    };
                    body.get("nodes").each(findComponent);
                } else {
                    mapping = {};
                    findComponent = function(n) {
                        var id;
                        id = n.get("id");
                        if (id) {
                            mapping[id] = n;
                        }
                        return n.get("nodes").each(findComponent);
                    };
                    body.get("nodes").each(findComponent);
                    nodeList.each(function(el) {
                        var id, target;
                        id = el.get("id");
                        target = mapping[id];
                        if (target) {
                            target.set("visible", el.get("visible"));
                            return target.set("editable", el.get("editable"));
                        }
                    });
                }
                return cola.widget("cardBookDataView").setCurrentIndex(index);
            },
            save: function() {
                var body, childNode, i, index, item, j, len, len1, list, pushElement, recursiveMakeList, ref, ref1;
                index = cola.widget("cardBookDataView").get("currentIndex");
                list = [];
                pushElement = function(el) {
                    return list.push({
                        componentId: el.id,
                        visible: el.visible,
                        editable: el.editable,
                        roleId: cola.widget("roleTable").get("currentItem").get("id"),
                        urlId: model.get("urlId")
                    });
                };
                if (index === 1) {
                    body = model.get("node").toJSON();
                    recursiveMakeList = function(el) {
                        var childNode, i, len, ref, results;
                        if (el.id) {
                            pushElement(el);
                        }
                        ref = el.nodes;
                        results = [];
                        for (i = 0, len = ref.length; i < len; i++) {
                            childNode = ref[i];
                            results.push(recursiveMakeList(childNode));
                        }
                        return results;
                    };
                    ref = body.nodes;
                    for (i = 0, len = ref.length; i < len; i++) {
                        childNode = ref[i];
                        recursiveMakeList(childNode);
                    }
                } else {
                    ref1 = model.get("nodeList").toJSON();
                    for (j = 0, len1 = ref1.length; j < len1; j++) {
                        item = ref1[j];
                        pushElement(item);
                    }
                }

                return $.ajax("./service/frame/urlcomponent/", {
                    data: JSON.stringify(list),
                    contentType: "application/json",
                    type: "POST",
                    success:function(){
                        cola.alert("保存成功!")
                    }
                });

            },
            getNodeName: function(node) {
                var comment, id, nodeName, tagName;
                id = node.get("id");
                tagName = node.get("tagName");
                comment = node.get("comment");
                nodeName = "";
                if (id) {
                    nodeName = tagName + "#" + id;
                } else {
                    nodeName = tagName;
                }
                if (comment) {
                    nodeName += comment;
                }
                return nodeName;
            }
        });

        model.widgetConfig({
            roleTable: {
                $type: "table",
                bind: "role in roles",
                showHeader: true,
                changeCurrentItem: true,
                highlightCurrentItem: true,
                currentPageOnly: true,
                columns: [{
                    caption: "角色名",
                    bind: ".name"
                },{
                    caption: "角色描述",
                    bind: ".desc"
                }],
                itemClick: function (self, arg) {
                    if (self.get("currentItem").get("id") != model.get("roleId")) {
                        model.set("roleId", self.get("currentItem").get("id"));
                        model.flush("urls");
                    }
                }
            },
            urlTree: {
                $type: "tree",
                highlightCurrentItem: true,
                autoExpand: true,
                bind: {
                    expression: "url in urls",
                    textProperty: "label",
                    child: {
                        recursive: true,
                        expression: "url in sort(url.menus, 'order')",
                        textProperty: "label"
                    }
                },
                itemClick: function(self, arg) {
                    var use=arg.item.get('data.forNavigation');
                    var url=arg.item.get('data.path');
                    var urlId=arg.item.get('data.id');
                    if(use&&url){
                        model.set("urlId", urlId);
                        model.action.showPageDomTree(url);
                    }else{
                        model.set("nodeList", []);
                        model.set("node", {
                            tagName: "body",
                            nodes: []
                        });
                    }
                }
            },
            domTree: {
                $type: "Tree",
                height: "92%",
                autoCollapse: false,
                autoExpand: true,
                bind: {
                    recursive: true,
                    expression: "node in node.nodes"
                },
                itemClick: function(self, arg) {
                    self.get$Dom().find(".current-node").removeClass("current-node");
                    return $(arg.dom).addClass("current-node");
                }
            },
            domList: {
                $type: "listView",
                bind: "node in nodeList",
                textProperty: "id"
            }
        });
    });
}).call(this);