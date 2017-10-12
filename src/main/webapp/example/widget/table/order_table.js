(function() {
  cola(function(model) {
    // 声明产品类型
    model.describe("products", {
      dataType: {
        name: "Product",
        properties: {
          name: {
            validators: ["required"]
          },
          unitPrice: {
            dataType: "number"
          }
        }
      },
      provider: {
        url: "./service/product?categoryId=1",
        pageSize: 5
      }
    });

    // 事件区域
    model.action({

    });

    return model.widgetConfig({
      productTable: {
        $type: "table",
        showHeader: true,
        bind: "product in products",
        highlightCurrentItem: true,
        currentPageOnly: true,
        sortMode: "remote",
        height: "100%",
        columns: [
          {
            caption: "产品编号",
            bind: ".id",
            sortable: true
          }, {
            caption: "产品名称",
            bind: ".productName",
            sortable: true
          }, {
            caption: "订货量",
            bind: ".reorderLevel",
            sortable: true,
            align: "right"
          }, {
            caption: "价格",
            template: "unitPriceTemp",
            sortable: true,
            property: "unitPrice",
            align: "center"
          }, {
            caption: "经营商",
            bind: ".quantityPerUnit",
            sortable: true
          }
        ]
      }
    });


  });
}).call(this);
