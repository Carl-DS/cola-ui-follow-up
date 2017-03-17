(function () {
    cola(function (model) {
        $(".ui.accordion").accordion({
            exclusive: false
        });
        model.describe("employees", {
            dataType: {
                name: "Employee",
                properties: {
                    lastName: {
                        validators: [
                            "required", {
                                $type: "length",
                                min: 4,
                                max: 20
                            }
                        ]
                    },
                    firstName: {
                        validators: [
                            "required", {
                                $type: "length",
                                min: 4,
                                max: 20
                            }
                        ]
                    },
                    sex: {
                        dataType: "boolean",
                        defaultValue: true,
                        validators: ["required"]
                    },
                    birthDate: {
                        dataType: "date"
                    },
                    hireDate: {
                        dataType: "date"
                    },
                    phone: {
                        validators: ["required"]
                    }
                }
            },
            provider: {
                url: "./service/employee/",
                pageSize: 4,
                beforeSend: function (self, arg) {
                    var contain, data;
                    data = arg.options.data;
                    contain = model.get("contain");
                    return data.contain = contain;
                }
            }
        });
        model.set("countries", [
            {
                name: "中国"
            }, {
                name: "美国"
            }, {
                name: "加拿大"
            }, {
                name: "印度尼西亚"
            }, {
                name: "马来西亚"
            }, {
                name: "英国"
            }, {
                name: "韩国"
            }, {
                name: "蒙古国"
            }, {
                name: "俄罗斯"
            }
        ]);
        model.describe("editItem", "Employee");
        model.action({
            getColor: function (status) {
                if (status === "完成") {
                    return "positive-text";
                } else {
                    return "negative-text";
                }
            },
            search: function () {
                return model.flush("employees");
            },
            add: function () {
                model.set("editItem", {
                    sex: true
                });
                return cola.widget("editLayer").show();
            },
            edit: function (item) {
                model.set("editItem", item.toJSON());
                return cola.widget("editLayer").show();
            },
            cancel: function () {
                return cola.widget("editLayer").hide();
            },
            ok: function () {
                debugger;
                var data, editItem, id, validate;
                editItem = model.get("editItem");
                validate = editItem.validate();
                console.log(validate);
                if (validate) {
                    id = editItem.get("id");
                    data = editItem.toJSON();
                    return $.ajax("./service/employee/", {
                        data: JSON.stringify(data),
                        type: data.id ? "PUT" : "POST",
                        contentType: "application/json",
                        complete: function () {
                            cola.widget("editLayer").hide();
                        }
                    });
                }
            },
            del: function (item) {
                return item.remove();
            }
        });
        return model.widgetConfig({
            radioGroup: {
                items: [
                    {
                        value: true,
                        label: "男"
                    }, {
                        value: false,
                        label: "女"
                    }
                ]
            },
            editLayer: {
                $type: "layer",
                width: "100%",
                onShow: function () {
                    return $("#mainView").hide();
                },
                beforeHide: function () {
                    return $("#mainView").show();
                }
            },
            shopDropDown: {
                $type: "dropdown",
                "class": "error",
                openMode: "drop",
                items: "{{shop in shops}}",
                valueProperty: "name",
                bind: "editItem.shop"
            },
            birthDatePicker: {
                $type: "datePicker",
                bind: "editItem.birthDate"
            },
            hireDatePicker: {
                $type: "datePicker",
                bind: "editItem.hireDate"
            },
            countryDropDown: {
                $type: "dropdown",
                "class": "error",
                openMode: "drop",
                items: "{{country in countries}}",
                valueProperty: "name",
                bind: "editItem.country"
            },
            employeeTable: {
                $type: "table",
                showHeader: true,
                bind: "item in employees",
                highlightCurrentItem: true,
                currentPageOnly: true,
                changeCurrentItem: true,
                columns: [
                    {
                        bind: ".id",
                        caption: "ID",
                        visible: false
                    }, {
                        bind: ".lastName",
                        caption: "Last Name"
                    }, {
                        bind: ".firstName",
                        caption: "First Name"
                    }, {
                        bind: ".title",
                        caption: "Title"
                    }, {
                        bind: ".titleOfCourtesy",
                        caption: "Title Of Courtesy"
                    }, {
                        bind: ".sex",
                        caption: "Sex"
                    }, {
                        bind: "formatDate(employee.birthDate, 'yyyy-MM-dd')",
                        caption: "Birthday"
                    }, {
                        bind: ".phone",
                        caption: "phone"
                    }, {
                        caption: "Operation",
                        align: "center",
                        template: "operations"
                    }
                ]
            }
        });
    });

}).call(this);
