/**
 * Created by carl.li on 2017/2/22.
 */
(function () {
    cola(function (model) {
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
                }
            }
        });
        // 以同步的方式先将roles加载出来,
        // 便于其它数据获取roleId
        model.get("roles", "sync");

        // 声明角色成员用户的EntityList
        model.describe("roleUsers", {
            provider: {
                url: "/service/frame/user/roleusers/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var roleId = cola.widget("roleTable").get("currentItem").get("id");
                    if (cola.defaultAction.isNotEmpty(roleId)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.roleId = encodeURI(roleId);
                    }
                }
            }
        });
        // 待选择的用户
        model.describe("users", {
            provider: {
                url: "/service/frame/user/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var contain = model.get("contain");
                    if (cola.defaultAction.isNotEmpty(contain)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.contain = encodeURI(contain);
                    }
                }
            }
        });
        // 声明已选择的用户的EntityList
        model.set("selectedUsers", []);

        // 声明角色成员岗位的EntityList
        model.describe("rolePositions", {
            provider: {
                url: "/service/frame/position/rolepositions/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var groupId = cola.widget("roleTable").get("currentItem").get("id");
                    if (cola.defaultAction.isNotEmpty(groupId)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.groupId = encodeURI(groupId);
                    }
                }
            }
        });
        // 待选择的岗位
        model.describe("positions", {
            provider: {
                url: "/service/frame/position/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var contain = model.get("contain");
                    if (cola.defaultAction.isNotEmpty(contain)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.contain = encodeURI(contain);
                    }
                }
            }
        });
        // 声明已选择的岗位的EntityList
        model.set("selectedPositions", []);

        // 声明角色成员部门的EntityList
        model.describe("roleDepts", {
            provider: {
                url: "service/frame/dept/roledepts/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var groupId = cola.widget("roleTable").get("currentItem").get("id");
                    if (cola.defaultAction.isNotEmpty(groupId)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.groupId = encodeURI(groupId);
                    }
                }
            }
        });
        // 待选择的部门
        model.describe("depts", {
            provider: {
                url: "service/frame/dept/depts"
            }
        });
        // 声明已选择的岗位的EntityList
        model.set("selectedDepts", []);

        // 声明角色成员群组的EntityList
        model.describe("roleGroups", {
            provider: {
                url: "service/frame/dept/rolegroups/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var groupId = cola.widget("roleTable").get("currentItem").get("id");
                    if (cola.defaultAction.isNotEmpty(groupId)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.groupId = encodeURI(groupId);
                    }
                }
            }
        });
        // 待选择的群组
        model.describe("groups", {
            provider: {
                url: "/service/frame/group/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var contain = model.get("contain");
                    if (cola.defaultAction.isNotEmpty(contain)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.contain = encodeURI(contain);
                    }
                }
            }
        });
        // 声明已选择的群组的EntityList
        model.set("selectedGroups", []);

        model.action({
            refresh: function () {
                cola.confirm("真的要刷新权限缓存信息吗?",{
                    title: "消息提示",
                    level: "info",
                    onApprove: function () { // 确认时触发的回调
                        cola.alert("刷新成功!");
                    }
                });
            },
            cancel: function (type) {
                model.set("editItem", {});
                var widgetId = type + "Sidebar";
                return cola.widget(widgetId).hide();
            },
            editUser: function () {
                // 刷新待选用户
                model.flush("users");
                // 重置已选择用户
                model.set("selectedUsers", []);
                cola.widget("userSidebar").show();
            },
            editPosition: function () {
                // 刷新待选岗位
                model.flush("positions");
                // 重置已选择岗位
                model.set("selectedPositions", []);
                cola.widget("positionSidebar").show();
            },
            editDept: function () {
                // 重置已选择岗位
                model.set("selectedDepts", []);
                cola.widget("deptSidebar").show();
            },
            editGroup: function () {
                // 重置已选择群组
                model.set("selectedGroups", []);
                cola.widget("groupSidebar").show();
            },
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
                    caption: "角色名称",
                    bind: ".name"
                },{
                    caption: "角色描述",
                    bind: ".desc"
                }],
                itemClick: function (self, arg) {
                    // 拿到当前行id,根据id获取后台功能数据
                    var roleId = self.get("currentItem").get("id");
                    if (roleId!=model.get("roleId")) {
                        model.set("roleId", roleId);
                        model.flush("roleUsers");
                        model.flush("rolePositions");
                        model.flush("roleDepts");
                        model.flush("roleGroups");
                    }
                }
            },
            roleUserTable: {
                $type: "table",
                bind: "roleUser in roleUsers",
                showHeader: true,
                changeCurrentItem: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".username",
                        caption: "用户名",
                        visible: true // 是否可见
                    }, {
                        bind: ".cname",
                        caption: "中文名"
                    }, {
                        bind: ".ename",
                        caption: "英文名"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            },
            userTable: {
                $type: "table",
                bind: "user in users",
                showHeader: true,
                changeCurrentItem: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".username",
                        caption: "用户名",
                        visible: true // 是否可见
                    }, {
                        bind: ".cname",
                        caption: "中文名"
                    }, {
                        bind: ".ename",
                        caption: "英文名"
                    }
                ]
            },
            selectedUserTable: {
                $type: "table",
                bind: "selectedUser in selectedUsers",
                showHeader: true,
                changeCurrentItem: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".username",
                        caption: "用户名",
                        visible: true // 是否可见
                    }, {
                        bind: ".cname",
                        caption: "中文名"
                    }, {
                        bind: ".ename",
                        caption: "英文名"
                    }
                ]
            },
            rolePositionTable: {
                $type: "table",
                bind: "rolePosition in rolePositions",
                changeCurrentItem: true,
                showHeader: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "岗位名称"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            },
            positionTable: {
                $type: "table",
                bind: "position in positions",
                changeCurrentItem: true,
                showHeader: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "岗位名称"
                    }
                ]
            },
            selectedPositionTable: {
                $type: "table",
                bind: "selectedPosition in selectedPositions",
                changeCurrentItem: true,
                showHeader: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "岗位名称"
                    }
                ]
            },
            roleDeptTable: {
                $type: "table",
                bind: "groupDept in groupDepts",
                showHeader: true,
                changeCurrentItem: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "部门名称"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            },
            deptTree: {
                $type: "tree",
                lazyRenderChildNodes: true,
                autoExpand: false,
                autoCollapse: false,
                highlightCurrentItem: true,
                bind: {
                    expression: "dept in depts",
                    expanded: false,
                    checkedProperty: "checked",
                    valueProperty: "id",
                    textProperty: "name",
                    child: {
                        recursive: true,
                        expression: "dept in sort(dept.depts, 'order')",
                        expanded: false,
                        checkedProperty: "checked",
                        valueProperty: "id",
                        textProperty: "name"
                    }
                },
                itemClick: function (self, arg) {
                    debugger;
                    var isSame = false;
                    var currentData = self.get("currentNode").get("data");
                    if (cola.defaultAction.isNotEmpty(currentData)) {
                        if (currentData.get("checked")) {
                            var groupId = cola.widget("groupTable").get("currentItem").get("id");
                            var deptId = currentData.get("id");

                            var selectedDepts = model.get("selectedDepts");
                            if (selectedDepts.entityCount > 0) {
                                selectedDepts.each(function (selectedDept) {
                                    if (deptId === selectedDept.get("id")) {
                                        return isSame = true;
                                    }
                                });
                            }
                            if (!isSame) {
                                $.ajax("./service/frame/groupmember/checksame/dept/", {
                                    type: "GET",
                                    data: {"groupId": groupId, "deptId": deptId},
                                    contentType: "application/json; charset=utf-8",
                                    success: function (self, arg) {
                                        if (self.length > 0) {
                                            currentData.set("checked", false);
                                            cola.alert("当前选择的[" + currentData.get("name") + "]已添加, 请重新选择");
                                        } else {
                                            model.get("selectedDepts").insert(currentData.toJSON());
                                        }
                                    }
                                });
                            }
                        }

                    }
                }
            },
            roleGroupTable: {
                $type: "table",
                bind: "roleGroup in roleGroups",
                showHeader: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "群组名称"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            },
            groupTable: {
                $type: "table",
                bind: "group in groups",
                showHeader: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "群组名称"
                    }
                ]
            },
            selectedGroupTable: {
                $type: "table",
                bind: "selectedGroup in selectedGroups",
                showHeader: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "群组名称"
                    }
                ]
            }

        });
    });
}).call(this);