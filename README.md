# DialogActionSheet
自定义对话框达到ios的ActionSheet效果
效果如下：

1 链式调用,标题默认隐藏，当设置标题后显示

    new DialogActionSheet(MainActivity.this).builder().setTitle("标题")
    .setTitleColor(Color.parseColor("#36ae9e"))
    .setCancelText("关闭").setCancelColor(Color.parseColor("#FD4A2E"))
    .addSheetItem("拍摄一张", Color.parseColor("#555555"),
    new DialogActionSheet.OnSheetItemClickListener() {
    @Override
    public void onClick(int which) {
    }
    })
    .addSheetItem("从相册中选择", Color.parseColor("#555555"),
    new DialogActionSheet.OnSheetItemClickListener() {
    @Override
    public void onClick(int which) {
    }
    }).show();
    
