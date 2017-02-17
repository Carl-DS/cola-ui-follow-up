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
        name: "provider1",
        url: "/service/product/?categoryId=1",
        pageSize: 5
      }
    });
    model.set("origPrices", [
      {
        price: "10"
      }, {
        price: "20"
      }, {
        price: "30"
      }, {
        price: "40"
      }, {
        price: "50"
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
            bind: ".id",
            caption: "产品编号"
          }, {
            caption: "产品",
            bind: ".productName"
          }, {
            bind: ".reorderLevel",
            caption: "订货量",
            align: "right"
          }, {
            template: "price",
            caption: "价格",
            align: "center",
            "class": "sss"
          }, {
            bind: ".quantityPerUnit",
            caption: "经营商"
          }
        ],
        renderCell: function(self, arg) {
          var caption;
          caption = arg.column.get("caption");
          if (arg.column.get("caption") === "价格") {
            $(arg.dom).addClass("product-price");
            return $(arg.dom).on("click", function() {
              /*debugger;*/
              var $input;
              $fly(arg.dom).addClass("focus");
              $input = $fly(arg.dom).find(".ui.input");
              cola.widget($input[0]).set("value",arg.item.get("unitPrice"))
              return cola.widget($input[0]).focus();
            });
          } else if (caption === "产品名称") {
            return $(arg.dom).parent().addClass("product-name");
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
        bind: "item.unitPrice",
        post: function (self) {
            return self.get$Dom().closest(".product-price").removeClass("focus");
        }
      }
    });
  });

}).call(this);
