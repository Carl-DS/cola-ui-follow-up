(function() {
  cola(function(model) {
    model.describe("products", {
      dataType: {
        name: "Product",
        properties: {
          productName: {
            validators: ["required"]
          },
          unitPrice: {
            dataType: "number"
          }
        }
      },
      provider: {
        url: "/service/product/?categoryId=1",
        pageSize: 5
      }
    });
    model.set("origPrices", [
      {
        price: "第一种产品"
      }, {
        price: "第二种产品"
      }, {
        price: "第三种产品"
      }, {
        price: "第四种产品"
      }, {
        price: "第五种产品"
      }
    ]);

    model.action({
      blur: function(self, arg) {
        return self.get$Dom().closest(".product-price").removeClass("focus");
      }
    });

    return model.widgetConfig({
      productTable: {
        $type: "table",
        showHeader: true,
        bind: "item in products",
        highlightCurrentItem: true,
        currentPageOnly: true,
        height: "300px",
        columns: [
          {
            caption: "产品编号",
            bind: ".id"
          }, {
            caption: "产品",
            template: "productName"
          }, {
            caption: "订货量",
            template: "reorderLevel"
          }, {
            caption: "价格",
            template: "price"
          }, {
            caption: "经营商",
            bind: ".quantityPerUnit"
          }
        ],
        renderCell: function(self, arg) {
          var caption;
          caption = arg.column.get("caption");
          if (arg.column.get("caption") === "产品") {
            $(arg.dom).addClass("product-price");
            return $(arg.dom).on("click", function() {
              debugger;
              var $input;
              $fly(arg.dom).addClass("focus");
              $input = $fly(arg.dom).find(".ui.input");
              cola.widget($input[0]).set("value",arg.item.get("productName"))
              return cola.widget($input[0]).focus();
            });
          } else if (caption === "价格") {
            $(arg.dom).addClass("product-price");
            return $(arg.dom).on("click", function() {
              debugger;
              var $input;
              $fly(arg.dom).addClass("focus");
              $input = $fly(arg.dom).find(".ui.input");
              cola.widget($input[0]).set("value", arg.item.get("unitPrice"))
              return cola.widget($input[0]).focus();
            });
          } else if (caption === "订货量") {
            $(arg.dom).addClass("product-price");
            return $(arg.dom).on("click", function() {
              debugger;
              var $input;
              $fly(arg.dom).addClass("focus");
              $input = $fly(arg.dom).find(".ui.input");
              cola.widget($input[0]).set("value", arg.item.get("reorderLevel"))
              return cola.widget($input[0]).focus();
            });
          }
        },
        renderRow: function(self, arg) {
          var item, rowDom;
          item = arg.item;
          rowDom = arg.dom;
          $fly(rowDom).addClass("product-item");
          return $(arg.dom).delegate(".product-name", "click", function() {
            var $rowDom, innerDom, nextIsDetail, oldNodes;
            $rowDom = $fly(rowDom);
            nextIsDetail = $rowDom.next().hasClass("row-detail");
            oldNodes = $rowDom.parent().find(">.row-detail");
            oldNodes.prev().find(".product-name.expanded").removeClass("expanded");
            oldNodes.find('>td>div').animate({
              height: "0px"
            }, function() {
              return oldNodes.remove();
            });
            if (nextIsDetail) {
              return;
            }
            innerDom = $.xCreate({
              tagName: "tr",
              "class": "row-detail",
              content: [
                {
                  tagName: "td",
                  colspan: 6,
                  content: {
                    tagName: "div"
                  }
                }
              ]
            });
            $rowDom.after(innerDom);
            $rowDom.find(".product-name").addClass("expanded");
            return $fly(innerDom).find(">td>div").animate({
              height: "200px"
            });
          });
        }
      },
      customizeDropdown: {
        $type: "dropdown",
        openMode: "drop",
        placeholder: "请选择",
        items: "{{origPrice in origPrices}}",
        valueProperty: "price",
        textProperty: "price",
        bind: "item.productName",
        post: function (self) {
          debugger;
          var productTableDom = cola.widget("productTable").getDom();
          $fly(productTableDom).find(".table.item.default.current .product-price").addClass("focus");
          cola.widget("productTable").get("currentItem").set("unitPrice", 20);
          cola.widget("productTable").get("currentItem").set("reorderLevel", 10);
          return self.get$Dom().closest(".product-price").removeClass("focus");
        }
      }
    });
  });

}).call(this);
