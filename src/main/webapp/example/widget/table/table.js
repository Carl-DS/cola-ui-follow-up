(function() {
  cola(function(model) {
    model.describe("products", {
      dataType: {
        name: "Product",
        properties: {
          name: {
            validators: ["required"]
          },
          price: {
            dataType: "number"
          }
        }
      },
      provider: {
        url: "/service/product/?categoryId=1",
        pageSize: 8
      }
    });
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
        // height: "100%",
        columns: [
          {
            bind: ".id",
            caption: "产品编号"
          }, {
            caption: "产品名称",
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
          if (caption === "产品名称") {
            return $(arg.dom).parent().addClass("product-name");
          }
        },
        renderRow: function(self, arg) {
          var item, rowDom;
          item = arg.item;
          rowDom = arg.dom;
          $fly(rowDom).addClass("product-item");
          return $(arg.dom).delegate(".product-name", "click", function() {
            var $rowDom, innerDom, nextIsDetail, oldNodes, context, setTable;
            $rowDom = $fly(rowDom);
            nextIsDetail = $rowDom.next().hasClass("row-detail");
            oldNodes = $rowDom.parent().find(">.row-detail");
            oldNodes.prev().find(".product-name.expanded").removeClass("expanded");
            oldNodes.find('>td>div').animate({
              speed:10,
              height: "0px"
            }, function() {
              return oldNodes.remove();
            });
            if (nextIsDetail) {
              return;
            }

            context = {};
            // $.xCreate() 的用法请参考http://cola-ui.com/api/$.html
            innerDom = $.xCreate({
              tagName: "tr",
              "class": "row-detail",
              content: [
                {
                  tagName: "td",
                  colspan: 6,
                  content: {
                    tagName: "div",
                    contextKey:"tablePane"
                  }
                }
              ]
            }, context);

            setTable = new cola.Table({
                $type: "table",
                showHeader: true,
                bind: "item in products",
                highlightCurrentItem: true,
                currentPageOnly: true,
                height: "100%",
                columns: [
                    {
                        bind: ".id",
                        caption: "产品编号"
                    }, {
                        caption: "产品名称",
                        bind: ".productName"
                    }, {
                        bind: ".reorderLevel",
                        caption: "订货量",
                        align: "right"
                    }, {
                        bind: ".unitPrice",
                        caption: "价格",
                        align: "center",
                        "class": "sss"
                    }, {
                        bind: ".quantityPerUnit",
                        caption: "经营商"
                    }
                ]
            });
            $rowDom.after(innerDom);
            $rowDom.find(".product-name").addClass("expanded");
            $fly(innerDom).find(">td>div").animate({
                speed:10,
                height: "200px"
            });
            return setTable.appendTo(context.tablePane);

          });
        }
      }
    });
  });

}).call(this);
